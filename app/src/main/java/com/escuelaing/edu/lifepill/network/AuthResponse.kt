package com.escuelaing.edu.lifepill.network

data class AuthResponse(
    val success: Boolean,
    val token: String?,
    val role: String?,
    val userId: String?,
    val message: String?
)