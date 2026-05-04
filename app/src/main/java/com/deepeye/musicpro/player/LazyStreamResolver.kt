package com.deepeye.musicpro.player

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.deepeye.musicpro.extractor.StreamExtractor
import com.deepeye.musicpro.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.min

class LazyStreamResolver(
    private val streamExtractor: StreamExtractor,
    private val scope: CoroutineScope,
    private val playerController: PlayerController // Still useful for tags, but we'll use Player for queue ops
) : Player.Listener {
    
    private val resolvingIds = HashSet<String>()
    private val TAG = "LazyStreamResolver"

    override fun onEvents(player: Player, events: Player.Events) {
        if (events.contains(Player.EVENT_MEDIA_ITEM_TRANSITION) || 
            events.contains(Player.EVENT_TIMELINE_CHANGED) ||
            events.contains(Player.EVENT_PLAYBACK_STATE_CHANGED)) {
            resolveAhead(player)
        }
    }

    private fun resolveAhead(player: Player) {
        val currentIndex = player.currentMediaItemIndex
        val total = player.mediaItemCount
        val lookahead = 3

        for (i in currentIndex until min(currentIndex + 1 + lookahead, total)) {
            val item = player.getMediaItemAt(i)
            val videoId = item.mediaId
            if (resolvingIds.contains(videoId)) continue

            if (item.localConfiguration?.uri?.toString()?.startsWith("placeholder") == true) {
                resolvingIds.add(videoId)
                Logger.d(TAG, "Resolving placeholder $videoId at index $i")
                scope.launch {
                    streamExtractor.extract(videoId).onSuccess { extraction ->
                        val streamUrl = extraction.selectedFormat?.url ?: extraction.track.streamUrl
                        if (streamUrl.isNullOrBlank()) {
                            Logger.w(TAG, "Resolved URI is empty for $videoId, skipping track")
                            if (i == player.currentMediaItemIndex) player.seekToNextMediaItem()
                            return@onSuccess
                        }

                        val resolvedItem = MediaItem.Builder()
                            .setMediaId(videoId)
                            .setUri(streamUrl)
                            .setMediaMetadata(item.mediaMetadata)
                            .setTag(extraction.track)
                            .build()
                        
                        // Replace in queue
                        try {
                            val isCurrent = i == player.currentMediaItemIndex
                            player.replaceMediaItem(i, resolvedItem)
                            if (isCurrent && (player.playbackState == Player.STATE_IDLE || !player.playWhenReady)) {
                                player.prepare()
                                player.play()
                            }
                            Logger.i(TAG, "Resolved $videoId → ready to play ${if (isCurrent) "(CURRENT)" else ""}")
                        } catch (e: Exception) {
                            Logger.w(TAG, "Failed to replace $videoId in queue", e)
                        }
                    }.onFailure {
                        Logger.w(TAG, "Failed to resolve $videoId: ${it.message}")
                        if (i == player.currentMediaItemIndex) {
                            player.seekToNextMediaItem()
                        }
                    }
                    resolvingIds.remove(videoId)
                }
            }
        }
    }
}
