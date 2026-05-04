package com.deepeye.musicpro.dsp.v4a

import android.os.SystemClock
import android.util.Log
import androidx.media3.common.C
import androidx.media3.common.audio.AudioProcessor
import androidx.media3.common.audio.AudioProcessor.AudioFormat
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.Locale
import kotlin.math.log10
import kotlin.math.sqrt

class V4ABundledProcessor(
    private val nativeV4A: NativeV4A,
    private val stateProvider: () -> V4AEngineState
) : AudioProcessor {
    private var inputFormat: AudioFormat = AudioFormat.NOT_SET
    private var outputBuffer: ByteBuffer = AudioProcessor.EMPTY_BUFFER
    private var inputEnded = false
    private var active = false
    private var framesSinceMetricsLog = 0L
    private var lastMetricsLogTimeMs = 0L
    private var persistentInput: ByteBuffer? = null
    private var persistentOutput: ByteBuffer? = null
    private var floatArrayBuffer: FloatArray? = null
    private var outputArrayBuffer: FloatArray? = null

    override fun configure(inputAudioFormat: AudioFormat): AudioFormat {
        inputFormat = inputAudioFormat
        active = nativeV4A.configure(inputAudioFormat.sampleRate, inputAudioFormat.channelCount)
        val state = stateProvider()
        nativeV4A.applyState(state.effects, state.isBundledProcessorActive)
        Log.d(TAG, "configure sampleRate=${inputAudioFormat.sampleRate} channels=${inputAudioFormat.channelCount} encoding=${inputAudioFormat.encoding} active=$active mode=${state.mode} preset=${state.currentPresetName}")
        return inputAudioFormat
    }

    override fun isActive(): Boolean = active && stateProvider().isBundledProcessorActive

    override fun queueInput(inputBuffer: ByteBuffer) {
        val size = inputBuffer.remaining()
        if (size == 0) return
        val channels = inputFormat.channelCount.coerceAtLeast(1)
        if (!isActive()) {
            outputBuffer = ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder())
            val rms = calculateInputRms(outputBuffer.asReadOnlyBuffer(), size, channels)
            outputBuffer.put(inputBuffer)
            outputBuffer.flip()
            logMetrics(stateProvider(), 0, rms, rms)
            return
        }
        nativeV4A.applyState(stateProvider().effects, true)
        when (inputFormat.encoding) {
            C.ENCODING_PCM_FLOAT -> processFloat(inputBuffer, size, channels)
            C.ENCODING_PCM_16BIT -> processPcm16(inputBuffer, size, channels)
            else -> copyUnsupportedInput(inputBuffer, size)
        }
    }

    private fun processFloat(inputBuffer: ByteBuffer, size: Int, channels: Int) {
        val frames = size / (Float.SIZE_BYTES * channels)
        
        val input = persistentInput?.takeIf { it.capacity() >= size } ?: ByteBuffer.allocateDirect(size * 2).order(ByteOrder.nativeOrder()).also { persistentInput = it }
        val output = persistentOutput?.takeIf { it.capacity() >= size } ?: ByteBuffer.allocateDirect(size * 2).order(ByteOrder.nativeOrder()).also { persistentOutput = it }
        
        input.clear()
        input.put(inputBuffer)
        input.flip()
        
        val inputRms = calculatePcmFloatRms(input)
        output.clear()
        
        if (!nativeV4A.processDirect(input, output, frames)) {
            input.rewind()
            output.put(input)
        }
        
        output.flip()
        val outputRms = calculatePcmFloatRms(output)
        outputBuffer = output
        logMetrics(stateProvider(), frames, inputRms, outputRms)
    }

    private fun processPcm16(inputBuffer: ByteBuffer, size: Int, channels: Int) {
        val samples = size / Short.SIZE_BYTES
        val frames = samples / channels
        
        val input = floatArrayBuffer?.takeIf { it.size >= samples } ?: FloatArray(samples * 2).also { floatArrayBuffer = it }
        val output = outputArrayBuffer?.takeIf { it.size >= samples } ?: FloatArray(samples * 2).also { outputArrayBuffer = it }
        
        val shortInput = inputBuffer.order(ByteOrder.LITTLE_ENDIAN).asShortBuffer()
        for (index in 0 until samples) input[index] = shortInput.get(index) / 32768f
        
        val inputRms = calculateFloatArrayRms(input, samples)
        if (!nativeV4A.processFloatArray(input, output, frames)) {
            System.arraycopy(input, 0, output, 0, samples)
        }
        val outputRms = calculateFloatArrayRms(output, samples)
        
        val directOutput = persistentOutput?.takeIf { it.capacity() >= size } ?: ByteBuffer.allocateDirect(size * 2).order(ByteOrder.LITTLE_ENDIAN).also { persistentOutput = it }
        directOutput.clear()
        directOutput.order(ByteOrder.LITTLE_ENDIAN)
        
        for (i in 0 until samples) {
            val clamped = output[i].coerceIn(-1f, 0.9999695f)
            directOutput.putShort((clamped * 32768f).toInt().toShort())
        }
        
        inputBuffer.position(inputBuffer.limit())
        directOutput.flip()
        outputBuffer = directOutput
        logMetrics(stateProvider(), frames, inputRms, outputRms)
    }

    private fun copyUnsupportedInput(inputBuffer: ByteBuffer, size: Int) {
        outputBuffer = ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder())
        outputBuffer.put(inputBuffer)
        outputBuffer.flip()
        logMetrics(stateProvider(), 0, 0.0, 0.0)
    }

    private fun calculateInputRms(buffer: ByteBuffer, size: Int, channels: Int): Double {
        return when (inputFormat.encoding) {
            C.ENCODING_PCM_FLOAT -> calculatePcmFloatRms(buffer.order(ByteOrder.nativeOrder()))
            C.ENCODING_PCM_16BIT -> calculatePcm16Rms(buffer.order(ByteOrder.LITTLE_ENDIAN), size / Short.SIZE_BYTES)
            else -> 0.0
        }
    }

    private fun calculatePcmFloatRms(buffer: ByteBuffer): Double {
        val duplicate = buffer.asReadOnlyBuffer().order(ByteOrder.nativeOrder())
        val floatBuffer = duplicate.asFloatBuffer()
        if (floatBuffer.limit() == 0) return 0.0
        var sumSquares = 0.0
        for (index in 0 until floatBuffer.limit()) {
            val sample = floatBuffer.get(index).toDouble()
            sumSquares += sample * sample
        }
        return sqrt(sumSquares / floatBuffer.limit())
    }

    private fun calculatePcm16Rms(buffer: ByteBuffer, samples: Int): Double {
        if (samples <= 0) return 0.0
        val shortBuffer = buffer.asShortBuffer()
        var sumSquares = 0.0
        for (index in 0 until samples) {
            val sample = shortBuffer.get(index) / 32768.0
            sumSquares += sample * sample
        }
        return sqrt(sumSquares / samples)
    }

    private fun calculateFloatArrayRms(samples: FloatArray, count: Int): Double {
        if (count <= 0) return 0.0
        var sumSquares = 0.0
        for (i in 0 until count) {
            val sample = samples[i].toDouble()
            sumSquares += sample * sample
        }
        return sqrt(sumSquares / count)
    }

    private fun logMetrics(state: V4AEngineState, frames: Int, inputRms: Double, outputRms: Double) {
        framesSinceMetricsLog += frames.toLong().coerceAtLeast(0L)
        val sampleRate = inputFormat.sampleRate.coerceAtLeast(1)
        val now = SystemClock.elapsedRealtime()
        val shouldLog = lastMetricsLogTimeMs == 0L || framesSinceMetricsLog >= sampleRate * 2L || now - lastMetricsLogTimeMs >= 2_000L
        if (!shouldLog) return
        val deltaDb = if (inputRms > MIN_RMS && outputRms > MIN_RMS) 20.0 * log10(outputRms / inputRms) else 0.0
        val spectrum = FloatArray(32)
        nativeV4A.getSpectrumMagnitudes(spectrum)
        val spectrumPeak = spectrum.maxOrNull() ?: 0f
        Log.d(
            TAG,
            String.format(
                Locale.US,
                "V4A process preset=%s mode=%s active=%s convolver=%s ddc=%s fet=%s tube=%.2f inputRms=%.6f outputRms=%.6f deltaDb=%.2f spectrumPeak=%.3f frames=%d",
                state.currentPresetName,
                state.mode,
                state.isBundledProcessorActive,
                state.effects.convolverIR ?: "none",
                state.effects.ddcProfile ?: "none",
                state.effects.isEnabled(V4AEffect.FET),
                state.effects.tubeWarmth,
                inputRms,
                outputRms,
                deltaDb,
                spectrumPeak,
                framesSinceMetricsLog
            )
        )
        framesSinceMetricsLog = 0L
        lastMetricsLogTimeMs = now
    }

    override fun queueEndOfStream() {
        inputEnded = true
    }

    override fun getOutput(): ByteBuffer {
        val buffer = outputBuffer
        outputBuffer = AudioProcessor.EMPTY_BUFFER
        return buffer
    }

    override fun isEnded(): Boolean = inputEnded && outputBuffer === AudioProcessor.EMPTY_BUFFER

    override fun flush() {
        outputBuffer = AudioProcessor.EMPTY_BUFFER
        inputEnded = false
    }

    override fun reset() {
        flush()
        inputFormat = AudioFormat.NOT_SET
        active = false
        framesSinceMetricsLog = 0L
        lastMetricsLogTimeMs = 0L
        nativeV4A.release()
    }

    private companion object {
        const val TAG = "V4ABundledProcessor"
        const val MIN_RMS = 0.000001
    }
}
