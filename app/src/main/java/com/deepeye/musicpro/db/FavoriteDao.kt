package com.deepeye.musicpro.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_tracks ORDER BY addedAt DESC")
    fun observeFavorites(): Flow<List<FavoriteTrack>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_tracks WHERE id = :id)")
    fun observeIsFavorite(id: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(track: FavoriteTrack)

    @Query("DELETE FROM favorite_tracks WHERE id = :id")
    suspend fun deleteById(id: String)

    @Delete
    suspend fun delete(track: FavoriteTrack)
}
