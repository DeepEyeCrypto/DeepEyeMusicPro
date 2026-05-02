package com.deepeye.musicpro.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.deepeye.musicpro.player.PlayerController
import com.deepeye.musicpro.DeepEyeApp
import com.deepeye.musicpro.R
import com.deepeye.musicpro.databinding.ActivityMainBinding
import com.deepeye.musicpro.extractor.LinkParser
import com.deepeye.musicpro.ui.home.HomeFragment
import com.deepeye.musicpro.ui.library.DownloadsFragment
import com.deepeye.musicpro.ui.library.LibraryFragment
import com.deepeye.musicpro.ui.search.SearchFragment
import com.deepeye.musicpro.ui.settings.SettingsFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var playerController: PlayerController
    private val notificationPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_DeepEyeMusicPro)
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playerController = PlayerController(this)
        playerController.connect()
        binding.miniPlayer.miniPlayPause.setOnClickListener { playerController.toggle() }
        binding.miniPlayer.miniPlayerCard.setOnClickListener { show(com.deepeye.musicpro.ui.player.NowPlayingFragment()) }
        lifecycleScope.launch {
            playerController.state.collectLatest { state ->
                binding.miniPlayer.miniTitle.text = state.currentTrack?.title ?: getString(R.string.app_name)
                binding.miniPlayer.miniSubtitle.text = state.currentTrack?.artist ?: getString(R.string.app_tagline)
                binding.miniPlayer.miniPlayPause.setImageResource(if (state.isPlaying) R.drawable.ic_pause else R.drawable.ic_play)
            }
        }
        maybeRequestNotificationPermission()
        setupNavigation()
        handleIncomingLink()
        if (savedInstanceState == null) show(HomeFragment())
        DeepEyeApp.from(this).applicationScope.launch { DeepEyeApp.from(this@MainActivity).appRepository.warmUp() }
    }

    private fun setupNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
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
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragmentContainer, fragment)
            .commit()
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
        binding.bottomNavigation.selectedItemId = R.id.nav_search
    }
}
