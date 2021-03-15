package com.example.myapplication.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.interfaces.LaboresAPI
import com.example.myapplication.models.Jornalero
import com.example.myapplication.models.Labor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileViewModel: ViewModel() {
    init {
        var user = MutableLiveData<Jornalero>()
    }

}