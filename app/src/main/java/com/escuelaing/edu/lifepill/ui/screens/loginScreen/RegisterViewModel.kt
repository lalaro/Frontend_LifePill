package com.escuelaing.edu.lifepill.ui.screens.loginScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.escuelaing.edu.lifepill.model.RegistrationData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegisterViewModel : ViewModel() {
    // Documento
    var documentType by mutableStateOf("CC")
    var documentNumber by mutableStateOf(FormField())

    // Información Personal
    var firstName by mutableStateOf(FormField())
    var lastName by mutableStateOf(FormField())
    var username by mutableStateOf(FormField())
    var birthDate by mutableStateOf("")
    var selectedGender by mutableStateOf("")

    // Información de Contacto
    var phone by mutableStateOf(FormField())
    var address by mutableStateOf("")
    var email by mutableStateOf(FormField())
    var password by mutableStateOf(FormField())

    // Información Física
    var age by mutableStateOf("")
    var weight by mutableStateOf("")
    var height by mutableStateOf("")
    var selectedWorkMode by mutableStateOf("")

    // Registro guardado localmente en memoria
    private val _registrationData = MutableStateFlow<RegistrationData?>(null)
    val registrationData: StateFlow<RegistrationData?> = _registrationData

    fun buildRegistrationData(): RegistrationData = RegistrationData(
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
        age = age,
        weight = weight,
        height = height,
        workMode = selectedWorkMode
    )

    fun saveToLocal() {
        _registrationData.value = buildRegistrationData()
    }

    suspend fun submitToBackend(/* repo o api */) {
        val data = buildRegistrationData()
        // enviar data al backend
    }
}