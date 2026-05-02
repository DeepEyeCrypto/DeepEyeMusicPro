package com.deepeye.musicpro.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.deepeye.musicpro.databinding.ScreenLibraryBinding
import com.google.android.material.tabs.TabLayoutMediator

class LibraryFragment : Fragment() {
    private var binding: ScreenLibraryBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ScreenLibraryBinding.inflate(inflater, container, false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.libraryPager?.adapter = LibraryPagerAdapter(this)
        TabLayoutMediator(binding!!.libraryTabs, binding!!.libraryPager) { tab, position ->
            tab.text = listOf("Favorites", "Downloads", "Recent")[position]
        }.attach()
    }
    override fun onDestroyView() { binding = null; super.onDestroyView() }
}
