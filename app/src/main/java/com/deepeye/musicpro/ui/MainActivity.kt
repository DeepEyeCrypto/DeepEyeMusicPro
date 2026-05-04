package com.deepeye.musicpro.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.RoundedCornersTransformation
import com.deepeye.musicpro.DeepEyeApp
import com.deepeye.musicpro.R
import com.deepeye.musicpro.databinding.ActivityMainM3Binding
import com.deepeye.musicpro.extractor.LinkParser
import com.deepeye.musicpro.player.PlayerController
import com.deepeye.musicpro.ui.home.HomeFragment
import com.deepeye.musicpro.ui.library.DownloadsFragment
import com.deepeye.musicpro.ui.library.LibraryFragment
import com.deepeye.musicpro.ui.search.SearchFragment
import com.deepeye.musicpro.ui.settings.SettingsFragment
import com.google.android.material.color.DynamicColors
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainM3Binding
    private lateinit var playerController: PlayerController
    private val notificationPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply Material 3 dynamic theme
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            DynamicColors.applyToActivityIfAvailable(this)
        }
        setTheme(R.style.Theme_DeepEyeMusicPro)
        
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        binding = ActivityMainM3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        
        playerController = PlayerController(this)
        playerController.connect()
        
        // Mini Player controls
        binding.bottomPlayer.miniPlayButton.setOnClickListener { playerController.toggle() }
        binding.bottomPlayer.miniPlayerCard.setOnClickListener { 
            show(com.deepeye.musicpro.ui.player.NowPlayingFragment()) 
        }
        binding.bottomPlayer.miniNextButton.setOnClickListener {
            // playerController.next() - assuming this exists or adding it
        }
        
        lifecycleScope.launch {
            playerController.state.collectLatest { state ->
                val track = state.currentTrack
                binding.bottomPlayer.miniTrackTitle.text = track?.title ?: getString(R.string.app_name)
                binding.bottomPlayer.miniArtistName.text = track?.artist ?: "Ready to play"
                binding.bottomPlayer.miniPlayButton.setIconResource(if (state.isPlaying) R.drawable.ic_pause else R.drawable.ic_play)
                
                track?.thumbnailUrl?.let { url ->
                    binding.bottomPlayer.miniAlbumArt.load(url) {
                        crossfade(true)
                        placeholder(R.drawable.bg_artwork_placeholder)
                        error(R.drawable.bg_artwork_placeholder)
                        transformations(RoundedCornersTransformation(12f))
                    }
                } ?: binding.bottomPlayer.miniAlbumArt.setImageResource(R.drawable.bg_artwork_placeholder)
            }
        }
        
        maybeRequestNotificationPermission()
        setupNavigation()
        handleIncomingLink()
        
        if (savedInstanceState == null) {
            show(HomeFragment())
        }
        
        DeepEyeApp.from(this).applicationScope.launch { 
            DeepEyeApp.from(this@MainActivity).appRepository.warmUp() 
        }
    }

    private fun setupNavigation() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> show(HomeFragment())
                R.id.nav_search -> show(SearchFragment())
                R.id.nav_library -> show(LibraryFragment())
                R.id.nav_downloads -> show(DownloadsFragment())
                R.id.nav_settings -> show(SettingsFragment())
            }
            true
        }
    }

    private fun show(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fade_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_down
            )
            .setReorderingAllowed(true)
            .replace(R.id.navHostFragment, fragment)
            
        if (fragment is com.deepeye.musicpro.ui.player.NowPlayingFragment) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    private fun maybeRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= 33 && ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    override fun onDestroy() {
        if (::playerController.isInitialized) playerController.release()
        super.onDestroy()
    }

    private fun handleIncomingLink() {
        val url = LinkParser.parseSharedText(intent) ?: return
        intent.putExtra("deepeye_initial_url", url)
        binding.bottomNav.selectedItemId = R.id.nav_search
    }
}

