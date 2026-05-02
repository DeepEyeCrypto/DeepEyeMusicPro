package com.deepeye.musicpro.player

import com.deepeye.musicpro.model.Track

enum class RepeatMode { OFF, ALL, ONE }

data class PlayerState(
    val currentTrack: Track? = null,
    val queue: List<Track> = emptyList(),
    val currentIndex: Int = -1,
    val isPlaying: Boolean = false,
    val positionMs: Long = 0L,
    val durationMs: Long = 0L,
    val shuffleEnabled: Boolean = false,
    val repeatMode: RepeatMode = RepeatMode.OFF,
    val buffering: Boolean = false,
    val errorMessage: String? = null
)
