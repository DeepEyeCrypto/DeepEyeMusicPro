package com.deepeye.musicpro.player

import android.content.Context
import androidx.media3.common.audio.AudioProcessor
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.audio.AudioSink
import androidx.media3.exoplayer.audio.DefaultAudioSink
import com.deepeye.musicpro.dsp.DSPAudioProcessor
import com.deepeye.musicpro.dsp.DSPPreset
import com.deepeye.musicpro.dsp.NativeDSP

/**
 * Helper to integrate DeepEye DSP into ExoPlayer pipeline.
 */
object ExoPlayerDSPIntegration {

    fun createPlayer(
        context: Context,
        nativeDSP: NativeDSP,
        presetProvider: () -> DSPPreset,
        enabledProvider: () -> Boolean
    ): ExoPlayer {
        
        val dspProcessor = DSPAudioProcessor(nativeDSP, presetProvider, enabledProvider)

        val renderersFactory = object : DefaultRenderersFactory(context) {
            override fun buildAudioSink(
                context: Context,
                enableFloatOutput: Boolean,
                enableAudioTrackPlaybackParams: Boolean
            ): AudioSink? {
                return DefaultAudioSink.Builder(context)
                    .setAudioProcessors(arrayOf(dspProcessor))
                    .build()
            }
        }

        return ExoPlayer.Builder(context, renderersFactory).build()
    }
}
