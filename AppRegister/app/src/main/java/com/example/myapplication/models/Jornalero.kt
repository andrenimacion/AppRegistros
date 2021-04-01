package com.example.myapplication.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Jornalero(
        @SerializedName("codjor")
        @Expose
        val id: String,
        @SerializedName("cedula")
        @Expose
        var cedula: String,
        @SerializedName("nombre")
        @Expose
        var nombre: String,
        @SerializedName("apellido")
        @Expose
        var apellido: String,
        @SerializedName("cond_jor")
        @Expose
        var cond_jor: String
        ):Parcelable{
                @SerializedName("cod_usuario")
                var cod_usuario:String = ""
                        get() {return id}

}