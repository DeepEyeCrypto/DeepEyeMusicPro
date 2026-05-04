package com.deepeye.musicpro.debug

import android.os.Handler
import android.os.Looper
import android.util.Log

/**
 * Watchdog thread to detect main thread hangs (ANRs) before the system does.
 * Useful for debugging deadlocks in native DSP calls.
 */
class ANRDetector(private val timeoutMs: Long = 5000) : Thread() {

    private val handler = Handler(Looper.getMainLooper())
    private val TAG = "ANRDetector"
    
    @Volatile
    private var tick = 0L
    
    @Volatile
    private var lastTick = 0L

    override fun run() {
        Log.i(TAG, "Starting ANR Watchdog (Timeout: ${timeoutMs}ms)")
        
        while (!isInterrupted) {
            val currentTick = tick
            
            // Post a "tick" to the main thread
            handler.post {
                tick++
            }

            try {
                sleep(timeoutMs)
            } catch (e: InterruptedException) {
                break
            }

            // If tick hasn't incremented, the main thread is blocked
            if (tick == currentTick && tick == lastTick) {
                reportANR()
            }
            lastTick = tick
        }
    }

    private fun reportANR() {
        val mainThread = Looper.getMainLooper().thread
        val stackTrace = mainThread.stackTrace
        
        Log.e(TAG, "⚠️ POTENTIAL ANR DETECTED! Main thread blocked.")
        Log.e(TAG, "Stack Trace:")
        for (element in stackTrace) {
            Log.e(TAG, "    at $element")
        }
        
        // Critical: Check if blocked in native DSP
        if (stackTrace.any { it.className.contains("NativeDSP") || it.isNativeMethod }) {
            Log.e(TAG, "🚨 BLOCKED IN NATIVE CODE! Possible deadlock or heavy processing.")
        }
    }
}
