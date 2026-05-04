package com.deepeye.musicpro.dsp;

import com.deepeye.musicpro.util.Logger;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0014\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b#\b\u0007\u0018\u0000 F2\u00020\u0001:\u0001FB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0004J\u0016\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011J\u001e\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0015J\u0019\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0082 J)\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0015H\u0082 J)\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u0011H\u0082 J)\u0010\u001f\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u0011H\u0082 J\u0011\u0010 \u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0082 J\u0011\u0010!\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0082 J\u0019\u0010\"\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010#\u001a\u00020$H\u0082 J\u0019\u0010%\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u0004H\u0082 J1\u0010&\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\'\u001a\u00020$2\u0006\u0010(\u001a\u00020$2\u0006\u0010)\u001a\u00020$2\u0006\u0010*\u001a\u00020$H\u0082 J\u0019\u0010+\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u0004H\u0082 J\u0019\u0010,\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u0004H\u0082 J#\u0010-\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010.\u001a\u00020\u00152\b\u0010/\u001a\u0004\u0018\u00010\u0015H\u0082 J\u0019\u00100\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u0004H\u0082 J\u0019\u00101\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u0004H\u0082 J!\u00102\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u00103\u001a\u00020\u00112\u0006\u00104\u001a\u00020$H\u0082 J\u0019\u00105\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u00106\u001a\u00020\u0015H\u0082 J\u0019\u00107\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u00108\u001a\u00020$H\u0082 J1\u00109\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010:\u001a\u00020$2\u0006\u0010;\u001a\u00020$2\u0006\u0010<\u001a\u00020$2\u0006\u0010=\u001a\u00020$H\u0082 J\u0019\u0010>\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u0004H\u0082 J\u0019\u0010?\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010#\u001a\u00020$H\u0082 J\u0019\u0010@\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u0004H\u0082 J\u001e\u0010A\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u0011J\u0006\u0010*\u001a\u00020\u000bJ\u0006\u0010B\u001a\u00020\u000bJ\u000e\u0010C\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u0004J\u000e\u0010D\u001a\u00020\u000b2\u0006\u0010E\u001a\u00020$R\u0011\u0010\u0003\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006G"}, d2 = {"Lcom/deepeye/musicpro/dsp/NativeDSP;", "", "()V", "available", "", "getAvailable", "()Z", "handle", "", "lock", "applyPreset", "", "preset", "Lcom/deepeye/musicpro/dsp/DSPPreset;", "enabled", "configure", "sampleRate", "", "channels", "getVisualizerData", "spectrum", "", "waveform", "peak", "nativeCreate", "nativeGetVisualizerData", "nativeProcessDirect", "input", "Ljava/nio/ByteBuffer;", "output", "frames", "nativeProcessFloatArray", "nativeRelease", "nativeReset", "nativeSetBassBoost", "amount", "", "nativeSetBassEnabled", "nativeSetCompressor", "threshold", "ratio", "attack", "release", "nativeSetCompressorEnabled", "nativeSetConvolverEnabled", "nativeSetConvolverIr", "left", "right", "nativeSetEnabled", "nativeSetEqEnabled", "nativeSetEqGain", "band", "gainDb", "nativeSetEqGains", "gains", "nativeSetLimiterCeiling", "ceilingDb", "nativeSetReverb", "room", "damping", "width", "wet", "nativeSetReverbEnabled", "nativeSetStereoWidth", "nativeSetWidthEnabled", "processDirect", "reset", "setEnabled", "setLimiterCeiling", "db", "Companion", "app_debug"})
public final class NativeDSP {
    private long handle = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Object lock = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.concurrent.atomic.AtomicBoolean libraryLoaded = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.deepeye.musicpro.dsp.NativeDSP.Companion Companion = null;
    
    public NativeDSP() {
        super();
    }
    
    public final boolean getAvailable() {
        return false;
    }
    
    public final boolean configure(int sampleRate, int channels) {
        return false;
    }
    
    public final void release() {
    }
    
    public final void applyPreset(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.dsp.DSPPreset preset, boolean enabled) {
    }
    
    public final void setLimiterCeiling(float db) {
    }
    
    public final void setEnabled(boolean enabled) {
    }
    
    public final void reset() {
    }
    
    public final boolean processDirect(@org.jetbrains.annotations.NotNull()
    java.nio.ByteBuffer input, @org.jetbrains.annotations.NotNull()
    java.nio.ByteBuffer output, int frames) {
        return false;
    }
    
    public final void getVisualizerData(@org.jetbrains.annotations.NotNull()
    float[] spectrum, @org.jetbrains.annotations.NotNull()
    float[] waveform, @org.jetbrains.annotations.NotNull()
    float[] peak) {
    }
    
    private final native void nativeReset(long handle) {
    }
    
    private final native long nativeCreate(int sampleRate, int channels) {
        return 0L;
    }
    
    private final native void nativeRelease(long handle) {
    }
    
    private final native void nativeSetEnabled(long handle, boolean enabled) {
    }
    
    private final native void nativeSetEqEnabled(long handle, boolean enabled) {
    }
    
    private final native void nativeSetBassEnabled(long handle, boolean enabled) {
    }
    
    private final native void nativeSetWidthEnabled(long handle, boolean enabled) {
    }
    
    private final native void nativeSetCompressorEnabled(long handle, boolean enabled) {
    }
    
    private final native void nativeSetReverbEnabled(long handle, boolean enabled) {
    }
    
    private final native void nativeSetConvolverEnabled(long handle, boolean enabled) {
    }
    
    private final native void nativeSetEqGain(long handle, int band, float gainDb) {
    }
    
    private final native void nativeSetEqGains(long handle, float[] gains) {
    }
    
    private final native void nativeSetBassBoost(long handle, float amount) {
    }
    
    private final native void nativeSetStereoWidth(long handle, float amount) {
    }
    
    private final native void nativeSetCompressor(long handle, float threshold, float ratio, float attack, float release) {
    }
    
    private final native void nativeSetReverb(long handle, float room, float damping, float width, float wet) {
    }
    
    private final native void nativeSetLimiterCeiling(long handle, float ceilingDb) {
    }
    
    private final native void nativeSetConvolverIr(long handle, float[] left, float[] right) {
    }
    
    private final native void nativeProcessFloatArray(long handle, float[] input, float[] output, int frames) {
    }
    
    private final native void nativeProcessDirect(long handle, java.nio.ByteBuffer input, java.nio.ByteBuffer output, int frames) {
    }
    
    private final native void nativeGetVisualizerData(long handle, float[] spectrum, float[] waveform, float[] peak) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/deepeye/musicpro/dsp/NativeDSP$Companion;", "", "()V", "libraryLoaded", "Ljava/util/concurrent/atomic/AtomicBoolean;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}