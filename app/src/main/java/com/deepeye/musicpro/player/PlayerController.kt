package com.deepeye.musicpro.player

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import androidx.media3.session.SessionToken
import com.deepeye.musicpro.model.Track
import com.deepeye.musicpro.service.MusicPlayerService
import com.deepeye.musicpro.util.Logger
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlayerController(private val context: Context) {
    private val _state = MutableStateFlow(PlayerState())
    val state: StateFlow<PlayerState> = _state.asStateFlow()
    
    private val _radioFetching = MutableStateFlow(false)
    val radioFetching: StateFlow<Boolean> = _radioFetching.asStateFlow()

    private var controller: MediaController? = null
    fun getController(): MediaController? = controller
    private var controllerFuture: ListenableFuture<MediaController>? = null
    private val queueManager = QueueManager()

    fun connect(onConnected: (() -> Unit)? = null) {
        if (controller != null) {
            onConnected?.invoke()
            return
        }
        val token = SessionToken(context, ComponentName(context, MusicPlayerService::class.java))
        val future = MediaController.Builder(context, token).buildAsync()
        controllerFuture = future
        future.addListener({
            try {
                controller = future.get().also { mediaController ->
                    mediaController.addListener(listener)
                    syncQueueManager(mediaController)
                    publishState(mediaController)
                }
                onConnected?.invoke()
            } catch (e: Exception) {
                Logger.e("PlayerController", "Failed to connect to MediaSession", e)
            }
        }, MoreExecutors.directExecutor())
    }

    fun playQueue(tracks: List<Track>, startIndex: Int = 0) {
        connect {
            val mediaController = controller
            if (mediaController == null) {
                queueManager.setQueue(tracks, startIndex)
                return@connect
            }
            queueManager.setQueue(tracks, startIndex)
            val items = tracks.map(Track::toMediaItem)
            mediaController.setMediaItems(items, startIndex.coerceIn(items.indices), 0L)
            syncQueueManager(mediaController)
            mediaController.prepare()
            mediaController.play()
        }
    }

    fun enqueue(track: Track, playIfEmpty: Boolean = true) {
        connect {
            val mediaController = controller ?: return@connect
            val items = (0 until mediaController.mediaItemCount).mapNotNull { 
                mediaController.getMediaItemAt(it).localConfiguration?.tag as? Track
            }
            if (items.isEmpty() && playIfEmpty) {
                playQueue(listOf(track))
            } else {
                mediaController.addMediaItem(track.toMediaItem())
            }
        }
    }

    fun enqueueRelated(results: List<com.deepeye.musicpro.model.SearchResult>) {
        connect {
            val mediaController = controller ?: return@connect
            val existingIds = (0 until mediaController.mediaItemCount).map { 
                mediaController.getMediaItemAt(it).mediaId
            }.toSet()
            
            results.forEach { result ->
                if (!existingIds.contains(result.track.id)) {
                    val placeholderUri = android.net.Uri.parse("placeholder://${result.track.id}")
                    val item = androidx.media3.common.MediaItem.Builder()
                        .setMediaId(result.track.id)
                        .setUri(placeholderUri)
                        .setMediaMetadata(result.track.toMediaMetadata())
                        .setTag(result.track)
                        .build()
                    mediaController.addMediaItem(item)
                }
            }
        }
    }

    fun getCurrentVideoId(): String? = controller?.currentMediaItem?.mediaId

    fun pause() { controller?.pause() }
    fun resume() { controller?.play() }
    fun toggle() { controller?.let { if (it.isPlaying) it.pause() else it.play() } }
    fun next() { controller?.seekToNextMediaItem() }
    fun previous() { controller?.seekToPreviousMediaItem() }
    fun seekTo(positionMs: Long) { controller?.seekTo(positionMs.coerceAtLeast(0L)) }
    fun setShuffle(enabled: Boolean) { controller?.shuffleModeEnabled = enabled }
    fun setRepeatMode(mode: RepeatMode) {
        controller?.repeatMode = when (mode) {
            RepeatMode.OFF -> Player.REPEAT_MODE_OFF
            RepeatMode.ALL -> Player.REPEAT_MODE_ALL
            RepeatMode.ONE -> Player.REPEAT_MODE_ONE
        }
    }

    fun release() {
        controller?.removeListener(listener)
        controllerFuture?.let { MediaController.releaseFuture(it) }
        controllerFuture = null
        controller = null
    }

    private val listener = object : Player.Listener {
        override fun onEvents(player: Player, events: Player.Events) {
            syncQueueManager(player)
            publishState(player)
        }
    }

    fun getQueueRemainingCount(): Int {
        val current = controller?.currentMediaItemIndex ?: 0
        val total = controller?.mediaItemCount ?: 0
        return (total - current - 1).coerceAtLeast(0)
    }

    private fun syncQueueManager(player: Player?) {
        player ?: return
        val tracks = (0 until player.mediaItemCount).mapNotNull { index ->
            player.getMediaItemAt(index).localConfiguration?.tag as? Track
        }
        if (tracks.isNotEmpty()) queueManager.setQueue(tracks, player.currentMediaItemIndex.coerceAtLeast(0))
    }

    private fun publishState(player: Player) {
        val currentTrack = player.currentMediaItem?.localConfiguration?.tag as? Track
        val repeat = when (player.repeatMode) {
            Player.REPEAT_MODE_ALL -> RepeatMode.ALL
            Player.REPEAT_MODE_ONE -> RepeatMode.ONE
            else -> RepeatMode.OFF
        }
        _state.value = PlayerState(
            currentTrack = currentTrack,
            queue = queueManager.queue.value,
            currentIndex = player.currentMediaItemIndex,
            isPlaying = player.isPlaying,
            positionMs = player.currentPosition.coerceAtLeast(0L),
            durationMs = player.duration.takeIf { it > 0 } ?: currentTrack?.durationMs ?: 0L,
            shuffleEnabled = player.shuffleModeEnabled,
            repeatMode = repeat,
            buffering = player.playbackState == Player.STATE_BUFFERING,
            errorMessage = player.playerError?.message
        )
    }
}
