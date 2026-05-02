package com.deepeye.musicpro.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.deepeye.musicpro.databinding.LayoutEmptyStateBinding

class DownloadsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return LayoutEmptyStateBinding.inflate(inflater, container, false).apply {
            emptyTitle.text = "Offline library"
            emptyBody.text = "Downloaded tracks are stored safely and shared through FileProvider."
        }.root
    }
}
