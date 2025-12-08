package com.escuelaing.edu.lifepill.ui.screens.loginScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.escuelaing.edu.lifepill.data.AuthRepository
import com.escuelaing.edu.lifepill.network.NetworkModule
import com.escuelaing.edu.lifepill.ui.screens.loginScreen.LoginScreens

@Composable
fun LoginScreenContainer(
    onForgotPasswordClick: () -> Unit,
    onLoginSuccess: (String) -> Unit,
    repo: AuthRepository = AuthRepository(NetworkModule.authApi)
) {
    val factory = LoginViewModelFactory(repo)
    val vm: LoginViewModel = viewModel(factory = factory)
    val authState = vm.authResult
    val emailState = remember { mutableStateOf("") }

    LaunchedEffect(authState) {
        val res = authState.value
        if (res != null && res.isSuccess) {
            onLoginSuccess(emailState.value)
        }
    }

    LoginScreens(
        onForgotPasswordClick = onForgotPasswordClick,
        onLogin = { email, password ->
            emailState.value = email
            vm.login(email, password)
        }
    )
}