package com.deepeye.musicpro.ui.player

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.RoundedCornersTransformation
import com.deepeye.musicpro.R
import com.deepeye.musicpro.player.PlayerViewModel
import com.deepeye.musicpro.player.RepeatMode
import com.deepeye.musicpro.ui.queue.QueueBottomSheetFragment
import com.deepeye.musicpro.ui.v4a.V4ABottomSheetFragment
import com.google.android.material.slider.Slider

class NowPlayingFragment : Fragment() {
    private val playerViewModel: PlayerViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.screen_nowplaying_m3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // UI references via findViewById to bypass binding generation issues
        val artworkImage = view.findViewById<android.widget.ImageView>(R.id.artworkImageM3)
        val trackTitle = view.findViewById<android.widget.TextView>(R.id.trackTitleM3)
        val artistName = view.findViewById<android.widget.TextView>(R.id.artistNameM3)
        val slider = view.findViewById<com.google.android.material.slider.Slider>(R.id.sliderM3)
        val elapsedTime = view.findViewById<android.widget.TextView>(R.id.elapsedTimeM3)
        val totalTime = view.findViewById<android.widget.TextView>(R.id.totalTimeM3)
        val previousButton = view.findViewById<com.google.android.material.button.MaterialButton>(R.id.previousButtonM3)
        val playPauseButton = view.findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.playPauseButtonM3)
        val nextButton = view.findViewById<com.google.android.material.button.MaterialButton>(R.id.nextButtonM3)
        val repeatButton = view.findViewById<com.google.android.material.button.MaterialButton>(R.id.repeatButtonM3)
        val favoriteButton = view.findViewById<com.google.android.material.button.MaterialButton>(R.id.favoriteButtonM3)
        val downloadButton = view.findViewById<com.google.android.material.button.MaterialButton>(R.id.downloadButtonM3)
        val v4aButton = view.findViewById<com.google.android.material.button.MaterialButton>(R.id.v4aButtonM3)
        val queueButton = view.findViewById<com.google.android.material.button.MaterialButton>(R.id.queueButtonM3)
        val connectButton = view.findViewById<com.google.android.material.button.MaterialButton>(R.id.connectButtonM3)
        val visualizerComposeView = view.findViewById<androidx.compose.ui.platform.ComposeView>(R.id.visualizerComposeView)

        val dspManager = (requireActivity().application as com.deepeye.musicpro.DeepEyeApp).dspManager
        val visualizerViewModel = com.deepeye.musicpro.ui.visualizer.VisualizerViewModel(dspManager)

        visualizerComposeView?.apply {
            setViewCompositionStrategy(androidx.compose.ui.platform.ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                androidx.compose.material3.MaterialTheme {
                    com.deepeye.musicpro.ui.visualizer.AudioVisualizer(viewModel = visualizerViewModel)
                }
            }
        }

        previousButton?.setOnClickListener { playerViewModel.previous() }
        playPauseButton?.setOnClickListener { playerViewModel.toggle() }
        nextButton?.setOnClickListener { playerViewModel.next() }
        repeatButton?.setOnClickListener { playerViewModel.toggleRepeat() }
        favoriteButton?.setOnClickListener { 
            playerViewModel.toggleFavorite()
            it.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
        }
        downloadButton?.setOnClickListener { 
            playerViewModel.download()
            android.widget.Toast.makeText(context, "Added to downloads", android.widget.Toast.LENGTH_SHORT).show()
        }
        v4aButton?.setOnClickListener { V4ABottomSheetFragment().show(parentFragmentManager, "deep-v4a") }
        queueButton?.setOnClickListener { QueueBottomSheetFragment().show(parentFragmentManager, "deep-queue") }
        connectButton?.setOnClickListener { com.deepeye.musicpro.ui.routing.DevicePickerBottomSheetFragment().show(parentFragmentManager, "deep-routing") }

        playerViewModel.currentItem.observe(viewLifecycleOwner) { track ->
            trackTitle?.text = track?.title ?: "DeepEyeMusicPro"
            artistName?.text = track?.artist ?: "Ready to play"
            
            val favIcon = if (track?.isFavorite == true) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            favoriteButton?.setIconResource(favIcon)
            
            track?.thumbnailUrl?.let { url ->
                artworkImage?.load(url) {
                    crossfade(true)
                    placeholder(R.drawable.bg_artwork_placeholder)
                    error(R.drawable.bg_artwork_placeholder)
                    transformations(RoundedCornersTransformation(32.dpToPx.toFloat()))
                }
            } ?: artworkImage?.setImageResource(R.drawable.bg_artwork_placeholder)
        }

        playerViewModel.position.observe(viewLifecycleOwner) { pos ->
            elapsedTime?.text = formatMs(pos)
            slider?.let {
                if (!it.isFocused) {
                    it.value = pos.toFloat().coerceIn(0f, it.valueTo)
                }
            }
        }

        playerViewModel.duration.observe(viewLifecycleOwner) { dur ->
            totalTime?.text = formatMs(dur)
            slider?.valueTo = if (dur > 0) dur.toFloat() else 1f
        }

        playerViewModel.isPlaying.observe(viewLifecycleOwner) { playing ->
            playPauseButton?.setImageResource(
                if (playing) R.drawable.ic_pause else R.drawable.ic_play
            )
        }

        playerViewModel.repeatMode.observe(viewLifecycleOwner) { mode ->
            val icon = when (mode) {
                RepeatMode.ONE -> R.drawable.ic_repeat_one
                else -> R.drawable.ic_repeat
            }
            repeatButton?.setIconResource(icon)
            repeatButton?.alpha = if (mode == RepeatMode.OFF) 0.5f else 1.0f
        }

        playerViewModel.dspPreset.observe(viewLifecycleOwner) { preset ->
            val isActive = preset != null && preset != "off" && preset != "flat"
            v4aButton?.alpha = if (isActive) 1.0f else 0.6f
        }

        slider?.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {}
            override fun onStopTrackingTouch(slider: Slider) {
                playerViewModel.seekTo(slider.value.toLong())
            }
        })
        
        slider?.addOnChangeListener { s: Slider, value: Float, fromUser: Boolean ->
            if (fromUser) {
                elapsedTime?.text = formatMs(value.toLong())
            }
        }
    }

    private fun formatMs(ms: Long): String {
        if (ms <= 0) return "0:00"
        val sec = ms / 1000
        return "%d:%02d".format(sec / 60, sec % 60)
    }

    private val Int.dpToPx: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

}
