package com.example.roomcrud.dao

import kotlinx.coroutines.flow.Flow

class ClienteRepository(private val clienteDao: ClienteDao) {
    val clientes: Flow<List<Cliente>> = clienteDao.obtenerClientes()

    suspend fun insertar(cliente: Cliente) = clienteDao.insertarCliente(cliente)
    suspend fun actualizar(cliente: Cliente) = clienteDao.actualizarCliente(cliente)
    suspend fun eliminar(cliente: Cliente) = clienteDao.eliminarCliente(cliente)
}
