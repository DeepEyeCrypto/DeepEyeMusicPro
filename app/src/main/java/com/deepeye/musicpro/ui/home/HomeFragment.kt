package com.deepeye.musicpro.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.deepeye.musicpro.DeepEyeApp
import com.deepeye.musicpro.R
import com.deepeye.musicpro.databinding.ScreenHomeBinding
import com.deepeye.musicpro.dsp.v4a.V4AEngineMode
import com.deepeye.musicpro.model.Track
import com.deepeye.musicpro.ui.v4a.V4ABottomSheetFragment
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var binding: ScreenHomeBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ScreenHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val picks = listOf(
            Track("deepeye-night-drive", "Night Drive Fidelity", "DeepEye Curated", thumbnailUrl = null, webUrl = "https://www.youtube.com/results?search_query=night+drive+music"),
            Track("deepeye-studio-focus", "Studio Focus", "DeepEye Curated", thumbnailUrl = null, webUrl = "https://www.youtube.com/results?search_query=studio+focus+music")
        )
        binding?.homeRecycler?.layoutManager = LinearLayoutManager(requireContext())
        binding?.homeRecycler?.adapter = HomeAdapter(picks)
        binding?.homeV4AButton?.setOnClickListener {
            V4ABottomSheetFragment().show(parentFragmentManager, "deep-v4a")
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                DeepEyeApp.from(requireContext()).v4aEngine.state.collect { state ->
                    binding?.homeV4AStatusDot?.setBackgroundResource(
                        if (state.active) R.drawable.bg_v4a_badge_active else R.drawable.bg_v4a_badge_inactive
                    )
                    binding?.homeV4ASubtitle?.text = when (state.mode) {
                        V4AEngineMode.SYSTEM -> "System V4A hardware driver active"
                        V4AEngineMode.BUNDLED -> "Bundled software V4A engine ready"
                    }
                }
            }
        }
    }

    override fun onDestroyView() { binding = null; super.onDestroyView() }
}
