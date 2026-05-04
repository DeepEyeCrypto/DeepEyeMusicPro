package com.deepeye.musicpro.routing

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Build

object AlexaEchoDetector {
    fun getEchoDevices(context: Context): List<BluetoothDevice> {
        return try {
            BluetoothAdapter.getDefaultAdapter()?.bondedDevices?.filter { device ->
                device.name?.contains("Echo", ignoreCase = true) == true ||
                device.name?.contains("Alexa", ignoreCase = true) == true ||
                device.name?.contains("Amazon", ignoreCase = true) == true
            } ?: emptyList()
        } catch (e: SecurityException) {
            emptyList()
        }
    }

    fun isAnyEchoConnected(context: Context): Boolean {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS).any { device ->
                device.type == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP &&
                (device.productName?.contains("Echo", ignoreCase = true) == true ||
                 device.productName?.contains("Amazon", ignoreCase = true) == true)
            }
        } else {
            audioManager.isBluetoothA2dpOn
        }
    }
}
