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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.escuelaing.edu.lifepill.R
import com.escuelaing.edu.lifepill.ui.theme.LifePillTheme
import java.util.*
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.escuelaing.edu.lifepill.auth.AuthViewModel
import com.escuelaing.edu.lifepill.network.RegisterRequest
import androidx.compose.runtime.livedata.observeAsState
object LifePillColors {
    val Primary = Color(0xFF007bff)
    val Background = Color(0xFF1E1E1E)
    val Surface = Color(0xFF16213E)
    val OnSurface = Color(0xFFB8BCC8)
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
    authViewModel: AuthViewModel,
    onForgotPasswordClick: () -> Unit = {},
    onLoginSuccess: (String) -> Unit = {},
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
                    authViewModel = authViewModel,
                    onForgotPasswordClick = onForgotPasswordClick,
                    onLoginSuccess = onLoginSuccess,
                    onNavigateToRegister = onNavigateToRegister
                )
            } else {
                CreateAccountContent(authViewModel = authViewModel)
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
    authViewModel: AuthViewModel,
    onForgotPasswordClick: () -> Unit = {},
    onLoginSuccess: (String) -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    onGoogleLoginClick: () -> Unit = {}
) {
    var emailField by remember { mutableStateOf(FormField()) }
    var passwordField by remember { mutableStateOf(FormField()) }
    var isLoading by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Observar resultados del ViewModel
    val authResult by authViewModel.authResult.observeAsState()
    val error by authViewModel.error.observeAsState()

    // Manejar respuestas del servidor
    LaunchedEffect(key1 = authResult) {
        authResult?.let { response ->
            isLoading = false
            if (response.success) {
                val role = response.role ?: "user"  // Default a "user" si es null
                onLoginSuccess(role)
            } else {
                errorMessage = response.message ?: "Error al iniciar sesión"
            }
        }
    }

    LaunchedEffect(error) {
        error?.let {
            isLoading = false
            errorMessage = it
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Mostrar mensaje de error si existe
        errorMessage?.let { msg ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LifePillColors.Error.copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = LifePillColors.Error,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = msg,
                        color = LifePillColors.Error,
                        fontSize = 14.sp
                    )
                }
            }
        }

        OutlinedTextField(
            value = emailField.value,
            onValueChange = { newValue ->
                errorMessage = null
                val validation = validateEmail(newValue)
                emailField = emailField.copy(
                    value = newValue,
                    error = validation.errorMessage,
                    isValid = validation.isValid
                )
            },
            label = { Text("Correo Electrónico", color = LifePillColors.OnSurfaceVariant) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    tint = LifePillColors.Primary
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(12.dp),
            isError = !emailField.isValid && emailField.error.isNotEmpty(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = LifePillColors.Surface,
                unfocusedContainerColor = LifePillColors.Surface,
                focusedBorderColor = LifePillColors.Primary,
                unfocusedBorderColor = LifePillColors.OnSurfaceVariant,
                focusedTextColor = LifePillColors.OnSurface,
                unfocusedTextColor = LifePillColors.OnSurface,
                cursorColor = LifePillColors.Primary,
                focusedLabelColor = LifePillColors.Primary,
                unfocusedLabelColor = LifePillColors.OnSurfaceVariant,
                errorTextColor = LifePillColors.OnSurface
            )
        )

        if (!emailField.isValid && emailField.error.isNotEmpty()) {
            Text(
                text = emailField.error,
                color = LifePillColors.Error,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = passwordField.value,
            onValueChange = { newValue ->
                errorMessage = null
                passwordField = passwordField.copy(value = newValue, error = "", isValid = true)
            },
            label = { Text("Contraseña", color = LifePillColors.OnSurfaceVariant) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Contraseña",
                    tint = LifePillColors.Primary
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {

                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(12.dp),
            isError = !passwordField.isValid && passwordField.error.isNotEmpty(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = LifePillColors.Surface,
                unfocusedContainerColor = LifePillColors.Surface,
                focusedBorderColor = LifePillColors.Primary,
                unfocusedBorderColor = LifePillColors.OnSurfaceVariant,
                focusedTextColor = LifePillColors.OnSurface,
                unfocusedTextColor = LifePillColors.OnSurface,
                cursorColor = LifePillColors.Primary,
                focusedLabelColor = LifePillColors.Primary,
                unfocusedLabelColor = LifePillColors.OnSurfaceVariant,
                errorTextColor = LifePillColors.OnSurface
            )
        )

        if (!passwordField.isValid && passwordField.error.isNotEmpty()) {
            Text(
                text = passwordField.error,
                color = LifePillColors.Error,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 4.dp)
            )
        }

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
                    errorMessage = null
                    authViewModel.login(
                        correo = emailField.value,
                        contraseña = passwordField.value
                    )
                }
            },
            enabled = !isLoading && emailField.value.isNotEmpty() && passwordField.value.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onGoogleLoginClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color.LightGray),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(50.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.screen6),
                contentDescription = "Google logo",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Iniciar sesión con Google",
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "¿Olvidaste tu contraseña?",
            color = LifePillColors.Primary,
            fontSize = 14.sp,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable { onForgotPasswordClick() }
        )

        Spacer(modifier = Modifier.height(24.dp))
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
fun CreateAccountContent(authViewModel: AuthViewModel) {
    var currentStep by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()
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
    var isCreatingAccount by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val authResult by authViewModel.authResult.observeAsState()
    val error by authViewModel.error.observeAsState()

    LaunchedEffect(authResult) {
        authResult?.let { response ->
            isCreatingAccount = false
            if (response.success) {
                showSuccessDialog = true
            } else {
                errorMessage = response.message ?: "Error al crear la cuenta"
            }
        }
    }

    LaunchedEffect(error) {
        error?.let {
            isCreatingAccount = false
            errorMessage = it
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("¡Cuenta creada!") },
            text = { Text("Tu cuenta ha sido creada exitosamente. Ahora puedes iniciar sesión.") },
            confirmButton = {
                TextButton(onClick = { showSuccessDialog = false }) {
                    Text("Aceptar")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        errorMessage?.let { msg ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LifePillColors.Error.copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = LifePillColors.Error,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = msg,
                        color = LifePillColors.Error,
                        fontSize = 14.sp
                    )
                }
            }
        }

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
                onBirthDateChange = { birthDate = it },
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
            isLoading = isCreatingAccount,
            onPrevious = { if (currentStep > 0) currentStep-- },
            onNext = { if (currentStep < 3) currentStep++ },
            onFinish = {
                val isValid = documentNumber.isValid && documentNumber.value.isNotEmpty() &&
                        firstName.isValid && firstName.value.isNotEmpty() &&
                        lastName.isValid && lastName.value.isNotEmpty() &&
                        username.isValid && username.value.isNotEmpty() &&
                        birthDate.isNotEmpty() &&
                        selectedGender.isNotEmpty() &&
                        phone.isValid && phone.value.isNotEmpty() &&
                        email.isValid && email.value.isNotEmpty() &&
                        password.isValid && password.value.isNotEmpty()

                if (isValid) {
                    isCreatingAccount = true
                    errorMessage = null

                    val registerRequest = RegisterRequest(
                        documentType = documentType,
                        documentNumber = documentNumber.value,
                        firstName = firstName.value,
                        lastName = lastName.value,
                        username = username.value,
                        birthDate = birthDate,
                        gender = selectedGender,
                        phone = phone.value,
                        address = address,
                        email = email.value,
                        password = password.value,
                        age = age.ifEmpty { "0" },
                        weight = weight.ifEmpty { "0" },
                        height = height.ifEmpty { "0" },
                        workMode = selectedWorkMode.ifEmpty { "No especificado" }
                    )

                    authViewModel.register(registerRequest)
                } else {
                    errorMessage = "Por favor completa todos los campos obligatorios"
                }
            }
        )
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
    Column {
        SectionTitle("Información de Documento")
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = documentNumber.value,
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
            label = { Text("Número de Documento", color = LifePillColors.OnSurfaceVariant) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Documento",
                    tint = LifePillColors.Primary
                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = if (documentType in listOf("CC", "TI", "CE")) KeyboardType.Number else KeyboardType.Text),
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

@Composable
private fun PersonalInfoStep(
    firstName: FormField,
    onFirstNameChange: (FormField) -> Unit,
    lastName: FormField,
    onLastNameChange: (FormField) -> Unit,
    username: FormField,
    onUsernameChange: (FormField) -> Unit,
    birthDate: String,
    onBirthDateChange: (String) -> Unit,
    selectedGender: String,
    onGenderChange: (String) -> Unit,
) {
    Column {
        SectionTitle("Información Personal")
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = firstName.value,
            onValueChange = { value ->
                val filteredValue = value.filter { it.isLetter() || it.isWhitespace() }
                val validation = validateName(filteredValue)
                onFirstNameChange(FormField(value = filteredValue, error = validation.errorMessage, isValid = validation.isValid))
            },
            label = { Text("Nombre", color = LifePillColors.OnSurfaceVariant) },
            leadingIcon = { Icon(Icons.Default.Person, "Nombre", tint = LifePillColors.Primary) },
            modifier = Modifier.fillMaxWidth(),
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

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = lastName.value,
            onValueChange = { value ->
                val filteredValue = value.filter { it.isLetter() || it.isWhitespace() }
                val validation = validateName(filteredValue)
                onLastNameChange(FormField(value = filteredValue, error = validation.errorMessage, isValid = validation.isValid))
            },
            label = { Text("Apellido", color = LifePillColors.OnSurfaceVariant) },
            leadingIcon = { Icon(Icons.Default.Person, "Apellido", tint = LifePillColors.Primary) },
            modifier = Modifier.fillMaxWidth(),
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

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username.value,
            onValueChange = { value ->
                val filteredValue = value.filter { it.isLetterOrDigit() || it == '_' || it == '.' }
                val validation = validateUsername(filteredValue)
                onUsernameChange(FormField(value = filteredValue, error = validation.errorMessage, isValid = validation.isValid))
            },
            label = { Text("Nombre de Usuario", color = LifePillColors.OnSurfaceVariant) },
            leadingIcon = { Icon(Icons.Default.AccountCircle, "Usuario", tint = LifePillColors.Primary) },
            modifier = Modifier.fillMaxWidth(),
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

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = birthDate,
            onValueChange = { value ->
                val digitsOnly = value.filter { it.isDigit() }
                if (digitsOnly.length <= 8) {
                    var isValid = true
                    if (digitsOnly.length >= 2) {
                        val day = digitsOnly.substring(0, 2).toIntOrNull() ?: 0
                        if (day > 31 || day == 0) isValid = false
                    }
                    if (digitsOnly.length >= 4) {
                        val month = digitsOnly.substring(2, 4).toIntOrNull() ?: 0
                        if (month > 12 || month == 0) isValid = false
                    }
                    if (digitsOnly.length == 8) {
                        val year = digitsOnly.substring(4, 8).toIntOrNull() ?: 0
                        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                        if (year > currentYear) isValid = false
                    }

                    if (isValid) {
                        val formatted = when {
                            digitsOnly.length <= 2 -> digitsOnly
                            digitsOnly.length <= 4 -> digitsOnly.substring(0, 2) + "/" + digitsOnly.substring(2)
                            else -> digitsOnly.substring(0, 2) + "/" + digitsOnly.substring(2, 4) + "/" + digitsOnly.substring(4)
                        }
                        onBirthDateChange(formatted)
                    }
                }
            },
            label = { Text("Fecha de Nacimiento (dd/mm/yyyy)", color = LifePillColors.OnSurfaceVariant) },
            placeholder = { Text("dd/mm/yyyy", color = LifePillColors.OnSurfaceVariant.copy(alpha = 0.6f)) },
            leadingIcon = { Icon(Icons.Default.DateRange, "Fecha", tint = LifePillColors.Primary) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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

        Spacer(modifier = Modifier.height(24.dp))

        GenderSelector(selectedGender = selectedGender, onGenderSelected = onGenderChange)
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
    onPasswordChange: (FormField) -> Unit,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column {
        SectionTitle("Información de Contacto")
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = phone.value,
            onValueChange = { value ->
                val digitsOnly = value.filter { it.isDigit() }
                if (digitsOnly.length <= 10) {
                    val validation = validatePhone(digitsOnly)
                    onPhoneChange(FormField(value = digitsOnly, error = validation.errorMessage, isValid = validation.isValid))
                }
            },
            label = { Text("Teléfono", color = LifePillColors.OnSurfaceVariant) },
            leadingIcon = { Icon(Icons.Default.Phone, "Teléfono", tint = LifePillColors.Primary) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            shape = RoundedCornerShape(12.dp),
            isError = !phone.isValid && phone.error.isNotEmpty(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = LifePillColors.Surface,
                unfocusedContainerColor = LifePillColors.Surface,
                focusedBorderColor = LifePillColors.Primary,
                unfocusedBorderColor = LifePillColors.OnSurfaceVariant,
                focusedTextColor = LifePillColors.OnSurface,
                unfocusedTextColor = LifePillColors.OnSurface,
                errorTextColor = LifePillColors.OnSurface
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = address,
            onValueChange = { onAddressChange(it) },
            label = { Text("Dirección", color = LifePillColors.OnSurfaceVariant) },
            leadingIcon = { Icon(Icons.Default.Home, "Dirección", tint = LifePillColors.Primary) },
            modifier = Modifier.fillMaxWidth(),
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

        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle("Credenciales de Acceso")
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { value ->
                val validation = validateEmail(value)
                onEmailChange(FormField(value = value, error = validation.errorMessage, isValid = validation.isValid))
            },
            label = { Text("Correo Electrónico", color = LifePillColors.OnSurfaceVariant) },
            leadingIcon = { Icon(Icons.Default.Email, "Email", tint = LifePillColors.Primary) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(12.dp),
            isError = !email.isValid && email.error.isNotEmpty(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = LifePillColors.Surface,
                unfocusedContainerColor = LifePillColors.Surface,
                focusedBorderColor = LifePillColors.Primary,
                unfocusedBorderColor = LifePillColors.OnSurfaceVariant,
                focusedTextColor = LifePillColors.OnSurface,
                unfocusedTextColor = LifePillColors.OnSurface,
                errorTextColor = LifePillColors.OnSurface
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { value ->
                val validation = validatePassword(value)
                onPasswordChange(FormField(value = value, error = validation.errorMessage, isValid = validation.isValid))
            },
            label = { Text("Contraseña", color = LifePillColors.OnSurfaceVariant) },
            leadingIcon = { Icon(Icons.Default.Lock, "Contraseña", tint = LifePillColors.Primary) },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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

@Composable
private fun PhysicalInfoStep(
    age: String,
    onAgeChange: (String) -> Unit,
    weight: String,
    onWeightChange: (String) -> Unit,
    height: String,
    onHeightChange: (String) -> Unit,
    selectedWorkMode: String,
    onWorkModeChange: (String) -> Unit
) {
    Column {
        SectionTitle("Información Física (Opcional)")
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = age,
                onValueChange = { value ->
                    val filteredValue = value.filter { it.isDigit() }
                    if (filteredValue.length <= 2) {
                        onAgeChange(filteredValue)
                    }
                },
                label = { Text("Edad", color = LifePillColors.OnSurfaceVariant) },
                leadingIcon = { Icon(Icons.Default.Face, "Edad", tint = LifePillColors.Primary) },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp),
                isError = age.isNotEmpty() && !validateAge(age).isValid,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = LifePillColors.Surface,
                    unfocusedContainerColor = LifePillColors.Surface,
                    focusedBorderColor = LifePillColors.Primary,
                    unfocusedBorderColor = LifePillColors.OnSurfaceVariant,
                    focusedTextColor = LifePillColors.OnSurface,
                    unfocusedTextColor = LifePillColors.OnSurface,
                    errorTextColor = LifePillColors.OnSurface
                )
            )

            OutlinedTextField(
                value = weight,
                onValueChange = { value ->
                    val filteredValue = value.filter { it.isDigit() }
                    if (filteredValue.length <= 3) {
                        onWeightChange(filteredValue)
                    }
                },
                label = { Text("Peso (kg)", color = LifePillColors.OnSurfaceVariant) },
                leadingIcon = { Icon(Icons.Default.FavoriteBorder, "Peso", tint = LifePillColors.Primary) },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp),
                isError = weight.isNotEmpty() && !validateWeight(weight).isValid,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = LifePillColors.Surface,
                    unfocusedContainerColor = LifePillColors.Surface,
                    focusedBorderColor = LifePillColors.Primary,
                    unfocusedBorderColor = LifePillColors.OnSurfaceVariant,
                    focusedTextColor = LifePillColors.OnSurface,
                    unfocusedTextColor = LifePillColors.OnSurface,
                    errorTextColor = LifePillColors.OnSurface
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = height,
            onValueChange = { value ->
                val filteredValue = value.filter { it.isDigit() }
                if (filteredValue.length <= 3) {
                    onHeightChange(filteredValue)
                }
            },
            label = { Text("Estatura (cm)", color = LifePillColors.OnSurfaceVariant) },
            leadingIcon = { Icon(Icons.Default.Info, "Estatura", tint = LifePillColors.Primary) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(12.dp),
            isError = height.isNotEmpty() && !validateHeight(height).isValid,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = LifePillColors.Surface,
                unfocusedContainerColor = LifePillColors.Surface,
                focusedBorderColor = LifePillColors.Primary,
                unfocusedBorderColor = LifePillColors.OnSurfaceVariant,
                focusedTextColor = LifePillColors.OnSurface,
                unfocusedTextColor = LifePillColors.OnSurface,
                errorTextColor = LifePillColors.OnSurface
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        WorkModeSelector(
            selectedWorkMode = selectedWorkMode,
            onWorkModeSelected = onWorkModeChange
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
    isLoading: Boolean = false,
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
                enabled = !isLoading,
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = LifePillColors.Primary
                ),
                border = BorderStroke(2.dp, LifePillColors.Primary)
            ) {
                Text(
                    text = "Anterior",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        OnBoardingStyleButton(
            text = when {
                isLoading -> "Creando cuenta..."
                currentStep == totalSteps - 1 -> "Crear Cuenta"
                else -> "Siguiente"
            },
            onClick = if (currentStep == totalSteps - 1) onFinish else onNext,
            enabled = !isLoading,
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

private fun validateEmail(email: String): ValidationResult {
    return when {
        email.isEmpty() -> ValidationResult(false, "El correo es obligatorio")
        !email.contains("@") -> ValidationResult(false, "El correo debe contener @")
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
                number.length > 15 -> ValidationResult(false, "Máximo 15 dígitos")
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
        digitsOnly.length != 10 -> ValidationResult(false, "Debe tener exactamente 10 dígitos")
        else -> ValidationResult(true)
    }
}

private fun validateAge(age: String): ValidationResult {
    return when {
        age.isEmpty() -> ValidationResult(true)
        age.length > 3 -> ValidationResult(false, "Máximo 3 dígitos")
        age.toIntOrNull() == null -> ValidationResult(false, "Solo números")
        age.toInt() < 1 || age.toInt() > 150 -> ValidationResult(false, "Edad inválida")
        else -> ValidationResult(true)
    }
}

private fun validateWeight(weight: String): ValidationResult {
    return when {
        weight.isEmpty() -> ValidationResult(true)
        weight.length > 3 -> ValidationResult(false, "Máximo 3 dígitos")
        weight.toIntOrNull() == null -> ValidationResult(false, "Solo números")
        weight.toInt() < 1 || weight.toInt() > 500 -> ValidationResult(false, "Peso inválido")
        else -> ValidationResult(true)
    }
}

private fun validateHeight(height: String): ValidationResult {
    return when {
        height.isEmpty() -> ValidationResult(true)
        height.length > 3 -> ValidationResult(false, "Máximo 3 dígitos")
        height.toIntOrNull() == null -> ValidationResult(false, "Solo números")
        height.toInt() < 50 || height.toInt() > 250 -> ValidationResult(false, "Altura inválida")
        else -> ValidationResult(true)
    }
}