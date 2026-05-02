package com.deepeye.musicpro.extractor

import android.content.Intent
import android.net.Uri

object LinkParser {
    private val youtubeHosts = setOf("youtube.com", "www.youtube.com", "m.youtube.com", "music.youtube.com", "youtu.be")

    fun parseSharedText(intent: Intent?): String? {
        val text = when (intent?.action) {
            Intent.ACTION_SEND -> intent.getStringExtra(Intent.EXTRA_TEXT)
            Intent.ACTION_VIEW -> intent.dataString
            else -> intent?.dataString
        }.orEmpty()
        return extractFirstUrl(text)
    }

    fun extractFirstUrl(text: String): String? {
        val match = Regex("https?://\\S+").find(text.trim()) ?: return null
        return sanitizeUrl(match.value)
    }

    fun isYouTubeLink(value: String): Boolean {
        val uri = runCatching { Uri.parse(value) }.getOrNull() ?: return false
        val host = uri.host?.lowercase() ?: return false
        return youtubeHosts.any { host == it || host.endsWith(".$it") }
    }

    fun videoId(url: String): String? {
        val uri = runCatching { Uri.parse(url) }.getOrNull() ?: return null
        val host = uri.host?.lowercase().orEmpty()
        return when {
            host == "youtu.be" -> uri.pathSegments.firstOrNull()
            uri.getQueryParameter("v") != null -> uri.getQueryParameter("v")
            uri.pathSegments.contains("shorts") -> uri.pathSegments.getOrNull(uri.pathSegments.indexOf("shorts") + 1)
            uri.pathSegments.contains("watch") -> uri.getQueryParameter("v")
            else -> null
        }?.takeIf { it.length in 6..32 }
    }

    fun normalizeYouTubeUrl(input: String): String? {
        val direct = if (input.startsWith("http", true)) sanitizeUrl(input) else extractFirstUrl(input)
        if (direct == null || !isYouTubeLink(direct)) return null
        val id = videoId(direct)
        return if (id != null) "https://www.youtube.com/watch?v=$id" else direct
    }

    private fun sanitizeUrl(url: String): String = url.trim().trimEnd(')', ']', '.', ',', ';')
}
