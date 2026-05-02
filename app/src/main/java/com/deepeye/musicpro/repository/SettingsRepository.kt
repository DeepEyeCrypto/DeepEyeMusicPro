package com.deepeye.musicpro.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.deepeye.musicpro.model.AppSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore by preferencesDataStore("deepeye_settings")

class SettingsRepository(private val context: Context) {
    val settings: Flow<AppSettings> = context.settingsDataStore.data.map { prefs ->
        AppSettings(
            themeMode = prefs[Keys.ThemeMode] ?: "dark",
            dynamicAccent = prefs[Keys.DynamicAccent] ?: false,
            audioQuality = prefs[Keys.AudioQuality] ?: "best",
            autoplay = prefs[Keys.Autoplay] ?: true,
            restoreLastSession = prefs[Keys.RestoreLastSession] ?: true,
            notificationStyle = prefs[Keys.NotificationStyle] ?: "rich",
            downloadLocationStrategy = prefs[Keys.DownloadLocationStrategy] ?: "app_private",
            dspEnabledDefault = prefs[Keys.DspEnabledDefault] ?: true,
            chosenPreset = prefs[Keys.ChosenPreset] ?: "flat",
            webViewFallbackEnabled = prefs[Keys.WebFallbackEnabled] ?: true,
            explicitContentEnabled = prefs[Keys.ExplicitContentEnabled] ?: true
        )
    }

    suspend fun setAudioQuality(value: String) = context.settingsDataStore.edit { it[Keys.AudioQuality] = value }
    suspend fun setDspEnabledDefault(value: Boolean) = context.settingsDataStore.edit { it[Keys.DspEnabledDefault] = value }
    suspend fun setChosenPreset(value: String) = context.settingsDataStore.edit { it[Keys.ChosenPreset] = value }
    suspend fun setWebFallbackEnabled(value: Boolean) = context.settingsDataStore.edit { it[Keys.WebFallbackEnabled] = value }
    suspend fun setDownloadLocation(value: String) = context.settingsDataStore.edit { it[Keys.DownloadLocationStrategy] = value }

    private object Keys {
        val ThemeMode = stringPreferencesKey("theme_mode")
        val DynamicAccent = booleanPreferencesKey("dynamic_accent")
        val AudioQuality = stringPreferencesKey("audio_quality")
        val Autoplay = booleanPreferencesKey("autoplay")
        val RestoreLastSession = booleanPreferencesKey("restore_last_session")
        val NotificationStyle = stringPreferencesKey("notification_style")
        val DownloadLocationStrategy = stringPreferencesKey("download_location_strategy")
        val DspEnabledDefault = booleanPreferencesKey("dsp_enabled_default")
        val ChosenPreset = stringPreferencesKey("chosen_preset")
        val WebFallbackEnabled = booleanPreferencesKey("webview_fallback_enabled")
        val ExplicitContentEnabled = booleanPreferencesKey("explicit_content_enabled")
    }
}
