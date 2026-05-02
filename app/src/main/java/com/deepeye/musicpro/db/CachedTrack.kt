package com.deepeye.musicpro.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deepeye.musicpro.model.SourceKind
import com.deepeye.musicpro.model.Track

@Entity(tableName = "cached_tracks")
data class CachedTrack(
    @PrimaryKey val id: String,
    val title: String,
    val artist: String,
    val durationMs: Long,
    val thumbnailUrl: String?,
    val streamUrl: String?,
    val webUrl: String?,
    val localUri: String?,
    val mimeType: String?,
    val bitrate: Int,
    val source: String,
    val isDownloaded: Boolean,
    val downloadedAt: Long,
    val lastPlayedAt: Long,
    val playCount: Int,
    val fileSizeBytes: Long
) {
    fun toTrack(): Track = Track(
        id = id,
        title = title,
        artist = artist,
        durationMs = durationMs,
        thumbnailUrl = thumbnailUrl,
        streamUrl = streamUrl,
        webUrl = webUrl,
        localUri = localUri,
        mimeType = mimeType,
        bitrate = bitrate,
        source = source
    )

    companion object {
        fun fromTrack(track: Track, downloaded: Boolean = false, fileSizeBytes: Long = 0L, now: Long = System.currentTimeMillis()): CachedTrack {
            return CachedTrack(
                id = track.id,
                title = track.title,
                artist = track.artist,
                durationMs = track.durationMs,
                thumbnailUrl = track.thumbnailUrl,
                streamUrl = track.streamUrl,
                webUrl = track.webUrl,
                localUri = track.localUri,
                mimeType = track.mimeType,
                bitrate = track.bitrate,
                source = track.source.ifBlank { SourceKind.YOUTUBE },
                isDownloaded = downloaded,
                downloadedAt = if (downloaded) now else 0L,
                lastPlayedAt = now,
                playCount = 0,
                fileSizeBytes = fileSizeBytes
            )
        }
    }
}
