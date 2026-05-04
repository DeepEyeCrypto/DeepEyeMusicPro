package com.deepeye.musicpro.model

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata

object SourceKind {
    const val YOUTUBE = "youtube"
    const val LOCAL = "local"
    const val WEB_FALLBACK = "web_fallback"
}

data class Track(
    val id: String,
    val title: String,
    val artist: String,
    val durationMs: Long = 0L,
    val thumbnailUrl: String? = null,
    val streamUrl: String? = null,
    val webUrl: String? = null,
    val localUri: String? = null,
    val mimeType: String? = null,
    val bitrate: Int = 0,
    val source: String = SourceKind.YOUTUBE,
    val isExplicit: Boolean = false,
    val isFavorite: Boolean = false
) {
    val playableUri: String?
        get() = localUri ?: streamUrl

    fun toMediaMetadata(): MediaMetadata {
        return MediaMetadata.Builder()
            .setTitle(title)
            .setArtist(artist)
            .setArtworkUri(thumbnailUrl?.let(android.net.Uri::parse))
            .build()
    }

    fun toMediaItem(): MediaItem {
        val uri = playableUri ?: webUrl.orEmpty()
        val metadata = MediaMetadata.Builder()
            .setTitle(title)
            .setArtist(artist)
            .setArtworkUri(thumbnailUrl?.let(android.net.Uri::parse))
            .build()
        return MediaItem.Builder()
            .setMediaId(id)
            .setUri(uri)
            .setMimeType(mimeType)
            .setMediaMetadata(metadata)
            .setTag(this)
            .build()
    }
}
