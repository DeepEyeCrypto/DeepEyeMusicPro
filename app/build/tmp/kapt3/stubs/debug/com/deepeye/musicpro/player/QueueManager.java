package com.deepeye.musicpro.player;

import com.deepeye.musicpro.model.Track;
import kotlinx.coroutines.flow.StateFlow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\bJ\b\u0010\u0012\u001a\u0004\u0018\u00010\bJ\u0010\u0010\u0013\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\u0005J\u0018\u0010\u0014\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\b\u0010\u0019\u001a\u0004\u0018\u00010\bJ\u000e\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001cJ\u001e\u0010\u001d\u001a\u00020\u00102\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010\u001f\u001a\u00020\u0005R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\f\u00a8\u0006 "}, d2 = {"Lcom/deepeye/musicpro/player/QueueManager;", "", "()V", "_index", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_queue", "", "Lcom/deepeye/musicpro/model/Track;", "index", "Lkotlinx/coroutines/flow/StateFlow;", "getIndex", "()Lkotlinx/coroutines/flow/StateFlow;", "queue", "getQueue", "add", "", "track", "current", "moveTo", "next", "repeatMode", "Lcom/deepeye/musicpro/player/RepeatMode;", "shuffle", "", "previous", "remove", "trackId", "", "setQueue", "tracks", "startIndex", "app_debug"})
public final class QueueManager {
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.deepeye.musicpro.model.Track>> _queue = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> _index = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.deepeye.musicpro.model.Track>> queue = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> index = null;
    
    public QueueManager() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.deepeye.musicpro.model.Track>> getQueue() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getIndex() {
        return null;
    }
    
    public final void setQueue(@org.jetbrains.annotations.NotNull()
    java.util.List<com.deepeye.musicpro.model.Track> tracks, int startIndex) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.deepeye.musicpro.model.Track current() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.deepeye.musicpro.model.Track moveTo(int index) {
        return null;
    }
    
    public final void add(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.model.Track track) {
    }
    
    public final void remove(@org.jetbrains.annotations.NotNull()
    java.lang.String trackId) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.deepeye.musicpro.model.Track next(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.player.RepeatMode repeatMode, boolean shuffle) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.deepeye.musicpro.model.Track previous() {
        return null;
    }
}