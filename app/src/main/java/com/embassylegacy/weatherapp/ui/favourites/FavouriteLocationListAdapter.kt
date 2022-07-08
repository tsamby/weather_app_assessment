package com.embassylegacy.weatherapp.ui.favourites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.embassylegacy.weatherapp.R
import com.embassylegacy.weatherapp.model.FavouriteLocation
import com.embassylegacy.weatherapp.ui.favourites.FavouriteLocationListAdapter.WordViewHolder

class FavouriteLocationListAdapter : ListAdapter<FavouriteLocation, WordViewHolder>(WORDS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.name)
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            wordItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.favourite_location_item, parent, false)
                return WordViewHolder(view)
            }
        }
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<FavouriteLocation>() {
            override fun areItemsTheSame(oldItem: FavouriteLocation, newItem: FavouriteLocation): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: FavouriteLocation, newItem: FavouriteLocation): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}