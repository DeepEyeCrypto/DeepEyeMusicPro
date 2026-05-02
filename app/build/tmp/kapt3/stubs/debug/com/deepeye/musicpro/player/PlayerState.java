package com.deepeye.musicpro.player;

import com.deepeye.musicpro.model.Track;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b!\b\u0086\b\u0018\u00002\u00020\u0001Bs\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b\u0012\b\b\u0002\u0010\r\u001a\u00020\t\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\t\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0012\u00a2\u0006\u0002\u0010\u0013J\u000b\u0010$\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0012H\u00c6\u0003J\u000f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0007H\u00c6\u0003J\t\u0010(\u001a\u00020\tH\u00c6\u0003J\t\u0010)\u001a\u00020\u000bH\u00c6\u0003J\t\u0010*\u001a\u00020\u000bH\u00c6\u0003J\t\u0010+\u001a\u00020\tH\u00c6\u0003J\t\u0010,\u001a\u00020\u000fH\u00c6\u0003J\t\u0010-\u001a\u00020\tH\u00c6\u0003Jw\u0010.\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\t2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\t2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u00c6\u0001J\u0013\u0010/\u001a\u00020\t2\b\u00100\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00101\u001a\u00020\u0007H\u00d6\u0001J\t\u00102\u001a\u00020\u0012H\u00d6\u0001R\u0011\u0010\u0010\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\f\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0015R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001bR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\r\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0015\u00a8\u00063"}, d2 = {"Lcom/deepeye/musicpro/player/PlayerState;", "", "currentTrack", "Lcom/deepeye/musicpro/model/Track;", "queue", "", "currentIndex", "", "isPlaying", "", "positionMs", "", "durationMs", "shuffleEnabled", "repeatMode", "Lcom/deepeye/musicpro/player/RepeatMode;", "buffering", "errorMessage", "", "(Lcom/deepeye/musicpro/model/Track;Ljava/util/List;IZJJZLcom/deepeye/musicpro/player/RepeatMode;ZLjava/lang/String;)V", "getBuffering", "()Z", "getCurrentIndex", "()I", "getCurrentTrack", "()Lcom/deepeye/musicpro/model/Track;", "getDurationMs", "()J", "getErrorMessage", "()Ljava/lang/String;", "getPositionMs", "getQueue", "()Ljava/util/List;", "getRepeatMode", "()Lcom/deepeye/musicpro/player/RepeatMode;", "getShuffleEnabled", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class PlayerState {
    @org.jetbrains.annotations.Nullable()
    private final com.deepeye.musicpro.model.Track currentTrack = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.deepeye.musicpro.model.Track> queue = null;
    private final int currentIndex = 0;
    private final boolean isPlaying = false;
    private final long positionMs = 0L;
    private final long durationMs = 0L;
    private final boolean shuffleEnabled = false;
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.player.RepeatMode repeatMode = null;
    private final boolean buffering = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String errorMessage = null;
    
    public PlayerState(@org.jetbrains.annotations.Nullable()
    com.deepeye.musicpro.model.Track currentTrack, @org.jetbrains.annotations.NotNull()
    java.util.List<com.deepeye.musicpro.model.Track> queue, int currentIndex, boolean isPlaying, long positionMs, long durationMs, boolean shuffleEnabled, @org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.player.RepeatMode repeatMode, boolean buffering, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.deepeye.musicpro.model.Track getCurrentTrack() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.deepeye.musicpro.model.Track> getQueue() {
        return null;
    }
    
    public final int getCurrentIndex() {
        return 0;
    }
    
    public final boolean isPlaying() {
        return false;
    }
    
    public final long getPositionMs() {
        return 0L;
    }
    
    public final long getDurationMs() {
        return 0L;
    }
    
    public final boolean getShuffleEnabled() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.player.RepeatMode getRepeatMode() {
        return null;
    }
    
    public final boolean getBuffering() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getErrorMessage() {
        return null;
    }
    
    public PlayerState() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.deepeye.musicpro.model.Track component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.deepeye.musicpro.model.Track> component2() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final long component5() {
        return 0L;
    }
    
    public final long component6() {
        return 0L;
    }
    
    public final boolean component7() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.player.RepeatMode component8() {
        return null;
    }
    
    public final boolean component9() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.player.PlayerState copy(@org.jetbrains.annotations.Nullable()
    com.deepeye.musicpro.model.Track currentTrack, @org.jetbrains.annotations.NotNull()
    java.util.List<com.deepeye.musicpro.model.Track> queue, int currentIndex, boolean isPlaying, long positionMs, long durationMs, boolean shuffleEnabled, @org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.player.RepeatMode repeatMode, boolean buffering, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage) {
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