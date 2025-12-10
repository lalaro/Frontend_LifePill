package com.escuelaing.edu.lifepill.network

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    val message: String?,
    val token: String?,
    @SerializedName("user")
    val userData: UserData?,
    val error: String? = null
) {
    // Propiedades calculadas para compatibilidad con el código existente
    val success: Boolean
        get() = error == null && token != null

    val role: String?
        get() = userData?.role

    val userId: String?
        get() = userData?.id
}

data class UserData(
    val id: String?,
    val email: String?,
    val username: String?,
    val role: String?
)