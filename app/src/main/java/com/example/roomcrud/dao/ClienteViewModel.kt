package com.example.roomcrud.dao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ClienteViewModel(private val repository: ClienteRepository) : ViewModel() {
    val clientes = repository.clientes

    fun agregarCliente(cliente: Cliente) {
        viewModelScope.launch { repository.insertar(cliente) }
    }

    fun actualizarCliente(cliente: Cliente) {
        viewModelScope.launch { repository.actualizar(cliente) }
    }

    fun eliminarCliente(cliente: Cliente) {
        viewModelScope.launch { repository.eliminar(cliente) }
    }
}
