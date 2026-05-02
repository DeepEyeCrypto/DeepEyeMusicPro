package com.deepeye.musicpro.extractor

import com.deepeye.musicpro.model.SearchResult
import com.deepeye.musicpro.model.Track
import com.deepeye.musicpro.util.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.schabi.newpipe.extractor.ServiceList
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeSearchQueryHandlerFactory
import org.schabi.newpipe.extractor.stream.StreamInfoItem
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class SearchService(
    private val httpClient: OkHttpClient,
    private val streamExtractor: StreamExtractor
) {
    suspend fun search(query: String): Result<List<SearchResult>> = withContext(Dispatchers.IO) {
        runCatching {
            val normalized = query.trim()
            if (normalized.isBlank()) return@runCatching emptyList<SearchResult>()
            LinkParser.normalizeYouTubeUrl(normalized)?.let { url ->
                val extraction = streamExtractor.extract(url).getOrThrow()
                return@runCatching listOf(SearchResult(extraction.track, extraction.sourceMessage, 1f, extraction.selectedFormat == null))
            }
            newPipeSearch(normalized).ifEmpty { suggestionSearch(normalized) }
        }
    }

    private fun newPipeSearch(query: String): List<SearchResult> {
        return runCatching {
            streamExtractor.ensureNewPipeInitialized()
            val extractor = ServiceList.YouTube.getSearchExtractor(
                query,
                listOf(YoutubeSearchQueryHandlerFactory.MUSIC_SONGS),
                ""
            )
            extractor.fetchPage()
            extractor.initialPage.items
                .filterIsInstance<StreamInfoItem>()
                .filter { item -> item.url.isNotBlank() }
                .take(12)
                .mapIndexed { index, item ->
                    SearchResult(
                        track = Track(
                            id = LinkParser.videoId(item.url) ?: "yt-${item.url.hashCode()}",
                            title = item.name.ifBlank { "YouTube Music result" },
                            artist = item.uploaderName.orEmpty().ifBlank { "YouTube Music" },
                            durationMs = item.duration.coerceAtLeast(0L) * 1000L,
                            thumbnailUrl = item.thumbnails.firstOrNull()?.url,
                            webUrl = item.url,
                            source = "youtube"
                        ),
                        sourceLabel = "YouTube Music stream",
                        confidence = (1f - index * 0.04f).coerceAtLeast(0.55f),
                        requiresExtraction = true
                    )
                }
        }.getOrElse { error ->
            Logger.w(TAG, "NewPipe search failed, using suggestion fallback", error)
            emptyList()
        }
    }

    private fun suggestionSearch(query: String): List<SearchResult> {
        val encoded = URLEncoder.encode(query, StandardCharsets.UTF_8.name())
        val request = Request.Builder()
            .url("https://suggestqueries.google.com/complete/search?client=firefox&ds=yt&q=$encoded")
            .get()
            .build()
        httpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return emptyList()
            val body = response.body?.string().orEmpty()
            val array = JSONArray(body)
            val suggestions = array.optJSONArray(1) ?: JSONArray()
            return (0 until suggestions.length()).mapNotNull { index ->
                val title = suggestions.optString(index).takeIf { it.isNotBlank() } ?: return@mapNotNull null
                val webUrl = "https://www.youtube.com/results?search_query=" + URLEncoder.encode(title, StandardCharsets.UTF_8.name())
                SearchResult(
                    track = Track(
                        id = "search-${title.hashCode()}",
                        title = title,
                        artist = "YouTube Music search",
                        webUrl = webUrl,
                        source = "youtube"
                    ),
                    sourceLabel = "YouTube suggestion",
                    confidence = (0.95f - index * 0.05f).coerceAtLeast(0.5f),
                    requiresExtraction = true
                )
            }
        }
    }

    companion object {
        private const val TAG = "SearchService"
    }
}
