package com.deepeye.musicpro.ui.visualizer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepeye.musicpro.dsp.DSPManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class VisualizerMode {
    BARS, WAVEFORM, CIRCULAR
}

class VisualizerViewModel(private val dspManager: DSPManager) : ViewModel() {
    private val _spectrum = MutableStateFlow(FloatArray(64))
    val spectrum: StateFlow<FloatArray> = _spectrum.asStateFlow()

    private val _waveform = MutableStateFlow(FloatArray(512))
    val waveform: StateFlow<FloatArray> = _waveform.asStateFlow()

    private val _peak = MutableStateFlow(0f)
    val peak: StateFlow<Float> = _peak.asStateFlow()

    private val _mode = MutableStateFlow(VisualizerMode.BARS)
    val mode: StateFlow<VisualizerMode> = _mode.asStateFlow()

    private var pollingJob: Job? = null
    private val spectrumBuffer = FloatArray(64)
    private val waveformBuffer = FloatArray(512)
    private val peakBuffer = FloatArray(1)

    init {
        startPolling()
    }

    fun setMode(mode: VisualizerMode) {
        _mode.value = mode
    }

    fun nextMode() {
        val modes = VisualizerMode.values()
        _mode.value = modes[(_mode.value.ordinal + 1) % modes.size]
    }

    private fun startPolling() {
        pollingJob?.cancel()
        pollingJob = viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                // Poll at ~30 FPS to save battery while remaining smooth
                dspManager.getVisualizerData(spectrumBuffer, waveformBuffer, peakBuffer)
                
                // Update flows
                _spectrum.value = spectrumBuffer.copyOf()
                _waveform.value = waveformBuffer.copyOf()
                _peak.value = peakBuffer[0]
                
                delay(33) 
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        pollingJob?.cancel()
    }
}
