package com.deepeye.musicpro.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.session.LibraryResult
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaLibraryService.LibraryParams
import androidx.media3.session.MediaLibraryService.MediaLibrarySession
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommand
import com.deepeye.musicpro.DeepEyeApp
import com.deepeye.musicpro.notification.PlayerNotificationManager
import com.deepeye.musicpro.util.Logger
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.launch

class MusicPlayerService : MediaLibraryService() {
    private lateinit var player: ExoPlayer
    private var mediaLibrarySession: MediaLibrarySession? = null

    private val noisyReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == AudioManager.ACTION_AUDIO_BECOMING_NOISY) player.pause()
        }
    }

    override fun onCreate() {
        super.onCreate()
        val app = DeepEyeApp.from(this)
        val httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setUserAgent(USER_AGENT)
            .setAllowCrossProtocolRedirects(true)
            .setConnectTimeoutMs(15_000)
            .setReadTimeoutMs(30_000)
            .setDefaultRequestProperties(
                mapOf(
                    "Accept" to "*/*",
                    "Accept-Language" to "en-US,en;q=0.9",
                    "Origin" to "https://www.youtube.com",
                    "Referer" to "https://www.youtube.com/"
                )
            )
        player = ExoPlayer.Builder(this, DeepEyeRenderersFactory(this, app.dspManager, app.v4aEngine))
            .setMediaSourceFactory(DefaultMediaSourceFactory(httpDataSourceFactory))
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
                    .setUsage(C.USAGE_MEDIA)
                    .build(),
                true
            )
            .setHandleAudioBecomingNoisy(true)
            .build()
        player.addListener(object : Player.Listener {
            override fun onAudioSessionIdChanged(audioSessionId: Int) {
                app.v4aEngine.init(audioSessionId)
            }

            override fun onEvents(player: Player, events: Player.Events) {
                if (events.contains(Player.EVENT_MEDIA_ITEM_TRANSITION) || events.contains(Player.EVENT_TIMELINE_CHANGED)) {
                    Logger.d(TAG, "Queue synchronized mediaItems=${player.mediaItemCount} currentIndex=${player.currentMediaItemIndex}")
                }
                if (player.isPlaying) {
                    Logger.d(TAG, "Playback started title=${player.mediaMetadata.title} uri=${player.currentMediaItem?.localConfiguration?.uri}")
                    startForeground(
                        PlayerNotificationManager.PLAYBACK_NOTIFICATION_ID,
                        PlayerNotificationManager.buildPlaybackNotification(this@MusicPlayerService, requireSession())
                    )
                }
            }

            override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                val uri = player.currentMediaItem?.localConfiguration?.uri
                Logger.e(TAG, "Playback error code=${error.errorCodeName} uri=$uri", error)
            }
        })
        app.v4aEngine.init(player.audioSessionId)
        mediaLibrarySession = MediaLibrarySession.Builder(this, player, Callback())
            .setId("DeepEyeMusicProSession")
            .build()
        ContextCompat.registerReceiver(this, noisyReceiver, IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY), ContextCompat.RECEIVER_NOT_EXPORTED)
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaLibrarySession? = mediaLibrarySession

    override fun onDestroy() {
        unregisterReceiver(noisyReceiver)
        mediaLibrarySession?.release()
        player.release()
        DeepEyeApp.from(this).dspManager.release()
        DeepEyeApp.from(this).v4aEngine.release()
        super.onDestroy()
    }

    private fun requireSession(): MediaSession = mediaLibrarySession ?: error("Media session not ready")

    private inner class Callback : MediaLibrarySession.Callback {
        override fun onConnect(session: MediaSession, controller: MediaSession.ControllerInfo): MediaSession.ConnectionResult {
            val commands = MediaSession.ConnectionResult.DEFAULT_SESSION_COMMANDS.buildUpon()
                .add(SessionCommand(COMMAND_DSP_TOGGLE, Bundle.EMPTY))
                .add(SessionCommand(COMMAND_DSP_PRESET, Bundle.EMPTY))
                .build()
            return MediaSession.ConnectionResult.accept(commands, MediaSession.ConnectionResult.DEFAULT_PLAYER_COMMANDS)
        }

        override fun onCustomCommand(
            session: MediaSession,
            controller: MediaSession.ControllerInfo,
            customCommand: SessionCommand,
            args: Bundle
        ): ListenableFuture<androidx.media3.session.SessionResult> {
            val dsp = DeepEyeApp.from(this@MusicPlayerService).dspManager
            when (customCommand.customAction) {
                COMMAND_DSP_TOGGLE -> dsp.setEnabled(args.getBoolean("enabled", true))
                COMMAND_DSP_PRESET -> args.getString("preset_id")?.let { id ->
                    DSPCommandBridge.applyPreset(this@MusicPlayerService, id)
                }
            }
            return Futures.immediateFuture(androidx.media3.session.SessionResult(androidx.media3.session.SessionResult.RESULT_SUCCESS))
        }

        override fun onGetLibraryRoot(
            session: MediaLibrarySession,
            browser: MediaSession.ControllerInfo,
            params: LibraryParams?
        ): ListenableFuture<LibraryResult<MediaItem>> {
            return Futures.immediateFuture(LibraryResult.ofItem(MediaItem.Builder().setMediaId(ROOT_ID).build(), params))
        }
    }

    companion object {
        const val ROOT_ID = "deepeye_root"
        const val COMMAND_DSP_TOGGLE = "com.deepeye.musicpro.DSP_TOGGLE"
        const val COMMAND_DSP_PRESET = "com.deepeye.musicpro.DSP_PRESET"
        private const val TAG = "MusicPlayerService"
        private const val USER_AGENT = "Mozilla/5.0 (Linux; Android 14) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Mobile Safari/537.36"
    }
}

private object DSPCommandBridge {
    fun applyPreset(context: Context, id: String) {
        val app = DeepEyeApp.from(context)
        app.applicationScope.launch { app.dspRepository.applyPreset(id) }
    }
}
