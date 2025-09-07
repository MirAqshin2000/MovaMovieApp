package com.example.movamovieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class MyListModel(
    val title: String,
    val image: String,
    var selected: Boolean = false,
    @PrimaryKey
    var id: Int

)