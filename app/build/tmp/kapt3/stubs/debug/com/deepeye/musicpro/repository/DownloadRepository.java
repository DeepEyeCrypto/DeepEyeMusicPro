package com.deepeye.musicpro.repository;

import android.content.Context;
import com.deepeye.musicpro.db.AppDatabase;
import com.deepeye.musicpro.db.CachedTrack;
import com.deepeye.musicpro.extractor.StreamExtractor;
import com.deepeye.musicpro.model.DownloadState;
import com.deepeye.musicpro.model.Track;
import com.deepeye.musicpro.util.FileUtils;
import kotlinx.coroutines.flow.StateFlow;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import java.io.File;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00a2\u0006\u0002\u0010\u0016J$\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010\u001a\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001b\u0010\u0016J\u0012\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\u001e0\u001dR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006 "}, d2 = {"Lcom/deepeye/musicpro/repository/DownloadRepository;", "", "context", "Landroid/content/Context;", "database", "Lcom/deepeye/musicpro/db/AppDatabase;", "streamExtractor", "Lcom/deepeye/musicpro/extractor/StreamExtractor;", "httpClient", "Lokhttp3/OkHttpClient;", "(Landroid/content/Context;Lcom/deepeye/musicpro/db/AppDatabase;Lcom/deepeye/musicpro/extractor/StreamExtractor;Lokhttp3/OkHttpClient;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/deepeye/musicpro/model/DownloadState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "deleteDownload", "", "trackId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "download", "Lkotlin/Result;", "Lcom/deepeye/musicpro/model/Track;", "input", "download-gIAlu-s", "observeDownloads", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/deepeye/musicpro/db/CachedTrack;", "app_debug"})
public final class DownloadRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.db.AppDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.extractor.StreamExtractor streamExtractor = null;
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.OkHttpClient httpClient = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.deepeye.musicpro.model.DownloadState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.deepeye.musicpro.model.DownloadState> state = null;
    
    public DownloadRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.db.AppDatabase database, @org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.extractor.StreamExtractor streamExtractor, @org.jetbrains.annotations.NotNull()
    okhttp3.OkHttpClient httpClient) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.deepeye.musicpro.model.DownloadState> getState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.deepeye.musicpro.db.CachedTrack>> observeDownloads() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteDownload(@org.jetbrains.annotations.NotNull()
    java.lang.String trackId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
}