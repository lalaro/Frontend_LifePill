package com.escuelaing.edu.lifepill.ui.screens.loginScreen.forgotPasswordScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Definición de colores del tema
private val BlueKing = Color(0xFF007bff)
private val DarkBackground = Color(0xFF1E1E1E)
private val LightGrayText = Color(0xFFB0B0B0)
private val WhiteText = Color.White
private val SuccessGreen = Color(0xFF28a745)

@Composable
fun PasswordSuccessScreen(
    onBack: () -> Unit,
    onContinue: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HeaderSection(onBack = onBack)
            Spacer(modifier = Modifier.weight(1f))
            SuccessIconSection()
            Spacer(modifier = Modifier.height(32.dp))
            SuccessMessageSection()
            Spacer(modifier = Modifier.height(48.dp))
            ContinueButton(onClick = onContinue)
            Spacer(modifier = Modifier.height(24.dp))
            SecurityNoteSection()
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun HeaderSection(onBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Cerrar",
                tint = BlueKing,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun SuccessIconSection() {
    Box(
        modifier = Modifier
            .size(120.dp)
            .background(
                color = SuccessGreen.copy(alpha = 0.1f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Éxito",
            tint = SuccessGreen,
            modifier = Modifier.size(64.dp)
        )
    }
}

@Composable
private fun SuccessMessageSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Contraseña actualizada!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = WhiteText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tu contraseña ha sido cambiada exitosamente.\nYa puedes iniciar sesión con tu nueva contraseña.",
            fontSize = 16.sp,
            color = LightGrayText,
            lineHeight = 22.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}

@Composable
private fun ContinueButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = BlueKing,
            contentColor = WhiteText
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "Continuar al inicio de sesión",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun SecurityNoteSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = BlueKing.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "💡 Recuerda guardar tu nueva contraseña en un lugar seguro",
            fontSize = 14.sp,
            color = LightGrayText,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}