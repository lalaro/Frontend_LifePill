package com.escuelaing.edu.lifepill.ui.screens.loginScreen.forgotPasswordScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
private val BlueKing = Color(0xFF007bff)
private val DarkBackground = Color(0xFF1E1E1E)
private val LightGrayText = Color(0xFFB0B0B0)
private val WhiteText = Color.White
private val SuccessGreen = Color(0xFF28a745)
private val ErrorRed = Color(0xFFdc3545)

@Composable
fun ResetPasswordScreen(
    onBack: () -> Unit,
    onPasswordReset: () -> Unit
) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val isLengthValid = password.length >= 6
    val hasUpperCase = password.any { it.isUpperCase() }
    val hasNumber = password.any { it.isDigit() }
    val isMatch = password == confirmPassword && password.isNotEmpty()
    val isFormValid = isLengthValid && hasUpperCase && hasNumber && isMatch

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderSection(onBack = onBack)
            Spacer(modifier = Modifier.height(24.dp))
            TitleSection()
            Spacer(modifier = Modifier.height(32.dp))
            PasswordFieldsSection(
                password = password,
                confirmPassword = confirmPassword,
                passwordVisible = passwordVisible,
                confirmPasswordVisible = confirmPasswordVisible,
                onPasswordChange = { password = it },
                onConfirmPasswordChange = { confirmPassword = it },
                onPasswordVisibilityToggle = { passwordVisible = !passwordVisible },
                onConfirmPasswordVisibilityToggle = { confirmPasswordVisible = !confirmPasswordVisible }
            )
            Spacer(modifier = Modifier.height(24.dp))
            ValidationSection(
                isLengthValid = isLengthValid,
                hasUpperCase = hasUpperCase,
                hasNumber = hasNumber,
                isMatch = isMatch
            )
            Spacer(modifier = Modifier.height(32.dp))
            UpdateButton(
                isEnabled = isFormValid,
                onClick = onPasswordReset
            )
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
private fun TitleSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    color = BlueKing.copy(alpha = 0.1f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Candado",
                tint = BlueKing,
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Nueva contraseña",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = WhiteText
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Crea una contraseña segura con al menos 6 caracteres",
            fontSize = 14.sp,
            color = LightGrayText,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun PasswordFieldsSection(
    password: String,
    confirmPassword: String,
    passwordVisible: Boolean,
    confirmPasswordVisible: Boolean,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onPasswordVisibilityToggle: () -> Unit,
    onConfirmPasswordVisibilityToggle: () -> Unit
) {
    Column {
        // Campo nueva contraseña
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Nueva contraseña", color = LightGrayText) },
            placeholder = { Text("Ingresa tu nueva contraseña", color = LightGrayText.copy(alpha = 0.6f)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Candado",
                    tint = BlueKing
                )
            },
            trailingIcon = {
                IconButton(onClick = onPasswordVisibilityToggle) {

                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
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

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = { Text("Confirmar contraseña", color = LightGrayText) },
            placeholder = { Text("Confirma tu nueva contraseña", color = LightGrayText.copy(alpha = 0.6f)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Candado",
                    tint = BlueKing
                )
            },
            trailingIcon = {
                IconButton(onClick = onConfirmPasswordVisibilityToggle) {

                }
            },
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
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
}

@Composable
private fun ValidationSection(
    isLengthValid: Boolean,
    hasUpperCase: Boolean,
    hasNumber: Boolean,
    isMatch: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = DarkBackground.copy(alpha = 0.3f)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Requisitos de contraseña:",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = WhiteText,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            ValidationItem(
                text = "Al menos 6 caracteres",
                isValid = isLengthValid
            )

            ValidationItem(
                text = "Al menos una mayúscula",
                isValid = hasUpperCase
            )

            ValidationItem(
                text = "Al menos un número",
                isValid = hasNumber
            )

            ValidationItem(
                text = "Las contraseñas coinciden",
                isValid = isMatch
            )
        }
    }
}

@Composable
private fun ValidationItem(
    text: String,
    isValid: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = if (isValid) SuccessGreen else LightGrayText.copy(alpha = 0.5f),
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            fontSize = 12.sp,
            color = if (isValid) SuccessGreen else LightGrayText.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun UpdateButton(
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = isEnabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = BlueKing,
            disabledContainerColor = LightGrayText.copy(alpha = 0.3f),
            contentColor = WhiteText,
            disabledContentColor = LightGrayText
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "Actualizar contraseña",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}