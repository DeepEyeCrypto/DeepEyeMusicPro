package com.deepeye.musicpro.adblock

import android.webkit.WebResourceResponse
import com.deepeye.musicpro.util.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.ByteArrayInputStream
import java.net.URI
import java.util.concurrent.atomic.AtomicBoolean

class AdBlockEngine(private val httpClient: OkHttpClient) {
    private val loaded = AtomicBoolean(false)
    private val domains = linkedSetOf(
        "doubleclick.net",
        "googlesyndication.com",
        "googleadservices.com",
        "youtube.com/pagead",
        "youtube.com/api/stats/ads"
    )
    private val patterns = mutableListOf(
        Regex(".*googlevideo\\.com.*[?&]oad=.*", RegexOption.IGNORE_CASE),
        Regex(".*youtube\\.com/(pagead|ptracking|api/stats/ads).*", RegexOption.IGNORE_CASE),
        Regex(".*doubleclick\\.net.*", RegexOption.IGNORE_CASE),
        Regex(".*googlesyndication\\.com.*", RegexOption.IGNORE_CASE)
    )
    private val cssSelectors = linkedSetOf(
        ".ytp-ad-module",
        ".video-ads",
        ".ytp-ad-overlay-container",
        "ytd-promoted-sparkles-web-renderer",
        "ytd-display-ad-renderer",
        "ytd-companion-slot-renderer"
    )

    suspend fun loadFilterLists() = withContext(Dispatchers.IO) {
        FILTER_LIST_URLS.forEach { url ->
            runCatching {
                val request = Request.Builder().url(url).get().build()
                httpClient.newCall(request).execute().use { response ->
                    if (response.isSuccessful) parseFilterList(response.body?.string().orEmpty())
                }
            }.onFailure { Logger.w(TAG, "Filter load failed: $url", it) }
        }
        loaded.set(true)
    }

    fun shouldBlock(url: String): Boolean {
        val host = extractHost(url)
        if (host != null && domains.any { host == it || host.endsWith(".$it") }) return true
        return patterns.any { it.containsMatchIn(url) }
    }

    fun createBlockResponse(): WebResourceResponse {
        return WebResourceResponse("text/plain", "UTF-8", ByteArrayInputStream(ByteArray(0)))
    }

    fun cosmeticCss(): String {
        val selectors = cssSelectors.joinToString(",")
        return "$selectors{display:none!important;visibility:hidden!important;opacity:0!important;}"
    }

    fun autoSkipScript(): String = """
        (function(){
          const css = `${cosmeticCss().replace("`", "")}`;
          if (!document.getElementById('deepeye-adblock-style')) {
            const style = document.createElement('style');
            style.id='deepeye-adblock-style'; style.textContent=css; document.documentElement.appendChild(style);
          }
          const clickSkip = function(){
            document.querySelectorAll('.ytp-ad-skip-button,.ytp-ad-skip-button-modern,.ytp-skip-ad-button').forEach(function(btn){ btn.click(); });
            document.querySelectorAll('.ytp-ad-overlay-close-button').forEach(function(btn){ btn.click(); });
          };
          clickSkip(); window.setInterval(clickSkip, 1000);
        })();
    """.trimIndent()

    private fun parseFilterList(content: String) {
        content.lineSequence().map { it.trim() }.filter { it.isNotEmpty() && !it.startsWith("!") && !it.startsWith("[") }.forEach { line ->
            when {
                line.startsWith("||") -> line.substring(2).takeWhile { it != '^' && it != '$' && it != '/' }.takeIf { it.contains('.') }?.let(domains::add)
                line.startsWith("0.0.0.0") || line.startsWith("127.0.0.1") -> line.split(Regex("\\s+")).getOrNull(1)?.let(domains::add)
                line.contains("##") -> line.substringAfter("##").takeIf { it.isNotBlank() }?.let(cssSelectors::add)
                line.contains('*') && line.length < 180 -> runCatching {
                    patterns.add(Regex(line.replace(".", "\\.").replace("*", ".*"), RegexOption.IGNORE_CASE))
                }
            }
        }
    }

    private fun extractHost(url: String): String? = runCatching { URI(url).host?.lowercase() }.getOrNull()

    companion object {
        private const val TAG = "AdBlockEngine"
        val FILTER_LIST_URLS = listOf(
            "https://easylist.to/easylist/easylist.txt",
            "https://easylist.to/easylist/easyprivacy.txt",
            "https://raw.githubusercontent.com/uBlockOrigin/uAssets/master/filters/filters.txt",
            "https://raw.githubusercontent.com/StevenBlack/hosts/master/hosts"
        )
    }
}
