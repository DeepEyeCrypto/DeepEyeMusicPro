package com.deepeye.musicpro.dsp

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * High-performance buffer management for real-time audio.
 * Focuses on zero-allocation reuse and direct memory access.
 */
class BufferLifecycleHelper {

    private var outputBuffer: ByteBuffer = ByteBuffer.allocateDirect(0).order(ByteOrder.nativeOrder())

    /**
     * Ensures the output buffer has enough capacity.
     * Re-allocates only if absolutely necessary (e.g., format change).
     */
    fun ensureCapacity(requiredBytes: Int): ByteBuffer {
        if (outputBuffer.capacity() < requiredBytes) {
            // Allocate slightly more to avoid frequent re-allocations if size fluctuates
            // Align to 1024 byte boundaries for efficiency
            val alignedBytes = ((requiredBytes + 1023) / 1024) * 1024
            outputBuffer = ByteBuffer.allocateDirect(alignedBytes)
                .order(ByteOrder.nativeOrder())
        }
        outputBuffer.clear()
        return outputBuffer
    }

    /**
     * Clears and returns the current output buffer.
     */
    fun getBuffer(): ByteBuffer {
        outputBuffer.clear()
        return outputBuffer
    }

    /**
     * Resets the buffer to empty state.
     */
    fun reset() {
        outputBuffer = ByteBuffer.allocateDirect(0).order(ByteOrder.nativeOrder())
    }
}
