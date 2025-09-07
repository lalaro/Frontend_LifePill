package com.escuelaing.edu.lifepill.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.escuelaing.edu.lifepill.R

val BlueKing = Color(0xFF007bff)
val DarkBackground = Color(0xFF1E1E1E)
val LightGrayText = Color(0xFFB0B0B0)

@Composable
fun OnBoardingScreen(
    onSkip: () -> Unit,
    onNext: () -> Unit
) {
    var currentPage by remember {
        mutableStateOf(0)
    }

    val pages = listOf(
        OnboardingPage(
            title = "Bienvenido a LifePill",
            subtitle = "Tu aliado para una vida saludable",
            description = "Facilitamos el bienestar a través de un\nacompañamiento integral en tu alimentación.",
            imageResId = R.drawable.screen1
        ),
        OnboardingPage(
            title = "Asistencia con IA Generativa",
            subtitle = "Tu nutricionista virtual",
            description = "Consulta y obtén sugerencias de alimentos\nsaludables en cualquier momento.",
            imageResId = R.drawable.screen2
        ),
        OnboardingPage(
            title = "Registro de Comidas",
            subtitle = "Lleva un control diario",
            description = "Registra y organiza tus comidas para\nconocer tus hábitos alimenticios.",
            imageResId = R.drawable.screen3
        ),
        OnboardingPage(
            title = "Recomendaciones Personalizadas",
            subtitle = "Planes a tu medida",
            description = "Recibe consejos basados en tu historial\ny objetivos de salud.",
            imageResId = R.drawable.screen4
        ),
        OnboardingPage(
            title = "Restaurantes y Mercados",
            subtitle = "Opciones saludables cerca de ti",
            description = "Te recomendamos restaurantes y supermercados en\ntu zona para seguir con tu alimentación.",
            imageResId = R.drawable.screen5
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(24.dp)
    ) {
        val page = pages[currentPage]

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = onSkip,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = BlueKing,
                            shape = RoundedCornerShape(50.dp)
                        )
                        .background(Color.Transparent, RoundedCornerShape(50.dp))
                        .padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = "Saltar",
                        color = BlueKing,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = page.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = page.subtitle,
                    color = BlueKing,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = page.description,
                    color = LightGrayText,
                    textAlign = TextAlign.Center
                )

                if (page.imageResId != null) {
                    Spacer(modifier = Modifier.height(32.dp))
                    Image(
                        painter = painterResource(id = page.imageResId),
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(pages.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(if (index == currentPage) 12.dp else 8.dp)
                            .background(
                                if (index == currentPage) BlueKing else LightGrayText,
                                CircleShape
                            )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (currentPage > 0) {
                    OutlinedButton(
                        onClick = { currentPage-- },
                        border = BorderStroke(1.dp, BlueKing),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = BlueKing
                        ),
                        modifier = Modifier.height(50.dp)
                    ) {
                        Text("Anterior")
                    }
                } else {
                    Spacer(modifier = Modifier.width(50.dp))
                }

                val isLastPage = currentPage == pages.lastIndex
                val buttonText = if (isLastPage) "Comenzar" else "Siguiente"

                Button(
                    onClick = {
                        if (!isLastPage) {
                            currentPage++
                        } else {
                            onSkip()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BlueKing,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.height(50.dp)
                ) {
                    Text(buttonText)
                }
            }
        }
    }
}

data class OnboardingPage(
    val title: String,
    val subtitle: String,
    val description: String,
    @DrawableRes val imageResId: Int? = null
)