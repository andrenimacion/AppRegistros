package com.example.myapplication.interfaces

import com.example.myapplication.models.Jornalero
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface JornalerosAPI {

    @GET("/api/jornaleros")
    fun getJornaleros():Call<List<Jornalero>>

    @GET("/api/jornaleros/{id}")
    fun getJornaleros(@Path("id") id:Int):Call<Jornalero>

    @POST("/api/AR_dp08r/VerificatorPersonal")
    fun postJornaleros(@Body jornalero:Jornalero?):Call<String>
}