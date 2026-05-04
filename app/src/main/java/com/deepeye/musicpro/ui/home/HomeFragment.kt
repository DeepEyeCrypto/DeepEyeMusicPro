package com.deepeye.musicpro.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.RoundedCornersTransformation
import com.deepeye.musicpro.R
import com.deepeye.musicpro.api.MusicCategory
import com.deepeye.musicpro.auth.YoutubeAuthManager
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()

    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        YoutubeAuthManager.handleSignInResult(
            data = result.data,
            onSuccess = {
                Toast.makeText(context, "Signed in successfully!", Toast.LENGTH_SHORT).show()
                viewModel.loadHomeContent()
            },
            onError = { e ->
                Toast.makeText(context, "Sign-in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.screen_home_m3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProfile(view)
        setupMoodChips(view)
        setupSections(view)
        observeData(view)
        viewModel.loadHomeContent()
    }

    private fun setupProfile(view: View) {
        val profileImage = view.findViewById<ImageView>(R.id.profileImage)
        val profileText = view.findViewById<TextView>(R.id.profileText)

        profileImage?.setOnClickListener {
            if (YoutubeAuthManager.isSignedIn()) {
                YoutubeAuthManager.signOut()
                viewModel.loadHomeContent()
                Toast.makeText(context, "Signed out", Toast.LENGTH_SHORT).show()
            } else {
                YoutubeAuthManager.signIn(requireActivity(), signInLauncher)
            }
        }
        
        lifecycleScope.launch {
            viewModel.isLoggedIn.collect { loggedIn ->
                if (loggedIn) {
                    profileText?.text = YoutubeAuthManager.getUserName() ?: "Profile"
                    val photoUrl = YoutubeAuthManager.getUserPhoto()
                    if (photoUrl != null) {
                        profileImage?.load(photoUrl) {
                            crossfade(true)
                            transformations(RoundedCornersTransformation(100f))
                        }
                    }
                } else {
                    profileText?.text = "Sign In"
                    profileImage?.setImageResource(R.drawable.ic_account)
                }
            }
        }
    }

    private fun setupMoodChips(view: View) {
        val moodChipGroup = view.findViewById<com.google.android.material.chip.ChipGroup>(R.id.moodChipGroup)
        val moods = listOf("🔥 Quick Picks", "🎵 Your Mix", "💿 Albums", "🎸 Rock", "🎹 Jazz", "Pop", "Electronic")
        moodChipGroup?.removeAllViews()
        moods.forEach { mood ->
            val chip = Chip(androidx.appcompat.view.ContextThemeWrapper(requireContext(), com.google.android.material.R.style.Widget_Material3_Chip_Filter)).apply {
                text = mood
                isCheckable = true
            }
            moodChipGroup?.addView(chip)
        }
    }

    private fun setupSections(view: View) {
        view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.madeForYouRecycler)?.layoutManager = 
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.listenAgainRecycler)?.layoutManager = 
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.newReleasesRecycler)?.layoutManager = 
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.trendingRecycler)?.layoutManager = 
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun observeData(view: View) {
        val madeForYouRecycler = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.madeForYouRecycler)
        val listenAgainRecycler = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.listenAgainRecycler)
        val newReleasesRecycler = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.newReleasesRecycler)
        val trendingRecycler = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.trendingRecycler)

        val madeForYouSection = view.findViewById<View>(R.id.madeForYouSection)
        val listenAgainSection = view.findViewById<View>(R.id.listenAgainSection)
        val newReleasesSection = view.findViewById<View>(R.id.newReleasesSection)
        val trendingSection = view.findViewById<View>(R.id.trendingSection)

        lifecycleScope.launch {
            viewModel.madeForYou.collect { mixes ->
                madeForYouRecycler?.adapter = HomeSectionAdapter(
                    items = mixes,
                    layoutRes = R.layout.item_mix_card_m3,
                    bind = { v, mix ->
                        v.findViewById<TextView>(R.id.cardTitle).text = mix.title
                        v.findViewById<TextView>(R.id.cardSubtitle).text = mix.description
                        v.findViewById<ImageView>(R.id.cardThumbnail).load(mix.thumbnailUrl) {
                            placeholder(R.drawable.bg_artwork_placeholder)
                            crossfade(true)
                        }
                    },
                    onClick = { /* Navigate to mix */ }
                )
                madeForYouSection?.visibility = if (mixes.isNotEmpty()) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.listenAgain.collect { tracks ->
                listenAgainRecycler?.adapter = HomeSectionAdapter(
                    items = tracks,
                    layoutRes = R.layout.item_track_card_m3,
                    bind = { v, track ->
                        v.findViewById<TextView>(R.id.trackTitle).text = track.title
                        v.findViewById<TextView>(R.id.trackArtist).text = track.artist
                        v.findViewById<ImageView>(R.id.trackThumbnail).load(track.thumbnailUrl) {
                            placeholder(R.drawable.bg_artwork_placeholder)
                            crossfade(true)
                        }
                    },
                    onClick = { /* Play track */ }
                )
                listenAgainSection?.visibility = if (tracks.isNotEmpty()) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.trending.collect { tracks ->
                trendingRecycler?.adapter = HomeSectionAdapter(
                    items = tracks,
                    layoutRes = R.layout.item_track_card_m3,
                    bind = { v, track ->
                        v.findViewById<TextView>(R.id.trackTitle).text = track.title
                        v.findViewById<TextView>(R.id.trackArtist).text = track.artist
                        v.findViewById<ImageView>(R.id.trackThumbnail).load(track.thumbnailUrl) {
                            placeholder(R.drawable.bg_artwork_placeholder)
                            crossfade(true)
                        }
                    },
                    onClick = { /* Play track */ }
                )
                trendingSection?.visibility = if (tracks.isNotEmpty()) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.newReleases.collect { albums ->
                newReleasesRecycler?.adapter = HomeSectionAdapter(
                    items = albums,
                    layoutRes = R.layout.item_mix_card_m3,
                    bind = { v, album ->
                        v.findViewById<TextView>(R.id.cardTitle).text = album.title
                        v.findViewById<TextView>(R.id.cardSubtitle).text = album.artist
                        v.findViewById<ImageView>(R.id.cardThumbnail).load(album.thumbnailUrl) {
                            placeholder(R.drawable.bg_artwork_placeholder)
                            crossfade(true)
                        }
                    },
                    onClick = { /* Navigate to album */ }
                )
                newReleasesSection?.visibility = if (albums.isNotEmpty()) View.VISIBLE else View.GONE
            }
        }
    }
}

