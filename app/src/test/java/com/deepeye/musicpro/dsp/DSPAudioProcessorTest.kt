package com.deepeye.musicpro.dsp

import androidx.media3.common.C
import androidx.media3.common.audio.AudioProcessor
import androidx.media3.common.audio.AudioProcessor.AudioFormat
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import java.nio.ByteBuffer
import java.nio.ByteOrder

class DSPAudioProcessorTest {

    private lateinit var nativeDSP: NativeDSP
    private lateinit var processor: DSPAudioProcessor
    private var isEnabled = true

    @Before
    fun setup() {
        nativeDSP = mock(NativeDSP::class.java)
        `when`(nativeDSP.configure(anyInt(), anyInt())).thenReturn(true)
        
        processor = DSPAudioProcessor(
            nativeDSP,
            { DSPPreset.CLEAN_BASS },
            { isEnabled }
        )
    }

    @Test
    fun testConfigureContract() {
        val format = AudioFormat(44100, 2, C.ENCODING_PCM_FLOAT)
        val outputFormat = processor.configure(format)
        
        assertEquals(format.sampleRate, outputFormat.sampleRate)
        assertEquals(format.channelCount, outputFormat.channelCount)
        assertEquals(format.encoding, outputFormat.encoding)
        verify(nativeDSP).configure(44100, 2)
    }

    @Test
    fun testPassthroughWhenDisabled() {
        isEnabled = false
        val format = AudioFormat(48000, 2, C.ENCODING_PCM_FLOAT)
        processor.configure(format)
        
        val input = ByteBuffer.allocateDirect(1024).order(ByteOrder.nativeOrder())
        for (i in 0 until 256) input.putFloat(0.5f)
        input.flip()
        
        processor.queueInput(input)
        val output = processor.getOutput()
        
        assertEquals(1024, output.remaining())
        assertEquals(0.5f, output.getFloat(), 0.0001f)
        verify(nativeDSP, never()).processDirect(any(), any(), anyInt())
    }

    @Test
    fun testActiveState() {
        assertFalse(processor.isActive) // Not configured
        
        processor.configure(AudioFormat(44100, 2, C.ENCODING_PCM_FLOAT))
        assertTrue(processor.isActive)
        
        isEnabled = false
        // Even if disabled, processor might stay active to handle format consistency
        // but our implementation returns enabledProvider()
        assertFalse(processor.isActive)
    }

    @Test
    fun testFlushResetsState() {
        processor.queueEndOfStream()
        assertTrue(processor.isEnded)
        
        processor.flush()
        assertFalse(processor.isEnded)
    }
}
