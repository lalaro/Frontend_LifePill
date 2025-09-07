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
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import com.escuelaing.edu.lifepill.ui.theme.LifePillTheme
import com.escuelaing.edu.lifepill.ui.screens.OnBoardingScreen

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
                    // Correct implementation of navigate with popUpTo and inclusive
                    navController.navigate("login") {
                        popUpTo("onboarding") {
                            inclusive = true
                        }
                    }
                },
                onNext = {
                    // The onNext lambda in OnBoardingScreen handles internal page changes
                    // within the same composable, so no navigation is needed here.
                }
            )
        }
        composable("login") {
            // Your LoginScreen composable will go here
        }
    }
}