package com.deepeye.musicpro.ui.dsp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.deepeye.musicpro.DeepEyeApp
import com.deepeye.musicpro.dsp.DSPPreset
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DspViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = DeepEyeApp.from(application).dspRepository
    val presets = repo.observePresets().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DSPPreset.BuiltIns)
    val currentPreset = repo.dspManager.currentPreset
    val enabled = repo.dspManager.enabled
    val available = repo.dspManager.available

    init { viewModelScope.launch { repo.seedBuiltIns() } }
    fun setEnabled(enabled: Boolean) = repo.dspManager.setEnabled(enabled)
    fun applyPreset(preset: DSPPreset) = repo.dspManager.applyPreset(preset)
    fun updateEqBand(index: Int, gain: Float) = repo.dspManager.updateEqBand(index, gain)
    fun saveCustom(preset: DSPPreset) = viewModelScope.launch { repo.saveCustom(preset.copy(builtIn = false)) }
}
