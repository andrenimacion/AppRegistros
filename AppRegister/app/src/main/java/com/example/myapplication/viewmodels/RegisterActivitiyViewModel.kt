package com.example.myapplication.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.BASE_URL
import com.example.myapplication.DateFun
import com.example.myapplication.interfaces.LaboresAPI
import com.example.myapplication.models.Labor
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.Exception
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

        getLaboresList()
    }


    /*private fun getLaboresList(){
        viewModelScope.launch {
            _status.value = LaboresAPIStatus.LOADING
            try {
                var laboresResult = LaboresAPI.retrofitService.getLabores()
                Log.i("Here", laboresResult.toString())
                val response = laboresResult
                _listaLabores.value = response
                _status.value = LaboresAPIStatus.DONE
            } catch (e :Exception){
                _status.value = LaboresAPIStatus.ERROR
                _listaLabores.value = ArrayList()
            }
        }
    }*/
    private fun getLaboresList(){
        LaboresAPI.retrofitService.getLaboresSynchro().execute()
         

    }

    override fun onCleared() {
        super.onCleared()
        Log.i("Cleared", "Se cerro la pantalla")
    }
}