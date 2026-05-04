package com.deepeye.musicpro.debug

import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * In-memory ring buffer for critical logs.
 * Can be dumped to a file or sent with a stability report if a crash occurs.
 */
object LogMonitor {

    private const val MAX_LOGS = 1000
    private val logQueue = ConcurrentLinkedQueue<String>()

    fun log(tag: String, message: String) {
        val timestamp = java.text.SimpleDateFormat("HH:mm:ss.SSS", Locale.US).format(Date())
        val formatted = "[$timestamp] $tag: $message"
        
        logQueue.add(formatted)
        if (logQueue.size > MAX_LOGS) {
            logQueue.poll()
        }
    }

    fun dump(): List<String> {
        return logQueue.toList()
    }

    fun clear() {
        logQueue.clear()
    }
}
