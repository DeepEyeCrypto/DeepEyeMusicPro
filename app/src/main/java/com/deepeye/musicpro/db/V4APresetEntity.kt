package com.deepeye.musicpro.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deepeye.musicpro.dsp.v4a.V4AEffect
import com.deepeye.musicpro.dsp.v4a.V4AEffectsState
import com.deepeye.musicpro.dsp.v4a.V4APreset

@Entity(tableName = "v4a_presets")
data class V4APresetEntity(
    @PrimaryKey val id: String,
    val name: String,
    val isBuiltIn: Boolean,
    val eqGains: List<Float>,
    val convolverIR: String?,
    val ddcProfile: String?,
    val fetAttack: Float,
    val fetRelease: Float,
    val fetRatio: Float,
    val fetThreshold: Float,
    val fetKnee: Float,
    val tubeWarmth: Float,
    val reverbRoom: Float,
    val masterEnabled: Boolean,
    val enabledEffectsCsv: String,
    val updatedAt: Long
) {
    fun toPreset(): V4APreset {
        val enabled = enabledEffectsCsv.split(',')
            .mapNotNull { value -> V4AEffect.values().firstOrNull { it.name == value } }
            .toSet()
        return V4APreset(
            id = id,
            name = name,
            isBuiltIn = isBuiltIn,
            effects = V4AEffectsState(
                eqGains = FloatArray(V4AEffectsState.EQ_BANDS) { index -> eqGains.getOrNull(index) ?: 0f },
                convolverIR = convolverIR,
                ddcProfile = ddcProfile,
                fetAttack = fetAttack,
                fetRelease = fetRelease,
                fetRatio = fetRatio,
                fetThreshold = fetThreshold,
                fetKnee = fetKnee,
                tubeWarmth = tubeWarmth,
                reverbRoom = reverbRoom,
                masterEnabled = masterEnabled,
                enabledEffects = enabled.ifEmpty { V4AEffectsState.Default.enabledEffects }
            )
        )
    }

    companion object {
        fun fromPreset(preset: V4APreset, updatedAt: Long): V4APresetEntity {
            val effects = preset.effects
            return V4APresetEntity(
                id = preset.id,
                name = preset.name,
                isBuiltIn = preset.isBuiltIn,
                eqGains = effects.normalizedEqGains().toList(),
                convolverIR = effects.convolverIR,
                ddcProfile = effects.ddcProfile,
                fetAttack = effects.fetAttack,
                fetRelease = effects.fetRelease,
                fetRatio = effects.fetRatio,
                fetThreshold = effects.fetThreshold,
                fetKnee = effects.fetKnee,
                tubeWarmth = effects.tubeWarmth,
                reverbRoom = effects.reverbRoom,
                masterEnabled = effects.masterEnabled,
                enabledEffectsCsv = effects.enabledEffects.joinToString(",") { it.name },
                updatedAt = updatedAt
            )
        }
    }
}
