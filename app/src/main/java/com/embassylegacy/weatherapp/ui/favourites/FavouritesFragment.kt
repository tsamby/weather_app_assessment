package com.embassylegacy.weatherapp.ui.favourites

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.embassylegacy.weatherapp.R
import com.embassylegacy.weatherapp.databinding.FragmentFavouritesBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FavouritesFragment : Fragment(), OnMapReadyCallback{

    private val favouritesViewModel : FavouritesViewModel by viewModels()
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!



    private var mMap: GoogleMap? = null
    var TamWorth = LatLng(-31.083332, 150.916672)
    var NewCastle = LatLng(-32.916668, 151.750000)
    var Brisbane = LatLng(-27.470125, 153.021072)

    // creating array list for adding all our locations.
    private var locationArrayList: ArrayList<LatLng>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = FavouriteLocationListAdapter()
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(context)

        favouritesViewModel.allFavouriteLocations.observe(viewLifecycleOwner) { favouriteLocations ->
            // Update the cached copy of the favourite locations in the adapter.
            favouriteLocations.let { adapter.submitList(it) }
        }

        // Initialize map fragment
        val supportMapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?
        supportMapFragment!!.getMapAsync(this);

        // in below line we are initializing our array list.
        // in below line we are initializing our array list.
        locationArrayList = ArrayList()

        // on below line we are adding our
        // locations in our array list.

        // on below line we are adding our
        // locations in our array list.
        locationArrayList!!.add(TamWorth)
        locationArrayList!!.add(NewCastle)
        locationArrayList!!.add(Brisbane)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        // inside on map ready method
        // we will be displaying all our markers.
        // for adding markers we are running for loop and
        // inside that we are drawing marker on our map.
        // inside on map ready method
        // we will be displaying all our markers.
        // for adding markers we are running for loop and
        // inside that we are drawing marker on our map.
        for (i in locationArrayList!!.indices) {

            // below line is use to add marker to each location of our array list.
            mMap?.addMarker(MarkerOptions().position(locationArrayList!![i]).title("Marker"))

            // below lin is use to zoom our camera on map.
            mMap?.animateCamera(CameraUpdateFactory.zoomTo(100.0f))

            // below line is use to move our camera to the specific location.
            mMap?.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList!![i]))
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.list_favourites-> {
//                binding.recyclerview.visibility == View.VISIBLE
//                binding.mapLayout!!.visibility = View.GONE
//                true
//
//            }
//            R.id.map_favourites->{
//                binding.mapLayout!!.visibility = View.VISIBLE
//                true
//            }
//
//            else -> true
//        }
//
//
//
//    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        when (id) {
            R.id.list_favourites -> {
                binding.recyclerview.visibility == View.VISIBLE
                binding.mapLayout!!.visibility = View.GONE
            }
            R.id.map_favourites -> {
                binding.mapLayout!!.visibility = View.VISIBLE
                binding.recyclerview.visibility == View.GONE
            }

        }
        return super.onOptionsItemSelected(item)
    }

}