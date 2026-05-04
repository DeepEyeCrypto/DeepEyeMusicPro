package com.deepeye.musicpro.dsp.v4a;

import android.content.Context;
import android.media.audiofx.AudioEffect;
import android.util.Log;
import androidx.media3.common.C;
import com.deepeye.musicpro.util.Logger;
import kotlinx.coroutines.flow.StateFlow;
import java.io.File;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0014\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\t\b\u0007\u0018\u0000 :2\u00020\u0001:\u0001:B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u000e\u0010\u001d\u001a\u00020\u00182\u0006\u0010\u001e\u001a\u00020\u001fJ\u0010\u0010 \u001a\u00020\u001c2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u000e\u0010!\u001a\u00020\u00182\u0006\u0010\"\u001a\u00020#J\u0010\u0010$\u001a\u00020\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u001aJ\u0006\u0010%\u001a\u00020\u0018J\u000e\u0010&\u001a\u00020\u00182\u0006\u0010\'\u001a\u00020(J\u000e\u0010)\u001a\u00020\u00182\u0006\u0010*\u001a\u00020+J\u0016\u0010,\u001a\u00020\u00182\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\u001cJ.\u00100\u001a\u00020\u00182\u0006\u00101\u001a\u0002022\u0006\u0010%\u001a\u0002022\u0006\u00103\u001a\u0002022\u0006\u00104\u001a\u0002022\u0006\u00105\u001a\u000202J\u000e\u00106\u001a\u00020\u00182\u0006\u0010/\u001a\u00020\u001cJ\u000e\u00107\u001a\u00020\u00182\u0006\u0010/\u001a\u00020\u001cJ\u000e\u00108\u001a\u00020\u00182\u0006\u00109\u001a\u000202R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n \t*\u0004\u0018\u00010\u00030\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006;"}, d2 = {"Lcom/deepeye/musicpro/dsp/v4a/V4AEngine;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/deepeye/musicpro/dsp/v4a/V4AEngineState;", "appContext", "kotlin.jvm.PlatformType", "audioProcessor", "Lcom/deepeye/musicpro/dsp/v4a/V4ABundledProcessor;", "getAudioProcessor", "()Lcom/deepeye/musicpro/dsp/v4a/V4ABundledProcessor;", "lock", "nativeV4A", "Lcom/deepeye/musicpro/dsp/v4a/NativeV4A;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "systemEffect", "Landroid/media/audiofx/AudioEffect;", "activateBundledV4A", "", "audioSessionId", "", "systemDetected", "", "applyPreset", "preset", "Lcom/deepeye/musicpro/dsp/v4a/V4APreset;", "attachSystemV4A", "getSpectrumMagnitudes", "out", "", "init", "release", "setConvolverIR", "irFile", "Ljava/io/File;", "setDDCProfile", "profile", "Lcom/deepeye/musicpro/dsp/v4a/DDCProfile;", "setEffectEnabled", "effect", "Lcom/deepeye/musicpro/dsp/v4a/V4AEffect;", "enabled", "setFETParams", "attack", "", "ratio", "threshold", "knee", "setMasterEnabled", "setTubeEnabled", "setTubeWarmth", "warmth", "Companion", "app_debug"})
public final class V4AEngine {
    private final android.content.Context appContext = null;
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.dsp.v4a.NativeV4A nativeV4A = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Object lock = null;
    @org.jetbrains.annotations.Nullable()
    private android.media.audiofx.AudioEffect systemEffect;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.deepeye.musicpro.dsp.v4a.V4AEngineState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.deepeye.musicpro.dsp.v4a.V4AEngineState> state = null;
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.dsp.v4a.V4ABundledProcessor audioProcessor = null;
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public static final java.lang.String TAG = "V4AEngine";
    @org.jetbrains.annotations.NotNull()
    private static final com.deepeye.musicpro.dsp.v4a.V4AEngine.Companion Companion = null;
    
    public V4AEngine(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.deepeye.musicpro.dsp.v4a.V4AEngineState> getState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.dsp.v4a.V4ABundledProcessor getAudioProcessor() {
        return null;
    }
    
    public final void init(int audioSessionId) {
    }
    
    public final void setMasterEnabled(boolean enabled) {
    }
    
    public final void setEffectEnabled(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.dsp.v4a.V4AEffect effect, boolean enabled) {
    }
    
    public final void setConvolverIR(@org.jetbrains.annotations.NotNull()
    java.io.File irFile) {
    }
    
    public final void setDDCProfile(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.dsp.v4a.DDCProfile profile) {
    }
    
    public final void setFETParams(float attack, float release, float ratio, float threshold, float knee) {
    }
    
    public final void setTubeWarmth(float warmth) {
    }
    
    public final void setTubeEnabled(boolean enabled) {
    }
    
    public final void applyPreset(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.dsp.v4a.V4APreset preset) {
    }
    
    public final void getSpectrumMagnitudes(@org.jetbrains.annotations.NotNull()
    float[] out) {
    }
    
    public final void release() {
    }
    
    private final boolean attachSystemV4A(int audioSessionId) {
        return false;
    }
    
    private final void activateBundledV4A(int audioSessionId, boolean systemDetected) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/deepeye/musicpro/dsp/v4a/V4AEngine$Companion;", "", "()V", "TAG", "", "app_debug"})
    static final class Companion {
        
        private Companion() {
            super();
        }
    }
}