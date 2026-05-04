package com.deepeye.musicpro.dsp.v4a;

import android.media.audiofx.AudioEffect;
import java.util.UUID;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u0004\u0018\u00010\nJ\u0006\u0010\u000b\u001a\u00020\fR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006\u00a8\u0006\r"}, d2 = {"Lcom/deepeye/musicpro/dsp/v4a/V4ADetector;", "", "()V", "V4A_LEGACY_UUID", "Ljava/util/UUID;", "getV4A_LEGACY_UUID", "()Ljava/util/UUID;", "V4A_UUID", "getV4A_UUID", "findV4ADescriptor", "Landroid/media/audiofx/AudioEffect$Descriptor;", "isV4AInstalled", "", "app_debug"})
public final class V4ADetector {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.UUID V4A_UUID = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.UUID V4A_LEGACY_UUID = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.deepeye.musicpro.dsp.v4a.V4ADetector INSTANCE = null;
    
    private V4ADetector() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.UUID getV4A_UUID() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.UUID getV4A_LEGACY_UUID() {
        return null;
    }
    
    public final boolean isV4AInstalled() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.media.audiofx.AudioEffect.Descriptor findV4ADescriptor() {
        return null;
    }
}