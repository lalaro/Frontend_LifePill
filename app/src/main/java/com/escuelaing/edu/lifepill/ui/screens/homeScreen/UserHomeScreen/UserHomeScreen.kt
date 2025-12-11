package com.escuelaing.edu.lifepill.ui.screens.homeScreen.UserHomeScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.escuelaing.edu.lifepill.auth.FoodViewModel
import com.escuelaing.edu.lifepill.ui.screens.loginScreen.LifePillColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserHomeScreen(
    userName: String = "Laura",
    token: String = "",
    foodViewModel: FoodViewModel = viewModel(),
    onLogout: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    onNavigateToAssistant: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    // Observar la lista de comidas
    val foodsList = foodViewModel.foodsList.observeAsState(emptyList())
    val error = foodViewModel.error.observeAsState()

    // Calcular calorías totales del día
    val totalCalories = remember(foodsList.value) {
        foodsList.value.sumOf { it.calorias }
    }

    // Meta de calorías diarias (puedes hacerlo configurable por usuario)
    val dailyGoal = 2000
    val progress = if (dailyGoal > 0) (totalCalories.toFloat() / dailyGoal).coerceIn(0f, 1f) else 0f

    // Cargar comidas al inicio
    LaunchedEffect(token) {
        if (token.isNotBlank()) {
            foodViewModel.getFoods(token)
        }
    }

    // Mostrar error si existe
    error.value?.let { errorMsg ->
        LaunchedEffect(errorMsg) {
            // Podrías mostrar un Snackbar aquí si lo deseas
            foodViewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Hola, $userName",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = LifePillColors.OnSurface,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notificaciones",
                                tint = Color(0xFFFFA726)
                            )
                        }
                        IconButton(onClick = onLogout) {
                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = "Cerrar sesión",
                                tint = LifePillColors.Primary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LifePillColors.Background,
                    titleContentColor = LifePillColors.OnSurface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = LifePillColors.Surface,
                contentColor = LifePillColors.OnSurface
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") },
                    selected = true,
                    onClick = { },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = LifePillColors.Primary,
                        selectedTextColor = LifePillColors.Primary,
                        unselectedIconColor = LifePillColors.OnSurfaceVariant,
                        unselectedTextColor = LifePillColors.OnSurfaceVariant,
                        indicatorColor = LifePillColors.Primary.copy(alpha = 0.2f)
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.DateRange, contentDescription = "Registro") },
                    label = { Text("Registro") },
                    selected = false,
                    onClick = onNavigateToRegister,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = LifePillColors.Primary,
                        selectedTextColor = LifePillColors.Primary,
                        unselectedIconColor = LifePillColors.OnSurfaceVariant,
                        unselectedTextColor = LifePillColors.OnSurfaceVariant,
                        indicatorColor = LifePillColors.Primary.copy(alpha = 0.2f)
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Star, contentDescription = "IA") },
                    label = { Text("IA") },
                    selected = false,
                    onClick = onNavigateToAssistant,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = LifePillColors.Primary,
                        selectedTextColor = LifePillColors.Primary,
                        unselectedIconColor = LifePillColors.OnSurfaceVariant,
                        unselectedTextColor = LifePillColors.OnSurfaceVariant,
                        indicatorColor = LifePillColors.Primary.copy(alpha = 0.2f)
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                    label = { Text("Perfil") },
                    selected = false,
                    onClick = onNavigateToProfile,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = LifePillColors.Primary,
                        selectedTextColor = LifePillColors.Primary,
                        unselectedIconColor = LifePillColors.OnSurfaceVariant,
                        unselectedTextColor = LifePillColors.OnSurfaceVariant,
                        indicatorColor = LifePillColors.Primary.copy(alpha = 0.2f)
                    )
                )
            }
        },
        containerColor = LifePillColors.Background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Card de Resumen del Día - ACTUALIZADO CON CALORÍAS DINÁMICAS
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LifePillColors.Surface
                ),
                border = BorderStroke(2.dp, LifePillColors.Primary)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Resumen del Día",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = LifePillColors.OnSurface
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Mostrar calorías dinámicas
                    Text(
                        text = "Calorías: ${totalCalories.toString().replace(Regex("(\\d)(?=(\\d{3})+$)"), "$1,")} / ${dailyGoal.toString().replace(Regex("(\\d)(?=(\\d{3})+$)"), "$1,")}",
                        fontSize = 16.sp,
                        color = LifePillColors.OnSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Barra de progreso dinámica
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = when {
                            progress < 0.5f -> Color(0xFF4CAF50) // Verde
                            progress < 0.9f -> Color(0xFFFFA726) // Naranja
                            else -> Color(0xFFEF5350) // Rojo si excede
                        },
                        trackColor = LifePillColors.OnSurfaceVariant.copy(alpha = 0.3f)
                    )

                    // Mostrar mensaje según el progreso
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = when {
                            totalCalories == 0 -> "Aún no has registrado comidas hoy"
                            progress < 0.5f -> "Vas por buen camino 💪"
                            progress < 0.9f -> "¡Casi alcanzas tu meta! 🎯"
                            progress < 1.0f -> "¡Muy cerca de tu objetivo! 🔥"
                            else -> "¡Meta alcanzada! ✅"
                        },
                        fontSize = 12.sp,
                        color = LifePillColors.OnSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Card de Sugerencia IA
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LifePillColors.Surface
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "💡",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                    Column {
                        Text(
                            text = "Sugerencia IA:",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = LifePillColors.OnSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = when {
                                totalCalories == 0 -> "Comienza tu día registrando tu primera comida para obtener recomendaciones personalizadas."
                                progress < 0.5f -> "Basado en tus objetivos, te recomendamos incluir más proteínas y carbohidratos complejos en tus próximas comidas."
                                progress < 0.9f -> "Estás haciendo un gran trabajo. Considera agregar una porción de vegetales y frutas para completar tu día."
                                else -> "¡Excelente! Has alcanzado tu meta. Mantén una buena hidratación y actividad física."
                            },
                            fontSize = 14.sp,
                            color = LifePillColors.OnSurfaceVariant,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}