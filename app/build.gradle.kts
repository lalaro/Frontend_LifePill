plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt") // ⭐ IMPORTANTE para Room
}

android {
    namespace = "com.escuelaing.edu.lifepill"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.escuelaing.edu.lifepill"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    // Configuración de Compose Compiler
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    // Empaquetado
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // ========== CORE ANDROID ==========
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // ========== COMPOSE ==========
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.compose.material:material-icons-extended:1.6.1") // ⭐ Íconos extendidos

    // ========== NAVIGATION ==========
    implementation(libs.androidx.navigation.compose.android)
    implementation("androidx.navigation:navigation-compose:2.7.6")

    // ========== LIFECYCLE & VIEWMODEL ==========
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    // ========== RETROFIT (NETWORKING) ==========
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // ========== OKHTTP ==========
    implementation("com.squareup.okhttp3:okhttp:4.12.0") // ⭐ Actualizado
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0") // ⭐ Actualizado

    // ========== GSON ==========
    implementation("com.google.code.gson:gson:2.10.1") // ⭐ Actualizado

    // ========== COROUTINES ==========
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3") // ⭐ Actualizado
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3") // ⭐ Actualizado

    // ========== ROOM DATABASE ==========
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // ========== SECURITY ==========
    implementation("at.favre.lib:bcrypt:0.10.2") // ⭐ Actualizado

    // ========== DATASTORE (OPCIONAL - Para guardar preferencias) ==========
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // ========== COIL (Para cargar imágenes - OPCIONAL) ==========
    implementation("io.coil-kt:coil-compose:2.5.0")

    // ========== ACCOMPANIST (Utilidades de Compose - OPCIONAL) ==========
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")

    // ========== GOOGLE AUTH (Para Google Sign In - OPCIONAL) ==========
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // ========== TESTING ==========
    // JUnit
    testImplementation("junit:junit:4.13.2")
    testImplementation(libs.junit)

    // Mockito
    testImplementation("org.mockito:mockito-core:5.8.0") // ⭐ Actualizado
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1") // ⭐ Actualizado

    // Coroutines Test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // Android Testing
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Compose Testing
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.1")
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.1")
    debugImplementation(libs.androidx.ui.test.manifest)

    // Navigation Testing
    androidTestImplementation("androidx.navigation:navigation-testing:2.7.6")

    // Debug
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.1")
}
