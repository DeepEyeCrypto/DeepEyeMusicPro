package com.deepeye.musicpro.dsp;

import android.content.Context;
import android.util.Log;
import com.deepeye.musicpro.BuildConfig;
import com.deepeye.musicpro.util.Logger;
import kotlinx.coroutines.flow.StateFlow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0014\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\b\u0007\u0018\u0000 .2\u00020\u0001:\u0001.B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u000bJ\u001e\u0010\"\u001a\u00020 2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020$2\u0006\u0010&\u001a\u00020$J\u0006\u0010\'\u001a\u00020 J\u000e\u0010(\u001a\u00020 2\u0006\u0010\u001b\u001a\u00020\tJ\u0016\u0010)\u001a\u00020 2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\t0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\r0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\t0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006/"}, d2 = {"Lcom/deepeye/musicpro/dsp/DSPManager;", "", "context", "Landroid/content/Context;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "(Landroid/content/Context;Lkotlinx/coroutines/CoroutineScope;)V", "_available", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_currentPreset", "Lcom/deepeye/musicpro/dsp/DSPPreset;", "_currentPresetId", "", "_enabled", "audioProcessor", "Lcom/deepeye/musicpro/dsp/DSPAudioProcessor;", "getAudioProcessor", "()Lcom/deepeye/musicpro/dsp/DSPAudioProcessor;", "available", "Lkotlinx/coroutines/flow/StateFlow;", "getAvailable", "()Lkotlinx/coroutines/flow/StateFlow;", "currentPreset", "getCurrentPreset", "currentPresetId", "getCurrentPresetId", "enabled", "getEnabled", "nativeDSP", "Lcom/deepeye/musicpro/dsp/NativeDSP;", "applyPreset", "", "preset", "getVisualizerData", "spectrum", "", "waveform", "peak", "release", "setEnabled", "updateEqBand", "index", "", "gainDb", "", "Companion", "app_debug"})
public final class DSPManager {
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.dsp.NativeDSP nativeDSP = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _enabled = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.deepeye.musicpro.dsp.DSPPreset> _currentPreset = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _currentPresetId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _available = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> enabled = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.deepeye.musicpro.dsp.DSPPreset> currentPreset = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> currentPresetId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> available = null;
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.dsp.DSPAudioProcessor audioProcessor = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "DSPManager";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG_DSP = "DeepEyeDSP";
    @org.jetbrains.annotations.NotNull()
    public static final com.deepeye.musicpro.dsp.DSPManager.Companion Companion = null;
    
    public DSPManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineScope scope) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getEnabled() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.deepeye.musicpro.dsp.DSPPreset> getCurrentPreset() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getCurrentPresetId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getAvailable() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.dsp.DSPAudioProcessor getAudioProcessor() {
        return null;
    }
    
    public final void setEnabled(boolean enabled) {
    }
    
    public final void applyPreset(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.dsp.DSPPreset preset) {
    }
    
    public final void updateEqBand(int index, float gainDb) {
    }
    
    public final void getVisualizerData(@org.jetbrains.annotations.NotNull()
    float[] spectrum, @org.jetbrains.annotations.NotNull()
    float[] waveform, @org.jetbrains.annotations.NotNull()
    float[] peak) {
    }
    
    public final void release() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/deepeye/musicpro/dsp/DSPManager$Companion;", "", "()V", "TAG", "", "TAG_DSP", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}