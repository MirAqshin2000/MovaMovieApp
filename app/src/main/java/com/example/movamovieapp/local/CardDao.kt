package com.example.movamovieapp.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movamovieapp.model.CardModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCard(card: CardModel)

    @Query("SELECT * FROM cards")
     fun getAllCards(): Flow<List<CardModel>>

    @Query("DELETE FROM cards WHERE id = :cardId")
    suspend fun deleteCard(cardId: Int): Int

}
