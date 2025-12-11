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

            // Card de Resumen del Día
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
                            text = "Basado en tus objetivos, te recomendamos mantener una dieta balanceada con proteínas, carbohidratos complejos y grasas saludables.",
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

@Preview(showBackground = true)
@Composable
fun UserHomeScreenPreview() {
    LifePillTheme {
        UserHomeScreen(
            onAddText = { _, _ -> }
        )
    }
}