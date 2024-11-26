package com.example.roomcrud.dao

import androidx.room.*

@Dao
interface ClienteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCliente(cliente: Cliente)

    @Update
    suspend fun actualizarCliente(cliente: Cliente)

    @Delete
    suspend fun eliminarCliente(cliente: Cliente)

    @Query("SELECT * FROM clientes ORDER BY id ASC")
    fun obtenerClientes(): kotlinx.coroutines.flow.Flow<List<Cliente>>
}
