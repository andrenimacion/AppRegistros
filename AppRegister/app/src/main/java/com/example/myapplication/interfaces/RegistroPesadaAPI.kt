package com.example.myapplication.interfaces

import com.example.myapplication.models.Jornalero
import com.example.myapplication.models.RegistroPesada
import retrofit2.Call
import retrofit2.http.*

interface RegistroPesadaAPI {
    @GET("/api/AR_dp08r")
    fun getWeightRegisters(): Call<List<RegistroPesada>>

    @GET("/api/AR_dp08r/{id}")
    fun getWeightRegister(@Path("id") id:Int, @Body register:RegistroPesada?): Call<RegistroPesada>

    @POST("/api/AR_dp08r/{id}")
    fun postWeightRegister(@Path("id") id:Int, @Body register:RegistroPesada?): Call<RegistroPesada>

    @PUT("/api/AR_dp08r/updateworkermodel/{id}")
    fun updateWeightRegister(@Path("id") id:Int, @Body register: RegistroPesada?): Call<RegistroPesada>

    @GET("/api/AR_dp08r/getvalidations/{valor}")
    fun getTypeTransact(@Path("valor") valor:String): Call<String>


}