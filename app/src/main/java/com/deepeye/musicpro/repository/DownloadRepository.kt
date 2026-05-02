package com.deepeye.musicpro.repository

import android.content.Context
import com.deepeye.musicpro.db.AppDatabase
import com.deepeye.musicpro.db.CachedTrack
import com.deepeye.musicpro.extractor.StreamExtractor
import com.deepeye.musicpro.model.DownloadState
import com.deepeye.musicpro.model.Track
import com.deepeye.musicpro.util.FileUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

class DownloadRepository(
    private val context: Context,
    private val database: AppDatabase,
    private val streamExtractor: StreamExtractor,
    private val httpClient: OkHttpClient
) {
    private val _state = MutableStateFlow<DownloadState>(DownloadState.Idle)
    val state: StateFlow<DownloadState> = _state.asStateFlow()
    fun observeDownloads() = database.trackDao().observeDownloads()

    suspend fun download(input: String): Result<Track> {
        val extraction = streamExtractor.extract(input).getOrElse { error ->
            _state.value = DownloadState.Failed(input, error.message ?: "Extraction failed")
            return Result.failure(error)
        }
        val selected = extraction.selectedFormat ?: return Result.failure(IllegalStateException("No direct audio stream available for offline download"))
        val baseTrack = extraction.track.copy(streamUrl = selected.url, mimeType = selected.mimeType, bitrate = selected.bitrate)
        val extension = FileUtils.extensionForMime(selected.mimeType)
        val file = File(FileUtils.downloadsDir(context), FileUtils.safeFileName(baseTrack, extension))
        val request = Request.Builder().url(selected.url).get().build()
        _state.value = DownloadState.Running(baseTrack.id, 0, 0L, selected.contentLength)
        httpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IllegalStateException("Download failed: HTTP ${response.code}")
            val body = response.body ?: throw IllegalStateException("Download response body missing")
            val total = if (body.contentLength() > 0) body.contentLength() else selected.contentLength
            body.byteStream().use { inputStream ->
                file.outputStream().use { output ->
                    val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                    var read: Int
                    var downloaded = 0L
                    while (inputStream.read(buffer).also { read = it } != -1) {
                        output.write(buffer, 0, read)
                        downloaded += read
                        val progress = if (total > 0) ((downloaded * 100L) / total).toInt().coerceIn(0, 100) else 0
                        _state.value = DownloadState.Running(baseTrack.id, progress, downloaded, total)
                    }
                }
            }
        }
        val downloadedTrack = baseTrack.copy(localUri = file.toURI().toString())
        database.trackDao().upsert(CachedTrack.fromTrack(downloadedTrack, downloaded = true, fileSizeBytes = file.length()))
        _state.value = DownloadState.Completed(downloadedTrack)
        return Result.success(downloadedTrack)
    }

    suspend fun deleteDownload(trackId: String): Boolean {
        val row = database.trackDao().getById(trackId) ?: return false
        row.localUri?.removePrefix("file:")?.let { path -> File(path).takeIf { it.exists() }?.delete() }
        database.trackDao().clearDownload(trackId)
        return true
    }
}
