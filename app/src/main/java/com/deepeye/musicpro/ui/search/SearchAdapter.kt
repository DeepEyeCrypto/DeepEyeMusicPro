package com.deepeye.musicpro.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deepeye.musicpro.databinding.ItemSearchResultBinding
import com.deepeye.musicpro.model.SearchResult

class SearchAdapter(
    private val onResultClick: (SearchResult) -> Unit
) : ListAdapter<SearchResult, SearchAdapter.ViewHolder>(Diff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false), onResultClick)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))
    class ViewHolder(
        private val binding: ItemSearchResultBinding,
        private val onResultClick: (SearchResult) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: SearchResult) {
            binding.resultTitle.text = result.track.title
            binding.resultArtist.text = result.track.artist
            binding.resultMeta.text = result.sourceLabel
            binding.root.setOnClickListener { onResultClick(result) }
        }
    }
    object Diff : DiffUtil.ItemCallback<SearchResult>() {
        override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean = oldItem.track.id == newItem.track.id
        override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean = oldItem == newItem
    }
}
