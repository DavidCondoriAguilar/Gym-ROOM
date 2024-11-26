package com.example.roomcrud.dao

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Cliente::class], version = 1, exportSchema = false)
abstract class AppDatabases : RoomDatabase() {
    abstract fun tuDao(): ClienteDao
}

