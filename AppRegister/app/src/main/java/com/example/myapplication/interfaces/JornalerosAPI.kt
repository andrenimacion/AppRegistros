package com.example.myapplication.interfaces

import com.example.myapplication.models.Jornalero
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

    @POST("/api/jornaleros/{id}")
    fun postJornaleros(@Path("id") id:Int, @Body jornalero:Jornalero?):Call<Jornalero>
}