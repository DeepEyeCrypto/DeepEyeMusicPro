package com.deepeye.musicpro.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {
    @Query("SELECT * FROM cached_tracks WHERE isDownloaded = 1 ORDER BY downloadedAt DESC")
    fun observeDownloads(): Flow<List<CachedTrack>>

    @Query("SELECT * FROM cached_tracks ORDER BY lastPlayedAt DESC LIMIT :limit")
    fun observeRecentlyPlayed(limit: Int): Flow<List<CachedTrack>>

    @Query("SELECT * FROM cached_tracks WHERE id = :id")
    suspend fun getById(id: String): CachedTrack?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(track: CachedTrack)

    @Query("UPDATE cached_tracks SET lastPlayedAt = :playedAt, playCount = playCount + 1 WHERE id = :id")
    suspend fun markPlayed(id: String, playedAt: Long)

    @Query("UPDATE cached_tracks SET isDownloaded = 0, localUri = NULL, fileSizeBytes = 0 WHERE id = :id")
    suspend fun clearDownload(id: String)

    @Delete
    suspend fun delete(track: CachedTrack)
}
