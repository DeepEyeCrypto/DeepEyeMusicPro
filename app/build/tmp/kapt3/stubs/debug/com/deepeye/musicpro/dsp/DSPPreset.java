package com.deepeye.musicpro.dsp;

import com.deepeye.musicpro.db.DspPresetEntity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b9\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\b\u0086\b\u0018\u0000 T2\u00020\u0001:\u0001TB\u00c1\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\u0007\u0012\u0006\u0010\u000b\u001a\u00020\u0007\u0012\u0006\u0010\f\u001a\u00020\u0007\u0012\u0006\u0010\r\u001a\u00020\u0007\u0012\u0006\u0010\u000e\u001a\u00020\u0007\u0012\u0006\u0010\u000f\u001a\u00020\u0007\u0012\u0006\u0010\u0010\u001a\u00020\u0007\u0012\u0006\u0010\u0011\u001a\u00020\u0007\u0012\u0006\u0010\u0012\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0014\u00a2\u0006\u0002\u0010\u001bJ\t\u00105\u001a\u00020\u0003H\u00c6\u0003J\t\u00106\u001a\u00020\u0007H\u00c6\u0003J\t\u00107\u001a\u00020\u0007H\u00c6\u0003J\t\u00108\u001a\u00020\u0007H\u00c6\u0003J\t\u00109\u001a\u00020\u0007H\u00c6\u0003J\t\u0010:\u001a\u00020\u0007H\u00c6\u0003J\t\u0010;\u001a\u00020\u0014H\u00c6\u0003J\t\u0010<\u001a\u00020\u0014H\u00c6\u0003J\t\u0010=\u001a\u00020\u0014H\u00c6\u0003J\t\u0010>\u001a\u00020\u0014H\u00c6\u0003J\t\u0010?\u001a\u00020\u0014H\u00c6\u0003J\t\u0010@\u001a\u00020\u0003H\u00c6\u0003J\t\u0010A\u001a\u00020\u0014H\u00c6\u0003J\t\u0010B\u001a\u00020\u0014H\u00c6\u0003J\u000f\u0010C\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0003J\t\u0010D\u001a\u00020\u0007H\u00c6\u0003J\t\u0010E\u001a\u00020\u0007H\u00c6\u0003J\t\u0010F\u001a\u00020\u0007H\u00c6\u0003J\t\u0010G\u001a\u00020\u0007H\u00c6\u0003J\t\u0010H\u001a\u00020\u0007H\u00c6\u0003J\t\u0010I\u001a\u00020\u0007H\u00c6\u0003J\u00e1\u0001\u0010J\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u00072\b\b\u0002\u0010\u000b\u001a\u00020\u00072\b\b\u0002\u0010\f\u001a\u00020\u00072\b\b\u0002\u0010\r\u001a\u00020\u00072\b\b\u0002\u0010\u000e\u001a\u00020\u00072\b\b\u0002\u0010\u000f\u001a\u00020\u00072\b\b\u0002\u0010\u0010\u001a\u00020\u00072\b\b\u0002\u0010\u0011\u001a\u00020\u00072\b\b\u0002\u0010\u0012\u001a\u00020\u00072\b\b\u0002\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u00142\b\b\u0002\u0010\u0016\u001a\u00020\u00142\b\b\u0002\u0010\u0017\u001a\u00020\u00142\b\b\u0002\u0010\u0018\u001a\u00020\u00142\b\b\u0002\u0010\u0019\u001a\u00020\u00142\b\b\u0002\u0010\u001a\u001a\u00020\u0014H\u00c6\u0001J\u0013\u0010K\u001a\u00020\u00142\b\u0010L\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010M\u001a\u00020NH\u00d6\u0001J\u0010\u0010O\u001a\u00020P2\b\b\u0002\u0010Q\u001a\u00020RJ\t\u0010S\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0015\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u001a\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001fR\u0011\u0010\f\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001dR\u0011\u0010\u0017\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001fR\u0011\u0010\u000b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001dR\u0011\u0010\r\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001dR\u0011\u0010\n\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001dR\u0011\u0010\u0019\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001fR\u0011\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0011\u0010\u0012\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001dR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010+R\u0011\u0010\u000f\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010\u001dR\u0011\u0010\u0018\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010\u001fR\u0011\u0010\u000e\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010\u001dR\u0011\u0010\u0011\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010\u001dR\u0011\u0010\u0010\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010\u001dR\u0011\u0010\t\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u0010\u001dR\u0011\u0010\u0016\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u0010\u001f\u00a8\u0006U"}, d2 = {"Lcom/deepeye/musicpro/dsp/DSPPreset;", "", "id", "", "name", "eqGains", "", "", "bassBoost", "stereoWidth", "compressorThresholdDb", "compressorRatio", "compressorAttackMs", "compressorReleaseMs", "reverbRoom", "reverbDamping", "reverbWidth", "reverbWet", "limiterCeilingDb", "eqEnabled", "", "bassEnabled", "widthEnabled", "compressorEnabled", "reverbEnabled", "convolverEnabled", "builtIn", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;FFFFFFFFFFFZZZZZZZ)V", "getBassBoost", "()F", "getBassEnabled", "()Z", "getBuiltIn", "getCompressorAttackMs", "getCompressorEnabled", "getCompressorRatio", "getCompressorReleaseMs", "getCompressorThresholdDb", "getConvolverEnabled", "getEqEnabled", "getEqGains", "()Ljava/util/List;", "getId", "()Ljava/lang/String;", "getLimiterCeilingDb", "getName", "getReverbDamping", "getReverbEnabled", "getReverbRoom", "getReverbWet", "getReverbWidth", "getStereoWidth", "getWidthEnabled", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toEntity", "Lcom/deepeye/musicpro/db/DspPresetEntity;", "updatedAt", "", "toString", "Companion", "app_debug"})
public final class DSPPreset {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Float> eqGains = null;
    private final float bassBoost = 0.0F;
    private final float stereoWidth = 0.0F;
    private final float compressorThresholdDb = 0.0F;
    private final float compressorRatio = 0.0F;
    private final float compressorAttackMs = 0.0F;
    private final float compressorReleaseMs = 0.0F;
    private final float reverbRoom = 0.0F;
    private final float reverbDamping = 0.0F;
    private final float reverbWidth = 0.0F;
    private final float reverbWet = 0.0F;
    private final float limiterCeilingDb = 0.0F;
    private final boolean eqEnabled = false;
    private final boolean bassEnabled = false;
    private final boolean widthEnabled = false;
    private final boolean compressorEnabled = false;
    private final boolean reverbEnabled = false;
    private final boolean convolverEnabled = false;
    private final boolean builtIn = false;
    @org.jetbrains.annotations.NotNull()
    private static final com.deepeye.musicpro.dsp.DSPPreset Flat = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<com.deepeye.musicpro.dsp.DSPPreset> BuiltIns = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.deepeye.musicpro.dsp.DSPPreset.Companion Companion = null;
    
    public DSPPreset(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Float> eqGains, float bassBoost, float stereoWidth, float compressorThresholdDb, float compressorRatio, float compressorAttackMs, float compressorReleaseMs, float reverbRoom, float reverbDamping, float reverbWidth, float reverbWet, float limiterCeilingDb, boolean eqEnabled, boolean bassEnabled, boolean widthEnabled, boolean compressorEnabled, boolean reverbEnabled, boolean convolverEnabled, boolean builtIn) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Float> getEqGains() {
        return null;
    }
    
    public final float getBassBoost() {
        return 0.0F;
    }
    
    public final float getStereoWidth() {
        return 0.0F;
    }
    
    public final float getCompressorThresholdDb() {
        return 0.0F;
    }
    
    public final float getCompressorRatio() {
        return 0.0F;
    }
    
    public final float getCompressorAttackMs() {
        return 0.0F;
    }
    
    public final float getCompressorReleaseMs() {
        return 0.0F;
    }
    
    public final float getReverbRoom() {
        return 0.0F;
    }
    
    public final float getReverbDamping() {
        return 0.0F;
    }
    
    public final float getReverbWidth() {
        return 0.0F;
    }
    
    public final float getReverbWet() {
        return 0.0F;
    }
    
    public final float getLimiterCeilingDb() {
        return 0.0F;
    }
    
    public final boolean getEqEnabled() {
        return false;
    }
    
    public final boolean getBassEnabled() {
        return false;
    }
    
    public final boolean getWidthEnabled() {
        return false;
    }
    
    public final boolean getCompressorEnabled() {
        return false;
    }
    
    public final boolean getReverbEnabled() {
        return false;
    }
    
    public final boolean getConvolverEnabled() {
        return false;
    }
    
    public final boolean getBuiltIn() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.db.DspPresetEntity toEntity(long updatedAt) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final float component10() {
        return 0.0F;
    }
    
    public final float component11() {
        return 0.0F;
    }
    
    public final float component12() {
        return 0.0F;
    }
    
    public final float component13() {
        return 0.0F;
    }
    
    public final float component14() {
        return 0.0F;
    }
    
    public final boolean component15() {
        return false;
    }
    
    public final boolean component16() {
        return false;
    }
    
    public final boolean component17() {
        return false;
    }
    
    public final boolean component18() {
        return false;
    }
    
    public final boolean component19() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final boolean component20() {
        return false;
    }
    
    public final boolean component21() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Float> component3() {
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
    public final com.deepeye.musicpro.dsp.DSPPreset copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Float> eqGains, float bassBoost, float stereoWidth, float compressorThresholdDb, float compressorRatio, float compressorAttackMs, float compressorReleaseMs, float reverbRoom, float reverbDamping, float reverbWidth, float reverbWet, float limiterCeilingDb, boolean eqEnabled, boolean bassEnabled, boolean widthEnabled, boolean compressorEnabled, boolean reverbEnabled, boolean convolverEnabled, boolean builtIn) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\rR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000e"}, d2 = {"Lcom/deepeye/musicpro/dsp/DSPPreset$Companion;", "", "()V", "BuiltIns", "", "Lcom/deepeye/musicpro/dsp/DSPPreset;", "getBuiltIns", "()Ljava/util/List;", "Flat", "getFlat", "()Lcom/deepeye/musicpro/dsp/DSPPreset;", "fromEntity", "entity", "Lcom/deepeye/musicpro/db/DspPresetEntity;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.deepeye.musicpro.dsp.DSPPreset getFlat() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.deepeye.musicpro.dsp.DSPPreset> getBuiltIns() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.deepeye.musicpro.dsp.DSPPreset fromEntity(@org.jetbrains.annotations.NotNull()
        com.deepeye.musicpro.db.DspPresetEntity entity) {
            return null;
        }
    }
}