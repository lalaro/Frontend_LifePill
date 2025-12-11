package com.escuelaing.edu.lifepill.data

import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val role: String)

interface Api {
    @POST("auth/login")
    suspend fun login(@Body req: LoginRequest): LoginResponse
}

class RetrofitUserService(private val api: Api): UserService {
    override suspend fun login(email: String, password: String): LoginResult {
        return try {
            val resp = api.login(LoginRequest(email, password))
            LoginResult.Success(resp.role)
        } catch (e: HttpException) {
            LoginResult.Failure("Error de servidor: ${e.code()}")
        } catch (e: Exception) {
            LoginResult.Failure("Error de red")
        }
    }

    companion object {
        fun create(retrofit: Retrofit): RetrofitUserService {
            return RetrofitUserService(retrofit.create(Api::class.java))
        }
    }
}