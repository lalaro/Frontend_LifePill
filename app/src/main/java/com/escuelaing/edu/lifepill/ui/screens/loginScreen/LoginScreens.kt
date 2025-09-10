package com.escuelaing.edu.lifepill.ui.screens.loginScreen

import android.util.Patterns
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.escuelaing.edu.lifepill.ui.theme.LifePillTheme
import java.util.*

object LifePillColors {
    val Primary = Color(0xFF007bff)
    val Background = Color(0xFF1E1E1E)
    val Surface = Color(0xFF16213E)
    val OnSurface = Color.White
    val OnSurfaceVariant = Color(0xFFB8BCC8)
    val Error = Color(0xFFFF6B6B)
}

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String = ""
)

data class FormField(
    val value: String = "",
    val error: String = "",
    val isValid: Boolean = true
)

@Composable
fun LoginScreens(
    onForgotPasswordClick: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {}
) {
    var isLoginScreen by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LifePillColors.Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LifePillHeader(isLoginScreen = isLoginScreen)

            Spacer(modifier = Modifier.height(16.dp))
            TabSelector(
                isLoginSelected = isLoginScreen,
                onTabSelected = { isLoginScreen = it }
            )

            Spacer(modifier = Modifier.height(32.dp))
            if (isLoginScreen) {
                LoginContent(
                    onForgotPasswordClick = onForgotPasswordClick,
                    onLoginSuccess = onLoginSuccess,
                    onNavigateToRegister = onNavigateToRegister
                )
            } else {
                CreateAccountContent()
            }
        }
    }
}

@Composable
private fun LifePillHeader(isLoginScreen: Boolean) {
    Spacer(modifier = Modifier.height(30.dp))
    Text(
        text = if (isLoginScreen) "LifePill" else "Crear Cuenta",
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = LifePillColors.OnSurface
    )
    if (isLoginScreen) {
        Text(
            text = "Tu salud en tus manos",
            fontSize = 16.sp,
            color = LifePillColors.OnSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun TabSelector(
    isLoginSelected: Boolean,
    onTabSelected: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(LifePillColors.Surface),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        TabButton(
            text = "Iniciar Sesión",
            isSelected = isLoginSelected,
            onClick = { onTabSelected(true) },
            modifier = Modifier.weight(1f)
        )
        TabButton(
            text = "Crear Cuenta",
            isSelected = !isLoginSelected,
            onClick = { onTabSelected(false) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun TabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.padding(4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) LifePillColors.Primary else Color.Transparent,
            contentColor = if (isSelected) Color.White else LifePillColors.OnSurfaceVariant
        ),
        elevation = ButtonDefaults.buttonElevation(0.dp)
    ) {
        Text(text = text, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
    }
}

@Composable
fun LoginContent(
    onForgotPasswordClick: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {}
) {
    var emailField by remember { mutableStateOf(FormField()) }
    var passwordField by remember { mutableStateOf(FormField()) }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LifePillTextField(
            field = emailField,
            onValueChange = { newValue ->
                emailField = emailField.copy(
                    value = newValue,
                    error = "",
                    isValid = true
                )
            },
            label = "Correo Electrónico",
            leadingIcon = Icons.Default.Email,
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(16.dp))

        LifePillTextField(
            field = passwordField,
            onValueChange = { newValue ->
                passwordField = passwordField.copy(
                    value = newValue,
                    error = "",
                    isValid = true
                )
            },
            label = "Contraseña",
            leadingIcon = Icons.Default.Lock,
            keyboardType = KeyboardType.Password,
            isPassword = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        OnBoardingStyleButton(
            text = if (isLoading) "Iniciando..." else "Iniciar Sesión",
            onClick = {
                val emailValidation = validateEmail(emailField.value)
                val passwordValidation = validatePassword(passwordField.value)

                emailField = emailField.copy(
                    error = emailValidation.errorMessage,
                    isValid = emailValidation.isValid
                )
                passwordField = passwordField.copy(
                    error = passwordValidation.errorMessage,
                    isValid = passwordValidation.isValid
                )

                if (emailValidation.isValid && passwordValidation.isValid) {
                    isLoading = true
                    // Simular login
                    // TODO: Implementar lógica de autenticación real
                    onLoginSuccess()
                }
            },
            enabled = !isLoading && emailField.value.isNotEmpty() && passwordField.value.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "¿Olvidaste tu contraseña?",
            color = LifePillColors.Primary,
            fontSize = 14.sp,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable { onForgotPasswordClick() }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "¿No tienes cuenta? ",
                color = LifePillColors.OnSurfaceVariant,
                fontSize = 14.sp
            )
            Text(
                text = "Regístrate",
                color = LifePillColors.Primary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onNavigateToRegister() }
            )
        }
    }
}

@Composable
private fun OnBoardingStyleButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = enabled,
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = LifePillColors.Primary,
            contentColor = Color.White,
            disabledContainerColor = LifePillColors.Surface.copy(alpha = 0.6f),
            disabledContentColor = LifePillColors.OnSurfaceVariant.copy(alpha = 0.6f)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp,
            disabledElevation = 0.dp
        )
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountContent() {
    var currentStep by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var documentType by remember { mutableStateOf("CC") }
    var documentNumber by remember { mutableStateOf(FormField()) }
    var firstName by remember { mutableStateOf(FormField()) }
    var lastName by remember { mutableStateOf(FormField()) }
    var username by remember { mutableStateOf(FormField()) }
    var email by remember { mutableStateOf(FormField()) }
    var password by remember { mutableStateOf(FormField()) }
    var phone by remember { mutableStateOf(FormField()) }
    var address by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var selectedWorkMode by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        StepProgressIndicator(
            currentStep = currentStep,
            totalSteps = 4,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        when (currentStep) {
            0 -> DocumentInfoStep(
                documentType = documentType,
                onDocumentTypeChange = { documentType = it },
                documentNumber = documentNumber,
                onDocumentNumberChange = { documentNumber = it }
            )
            1 -> PersonalInfoStep(
                firstName = firstName,
                onFirstNameChange = { firstName = it },
                lastName = lastName,
                onLastNameChange = { lastName = it },
                username = username,
                onUsernameChange = { username = it },
                birthDate = birthDate,
                onBirthDateClick = { showDatePicker = true },
                selectedGender = selectedGender,
                onGenderChange = { selectedGender = it }
            )
            2 -> ContactInfoStep(
                phone = phone,
                onPhoneChange = { phone = it },
                address = address,
                onAddressChange = { address = it },
                email = email,
                onEmailChange = { email = it },
                password = password,
                onPasswordChange = { password = it }
            )
            3 -> PhysicalInfoStep(
                age = age,
                onAgeChange = { age = it },
                weight = weight,
                onWeightChange = { weight = it },
                height = height,
                onHeightChange = { height = it },
                selectedWorkMode = selectedWorkMode,
                onWorkModeChange = { selectedWorkMode = it }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        NavigationButtons(
            currentStep = currentStep,
            totalSteps = 4,
            onPrevious = { if (currentStep > 0) currentStep-- },
            onNext = { if (currentStep < 3) currentStep++ },
            onFinish = { /* TODO: Implementar registro */ }
        )
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            birthDate = formatDate(millis)
                        }
                        showDatePicker = false
                    }
                ) { Text("Confirmar", color = LifePillColors.Primary) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar", color = LifePillColors.OnSurfaceVariant)
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    selectedDayContainerColor = LifePillColors.Primary,
                    todayContentColor = LifePillColors.Primary,
                    todayDateBorderColor = LifePillColors.Primary
                )
            )
        }
    }
}

@Composable
private fun StepProgressIndicator(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        repeat(totalSteps) { step ->
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(
                        color = if (step <= currentStep) LifePillColors.Primary else LifePillColors.Surface,
                        shape = RoundedCornerShape(6.dp)
                    )
            )
        }
    }
}

@Composable
private fun DocumentInfoStep(
    documentType: String,
    onDocumentTypeChange: (String) -> Unit,
    documentNumber: FormField,
    onDocumentNumberChange: (FormField) -> Unit
) {
    val documentTypes = listOf("CC", "TI", "CE", "Pasaporte")
    var expanded by remember { mutableStateOf(false) }

    Column {
        SectionTitle("Información de Documento")

        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))

        LifePillTextField(
            field = documentNumber,
            onValueChange = { value ->
                val filteredValue = when (documentType) {
                    "CC", "TI", "CE" -> value.filter { it.isDigit() }
                    else -> value.filter { it.isLetterOrDigit() }
                }
                val validation = validateDocumentNumber(filteredValue, documentType)
                onDocumentNumberChange(
                    FormField(
                        value = filteredValue,
                        error = validation.errorMessage,
                        isValid = validation.isValid
                    )
                )
            },
            label = "Número de Documento",
            leadingIcon = Icons.Default.Create,
            keyboardType = if (documentType in listOf("CC", "TI", "CE")) KeyboardType.Number else KeyboardType.Text
        )
    }
}

@Composable
private fun PersonalInfoStep(
    firstName: FormField,
    onFirstNameChange: (FormField) -> Unit,
    lastName: FormField,
    onLastNameChange: (FormField) -> Unit,
    username: FormField,
    onUsernameChange: (FormField) -> Unit,
    birthDate: String,
    onBirthDateClick: () -> Unit,
    selectedGender: String,
    onGenderChange: (String) -> Unit
) {
    Column {
        SectionTitle("Información Personal")

        Spacer(modifier = Modifier.height(16.dp))

        LifePillTextField(
            field = firstName,
            onValueChange = { value ->
                val filteredValue = value.filter { it.isLetter() || it.isWhitespace() }
                val validation = validateName(filteredValue)
                onFirstNameChange(
                    FormField(
                        value = filteredValue,
                        error = validation.errorMessage,
                        isValid = validation.isValid
                    )
                )
            },
            label = "Nombre",
            leadingIcon = Icons.Default.Person
        )

        Spacer(modifier = Modifier.height(16.dp))

        LifePillTextField(
            field = lastName,
            onValueChange = { value ->
                val filteredValue = value.filter { it.isLetter() || it.isWhitespace() }
                val validation = validateName(filteredValue)
                onLastNameChange(
                    FormField(
                        value = filteredValue,
                        error = validation.errorMessage,
                        isValid = validation.isValid
                    )
                )
            },
            label = "Apellido",
            leadingIcon = Icons.Default.Person
        )

        Spacer(modifier = Modifier.height(16.dp))

        LifePillTextField(
            field = username,
            onValueChange = { value ->
                val filteredValue = value.filter { it.isLetterOrDigit() || it == '_' || it == '.' }
                val validation = validateUsername(filteredValue)
                onUsernameChange(
                    FormField(
                        value = filteredValue,
                        error = validation.errorMessage,
                        isValid = validation.isValid
                    )
                )
            },
            label = "Nombre de Usuario",
            leadingIcon = Icons.Default.AccountCircle
        )

        Spacer(modifier = Modifier.height(16.dp))

        LifePillTextField(
            field = FormField(value = birthDate),
            onValueChange = {},
            label = "Fecha de Nacimiento",
            leadingIcon = Icons.Default.DateRange,
            readOnly = true,
            modifier = Modifier.clickable { onBirthDateClick() }
        )

        Spacer(modifier = Modifier.height(24.dp))

        GenderSelector(
            selectedGender = selectedGender,
            onGenderSelected = onGenderChange
        )
    }
}

@Composable
private fun ContactInfoStep(
    phone: FormField,
    onPhoneChange: (FormField) -> Unit,
    address: String,
    onAddressChange: (String) -> Unit,
    email: FormField,
    onEmailChange: (FormField) -> Unit,
    password: FormField,
    onPasswordChange: (FormField) -> Unit
) {
    Column {
        SectionTitle("Información de Contacto")

        Spacer(modifier = Modifier.height(16.dp))

        LifePillTextField(
            field = phone,
            onValueChange = { value ->
                val validation = validatePhone(value)
                onPhoneChange(
                    FormField(
                        value = value,
                        error = validation.errorMessage,
                        isValid = validation.isValid
                    )
                )
            },
            label = "Teléfono",
            leadingIcon = Icons.Default.Phone,
            keyboardType = KeyboardType.Phone
        )

        Spacer(modifier = Modifier.height(16.dp))

        LifePillTextField(
            field = FormField(value = address),
            onValueChange = { onAddressChange(it) },
            label = "Dirección",
            leadingIcon = Icons.Default.Home
        )

        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle("Credenciales de Acceso")

        Spacer(modifier = Modifier.height(16.dp))

        LifePillTextField(
            field = email,
            onValueChange = { value ->
                val validation = validateEmail(value)
                onEmailChange(
                    FormField(
                        value = value,
                        error = validation.errorMessage,
                        isValid = validation.isValid
                    )
                )
            },
            label = "Correo Electrónico",
            leadingIcon = Icons.Default.Email,
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(16.dp))

        LifePillTextField(
            field = password,
            onValueChange = { value ->
                val validation = validatePassword(value)
                onPasswordChange(
                    FormField(
                        value = value,
                        error = validation.errorMessage,
                        isValid = validation.isValid
                    )
                )
            },
            label = "Contraseña",
            leadingIcon = Icons.Default.Lock,
            keyboardType = KeyboardType.Password,
            isPassword = true
        )
    }
}

@Composable
private fun PhysicalInfoStep(
    age: String,
    onAgeChange: (String) -> Unit,
    weight: String,
    onWeightChange: (String) -> Unit,
    height: String,
    onHeightChange: (String) -> Unit,
    selectedWorkMode: String,
    onWorkModeChange: (String) -> Unit // CORREGIDO: cambié el nombre del parámetro
) {
    Column {
        SectionTitle("Información Física (Opcional)")

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LifePillTextField(
                field = FormField(value = age),
                onValueChange = { value ->
                    val filteredValue = value.filter { it.isDigit() }
                    onAgeChange(filteredValue)
                },
                label = "Edad",
                leadingIcon = Icons.Default.Face,
                keyboardType = KeyboardType.Number,
                modifier = Modifier.weight(1f)
            )

            LifePillTextField(
                field = FormField(value = weight),
                onValueChange = { value ->
                    val filteredValue = value.filter { it.isDigit() }
                    onWeightChange(filteredValue)
                },
                label = "Peso (kg)",
                leadingIcon = Icons.Default.Create,
                keyboardType = KeyboardType.Number,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LifePillTextField(
            field = FormField(value = height),
            onValueChange = { value ->
                val filteredValue = value.filter { it.isDigit() }
                onHeightChange(filteredValue)
            },
            label = "Estatura (cm)",
            leadingIcon = Icons.Default.Create,
            keyboardType = KeyboardType.Number
        )

        Spacer(modifier = Modifier.height(24.dp))

        WorkModeSelector(
            selectedWorkMode = selectedWorkMode,
            onWorkModeSelected = onWorkModeChange // CORREGIDO: ahora usa el parámetro correcto
        )
    }
}

@Composable
private fun WorkModeSelector(
    selectedWorkMode: String,
    onWorkModeSelected: (String) -> Unit
) {
    Column {
        Text(
            text = "Modo de Trabajo",
            color = LifePillColors.Primary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            WorkModeButton(
                text = "Híbrido",
                isSelected = selectedWorkMode == "Híbrido",
                onClick = { onWorkModeSelected("Híbrido") },
                modifier = Modifier.weight(1f)
            )
            WorkModeButton(
                text = "Virtual",
                isSelected = selectedWorkMode == "Virtual",
                onClick = { onWorkModeSelected("Virtual") },
                modifier = Modifier.weight(1f)
            )
            WorkModeButton(
                text = "Presencial",
                isSelected = selectedWorkMode == "Presencial",
                onClick = { onWorkModeSelected("Presencial") },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun WorkModeButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) LifePillColors.Primary else LifePillColors.Surface,
            contentColor = if (isSelected) Color.White else LifePillColors.OnSurfaceVariant
        ),
        border = if (!isSelected) BorderStroke(2.dp, LifePillColors.Primary) else null,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (isSelected) 4.dp else 0.dp,
            pressedElevation = if (isSelected) 8.dp else 2.dp
        )
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun NavigationButtons(
    currentStep: Int,
    totalSteps: Int,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onFinish: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (currentStep == 0) Arrangement.End else Arrangement.SpaceBetween
    ) {
        if (currentStep > 0) {
            OutlinedButton(
                onClick = onPrevious,
                modifier = Modifier.height(56.dp),
                shape = RoundedCornerShape(28.dp), // Mismo estilo redondeado
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = LifePillColors.Primary
                ),
                border = BorderStroke(2.dp, LifePillColors.Primary) // Borde más grueso
            ) {
                Text(
                    text = "Anterior",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        OnBoardingStyleButton(
            text = if (currentStep == totalSteps - 1) "Crear Cuenta" else "Siguiente",
            onClick = if (currentStep == totalSteps - 1) onFinish else onNext,
            modifier = if (currentStep == 0) Modifier else Modifier.weight(1f).padding(start = 16.dp)
        )
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        color = LifePillColors.Primary,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun GenderSelector(
    selectedGender: String,
    onGenderSelected: (String) -> Unit
) {
    Column {
        Text(
            text = "Género",
            color = LifePillColors.Primary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GenderButton(
                text = "Masculino",
                isSelected = selectedGender == "Masculino",
                onClick = { onGenderSelected("Masculino") },
                modifier = Modifier.weight(1f)
            )
            GenderButton(
                text = "Femenino",
                isSelected = selectedGender == "Femenino",
                onClick = { onGenderSelected("Femenino") },
                modifier = Modifier.weight(1f)
            )
            GenderButton(
                text = "Otro",
                isSelected = selectedGender == "Otro",
                onClick = { onGenderSelected("Otro") },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun GenderButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(24.dp), // Más redondeado como el estilo del onboarding
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) LifePillColors.Primary else LifePillColors.Surface,
            contentColor = if (isSelected) Color.White else LifePillColors.OnSurfaceVariant
        ),
        border = if (!isSelected) BorderStroke(2.dp, LifePillColors.Primary) else null,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (isSelected) 4.dp else 0.dp,
            pressedElevation = if (isSelected) 8.dp else 2.dp
        )
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun LifePillTextField(
    field: FormField,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val hasError = !field.isValid && field.error.isNotEmpty()

    Column(modifier = modifier) {
        OutlinedTextField(
            value = field.value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(label, color = LifePillColors.OnSurfaceVariant) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            readOnly = readOnly,
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            isError = hasError,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = if (hasError) Color.Black else LifePillColors.Surface,
                unfocusedContainerColor = if (hasError) Color.Black else LifePillColors.Surface,
                focusedBorderColor = if (hasError) LifePillColors.Error else LifePillColors.Primary,
                unfocusedBorderColor = if (hasError) LifePillColors.Error else LifePillColors.OnSurfaceVariant,
                focusedTextColor = if (hasError) Color.White else LifePillColors.OnSurface,
                unfocusedTextColor = if (hasError) Color.White else LifePillColors.OnSurface,
                cursorColor = if (hasError) Color.White else LifePillColors.Primary,
                focusedLabelColor = if (hasError) Color.White else LifePillColors.Primary,
                unfocusedLabelColor = if (hasError) Color.White else LifePillColors.OnSurfaceVariant,
                errorBorderColor = LifePillColors.Error,
                errorLabelColor = Color.White,
                errorContainerColor = Color.Black,
                errorTextColor = Color.White,
                errorCursorColor = Color.White
            )
        )

        if (hasError) {
            Text(
                text = field.error,
                color = LifePillColors.Error,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
private fun LifePillButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = LifePillColors.Primary,
            contentColor = Color.White,
            disabledContainerColor = LifePillColors.Surface,
            disabledContentColor = LifePillColors.OnSurfaceVariant
        )
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

private fun validateEmail(email: String): ValidationResult {
    return when {
        email.isEmpty() -> ValidationResult(false, "El correo es obligatorio")
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ValidationResult(false, "Formato de correo inválido")
        else -> ValidationResult(true)
    }
}

private fun validatePassword(password: String): ValidationResult {
    return when {
        password.isEmpty() -> ValidationResult(false, "La contraseña es obligatoria")
        password.length < 6 -> ValidationResult(false, "Mínimo 6 caracteres")
        else -> ValidationResult(true)
    }
}

private fun validateDocumentNumber(number: String, type: String): ValidationResult {
    return when (type) {
        "CC", "TI", "CE" -> {
            when {
                number.isEmpty() -> ValidationResult(false, "Campo obligatorio")
                !number.all { it.isDigit() } -> ValidationResult(false, "Solo números")
                number.length < 7 -> ValidationResult(false, "Mínimo 7 dígitos")
                else -> ValidationResult(true)
            }
        }
        "Pasaporte" -> {
            when {
                number.isEmpty() -> ValidationResult(false, "Campo obligatorio")
                number.length < 6 -> ValidationResult(false, "Mínimo 6 caracteres")
                else -> ValidationResult(true)
            }
        }
        else -> ValidationResult(true)
    }
}

private fun validateName(name: String): ValidationResult {
    return when {
        name.isEmpty() -> ValidationResult(false, "Campo obligatorio")
        !name.all { it.isLetter() || it.isWhitespace() } -> ValidationResult(false, "Solo letras")
        name.trim().length < 2 -> ValidationResult(false, "Mínimo 2 caracteres")
        else -> ValidationResult(true)
    }
}

private fun validateUsername(username: String): ValidationResult {
    return when {
        username.isEmpty() -> ValidationResult(false, "Campo obligatorio")
        username.length < 3 -> ValidationResult(false, "Mínimo 3 caracteres")
        !username.all { it.isLetterOrDigit() || it == '_' || it == '.' } ->
            ValidationResult(false, "Solo letras, números, _ y .")
        else -> ValidationResult(true)
    }
}

private fun validatePhone(phone: String): ValidationResult {
    val digitsOnly = phone.replace(Regex("[^\\d]"), "")
    return when {
        phone.isEmpty() -> ValidationResult(false, "Campo obligatorio")
        digitsOnly.length < 10 -> ValidationResult(false, "Mínimo 10 dígitos")
        digitsOnly.length > 15 -> ValidationResult(false, "Máximo 15 dígitos")
        else -> ValidationResult(true)
    }
}

private fun formatDate(millis: Long): String {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = millis
    }
    return String.format(
        "%02d/%02d/%04d",
        calendar.get(Calendar.DAY_OF_MONTH),
        calendar.get(Calendar.MONTH) + 1,
        calendar.get(Calendar.YEAR)
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LifePillTheme {
        LoginScreens()
    }
}