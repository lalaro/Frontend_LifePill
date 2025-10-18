package com.escuelaing.edu.lifepill.ui.screens.loginScreen.forgotPasswordScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.OutlinedTextField

private val BlueKing = Color(0xFF007bff)
private val DarkBackground = Color(0xFF1E1E1E)
private val LightGrayText = Color(0xFFB0B0B0)
private val WhiteText = Color.White

@Composable
fun ForgotPasswordScreen(
    onBack: () -> Unit = {},
    onVerify: (String) -> Unit = {}
) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    val isEmailValid = email.text.contains("@") && email.text.isNotEmpty()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            HeaderSection(onBack = onBack)
            Spacer(modifier = Modifier.height(32.dp))
            TitleSection()
            Spacer(modifier = Modifier.height(48.dp))
            InstructionsSection()
            Spacer(modifier = Modifier.height(32.dp))
            EmailInputSection(
                email = email,
                onEmailChange = { email = it }
            )
            Spacer(modifier = Modifier.height(12.dp))
            WarningSection()
            Spacer(modifier = Modifier.height(40.dp))
            VerifyButton(
                isEnabled = isEmailValid,
                onClick = { onVerify(email.text) }
            )
        }
    }
}

@Composable
private fun HeaderSection(onBack: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopStart
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier.size(32.dp)
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
private fun TitleSection() {
    Text(
        text = "Recuperar Contraseña",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = WhiteText,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun InstructionsSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ingresa tu email",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = BlueKing
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Verificaremos que tengas una cuenta registrada y te enviaremos un código de verificación por email",
            fontSize = 14.sp,
            color = LightGrayText,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun EmailInputSection(
    email: TextFieldValue,
    onEmailChange: (TextFieldValue) -> Unit
) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = {
            Text(
                text = "Correo electrónico",
                color = LightGrayText
            )
        },
        placeholder = {
            Text(
                text = "ejemplo@correo.com",
                color = LightGrayText.copy(alpha = 0.6f)
            )
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Icono de email",
                tint = BlueKing
            )
        },
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF007bff),
            unfocusedBorderColor = Color(0xFF007bff),
            cursorColor = Color(0xFF007bff),
            focusedLabelColor = Color(0xFF007bff),
            unfocusedLabelColor = Color(0xFFB0B0B0),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        )
    )
}

@Composable
private fun WarningSection() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Información",
            tint = BlueKing,
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "Solo se pueden recuperar cuentas registradas en el sistema",
            fontSize = 12.sp,
            color = LightGrayText,
            lineHeight = 16.sp
        )
    }
}

@Composable
private fun VerifyButton(
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = isEnabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = BlueKing,
            disabledContainerColor = LightGrayText.copy(alpha = 0.3f),
            contentColor = WhiteText,
            disabledContentColor = LightGrayText
        )
    ) {
        Text(
            text = "Verificar y enviar código",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}