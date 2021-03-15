package com.example.myapplication.interfaces

import com.example.myapplication.BASE_URL
import com.example.myapplication.models.Labor
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private val gson = GsonBuilder()
    .setLenient()
    .create()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl(BASE_URL)
    .build()

interface LaboresAPIService {
    @GET("/api/AR_dp08r/getworkers/c/ca")
    suspend fun getLabores():List<Labor>

    @GET("/api/AR_dp08r/getworkers/c/ca")
    fun getLaboresSynchro():Call<List<Labor>>
}
object LaboresAPI{
    val retrofitService:LaboresAPIService by lazy { retrofit.create(LaboresAPIService::class.java) }
}