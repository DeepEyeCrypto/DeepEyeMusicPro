package com.deepeye.musicpro.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HomeSectionAdapter<T>(
    private val items: List<T>,
    private val layoutRes: Int,
    private val bind: (View, T) -> Unit,
    private val onClick: (T) -> Unit
) : RecyclerView.Adapter<HomeSectionAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(layoutRes, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        bind(holder.view, item)
        holder.view.setOnClickListener { onClick(item) }
    }

    override fun getItemCount() = items.size
}
