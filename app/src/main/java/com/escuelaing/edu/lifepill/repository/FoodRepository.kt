package com.escuelaing.edu.lifepill.repository

import com.escuelaing.edu.lifepill.network.FoodApi
import com.escuelaing.edu.lifepill.network.FoodRequest

class FoodRepository(private val api: FoodApi) {
    suspend fun getFoods(token: String) = api.getFoods("Bearer $token")

    suspend fun createFood(token: String, req: FoodRequest) =
        api.createFood("Bearer $token", req)
}