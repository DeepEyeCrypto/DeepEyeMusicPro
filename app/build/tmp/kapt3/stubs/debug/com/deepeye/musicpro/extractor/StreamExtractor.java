package com.deepeye.musicpro.extractor;

import com.deepeye.musicpro.model.StreamExtractionResult;
import com.deepeye.musicpro.model.StreamFormat;
import com.deepeye.musicpro.model.Track;
import com.deepeye.musicpro.util.Logger;
import kotlinx.coroutines.Dispatchers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONObject;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.ServiceList;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0003\b\u0007\u0018\u0000  2\u00020\u0001:\u0001 B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u000b\u001a\u0004\u0018\u00010\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000eJ\u0006\u0010\u000f\u001a\u00020\u0010J$\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0010\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0015H\u0002J#\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u0019\u001a\u00020\u0015H\u0002\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\fH\u0002R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006!"}, d2 = {"Lcom/deepeye/musicpro/extractor/StreamExtractor;", "", "httpClient", "Lokhttp3/OkHttpClient;", "downloader", "Lcom/deepeye/musicpro/extractor/DownloaderImpl;", "(Lokhttp3/OkHttpClient;Lcom/deepeye/musicpro/extractor/DownloaderImpl;)V", "getDownloader", "()Lcom/deepeye/musicpro/extractor/DownloaderImpl;", "initialized", "Ljava/util/concurrent/atomic/AtomicBoolean;", "chooseBest", "Lcom/deepeye/musicpro/model/StreamFormat;", "formats", "", "ensureNewPipeInitialized", "", "extract", "Lkotlin/Result;", "Lcom/deepeye/musicpro/model/StreamExtractionResult;", "input", "", "extract-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "metadataFallback", "url", "newPipeExtraction", "newPipeExtraction-IoAF18A", "(Ljava/lang/String;)Ljava/lang/Object;", "scoreFormat", "", "format", "Companion", "app_debug"})
public final class StreamExtractor {
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.OkHttpClient httpClient = null;
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.extractor.DownloaderImpl downloader = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.atomic.AtomicBoolean initialized = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "StreamExtractor";
    @org.jetbrains.annotations.NotNull()
    public static final com.deepeye.musicpro.extractor.StreamExtractor.Companion Companion = null;
    
    public StreamExtractor(@org.jetbrains.annotations.NotNull()
    okhttp3.OkHttpClient httpClient, @org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.extractor.DownloaderImpl downloader) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.deepeye.musicpro.extractor.DownloaderImpl getDownloader() {
        return null;
    }
    
    public final void ensureNewPipeInitialized() {
    }
    
    private final com.deepeye.musicpro.model.StreamExtractionResult metadataFallback(java.lang.String url) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.deepeye.musicpro.model.StreamFormat chooseBest(@org.jetbrains.annotations.NotNull()
    java.util.List<com.deepeye.musicpro.model.StreamFormat> formats) {
        return null;
    }
    
    private final int scoreFormat(com.deepeye.musicpro.model.StreamFormat format) {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/deepeye/musicpro/extractor/StreamExtractor$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}