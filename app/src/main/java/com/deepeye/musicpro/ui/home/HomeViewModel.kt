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
        android.util.Log.d("HomeViewModel", "loadHomeContent: Starting")
        viewModelScope.launch {
            _isLoading.value = true
            val token = YoutubeAuthManager.getAccessToken()
            android.util.Log.d("HomeViewModel", "loadHomeContent: Token exists: ${token != null}")
            _isLoggedIn.value = token != null
            
            if (token != null) {
                YoutubeMusicApi.getMadeForYou(token).onSuccess { 
                    android.util.Log.d("HomeViewModel", "loadHomeContent: MadeForYou success, size: ${it.size}")
                    _madeForYou.value = it 
                }.onFailure {
                    android.util.Log.e("HomeViewModel", "loadHomeContent: MadeForYou failed", it)
                }
                YoutubeMusicApi.getListenAgain(token).onSuccess { _listenAgain.value = it }
            } else {
                android.util.Log.d("HomeViewModel", "loadHomeContent: Using guest mixes")
                // Fallback / Guest Content
                _madeForYou.value = listOf(
                    MusicMix("1", "Your Mix", "Songs you love", "https://i.ytimg.com/vi/0/maxresdefault.jpg", 50),
                    MusicMix("2", "Discover Mix", "New music", "https://i.ytimg.com/vi/1/maxresdefault.jpg", 30),
                    MusicMix("3", "Release Radar", "Latest tracks", "https://i.ytimg.com/vi/2/maxresdefault.jpg", 25)
                )
            }
            
            YoutubeMusicApi.getNewReleases().onSuccess { _newReleases.value = it }
            YoutubeMusicApi.getTrending().onSuccess { _trending.value = it }
            YoutubeMusicApi.getMoodGenres().onSuccess { _moodGenres.value = it }
            
            _isLoading.value = false
            android.util.Log.d("HomeViewModel", "loadHomeContent: Finished")
        }
    }
}
