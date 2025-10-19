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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserHomeScreen(
    userName: String = "Laura",
    onLogout: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    onNavigateToAssistant: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onTakePhoto: (String) -> Unit = {},
    onAddText: (String, String) -> Unit = { _, _ -> }
) {
    val scrollState = rememberScrollState()
    var showAddMealDialog by remember { mutableStateOf(false) }
    var selectedMeal by remember { mutableStateOf("") }
    var desayuno by remember { mutableStateOf("Avena con frutas") }
    var almuerzo by remember { mutableStateOf<String?>(null) }
    var cena by remember { mutableStateOf<String?>(null) }

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
                    Text(
                        text = "Calorías: 1,200 / 2,000",
                        fontSize = 16.sp,
                        color = LifePillColors.OnSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { 0.6f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = LifePillColors.Primary,
                        trackColor = LifePillColors.OnSurfaceVariant.copy(alpha = 0.3f)
                    )
                }
            }
            MealCard(
                mealName = "Desayuno",
                foodItem = desayuno,
                onAddClick = {
                    selectedMeal = "Desayuno"
                    showAddMealDialog = true
                }
            )
            MealCard(
                mealName = "Almuerzo",
                foodItem = almuerzo,
                onAddClick = {
                    selectedMeal = "Almuerzo"
                    showAddMealDialog = true
                }
            )
            MealCard(
                mealName = "Cena",
                foodItem = cena,
                onAddClick = {
                    selectedMeal = "Cena"
                    showAddMealDialog = true
                }
            )
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
                            text = "Basado en tu desayuno, te recomiendo una ensalada con pollo para el almuerzo.",
                            fontSize = 14.sp,
                            color = LifePillColors.OnSurfaceVariant,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
        if (showAddMealDialog) {
            AddMealDialog(
                mealName = selectedMeal,
                onDismiss = { showAddMealDialog = false },
                onTakePhoto = {
                    showAddMealDialog = false
                    onTakePhoto(selectedMeal)
                },
                onAddText = { text ->
                    when (selectedMeal) {
                        "Desayuno" -> desayuno = text
                        "Almuerzo" -> almuerzo = text
                        "Cena" -> cena = text
                    }
                    showAddMealDialog = false
                    onAddText(selectedMeal, text)
                }
            )
        }
    }
}

@Composable
private fun MealCard(
    mealName: String,
    foodItem: String?,
    onAddClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = LifePillColors.Surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = mealName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = LifePillColors.OnSurface
            )
            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LifePillColors.Background
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = foodItem ?: "No registrado",
                        fontSize = 14.sp,
                        color = LifePillColors.OnSurface
                    )
                    TextButton(onClick = onAddClick) {
                        Text(
                            text = "+ Agregar",
                            color = LifePillColors.Primary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AddMealDialog(
    mealName: String,
    onDismiss: () -> Unit,
    onTakePhoto: () -> Unit,
    onAddText: (String) -> Unit
) {
    var textInput by remember { mutableStateOf("") }
    var showTextInput by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Agregar $mealName",
                fontWeight = FontWeight.Bold,
                color = LifePillColors.OnSurface
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (!showTextInput) {
                    Text(
                        text = "¿Cómo quieres agregar tu comida?",
                        fontSize = 14.sp,
                        color = LifePillColors.OnSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = onTakePhoto,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = LifePillColors.Primary
                        ),
                        border = BorderStroke(2.dp, LifePillColors.Primary)
                    ) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Tomar Foto",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    OutlinedButton(
                        onClick = { showTextInput = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = LifePillColors.Primary
                        ),
                        border = BorderStroke(2.dp, LifePillColors.Primary)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Texto",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Escribir",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                } else {
                    Text(
                        text = "Escribe qué comiste:",
                        fontSize = 14.sp,
                        color = LifePillColors.OnSurfaceVariant
                    )

                    OutlinedTextField(
                        value = textInput,
                        onValueChange = { textInput = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Ej: Ensalada César") },
                        shape = RoundedCornerShape(12.dp),
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
            }
        },
        confirmButton = {
            if (showTextInput) {
                Button(
                    onClick = {
                        if (textInput.isNotBlank()) {
                            onAddText(textInput)
                        }
                    },
                    enabled = textInput.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LifePillColors.Primary
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Guardar")
                }
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
        shape = RoundedCornerShape(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun UserHomeScreenPreview() {
    LifePillTheme {
        UserHomeScreen(
            onAddText = { _, _ -> }
        )
    }
}