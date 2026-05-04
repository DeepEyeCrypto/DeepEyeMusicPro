package com.deepeye.musicpro.extractor

import okhttp3.Interceptor
import okhttp3.Response

class PrivacyInterceptor : Interceptor {
    companion object {
        private const val USER_AGENT = "Mozilla/5.0 (Linux; Android 13; Pixel 7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Mobile Safari/537.36"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()

        // 1. Scrub sensitive headers that might have leaked from other parts of the app
        builder.removeHeader("Authorization")
        builder.removeHeader("X-GData-Key")
        
        // 2. Anonymize Fingerprint
        builder.header("User-Agent", USER_AGENT)
        
        // 3. Set standard privacy-preserving headers
        builder.header("DNT", "1") // Do Not Track
        builder.header("Sec-Fetch-Site", "cross-site")
        builder.header("Sec-Fetch-Mode", "navigate")
        
        // 4. Referrer Policy: Never send full URL
        builder.header("Referer", "https://www.youtube.com/")

        val sanitizedRequest = builder.build()
        return chain.proceed(sanitizedRequest)
    }
}
