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
    @Query("SELECT * FROM user")
    suspend fun getUser() : List<User>
    @Query("SELECT * FROM user Where id=:id_user")
    suspend fun getDataUser(id_user: Int): List<User>
}