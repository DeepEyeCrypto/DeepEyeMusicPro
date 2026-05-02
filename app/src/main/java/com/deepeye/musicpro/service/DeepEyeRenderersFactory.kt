package com.deepeye.musicpro.service

import android.content.Context
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.audio.AudioSink
import androidx.media3.exoplayer.audio.DefaultAudioSink
import com.deepeye.musicpro.dsp.DSPManager
import com.deepeye.musicpro.dsp.v4a.V4AEngine

class DeepEyeRenderersFactory(
    context: Context,
    private val dspManager: DSPManager,
    private val v4aEngine: V4AEngine
) : DefaultRenderersFactory(context) {
    private val appContext = context.applicationContext

    override fun buildAudioSink(context: Context, enableFloatOutput: Boolean, enableAudioTrackPlaybackParams: Boolean): AudioSink {
        @Suppress("UNUSED_VARIABLE") val floatOutputRequested = enableFloatOutput
        @Suppress("UNUSED_VARIABLE") val playbackParamsRequested = enableAudioTrackPlaybackParams
        val chain = DefaultAudioSink.DefaultAudioProcessorChain(dspManager.audioProcessor, v4aEngine.audioProcessor)
        return DefaultAudioSink.Builder(appContext)
            .setAudioProcessorChain(chain)
            .build()
    }
}
