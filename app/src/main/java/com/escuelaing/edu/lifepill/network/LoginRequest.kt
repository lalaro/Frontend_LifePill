package com.escuelaing.edu.lifepill.network

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email") val correo: String,
    @SerializedName("password") val contrasena: String
)