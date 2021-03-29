package com.example.myapplication.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class RegisterPost(
    @SerializedName("cod_usuario")
    @Expose
    val cod_usuario:String,
    @SerializedName("cantidad")
    @Expose
    val cantidad: Float,
    @SerializedName("cod_labor")
    @Expose
    val cod_labor: String,
    @SerializedName("observaciones")
    @Expose
    val observaciones:String
    ) {
    constructor(cod_usuario:String) : this(cod_usuario,0.0f,"","")
}