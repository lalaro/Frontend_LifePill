package com.escuelaing.edu.lifepill.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val email: String,
    val name: String? = null,
    val passwordHash: String,
    val role: String? = "user",
    val profile: String? = null
)