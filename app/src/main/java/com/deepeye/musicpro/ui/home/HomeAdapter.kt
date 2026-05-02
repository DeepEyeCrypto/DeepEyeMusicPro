package com.deepeye.musicpro.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deepeye.musicpro.databinding.ItemQuickActionBinding
import com.deepeye.musicpro.model.Track

class HomeAdapter(
    private val tracks: List<Track>
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private val rows = tracks.map { Row.TrackRow(it) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemQuickActionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = rows.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(rows[position])

    class ViewHolder(private val binding: ItemQuickActionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(row: Row.TrackRow) {
            binding.quickTitle.text = row.track.title
            binding.quickSubtitle.text = row.track.artist
        }
    }

    sealed class Row { data class TrackRow(val track: Track): Row() }
}
