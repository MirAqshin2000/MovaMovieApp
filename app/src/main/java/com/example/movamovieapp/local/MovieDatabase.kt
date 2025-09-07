package com.example.movamovieapp.local

import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movamovieapp.model.MyListModel

@Database(entities = [MyListModel::class], version = 1, exportSchema = false)
abstract class MovieDatabase :RoomDatabase(){
    abstract val createMovieDao: MovieDao

}