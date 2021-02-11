package com.example.myapplication.models

data class Labor(
    val codlab: String,
    val nomlab: String,
    val can_hor:String,
    val unilab: String,
    val prelab: String,
    val turno_bio:String

){
    override fun toString(): String {
        return "$codlab-$nomlab-$can_hor"
    }
}
