package com.deepeye.musicpro.util

sealed class UiState<out T> {
    data object Idle : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Empty(val title: String, val message: String) : UiState<Nothing>()
    data class Error(val message: String, val retryable: Boolean = true) : UiState<Nothing>()
}
