package com.deepeye.musicpro.dsp.v4a

import com.deepeye.musicpro.db.V4APresetEntity

private fun eq(vararg values: Float): FloatArray = values.copyOf()

data class V4APreset(
    val id: String,
    val name: String,
    val isBuiltIn: Boolean,
    val effects: V4AEffectsState
) {
    fun toEntity(updatedAt: Long = System.currentTimeMillis()): V4APresetEntity = V4APresetEntity.fromPreset(this, updatedAt)

    companion object {
        val SuperBass = V4APreset(
            id = "super_bass",
            name = "Super Bass",
            isBuiltIn = true,
            effects = V4AEffectsState.Default.copy(
                eqGains = eq(6f, 5.5f, 4f, 2f, 0.5f, 0f, -0.5f, 0f, 1f, 1.5f),
                convolverIR = "BassBoost.irs",
                fetAttack = 8f,
                fetRelease = 180f,
                fetRatio = 2.8f,
                fetThreshold = -12f,
                tubeWarmth = 0.45f,
                enabledEffects = setOf(V4AEffect.EQ, V4AEffect.CONVOLVER, V4AEffect.FET, V4AEffect.TUBE)
            )
        )

        val ClearVocal = V4APreset(
            id = "clear_vocal",
            name = "Clear Vocal",
            isBuiltIn = true,
            effects = V4AEffectsState.Default.copy(
                eqGains = eq(-2f, -1.5f, -0.5f, 1f, 2.5f, 3.5f, 3f, 1.5f, 0.5f, 0f),
                ddcProfile = "Vocal_Enhance.ddc",
                fetAttack = 4f,
                fetRelease = 110f,
                fetRatio = 1.8f,
                fetThreshold = -9f,
                tubeWarmth = 0.2f,
                enabledEffects = setOf(V4AEffect.EQ, V4AEffect.DDC, V4AEffect.FET)
            )
        )

        val ConcertHall = V4APreset(
            id = "concert_hall",
            name = "Concert Hall",
            isBuiltIn = true,
            effects = V4AEffectsState.Default.copy(
                eqGains = eq(1f, 1f, 0.5f, 0f, 0.5f, 1f, 1.5f, 2f, 2.5f, 2f),
                convolverIR = "Reverb_ConcertHall.irs",
                reverbRoom = 0.75f,
                fetRelease = 220f,
                tubeWarmth = 0.3f,
                enabledEffects = setOf(V4AEffect.EQ, V4AEffect.CONVOLVER, V4AEffect.REVERB, V4AEffect.TUBE)
            )
        )

        val HeadphoneSurround = V4APreset(
            id = "headphone_surround",
            name = "Headphone Surround",
            isBuiltIn = true,
            effects = V4AEffectsState.Default.copy(
                eqGains = eq(2f, 1.5f, 0.5f, 0f, 0f, 0.5f, 1.5f, 3f, 3f, 2f),
                convolverIR = "Headphone_Surround.irs",
                reverbRoom = 0.28f,
                enabledEffects = setOf(V4AEffect.EQ, V4AEffect.CONVOLVER, V4AEffect.REVERB)
            )
        )

        val WarmTube = V4APreset(
            id = "warm_tube",
            name = "Warm Tube",
            isBuiltIn = true,
            effects = V4AEffectsState.Default.copy(
                eqGains = eq(1.5f, 1f, 0.5f, 0f, 0f, 0.5f, 1f, 1f, 0.5f, 0f),
                fetAttack = 12f,
                fetRelease = 260f,
                fetRatio = 1.6f,
                fetThreshold = -8f,
                tubeWarmth = 0.78f,
                enabledEffects = setOf(V4AEffect.EQ, V4AEffect.FET, V4AEffect.TUBE)
            )
        )

        val StudioReference = V4APreset(
            id = "studio_reference",
            name = "Studio Reference",
            isBuiltIn = true,
            effects = V4AEffectsState.Default.copy(
                eqGains = FloatArray(V4AEffectsState.EQ_BANDS),
                ddcProfile = "Sennheiser_HD650.ddc",
                fetAttack = 10f,
                fetRelease = 180f,
                fetRatio = 1.4f,
                fetThreshold = -6f,
                tubeWarmth = 0.12f,
                enabledEffects = setOf(V4AEffect.EQ, V4AEffect.DDC, V4AEffect.FET)
            )
        )

        val MovieMode = V4APreset(
            id = "movie_mode",
            name = "Movie Mode",
            isBuiltIn = true,
            effects = V4AEffectsState.Default.copy(
                eqGains = eq(3f, 2f, 1f, 0.5f, -0.5f, 0f, 1.5f, 3f, 2.5f, 1f),
                convolverIR = "Headphone_Surround.irs",
                fetAttack = 5f,
                fetRelease = 150f,
                fetRatio = 2.6f,
                fetThreshold = -11f,
                reverbRoom = 0.22f,
                tubeWarmth = 0.38f,
                enabledEffects = setOf(V4AEffect.EQ, V4AEffect.CONVOLVER, V4AEffect.FET, V4AEffect.TUBE, V4AEffect.REVERB)
            )
        )

        val Gaming = V4APreset(
            id = "gaming",
            name = "Gaming",
            isBuiltIn = true,
            effects = V4AEffectsState.Default.copy(
                eqGains = eq(1f, 0.5f, 0f, 0.5f, 1.5f, 2.5f, 3f, 4f, 3f, 1f),
                fetAttack = 2f,
                fetRelease = 80f,
                fetRatio = 2.2f,
                fetThreshold = -10f,
                tubeWarmth = 0.18f,
                enabledEffects = setOf(V4AEffect.EQ, V4AEffect.FET)
            )
        )

        val BuiltIns = listOf(SuperBass, ClearVocal, ConcertHall, HeadphoneSurround, WarmTube, StudioReference, MovieMode, Gaming)

        fun fromEntity(entity: V4APresetEntity): V4APreset = entity.toPreset()
    }
}
