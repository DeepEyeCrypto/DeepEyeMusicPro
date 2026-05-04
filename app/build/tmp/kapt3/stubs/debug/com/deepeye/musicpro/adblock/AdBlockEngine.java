package com.deepeye.musicpro.adblock;

import android.webkit.WebResourceResponse;
import com.deepeye.musicpro.util.Logger;
import kotlinx.coroutines.Dispatchers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.concurrent.atomic.AtomicBoolean;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0007\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000e\u001a\u00020\u0007J\u0006\u0010\u000f\u001a\u00020\u0007J\u0006\u0010\u0010\u001a\u00020\u0011J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0013\u001a\u00020\u0007H\u0002J\u000e\u0010\u0014\u001a\u00020\u0015H\u0086@\u00a2\u0006\u0002\u0010\u0016J\u0010\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0007H\u0002J\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0013\u001a\u00020\u0007R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/deepeye/musicpro/adblock/AdBlockEngine;", "", "httpClient", "Lokhttp3/OkHttpClient;", "(Lokhttp3/OkHttpClient;)V", "cssSelectors", "Ljava/util/LinkedHashSet;", "", "domains", "loaded", "Ljava/util/concurrent/atomic/AtomicBoolean;", "patterns", "", "Lkotlin/text/Regex;", "autoSkipScript", "cosmeticCss", "createBlockResponse", "Landroid/webkit/WebResourceResponse;", "extractHost", "url", "loadFilterLists", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "parseFilterList", "content", "shouldBlock", "", "Companion", "app_debug"})
public final class AdBlockEngine {
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.OkHttpClient httpClient = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.atomic.AtomicBoolean loaded = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.LinkedHashSet<java.lang.String> domains = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<kotlin.text.Regex> patterns = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.LinkedHashSet<java.lang.String> cssSelectors = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "AdBlockEngine";
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> FILTER_LIST_URLS = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.deepeye.musicpro.adblock.AdBlockEngine.Companion Companion = null;
    
    public AdBlockEngine(@org.jetbrains.annotations.NotNull()
    okhttp3.OkHttpClient httpClient) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object loadFilterLists(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final boolean shouldBlock(@org.jetbrains.annotations.NotNull()
    java.lang.String url) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.webkit.WebResourceResponse createBlockResponse() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String cosmeticCss() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String autoSkipScript() {
        return null;
    }
    
    private final void parseFilterList(java.lang.String content) {
    }
    
    private final java.lang.String extractHost(java.lang.String url) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/deepeye/musicpro/adblock/AdBlockEngine$Companion;", "", "()V", "FILTER_LIST_URLS", "", "", "getFILTER_LIST_URLS", "()Ljava/util/List;", "TAG", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> getFILTER_LIST_URLS() {
            return null;
        }
    }
}