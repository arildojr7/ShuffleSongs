package com.arildojr.shufflesongs.songs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arildojr.data.songs.model.Song
import com.arildojr.shufflesongs.databinding.ItemSongListBinding

class SongsAdapter(
    private var items: List<Song>,
    private val openEventDetails: (Song) -> Unit
) : RecyclerView.Adapter<SongsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSongListBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position], this)

    fun setData(data: List<Song>?) {
        this.items = data.orEmpty()
        notifyDataSetChanged()
    }

    fun onItemClicked(item: Song) {
        openEventDetails(item)
    }

    class ViewHolder(private val binding: ItemSongListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song, adapter: SongsAdapter) {
            binding.song = song
            binding.executePendingBindings()
        }
    }
}