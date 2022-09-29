package com.example.food.user

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Makanan :: class],
    version = 1
)

abstract class MakananDB : RoomDatabase() {
    abstract fun makananDao() : MakananDAO
    companion object {

        @Volatile private var instance : MakananDB? = null

        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MakananDB::class.java,
                "user.db"
            ).build()
    }
}