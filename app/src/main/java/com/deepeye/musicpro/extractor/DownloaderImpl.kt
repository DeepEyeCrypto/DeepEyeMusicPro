package com.deepeye.musicpro.extractor

import org.schabi.newpipe.extractor.downloader.Request as ExtractorRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.schabi.newpipe.extractor.downloader.Downloader
import org.schabi.newpipe.extractor.downloader.Response
import java.io.IOException

class DownloaderImpl(
    private val httpClient: OkHttpClient
) : Downloader() {
    @Throws(IOException::class)
    override fun execute(request: ExtractorRequest): Response {
        val requestBuilder = Request.Builder()
            .url(request.url())
        request.headers().entries.forEach { entry ->
            entry.value.forEach { value -> requestBuilder.addHeader(entry.key, value) }
        }
        val requestBytes = request.dataToSend()
        val body = requestBytes?.toRequestBody("application/octet-stream".toMediaTypeOrNull())
        when (request.httpMethod().uppercase()) {
            "POST" -> requestBuilder.post(body ?: ByteArray(0).toRequestBody(null))
            "HEAD" -> requestBuilder.head()
            else -> requestBuilder.get()
        }
        httpClient.newCall(requestBuilder.build()).execute().use { response ->
            val responseHeaders = response.headers.toMultimap()
            val bodyString = response.body?.string().orEmpty()
            return Response(
                response.code,
                response.message,
                responseHeaders,
                bodyString,
                response.request.url.toString()
            )
        }
    }
}
