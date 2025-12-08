// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.devtools.ksp") version "1.9.20-1.0.14" apply false // ⭐ KSP (alternativa a kapt)
    id("org.jetbrains.kotlin.kapt") version "1.9.20" apply false // ⭐ KAPT para Room
}

// Configuración opcional pero recomendada
allprojects {
    // Configuración global si la necesitas
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}