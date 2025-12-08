
package com.escuelaing.edu.lifepill.network

import com.escuelaing.edu.lifepill.model.RegistrationData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/api/auth/register")
    suspend fun register(@Body request: RegistrationData): Response<Unit>

    @POST("/api/auth/google")
    suspend fun googleAuth(@Body request: GoogleAuthRequest): Response<LoginResponse>
}

data class GoogleAuthRequest(
    val idToken: String
)