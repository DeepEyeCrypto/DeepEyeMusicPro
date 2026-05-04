package com.deepeye.musicpro.extractor

import com.deepeye.musicpro.model.SearchResult
import com.deepeye.musicpro.model.Track
import com.deepeye.musicpro.util.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.schabi.newpipe.extractor.ServiceList
import org.schabi.newpipe.extractor.stream.StreamInfo
import org.schabi.newpipe.extractor.stream.StreamInfoItem

object RelatedExtractor {
    private const val TAG = "RelatedExtractor"
    private const val MIN_RELATED = 5
    private const val MAX_RELATED = 15

    suspend fun fetchRelated(videoId: String, fallbackSearchService: SearchService? = null): Result<List<SearchResult>> = withContext(Dispatchers.IO) {
        try {
            // Method 1: NewPipe StreamInfo related items
            val url = "https://www.youtube.com/watch?v=$videoId"
            val info = StreamInfo.getInfo(ServiceList.YouTube, url)
            
            val relatedItems = info.relatedItems ?: emptyList()
            val related = relatedItems
                .filterIsInstance<StreamInfoItem>()
                .take(MAX_RELATED)
                .mapIndexed { index, item ->
                    val rawId = LinkParser.videoId(item.url) ?: item.url.substringAfter("v=").substringBefore("&")
                    // Privacy: Scrub any tracking params (?si=, ?feature=, etc)
                    val cleanUrl = "https://www.youtube.com/watch?v=$rawId"
                    
                    SearchResult(
                        track = Track(
                            id = rawId,
                            title = item.name.ifBlank { "Unknown title" },
                            artist = item.uploaderName.orEmpty().ifBlank { "Unknown artist" },
                            durationMs = item.duration.coerceAtLeast(0L) * 1000L,
                            thumbnailUrl = item.thumbnails.firstOrNull()?.url,
                            webUrl = cleanUrl,
                            source = "youtube"
                        ),
                        sourceLabel = "Related song",
                        confidence = (0.95f - index * 0.05f).coerceAtLeast(0.5f),
                        requiresExtraction = true
                    )
                }

            if (related.size >= MIN_RELATED) {
                return@withContext Result.success(related)
            }

            // Method 2: Fallback — YouTube Music watch playlist API
            fetchWatchPlaylist(videoId).onSuccess { 
                if (it.isNotEmpty()) return@withContext Result.success(it) 
            }

            // Method 3: Final fallback — search similar title
            if (fallbackSearchService != null) {
                val seedTitle = info.name.orEmpty().ifBlank { "" }
                val seedArtist = info.uploaderName.orEmpty().ifBlank { "" }
                val fallbackQuery = if (seedTitle.isNotBlank()) "$seedTitle $seedArtist similar" else "popular music"
                
                fallbackSearchService.search(fallbackQuery).onSuccess { results ->
                    if (results.isNotEmpty()) {
                        return@withContext Result.success(results.take(MAX_RELATED))
                    }
                }
            }

            Result.success(related)
        } catch (e: Exception) {
            // Privacy: Don't log full exception if it contains user tokens or full URLs
            Logger.e(TAG, "Failed to fetch related for ID: ${videoId.take(11)}") 
            Result.failure(e)
        }
    }

    private suspend fun fetchWatchPlaylist(videoId: String): Result<List<SearchResult>> = withContext(Dispatchers.IO) {
        runCatching {
            // Placeholder for internal ytmusicapi-style extraction if NewPipe related fails
            emptyList<SearchResult>()
        }
    }
}
