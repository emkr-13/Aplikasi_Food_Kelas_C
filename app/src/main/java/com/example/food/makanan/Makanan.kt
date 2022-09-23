package com.example.food.makanan
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Makanan (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val harga: Float,
        )