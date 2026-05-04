package com.deepeye.musicpro.dsp

import com.deepeye.musicpro.util.Logger
import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicBoolean

class NativeDSP {
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

    fun applyPreset(preset: DSPPreset, enabled: Boolean) {
        if (handle == 0L || !available) return
        nativeSetEnabled(handle, enabled)
        nativeSetEqGains(handle, preset.eqGains.toFloatArray())
        nativeSetBassBoost(handle, preset.bassBoost)
        nativeSetLimiterCeiling(handle, preset.limiterCeilingDb)
    }

    fun setLimiterCeiling(db: Float) {
        if (handle != 0L && available) {
            nativeSetLimiterCeiling(handle, db)
        }
    }

    fun setEnabled(enabled: Boolean) {
        if (handle != 0L && available) {
            nativeSetEnabled(handle, enabled)
        }
    }

    fun reset() = synchronized(lock) {
        if (handle != 0L && available) {
            nativeReset(handle)
        }
    }

    fun processDirect(input: ByteBuffer, output: ByteBuffer, frames: Int): Boolean {
        if (handle == 0L || !available) return false
        nativeProcessDirect(handle, input, output, frames)
        return true
    }

    fun getVisualizerData(spectrum: FloatArray, waveform: FloatArray, peak: FloatArray) {
        if (handle != 0L && available) {
            nativeGetVisualizerData(handle, spectrum, waveform, peak)
        }
    }

    private external fun nativeReset(handle: Long)
    private external fun nativeCreate(sampleRate: Int, channels: Int): Long
    private external fun nativeRelease(handle: Long)
    private external fun nativeSetEnabled(handle: Long, enabled: Boolean)
    private external fun nativeSetEqEnabled(handle: Long, enabled: Boolean)
    private external fun nativeSetBassEnabled(handle: Long, enabled: Boolean)
    private external fun nativeSetWidthEnabled(handle: Long, enabled: Boolean)
    private external fun nativeSetCompressorEnabled(handle: Long, enabled: Boolean)
    private external fun nativeSetReverbEnabled(handle: Long, enabled: Boolean)
    private external fun nativeSetConvolverEnabled(handle: Long, enabled: Boolean)
    private external fun nativeSetEqGain(handle: Long, band: Int, gainDb: Float)
    private external fun nativeSetEqGains(handle: Long, gains: FloatArray)
    private external fun nativeSetBassBoost(handle: Long, amount: Float)
    private external fun nativeSetStereoWidth(handle: Long, amount: Float)
    private external fun nativeSetCompressor(handle: Long, threshold: Float, ratio: Float, attack: Float, release: Float)
    private external fun nativeSetReverb(handle: Long, room: Float, damping: Float, width: Float, wet: Float)
    private external fun nativeSetLimiterCeiling(handle: Long, ceilingDb: Float)
    private external fun nativeSetConvolverIr(handle: Long, left: FloatArray, right: FloatArray?)
    private external fun nativeProcessFloatArray(handle: Long, input: FloatArray, output: FloatArray, frames: Int)
    private external fun nativeProcessDirect(handle: Long, input: ByteBuffer, output: ByteBuffer, frames: Int)
    private external fun nativeGetVisualizerData(handle: Long, spectrum: FloatArray, waveform: FloatArray, peak: FloatArray)

    companion object {
        private val libraryLoaded = AtomicBoolean(false)

        init {
            try {
                System.loadLibrary("deepeye_dsp")
                libraryLoaded.set(true)
            } catch (error: UnsatisfiedLinkError) {
                Logger.e("NativeDSP", "Native DSP unavailable", error)
                libraryLoaded.set(false)
            }
        }
    }
}
