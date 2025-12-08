package com.escuelaing.edu.lifepill.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    // ⭐ CONFIGURACIÓN IMPORTANTE ⭐
    // Para EMULADOR de Android Studio: usa "http://10.0.2.2:8085/"
    // Para DISPOSITIVO FÍSICO: usa "http://TU_IP_LOCAL:8085/"
    //    - Windows: Abre CMD y escribe: ipconfig
    //    - Mac/Linux: Terminal y escribe: ifconfig
    //    - Busca tu IPv4 (ej: 192.168.1.100)

    private const val BASE_URL = "http://10.0.2.2:8086/"  // ← Para emulador
    // private const val BASE_URL = "http://192.168.56.1:8085/"  // ← Para dispositivo físico (descomenta y usa tu IP)

    // Logging interceptor para ver las peticiones en Logcat
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Muestra todo: headers, body, etc.
    }

    // Cliente HTTP con timeouts y logging
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)  // Para debugging
        .connectTimeout(30, TimeUnit.SECONDS)  // Timeout de conexión
        .readTimeout(30, TimeUnit.SECONDS)     // Timeout de lectura
        .writeTimeout(30, TimeUnit.SECONDS)    // Timeout de escritura
        .build()

    // Retrofit instance
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)  // ⚠️ Solo la URL base, SIN /api/auth
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // API interface
    val authApi: AuthApi = retrofit.create(AuthApi::class.java)
}