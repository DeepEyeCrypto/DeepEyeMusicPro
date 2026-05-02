package com.deepeye.musicpro.repository

import com.deepeye.musicpro.db.AppDatabase
import com.deepeye.musicpro.db.CachedTrack
import com.deepeye.musicpro.db.SearchHistoryEntry
import com.deepeye.musicpro.extractor.SearchService
import com.deepeye.musicpro.extractor.StreamExtractor
import com.deepeye.musicpro.model.SearchResult
import com.deepeye.musicpro.model.StreamExtractionResult
import kotlinx.coroutines.flow.Flow

class SearchRepository(
    private val database: AppDatabase,
    private val searchService: SearchService,
    private val streamExtractor: StreamExtractor
) {
    fun recentSearches(): Flow<List<SearchHistoryEntry>> = database.searchHistoryDao().observeRecent(12)

    suspend fun search(query: String): Result<List<SearchResult>> {
        val trimmed = query.trim()
        if (trimmed.isBlank()) return Result.success(emptyList())
        val existing = database.searchHistoryDao().get(trimmed)
        database.searchHistoryDao().upsert(
            SearchHistoryEntry(trimmed, System.currentTimeMillis(), (existing?.hitCount ?: 0) + 1)
        )
        return searchService.search(trimmed)
    }

    suspend fun extract(input: String): Result<StreamExtractionResult> {
        return streamExtractor.extract(input).onSuccess { extraction ->
            database.trackDao().upsert(CachedTrack.fromTrack(extraction.track))
        }
    }
}
