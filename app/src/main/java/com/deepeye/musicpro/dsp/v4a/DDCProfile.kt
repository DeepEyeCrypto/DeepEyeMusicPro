package com.deepeye.musicpro.dsp.v4a

data class DDCProfile(
    val id: String,
    val name: String,
    val assetName: String
) {
    companion object {
        val BuiltIns = listOf(
            DDCProfile("sony_wh_1000xm4", "Sony WH-1000XM4", "Sony_WH-1000XM4.ddc"),
            DDCProfile("sennheiser_hd650", "Sennheiser HD650", "Sennheiser_HD650.ddc"),
            DDCProfile("apple_airpods_pro", "Apple AirPods Pro", "Apple_AirPods_Pro.ddc"),
            DDCProfile("generic_bass_boost", "Generic Bass Boost", "Generic_BassBoost.ddc"),
            DDCProfile("vocal_enhance", "Vocal Enhance", "Vocal_Enhance.ddc")
        )
    }
}
