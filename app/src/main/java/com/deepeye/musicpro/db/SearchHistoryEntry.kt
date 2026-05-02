package com.deepeye.musicpro.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistoryEntry(
    @PrimaryKey val query: String,
    val lastSearchedAt: Long,
    val hitCount: Int
)
