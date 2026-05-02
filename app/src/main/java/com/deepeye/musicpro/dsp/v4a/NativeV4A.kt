package com.deepeye.musicpro.dsp.v4a

import com.deepeye.musicpro.util.Logger
import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicBoolean

class NativeV4A {
    @Volatile
    private var handle: Long = 0L
    private val lock = Any()

    val available: Boolean
        get() = libraryLoaded.get()

    fun configure(sampleRate: Int, channels: Int): Boolean = synchronized(lock) {
        release()
        if (!available) return false
        handle = nativeCreate(sampleRate, channels)
        handle != 0L
    }

    fun release() = synchronized(lock) {
        if (handle != 0L && available) {
            nativeRelease(handle)
            handle = 0L
        }
    }

    fun applyState(state: V4AEffectsState, active: Boolean) = synchronized(lock) {
        if (handle == 0L || !available) return
        nativeSetMasterEnabled(handle, active && state.masterEnabled)
        V4AEffect.values().forEach { effect -> nativeSetEffectEnabled(handle, effect.ordinal, state.isEnabled(effect)) }
        nativeSetEqGains(handle, state.normalizedEqGains())
        nativeSetTubeWarmth(handle, state.tubeWarmth)
        nativeSetReverb(handle, state.reverbRoom, 0.55f, 0.65f, if (state.isEnabled(V4AEffect.REVERB)) 0.16f else 0f)
        setFETParams(state.fetAttack, state.fetRelease, state.fetRatio, state.fetThreshold, state.fetKnee)
    }

    fun processDirect(input: ByteBuffer, output: ByteBuffer, frames: Int): Boolean = synchronized(lock) {
        if (handle == 0L || !available) return false
        nativeProcessDirect(handle, input, output, frames)
        true
    }

    fun processFloatArray(input: FloatArray, output: FloatArray, frames: Int): Boolean = synchronized(lock) {
        if (handle == 0L || !available) return false
        nativeProcessFloatArray(handle, input, output, frames)
        true
    }

    @Synchronized
    external fun loadConvolverIR(path: String)

    @Synchronized
    external fun loadDDCProfile(path: String)

    @Synchronized
    external fun setFETParams(attack: Float, release: Float, ratio: Float, threshold: Float, knee: Float)

    @Synchronized
    external fun getSpectrumMagnitudes(out: FloatArray)

    private external fun nativeCreate(sampleRate: Int, channels: Int): Long
    private external fun nativeRelease(handle: Long)
    private external fun nativeSetMasterEnabled(handle: Long, enabled: Boolean)
    private external fun nativeSetEffectEnabled(handle: Long, effectOrdinal: Int, enabled: Boolean)
    private external fun nativeSetEqGains(handle: Long, gains: FloatArray)
    private external fun nativeSetTubeWarmth(handle: Long, warmth: Float)
    private external fun nativeSetReverb(handle: Long, room: Float, damping: Float, width: Float, wet: Float)
    private external fun nativeProcessFloatArray(handle: Long, input: FloatArray, output: FloatArray, frames: Int)
    private external fun nativeProcessDirect(handle: Long, input: ByteBuffer, output: ByteBuffer, frames: Int)

    companion object {
        private val libraryLoaded = AtomicBoolean(false)

        init {
            try {
                System.loadLibrary("deepeye_dsp")
                libraryLoaded.set(true)
            } catch (error: UnsatisfiedLinkError) {
                Logger.e("NativeV4A", "Native V4A unavailable", error)
                libraryLoaded.set(false)
            }
        }
    }
}
