package com.deepeye.musicpro.dsp.v4a

data class V4AEffectsState(
    val eqGains: FloatArray,
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
    val enabledEffects: Set<V4AEffect>
) {
    fun isEnabled(effect: V4AEffect): Boolean = enabledEffects.contains(effect)

    fun withEffect(effect: V4AEffect, enabled: Boolean): V4AEffectsState {
        val updated = if (enabled) enabledEffects + effect else enabledEffects - effect
        return copy(enabledEffects = updated)
    }

    fun normalizedEqGains(): FloatArray {
        if (eqGains.size == EQ_BANDS) return eqGains.copyOf()
        return FloatArray(EQ_BANDS) { index -> eqGains.getOrNull(index) ?: 0f }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as V4AEffectsState
        return eqGains.contentEquals(other.eqGains) &&
            convolverIR == other.convolverIR &&
            ddcProfile == other.ddcProfile &&
            fetAttack == other.fetAttack &&
            fetRelease == other.fetRelease &&
            fetRatio == other.fetRatio &&
            fetThreshold == other.fetThreshold &&
            fetKnee == other.fetKnee &&
            tubeWarmth == other.tubeWarmth &&
            reverbRoom == other.reverbRoom &&
            masterEnabled == other.masterEnabled &&
            enabledEffects == other.enabledEffects
    }

    override fun hashCode(): Int {
        var result = eqGains.contentHashCode()
        result = 31 * result + (convolverIR?.hashCode() ?: 0)
        result = 31 * result + (ddcProfile?.hashCode() ?: 0)
        result = 31 * result + fetAttack.hashCode()
        result = 31 * result + fetRelease.hashCode()
        result = 31 * result + fetRatio.hashCode()
        result = 31 * result + fetThreshold.hashCode()
        result = 31 * result + fetKnee.hashCode()
        result = 31 * result + tubeWarmth.hashCode()
        result = 31 * result + reverbRoom.hashCode()
        result = 31 * result + masterEnabled.hashCode()
        result = 31 * result + enabledEffects.hashCode()
        return result
    }

    companion object {
        const val EQ_BANDS = 10

        val Default = V4AEffectsState(
            eqGains = FloatArray(EQ_BANDS),
            convolverIR = null,
            ddcProfile = null,
            fetAttack = 6f,
            fetRelease = 120f,
            fetRatio = 2.2f,
            fetThreshold = -10f,
            fetKnee = 6f,
            tubeWarmth = 0.25f,
            reverbRoom = 0.18f,
            masterEnabled = true,
            enabledEffects = setOf(V4AEffect.EQ, V4AEffect.FET, V4AEffect.TUBE)
        )
    }
}
