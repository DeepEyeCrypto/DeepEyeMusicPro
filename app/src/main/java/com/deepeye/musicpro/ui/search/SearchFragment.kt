package com.deepeye.musicpro.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.deepeye.musicpro.DeepEyeApp
import com.deepeye.musicpro.databinding.ScreenSearchM3Binding
import com.deepeye.musicpro.model.SearchResult
import com.deepeye.musicpro.player.PlayerController
import com.deepeye.musicpro.util.UiState
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private var _binding: ScreenSearchM3Binding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private val adapter = SearchAdapter(::playResult)
    private lateinit var playerController: PlayerController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ScreenSearchM3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        playerController = PlayerController(requireContext().applicationContext)
        playerController.connect()
        
        binding.searchResultsRecyclerM3.layoutManager = LinearLayoutManager(requireContext())
        binding.searchResultsRecyclerM3.adapter = adapter
        binding.searchInputM3.doAfterTextChanged { viewModel.search(it?.toString().orEmpty()) }
        
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.searchStatusM3.text = when (state) {
                    UiState.Idle -> "Search YouTube Music or paste a link"
                    UiState.Loading -> "Searching…"
                    is UiState.Empty -> state.message
                    is UiState.Error -> state.message
                    is UiState.Success -> "${state.data.size} results"
                }
                adapter.submitList((state as? UiState.Success)?.data.orEmpty())
            }
        }
    }

    private fun playResult(result: SearchResult) {
        val app = DeepEyeApp.from(requireContext())
        val input = result.track.playableUri ?: result.track.webUrl ?: result.track.title
        binding.searchStatusM3.text = "Resolving audio stream…"
        viewLifecycleOwner.lifecycleScope.launch {
            app.searchRepository.extract(input)
                .onSuccess { extraction ->
                    playerController.enqueue(extraction.track, playIfEmpty = true)
                    binding.searchStatusM3.text = "Queued ${extraction.track.title}"
                }
                .onFailure { error ->
                    binding.searchStatusM3.text = error.message ?: "Unable to resolve stream"
                }
        }
    }

    override fun onDestroyView() {
        if (::playerController.isInitialized) playerController.release()
        _binding = null
        super.onDestroyView()
    }
}

