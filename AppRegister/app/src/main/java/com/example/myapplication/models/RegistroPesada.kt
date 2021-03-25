package com.example.myapplication.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.time.LocalDateTime


data class RegistroPesada(
    val id: Int,
    var fecha: LocalDateTime?,
    //@SerializedName("Jornalero")
    val cod_usuario: Jornalero?,
    var cod_labor: Labor?,
    var cantidad: Float,
    val foto: String,
    var fecha_s: LocalDateTime?,
    val observaciones: String,
    val auditado: Int,
    val user_auditado: String,
    val fecha_auditado: LocalDateTime?,
    //@SerializedName("cod_usuario")
    //@Expose
    val cod_user: String = cod_usuario!!.cond_jor
){
    constructor(cod_usuario: Jornalero): this(0, null,cod_usuario,null,0.0f,"",null, "",0,"",null)
}

