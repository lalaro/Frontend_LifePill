package com.escuelaing.edu.lifepill.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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

    fun login(correo: String, contraseña: String) = viewModelScope.launch {
        try {
            val response = repo.login(LoginRequest(correo = correo, contrasena = contraseña))
            if (response.isSuccessful) {
                authResult.postValue(response.body())
            } else {
                error.postValue("Error ${response.code()}")
            }
        } catch (e: Exception) {
            error.postValue(e.message)
        }
    }

    fun register(request: RegisterRequest) = viewModelScope.launch {
        try {
            val response = repo.register(request)
            if (response.isSuccessful) {
                authResult.postValue(response.body())
            } else {
                error.postValue("Error ${response.code()}")
            }
        } catch (e: Exception) {
            error.postValue(e.message)
        }
    }
}