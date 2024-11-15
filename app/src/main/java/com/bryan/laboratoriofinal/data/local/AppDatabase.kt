package com.bryan.laboratoriofinal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bryan.laboratoriofinal.data.local.dao.AssetDao
import com.bryan.laboratoriofinal.data.local.entity.AssetEntity

@Database(
    entities = [AssetEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun assetDao(): AssetDao
}
