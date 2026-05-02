package com.deepeye.musicpro.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.deepeye.musicpro.DeepEyeApp
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED || intent.action == Intent.ACTION_MY_PACKAGE_REPLACED) {
            DeepEyeApp.from(context).applicationScope.launch {
                DeepEyeApp.from(context).appRepository.warmUp()
            }
        }
    }
}
