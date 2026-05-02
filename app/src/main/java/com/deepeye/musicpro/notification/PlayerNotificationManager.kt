package com.deepeye.musicpro.notification

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaStyleNotificationHelper
import com.deepeye.musicpro.DeepEyeApp
import com.deepeye.musicpro.R

object PlayerNotificationManager {
    const val PLAYBACK_NOTIFICATION_ID = 4010
    const val DOWNLOAD_NOTIFICATION_ID = 4020

    fun buildPlaybackNotification(context: Context, mediaSession: MediaSession): Notification {
        val metadata = mediaSession.player.mediaMetadata
        return NotificationCompat.Builder(context, DeepEyeApp.CHANNEL_PLAYBACK)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(metadata.title ?: context.getString(R.string.app_name))
            .setContentText(metadata.artist ?: context.getString(R.string.app_tagline))
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .setOngoing(mediaSession.player.playWhenReady)
            .setStyle(MediaStyleNotificationHelper.MediaStyle(mediaSession))
            .build()
    }

    fun buildDownloadNotification(context: Context, title: String, progress: Int): Notification {
        return NotificationCompat.Builder(context, DeepEyeApp.CHANNEL_DOWNLOADS)
            .setSmallIcon(R.drawable.ic_download)
            .setContentTitle(title)
            .setContentText("Saving for offline playback")
            .setProgress(100, progress.coerceIn(0, 100), progress <= 0)
            .setOngoing(progress in 1..99)
            .setOnlyAlertOnce(true)
            .build()
    }
}
