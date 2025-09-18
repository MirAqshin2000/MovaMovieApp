package com.example.movamovieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloads")

data class DownloadModel (
    @PrimaryKey

    val title: String,
    val image: String,
    var selected: Boolean = false,
    var id: Int
)


