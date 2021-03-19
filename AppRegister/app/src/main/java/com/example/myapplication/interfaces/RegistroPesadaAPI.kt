package com.example.myapplication.interfaces

import com.example.myapplication.models.Jornalero
import com.example.myapplication.models.RegistroPesada
import com.example.myapplication.models.ResponseText
import retrofit2.Call
import retrofit2.http.*

interface RegistroPesadaAPI {
    @GET("/api/AR_dp08r")
    fun getWeightRegisters(): Call<List<RegistroPesada>>

    @GET("/api/AR_dp08r/getworkers/{value}/ca")
    fun getWeightRegister(@Path("value") value:String): Call<RegistroPesada>

    /*@POST("/api/AR_dp08r/{id}")
    fun postWeightRegister(@Path("id") id:Int, @Body register:RegistroPesada?): Call<RegistroPesada>

    @PUT("/api/AR_dp08r/updateworkermodel/{id}")
    fun updateWeightRegister(@Path("id") id:Int, @Body register: RegistroPesada?): Call<RegistroPesada>*/

    @POST("/api/AR_dp08r/UpWorkers/{value}")
    suspend fun postWeightRegister(@Path("value") value:String, @Body register:RegistroPesada): String

    @GET("/api/AR_dp08r/getworkersCODJOR/{valor}")
    fun getTypeTransact(@Path("valor") valor:String): Call<List<ResponseText>>


}