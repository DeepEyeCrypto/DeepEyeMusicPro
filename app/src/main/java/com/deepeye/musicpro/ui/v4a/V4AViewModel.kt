package com.deepeye.musicpro.ui.v4a

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.deepeye.musicpro.DeepEyeApp
import com.deepeye.musicpro.dsp.v4a.DDCProfile
import com.deepeye.musicpro.dsp.v4a.V4AAssetLoader
import com.deepeye.musicpro.dsp.v4a.V4AEffect
import com.deepeye.musicpro.dsp.v4a.V4APreset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File

class V4AViewModel(application: Application) : AndroidViewModel(application) {
    private val app = DeepEyeApp.from(application)
    private val engine = app.v4aEngine
    private val database = app.database
    private val _irsFiles = MutableStateFlow<List<File>>(emptyList())
    private val _ddcFiles = MutableStateFlow<List<File>>(emptyList())

    val engineState = engine.state
    val irsFiles: StateFlow<List<File>> = _irsFiles
    val ddcFiles: StateFlow<List<File>> = _ddcFiles
    val presets: StateFlow<List<V4APreset>> = database.v4aPresetDao().observePresets()
        .map { rows -> if (rows.isEmpty()) V4APreset.BuiltIns else rows.map(V4APreset::fromEntity) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, V4APreset.BuiltIns)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            V4AAssetLoader.copyAssetsToCache(application)
            database.v4aPresetDao().upsertAll(V4APreset.BuiltIns.map { it.toEntity() })
            refreshAssets()
        }
    }

    fun setMasterEnabled(enabled: Boolean) = engine.setMasterEnabled(enabled)
    fun setEffectEnabled(effect: V4AEffect, enabled: Boolean) = engine.setEffectEnabled(effect, enabled)
    fun applyPreset(preset: V4APreset) = engine.applyPreset(preset)
    fun selectConvolver(file: File) = engine.setConvolverIR(file)

    fun selectDDC(file: File) {
        val profile = DDCProfile.BuiltIns.firstOrNull { it.assetName == file.name } ?: DDCProfile(file.nameWithoutExtension, file.nameWithoutExtension.replace('_', ' '), file.name)
        engine.setDDCProfile(profile)
    }

    fun setFET(attack: Float, release: Float, threshold: Float, knee: Float) {
        val current = engineState.value.effects
        engine.setFETParams(attack, release, current.fetRatio, threshold, knee)
    }

    fun setTubeWarmth(warmth: Float) = engine.setTubeWarmth(warmth)

    fun getSpectrumMagnitudes(out: FloatArray) = engine.getSpectrumMagnitudes(out)

    fun saveCurrentPreset(name: String = "Custom V4A") {
        val current = engineState.value
        val preset = V4APreset(
            id = "custom_${System.currentTimeMillis()}",
            name = name,
            isBuiltIn = false,
            effects = current.effects
        )
        viewModelScope.launch(Dispatchers.IO) { database.v4aPresetDao().upsert(preset.toEntity()) }
    }

    private fun refreshAssets() {
        _irsFiles.value = V4AAssetLoader.listCachedFiles(getApplication(), "irs", "irs")
        _ddcFiles.value = V4AAssetLoader.listCachedFiles(getApplication(), "ddc", "ddc")
    }
}
