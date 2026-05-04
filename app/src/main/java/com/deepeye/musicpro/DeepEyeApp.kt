package com.deepeye.musicpro

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.content.getSystemService
import androidx.room.Room
import com.deepeye.musicpro.adblock.AdBlockEngine
import com.deepeye.musicpro.db.AppDatabase
import com.deepeye.musicpro.dsp.DSPManager
import com.deepeye.musicpro.dsp.v4a.V4AAssetLoader
import com.deepeye.musicpro.dsp.v4a.V4AEngine
import com.deepeye.musicpro.extractor.DownloaderImpl
import com.deepeye.musicpro.extractor.SearchService
import com.deepeye.musicpro.extractor.StreamExtractor
import com.deepeye.musicpro.repository.AppRepository
import com.deepeye.musicpro.repository.DSPRepository
import com.deepeye.musicpro.repository.DownloadRepository
import com.deepeye.musicpro.repository.PlayerRepository
import com.deepeye.musicpro.repository.SearchRepository
import com.deepeye.musicpro.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class DeepEyeApp : Application() {
    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(12, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(com.deepeye.musicpro.extractor.PrivacyInterceptor())
            .retryOnConnectionFailure(true)
            .build()
    }

    val database: AppDatabase by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "deepeye_musicpro.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    val settingsRepository: SettingsRepository by lazy { SettingsRepository(this) }
    val adBlockEngine: AdBlockEngine by lazy { AdBlockEngine(httpClient) }
    val downloader: DownloaderImpl by lazy { DownloaderImpl(httpClient) }
    val streamExtractor: StreamExtractor by lazy { StreamExtractor(httpClient, downloader) }
    val searchService: SearchService by lazy { SearchService(httpClient, streamExtractor) }
    val searchRepository: SearchRepository by lazy { SearchRepository(database, searchService, streamExtractor) }
    val dspManager: DSPManager by lazy { DSPManager(this, applicationScope) }
    val v4aEngine: V4AEngine by lazy { V4AEngine(this) }
    val dspRepository: DSPRepository by lazy { DSPRepository(database, dspManager) }
    val downloadRepository: DownloadRepository by lazy { DownloadRepository(this, database, streamExtractor, httpClient) }
    val playerRepository: PlayerRepository by lazy { PlayerRepository(database, streamExtractor) }
    val appRepository: AppRepository by lazy {
        AppRepository(
            database = database,
            settingsRepository = settingsRepository,
            playerRepository = playerRepository,
            searchRepository = searchRepository,
            downloadRepository = downloadRepository,
            dspRepository = dspRepository
        )
    }

    override fun onCreate() {
        super.onCreate()
        com.deepeye.musicpro.CrashGuard.init(this, BuildConfig.DEBUG)
        
        try {
            com.google.firebase.crashlytics.FirebaseCrashlytics.getInstance().apply {
                setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
                setCustomKey("native_dsp_loaded", com.deepeye.musicpro.dsp.NativeDSP().available)
                setCustomKey("device_model", Build.MODEL)
            }
        } catch (e: Exception) {
            android.util.Log.e("DeepEyeApp", "Crashlytics init failed - missing google-services.json?", e)
        }

        com.deepeye.musicpro.auth.YoutubeAuthManager.init(this)
        createNotificationChannels()
        applicationScope.launch { V4AAssetLoader.copyAssetsToCache(this@DeepEyeApp) }
        applicationScope.launch { adBlockEngine.loadFilterLists() }
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val manager = getSystemService<NotificationManager>() ?: return
        val playback = NotificationChannel(
            CHANNEL_PLAYBACK,
            getString(R.string.notification_channel_playback),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = getString(R.string.notification_channel_playback_desc)
            setShowBadge(false)
        }
        val downloads = NotificationChannel(
            CHANNEL_DOWNLOADS,
            getString(R.string.notification_channel_downloads),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = getString(R.string.notification_channel_downloads_desc)
            setShowBadge(false)
        }
        val diagnostics = NotificationChannel(
            CHANNEL_DIAGNOSTICS,
            getString(R.string.notification_channel_diagnostics),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = getString(R.string.notification_channel_diagnostics_desc)
            setShowBadge(false)
        }
        manager.createNotificationChannels(listOf(playback, downloads, diagnostics))
    }

    companion object {
        const val CHANNEL_PLAYBACK = "deepeye_playback"
        const val CHANNEL_DOWNLOADS = "deepeye_downloads"
        const val CHANNEL_DIAGNOSTICS = "deepeye_diagnostics"

        fun from(context: Context): DeepEyeApp = context.applicationContext as DeepEyeApp
    }
}
