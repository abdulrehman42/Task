package com.example.task.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.task.repository.Repository

class viewmodelFactory(private val repository:Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       (modelClass.isAssignableFrom(AddfragmentViewModel::class.java)) {
            return AddfragmentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}