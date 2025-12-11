package com.escuelaing.edu.lifepill.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // Microservicio de Autenticación (puerto 3000)
    private const val AUTH_BASE_URL = "http://44.210.0.255:8086/"

    // Microservicio de Comidas (puerto 8087)
    private const val FOOD_BASE_URL = "http://54.156.203.198:8087/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    // Retrofit para el microservicio de autenticación
    private val authRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(AUTH_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Retrofit para el microservicio de comidas
    private val foodRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(FOOD_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // APIs
    val authApi: AuthApi = authRetrofit.create(AuthApi::class.java)
    val foodApi: FoodApi = foodRetrofit.create(FoodApi::class.java)
}