package com.deepeye.musicpro.dsp.v4a;

import android.os.SystemClock;
import android.util.Log;
import androidx.media3.common.C;
import androidx.media3.common.audio.AudioProcessor;
import androidx.media3.common.audio.AudioProcessor.AudioFormat;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Locale;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0011\u0018\u0000 02\u00020\u0001:\u00010B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J \u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0002J\u0018\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0019H\u0002J\u0010\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0011H\u0002J\u0010\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u001e\u001a\u00020\u000eH\u0016J\u0018\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\b\u0010\"\u001a\u00020 H\u0016J\b\u0010#\u001a\u00020\u0011H\u0016J\b\u0010$\u001a\u00020\tH\u0016J\b\u0010%\u001a\u00020\tH\u0016J(\u0010&\u001a\u00020 2\u0006\u0010\'\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u00192\u0006\u0010)\u001a\u00020\u00132\u0006\u0010*\u001a\u00020\u0013H\u0002J \u0010+\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0002J \u0010,\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0002J\b\u0010-\u001a\u00020 H\u0016J\u0010\u0010.\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0011H\u0016J\b\u0010/\u001a\u00020 H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00061"}, d2 = {"Lcom/deepeye/musicpro/dsp/v4a/V4ABundledProcessor;", "Landroidx/media3/common/audio/AudioProcessor;", "nativeV4A", "Lcom/deepeye/musicpro/dsp/v4a/NativeV4A;", "stateProvider", "Lkotlin/Function0;", "Lcom/deepeye/musicpro/dsp/v4a/V4AEngineState;", "(Lcom/deepeye/musicpro/dsp/v4a/NativeV4A;Lkotlin/jvm/functions/Function0;)V", "active", "", "framesSinceMetricsLog", "", "inputEnded", "inputFormat", "Landroidx/media3/common/audio/AudioProcessor$AudioFormat;", "lastMetricsLogTimeMs", "outputBuffer", "Ljava/nio/ByteBuffer;", "calculateFloatArrayRms", "", "samples", "", "calculateInputRms", "buffer", "size", "", "channels", "calculatePcm16Rms", "calculatePcmFloatRms", "configure", "inputAudioFormat", "copyUnsupportedInput", "", "inputBuffer", "flush", "getOutput", "isActive", "isEnded", "logMetrics", "state", "frames", "inputRms", "outputRms", "processFloat", "processPcm16", "queueEndOfStream", "queueInput", "reset", "Companion", "app_debug"})
public final class V4ABundledProcessor implements androidx.media3.common.audio.AudioProcessor {
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.dsp.v4a.NativeV4A nativeV4A = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function0<com.deepeye.musicpro.dsp.v4a.V4AEngineState> stateProvider = null;
    @org.jetbrains.annotations.NotNull()
    private androidx.media3.common.audio.AudioProcessor.AudioFormat inputFormat;
    @org.jetbrains.annotations.NotNull()
    private java.nio.ByteBuffer outputBuffer;
    private boolean inputEnded = false;
    private boolean active = false;
    private long framesSinceMetricsLog = 0L;
    private long lastMetricsLogTimeMs = 0L;
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public static final java.lang.String TAG = "V4ABundledProcessor";
    @java.lang.Deprecated()
    public static final double MIN_RMS = 1.0E-6;
    @org.jetbrains.annotations.NotNull()
    private static final com.deepeye.musicpro.dsp.v4a.V4ABundledProcessor.Companion Companion = null;
    
    public V4ABundledProcessor(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.dsp.v4a.NativeV4A nativeV4A, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<com.deepeye.musicpro.dsp.v4a.V4AEngineState> stateProvider) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public androidx.media3.common.audio.AudioProcessor.AudioFormat configure(@org.jetbrains.annotations.NotNull()
    androidx.media3.common.audio.AudioProcessor.AudioFormat inputAudioFormat) {
        return null;
    }
    
    @java.lang.Override()
    public boolean isActive() {
        return false;
    }
    
    @java.lang.Override()
    public void queueInput(@org.jetbrains.annotations.NotNull()
    java.nio.ByteBuffer inputBuffer) {
    }
    
    private final void processFloat(java.nio.ByteBuffer inputBuffer, int size, int channels) {
    }
    
    private final void processPcm16(java.nio.ByteBuffer inputBuffer, int size, int channels) {
    }
    
    private final void copyUnsupportedInput(java.nio.ByteBuffer inputBuffer, int size) {
    }
    
    private final double calculateInputRms(java.nio.ByteBuffer buffer, int size, int channels) {
        return 0.0;
    }
    
    private final double calculatePcmFloatRms(java.nio.ByteBuffer buffer) {
        return 0.0;
    }
    
    private final double calculatePcm16Rms(java.nio.ByteBuffer buffer, int samples) {
        return 0.0;
    }
    
    private final double calculateFloatArrayRms(float[] samples) {
        return 0.0;
    }
    
    private final void logMetrics(com.deepeye.musicpro.dsp.v4a.V4AEngineState state, int frames, double inputRms, double outputRms) {
    }
    
    @java.lang.Override()
    public void queueEndOfStream() {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.nio.ByteBuffer getOutput() {
        return null;
    }
    
    @java.lang.Override()
    public boolean isEnded() {
        return false;
    }
    
    @java.lang.Override()
    public void flush() {
    }
    
    @java.lang.Override()
    public void reset() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/deepeye/musicpro/dsp/v4a/V4ABundledProcessor$Companion;", "", "()V", "MIN_RMS", "", "TAG", "", "app_debug"})
    static final class Companion {
        
        private Companion() {
            super();
        }
    }
}