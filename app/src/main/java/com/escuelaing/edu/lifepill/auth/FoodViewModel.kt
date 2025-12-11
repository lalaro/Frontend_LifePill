package com.escuelaing.edu.lifepill.auth

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.escuelaing.edu.lifepill.network.FoodRequest
import com.escuelaing.edu.lifepill.network.FoodResponse
import com.escuelaing.edu.lifepill.network.RetrofitClient
import com.escuelaing.edu.lifepill.repository.FoodRepository
import kotlinx.coroutines.launch

class FoodViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = FoodRepository(RetrofitClient.foodApi)
    val foodResult = MutableLiveData<FoodResponse?>()
    val foodsList = MutableLiveData<List<FoodResponse>>()
    val error = MutableLiveData<String?>()

    companion object {
        private const val TAG = "FoodViewModel"
    }

    fun createFood(token: String, tipo: String, calorias: Int, descripcion: String?) = viewModelScope.launch {
        try {
            // 🔒 VALIDACIÓN DEL TOKEN
            if (token.isBlank()) {
                Log.e(TAG, "❌ Token vacío o inválido")
                error.postValue("Token de autenticación inválido. Por favor inicia sesión nuevamente.")
                return@launch
            }

            // 🔍 LOGS DE DEPURACIÓN
            Log.d(TAG, "🔑 Token recibido (primeros 20 chars): ${token.take(20)}...")
            Log.d(TAG, "🔑 Token length: ${token.length}")
            Log.d(TAG, "🔵 Creando comida: tipo=$tipo, calorias=$calorias")

            val request = FoodRequest(
                tipo = tipo,
                calorias = calorias,
                descripcion = descripcion
            )

            val response = repo.createFood(token, request)

            Log.d(TAG, "📡 Response code: ${response.code()}")
            Log.d(TAG, "📡 Response successful: ${response.isSuccessful}")

            if (response.isSuccessful) {
                val body = response.body()
                Log.d(TAG, "✅ Comida creada exitosamente")
                Log.d(TAG, "✅ ID: ${body?.id}")
                Log.d(TAG, "✅ Tipo: ${body?.tipo}")
                Log.d(TAG, "✅ Calorías: ${body?.calorias}")

                foodResult.postValue(body)
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e(TAG, "❌ Error body completo: $errorBody")

                val errorMsg = when (response.code()) {
                    401 -> "Token inválido o expirado. Por favor inicia sesión nuevamente."
                    403 -> "No tienes permisos para realizar esta acción."
                    else -> if (errorBody != null) {
                        "Error: $errorBody"
                    } else {
                        "Error ${response.code()}: ${response.message()}"
                    }
                }

                Log.e(TAG, "❌ Error al crear comida: $errorMsg")
                error.postValue(errorMsg)
            }
        } catch (e: Exception) {
            Log.e(TAG, "💥 Excepción al crear comida: ${e.message}", e)
            error.postValue("Error de conexión: ${e.message}")
        }
    }

    fun getFoods(token: String) = viewModelScope.launch {
        try {
            // 🔒 VALIDACIÓN DEL TOKEN
            if (token.isBlank()) {
                Log.e(TAG, "❌ Token vacío o inválido")
                error.postValue("Token de autenticación inválido. Por favor inicia sesión nuevamente.")
                return@launch
            }

            // 🔍 LOGS DE DEPURACIÓN
            Log.d(TAG, "🔑 Token para getFoods (primeros 20 chars): ${token.take(20)}...")
            Log.d(TAG, "🔵 Obteniendo lista de comidas")

            val response = repo.getFoods(token)

            Log.d(TAG, "📡 Response code: ${response.code()}")
            Log.d(TAG, "📡 Response successful: ${response.isSuccessful}")

            if (response.isSuccessful) {
                val body = response.body()
                Log.d(TAG, "✅ Lista de comidas obtenida: ${body?.size} elementos")

                foodsList.postValue(body ?: emptyList())
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e(TAG, "❌ Error body completo: $errorBody")

                val errorMsg = when (response.code()) {
                    401 -> "Token inválido o expirado. Por favor inicia sesión nuevamente."
                    403 -> "No tienes permisos para realizar esta acción."
                    else -> if (errorBody != null) {
                        "Error: $errorBody"
                    } else {
                        "Error ${response.code()}: ${response.message()}"
                    }
                }

                Log.e(TAG, "❌ Error al obtener comidas: $errorMsg")
                error.postValue(errorMsg)
            }
        } catch (e: Exception) {
            Log.e(TAG, "💥 Excepción al obtener comidas: ${e.message}", e)
            error.postValue("Error de conexión: ${e.message}")
        }
    }

    // 🧹 Función para limpiar errores
    fun clearError() {
        error.value = null
    }

    // 📊 Calcular calorías totales del día
    fun calculateTodayCalories(): Int {
        val foods = foodsList.value ?: return 0
        return foods.sumOf { it.calorias }
    }
}