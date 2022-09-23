package com.example.food.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM user")
    suspend fun getUsers() : List<User>


    @Query("SELECT * FROM user WHERE id =:user_id")
    suspend fun getUser(user_id: Int) : List<User>
}


