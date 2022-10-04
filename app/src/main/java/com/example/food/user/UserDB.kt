package com.example.food.user

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi

@Database(
    entities = [User::class, Makanan :: class],
    version = 3
)

abstract class UserDB: RoomDatabase() {

    abstract fun userDao() : UserDao
    abstract fun makananDao() : MakananDAO

    companion object
    {
        @Volatile private var instance : UserDB? = null
        private val LOCK = Any()


        @OptIn(InternalCoroutinesApi::class)
        operator fun invoke(context: Context) = instance ?: kotlinx.coroutines.internal.synchronized(
            LOCK
        ) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, UserDB::class.java,"tubes.db"
        ).fallbackToDestructiveMigration().build()
    }
}