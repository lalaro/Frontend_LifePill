package com.escuelaing.edu.lifepill.ui.screens.homeScreen.UserHomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.escuelaing.edu.lifepill.ui.screens.loginScreen.LifePillColors
import com.escuelaing.edu.lifepill.ui.theme.LifePillTheme

data class ChatMessage(
    val id: String = "",
    val content: String = "",
    val isUser: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIAssistantScreen(
    onBack: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    onNavigateToAssistant: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    val listState = rememberLazyListState()
    var messages by remember { mutableStateOf<List<ChatMessage>>(emptyList()) }
    var inputText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // Initial AI greeting
    LaunchedEffect(Unit) {
        messages = listOf(
            ChatMessage(
                id = "greeting",
                content = "¡Hola! Soy tu asistente nutricional. ¿En qué puedo ayudarte hoy?",
                isUser = false
            )
        )
    }

    // Scroll to bottom when new messages arrive
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Asistente Nutricional",
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
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Messages List
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(messages) { message ->
                    ChatMessageBubble(message = message)
                }

                if (isLoading) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Surface(
                                modifier = Modifier
                                    .widthIn(max = 200.dp)
                                    .background(
                                        color = LifePillColors.Primary.copy(alpha = 0.15f),
                                        shape = RoundedCornerShape(16.dp)
                                    ),
                                shape = RoundedCornerShape(16.dp),
                                color = LifePillColors.Primary.copy(alpha = 0.15f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    repeat(3) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .background(
                                                    color = LifePillColors.Primary,
                                                    shape = RoundedCornerShape(4.dp)
                                                )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Input Area
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                color = LifePillColors.Surface,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 48.dp),
                        placeholder = {
                            Text(
                                "Escribe tu consulta...",
                                color = LifePillColors.OnSurfaceVariant,
                                fontSize = 14.sp
                            )
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = LifePillColors.Background,
                            unfocusedContainerColor = LifePillColors.Background,
                            focusedBorderColor = LifePillColors.Primary,
                            unfocusedBorderColor = LifePillColors.OnSurfaceVariant.copy(alpha = 0.3f),
                            focusedTextColor = LifePillColors.OnSurface,
                            unfocusedTextColor = LifePillColors.OnSurface,
                            cursorColor = LifePillColors.Primary
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        singleLine = false,
                        maxLines = 3
                    )

                    Button(
                        onClick = {
                            if (inputText.isNotBlank()) {
                                messages = messages + ChatMessage(
                                    id = "user_${System.currentTimeMillis()}",
                                    content = inputText,
                                    isUser = true
                                )
                                inputText = ""
                                isLoading = true

                                // Simulate AI response delay
                                Thread {
                                    Thread.sleep(1500)
                                    messages = messages + ChatMessage(
                                        id = "ai_${System.currentTimeMillis()}",
                                        content = generateAIResponse(messages),
                                        isUser = false
                                    )
                                    isLoading = false
                                }.start()
                            }
                        },
                        modifier = Modifier
                            .size(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LifePillColors.Primary,
                            contentColor = Color.White
                        ),
                        enabled = inputText.isNotBlank() && !isLoading
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Enviar",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChatMessageBubble(message: ChatMessage) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            modifier = Modifier
                .widthIn(max = 280.dp),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (message.isUser) 16.dp else 0.dp,
                bottomEnd = if (message.isUser) 0.dp else 16.dp
            ),
            color = if (message.isUser)
                LifePillColors.Primary
            else
                LifePillColors.Primary.copy(alpha = 0.15f),
            shadowElevation = 2.dp
        ) {
            Text(
                text = message.content,
                modifier = Modifier.padding(12.dp),
                color = if (message.isUser) Color.White else LifePillColors.OnSurface,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}

private fun generateAIResponse(messages: List<ChatMessage>): String {
    val responses = listOf(
        "Basándome en tu registro nutricional diario, te recomiendo aumentar tu ingesta de agua.",
        "¡Excelente opción! Considera agregar más vegetales para equilibrar tus comidas.",
        "Noté que tuviste un buen desayuno. Para el almuerzo, te sugiero una combinación balanceada de proteína y vegetales.",
        "Tu ingesta de calorías se ve balanceada hoy. ¡Sigue adelante!",
        "¿Te gustaría algunas recomendaciones para snacks saludables?",
        "Agregar más granos integrales a tu dieta puede mejorar tu balance nutricional.",
        "Esa combinación de alimentos se ve nutritivamente balanceada. ¡Excelente elección!",
        "Te recomiendo rastrear tus porciones para asegurar una nutrición óptima.",
        "Veo que has estado comiendo bien. Mantén estos hábitos saludables.",
        "¿Necesitas consejos sobre cómo preparar comidas más saludables?",
        "Recuerda mantenerte hidratado durante todo el día.",
        "Las proteínas son importantes para tu cuerpo. ¿Las incluiste hoy?",
        "Una buena estrategia es hacer 5 comidas pequeñas durante el día.",
        "Los alimentos ricos en fibra te ayudarán a sentirte más satisfecho.",
        "Intenta comer despacio y masticar bien para mejor digestión.",
        "¿Has considerado añadir frutas frescas a tus desayunos?",
        "Las grasas saludables como el aguacate son excelentes para ti.",
        "Recuerda que la hidratación es tan importante como la alimentación.",
        "¿Cuál es tu objetivo nutricional principal?",
        "Los vegetales de hoja verde son muy nutritivos y bajos en calorías."
    )
    return responses.random()
}

@Preview(showBackground = true)
@Composable
fun AIAssistantScreenPreview() {
    LifePillTheme {
        AIAssistantScreen()
    }
}