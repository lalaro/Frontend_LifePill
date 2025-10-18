package com.escuelaing.edu.lifepill.ui.screens.loginScreen.forgotPasswordScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val BlueKing = Color(0xFF007bff)
private val DarkBackground = Color(0xFF1E1E1E)
private val LightGrayText = Color(0xFFB0B0B0)
private val WhiteText = Color.White

@Composable
fun VerificationScreen(
    email: String,
    onBack: () -> Unit = {},
    onVerify: (String) -> Unit = {},
    onResend: () -> Unit = {}
) {
    var code by remember { mutableStateOf("") }
    var timeLeft by remember { mutableStateOf(600) }
    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    val isCodeComplete = code.length == 6
    val isCodeValid = code.all { it.isDigit() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderSection(onBack = onBack)
        Spacer(modifier = Modifier.height(32.dp))
        TitleSection(email = email)
        Spacer(modifier = Modifier.height(24.dp))
        TimerSection(minutes = minutes, seconds = seconds)
        Spacer(modifier = Modifier.height(40.dp))
        CodeInputSection(
            code = code,
            onCodeChange = { newCode ->
                if (newCode.length <= 6 && newCode.all { it.isDigit() }) {
                    code = newCode
                }
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        VerifyButton(
            isEnabled = isCodeComplete && isCodeValid,
            onClick = { onVerify(code) }
        )
        Spacer(modifier = Modifier.height(24.dp))
        ResendSection(onResend = onResend)
        Spacer(modifier = Modifier.weight(1f))
        InfoNoteSection()
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
private fun TitleSection(email: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Verificación de código",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = WhiteText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Código enviado",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = BlueKing
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Hemos enviado un código de 6 dígitos a:",
            fontSize = 14.sp,
            color = LightGrayText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = BlueKing.copy(alpha = 0.1f)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    tint = BlueKing,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = email,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = WhiteText
                )
            }
        }
    }
}

@Composable
private fun TimerSection(minutes: Int, seconds: Int) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF007bff).copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "El código expira en ${String.format("%02d:%02d", minutes, seconds)}",
                fontSize = 12.sp,
                color = Color(0xFF007bff),
                fontWeight = FontWeight.Medium
            )
        }
    }
}


@Composable
private fun CodeInputSection(
    code: String,
    onCodeChange: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ingresa el código de verificación",
            fontSize = 16.sp,
            color = LightGrayText,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        BasicTextField(
            value = code,
            onValueChange = onCodeChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            decorationBox = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(6) { index ->
                        CodeDigitBox(
                            digit = code.getOrNull(index)?.toString() ?: "",
                            isFocused = index == code.length,
                            isFilled = index < code.length
                        )
                    }
                }
            },
            textStyle = TextStyle(color = Color.Transparent)
        )
    }
}

@Composable
private fun CodeDigitBox(
    digit: String,
    isFocused: Boolean,
    isFilled: Boolean
) {
    val borderColor = when {
        isFilled -> BlueKing
        isFocused -> BlueKing.copy(alpha = 0.7f)
        else -> LightGrayText.copy(alpha = 0.5f)
    }

    val backgroundColor = when {
        isFilled -> BlueKing.copy(alpha = 0.1f)
        isFocused -> BlueKing.copy(alpha = 0.05f)
        else -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .size(48.dp)
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .border(
                width = if (isFocused) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = digit,
            color = WhiteText,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
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
        colors = ButtonDefaults.buttonColors(
            containerColor = BlueKing,
            disabledContainerColor = LightGrayText.copy(alpha = 0.3f),
            contentColor = WhiteText,
            disabledContentColor = LightGrayText
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "Verificar código",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun ResendSection(onResend: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¿No recibiste el código?",
            fontSize = 14.sp,
            color = LightGrayText
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = onResend,
            colors = ButtonDefaults.textButtonColors(
                contentColor = BlueKing
            )
        ) {
            Text(
                text = "Reenviar código",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun InfoNoteSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = LightGrayText.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "💡 Revisa tu carpeta de spam si no encuentras el email",
            fontSize = 12.sp,
            color = LightGrayText,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(12.dp)
        )
    }
}