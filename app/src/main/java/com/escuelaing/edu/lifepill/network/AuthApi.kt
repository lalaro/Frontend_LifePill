package com.escuelaing.edu.lifepill.network

import retrofit2.Response
import retrofit2.http.*

interface AuthApi {
    @POST("api/auth/register")
    suspend fun register(@Body body: RegisterRequest): Response<AuthResponse>

    @POST("api/auth/login")
    suspend fun login(@Body body: LoginRequest): Response<AuthResponse>
}