package com.escuelaing.edu.lifepill.network

data class RegisterRequest(
    val documentType: String,
    val documentNumber: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val birthDate: String,
    val gender: String,
    val phone: String,
    val address: String,
    val email: String,
    val password: String,
    val age: String,
    val weight: String,
    val height: String,
    val workMode: String
)