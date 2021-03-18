package com.example.myapplication.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.DateFun
import com.example.myapplication.interfaces.LaboresAPI
import com.example.myapplication.models.Labor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class LaboresAPIStatus {LOADING, DONE, ERROR}
class RegisterActivitiyViewModel : ViewModel(){

    private var _listaLabores = MutableLiveData<List<Labor>>()
        val listaLabores:LiveData<List<Labor>>
        get() = _listaLabores

    private val _response = MutableLiveData<String>()
        val response:LiveData<String>
            get() = _response

    private val _status = MutableLiveData<LaboresAPIStatus>()
        val status: LiveData<LaboresAPIStatus>
        get() = _status

    private val _DateF = MutableLiveData<String>()
        val DateF: LiveData<String>
        get() = _DateF

    init{
        Log.i("Init viewmodel", "Inicia el view model")
        _listaLabores.value = mutableListOf()
        _DateF.value = DateFun().date()
        CoroutineScope(IO).launch {
            Log.i("Init viewmodel", "Coroutine Scope")
            withContext(Dispatchers.Default) {
                getLaboresList()
            }
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