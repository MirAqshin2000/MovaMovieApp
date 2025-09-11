package com.example.movamovieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")

data class CardModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cardName: String,
    val cardNumber: String,
    val cardImage: Int,
    var selected: Boolean,



    )
