package com.escuelaing.edu.lifepill.network

import com.google.gson.annotations.SerializedName

data class FoodRequest(
    val tipo: String,  // "desayuno", "almuerzo", "cena", "otro"
    val calorias: Int,
    val descripcion: String?
)

data class FoodResponse(
    @SerializedName("_id")
    val id: String?,
    val tipo: String,
    val calorias: Int,
    val descripcion: String?,
    val userId: String?,
    val createdAt: String?,
    val updatedAt: String?
)