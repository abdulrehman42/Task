package com.example.task.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Entiti::class], version = 1, exportSchema = false)
abstract class ListDatabase: RoomDatabase() {
    abstract fun get_dao(): Dao
    companion object {
        private const val DB_NAME = "note_database.db"
        @Volatile
        private var instance: ListDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ListDatabase::class.java,
            DB_NAME
        ).allowMainThreadQueries().build()
    }
}