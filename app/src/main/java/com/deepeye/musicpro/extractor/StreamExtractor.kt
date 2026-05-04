package com.deepeye.musicpro.extractor

import com.deepeye.musicpro.model.StreamExtractionResult
import com.deepeye.musicpro.model.StreamFormat
import com.deepeye.musicpro.model.Track
import com.deepeye.musicpro.util.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.ServiceList
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.concurrent.atomic.AtomicBoolean

class StreamExtractor(
    private val httpClient: OkHttpClient,
    val downloader: DownloaderImpl
) {
    private val initialized = AtomicBoolean(false)

    suspend fun extract(input: String): Result<StreamExtractionResult> = withContext(Dispatchers.IO) {
        val url = if (input.length in 6..15 && !input.contains("/")) {
            "https://www.youtube.com/watch?v=$input"
        } else {
            LinkParser.normalizeYouTubeUrl(input) ?: input
        }
        runCatching {
            ensureNewPipeInitialized()
            newPipeExtraction(url).getOrElse { error ->
                Logger.w(TAG, "NewPipe extraction failed, using metadata fallback", error)
                metadataFallback(url)
            }
        }
    }

    fun ensureNewPipeInitialized() {
        if (initialized.compareAndSet(false, true)) {
            NewPipe.init(downloader)
        }
    }

    private fun newPipeExtraction(url: String): Result<StreamExtractionResult> = runCatching {
        val extractor = ServiceList.YouTube.getStreamExtractor(url)
        extractor.fetchPage()
        val formats = extractor.audioStreams.mapNotNull { stream ->
            val streamUrl = stream.url?.takeIf { it.isNotBlank() } ?: return@mapNotNull null
            StreamFormat(
                url = streamUrl,
                mimeType = stream.format?.mimeType,
                bitrate = stream.averageBitrate,
                contentLength = -1L,
                codec = stream.codec,
                isAudioOnly = true
            )
        }.sortedWith(compareByDescending<StreamFormat> { scoreFormat(it) }.thenByDescending { it.bitrate })
        val selected = formats.firstOrNull()
        val track = Track(
            id = LinkParser.videoId(url) ?: extractor.id,
            title = extractor.name.orEmpty().ifBlank { "YouTube audio" },
            artist = extractor.uploaderName.orEmpty().ifBlank { "Unknown artist" },
            durationMs = extractor.length.coerceAtLeast(0L) * 1000L,
            thumbnailUrl = extractor.thumbnails.firstOrNull()?.url,
            streamUrl = selected?.url,
            webUrl = url,
            mimeType = selected?.mimeType,
            bitrate = selected?.bitrate ?: 0
        )
        StreamExtractionResult(track, formats, selected, url, "NewPipe audio stream selected")
    }

    private fun metadataFallback(url: String): StreamExtractionResult {
        val encoded = URLEncoder.encode(url, StandardCharsets.UTF_8.name())
        val request = Request.Builder()
            .url("https://www.youtube.com/oembed?format=json&url=$encoded")
            .get()
            .build()
        httpClient.newCall(request).execute().use { response ->
            val body = response.body?.string().orEmpty()
            val json = if (response.isSuccessful && body.isNotBlank()) JSONObject(body) else JSONObject()
            val title = json.optString("title", "YouTube audio")
            val author = json.optString("author_name", "YouTube")
            val thumb = json.optString("thumbnail_url", null)
            val track = Track(
                id = LinkParser.videoId(url) ?: url.hashCode().toString(),
                title = title,
                artist = author,
                thumbnailUrl = thumb,
                webUrl = url,
                streamUrl = null,
                source = "web_fallback"
            )
            return StreamExtractionResult(track, emptyList(), null, url, "Direct stream unavailable; WebView fallback prepared")
        }
    }

    fun chooseBest(formats: List<StreamFormat>): StreamFormat? {
        return formats.maxWithOrNull(compareBy<StreamFormat> { scoreFormat(it) }.thenBy { it.bitrate })
    }

    private fun scoreFormat(format: StreamFormat): Int {
        val mime = format.mimeType.orEmpty().lowercase()
        val codec = format.codec.orEmpty().lowercase()
        var score = format.bitrate
        if (mime.contains("opus") || codec.contains("opus")) score += 500
        if (mime.contains("webm")) score += 200
        if (mime.contains("mp4") || codec.contains("mp4a")) score += 120
        if (!format.isAudioOnly) score -= 800
        return score
    }

    companion object {
        private const val TAG = "StreamExtractor"
    }
}
