package com.deepeye.musicpro.player

import com.deepeye.musicpro.extractor.RelatedExtractor
import com.deepeye.musicpro.extractor.SearchService
import com.deepeye.musicpro.repository.SettingsRepository
import com.deepeye.musicpro.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AutoRadioEngine(
    private val playerController: PlayerController,
    private val scope: CoroutineScope,
    private val settingsRepository: SettingsRepository,
    private val searchService: SearchService
) {
    companion object {
        private const val TAG = "AutoRadioEngine"
    }

    private val _isEnabled = MutableStateFlow(true)
    val isEnabled: StateFlow<Boolean> = _isEnabled
    
    private val _isFetching = MutableStateFlow(false)
    val isFetching: StateFlow<Boolean> = _isFetching
    
    private var lastRelatedVideoId: String? = null

    init {
        scope.launch {
            settingsRepository.settings.collect { settings ->
                _isEnabled.value = settings.autoRadioEnabled
            }
        }
    }

    fun setEnabled(enabled: Boolean) {
        _isEnabled.value = enabled
        scope.launch {
            settingsRepository.setAutoRadioEnabled(enabled)
        }
        Logger.i(TAG, "Auto-radio ${if (enabled) "enabled" else "disabled"}")
    }

    fun onTrackStarted(videoId: String) {
        if (!_isEnabled.value) {
            Logger.d(TAG, "onTrackStarted: Auto-radio is disabled")
            return
        }
        Logger.d(TAG, "onTrackStarted: videoId=$videoId")
        scope.launch {
            _isFetching.value = true
            val settings = settingsRepository.settings.first()
            val result = RelatedExtractor.fetchRelated(videoId, if (settings.radioSource == "related") null else searchService)
            result.onSuccess { related ->
                if (related.isNotEmpty()) {
                    playerController.enqueueRelated(related)
                    lastRelatedVideoId = related.lastOrNull()?.track?.id
                    Logger.i(TAG, "Auto-queued ${related.size} related songs")
                } else {
                    Logger.w(TAG, "No related songs found for $videoId")
                }
            }
            result.onFailure {
                Logger.e(TAG, "Failed to fetch related for $videoId", it)
            }
            _isFetching.value = false
        }
    }

    fun onQueueLow(remainingCount: Int) {
        val threshold = 3 // Could be made configurable
        if (!_isEnabled.value || remainingCount > threshold) return
        val seedId = lastRelatedVideoId ?: playerController.getCurrentVideoId() ?: return
        scope.launch {
            _isFetching.value = true
            val settings = settingsRepository.settings.first()
            val result = RelatedExtractor.fetchRelated(seedId, searchService)
            result.onSuccess { more ->
                if (more.isNotEmpty()) {
                    playerController.enqueueRelated(more)
                    lastRelatedVideoId = more.lastOrNull()?.track?.id
                    Logger.i(TAG, "Prefetched ${more.size} more songs, queue extended")
                }
            }
            _isFetching.value = false
        }
    }
}
