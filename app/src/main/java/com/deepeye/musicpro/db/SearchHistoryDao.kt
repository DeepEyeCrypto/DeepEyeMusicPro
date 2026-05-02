package com.deepeye.musicpro.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM search_history ORDER BY lastSearchedAt DESC LIMIT :limit")
    fun observeRecent(limit: Int): Flow<List<SearchHistoryEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entry: SearchHistoryEntry)

    @Query("SELECT * FROM search_history WHERE query = :query")
    suspend fun get(query: String): SearchHistoryEntry?

    @Query("DELETE FROM search_history")
    suspend fun clear()
}
