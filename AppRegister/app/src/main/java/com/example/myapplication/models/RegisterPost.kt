package com.example.myapplication.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class RegisterPost(
    @SerializedName("cod_usuario")
    @Expose
    var cod_usuario:String,
    @SerializedName("cantidad")
    @Expose
    var cantidad: Float,
    @SerializedName("cod_labor")
    @Expose
    var cod_labor: String,
    @SerializedName("observaciones")
    @Expose
    var observaciones:String
    ) {
    constructor(cod_usuario:String) : this(cod_usuario,0.0f,"","")
}