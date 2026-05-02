package com.deepeye.musicpro.ui.player

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.deepeye.musicpro.DeepEyeApp
import com.deepeye.musicpro.player.PlayerController

class NowPlayingViewModel(application: Application) : AndroidViewModel(application) {
    val controller = PlayerController(application)
    val state = controller.state
    val v4aState = DeepEyeApp.from(application).v4aEngine.state

    init { controller.connect() }
    fun toggle() = controller.toggle()
    fun next() = controller.next()
    fun previous() = controller.previous()
    fun seekTo(positionMs: Long) = controller.seekTo(positionMs)
    override fun onCleared() { controller.release() }
}
