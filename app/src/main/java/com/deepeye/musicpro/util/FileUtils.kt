package com.deepeye.musicpro.util

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.deepeye.musicpro.model.Track
import java.io.File
import java.security.MessageDigest
import java.util.Locale

object FileUtils {
    fun downloadsDir(context: Context): File {
        val dir = File(context.getExternalFilesDir(null) ?: context.filesDir, "downloads")
        if (!dir.exists()) dir.mkdirs()
        return dir
    }

    fun safeFileName(track: Track, extension: String): String {
        val base = "${track.artist}-${track.title}"
            .lowercase(Locale.US)
            .replace(Regex("[^a-z0-9._-]+"), "-")
            .trim('-')
            .ifBlank { track.id }
        val digest = MessageDigest.getInstance("SHA-256")
            .digest(track.id.toByteArray())
            .joinToString("") { "%02x".format(it) }
            .take(10)
        return "$base-$digest.$extension"
    }

    fun extensionForMime(mimeType: String?): String = when (mimeType?.lowercase(Locale.US)) {
        "audio/opus", "audio/ogg" -> "ogg"
        "audio/webm", "video/webm" -> "webm"
        "audio/mp4", "audio/aac", "video/mp4" -> "m4a"
        "audio/mpeg" -> "mp3"
        "audio/flac" -> "flac"
        else -> "m4a"
    }

    fun fileUri(context: Context, file: File): Uri {
        return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    }
}
