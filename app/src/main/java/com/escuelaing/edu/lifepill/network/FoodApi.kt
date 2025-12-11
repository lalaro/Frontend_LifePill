package com.escuelaing.edu.lifepill.network

import retrofit2.Response
import retrofit2.http.*

interface FoodApi {
    @GET("api/foods")
    suspend fun getFoods(
        @Header("Authorization") token: String
    ): Response<List<FoodResponse>>

    @POST("api/foods")
    suspend fun createFood(
        @Header("Authorization") token: String,
        @Body body: FoodRequest
    ): Response<FoodResponse>
}