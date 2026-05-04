package com.deepeye.musicpro.repository;

import com.deepeye.musicpro.db.AppDatabase;
import com.deepeye.musicpro.db.CachedTrack;
import com.deepeye.musicpro.extractor.StreamExtractor;
import com.deepeye.musicpro.model.Track;
import kotlinx.coroutines.flow.StateFlow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u0012J$\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\n0\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0017\u0010\u0018J(\u0010\u0019\u001a\u0004\u0018\u00010\n2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\n0\t2\b\b\u0002\u0010\u001b\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0016\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0011\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u0012R\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006 "}, d2 = {"Lcom/deepeye/musicpro/repository/PlayerRepository;", "", "database", "Lcom/deepeye/musicpro/db/AppDatabase;", "streamExtractor", "Lcom/deepeye/musicpro/extractor/StreamExtractor;", "(Lcom/deepeye/musicpro/db/AppDatabase;Lcom/deepeye/musicpro/extractor/StreamExtractor;)V", "_queue", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/deepeye/musicpro/model/Track;", "queue", "Lkotlinx/coroutines/flow/StateFlow;", "getQueue", "()Lkotlinx/coroutines/flow/StateFlow;", "markPlayed", "", "track", "(Lcom/deepeye/musicpro/model/Track;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resolveTrack", "Lkotlin/Result;", "input", "", "resolveTrack-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setQueue", "tracks", "startIndex", "", "(Ljava/util/List;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toggleFavorite", "", "app_debug"})
public final class PlayerRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.db.AppDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.extractor.StreamExtractor streamExtractor = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.deepeye.musicpro.model.Track>> _queue = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.deepeye.musicpro.model.Track>> queue = null;
    
    public PlayerRepository(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.db.AppDatabase database, @org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.extractor.StreamExtractor streamExtractor) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.deepeye.musicpro.model.Track>> getQueue() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setQueue(@org.jetbrains.annotations.NotNull()
    java.util.List<com.deepeye.musicpro.model.Track> tracks, int startIndex, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.deepeye.musicpro.model.Track> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object markPlayed(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.model.Track track, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object toggleFavorite(@org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.model.Track track, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
}