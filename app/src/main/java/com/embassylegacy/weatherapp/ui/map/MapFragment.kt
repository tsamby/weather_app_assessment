package com.embassylegacy.weatherapp.ui.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.embassylegacy.weatherapp.R
import com.embassylegacy.weatherapp.databinding.FragmentMapBinding
import com.embassylegacy.weatherapp.model.FavouriteLocation
import com.embassylegacy.weatherapp.ui.favourites.FavouriteLocationListAdapter
import com.embassylegacy.weatherapp.ui.shared.SharedViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback{

    private val sharedViewModel: SharedViewModel by viewModels()
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private var mMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize map fragment
        val supportMapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?
        supportMapFragment!!.getMapAsync(this);

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        createBookmarkMarkerObserver()
    }

    private fun createBookmarkMarkerObserver() {
        sharedViewModel.allFavouriteLocations.observe(viewLifecycleOwner) { favouriteLocations ->
            favouriteLocations?.let {
                displayAllBookmarks(it)
            }

        }
    }

    private fun displayAllBookmarks(favouriteLocations: List<FavouriteLocation>) {
        favouriteLocations.forEach { it ->
            var location =LatLng(it.latitude, it.longitude)
            addPlaceMarker(location)
        }
        Log.e("Marker", favouriteLocations.toString())
    }

    private fun addPlaceMarker(location:LatLng): Marker? {
        val marker = mMap!!.addMarker(MarkerOptions()
            .position(location)
            .icon(
                BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_RED))
            .alpha(0.8f))
        marker.tag = location
        return marker
    }


   override fun onResume() {
        super.onResume()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        when (id) {
            R.id.list_favourites -> {
            }
            R.id.map_favourites -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

}