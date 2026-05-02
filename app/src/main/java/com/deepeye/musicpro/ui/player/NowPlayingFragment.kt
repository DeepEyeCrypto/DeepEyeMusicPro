package com.deepeye.musicpro.ui.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.deepeye.musicpro.R
import com.deepeye.musicpro.databinding.ScreenNowPlayingBinding
import com.deepeye.musicpro.ui.queue.QueueBottomSheetFragment
import com.deepeye.musicpro.ui.v4a.V4ABottomSheetFragment
import com.deepeye.musicpro.util.TimeUtils
import kotlinx.coroutines.launch
import java.util.Locale

class NowPlayingFragment : Fragment() {
    private var binding: ScreenNowPlayingBinding? = null
    private val viewModel: NowPlayingViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ScreenNowPlayingBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.playerPlayPause?.setOnClickListener { viewModel.toggle() }
        binding?.playerNext?.setOnClickListener { viewModel.next() }
        binding?.playerPrev?.setOnClickListener { viewModel.previous() }
        binding?.playerDsp?.setOnClickListener { V4ABottomSheetFragment().show(parentFragmentManager, "deep-v4a") }
        binding?.playerQueue?.setOnClickListener { QueueBottomSheetFragment().show(parentFragmentManager, "deep-queue") }
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding?.playerTitle?.text = state.currentTrack?.title ?: "DeepEyeMusicPro"
                binding?.playerArtist?.text = state.currentTrack?.artist ?: "Ready to play"
                binding?.playerElapsed?.text = TimeUtils.formatDuration(state.positionMs)
                binding?.playerRemaining?.text = TimeUtils.formatDuration((state.durationMs - state.positionMs).coerceAtLeast(0L))
                binding?.playerProgress?.max = state.durationMs.toInt().coerceAtLeast(1)
                binding?.playerProgress?.progress = state.positionMs.toInt().coerceAtLeast(0)
                binding?.playerPlayPause?.setImageResource(if (state.isPlaying) R.drawable.ic_pause else R.drawable.ic_play)
            }
        }
        lifecycleScope.launch {
            viewModel.v4aState.collect { state ->
                val mode = state.mode.name.lowercase(Locale.US).replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.US) else it.toString() }
                binding?.playerDsp?.text = if (state.active) "V4A • $mode" else "V4A"
            }
        }
    }

    override fun onDestroyView() { binding = null; super.onDestroyView() }
}
