package com.deepeye.musicpro.player

import com.deepeye.musicpro.model.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class QueueManager {
    private val _queue = MutableStateFlow<List<Track>>(emptyList())
    private val _index = MutableStateFlow(-1)
    val queue: StateFlow<List<Track>> = _queue.asStateFlow()
    val index: StateFlow<Int> = _index.asStateFlow()

    fun setQueue(tracks: List<Track>, startIndex: Int = 0) {
        _queue.value = tracks
        _index.value = if (tracks.isEmpty()) -1 else startIndex.coerceIn(tracks.indices)
    }

    fun current(): Track? = _queue.value.getOrNull(_index.value)

    fun moveTo(index: Int): Track? {
        if (index !in _queue.value.indices) return null
        _index.value = index
        return current()
    }

    fun add(track: Track) {
        _queue.value = _queue.value + track
        if (_index.value < 0) _index.value = 0
    }

    fun remove(trackId: String) {
        val currentTrack = current()
        val updated = _queue.value.filterNot { it.id == trackId }
        _queue.value = updated
        _index.value = when {
            updated.isEmpty() -> -1
            currentTrack == null -> 0
            else -> updated.indexOfFirst { it.id == currentTrack.id }.takeIf { it >= 0 } ?: _index.value.coerceIn(updated.indices)
        }
    }

    fun next(repeatMode: RepeatMode, shuffle: Boolean): Track? {
        val queue = _queue.value
        if (queue.isEmpty()) return null
        val nextIndex = when {
            repeatMode == RepeatMode.ONE -> _index.value
            shuffle -> ((_index.value + 3) % queue.size)
            _index.value < queue.lastIndex -> _index.value + 1
            repeatMode == RepeatMode.ALL -> 0
            else -> -1
        }
        if (nextIndex < 0) return null
        _index.value = nextIndex
        return current()
    }

    fun previous(): Track? {
        val queue = _queue.value
        if (queue.isEmpty()) return null
        _index.value = if (_index.value > 0) _index.value - 1 else 0
        return current()
    }
}
