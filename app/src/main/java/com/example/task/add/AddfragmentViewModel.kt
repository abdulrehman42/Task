package com.example.task.add

import androidx.lifecycle.ViewModel
import com.example.task.repository.Repository
import com.example.task.room.Entiti

class AddfragmentViewModel(val rep:Repository):ViewModel() {
    suspend fun insertNote(emp: Entiti)=  rep.insert(emp)
    suspend fun updateNote(emp: Entiti) = rep.update(emp)
    suspend fun deleteNote(emp: Entiti) = rep.remove(emp)
     fun getAllNotes() = rep.getAllNotes()
}