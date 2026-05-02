package com.deepeye.musicpro.dsp

import com.deepeye.musicpro.db.DspPresetEntity

private fun eq(vararg values: Float): List<Float> = values.toList()

data class DSPPreset(
    val id: String,
    val name: String,
    val eqGains: List<Float>,
    val bassBoost: Float,
    val stereoWidth: Float,
    val compressorThresholdDb: Float,
    val compressorRatio: Float,
    val compressorAttackMs: Float,
    val compressorReleaseMs: Float,
    val reverbRoom: Float,
    val reverbDamping: Float,
    val reverbWidth: Float,
    val reverbWet: Float,
    val limiterCeilingDb: Float,
    val eqEnabled: Boolean = true,
    val bassEnabled: Boolean = true,
    val widthEnabled: Boolean = true,
    val compressorEnabled: Boolean = true,
    val reverbEnabled: Boolean = false,
    val convolverEnabled: Boolean = false,
    val builtIn: Boolean = true
) {
    fun toEntity(updatedAt: Long = System.currentTimeMillis()): DspPresetEntity = DspPresetEntity(
        id = id,
        name = name,
        eqGains = eqGains,
        bassBoost = bassBoost,
        stereoWidth = stereoWidth,
        compressorThresholdDb = compressorThresholdDb,
        compressorRatio = compressorRatio,
        compressorAttackMs = compressorAttackMs,
        compressorReleaseMs = compressorReleaseMs,
        reverbRoom = reverbRoom,
        reverbDamping = reverbDamping,
        reverbWidth = reverbWidth,
        reverbWet = reverbWet,
        limiterCeilingDb = limiterCeilingDb,
        builtIn = builtIn,
        updatedAt = updatedAt
    )

    companion object {
        val Flat = DSPPreset("flat", "Flat", List(10) { 0f }, 0f, 0f, -4f, 1.5f, 8f, 160f, 0.2f, 0.5f, 0.3f, 0f, -0.8f, bassEnabled = false, widthEnabled = false)
        val BuiltIns = listOf(
            Flat,
            DSPPreset("bass_boost", "Bass Boost", eq(5f, 4.5f, 3.5f, 2f, 0.5f, 0f, -0.5f, -1f, 0f, 1f), 0.35f, 0.08f, -8f, 2.2f, 8f, 180f, 0.25f, 0.6f, 0.35f, 0.02f, -1.0f),
            DSPPreset("vocal_clarity", "Vocal Clarity", eq(-2f, -1f, 0f, 1.5f, 3f, 3.5f, 2.5f, 1.5f, 0.5f, 0f), 0.08f, 0.02f, -10f, 2.5f, 5f, 130f, 0.18f, 0.7f, 0.25f, 0f, -1.2f),
            DSPPreset("treble_boost", "Treble Boost", eq(-1f, -1f, -0.5f, 0f, 0.5f, 1.5f, 2.5f, 3.5f, 4f, 4f), 0.02f, 0.06f, -7f, 1.8f, 6f, 150f, 0.14f, 0.7f, 0.3f, 0f, -1.0f),
            DSPPreset("hip_hop", "Hip Hop", eq(5f, 4f, 3f, 1f, -0.5f, 0f, 1f, 2f, 2.5f, 2f), 0.3f, 0.1f, -9f, 2.4f, 7f, 180f, 0.25f, 0.55f, 0.4f, 0.04f, -1.0f),
            DSPPreset("rock", "Rock", eq(3f, 2.5f, 1.5f, 0f, -0.5f, 0.5f, 2f, 3f, 3f, 2f), 0.18f, 0.07f, -8f, 2.1f, 6f, 140f, 0.18f, 0.55f, 0.35f, 0.03f, -1.0f),
            DSPPreset("classical", "Classical", eq(1f, 1f, 0.5f, 0f, 0f, 0.5f, 1f, 1.5f, 2f, 2f), 0.06f, 0.04f, -5f, 1.4f, 12f, 250f, 0.35f, 0.65f, 0.55f, 0.07f, -0.6f, reverbEnabled = true),
            DSPPreset("night_mode", "Night Mode", eq(1f, 0.5f, 0f, -0.5f, -1f, -1f, -0.5f, 0f, 0f, -0.5f), 0.08f, -0.03f, -18f, 3.8f, 4f, 220f, 0.1f, 0.7f, 0.15f, 0f, -4.0f),
            DSPPreset("gaming", "Gaming", eq(1.5f, 1f, 0.5f, 1f, 2f, 3f, 3f, 2f, 1f, 0f), 0.1f, 0.18f, -9f, 2f, 3f, 120f, 0.12f, 0.75f, 0.25f, 0f, -1.2f),
            DSPPreset("studio", "Studio", eq(0.5f, 0.5f, 0f, 0f, 0f, 0.5f, 0.5f, 0.5f, 0f, 0f), 0.04f, 0f, -6f, 1.6f, 10f, 220f, 0.08f, 0.8f, 0.15f, 0f, -0.8f)
        )

        fun fromEntity(entity: DspPresetEntity): DSPPreset = DSPPreset(
            id = entity.id,
            name = entity.name,
            eqGains = entity.eqGains.ifEmpty { List(10) { 0f } },
            bassBoost = entity.bassBoost,
            stereoWidth = entity.stereoWidth,
            compressorThresholdDb = entity.compressorThresholdDb,
            compressorRatio = entity.compressorRatio,
            compressorAttackMs = entity.compressorAttackMs,
            compressorReleaseMs = entity.compressorReleaseMs,
            reverbRoom = entity.reverbRoom,
            reverbDamping = entity.reverbDamping,
            reverbWidth = entity.reverbWidth,
            reverbWet = entity.reverbWet,
            limiterCeilingDb = entity.limiterCeilingDb,
            builtIn = entity.builtIn
        )
    }
}
