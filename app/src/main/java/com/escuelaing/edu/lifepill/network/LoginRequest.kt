package com.escuelaing.edu.lifepill.network

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("correo") val correo: String,
    @SerializedName("contraseña") val contrasena: String
)