package com.deepeye.musicpro.adblock

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

@SuppressLint("SetJavaScriptEnabled")
class AdBlockingWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : WebView(context, attrs) {
    var adBlockEngine: AdBlockEngine? = null

    init {
        setBackgroundColor(Color.BLACK)
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.mediaPlaybackRequiresUserGesture = false
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.userAgentString = settings.userAgentString + " DeepEyeMusicPro/1.0"
        webChromeClient = WebChromeClient()
        webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
                val url = request.url.toString()
                val engine = adBlockEngine ?: return null
                return if (engine.shouldBlock(url)) engine.createBlockResponse() else null
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                val script = adBlockEngine?.autoSkipScript() ?: return
                evaluateJavascript(script, null)
            }
        }
    }

    fun loadFallback(url: String, engine: AdBlockEngine) {
        adBlockEngine = engine
        loadUrl(url)
    }

    fun release() {
        stopLoading()
        loadUrl("about:blank")
        clearHistory()
        removeAllViews()
        destroy()
    }
}
