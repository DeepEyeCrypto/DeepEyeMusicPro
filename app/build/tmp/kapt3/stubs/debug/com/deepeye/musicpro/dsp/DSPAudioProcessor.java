package com.deepeye.musicpro.dsp;

import androidx.media3.common.C;
import androidx.media3.common.audio.AudioProcessor;
import androidx.media3.common.audio.AudioProcessor.AudioFormat;
import androidx.media3.common.util.Assertions;
import com.deepeye.musicpro.security.AudioSecurityManager;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Hardened DSP AudioProcessor for Media3.
 *
 * CONTRACT COMPLIANCE:
 * - Zero-allocation in queueInput/getOutput.
 * - Fully consumes inputBuffer.
 * - Handles PCM_FLOAT and PCM_16BIT (via internal float conversion).
 * - Thread-safe state management.
 * - Graceful fallback to passthrough on native failure.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u00a2\u0006\u0002\u0010\tJ\u0010\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u000eH\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0012H\u0016J\b\u0010\u001a\u001a\u00020\bH\u0016J\b\u0010\u001b\u001a\u00020\bH\u0016J\b\u0010\u001c\u001a\u00020\u0018H\u0016J\u0010\u0010\u001d\u001a\u00020\u00182\u0006\u0010\u001e\u001a\u00020\u0012H\u0016J\b\u0010\u001f\u001a\u00020\u0018H\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/deepeye/musicpro/dsp/DSPAudioProcessor;", "Landroidx/media3/common/audio/AudioProcessor;", "nativeDSP", "Lcom/deepeye/musicpro/dsp/NativeDSP;", "presetProvider", "Lkotlin/Function0;", "Lcom/deepeye/musicpro/dsp/DSPPreset;", "enabledProvider", "", "(Lcom/deepeye/musicpro/dsp/NativeDSP;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V", "bufferHelper", "Lcom/deepeye/musicpro/dsp/BufferLifecycleHelper;", "inputEnded", "inputFormat", "Landroidx/media3/common/audio/AudioProcessor$AudioFormat;", "isNativeInitialized", "isPassthroughMode", "outputBuffer", "Ljava/nio/ByteBuffer;", "outputFormat", "pendingInputFormat", "configure", "inputAudioFormat", "flush", "", "getOutput", "isActive", "isEnded", "queueEndOfStream", "queueInput", "inputBuffer", "reset", "app_debug"})
public final class DSPAudioProcessor implements androidx.media3.common.audio.AudioProcessor {
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.dsp.NativeDSP nativeDSP = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function0<com.deepeye.musicpro.dsp.DSPPreset> presetProvider = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function0<java.lang.Boolean> enabledProvider = null;
    @org.jetbrains.annotations.NotNull()
    private androidx.media3.common.audio.AudioProcessor.AudioFormat pendingInputFormat;
    @org.jetbrains.annotations.NotNull()
    private androidx.media3.common.audio.AudioProcessor.AudioFormat inputFormat;
    @org.jetbrains.annotations.NotNull()
    private androidx.media3.common.audio.AudioProcessor.AudioFormat outputFormat;
    @org.jetbrains.annotations.NotNull()
    private com.deepeye.musicpro.dsp.BufferLifecycleHelper bufferHelper;
    @org.jetbrains.annotations.NotNull()
    private java.nio.ByteBuffer outputBuffer;
    private boolean inputEnded = false;
    private boolean isNativeInitialized = false;
    private boolean isPassthroughMode = false;
    
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
}