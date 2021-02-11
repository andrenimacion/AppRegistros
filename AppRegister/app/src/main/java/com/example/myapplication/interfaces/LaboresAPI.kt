package com.example.myapplication.interfaces

import com.example.myapplication.models.Labor
import retrofit2.Call
import retrofit2.http.GET

interface LaboresAPI {
    @GET("/api/AR_dp08r/getworkers/c/ca")
    fun getLabores():Call<List<Labor>>
}