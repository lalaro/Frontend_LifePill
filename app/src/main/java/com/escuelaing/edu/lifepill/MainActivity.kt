package com.escuelaing.edu.lifepill

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.escuelaing.edu.lifepill.ui.theme.LifePillTheme
import com.escuelaing.edu.lifepill.ui.screens.OnBoardingScreen
import com.escuelaing.edu.lifepill.ui.screens.loginScreen.LoginScreens
import com.escuelaing.edu.lifepill.ui.screens.loginScreen.forgotPasswordScreen.ForgotPasswordScreen
import com.escuelaing.edu.lifepill.ui.screens.loginScreen.forgotPasswordScreen.VerificationScreen
import com.escuelaing.edu.lifepill.ui.screens.loginScreen.forgotPasswordScreen.ResetPasswordScreen
import com.escuelaing.edu.lifepill.ui.screens.loginScreen.forgotPasswordScreen.PasswordSuccessScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LifePillTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "onboarding") {
        composable("onboarding") {
            OnBoardingScreen(
                onSkip = {
                    navController.navigate("login") {
                        popUpTo("onboarding") {
                            inclusive = true
                        }
                    }
                },
                onNext = {
                }
            )
        }
        composable("login") {
            LoginScreens(
                onForgotPasswordClick = {
                    navController.navigate("forgot_password")
                }
            )

        }

        composable("forgot_password") {
            ForgotPasswordScreen(
                onBack = { navController.popBackStack() },
                onVerify = { email ->
                    // 👉 Aquí navegamos a VerificationScreen
                    navController.navigate("verification/$email")
                }
            )
        }
        composable("verification/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            VerificationScreen(
                email = email,
                onBack = { navController.popBackStack() },
                onVerify = {
                    navController.navigate("reset_password")
                }
            )
        }


        composable("reset_password") {
            ResetPasswordScreen(
                onBack = { navController.popBackStack() },
                onPasswordReset = {
                    navController.navigate("password_success") {
                        popUpTo("reset_password") { inclusive = true }
                    }
                }
            )
        }
        composable("password_success") {
            PasswordSuccessScreen(
                onBack = { navController.popBackStack() },
                onContinue = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
    }



}