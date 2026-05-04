package com.deepeye.musicpro.dsp.v4a;

import com.deepeye.musicpro.util.Logger;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u001d\b\u0007\u0018\u0000 <2\u00020\u0001:\u0001<B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0004J\u0016\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011J\u0011\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u0015H\u0086 J\t\u0010\u0016\u001a\u00020\u0017H\u0086 J\u0011\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u001aH\u0086 J\u0011\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u001aH\u0086 J\u0019\u0010\u001c\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0082 J\u0011\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u0007\u001a\u00020\bH\u0082 J)\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020\u0011H\u0082 J)\u0010#\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020\u00152\u0006\u0010!\u001a\u00020\u00152\u0006\u0010\"\u001a\u00020\u0011H\u0082 J\u0011\u0010$\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0082 J!\u0010%\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010&\u001a\u00020\u00112\u0006\u0010\'\u001a\u00020\u0004H\u0082 J\u0019\u0010(\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010)\u001a\u00020\u0015H\u0082 J\u0019\u0010*\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\'\u001a\u00020\u0004H\u0082 J1\u0010+\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010,\u001a\u00020\u00172\u0006\u0010-\u001a\u00020\u00172\u0006\u0010.\u001a\u00020\u00172\u0006\u0010/\u001a\u00020\u0017H\u0082 J\u0019\u00100\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\'\u001a\u00020\u0004H\u0082 J\u0019\u00101\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u00102\u001a\u00020\u0017H\u0082 J\u001e\u00103\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020\u0011J\u001e\u00104\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u00152\u0006\u0010!\u001a\u00020\u00152\u0006\u0010\"\u001a\u00020\u0011J\u0006\u00105\u001a\u00020\u000bJ1\u00106\u001a\u00020\u000b2\u0006\u00107\u001a\u00020\u00172\u0006\u00105\u001a\u00020\u00172\u0006\u00108\u001a\u00020\u00172\u0006\u00109\u001a\u00020\u00172\u0006\u0010:\u001a\u00020\u0017H\u0086 J\u0011\u0010;\u001a\u00020\u000b2\u0006\u0010\'\u001a\u00020\u0004H\u0086 R\u0011\u0010\u0003\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006="}, d2 = {"Lcom/deepeye/musicpro/dsp/v4a/NativeV4A;", "", "()V", "available", "", "getAvailable", "()Z", "handle", "", "lock", "applyState", "", "state", "Lcom/deepeye/musicpro/dsp/v4a/V4AEffectsState;", "active", "configure", "sampleRate", "", "channels", "getSpectrumMagnitudes", "out", "", "getTubeWarmth", "", "loadConvolverIR", "path", "", "loadDDCProfile", "nativeCreate", "nativeGetTubeWarmth", "nativeProcessDirect", "input", "Ljava/nio/ByteBuffer;", "output", "frames", "nativeProcessFloatArray", "nativeRelease", "nativeSetEffectEnabled", "effectOrdinal", "enabled", "nativeSetEqGains", "gains", "nativeSetMasterEnabled", "nativeSetReverb", "room", "damping", "width", "wet", "nativeSetTubeEnabled", "nativeSetTubeWarmth", "warmth", "processDirect", "processFloatArray", "release", "setFETParams", "attack", "ratio", "threshold", "knee", "setTubeEnabled", "Companion", "app_debug"})
public final class NativeV4A {
    @kotlin.jvm.Volatile()
    private volatile long handle = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Object lock = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.concurrent.atomic.AtomicBoolean libraryLoaded = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.deepeye.musicpro.dsp.v4a.NativeV4A.Companion Companion = null;
    
    public NativeV4A() {
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
    
    public final void applyState(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.dsp.v4a.V4AEffectsState state, boolean active) {
    }
    
    public final boolean processDirect(@org.jetbrains.annotations.NotNull()
    java.nio.ByteBuffer input, @org.jetbrains.annotations.NotNull()
    java.nio.ByteBuffer output, int frames) {
        return false;
    }
    
    public final boolean processFloatArray(@org.jetbrains.annotations.NotNull()
    float[] input, @org.jetbrains.annotations.NotNull()
    float[] output, int frames) {
        return false;
    }
    
    @kotlin.jvm.Synchronized()
    public final synchronized native void loadConvolverIR(@org.jetbrains.annotations.NotNull()
    java.lang.String path) {
    }
    
    @kotlin.jvm.Synchronized()
    public final synchronized native void loadDDCProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String path) {
    }
    
    @kotlin.jvm.Synchronized()
    public final synchronized native void setFETParams(float attack, float release, float ratio, float threshold, float knee) {
    }
    
    @kotlin.jvm.Synchronized()
    public final synchronized native void getSpectrumMagnitudes(@org.jetbrains.annotations.NotNull()
    float[] out) {
    }
    
    @kotlin.jvm.Synchronized()
    public final synchronized native void setTubeEnabled(boolean enabled) {
    }
    
    @kotlin.jvm.Synchronized()
    public final synchronized native float getTubeWarmth() {
        return 0.0F;
    }
    
    private final native long nativeCreate(int sampleRate, int channels) {
        return 0L;
    }
    
    private final native void nativeRelease(long handle) {
    }
    
    private final native void nativeSetMasterEnabled(long handle, boolean enabled) {
    }
    
    private final native void nativeSetEffectEnabled(long handle, int effectOrdinal, boolean enabled) {
    }
    
    private final native void nativeSetEqGains(long handle, float[] gains) {
    }
    
    private final native void nativeSetTubeWarmth(long handle, float warmth) {
    }
    
    private final native void nativeSetTubeEnabled(long handle, boolean enabled) {
    }
    
    private final native float nativeGetTubeWarmth(long handle) {
        return 0.0F;
    }
    
    private final native void nativeSetReverb(long handle, float room, float damping, float width, float wet) {
    }
    
    private final native void nativeProcessFloatArray(long handle, float[] input, float[] output, int frames) {
    }
    
    private final native void nativeProcessDirect(long handle, java.nio.ByteBuffer input, java.nio.ByteBuffer output, int frames) {
    }
    
    @kotlin.jvm.JvmStatic()
    public static final native void setTubeWarmthNative(float warmth) {
    }
    
    @kotlin.jvm.JvmStatic()
    public static final native void setTubeEnabledNative(boolean enabled) {
    }
    
    @kotlin.jvm.JvmStatic()
    public static final native float getTubeWarmthNative() {
        return 0.0F;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\t\u0010\u0005\u001a\u00020\u0006H\u0087 J\u0011\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0087 J\u0011\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u0006H\u0087 R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/deepeye/musicpro/dsp/v4a/NativeV4A$Companion;", "", "()V", "libraryLoaded", "Ljava/util/concurrent/atomic/AtomicBoolean;", "getTubeWarmthNative", "", "setTubeEnabledNative", "", "enabled", "", "setTubeWarmthNative", "warmth", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @kotlin.jvm.JvmStatic()
        public final void setTubeWarmthNative(float warmth) {
        }
        
        @kotlin.jvm.JvmStatic()
        public final void setTubeEnabledNative(boolean enabled) {
        }
        
        @kotlin.jvm.JvmStatic()
        public final float getTubeWarmthNative() {
            return 0.0F;
        }
    }
}