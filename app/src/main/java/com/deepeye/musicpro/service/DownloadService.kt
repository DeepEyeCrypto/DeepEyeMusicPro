package com.deepeye.musicpro.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.deepeye.musicpro.DeepEyeApp
import com.deepeye.musicpro.notification.PlayerNotificationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DownloadService : Service() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        scope.launch {
            DeepEyeApp.from(this@DownloadService).downloadRepository.state.collectLatest { state ->
                when (state) {
                    is com.deepeye.musicpro.model.DownloadState.Running -> startForeground(
                        PlayerNotificationManager.DOWNLOAD_NOTIFICATION_ID,
                        PlayerNotificationManager.buildDownloadNotification(this@DownloadService, "Downloading ${state.trackId}", state.progress)
                    )
                    else -> Unit
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val input = intent?.getStringExtra(EXTRA_INPUT).orEmpty()
        if (input.isBlank()) {
            stopSelf(startId)
            return START_NOT_STICKY
        }
        scope.launch {
            DeepEyeApp.from(this@DownloadService).downloadRepository.download(input)
            stopSelf(startId)
        }
        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    companion object {
        const val EXTRA_INPUT = "extra_input"
        fun start(context: Context, input: String) {
            val intent = Intent(context, DownloadService::class.java).putExtra(EXTRA_INPUT, input)
            androidx.core.content.ContextCompat.startForegroundService(context, intent)
        }
    }
}
