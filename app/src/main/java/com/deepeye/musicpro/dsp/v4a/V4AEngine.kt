package com.deepeye.musicpro.dsp.v4a

import android.content.Context
import android.media.audiofx.AudioEffect
import android.util.Log
import androidx.media3.common.C
import com.deepeye.musicpro.util.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File

class V4AEngine(context: Context) {
    private val appContext = context.applicationContext
    private val nativeV4A = NativeV4A()
    private val lock = Any()
    private var systemEffect: AudioEffect? = null
    private val _state = MutableStateFlow(V4AEngineState())

    val state: StateFlow<V4AEngineState> = _state.asStateFlow()
    val audioProcessor = V4ABundledProcessor(nativeV4A) { _state.value }

    fun init(audioSessionId: Int = C.AUDIO_SESSION_ID_UNSET) = synchronized(lock) {
        val detected = V4ADetector.isV4AInstalled()
        if (detected && audioSessionId != C.AUDIO_SESSION_ID_UNSET && attachSystemV4A(audioSessionId)) {
            _state.value = _state.value.copy(
                initialized = true,
                active = _state.value.effects.masterEnabled,
                mode = V4AEngineMode.SYSTEM,
                systemDetected = true,
                audioSessionId = audioSessionId,
                message = "System V4A detected — hardware processing active"
            )
        } else {
            activateBundledV4A(audioSessionId, detected)
        }
    }

    fun setMasterEnabled(enabled: Boolean) = synchronized(lock) {
        val effects = _state.value.effects.copy(masterEnabled = enabled)
        systemEffect?.enabled = enabled && _state.value.mode == V4AEngineMode.SYSTEM
        nativeV4A.applyState(effects, _state.value.mode == V4AEngineMode.BUNDLED && enabled)
        _state.value = _state.value.copy(active = enabled, effects = effects)
    }

    fun setEffectEnabled(effect: V4AEffect, enabled: Boolean) = synchronized(lock) {
        val effects = _state.value.effects.withEffect(effect, enabled)
        nativeV4A.applyState(effects, _state.value.mode == V4AEngineMode.BUNDLED && effects.masterEnabled)
        _state.value = _state.value.copy(effects = effects)
    }

    fun setConvolverIR(irFile: File) = synchronized(lock) {
        if (irFile.isFile) {
            nativeV4A.loadConvolverIR(irFile.absolutePath)
            Log.d(TAG, "loadConvolverIR file=${irFile.name} path=${irFile.absolutePath}")
        }
        val effects = _state.value.effects.copy(convolverIR = irFile.name).withEffect(V4AEffect.CONVOLVER, true)
        nativeV4A.applyState(effects, _state.value.mode == V4AEngineMode.BUNDLED && effects.masterEnabled)
        _state.value = _state.value.copy(effects = effects)
    }

    fun setDDCProfile(profile: DDCProfile) = synchronized(lock) {
        val file = V4AAssetLoader.cachedFile(appContext, "ddc", profile.assetName)
        if (file.isFile) {
            nativeV4A.loadDDCProfile(file.absolutePath)
            Log.d(TAG, "loadDDCProfile file=${file.name} path=${file.absolutePath}")
        }
        val effects = _state.value.effects.copy(ddcProfile = profile.assetName).withEffect(V4AEffect.DDC, true)
        nativeV4A.applyState(effects, _state.value.mode == V4AEngineMode.BUNDLED && effects.masterEnabled)
        _state.value = _state.value.copy(effects = effects)
    }

    fun setFETParams(attack: Float, release: Float, ratio: Float, threshold: Float, knee: Float) = synchronized(lock) {
        nativeV4A.setFETParams(attack, release, ratio, threshold, knee)
        Log.d(TAG, "setFETParams attack=$attack release=$release ratio=$ratio threshold=$threshold knee=$knee")
        val effects = _state.value.effects.copy(
            fetAttack = attack,
            fetRelease = release,
            fetRatio = ratio,
            fetThreshold = threshold,
            fetKnee = knee
        ).withEffect(V4AEffect.FET, true)
        _state.value = _state.value.copy(effects = effects)
    }

    fun setTubeWarmth(warmth: Float) = synchronized(lock) {
        val effects = _state.value.effects.copy(tubeWarmth = warmth.coerceIn(0f, 1f)).withEffect(V4AEffect.TUBE, warmth > 0f)
        nativeV4A.applyState(effects, _state.value.mode == V4AEngineMode.BUNDLED && effects.masterEnabled)
        Log.i(TAG, "Tube warmth=%.2f".format(warmth))
        _state.value = _state.value.copy(effects = effects)
    }

    fun setTubeEnabled(enabled: Boolean) = synchronized(lock) {
        val effects = _state.value.effects.withEffect(V4AEffect.TUBE, enabled)
        nativeV4A.applyState(effects, _state.value.mode == V4AEngineMode.BUNDLED && effects.masterEnabled)
        _state.value = _state.value.copy(effects = effects)
    }

    fun applyPreset(preset: V4APreset) = synchronized(lock) {
        val effects = preset.effects.copy(masterEnabled = _state.value.effects.masterEnabled)
        effects.convolverIR?.let { name ->
            V4AAssetLoader.cachedFile(appContext, "irs", name).takeIf(File::isFile)?.let { nativeV4A.loadConvolverIR(it.absolutePath) }
        }
        effects.ddcProfile?.let { name ->
            V4AAssetLoader.cachedFile(appContext, "ddc", name).takeIf(File::isFile)?.let { nativeV4A.loadDDCProfile(it.absolutePath) }
        }
        nativeV4A.applyState(effects, _state.value.mode == V4AEngineMode.BUNDLED && effects.masterEnabled)
        Log.d(TAG, "applyPreset id=${preset.id} name=${preset.name} convolver=${effects.convolverIR ?: "none"} ddc=${effects.ddcProfile ?: "none"} fet=${effects.isEnabled(V4AEffect.FET)} tube=${effects.tubeWarmth} master=${effects.masterEnabled}")
        _state.value = _state.value.copy(
            active = effects.masterEnabled,
            currentPresetName = preset.name,
            effects = effects
        )
    }

    fun getSpectrumMagnitudes(out: FloatArray) = synchronized(lock) {
        nativeV4A.getSpectrumMagnitudes(out)
    }

    fun release() = synchronized(lock) {
        systemEffect?.release()
        systemEffect = null
        audioProcessor.reset()
        nativeV4A.release()
        _state.value = _state.value.copy(initialized = false, active = false)
    }

    private fun attachSystemV4A(audioSessionId: Int): Boolean {
        val descriptor = V4ADetector.findV4ADescriptor() ?: return false
        return runCatching {
            systemEffect?.release()
            val constructor = AudioEffect::class.java.getConstructor(
                java.util.UUID::class.java,
                java.util.UUID::class.java,
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType
            )
            systemEffect = (constructor.newInstance(descriptor.type, descriptor.uuid, 0, audioSessionId) as AudioEffect).apply {
                enabled = _state.value.effects.masterEnabled
            }
            true
        }.onFailure { error ->
            Logger.e(TAG, "System V4A attach failed; using bundled V4A", error)
        }.getOrDefault(false)
    }

    private fun activateBundledV4A(audioSessionId: Int, systemDetected: Boolean) {
        systemEffect?.release()
        systemEffect = null
        val effects = _state.value.effects
        nativeV4A.applyState(effects, effects.masterEnabled)
        _state.value = _state.value.copy(
            initialized = true,
            active = effects.masterEnabled,
            mode = V4AEngineMode.BUNDLED,
            systemDetected = systemDetected,
            audioSessionId = audioSessionId,
            message = "Bundled V4A engine — software processing"
        )
    }

    private companion object {
        const val TAG = "V4AEngine"
    }
}
