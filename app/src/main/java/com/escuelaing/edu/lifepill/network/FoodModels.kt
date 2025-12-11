package com.escuelaing.edu.lifepill.network

import com.google.gson.annotations.SerializedName

data class FoodModels(
    val id: String,
    val tipo: String,
    val calorias: Int,
    val descripcion: String,
    val fechaFormateada: String  // "10 Dic 2024, 2:30 PM"
)

// Función de extensión para convertir Response a modelo UI
fun FoodResponse.toFood(): FoodModels {
    return FoodModels(
        id = this.id ?: "",
        tipo = this.tipo,
        calorias = this.calorias,
        descripcion = this.descripcion ?: "",
        fechaFormateada = formatDate(this.createdAt)
    )
}

private fun formatDate(isoDate: String?): String {
    // Lógica para formatear fecha ISO a formato legible
    return isoDate ?: "Fecha desconocida"
}