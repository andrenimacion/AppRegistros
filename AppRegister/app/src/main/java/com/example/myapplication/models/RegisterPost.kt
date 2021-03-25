package com.example.myapplication.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class RegisterPost(
    @SerializedName("cod_usuario")
    @Expose
    val cod_usuario:String) {
}