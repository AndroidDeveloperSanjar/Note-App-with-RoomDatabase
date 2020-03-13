package com.example.myapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.DateFormat
import java.util.*

@Entity
data class Word(

    val engWord: String,

    val translateWord: String,

    val writedTime: String = DateFormat.getDateTimeInstance().format(Date())

): Serializable {

    @PrimaryKey(autoGenerate = true)

    var id: Int = 0

}
