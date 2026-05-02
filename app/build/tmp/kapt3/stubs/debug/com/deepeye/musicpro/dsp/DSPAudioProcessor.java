package com.deepeye.musicpro.dsp;

import android.os.SystemClock;
import android.util.Log;
import androidx.media3.common.C;
import androidx.media3.common.audio.AudioProcessor;
import androidx.media3.common.audio.AudioProcessor.AudioFormat;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Locale;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u000b\u0018\u0000 52\u00020\u0001:\u000256B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u00a2\u0006\u0002\u0010\tJ\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u0018H\u0002J \u0010\u001b\u001a\u00020\u00142\u0006\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u0018H\u0002J\u0018\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0018H\u0002J\u0010\u0010\u001f\u001a\u00020\u00142\u0006\u0010\u001e\u001a\u00020\u0012H\u0002J\u0010\u0010 \u001a\u00020\u000f2\u0006\u0010!\u001a\u00020\u000fH\u0016J \u0010\"\u001a\u00020#2\u0006\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010$\u001a\u00020\u0018H\u0002J\b\u0010%\u001a\u00020&H\u0016J\b\u0010\'\u001a\u00020\u0012H\u0016J\b\u0010(\u001a\u00020\bH\u0016J\b\u0010)\u001a\u00020\bH\u0016J0\u0010*\u001a\u00020&2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\b2\u0006\u0010$\u001a\u00020\u00182\u0006\u0010.\u001a\u00020\u00142\u0006\u0010/\u001a\u00020\u0014H\u0002J \u00100\u001a\u00020#2\u0006\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u0018H\u0002J \u00101\u001a\u00020#2\u0006\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u0018H\u0002J\b\u00102\u001a\u00020&H\u0016J\u0010\u00103\u001a\u00020&2\u0006\u0010\u001c\u001a\u00020\u0012H\u0016J\b\u00104\u001a\u00020&H\u0016R\u000e\u0010\n\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00067"}, d2 = {"Lcom/deepeye/musicpro/dsp/DSPAudioProcessor;", "Landroidx/media3/common/audio/AudioProcessor;", "nativeDSP", "Lcom/deepeye/musicpro/dsp/NativeDSP;", "presetProvider", "Lkotlin/Function0;", "Lcom/deepeye/musicpro/dsp/DSPPreset;", "enabledProvider", "", "(Lcom/deepeye/musicpro/dsp/NativeDSP;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V", "active", "framesSinceMetricsLog", "", "inputEnded", "inputFormat", "Landroidx/media3/common/audio/AudioProcessor$AudioFormat;", "lastMetricsLogTimeMs", "outputBuffer", "Ljava/nio/ByteBuffer;", "calculateFloatArrayRms", "", "samples", "", "calculateFrameCount", "", "size", "channels", "calculateInputRms", "inputBuffer", "calculatePcm16Rms", "buffer", "calculatePcmFloatRms", "configure", "inputAudioFormat", "copyUnsupportedInput", "Lcom/deepeye/musicpro/dsp/DSPAudioProcessor$RmsMetrics;", "frames", "flush", "", "getOutput", "isActive", "isEnded", "logMetrics", "presetId", "", "enabled", "inputRms", "outputRms", "processFloat", "processPcm16", "queueEndOfStream", "queueInput", "reset", "Companion", "RmsMetrics", "app_debug"})
public final class DSPAudioProcessor implements androidx.media3.common.audio.AudioProcessor {
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.dsp.NativeDSP nativeDSP = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function0<com.deepeye.musicpro.dsp.DSPPreset> presetProvider = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function0<java.lang.Boolean> enabledProvider = null;
    @org.jetbrains.annotations.NotNull()
    private androidx.media3.common.audio.AudioProcessor.AudioFormat inputFormat;
    @org.jetbrains.annotations.NotNull()
    private java.nio.ByteBuffer outputBuffer;
    private boolean inputEnded = false;
    private boolean active = false;
    private long framesSinceMetricsLog = 0L;
    private long lastMetricsLogTimeMs = 0L;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "DeepEyeDSP";
    private static final double MIN_RMS = 1.0E-6;
    @org.jetbrains.annotations.NotNull()
    private static final com.deepeye.musicpro.dsp.DSPAudioProcessor.Companion Companion = null;
    
    public DSPAudioProcessor(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.dsp.NativeDSP nativeDSP, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<com.deepeye.musicpro.dsp.DSPPreset> presetProvider, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<java.lang.Boolean> enabledProvider) {
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
    
    private final com.deepeye.musicpro.dsp.DSPAudioProcessor.RmsMetrics processFloat(java.nio.ByteBuffer inputBuffer, int size, int channels) {
        return null;
    }
    
    private final com.deepeye.musicpro.dsp.DSPAudioProcessor.RmsMetrics processPcm16(java.nio.ByteBuffer inputBuffer, int size, int channels) {
        return null;
    }
    
    private final com.deepeye.musicpro.dsp.DSPAudioProcessor.RmsMetrics copyUnsupportedInput(java.nio.ByteBuffer inputBuffer, int size, int frames) {
        return null;
    }
    
    private final int calculateFrameCount(int size, int channels) {
        return 0;
    }
    
    private final double calculateInputRms(java.nio.ByteBuffer inputBuffer, int size, int channels) {
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
    
    private final void logMetrics(java.lang.String presetId, boolean enabled, int frames, double inputRms, double outputRms) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/deepeye/musicpro/dsp/DSPAudioProcessor$Companion;", "", "()V", "MIN_RMS", "", "TAG", "", "app_debug"})
    static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u000f\u001a\u00020\u0005H\u00c6\u0003J\'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0014\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b\u00a8\u0006\u0017"}, d2 = {"Lcom/deepeye/musicpro/dsp/DSPAudioProcessor$RmsMetrics;", "", "frames", "", "inputRms", "", "outputRms", "(IDD)V", "getFrames", "()I", "getInputRms", "()D", "getOutputRms", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
    static final class RmsMetrics {
        private final int frames = 0;
        private final double inputRms = 0.0;
        private final double outputRms = 0.0;
        
        public RmsMetrics(int frames, double inputRms, double outputRms) {
            super();
        }
        
        public final int getFrames() {
            return 0;
        }
        
        public final double getInputRms() {
            return 0.0;
        }
        
        public final double getOutputRms() {
            return 0.0;
        }
        
        public final int component1() {
            return 0;
        }
        
        public final double component2() {
            return 0.0;
        }
        
        public final double component3() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.deepeye.musicpro.dsp.DSPAudioProcessor.RmsMetrics copy(int frames, double inputRms, double outputRms) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}