package com.deepeye.musicpro.extractor;

import com.deepeye.musicpro.model.SearchResult;
import com.deepeye.musicpro.model.Track;
import com.deepeye.musicpro.util.Logger;
import kotlinx.coroutines.Dispatchers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONArray;
import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeSearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0002J*\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\r2\u0006\u0010\n\u001a\u00020\u000bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0012"}, d2 = {"Lcom/deepeye/musicpro/extractor/SearchService;", "", "httpClient", "Lokhttp3/OkHttpClient;", "streamExtractor", "Lcom/deepeye/musicpro/extractor/StreamExtractor;", "(Lokhttp3/OkHttpClient;Lcom/deepeye/musicpro/extractor/StreamExtractor;)V", "newPipeSearch", "", "Lcom/deepeye/musicpro/model/SearchResult;", "query", "", "search", "Lkotlin/Result;", "search-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "suggestionSearch", "Companion", "app_debug"})
public final class SearchService {
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.OkHttpClient httpClient = null;
    @org.jetbrains.annotations.NotNull()
    private final com.deepeye.musicpro.extractor.StreamExtractor streamExtractor = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "SearchService";
    @org.jetbrains.annotations.NotNull()
    public static final com.deepeye.musicpro.extractor.SearchService.Companion Companion = null;
    
    public SearchService(@org.jetbrains.annotations.NotNull()
    okhttp3.OkHttpClient httpClient, @org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.extractor.StreamExtractor streamExtractor) {
        super();
    }
    
    private final java.util.List<com.deepeye.musicpro.model.SearchResult> newPipeSearch(java.lang.String query) {
        return null;
    }
    
    private final java.util.List<com.deepeye.musicpro.model.SearchResult> suggestionSearch(java.lang.String query) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/deepeye/musicpro/extractor/SearchService$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}