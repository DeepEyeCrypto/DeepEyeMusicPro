package com.deepeye.musicpro.dsp.v4a;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\"\n\u0002\u0010\b\n\u0002\b\b\b\u0086\b\u0018\u0000 =2\u00020\u0001:\u0001=Bo\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\b\u0012\u0006\u0010\u000b\u001a\u00020\b\u0012\u0006\u0010\f\u001a\u00020\b\u0012\u0006\u0010\r\u001a\u00020\b\u0012\u0006\u0010\u000e\u001a\u00020\b\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012\u00a2\u0006\u0002\u0010\u0014J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\'\u001a\u00020\bH\u00c6\u0003J\t\u0010(\u001a\u00020\u0010H\u00c6\u0003J\u000f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u00c6\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010+\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010,\u001a\u00020\bH\u00c6\u0003J\t\u0010-\u001a\u00020\bH\u00c6\u0003J\t\u0010.\u001a\u00020\bH\u00c6\u0003J\t\u0010/\u001a\u00020\bH\u00c6\u0003J\t\u00100\u001a\u00020\bH\u00c6\u0003J\t\u00101\u001a\u00020\bH\u00c6\u0003J\u008b\u0001\u00102\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\b2\b\b\u0002\u0010\u000b\u001a\u00020\b2\b\b\u0002\u0010\f\u001a\u00020\b2\b\b\u0002\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\u000e\u001a\u00020\b2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u00c6\u0001J\u0013\u00103\u001a\u00020\u00102\b\u00104\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u00105\u001a\u000206H\u0016J\u000e\u00107\u001a\u00020\u00102\u0006\u00108\u001a\u00020\u0013J\u0006\u00109\u001a\u00020\u0003J\t\u0010:\u001a\u00020\u0005H\u00d6\u0001J\u0016\u0010;\u001a\u00020\u00002\u0006\u00108\u001a\u00020\u00132\u0006\u0010<\u001a\u00020\u0010R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\f\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001dR\u0011\u0010\n\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001dR\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001dR\u0011\u0010\u000b\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001dR\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0011\u0010\u000e\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001dR\u0011\u0010\r\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001d\u00a8\u0006>"}, d2 = {"Lcom/deepeye/musicpro/dsp/v4a/V4AEffectsState;", "", "eqGains", "", "convolverIR", "", "ddcProfile", "fetAttack", "", "fetRelease", "fetRatio", "fetThreshold", "fetKnee", "tubeWarmth", "reverbRoom", "masterEnabled", "", "enabledEffects", "", "Lcom/deepeye/musicpro/dsp/v4a/V4AEffect;", "([FLjava/lang/String;Ljava/lang/String;FFFFFFFZLjava/util/Set;)V", "getConvolverIR", "()Ljava/lang/String;", "getDdcProfile", "getEnabledEffects", "()Ljava/util/Set;", "getEqGains", "()[F", "getFetAttack", "()F", "getFetKnee", "getFetRatio", "getFetRelease", "getFetThreshold", "getMasterEnabled", "()Z", "getReverbRoom", "getTubeWarmth", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "isEnabled", "effect", "normalizedEqGains", "toString", "withEffect", "enabled", "Companion", "app_debug"})
public final class V4AEffectsState {
    @org.jetbrains.annotations.NotNull()
    private final float[] eqGains = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String convolverIR = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String ddcProfile = null;
    private final float fetAttack = 0.0F;
    private final float fetRelease = 0.0F;
    private final float fetRatio = 0.0F;
    private final float fetThreshold = 0.0F;
    private final float fetKnee = 0.0F;
    private final float tubeWarmth = 0.0F;
    private final float reverbRoom = 0.0F;
    private final boolean masterEnabled = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<com.deepeye.musicpro.dsp.v4a.V4AEffect> enabledEffects = null;
    public static final int EQ_BANDS = 10;
    @org.jetbrains.annotations.NotNull()
    private static final com.deepeye.musicpro.dsp.v4a.V4AEffectsState Default = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.deepeye.musicpro.dsp.v4a.V4AEffectsState.Companion Companion = null;
    
    public V4AEffectsState(@org.jetbrains.annotations.NotNull()
    float[] eqGains, @org.jetbrains.annotations.Nullable()
    java.lang.String convolverIR, @org.jetbrains.annotations.Nullable()
    java.lang.String ddcProfile, float fetAttack, float fetRelease, float fetRatio, float fetThreshold, float fetKnee, float tubeWarmth, float reverbRoom, boolean masterEnabled, @org.jetbrains.annotations.NotNull()
    java.util.Set<? extends com.deepeye.musicpro.dsp.v4a.V4AEffect> enabledEffects) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final float[] getEqGains() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getConvolverIR() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDdcProfile() {
        return null;
    }
    
    public final float getFetAttack() {
        return 0.0F;
    }
    
    public final float getFetRelease() {
        return 0.0F;
    }
    
    public final float getFetRatio() {
        return 0.0F;
    }
    
    public final float getFetThreshold() {
        return 0.0F;
    }
    
    public final float getFetKnee() {
        return 0.0F;
    }
    
    public final float getTubeWarmth() {
        return 0.0F;
    }
    
    public final float getReverbRoom() {
        return 0.0F;
    }
    
    public final boolean getMasterEnabled() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<com.deepeye.musicpro.dsp.v4a.V4AEffect> getEnabledEffects() {
        return null;
    }
    
    public final boolean isEnabled(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.dsp.v4a.V4AEffect effect) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.dsp.v4a.V4AEffectsState withEffect(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.dsp.v4a.V4AEffect effect, boolean enabled) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final float[] normalizedEqGains() {
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
    
    @org.jetbrains.annotations.NotNull()
    public final float[] component1() {
        return null;
    }
    
    public final float component10() {
        return 0.0F;
    }
    
    public final boolean component11() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<com.deepeye.musicpro.dsp.v4a.V4AEffect> component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    public final float component4() {
        return 0.0F;
    }
    
    public final float component5() {
        return 0.0F;
    }
    
    public final float component6() {
        return 0.0F;
    }
    
    public final float component7() {
        return 0.0F;
    }
    
    public final float component8() {
        return 0.0F;
    }
    
    public final float component9() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.dsp.v4a.V4AEffectsState copy(@org.jetbrains.annotations.NotNull()
    float[] eqGains, @org.jetbrains.annotations.Nullable()
    java.lang.String convolverIR, @org.jetbrains.annotations.Nullable()
    java.lang.String ddcProfile, float fetAttack, float fetRelease, float fetRatio, float fetThreshold, float fetKnee, float tubeWarmth, float reverbRoom, boolean masterEnabled, @org.jetbrains.annotations.NotNull()
    java.util.Set<? extends com.deepeye.musicpro.dsp.v4a.V4AEffect> enabledEffects) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/deepeye/musicpro/dsp/v4a/V4AEffectsState$Companion;", "", "()V", "Default", "Lcom/deepeye/musicpro/dsp/v4a/V4AEffectsState;", "getDefault", "()Lcom/deepeye/musicpro/dsp/v4a/V4AEffectsState;", "EQ_BANDS", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.deepeye.musicpro.dsp.v4a.V4AEffectsState getDefault() {
            return null;
        }
    }
}