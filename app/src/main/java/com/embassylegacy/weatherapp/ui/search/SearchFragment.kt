package com.embassylegacy.weatherapp.ui.search


import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.embassylegacy.weatherapp.R
import com.embassylegacy.weatherapp.databinding.FragmentDashboardBinding
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener


class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val ai: ApplicationInfo = activity?.applicationContext!!.packageManager
            .getApplicationInfo(
                activity?.applicationContext!!.packageName,
                PackageManager.GET_META_DATA
            )
        val value = ai.metaData["com.google.android.geo.API_KEY"]
        val apiKey = value.toString()
        //Toast.makeText(activity?.applicationContext!!,key, Toast.LENGTH_LONG).show()

// Initializing the Places API
        // with the help of our API_KEY
        if (!Places.isInitialized()) {
            Places.initialize(activity?.applicationContext!!, apiKey)
        }
        val placesClient = Places.createClient(activity?.applicationContext)

        // Initialize Autocomplete Fragments
        // from the main activity layout file
        //val autocompleteSupportFragment1 = getFragmentManager()?.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment?


        val autocompleteSupportFragment1 =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment?
        // Information that we wish to fetch after typing
        // the location and clicking on one of the options
        autocompleteSupportFragment1!!.setPlaceFields(
            listOf(

                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.PHONE_NUMBER,
                Place.Field.LAT_LNG,
                Place.Field.OPENING_HOURS,
                Place.Field.RATING,
                Place.Field.USER_RATINGS_TOTAL

            )
        )

        // Display the fetched information after clicking on one of the options
        autocompleteSupportFragment1?.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {

                // Text view where we will
                // append the information that we fetch
                val textView: TextView? = binding.tv1

                // Information about the place
                val name = place.name
                val address = place.address
                val id = place.id.toString()
                val latlng = place.latLng
                val latitude = latlng?.latitude
                val longitude = latlng?.longitude

                val isOpenStatus: String = if (place.isOpen == true) {
                    "Open"
                } else {
                    "Closed"
                }

                val rating = place.rating
                val userRatings = place.userRatingsTotal

                textView!!.text = "ID: $id \nName: $name \nAddress: $address  \n" +
                        "Latitude, Longitude: $latitude , $longitude \nIs open: $isOpenStatus \n" +
                        "Rating: $rating \nUser ratings: $userRatings"
            }

            override fun onError(status: Status) {
                Toast.makeText(
                    activity?.applicationContext!!,
                    "Some error occurred",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })


        // val textView: TextView = binding.textDashboard
        searchViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}