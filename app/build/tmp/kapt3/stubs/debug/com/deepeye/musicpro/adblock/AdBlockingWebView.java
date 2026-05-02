package com.deepeye.musicpro.adblock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u001b\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\bJ\u0006\u0010\u0012\u001a\u00020\u000eR\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f\u00a8\u0006\u0013"}, d2 = {"Lcom/deepeye/musicpro/adblock/AdBlockingWebView;", "Landroid/webkit/WebView;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "adBlockEngine", "Lcom/deepeye/musicpro/adblock/AdBlockEngine;", "getAdBlockEngine", "()Lcom/deepeye/musicpro/adblock/AdBlockEngine;", "setAdBlockEngine", "(Lcom/deepeye/musicpro/adblock/AdBlockEngine;)V", "loadFallback", "", "url", "", "engine", "release", "app_debug"})
@android.annotation.SuppressLint(value = {"SetJavaScriptEnabled"})
public final class AdBlockingWebView extends android.webkit.WebView {
    @org.jetbrains.annotations.Nullable()
    private com.deepeye.musicpro.adblock.AdBlockEngine adBlockEngine;
    
    @kotlin.jvm.JvmOverloads()
    public AdBlockingWebView(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
    
    @kotlin.jvm.JvmOverloads()
    public AdBlockingWebView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.deepeye.musicpro.adblock.AdBlockEngine getAdBlockEngine() {
        return null;
    }
    
    public final void setAdBlockEngine(@org.jetbrains.annotations.Nullable()
    com.deepeye.musicpro.adblock.AdBlockEngine p0) {
    }
    
    public final void loadFallback(@org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.NotNull()
    com.deepeye.musicpro.adblock.AdBlockEngine engine) {
    }
    
    public final void release() {
    }
}