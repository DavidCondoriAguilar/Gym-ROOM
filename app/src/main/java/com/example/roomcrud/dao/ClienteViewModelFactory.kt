package com.example.roomcrud.dao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ClienteViewModelFactory(private val repository: ClienteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClienteViewModel::class.java)) {
            return ClienteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
