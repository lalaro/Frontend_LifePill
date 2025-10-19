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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.escuelaing.edu.lifepill.ui.screens.loginScreen.LifePillColors
import com.escuelaing.edu.lifepill.ui.theme.LifePillTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodRegisterScreen(
    onBack: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    onNavigateToAssistant: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onSaveFood: (String) -> Unit = {}
) {
    val scrollState = rememberScrollState()
    var showPhotoVoiceDialog by remember { mutableStateOf(false) }
    var selectedFoods by remember { mutableStateOf<List<FoodItem>>(emptyList()) }
    var searchText by remember { mutableStateOf("") }

    // Base de datos de alimentos con calorías
    val foodDatabase = listOf(
        FoodItem("Manzana", "52 cal por 100g"),
        FoodItem("Ensalada César", "184 cal por porción"),
        FoodItem("Pollo a la plancha", "165 cal por 100g"),
        FoodItem("Arroz blanco", "130 cal por 100g"),
        FoodItem("Brócoli", "34 cal por 100g"),
        FoodItem("Salmón", "208 cal por 100g"),
        FoodItem("Avena", "389 cal por 100g"),
        FoodItem("Leche", "61 cal por 100ml"),
        FoodItem("Pan integral", "218 cal por 100g"),
        FoodItem("Huevo cocido", "155 cal por 100g")
    )

    // Filtrar alimentos según búsqueda
    val filteredFoods = if (searchText.isBlank()) {
        foodDatabase
    } else {
        foodDatabase.filter { food ->
            food.name.contains(searchText, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Agregar Comida",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = LifePillColors.OnSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = LifePillColors.OnSurface
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

            // Botón Agregar Comida
            OutlinedButton(
                onClick = { showPhotoVoiceDialog = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = LifePillColors.Primary
                ),
                border = BorderStroke(2.dp, LifePillColors.Primary)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Agregar Comida",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Campo de búsqueda
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                placeholder = { Text("Buscar alimento...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = LifePillColors.OnSurfaceVariant
                    )
                },
                trailingIcon = {
                    if (searchText.isNotBlank()) {
                        IconButton(onClick = { searchText = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Limpiar búsqueda",
                                tint = LifePillColors.OnSurfaceVariant
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = LifePillColors.Surface,
                    unfocusedContainerColor = LifePillColors.Surface,
                    focusedBorderColor = LifePillColors.Primary,
                    unfocusedBorderColor = LifePillColors.OnSurfaceVariant,
                    focusedTextColor = LifePillColors.OnSurface,
                    unfocusedTextColor = LifePillColors.OnSurface
                ),
                singleLine = true
            )

            // Título de resultados
            Text(
                text = "Resultados de búsqueda: ${filteredFoods.size}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = LifePillColors.OnSurface
            )

            // Lista de alimentos disponibles (filtrada)
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (filteredFoods.isEmpty()) {
                    Text(
                        text = "No se encontraron alimentos",
                        fontSize = 14.sp,
                        color = LifePillColors.OnSurfaceVariant,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    filteredFoods.forEach { food ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = LifePillColors.Surface
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = food.name,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = LifePillColors.OnSurface
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = food.calories,
                                        fontSize = 14.sp,
                                        color = LifePillColors.OnSurfaceVariant
                                    )
                                }

                                TextButton(
                                    onClick = {
                                        selectedFoods = selectedFoods + food
                                        onSaveFood("${food.name} - ${food.calories}")
                                    }
                                ) {
                                    Text(
                                        text = "+",
                                        color = LifePillColors.Primary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 24.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedFoods.isNotEmpty()) {
                Divider(
                    color = LifePillColors.OnSurfaceVariant.copy(alpha = 0.2f),
                    thickness = 1.dp
                )

                Text(
                    text = "Productos Seleccionados (${selectedFoods.size})",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = LifePillColors.OnSurface
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    selectedFoods.forEachIndexed { index, food ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = LifePillColors.Primary.copy(alpha = 0.1f)
                            ),
                            border = BorderStroke(1.dp, LifePillColors.Primary)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = food.name,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = LifePillColors.OnSurface
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = food.calories,
                                        fontSize = 14.sp,
                                        color = LifePillColors.OnSurfaceVariant
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        selectedFoods = selectedFoods.toMutableList().apply {
                                            removeAt(index)
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar",
                                        tint = Color(0xFFD32F2F),
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón Guardar Registro
                Button(
                    onClick = {
                        selectedFoods = emptyList()
                        searchText = ""
                    },
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
                        text = "Guardar Registro",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        if (showPhotoVoiceDialog) {
            PhotoVoiceDialog(
                onDismiss = { showPhotoVoiceDialog = false },
                onTakePhoto = {
                    showPhotoVoiceDialog = false
                },
                onUseVoice = {
                    showPhotoVoiceDialog = false
                }
            )
        }
    }
}

@Composable
private fun PhotoVoiceDialog(
    onDismiss: () -> Unit,
    onTakePhoto: () -> Unit,
    onUseVoice: () -> Unit
) {
    var showTextInput by remember { mutableStateOf(false) }
    var textInput by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Agregar Comida",
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
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Cámara",
                            modifier = Modifier.size(24.dp)
                        )
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
                            onUseVoice()
                            textInput = ""
                            showTextInput = false
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
                onClick = {
                    if (showTextInput) {
                        showTextInput = false
                    } else {
                        onDismiss()
                    }
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = LifePillColors.OnSurfaceVariant
                )
            ) {
                Text(if (showTextInput) "Atrás" else "Cancelar")
            }
        },
        containerColor = LifePillColors.Background,
        shape = RoundedCornerShape(16.dp)
    )
}

data class FoodItem(
    val name: String,
    val calories: String
)

@Preview(showBackground = true)
@Composable
fun FoodRegisterScreenPreview() {
    LifePillTheme {
        FoodRegisterScreen()
    }
}