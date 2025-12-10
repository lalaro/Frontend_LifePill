package com.escuelaing.edu.lifepill

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.escuelaing.edu.lifepill.auth.AuthViewModel
import com.escuelaing.edu.lifepill.ui.theme.LifePillTheme
import com.escuelaing.edu.lifepill.ui.screens.OnBoardingScreen
import com.escuelaing.edu.lifepill.ui.screens.loginScreen.LoginScreens
import com.escuelaing.edu.lifepill.ui.screens.loginScreen.forgotPasswordScreen.ForgotPasswordScreen
import com.escuelaing.edu.lifepill.ui.screens.loginScreen.forgotPasswordScreen.VerificationScreen
import com.escuelaing.edu.lifepill.ui.screens.loginScreen.forgotPasswordScreen.ResetPasswordScreen
import com.escuelaing.edu.lifepill.ui.screens.loginScreen.forgotPasswordScreen.PasswordSuccessScreen
import com.escuelaing.edu.lifepill.ui.screens.homeScreen.AdminHomeScreen
import com.escuelaing.edu.lifepill.ui.screens.homeScreen.UserHomeScreen.UserHomeScreen
import com.escuelaing.edu.lifepill.ui.screens.homeScreen.UserHomeScreen.FoodRegisterScreen
import com.escuelaing.edu.lifepill.ui.screens.homeScreen.UserHomeScreen.AIAssistantScreen
import com.escuelaing.edu.lifepill.ui.screens.homeScreen.UserHomeScreen.UserProfileScreen


class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LifePillTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(authViewModel = authViewModel)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(authViewModel: AuthViewModel) {
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
                authViewModel = authViewModel,
                onForgotPasswordClick = {
                    navController.navigate("forgot_password")
                },
                onLoginSuccess = { role ->
                    if (role == "admin") {
                        navController.navigate("admin_home") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        navController.navigate("user_home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
            )
        }

        composable("admin_home") {
            AdminHomeScreen(
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("admin_home") { inclusive = true }
                    }
                }
            )
        }

        composable("user_home") {
            UserHomeScreen(
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("user_home") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("food_register")
                },
                onNavigateToAssistant = {
                    navController.navigate("ai_assistant")
                },
                onNavigateToProfile = {
                    navController.navigate("user_profile")
                }
            )
        }

        composable("food_register") {
            FoodRegisterScreen(
                onBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate("user_home") {
                        popUpTo("food_register") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                },
                onNavigateToAssistant = {
                    navController.navigate("ai_assistant")
                },
                onNavigateToProfile = {
                    navController.navigate("user_profile")
                },
                onSaveFood = { foods ->
                    println("Foods saved: $foods")
                }
            )
        }

        composable("ai_assistant") {
            AIAssistantScreen(
                onBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate("user_home") {
                        popUpTo("ai_assistant") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("food_register")
                },
                onNavigateToAssistant = {
                    // Already on assistant screen
                },
                onNavigateToProfile = {
                    navController.navigate("user_profile")
                }
            )
        }

        composable("user_profile") {
            UserProfileScreen(
                onNavigateToHome = {
                    navController.navigate("user_home") {
                        popUpTo("user_profile") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("food_register")
                },
                onNavigateToAssistant = {
                    navController.navigate("ai_assistant")
                },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("user_profile") { inclusive = true }
                    }
                }
            )
        }

        composable("forgot_password") {
            ForgotPasswordScreen(
                onBack = { navController.popBackStack() },
                onVerify = { email ->
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