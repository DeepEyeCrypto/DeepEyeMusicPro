package com.deepeye.musicpro.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.deepeye.musicpro.databinding.LayoutEmptyStateBinding

class FavoritesFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return LayoutEmptyStateBinding.inflate(inflater, container, false).apply {
            emptyTitle.text = "No favorites yet"
            emptyBody.text = "Favorite tracks appear here with quick queue and download actions."
        }.root
    }
}
