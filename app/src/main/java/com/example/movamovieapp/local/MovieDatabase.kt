package com.example.movamovieapp.local

import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movamovieapp.model.CardModel
import com.example.movamovieapp.model.MyListModel

@Database(entities = [MyListModel::class, CardModel::class], version = 3)


abstract class MovieDatabase :RoomDatabase(){
    abstract val createMovieDao: MovieDao
    abstract val createCardDao: CardDao



}

