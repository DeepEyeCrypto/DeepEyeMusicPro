package com.deepeye.musicpro.dsp

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

class DSPAudioProcessor(
    private val nativeDSP: NativeDSP,
    private val presetProvider: () -> DSPPreset,
    private val enabledProvider: () -> Boolean
) : AudioProcessor {
    private var inputFormat: AudioFormat = AudioFormat.NOT_SET
    private var outputBuffer: ByteBuffer = AudioProcessor.EMPTY_BUFFER
    private var inputEnded = false
    private var active = false
    private var framesSinceMetricsLog = 0L
    private var lastMetricsLogTimeMs = 0L

    override fun configure(inputAudioFormat: AudioFormat): AudioFormat {
        inputFormat = inputAudioFormat
        active = nativeDSP.configure(inputAudioFormat.sampleRate, inputAudioFormat.channelCount)
        nativeDSP.applyPreset(presetProvider(), enabledProvider())
        return inputAudioFormat
    }

    override fun isActive(): Boolean = active && enabledProvider()

    override fun queueInput(inputBuffer: ByteBuffer) {
        val size = inputBuffer.remaining()
        if (size == 0) return
        val channels = inputFormat.channelCount.coerceAtLeast(1)
        val frames = calculateFrameCount(size, channels)
        if (!isActive()) {
            val rms = calculateInputRms(inputBuffer, size, channels)
            outputBuffer = ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder())
            outputBuffer.put(inputBuffer)
            outputBuffer.flip()
            logMetrics(presetProvider().id, enabledProvider(), frames, rms, rms)
            return
        }
        val preset = presetProvider()
        nativeDSP.applyPreset(preset, true)
        val metrics = when (inputFormat.encoding) {
            C.ENCODING_PCM_FLOAT -> processFloat(inputBuffer, size, channels)
            C.ENCODING_PCM_16BIT -> processPcm16(inputBuffer, size, channels)
            else -> copyUnsupportedInput(inputBuffer, size, frames)
        }
        logMetrics(preset.id, true, metrics.frames, metrics.inputRms, metrics.outputRms)
    }

    private fun processFloat(inputBuffer: ByteBuffer, size: Int, channels: Int): RmsMetrics {
        val frames = size / (Float.SIZE_BYTES * channels)
        val directInput = ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder())
        val directOutput = ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder())
        directInput.put(inputBuffer)
        directInput.flip()
        val inputRms = calculatePcmFloatRms(directInput)
        if (!nativeDSP.processDirect(directInput, directOutput, frames)) {
            directOutput.clear()
            directInput.rewind()
            directOutput.put(directInput)
        }
        directOutput.position(size)
        directOutput.flip()
        val outputRms = calculatePcmFloatRms(directOutput)
        outputBuffer = directOutput
        return RmsMetrics(frames, inputRms, outputRms)
    }

    private fun processPcm16(inputBuffer: ByteBuffer, size: Int, channels: Int): RmsMetrics {
        val samples = size / Short.SIZE_BYTES
        val frames = samples / channels
        val input = FloatArray(samples)
        val output = FloatArray(samples)
        val shortInput = inputBuffer.order(ByteOrder.LITTLE_ENDIAN).asShortBuffer()
        for (i in 0 until samples) input[i] = shortInput.get(i) / 32768f
        val inputRms = calculateFloatArrayRms(input)
        if (!nativeDSP.processFloatArray(input, output, frames)) input.copyInto(output)
        val outputRms = calculateFloatArrayRms(output)
        val directOutput = ByteBuffer.allocateDirect(size).order(ByteOrder.LITTLE_ENDIAN)
        for (sample in output) {
            val clamped = sample.coerceIn(-1f, 0.9999695f)
            directOutput.putShort((clamped * 32768f).toInt().toShort())
        }
        inputBuffer.position(inputBuffer.limit())
        directOutput.flip()
        outputBuffer = directOutput
        return RmsMetrics(frames, inputRms, outputRms)
    }

    private fun copyUnsupportedInput(inputBuffer: ByteBuffer, size: Int, frames: Int): RmsMetrics {
        outputBuffer = ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder())
        outputBuffer.put(inputBuffer)
        outputBuffer.flip()
        return RmsMetrics(frames, 0.0, 0.0)
    }

    private fun calculateFrameCount(size: Int, channels: Int): Int {
        when (inputFormat.encoding) {
            C.ENCODING_PCM_FLOAT -> return size / (Float.SIZE_BYTES * channels)
            C.ENCODING_PCM_16BIT -> return size / (Short.SIZE_BYTES * channels)
            else -> return 0
        }
    }

    private fun calculateInputRms(inputBuffer: ByteBuffer, size: Int, channels: Int): Double {
        return when (inputFormat.encoding) {
            C.ENCODING_PCM_FLOAT -> calculatePcmFloatRms(inputBuffer.asReadOnlyBuffer().order(ByteOrder.nativeOrder()))
            C.ENCODING_PCM_16BIT -> calculatePcm16Rms(inputBuffer.asReadOnlyBuffer().order(ByteOrder.LITTLE_ENDIAN), size / Short.SIZE_BYTES)
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

    private fun calculateFloatArrayRms(samples: FloatArray): Double {
        if (samples.isEmpty()) return 0.0
        var sumSquares = 0.0
        for (sampleValue in samples) {
            val sample = sampleValue.toDouble()
            sumSquares += sample * sample
        }
        return sqrt(sumSquares / samples.size)
    }

    private fun logMetrics(presetId: String, enabled: Boolean, frames: Int, inputRms: Double, outputRms: Double) {
        framesSinceMetricsLog += frames.toLong().coerceAtLeast(0L)
        val sampleRate = inputFormat.sampleRate.coerceAtLeast(1)
        val now = SystemClock.elapsedRealtime()
        val shouldLog = lastMetricsLogTimeMs == 0L || framesSinceMetricsLog >= sampleRate * 5L || now - lastMetricsLogTimeMs >= 5_000L
        if (!shouldLog) return
        val deltaDb = if (inputRms > MIN_RMS && outputRms > MIN_RMS) 20.0 * log10(outputRms / inputRms) else 0.0
        Log.d(
            TAG,
            String.format(
                Locale.US,
                "preset=%s enabled=%s inputRms=%.6f outputRms=%.6f deltaDb=%.2f frames=%d",
                presetId,
                enabled,
                inputRms,
                outputRms,
                deltaDb,
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
        nativeDSP.release()
    }

    private data class RmsMetrics(val frames: Int, val inputRms: Double, val outputRms: Double)

    private companion object {
        private const val TAG = "DeepEyeDSP"
        private const val MIN_RMS = 0.000001
    }
}
