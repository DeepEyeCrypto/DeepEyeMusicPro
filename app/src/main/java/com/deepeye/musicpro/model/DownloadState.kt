package com.deepeye.musicpro.model

sealed class DownloadState {
    data object Idle : DownloadState()
    data class Running(val trackId: String, val progress: Int, val bytesDownloaded: Long, val totalBytes: Long) : DownloadState()
    data class Completed(val track: Track) : DownloadState()
    data class Failed(val trackId: String, val message: String) : DownloadState()
}
