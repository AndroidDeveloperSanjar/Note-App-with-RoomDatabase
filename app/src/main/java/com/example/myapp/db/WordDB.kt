package com.example.myapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Word::class],
    version = 2,
    exportSchema = false
)
abstract class WordDB : RoomDatabase(){

    abstract fun getWordDao() : WordDao

    companion object{

        @Volatile
        private var instance : WordDB? = null
        private val LOCK = Any()

        operator fun invoke(
            context: Context
        ) = instance ?: synchronized(LOCK){
            instance ?: buildDB(context).also {
                instance = it
            }
        }

        private fun buildDB(
            context: Context
        ) = Room.databaseBuilder(
            context.applicationContext,
            WordDB::class.java,
            "wordDB"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}