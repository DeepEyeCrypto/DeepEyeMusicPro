package com.deepeye.musicpro.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.deepeye.musicpro.dsp.v4a.V4AEffect;
import com.deepeye.musicpro.dsp.v4a.V4AEffectsState;
import com.deepeye.musicpro.dsp.v4a.V4APreset;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0010\t\n\u0002\b)\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 D2\u00020\u0001:\u0001DB\u008f\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\f\u001a\u00020\t\u0012\u0006\u0010\r\u001a\u00020\t\u0012\u0006\u0010\u000e\u001a\u00020\t\u0012\u0006\u0010\u000f\u001a\u00020\t\u0012\u0006\u0010\u0010\u001a\u00020\t\u0012\u0006\u0010\u0011\u001a\u00020\t\u0012\u0006\u0010\u0012\u001a\u00020\t\u0012\u0006\u0010\u0013\u001a\u00020\u0006\u0012\u0006\u0010\u0014\u001a\u00020\u0003\u0012\u0006\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\u0002\u0010\u0017J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\t\u0010-\u001a\u00020\tH\u00c6\u0003J\t\u0010.\u001a\u00020\tH\u00c6\u0003J\t\u0010/\u001a\u00020\tH\u00c6\u0003J\t\u00100\u001a\u00020\tH\u00c6\u0003J\t\u00101\u001a\u00020\u0006H\u00c6\u0003J\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\t\u00103\u001a\u00020\u0016H\u00c6\u0003J\t\u00104\u001a\u00020\u0003H\u00c6\u0003J\t\u00105\u001a\u00020\u0006H\u00c6\u0003J\u000f\u00106\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00c6\u0003J\u000b\u00107\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u00108\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u00109\u001a\u00020\tH\u00c6\u0003J\t\u0010:\u001a\u00020\tH\u00c6\u0003J\t\u0010;\u001a\u00020\tH\u00c6\u0003J\u00b3\u0001\u0010<\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\f\u001a\u00020\t2\b\b\u0002\u0010\r\u001a\u00020\t2\b\b\u0002\u0010\u000e\u001a\u00020\t2\b\b\u0002\u0010\u000f\u001a\u00020\t2\b\b\u0002\u0010\u0010\u001a\u00020\t2\b\b\u0002\u0010\u0011\u001a\u00020\t2\b\b\u0002\u0010\u0012\u001a\u00020\t2\b\b\u0002\u0010\u0013\u001a\u00020\u00062\b\b\u0002\u0010\u0014\u001a\u00020\u00032\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u00c6\u0001J\u0013\u0010=\u001a\u00020\u00062\b\u0010>\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010?\u001a\u00020@H\u00d6\u0001J\u0006\u0010A\u001a\u00020BJ\t\u0010C\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019R\u0011\u0010\u0014\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0019R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\f\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0010\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001fR\u0011\u0010\u000e\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001fR\u0011\u0010\r\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001fR\u0011\u0010\u000f\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001fR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0019R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010%R\u0011\u0010\u0013\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010%R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u0019R\u0011\u0010\u0012\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001fR\u0011\u0010\u0011\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001fR\u0011\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+\u00a8\u0006E"}, d2 = {"Lcom/deepeye/musicpro/db/V4APresetEntity;", "", "id", "", "name", "isBuiltIn", "", "eqGains", "", "", "convolverIR", "ddcProfile", "fetAttack", "fetRelease", "fetRatio", "fetThreshold", "fetKnee", "tubeWarmth", "reverbRoom", "masterEnabled", "enabledEffectsCsv", "updatedAt", "", "(Ljava/lang/String;Ljava/lang/String;ZLjava/util/List;Ljava/lang/String;Ljava/lang/String;FFFFFFFZLjava/lang/String;J)V", "getConvolverIR", "()Ljava/lang/String;", "getDdcProfile", "getEnabledEffectsCsv", "getEqGains", "()Ljava/util/List;", "getFetAttack", "()F", "getFetKnee", "getFetRatio", "getFetRelease", "getFetThreshold", "getId", "()Z", "getMasterEnabled", "getName", "getReverbRoom", "getTubeWarmth", "getUpdatedAt", "()J", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toPreset", "Lcom/deepeye/musicpro/dsp/v4a/V4APreset;", "toString", "Companion", "app_debug"})
@androidx.room.Entity(tableName = "v4a_presets")
public final class V4APresetEntity {
    @androidx.room.PrimaryKey()
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    private final boolean isBuiltIn = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Float> eqGains = null;
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
    private final java.lang.String enabledEffectsCsv = null;
    private final long updatedAt = 0L;
    @org.jetbrains.annotations.NotNull()
    public static final com.deepeye.musicpro.db.V4APresetEntity.Companion Companion = null;
    
    public V4APresetEntity(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, boolean isBuiltIn, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Float> eqGains, @org.jetbrains.annotations.Nullable()
    java.lang.String convolverIR, @org.jetbrains.annotations.Nullable()
    java.lang.String ddcProfile, float fetAttack, float fetRelease, float fetRatio, float fetThreshold, float fetKnee, float tubeWarmth, float reverbRoom, boolean masterEnabled, @org.jetbrains.annotations.NotNull()
    java.lang.String enabledEffectsCsv, long updatedAt) {
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
    
    public final boolean isBuiltIn() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Float> getEqGains() {
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
    public final java.lang.String getEnabledEffectsCsv() {
        return null;
    }
    
    public final long getUpdatedAt() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.dsp.v4a.V4APreset toPreset() {
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
    
    public final boolean component14() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component15() {
        return null;
    }
    
    public final long component16() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final boolean component3() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Float> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
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
    public final com.deepeye.musicpro.db.V4APresetEntity copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, boolean isBuiltIn, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Float> eqGains, @org.jetbrains.annotations.Nullable()
    java.lang.String convolverIR, @org.jetbrains.annotations.Nullable()
    java.lang.String ddcProfile, float fetAttack, float fetRelease, float fetRatio, float fetThreshold, float fetKnee, float tubeWarmth, float reverbRoom, boolean masterEnabled, @org.jetbrains.annotations.NotNull()
    java.lang.String enabledEffectsCsv, long updatedAt) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u00a8\u0006\t"}, d2 = {"Lcom/deepeye/musicpro/db/V4APresetEntity$Companion;", "", "()V", "fromPreset", "Lcom/deepeye/musicpro/db/V4APresetEntity;", "preset", "Lcom/deepeye/musicpro/dsp/v4a/V4APreset;", "updatedAt", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.deepeye.musicpro.db.V4APresetEntity fromPreset(@org.jetbrains.annotations.NotNull()
        com.deepeye.musicpro.dsp.v4a.V4APreset preset, long updatedAt) {
            return null;
        }
    }
}