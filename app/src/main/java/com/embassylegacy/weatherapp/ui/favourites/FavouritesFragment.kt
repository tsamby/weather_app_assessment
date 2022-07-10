package com.embassylegacy.weatherapp.ui.favourites



import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.embassylegacy.weatherapp.databinding.FragmentFavouritesBinding
import com.embassylegacy.weatherapp.model.FavouriteLocation
import com.embassylegacy.weatherapp.ui.home.WeatherAdapter
import com.embassylegacy.weatherapp.ui.shared.SharedViewModel
import com.embassylegacy.weatherapp.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import android.content.DialogInterface

import com.embassylegacy.weatherapp.ui.MainActivity
import com.embassylegacy.weatherapp.utils.showDialog

import com.google.android.material.dialog.MaterialAlertDialogBuilder





@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private val sharedViewModel: SharedViewModel  by viewModels()
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    var adapter: WeatherAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = FavouriteLocationListAdapter(this::onItemClicked)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(context)

        sharedViewModel.allFavouriteLocations.observe(viewLifecycleOwner) { favouriteLocations ->
            // Update the cached copy of the favourite locations in the adapter.
            favouriteLocations.let { adapter.submitList(it) }
        }

        setHasOptionsMenu(true)

        return root
    }

    private fun onItemClicked(favouriteLocation: FavouriteLocation) {
        val message=
            "Address: ${favouriteLocation.address} \n"+
            "PhoneNumber: ${favouriteLocation.phoneNumber} \n"+
            "Is open: ${favouriteLocation.isOpen} \n" +
            "Rating: ${favouriteLocation.rating} \n" +
            "User ratings: ${favouriteLocation.userRating}"
        showDialog(message)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

}