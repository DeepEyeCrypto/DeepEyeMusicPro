package com.deepeye.musicpro.dsp.v4a

import android.media.audiofx.AudioEffect
import java.util.UUID

object V4ADetector {
    val V4A_UUID: UUID = UUID.fromString("90380da3-8536-4744-a6a3-5731970e640f")
    val V4A_LEGACY_UUID: UUID = UUID.fromString("41d3c987-e6cf-11e3-a88a-11aba5d5c51b")

    fun isV4AInstalled(): Boolean = findV4ADescriptor() != null

    fun findV4ADescriptor(): AudioEffect.Descriptor? {
        return runCatching {
            AudioEffect.queryEffects()?.firstOrNull { descriptor ->
                descriptor.uuid == V4A_UUID || descriptor.uuid == V4A_LEGACY_UUID
            }
        }.getOrNull()
    }
}
