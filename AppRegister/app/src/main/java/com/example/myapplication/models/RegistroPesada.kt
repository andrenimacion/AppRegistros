package com.example.myapplication.models

import java.time.LocalDateTime

data class RegistroPesada(
    val id: Int,
    var fecha: LocalDateTime?,
    val cod_usuario: Jornalero?,
    var cod_labor: Labor?,
    var cantidad: Float,
    val foto: String,
    var fecha_s: LocalDateTime?,
    val observaciones: String,
    val auditado: Int,
    val user_auditado: String,
    val fecha_auditado: LocalDateTime?
){
    constructor(cod_usuario: Jornalero): this(0, null,cod_usuario,null,0.0f,"",null, "",0,"",null)
}

