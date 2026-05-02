package com.deepeye.musicpro.player

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.deepeye.musicpro.DeepEyeApp

class PlayerViewModel(application: Application) : AndroidViewModel(application) {
    private val controller = PlayerController(application)
    val state = controller.state
    val v4aState = DeepEyeApp.from(application).v4aEngine.state

    init { controller.connect() }

    fun toggle() = controller.toggle()
    fun next() = controller.next()
    fun previous() = controller.previous()
    fun seekTo(positionMs: Long) = controller.seekTo(positionMs)

    override fun onCleared() {
        controller.release()
        super.onCleared()
    }
}
