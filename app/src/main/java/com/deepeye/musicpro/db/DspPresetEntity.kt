package com.deepeye.musicpro.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dsp_presets")
data class DspPresetEntity(
    @PrimaryKey val id: String,
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
    val builtIn: Boolean,
    val updatedAt: Long
)
