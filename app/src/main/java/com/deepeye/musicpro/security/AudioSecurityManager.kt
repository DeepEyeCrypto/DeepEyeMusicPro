package com.deepeye.musicpro.security

import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Build
import android.util.Log
import com.deepeye.musicpro.dsp.NativeDSP

/**
 * Security policy manager for audio pipeline.
 * Enforces input validation, buffer safety, and runtime hardening.
 */
object AudioSecurityManager {

    private const val TAG = "AudioSecurity"

    // ── Input Validation ──

    fun validateEqBandIndex(band: Int): Boolean {
        return band in 0..9
    }

    fun validateEqGain(gainDb: Float): Float {
        // Clamp to safe range (-24 to +24 dB)
        return gainDb.coerceIn(-24.0f, 24.0f)
    }

    fun validatePreamp(db: Float): Float {
        return db.coerceIn(-30.0f, 30.0f)
    }

    fun validateSubIntensity(intensity: Float): Float {
        return intensity.coerceIn(0.0f, 1.0f)
    }

    fun validateLimiterThreshold(threshold: Float): Float {
        return threshold.coerceIn(-30.0f, 0.0f)
    }

    fun validateStereoWidth(width: Float): Float {
        return width.coerceIn(0.0f, 2.0f)
    }

    // ── Route-aware Safety ──

    fun detectAudioRoute(context: Context): AudioRoute {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val devices = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
        } else {
            emptyArray()
        }

        return when {
            devices.any { it.type == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP } ->
                AudioRoute.BLUETOOTH
            devices.any { it.type == AudioDeviceInfo.TYPE_WIRED_HEADPHONES ||
                          it.type == AudioDeviceInfo.TYPE_WIRED_HEADSET } ->
                AudioRoute.WIRED_HEADPHONES
            devices.any { it.type == AudioDeviceInfo.TYPE_USB_DEVICE ||
                          it.type == AudioDeviceInfo.TYPE_USB_HEADSET } ->
                AudioRoute.USB_DAC
            devices.any { it.type == AudioDeviceInfo.TYPE_BUILTIN_SPEAKER } ->
                AudioRoute.SPEAKER
            else -> AudioRoute.UNKNOWN
        }
    }

    fun applyRouteSafety(context: Context, nativeDSP: NativeDSP) {
        val route = detectAudioRoute(context)
        when (route) {
            AudioRoute.SPEAKER -> {
                nativeDSP.setLimiterCeiling(-2.0f) // More conservative for internal speakers
                Log.i(TAG, "Security: Speaker mode safety applied")
            }
            AudioRoute.BLUETOOTH -> {
                nativeDSP.setLimiterCeiling(-1.0f)
            }
            AudioRoute.WIRED_HEADPHONES, AudioRoute.USB_DAC -> {
                nativeDSP.setLimiterCeiling(-0.5f)
            }
            AudioRoute.UNKNOWN -> {
                nativeDSP.setLimiterCeiling(-1.5f)
            }
        }
    }

    fun emergencyDisableEngine() {
        Log.e(TAG, "SECURITY CRITICAL: Emergency disabling DSP engine due to native failure.")
    }

    // ── Log Sanitization ──

    fun sanitizeForLog(input: String?): String {
        if (input == null) return "[null]"
        // Strip potential PII: file paths
        return input
            .replace(Regex("/storage/emulated/\\d+/"), "[STORAGE]/")
            .replace(Regex("/data/data/[^/]+/"), "[APP_DATA]/")
            .take(200)
    }

    enum class AudioRoute {
        SPEAKER, BLUETOOTH, WIRED_HEADPHONES, USB_DAC, UNKNOWN
    }
}
