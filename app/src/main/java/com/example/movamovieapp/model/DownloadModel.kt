package com.example.movamovieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloads")
class DownloadModel (
    val title: String,
    val image: String,
    var selected: Boolean = false,
    @PrimaryKey
    var id: Int
)


