package com.example.food.user

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val user: String,
    val password: String,
    val nomorHP: String,
    val tanggalLahir: Int,

        )