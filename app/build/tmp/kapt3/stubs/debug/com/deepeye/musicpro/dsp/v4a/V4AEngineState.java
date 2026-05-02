package com.deepeye.musicpro.dsp.v4a;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001e\b\u0086\b\u0018\u00002\u00020\u0001BU\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0006H\u00c6\u0003J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\"\u001a\u00020\tH\u00c6\u0003J\t\u0010#\u001a\u00020\u000bH\u00c6\u0003J\t\u0010$\u001a\u00020\rH\u00c6\u0003J\t\u0010%\u001a\u00020\u000bH\u00c6\u0003JY\u0010&\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000bH\u00c6\u0001J\u0013\u0010\'\u001a\u00020\u00032\b\u0010(\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010)\u001a\u00020\tH\u00d6\u0001J\t\u0010*\u001a\u00020\u000bH\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u0011\u0010\u0019\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u0011R\u0011\u0010\u000e\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0015R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0011\u00a8\u0006+"}, d2 = {"Lcom/deepeye/musicpro/dsp/v4a/V4AEngineState;", "", "initialized", "", "active", "mode", "Lcom/deepeye/musicpro/dsp/v4a/V4AEngineMode;", "systemDetected", "audioSessionId", "", "currentPresetName", "", "effects", "Lcom/deepeye/musicpro/dsp/v4a/V4AEffectsState;", "message", "(ZZLcom/deepeye/musicpro/dsp/v4a/V4AEngineMode;ZILjava/lang/String;Lcom/deepeye/musicpro/dsp/v4a/V4AEffectsState;Ljava/lang/String;)V", "getActive", "()Z", "getAudioSessionId", "()I", "getCurrentPresetName", "()Ljava/lang/String;", "getEffects", "()Lcom/deepeye/musicpro/dsp/v4a/V4AEffectsState;", "getInitialized", "isBundledProcessorActive", "getMessage", "getMode", "()Lcom/deepeye/musicpro/dsp/v4a/V4AEngineMode;", "getSystemDetected", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class V4AEngineState {
    private final boolean initialized = false;
    private final boolean active = false;
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.dsp.v4a.V4AEngineMode mode = null;
    private final boolean systemDetected = false;
    private final int audioSessionId = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String currentPresetName = null;
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.dsp.v4a.V4AEffectsState effects = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String message = null;
    
    public V4AEngineState(boolean initialized, boolean active, @org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.dsp.v4a.V4AEngineMode mode, boolean systemDetected, int audioSessionId, @org.jetbrains.annotations.NotNull()
    java.lang.String currentPresetName, @org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.dsp.v4a.V4AEffectsState effects, @org.jetbrains.annotations.NotNull()
    java.lang.String message) {
        super();
    }
    
    public final boolean getInitialized() {
        return false;
    }
    
    public final boolean getActive() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.dsp.v4a.V4AEngineMode getMode() {
        return null;
    }
    
    public final boolean getSystemDetected() {
        return false;
    }
    
    public final int getAudioSessionId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCurrentPresetName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.dsp.v4a.V4AEffectsState getEffects() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMessage() {
        return null;
    }
    
    public final boolean isBundledProcessorActive() {
        return false;
    }
    
    public V4AEngineState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    public final boolean component2() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.dsp.v4a.V4AEngineMode component3() {
        return null;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final int component5() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.dsp.v4a.V4AEffectsState component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.dsp.v4a.V4AEngineState copy(boolean initialized, boolean active, @org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.dsp.v4a.V4AEngineMode mode, boolean systemDetected, int audioSessionId, @org.jetbrains.annotations.NotNull()
    java.lang.String currentPresetName, @org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.dsp.v4a.V4AEffectsState effects, @org.jetbrains.annotations.NotNull()
    java.lang.String message) {
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