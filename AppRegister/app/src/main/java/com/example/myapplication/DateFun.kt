package com.example.myapplication

import android.util.Log
import androidx.compose.ui.input.key.Key.Companion.I
import java.text.SimpleDateFormat
import java.util.*

class DateFun {


    fun date(): String {

        val fecha = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = fecha.format(Date())

        //Log.i("TAG", currentDate)

        return currentDate;

    }

}