package com.escuelaing.edu.lifepill.ui.screens.homeScreen.UserHomeScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.escuelaing.edu.lifepill.ui.screens.loginScreen.LifePillColors
import com.escuelaing.edu.lifepill.ui.theme.LifePillTheme
import kotlin.math.pow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    onNavigateToAssistant: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    var showEditObjectivesDialog by remember { mutableStateOf(false) }
    var userName by remember { mutableStateOf("Laura Rodríguez") }
    var userEmail by remember { mutableStateOf("laurarodriguez@email.com") }
    var age by remember { mutableStateOf("21") }
    var weight by remember { mutableStateOf(51.0) }
    var height by remember { mutableStateOf(151.0) }
    var dailyGoal by remember { mutableStateOf("2,000") }
    var objective by remember { mutableStateOf("Aumentar peso") }
    var activeDays by remember { mutableStateOf("12") }
    var registeredMeals by remember { mutableStateOf("48") }
    var aiConsultations by remember { mutableStateOf("23") }

    fun calculateIMC(): String {
        val heightInMeters = height / 100
        val imc = weight / (heightInMeters * heightInMeters)
        return String.format("%.1f", imc)
    }

    fun getIMCCategory(): String {
        val imc = calculateIMC().toDouble()
        return when {
            imc < 18.5 -> "Bajo peso"
            imc < 25.0 -> "Normal"
            imc < 30.0 -> "Sobrepeso"
            else -> "Obesidad"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mi Perfil",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = LifePillColors.OnSurface
                    )
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Configuración",
                            tint = LifePillColors.Primary
                        )
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
                    selected = false,
                    onClick = onNavigateToHome,
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Profile Header
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    color = LifePillColors.Surface,
                    border = BorderStroke(3.dp, LifePillColors.Primary)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Perfil",
                            modifier = Modifier.size(60.dp),
                            tint = LifePillColors.Primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = userName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = LifePillColors.OnSurface
                )

                Text(
                    text = userEmail,
                    fontSize = 14.sp,
                    color = LifePillColors.OnSurfaceVariant
                )
            }

            Divider(
                color = LifePillColors.OnSurfaceVariant.copy(alpha = 0.2f),
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )

            // Personal Information
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LifePillColors.Surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Información Personal",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = LifePillColors.OnSurface
                    )

                    InfoRow(label = "Edad", value = "$age años")
                    InfoRow(label = "Peso", value = "${weight.toInt()} kg")
                    InfoRow(label = "Estatura", value = "${height.toInt()} cm")
                    InfoRow(label = "IMC", value = "${calculateIMC()} (${getIMCCategory()})")
                }
            }

            // Objectives
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LifePillColors.Surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Objetivos",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = LifePillColors.OnSurface
                    )

                    InfoRow(label = "Meta diaria", value = "$dailyGoal cal")
                    InfoRow(label = "Objetivo", value = objective)

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = { showEditObjectivesDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = LifePillColors.Primary
                        ),
                        border = BorderStroke(2.dp, LifePillColors.Primary)
                    ) {
                        Text(
                            text = "Cambiar objetivos",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Statistics
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LifePillColors.Surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Estadísticas",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = LifePillColors.OnSurface
                    )

                    InfoRow(label = "Días activos", value = activeDays)
                    InfoRow(label = "Comidas registradas", value = registeredMeals)
                    InfoRow(label = "Consultas IA", value = aiConsultations)
                }
            }

            // Policies Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LifePillColors.Surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Políticas y Condiciones",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = LifePillColors.OnSurface
                    )

                    PrivacyPolicyLink(
                        title = "Política de Privacidad",
                        description = "Conoce cómo protegemos tus datos"
                    )

                    PrivacyPolicyLink(
                        title = "Términos de Servicio",
                        description = "Lee nuestros términos y condiciones"
                    )

                    PrivacyPolicyLink(
                        title = "Política de Cookies",
                        description = "Gestiona tus preferencias de cookies"
                    )
                }
            }

            // Logout Button
            Button(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LifePillColors.Primary,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Text(
                    text = "Cerrar Sesión",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        if (showEditObjectivesDialog) {
            EditObjectivesDialog(
                currentGoal = dailyGoal,
                currentObjective = objective,
                onDismiss = { showEditObjectivesDialog = false },
                onSave = { newGoal, newObjective ->
                    dailyGoal = newGoal
                    objective = newObjective
                    showEditObjectivesDialog = false
                }
            )
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = LifePillColors.OnSurfaceVariant
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = LifePillColors.OnSurface
        )
    }
}

@Composable
private fun PrivacyPolicyLink(
    title: String,
    description: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = LifePillColors.OnSurface
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = LifePillColors.OnSurfaceVariant
            )
        }
    }
}

@Composable
private fun EditObjectivesDialog(
    currentGoal: String,
    currentObjective: String,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var newGoal by remember { mutableStateOf(currentGoal) }
    var newObjective by remember { mutableStateOf(currentObjective) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Cambiar Objetivos",
                fontWeight = FontWeight.Bold,
                color = LifePillColors.OnSurface
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = newGoal,
                    onValueChange = { newGoal = it },
                    label = { Text("Meta diaria (calorías)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = LifePillColors.Surface,
                        unfocusedContainerColor = LifePillColors.Surface,
                        focusedBorderColor = LifePillColors.Primary,
                        unfocusedBorderColor = LifePillColors.OnSurfaceVariant,
                        focusedTextColor = LifePillColors.OnSurface,
                        unfocusedTextColor = LifePillColors.OnSurface
                    )
                )

                OutlinedTextField(
                    value = newObjective,
                    onValueChange = { newObjective = it },
                    label = { Text("Objetivo") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = LifePillColors.Surface,
                        unfocusedContainerColor = LifePillColors.Surface,
                        focusedBorderColor = LifePillColors.Primary,
                        unfocusedBorderColor = LifePillColors.OnSurfaceVariant,
                        focusedTextColor = LifePillColors.OnSurface,
                        unfocusedTextColor = LifePillColors.OnSurface
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(newGoal, newObjective) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = LifePillColors.Primary
                )
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = LifePillColors.OnSurfaceVariant
                )
            ) {
                Text("Cancelar")
            }
        },
        containerColor = LifePillColors.Background,
        shape = RoundedCornerShape(12.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun UserProfileScreenPreview() {
    LifePillTheme {
        UserProfileScreen()
    }
}