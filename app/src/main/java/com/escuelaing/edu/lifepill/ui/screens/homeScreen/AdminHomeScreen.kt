package com.escuelaing.edu.lifepill.ui.screens.homeScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.escuelaing.edu.lifepill.ui.screens.loginScreen.LifePillColors
import com.escuelaing.edu.lifepill.ui.theme.LifePillTheme

data class User(
    val id: String,
    val name: String,
    val email: String,
    val registeredDate: String,
    val mealsRegistered: Int,
    val isActive: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHomeScreen(
    onLogout: () -> Unit = {}
) {
    var showUsersDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var userToDelete by remember { mutableStateOf<User?>(null) }
    var selectedTab by remember { mutableStateOf(0) }

    var users by remember {
        mutableStateOf(
            listOf(
                User("1", "Laura Rodríguez", "laura@email.com", "15/10/2024", 48, true),
                User("2", "Carlos Méndez", "carlos@email.com", "10/10/2024", 32, true),
                User("3", "Ana García", "ana@email.com", "05/10/2024", 67, false),
                User("4", "Juan Pérez", "juan@email.com", "01/10/2024", 23, true),
                User("5", "María López", "maria@email.com", "28/09/2024", 89, true)
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Panel Administrador",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = LifePillColors.OnSurface
                            )
                            Text(
                                text = "Bienvenido, Admin",
                                fontSize = 12.sp,
                                color = LifePillColors.OnSurfaceVariant
                            )
                        }
                        IconButton(onClick = onLogout) {
                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = "Cerrar Sesión",
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
        containerColor = LifePillColors.Background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Tabs
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = LifePillColors.Surface,
                contentColor = LifePillColors.Primary
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Estadísticas") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Usuarios") }
                )
            }

            when (selectedTab) {
                0 -> StatisticsTab(
                    totalUsers = users.size,
                    activeUsers = users.count { it.isActive },
                    totalMeals = users.sumOf { it.mealsRegistered }
                )
                1 -> UsersManagementTab(
                    users = users,
                    onDeleteUser = { user ->
                        userToDelete = user
                        showDeleteConfirmDialog = true
                    }
                )
            }
        }

        if (showDeleteConfirmDialog && userToDelete != null) {
            AlertDialog(
                onDismissRequest = {
                    showDeleteConfirmDialog = false
                    userToDelete = null
                },
                title = {
                    Text(
                        text = "Eliminar Usuario",
                        fontWeight = FontWeight.Bold,
                        color = LifePillColors.OnSurface
                    )
                },
                text = {
                    Text(
                        text = "¿Estás seguro de que deseas eliminar a ${userToDelete?.name}? Esta acción no se puede deshacer.",
                        color = LifePillColors.OnSurfaceVariant
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            userToDelete?.let { user ->
                                users = users.filter { it.id != user.id }
                            }
                            showDeleteConfirmDialog = false
                            userToDelete = null
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD32F2F)
                        )
                    ) {
                        Text("Eliminar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDeleteConfirmDialog = false
                            userToDelete = null
                        }
                    ) {
                        Text("Cancelar", color = LifePillColors.OnSurfaceVariant)
                    }
                },
                containerColor = LifePillColors.Background,
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}

@Composable
private fun StatisticsTab(
    totalUsers: Int,
    activeUsers: Int,
    totalMeals: Int
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Resumen General",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = LifePillColors.OnSurface,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                title = "Usuarios Totales",
                value = totalUsers.toString(),
                icon = Icons.Default.Person,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "Usuarios Activos",
                value = activeUsers.toString(),
                icon = Icons.Default.CheckCircle,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

        }

        Spacer(modifier = Modifier.height(16.dp))

        // System Health Card
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Estado",
                        tint = LifePillColors.Primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Estado del Sistema",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = LifePillColors.OnSurface
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                SystemStatusRow(label = "Servidor", status = "Operativo", isOnline = true)
                SystemStatusRow(label = "Base de Datos", status = "Operativo", isOnline = true)
                SystemStatusRow(label = "IA Assistant", status = "Operativo", isOnline = true)
            }
        }

        // Activity Chart Placeholder
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
                    text = "Actividad Reciente",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = LifePillColors.OnSurface
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "📊 Gráfico de actividad de usuarios",
                    fontSize = 14.sp,
                    color = LifePillColors.OnSurfaceVariant
                )
                Text(
                    text = "Los usuarios están más activos entre las 8:00 AM y 10:00 PM",
                    fontSize = 12.sp,
                    color = LifePillColors.OnSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun UsersManagementTab(
    users: List<User>,
    onDeleteUser: (User) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Gestión de Usuarios (${users.size})",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = LifePillColors.OnSurface,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        items(users) { user ->
            UserCard(
                user = user,
                onDelete = { onDeleteUser(user) }
            )
        }
    }
}

@Composable
private fun UserCard(
    user: User,
    onDelete: () -> Unit
) {
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Surface(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                color = LifePillColors.Primary.copy(alpha = 0.2f),
                border = BorderStroke(2.dp, LifePillColors.Primary)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Usuario",
                        tint = LifePillColors.Primary,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // User Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = user.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = LifePillColors.OnSurface
                )
                Text(
                    text = user.email,
                    fontSize = 12.sp,
                    color = LifePillColors.OnSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatusChip(
                        text = if (user.isActive) "Activo" else "Inactivo",
                        isActive = user.isActive
                    )
                    Text(
                        text = "${user.mealsRegistered} comidas",
                        fontSize = 11.sp,
                        color = LifePillColors.OnSurfaceVariant
                    )
                }
            }

            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar usuario",
                    tint = Color(0xFFD32F2F)
                )
            }
        }
    }
}

@Composable
private fun StatusChip(
    text: String,
    isActive: Boolean
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isActive)
            Color(0xFF4CAF50).copy(alpha = 0.2f)
        else
            Color(0xFFFF9800).copy(alpha = 0.2f)
    ) {
        Text(
            text = text,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = if (isActive) Color(0xFF4CAF50) else Color(0xFFFF9800),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun SystemStatusRow(
    label: String,
    status: String,
    isOnline: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = LifePillColors.OnSurfaceVariant
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .then(
                        Modifier.clip(CircleShape)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier.size(8.dp),
                    shape = CircleShape,
                    color = if (isOnline) Color(0xFF4CAF50) else Color(0xFFD32F2F)
                ) {}
            }
            Text(
                text = status,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isOnline) Color(0xFF4CAF50) else Color(0xFFD32F2F)
            )
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = LifePillColors.Surface
        ),
        border = BorderStroke(1.dp, LifePillColors.Primary.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = LifePillColors.Primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = LifePillColors.OnSurface
            )
            Text(
                text = title,
                fontSize = 12.sp,
                color = LifePillColors.OnSurfaceVariant,
                maxLines = 2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminHomeScreenPreview() {
    LifePillTheme {
        AdminHomeScreen()
    }
}