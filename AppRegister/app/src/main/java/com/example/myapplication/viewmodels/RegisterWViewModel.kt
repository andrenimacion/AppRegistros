package com.example.myapplication.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.BASE_URL
import com.example.myapplication.DateFun
import com.example.myapplication.interfaces.LaboresAPI
import com.example.myapplication.interfaces.RegistroPesadaAPI
import com.example.myapplication.models.Jornalero
import com.example.myapplication.models.Labor
import com.example.myapplication.models.RegistroPesada
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RegisterWViewModel : ViewModel() {
    private var _listaLabores = MutableLiveData<List<Labor>>()
    val listaLabores: LiveData<List<Labor>>
        get() = _listaLabores

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val _status = MutableLiveData<LaboresAPIStatus>()
    val status: LiveData<LaboresAPIStatus>
        get() = _status

    private val _DateF = MutableLiveData<String>()
    val DateF: LiveData<String>
        get() = _DateF

    private val _jornalero = MutableLiveData<Jornalero>()
    val jornalero: LiveData<Jornalero>
        get() = _jornalero

    private val _registro = MutableLiveData<RegistroPesada>()
    val registro: LiveData<RegistroPesada>
        get() = _registro

    private fun getRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    init{
        Log.i("Init viewmodel", "Inicia el view model")
        _listaLabores.value = mutableListOf()
        _DateF.value = DateFun().date()
        //constructRegister()
        CoroutineScope(Dispatchers.IO).launch {
            Log.i("Init viewmodel", "Coroutine Scope")
            async{
                getLaboresList()
            }.await()
        }
    }
    private fun constructRegister(){
        //_registro.value = RegistroPesada(_jornalero.value!!)
        //_registro.value!!.fecha = DateFun().date()!!
    }

    fun postRegister(){
        try {
            viewModelScope.launch {
                getRetrofit().create(RegistroPesadaAPI::class.java).postWeightRegister("_PUT_",_registro.value!!)
            }
        }catch (e:Exception){

        }
    }


    /*private fun getLaboresList(){
        Log.i("Here", "LaboresList")
        viewModelScope.launch {
            _status.value = LaboresAPIStatus.LOADING
            try {
                runCatching {
                    Log.i("Here", "Entre")
                    var laboresResult = LaboresAPI.retrofitService.getLabores()
                    Log.i("Here", laboresResult.toString())
                    laboresResult
                }.onSuccess {
                    _listaLabores.value = it
                }.onFailure {
                    it.message
                    _status.value = LaboresAPIStatus.ERROR
                }
                _status.value = LaboresAPIStatus.DONE
            } catch (e :Exception){
                _status.value = LaboresAPIStatus.ERROR
                _listaLabores.value = ArrayList()
            }
        }
    }*/
    private fun getLaboresList(){
        try {
            val response = LaboresAPI.retrofitService.getLaboresSynchro().execute()
            _listaLabores.value = response.body()
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    override fun onCleared() {
        super.onCleared()
        Log.i("Cleared", "Se cerro la pantalla")
    }





}