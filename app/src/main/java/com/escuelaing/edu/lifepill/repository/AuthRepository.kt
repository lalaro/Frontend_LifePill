package com.escuelaing.edu.lifepill.repository



import com.escuelaing.edu.lifepill.network.AuthApi
import com.escuelaing.edu.lifepill.network.LoginRequest
import com.escuelaing.edu.lifepill.network.RegisterRequest

class AuthRepository(private val api: AuthApi) {
    suspend fun register(req: RegisterRequest) = api.register(req)
    suspend fun login(req: LoginRequest) = api.login(req)
}