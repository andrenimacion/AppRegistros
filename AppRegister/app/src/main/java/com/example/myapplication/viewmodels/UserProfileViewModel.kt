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
import com.example.myapplication.models.RegisterPost
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

    private val _registro = MutableLiveData<RegistroPesada>()
    val registro:LiveData<RegistroPesada>
        get() = _registro

    private val _user = MutableLiveData<Jornalero>()
    val user:LiveData<Jornalero>
        get() = _user

    init {
        Log.i("UserProfile Init", user.value.toString())

    }

    fun setUser(user:Jornalero){
        _user.value = user
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

    fun postEntrance(){
        //_registro.value = RegistroPesada(_user.value!!)
        var register = RegisterPost(_user.value!!.cod_usuario)
        var respon:String = ""
        Log.i("PostRegister", register.toString())
        viewModelScope.launch {
            try {
                val response = getRetrofit().create(RegistroPesadaAPI::class.java)
                    .postWeightRegister("_post_", register)
                Log.i("Response", response)

            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("Exception",e.message)
            }
        }
    }

}