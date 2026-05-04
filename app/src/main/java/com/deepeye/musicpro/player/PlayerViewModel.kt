package com.deepeye.musicpro.player

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.deepeye.musicpro.DeepEyeApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PlayerViewModel(application: Application) : AndroidViewModel(application) {
    private val controller = PlayerController(application)
    val state = controller.state
    
    val currentItem = state.map { it.currentTrack }.asLiveData()
    val isPlaying = state.map { it.isPlaying }.asLiveData()
    val duration = state.map { it.durationMs }.asLiveData()
    val repeatMode = state.map { it.repeatMode }.asLiveData()
    
    private val _position = MutableLiveData<Long>(0L)
    val position: LiveData<Long> = _position
    
    val v4aState = DeepEyeApp.from(application).v4aEngine.state
    val dspPreset = v4aState.map { if (it.active) it.currentPresetName else null }.asLiveData<String?>()

    private val handler = Handler(Looper.getMainLooper())
    private val positionRunnable = object : Runnable {
        override fun run() {
            val currentController = controller.getController()
            if (currentController != null) {
                _position.postValue(currentController.currentPosition.coerceAtLeast(0))
            }
            handler.postDelayed(this, 500)
        }
    }

    init {
        controller.connect()
        handler.post(positionRunnable)
    }

    fun toggle() = controller.toggle()
    fun next() = controller.next()
    fun previous() = controller.previous()
    fun seekTo(positionMs: Long) = controller.seekTo(positionMs)
    
    fun toggleRepeat() {
        val current = controller.state.value.repeatMode
        val nextMode = when (current) {
            RepeatMode.OFF -> RepeatMode.ALL
            RepeatMode.ALL -> RepeatMode.ONE
            RepeatMode.ONE -> RepeatMode.OFF
        }
        controller.setRepeatMode(nextMode)
    }
    
    fun toggleFavorite() {
        val track = currentItem.value ?: return
        viewModelScope.launch {
            DeepEyeApp.from(getApplication()).playerRepository.toggleFavorite(track)
        }
    }
    
    fun download() {
        val track = currentItem.value ?: return
        viewModelScope.launch(Dispatchers.IO) {
            DeepEyeApp.from(getApplication()).downloadRepository.download(track.id)
        }
    }

    override fun onCleared() {
        handler.removeCallbacks(positionRunnable)
        controller.release()
        super.onCleared()
    }
}
