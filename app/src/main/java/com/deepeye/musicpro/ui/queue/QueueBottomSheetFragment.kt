package com.deepeye.musicpro.ui.queue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deepeye.musicpro.R
import com.deepeye.musicpro.databinding.ItemQueueTrackBinding
import com.deepeye.musicpro.databinding.ScreenQueueSheetBinding
import com.deepeye.musicpro.model.Track
import com.deepeye.musicpro.player.PlayerController
import com.deepeye.musicpro.util.TimeUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class QueueBottomSheetFragment : BottomSheetDialogFragment() {
    private var binding: ScreenQueueSheetBinding? = null
    private lateinit var playerController: PlayerController
    private val adapter = QueueAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ScreenQueueSheetBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        playerController = PlayerController(requireContext().applicationContext)
        binding?.queueRecycler?.layoutManager = LinearLayoutManager(requireContext())
        binding?.queueRecycler?.adapter = adapter
        playerController.connect()
        
        val app = com.deepeye.musicpro.DeepEyeApp.from(requireContext())
        val settingsRepo = app.settingsRepository
        
        lifecycleScope.launch {
            settingsRepo.settings.collect { settings ->
                binding?.radioToggle?.isChecked = settings.autoRadioEnabled
                binding?.radioIndicatorContainer?.visibility = if (settings.autoRadioEnabled) View.VISIBLE else View.VISIBLE // Always show for toggle
                binding?.radioStatusText?.text = if (settings.autoRadioEnabled) "Auto-playing related songs" else "Radio mode disabled"
            }
        }
        
        binding?.radioToggle?.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                settingsRepo.setAutoRadioEnabled(isChecked)
            }
        }

        lifecycleScope.launch {
            playerController.state.collect { state ->
                adapter.currentIndex = state.currentIndex
                adapter.submitList(state.queue)
                binding?.queueSummary?.text = when (state.queue.size) {
                    0 -> "No tracks queued"
                    1 -> "1 track queued"
                    else -> "${state.queue.size} tracks queued"
                }
            }
        }
    }

    override fun onDestroyView() {
        playerController.release()
        binding = null
        super.onDestroyView()
    }

    private class QueueAdapter : ListAdapter<Track, QueueAdapter.QueueViewHolder>(DiffCallback) {
        var currentIndex: Int = -1
            set(value) {
                val previous = field
                field = value
                if (previous >= 0) notifyItemChanged(previous)
                if (value >= 0) notifyItemChanged(value)
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueueViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return QueueViewHolder(ItemQueueTrackBinding.inflate(inflater, parent, false))
        }

        override fun onBindViewHolder(holder: QueueViewHolder, position: Int) {
            holder.bind(getItem(position), position, position == currentIndex)
        }

        class QueueViewHolder(private val binding: ItemQueueTrackBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(track: Track, index: Int, isCurrent: Boolean) {
                binding.queueItemRoot.setBackgroundResource(if (isCurrent) R.drawable.bg_surface_card_selected else R.drawable.bg_surface_card)
                binding.queueItemIndex.text = (index + 1).toString()
                binding.queueItemTitle.text = track.title
                binding.queueItemArtist.text = track.artist
                binding.queueItemDuration.text = TimeUtils.formatDuration(track.durationMs)
            }
        }

        private object DiffCallback : DiffUtil.ItemCallback<Track>() {
            override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean = oldItem == newItem
        }
    }
}
