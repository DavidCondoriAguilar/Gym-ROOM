package com.example.roomcrud.dao

import android.content.Context
import androidx.room.Room

object DataBaseProvider {
    private var INSTANCE: AppDatabase? = null

    // Función para obtener la instancia de la base de datos
    fun getDataBase(context: Context): AppDatabase {
        // Si la instancia ya existe, la retorna, si no, la crea
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "cliente_database" // El nombre de tu base de datos
            ).fallbackToDestructiveMigration() // Este método permite manejar migraciones (puedes modificarlo si lo necesitas)
                .build()
            INSTANCE = instance
            instance
        }
    }
}
