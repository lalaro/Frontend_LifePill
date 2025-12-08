package com.escuelaing.edu.lifepill.data

import com.escuelaing.edu.lifepill.model.RegistrationData
import com.escuelaing.edu.lifepill.network.AuthApi
import com.escuelaing.edu.lifepill.network.LoginRequest
import com.escuelaing.edu.lifepill.network.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(private val api: AuthApi) {

    suspend fun register(request: RegistrationData): Result<Unit> {
        return try {
            val resp = api.register(request)
            if (resp.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("HTTP ${resp.code()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun login(email: String, password: String): Result<String> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.success && !body.token.isNullOrEmpty()) {
                        Result.success(body.token)
                    } else {
                        Result.failure(Exception(body?.message ?: "Credenciales inválidas"))
                    }
                } else {
                    Result.failure(Exception("Error de red: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}