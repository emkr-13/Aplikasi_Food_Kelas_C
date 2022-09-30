package com.example.food.user

import androidx.room.*


@Dao
interface MakananDAO {

    @Insert
    suspend fun addMakan(makanan: Makanan)
    @Update
    suspend fun updateMakan(makanan: Makanan)
    @Delete
    suspend fun deleteMakan(makanan: Makanan)
    @Query("SELECT * FROM makanan")
    suspend fun getMakan() : List<Makanan>
    @Query("SELECT * FROM makanan Where id=:id_makan")
    suspend fun getDataMakan(id_makan: Int): List<Makanan>
}