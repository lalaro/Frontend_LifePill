package com.escuelaing.edu.lifepill.auth

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.escuelaing.edu.lifepill.network.AuthResponse
import com.escuelaing.edu.lifepill.network.LoginRequest
import com.escuelaing.edu.lifepill.network.RegisterRequest
import com.escuelaing.edu.lifepill.network.RetrofitClient
import com.escuelaing.edu.lifepill.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = AuthRepository(RetrofitClient.authApi)
    val authResult = MutableLiveData<AuthResponse?>()
    val error = MutableLiveData<String?>()

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    companion object {
        private const val TAG = "AuthViewModel"
    }

    fun login(correo: String, contraseña: String) = viewModelScope.launch {
        try {
            Log.d(TAG, "🔵 Iniciando login para: $correo")

            val response = repo.login(LoginRequest(correo = correo, contrasena = contraseña))

            Log.d(TAG, "📡 Response code: ${response.code()}")
            Log.d(TAG, "📡 Response successful: ${response.isSuccessful}")

            if (response.isSuccessful) {
                val body = response.body()
                Log.d(TAG, "📡 Response body: $body")
                Log.d(TAG, "✅ Login exitoso")
                Log.d(TAG, "✅ Success calculado: ${body?.success}")
                Log.d(TAG, "✅ Role: ${body?.role}")
                Log.d(TAG, "✅ UserId: ${body?.userId}")
                Log.d(TAG, "✅ Message: ${body?.message}")
                Log.d(TAG, "✅ Token: ${body?.token?.take(20)}...")

                // 🔑 GUARDAR EL TOKEN AUTOMÁTICAMENTE
                if (body?.token != null) {
                    Log.d(TAG, "💾 Guardando token en LiveData...")
                    _token.postValue(body.token)
                } else {
                    Log.e(TAG, "⚠️ Token es NULL en la respuesta")
                }

                authResult.postValue(body)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMsg = if (errorBody != null) {
                    "Error: $errorBody"
                } else {
                    "Error ${response.code()}: ${response.message()}"
                }
                Log.e(TAG, "❌ Login fallido: $errorMsg")
                error.postValue(errorMsg)
            }
        } catch (e: Exception) {
            Log.e(TAG, "💥 Excepción en login: ${e.message}", e)
            error.postValue(e.message)
        }
    }

    fun register(request: RegisterRequest) = viewModelScope.launch {
        try {
            Log.d(TAG, "🔵 Iniciando registro para: ${request.email}")

            val response = repo.register(request)

            Log.d(TAG, "📡 Response code: ${response.code()}")
            Log.d(TAG, "📡 Response body: ${response.body()}")

            if (response.isSuccessful) {
                val body = response.body()
                Log.d(TAG, "✅ Registro exitoso")
                Log.d(TAG, "✅ Success: ${body?.success}")
                Log.d(TAG, "✅ Message: ${body?.message}")

                // 🔑 GUARDAR EL TOKEN SI VIENE EN EL REGISTRO
                if (body?.token != null) {
                    Log.d(TAG, "💾 Guardando token en LiveData después del registro...")
                    _token.postValue(body.token)
                }

                authResult.postValue(body)
            } else {
                val errorMsg = "Error ${response.code()}: ${response.message()}"
                Log.e(TAG, "❌ Registro fallido: $errorMsg")
                error.postValue(errorMsg)
            }
        } catch (e: Exception) {
            Log.e(TAG, "💥 Excepción en registro: ${e.message}", e)
            error.postValue(e.message)
        }
    }

    // Esta función ya no es necesaria llamarla manualmente
    fun onLoginSuccess(tokenValue: String) {
        _token.value = tokenValue
    }
}