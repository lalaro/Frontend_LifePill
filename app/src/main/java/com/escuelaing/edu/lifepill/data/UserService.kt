package com.escuelaing.edu.lifepill.data

interface UserService {
    suspend fun login(email: String, password: String): LoginResult
}

sealed class LoginResult {
    data class Success(val role: String): LoginResult()
    data class Failure(val message: String): LoginResult()
}

// Ejemplo para pruebas
class FakeUserService: UserService {
    override suspend fun login(email: String, password: String): LoginResult {
        kotlinx.coroutines.delay(700)
        return if (email == "admin@gmail.com" && password == "admin123") {
            LoginResult.Success("admin")
        } else if (email.contains("@") && password.length >= 6) {
            LoginResult.Success("usuario")
        } else {
            LoginResult.Failure("Credenciales inválidas")
        }
    }
}