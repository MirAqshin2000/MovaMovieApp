package com.example.movamovieapp.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movamovieapp.model.DownloadModel
import kotlinx.coroutines.flow.Flow

@Dao
interface DownloadDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDownload(download: DownloadModel)

    @Query("SELECT * FROM downloads")
     suspend fun getAllDownloads(): List<DownloadModel>

    @Query("DELETE FROM downloads WHERE id = :downloadId")
    suspend fun deleteDownload(downloadId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM downloads WHERE id = :downloadId)")
    suspend fun isDownloadInList(downloadId: Int): Boolean



}