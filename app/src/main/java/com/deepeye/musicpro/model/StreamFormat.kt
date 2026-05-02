package com.deepeye.musicpro.model

data class StreamFormat(
    val url: String,
    val mimeType: String?,
    val bitrate: Int,
    val contentLength: Long = -1L,
    val codec: String? = null,
    val isAudioOnly: Boolean = true
)

data class StreamExtractionResult(
    val track: Track,
    val formats: List<StreamFormat>,
    val selectedFormat: StreamFormat?,
    val fallbackWebUrl: String?,
    val sourceMessage: String
)
