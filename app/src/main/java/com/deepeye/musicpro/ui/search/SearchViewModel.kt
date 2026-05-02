package com.deepeye.musicpro.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.deepeye.musicpro.DeepEyeApp
import com.deepeye.musicpro.model.SearchResult
import com.deepeye.musicpro.util.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = DeepEyeApp.from(application).searchRepository
    private val _state = MutableStateFlow<UiState<List<SearchResult>>>(UiState.Idle)
    val state: StateFlow<UiState<List<SearchResult>>> = _state.asStateFlow()
    val recentSearches = repo.recentSearches()
    private var searchJob: Job? = null

    fun search(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            if (query.isBlank()) {
                _state.value = UiState.Empty("Start with a sound", "Search songs, artists, or paste a YouTube link.")
                return@launch
            }
            _state.value = UiState.Loading
            repo.search(query)
                .onSuccess { results -> _state.value = if (results.isEmpty()) UiState.Empty("No results", "Try another title, artist, or YouTube link.") else UiState.Success(results) }
                .onFailure { _state.value = UiState.Error(it.message ?: "Search failed") }
        }
    }
}
