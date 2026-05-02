package com.deepeye.musicpro.dsp

import android.content.Context
import android.util.Log
import com.deepeye.musicpro.BuildConfig
import com.deepeye.musicpro.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DSPManager(
    context: Context,
    private val scope: CoroutineScope
) {
    private val nativeDSP = NativeDSP()
    private val _enabled = MutableStateFlow(BuildConfig.ENABLE_DSP)
    private val _currentPreset = MutableStateFlow(DSPPreset.Flat)
    private val _currentPresetId = MutableStateFlow(DSPPreset.Flat.id)
    private val _available = MutableStateFlow(nativeDSP.available)

    val enabled: StateFlow<Boolean> = _enabled.asStateFlow()
    val currentPreset: StateFlow<DSPPreset> = _currentPreset.asStateFlow()
    val currentPresetId: StateFlow<String> = _currentPresetId.asStateFlow()
    val available: StateFlow<Boolean> = _available.asStateFlow()
    val audioProcessor = DSPAudioProcessor(nativeDSP, { _currentPreset.value }, { _enabled.value })

    fun setEnabled(enabled: Boolean) {
        _enabled.value = enabled && BuildConfig.ENABLE_DSP
        nativeDSP.applyPreset(_currentPreset.value, _enabled.value)
        Logger.d(TAG, "DSP enabled=${_enabled.value} preset=${_currentPreset.value.id}")
    }

    fun applyPreset(preset: DSPPreset) {
        _currentPreset.value = preset
        _currentPresetId.value = preset.id
        nativeDSP.applyPreset(preset, _enabled.value)
        Log.d(TAG_DSP, "applyPreset id=${preset.id} name=${preset.name}")
        Logger.d(TAG, "Applied DSP preset ${preset.id} (${preset.name}) enabled=${_enabled.value}")
    }

    fun updateEqBand(index: Int, gainDb: Float) {
        val current = _currentPreset.value
        if (index !in current.eqGains.indices) return
        val gains = current.eqGains.toMutableList()
        gains[index] = gainDb.coerceIn(-12f, 12f)
        applyPreset(current.copy(id = "custom_live", name = "Custom Live", eqGains = gains, builtIn = false))
    }

    fun release() {
        audioProcessor.reset()
    }

    companion object {
        private const val TAG = "DSPManager"
        private const val TAG_DSP = "DeepEyeDSP"
    }
}
