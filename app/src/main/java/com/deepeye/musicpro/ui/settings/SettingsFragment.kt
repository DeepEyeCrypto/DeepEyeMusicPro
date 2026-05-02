package com.deepeye.musicpro.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.deepeye.musicpro.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}
