package com.deepeye.musicpro.repository

import com.deepeye.musicpro.db.AppDatabase
import com.deepeye.musicpro.db.CachedTrack
import com.deepeye.musicpro.extractor.StreamExtractor
import com.deepeye.musicpro.model.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlayerRepository(
    private val database: AppDatabase,
    private val streamExtractor: StreamExtractor
) {
    private val _queue = MutableStateFlow<List<Track>>(emptyList())
    val queue: StateFlow<List<Track>> = _queue.asStateFlow()

    suspend fun resolveTrack(input: String): Result<Track> {
        return streamExtractor.extract(input).map { it.track }.onSuccess { track ->
            database.trackDao().upsert(CachedTrack.fromTrack(track))
        }
    }

    suspend fun setQueue(tracks: List<Track>, startIndex: Int = 0): Track? {
        if (tracks.isEmpty()) return null
        _queue.value = tracks
        val track = tracks[startIndex.coerceIn(tracks.indices)]
        database.trackDao().upsert(CachedTrack.fromTrack(track))
        return track
    }

    suspend fun markPlayed(track: Track) {
        database.trackDao().upsert(CachedTrack.fromTrack(track))
        database.trackDao().markPlayed(track.id, System.currentTimeMillis())
    }
}
