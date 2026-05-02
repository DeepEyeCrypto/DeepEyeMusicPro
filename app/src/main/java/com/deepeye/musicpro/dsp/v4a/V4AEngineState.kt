package com.deepeye.musicpro.dsp.v4a

data class V4AEngineState(
    val initialized: Boolean = false,
    val active: Boolean = true,
    val mode: V4AEngineMode = V4AEngineMode.BUNDLED,
    val systemDetected: Boolean = false,
    val audioSessionId: Int = -1,
    val currentPresetName: String = V4APreset.SuperBass.name,
    val effects: V4AEffectsState = V4APreset.SuperBass.effects,
    val message: String = "Bundled V4A engine — software processing"
) {
    val isBundledProcessorActive: Boolean
        get() = active && mode == V4AEngineMode.BUNDLED && effects.masterEnabled
}
