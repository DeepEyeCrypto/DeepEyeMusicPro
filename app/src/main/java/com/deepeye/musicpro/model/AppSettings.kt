package com.deepeye.musicpro.model

data class AppSettings(
    val themeMode: String = "dark",
    val dynamicAccent: Boolean = false,
    val audioQuality: String = "best",
    val autoplay: Boolean = true,
    val restoreLastSession: Boolean = true,
    val notificationStyle: String = "rich",
    val downloadLocationStrategy: String = "app_private",
    val dspEnabledDefault: Boolean = true,
    val chosenPreset: String = "flat",
    val webViewFallbackEnabled: Boolean = true,
    val explicitContentEnabled: Boolean = true
)
