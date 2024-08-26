package com.example.colorsync.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDao {
    @Insert
    suspend fun insertColor(color: ColorEntry): Long

    @Query("SELECT * FROM colors")
    fun getAllColors(): Flow<List<ColorEntry>>

    @Query("SELECT COUNT(*) FROM colors WHERE id = :id")
    suspend fun getPendingSyncCount(id: Int): Int

    @Query("DELETE FROM colors")
    suspend fun clearColors(): Int
}
