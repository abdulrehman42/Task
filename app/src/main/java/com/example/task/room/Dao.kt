package com.example.task.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNot(entiti: Entiti)
    @Update
    suspend fun updateNot(entiti: Entiti)
    @Delete
    suspend fun deleteNot(entiti: Entiti)
    @Query("SELECT  *FROM record order by id asc")
     fun getAllNotes(): LiveData<List<Entiti>>
}