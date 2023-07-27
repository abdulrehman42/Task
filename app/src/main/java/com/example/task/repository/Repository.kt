package com.example.task.repository

import androidx.lifecycle.LiveData
import com.example.task.room.Entiti
import com.example.task.room.ListDatabase

class Repository(val db:ListDatabase) {
    suspend fun insert(title:Entiti)=db.get_dao().insertNot(title)
    suspend fun update(date:Entiti)=db.get_dao().updateNot(date)
    suspend fun remove(delete:Entiti)=db.get_dao().deleteNot(delete)
     fun getAllNotes(): LiveData<List<Entiti>> = db.get_dao().getAllNotes()

}