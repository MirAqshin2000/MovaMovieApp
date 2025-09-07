package com.example.movamovieapp.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movamovieapp.model.MyListModel

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun addMovie(movie: MyListModel)

@Query("SELECT * FROM movie_table")
suspend fun getAllMovies(): List<MyListModel>


    @Query("SELECT EXISTS(SELECT 1 FROM movie_table WHERE id = :movieId)")
    suspend fun isMovieInList(movieId: Int): Boolean

@Query("DELETE FROM movie_table WHERE id = :movieId")
suspend fun deleteMovie(movieId: Int):Int


}