package com.escuelaing.edu.lifepill.network

data class LoginResponse(
    val success: Boolean,
    val token: String? = null,
    val message: String? = null
)