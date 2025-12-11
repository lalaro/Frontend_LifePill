package com.escuelaing.edu.lifepill.ui.screens.homeScreen.UserHomeScreen

import android.widget.Toast
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.escuelaing.edu.lifepill.ui.screens.loginScreen.LifePillColors
import com.escuelaing.edu.lifepill.auth.FoodViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodRegisterScreen(
    token: String,
    foodViewModel: FoodViewModel = viewModel(),
    onBack: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    onNavigateToAssistant: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    viewModel: FoodViewModel = viewModel()
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var showPhotoVoiceDialog by remember { mutableStateOf(false) }
    var selectedFoods by remember { mutableStateOf<List<FoodItem>>(emptyList()) }
    var searchText by remember { mutableStateOf("") }
    var selectedMealType by remember { mutableStateOf("desayuno") }
    var isLoading by remember { mutableStateOf(false) }

    val foodResult by viewModel.foodResult.observeAsState()
    val error by viewModel.error.observeAsState()

    LaunchedEffect(foodResult) {
        foodResult?.let {
            Toast.makeText(context, "✅ Comida guardada: ${it.tipo}", Toast.LENGTH_SHORT).show()
            selectedFoods = emptyList()
            isLoading = false
        }
    }

    LaunchedEffect(error) {
        error?.let {
            Toast.makeText(context, "❌ Error: $it", Toast.LENGTH_LONG).show()
            isLoading = false
        }
    }

    val foodDatabase = listOf(
        FoodItem("Manzana", "52", "desayuno", "0.3g", "14g", "0.2g"),
        FoodItem("Ensalada César", "184", "almuerzo", "8g", "12g", "14g"),
        FoodItem("Pollo a la plancha", "165", "almuerzo", "31g", "0g", "3.6g"),
        FoodItem("Arroz blanco", "130", "almuerzo", "2.7g", "28g", "0.3g"),
        FoodItem("Brócoli", "34", "cena", "2.8g", "7g", "0.4g"),
        FoodItem("Salmón", "208", "cena", "20g", "0g", "13g"),
        FoodItem("Avena", "389", "desayuno", "17g", "66g", "7g"),
        FoodItem("Leche", "61", "desayuno", "3.2g", "4.8g", "3.3g"),
        FoodItem("Pan integral", "218", "desayuno", "9g", "41g", "3g"),
        FoodItem("Huevo cocido", "155", "desayuno", "13g", "1.1g", "11g")
    )

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

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LifePillColors.Surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Tipo de comida",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = LifePillColors.OnSurface
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("desayuno", "almuerzo", "cena", "otro").forEach { tipo ->
                            FilterChip(
                                selected = selectedMealType == tipo,
                                onClick = { selectedMealType = tipo },
                                label = {
                                    Text(
                                        tipo.replaceFirstChar { it.uppercase() },
                                        fontSize = 14.sp
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = LifePillColors.Primary,
                                    selectedLabelColor = Color.White,
                                    containerColor = LifePillColors.Surface,
                                    labelColor = LifePillColors.OnSurface
                                )
                            )
                        }
                    }
                }
            }

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

            Text(
                text = "Resultados de búsqueda: ${filteredFoods.size}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = LifePillColors.OnSurface
            )

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
                                        text = "${food.calories} cal",
                                        fontSize = 14.sp,
                                        color = LifePillColors.OnSurfaceVariant
                                    )
                                    Text(
                                        text = "P: ${food.protein} | C: ${food.carbs} | G: ${food.fats}",
                                        fontSize = 12.sp,
                                        color = LifePillColors.OnSurfaceVariant
                                    )
                                }

                                TextButton(
                                    onClick = {
                                        selectedFoods = selectedFoods + food.copy()
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
                HorizontalDivider(
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
                                        text = "${(food.calories.toIntOrNull() ?: 0) * food.quantity} cal (${food.quantity}x)",
                                        fontSize = 14.sp,
                                        color = LifePillColors.OnSurfaceVariant
                                    )
                                }

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(
                                        onClick = {
                                            selectedFoods = selectedFoods.toMutableList().apply {
                                                if (get(index).quantity > 1) {
                                                    set(index, get(index).copy(quantity = get(index).quantity - 1))
                                                }
                                            }
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                    }

                                    Text(
                                        text = "${food.quantity}",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = LifePillColors.OnSurface,
                                        modifier = Modifier.padding(horizontal = 4.dp)
                                    )

                                    IconButton(
                                        onClick = {
                                            selectedFoods = selectedFoods.toMutableList().apply {
                                                set(index, get(index).copy(quantity = get(index).quantity + 1))
                                            }
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Aumentar",
                                            tint = LifePillColors.Primary,
                                            modifier = Modifier.size(20.dp)
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
                }

                val totalCalorias = selectedFoods.sumOf {
                    (it.calories.toIntOrNull() ?: 0) * it.quantity
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = when {
                            totalCalorias > 2000 -> Color(0xFFFFEBEE)
                            totalCalorias > 1500 -> Color(0xFFFFF3E0)
                            else -> LifePillColors.Primary.copy(alpha = 0.2f)
                        }
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total de calorías:",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = LifePillColors.OnSurface
                            )
                            Text(
                                text = "$totalCalorias cal",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = when {
                                    totalCalorias > 2000 -> Color(0xFFD32F2F)
                                    totalCalorias > 1500 -> Color(0xFFFF6F00)
                                    else -> LifePillColors.Primary
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { (totalCalorias.toFloat() / 2000f).coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp),
                            color = when {
                                totalCalorias > 2000 -> Color(0xFFD32F2F)
                                totalCalorias > 1500 -> Color(0xFFFF6F00)
                                else -> LifePillColors.Primary
                            },
                            trackColor = LifePillColors.OnSurfaceVariant.copy(alpha = 0.2f),
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = when {
                                totalCalorias > 2000 -> "⚠️ Excede la recomendación diaria"
                                totalCalorias > 1500 -> "⚡ Acercándose al límite"
                                else -> "✓ Dentro del rango saludable"
                            },
                            fontSize = 12.sp,
                            color = LifePillColors.OnSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        isLoading = true
                        val descripcion = selectedFoods.joinToString(", ") {
                            "${it.name} (${it.quantity}x)"
                        }
                        viewModel.createFood(
                            token = token,
                            tipo = selectedMealType,
                            calorias = totalCalorias,
                            descripcion = descripcion
                        )
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
                    ),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White
                        )
                    } else {
                        Text(
                            text = "Guardar Registro",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
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
    val calories: String,
    val mealType: String,
    val protein: String = "0g",
    val carbs: String = "0g",
    val fats: String = "0g",
    var quantity: Int = 1
)