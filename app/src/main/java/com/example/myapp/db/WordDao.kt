package com.example.myapp.db

import androidx.room.*

@Dao
interface WordDao {

    @Insert
    suspend fun addWord(word: Word)

    @Query("SELECT * FROM word ORDER BY id DESC")
    suspend fun getAllWords() : MutableList<Word>

    @Insert
    suspend fun addMultipleWords(vararg word: Word)

    @Update
    suspend fun updateWord(word: Word)

    @Delete
    suspend fun deleteWord(word: Word)

}