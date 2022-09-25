package com.example.food.user

import androidx.room.*


@Dao
interface MakananDAO {

    @Insert
    suspend fun addUser(makanan: Makanan)
    @Update
    suspend fun updateUser(makanan: Makanan)
    @Delete
    suspend fun deleteUser(makanan: Makanan)
}