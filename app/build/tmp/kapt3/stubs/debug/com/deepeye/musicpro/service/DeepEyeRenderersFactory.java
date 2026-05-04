package com.deepeye.musicpro.service;

import android.content.Context;
import androidx.media3.exoplayer.DefaultRenderersFactory;
import androidx.media3.exoplayer.audio.AudioSink;
import androidx.media3.exoplayer.audio.DefaultAudioSink;
import com.deepeye.musicpro.dsp.DSPManager;
import com.deepeye.musicpro.dsp.v4a.V4AEngine;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ \u0010\u000b\u001a\u00020\f2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0014R\u0016\u0010\t\u001a\n \n*\u0004\u0018\u00010\u00030\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/deepeye/musicpro/service/DeepEyeRenderersFactory;", "Landroidx/media3/exoplayer/DefaultRenderersFactory;", "context", "Landroid/content/Context;", "dspManager", "Lcom/deepeye/musicpro/dsp/DSPManager;", "v4aEngine", "Lcom/deepeye/musicpro/dsp/v4a/V4AEngine;", "(Landroid/content/Context;Lcom/deepeye/musicpro/dsp/DSPManager;Lcom/deepeye/musicpro/dsp/v4a/V4AEngine;)V", "appContext", "kotlin.jvm.PlatformType", "buildAudioSink", "Landroidx/media3/exoplayer/audio/AudioSink;", "enableFloatOutput", "", "enableAudioTrackPlaybackParams", "app_debug"})
public final class DeepEyeRenderersFactory extends androidx.media3.exoplayer.DefaultRenderersFactory {
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.dsp.DSPManager dspManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.dsp.v4a.V4AEngine v4aEngine = null;
    private final android.content.Context appContext = null;
    
    public DeepEyeRenderersFactory(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.dsp.DSPManager dspManager, @org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.dsp.v4a.V4AEngine v4aEngine) {
        super(null);
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    protected androidx.media3.exoplayer.audio.AudioSink buildAudioSink(@org.jetbrains.annotations.NotNull()
    android.content.Context context, boolean enableFloatOutput, boolean enableAudioTrackPlaybackParams) {
        return null;
    }
}