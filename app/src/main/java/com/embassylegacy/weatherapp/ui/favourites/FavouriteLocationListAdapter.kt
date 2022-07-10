package com.embassylegacy.weatherapp.ui.favourites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.embassylegacy.weatherapp.R
import com.embassylegacy.weatherapp.model.FavouriteLocation
import com.embassylegacy.weatherapp.ui.favourites.FavouriteLocationListAdapter.FavouriteLocationViewHolder

class FavouriteLocationListAdapter(private val onItemClicked: (FavouriteLocation) -> Unit
) : ListAdapter<FavouriteLocation, FavouriteLocationViewHolder>(LOCATIONS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteLocationViewHolder {
        return FavouriteLocationViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FavouriteLocationViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    class FavouriteLocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val favLocationItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(favouriteLocation: FavouriteLocation, onItemClicked: (FavouriteLocation) -> Unit) {
            favLocationItemView.text = favouriteLocation.name

            itemView.setOnClickListener {
                onItemClicked(favouriteLocation)
            }
        }

        companion object {
            fun create(parent: ViewGroup): FavouriteLocationViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.favourite_location_item, parent, false)
                return FavouriteLocationViewHolder(view)
            }
        }
    }

    companion object {
        private val LOCATIONS_COMPARATOR = object : DiffUtil.ItemCallback<FavouriteLocation>() {
            override fun areItemsTheSame(oldItem: FavouriteLocation, newItem: FavouriteLocation): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: FavouriteLocation, newItem: FavouriteLocation): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}