package com.example.food.user


import androidx.room.*

@Dao
interface UserDao {

    @Insert
    suspend fun addUser(user: User)
    @Update
    suspend fun updateUser(user: User)
    @Delete
    suspend fun deleteUser(user: User)

}