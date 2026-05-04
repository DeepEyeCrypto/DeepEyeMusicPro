package com.deepeye.musicpro

import android.content.Context
import android.os.StrictMode
import android.util.Log
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.system.exitProcess

/**
 * Global Emergency Crash Handler & StrictMode Policy.
 * Captures Java/Kotlin fatal exceptions before the OS kills the process.
 */
object CrashGuard {
    private const val TAG = "CrashGuard"
    private var defaultHandler: Thread.UncaughtExceptionHandler? = null

    fun init(context: Context, isDebug: Boolean) {
        if (isDebug) {
            enableStrictMode()
        }

        defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            handleCrash(context, thread, throwable)
        }
    }

    private fun handleCrash(context: Context, thread: Thread, throwable: Throwable) {
        Log.e(TAG, "FATAL EXCEPTION: ${thread.name}", throwable)
        
        try {
            // Save local crash log for user retrieval / analytics upload
            val crashDir = File(context.cacheDir, "crash_logs").apply { mkdirs() }
            val crashFile = File(crashDir, "crash_${System.currentTimeMillis()}.log")
            
            val sw = StringWriter()
            throwable.printStackTrace(PrintWriter(sw))
            
            crashFile.writeText("Thread: ${thread.name}\n\n${sw.toString()}")
            Log.e(TAG, "Crash report saved locally to ${crashFile.absolutePath}")
            
            // TODO: Upload to Crashlytics / Sentry / Custom Backend here
            
        } catch (e: Exception) {
            Log.e(TAG, "Failed to persist crash log", e)
        }

        // Delegate to default Android handler (shows ANR/Crash dialog and kills process)
        defaultHandler?.uncaughtException(thread, throwable) ?: exitProcess(1)
    }

    private fun enableStrictMode() {
        Log.i(TAG, "Enabling StrictMode penaltyDeath for debug build")
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath() // Force crash on UI thread IO
                .build()
        )
        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .detectActivityLeaks()
                .penaltyLog()
                .penaltyDeath()
                .build()
        )
    }
}
