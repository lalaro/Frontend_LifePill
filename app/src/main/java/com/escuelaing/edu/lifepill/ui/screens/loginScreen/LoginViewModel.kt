package com.escuelaing.edu.lifepill.ui.screens.loginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.escuelaing.edu.lifepill.data.AuthRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(private val repo: AuthRepository) : ViewModel() {
    private val _authResult = MutableStateFlow<Result<String>?>(null)
    val authResult: StateFlow<Result<String>?> = _authResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authResult.value = repo.login(email, password)
        }
    }
}