package com.deepeye.musicpro.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepeye.musicpro.api.*
import com.deepeye.musicpro.auth.YoutubeAuthManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _madeForYou = MutableStateFlow<List<MusicMix>>(emptyList())
    val madeForYou: StateFlow<List<MusicMix>> = _madeForYou.asStateFlow()
    
    private val _listenAgain = MutableStateFlow<List<MusicTrack>>(emptyList())
    val listenAgain: StateFlow<List<MusicTrack>> = _listenAgain.asStateFlow()
    
    private val _newReleases = MutableStateFlow<List<MusicAlbum>>(emptyList())
    val newReleases: StateFlow<List<MusicAlbum>> = _newReleases.asStateFlow()
    
    private val _trending = MutableStateFlow<List<MusicTrack>>(emptyList())
    val trending: StateFlow<List<MusicTrack>> = _trending.asStateFlow()
    
    private val _moodGenres = MutableStateFlow<List<MusicCategory>>(emptyList())
    val moodGenres: StateFlow<List<MusicCategory>> = _moodGenres.asStateFlow()
    
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()
    
    fun loadHomeContent() {
        viewModelScope.launch {
            _isLoading.value = true
            val token = YoutubeAuthManager.getAccessToken()
            _isLoggedIn.value = token != null
            
            if (token != null) {
                YoutubeMusicApi.getMadeForYou(token).onSuccess { _madeForYou.value = it }
                YoutubeMusicApi.getListenAgain(token).onSuccess { _listenAgain.value = it }
            } else {
                // Fallback / Guest Content
                _madeForYou.value = listOf(
                    MusicMix("1", "Your Mix", "Songs you love", "", 50),
                    MusicMix("2", "Discover Mix", "New music", "", 30),
                    MusicMix("3", "Release Radar", "Latest tracks", "", 25)
                )
            }
            
            YoutubeMusicApi.getNewReleases().onSuccess { _newReleases.value = it }
            YoutubeMusicApi.getTrending().onSuccess { _trending.value = it }
            YoutubeMusicApi.getMoodGenres().onSuccess { _moodGenres.value = it }
            
            _isLoading.value = false
        }
    }
}
