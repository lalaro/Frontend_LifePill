package com.escuelaing.edu.lifepill.repository

import com.escuelaing.edu.lifepill.network.NetworkModule
import com.escuelaing.edu.lifepill.model.RegistrationData

class AuthRepository {
    private val api = NetworkModule.authApi

    suspend fun register(request: RegistrationData): Result<Unit> {
        return try {
            val resp = api.register(request)
            if (resp.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("HTTP ${resp.code()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}