package com.deepeye.musicpro.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.deepeye.musicpro.BuildConfig
import com.deepeye.musicpro.databinding.ScreenAboutBinding

class AboutFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ScreenAboutBinding.inflate(inflater, container, false).apply {
            aboutTitle.text = "DeepEyeMusicPro"
            aboutBody.text = "Version ${BuildConfig.VERSION_NAME}\nMedia3 playback • Native DSP • Offline library"
        }.root
    }
}
