package com.example.myapplication.models

import java.time.LocalDateTime

data class RegistroPesada(
    val id: Int,
    val fecha: LocalDateTime,
    val cod_usuario: Jornalero,
    val cod_labor: Labor,
    val cantidad: Float,
    val foto: String,
    val fecha_s: LocalDateTime,
    val observaciones: String,
    val auditado: Boolean,
    val user_auditado: String,
    val fecha_auditado: LocalDateTime

)

