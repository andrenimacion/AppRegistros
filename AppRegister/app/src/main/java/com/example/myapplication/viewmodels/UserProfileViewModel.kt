package com.example.myapplication.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.BASE_URL
import com.example.myapplication.interfaces.LaboresAPI
import com.example.myapplication.interfaces.RegistroPesadaAPI
import com.example.myapplication.models.Jornalero
import com.example.myapplication.models.Labor
import com.example.myapplication.models.RegistroPesada
import com.google.gson.GsonBuilder
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class UserProfileViewModel: ViewModel() {

    val _registro = MutableLiveData<RegistroPesada>()
    val registro:LiveData<RegistroPesada>
        get() = _registro

    init {
        var user = MutableLiveData<Jornalero>()
    }

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

    private fun postEntrance(){
        viewModelScope.launch {
            try {
                getRetrofit().create(RegistroPesadaAPI::class.java)
                    .postWeightRegister("_POST_", _registro.value!!)
            } catch (e: Exception) {

            }
        }
    }

}