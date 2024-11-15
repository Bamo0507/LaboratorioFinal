package com.bryan.laboratoriofinal.data.local

import android.content.Context
import androidx.room.Room

object DatabaseModule {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun provideDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "monsters_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
