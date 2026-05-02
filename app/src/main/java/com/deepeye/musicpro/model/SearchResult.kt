package com.deepeye.musicpro.model

data class SearchResult(
    val track: Track,
    val sourceLabel: String,
    val confidence: Float,
    val requiresExtraction: Boolean = true
)
