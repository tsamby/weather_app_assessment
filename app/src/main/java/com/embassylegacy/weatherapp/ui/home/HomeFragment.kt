package com.embassylegacy.weatherapp.ui.home

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.embassylegacy.weatherapp.R
import com.embassylegacy.weatherapp.databinding.FragmentHomeBinding
import com.embassylegacy.weatherapp.model.FavouriteLocation
import com.embassylegacy.weatherapp.model.State
import com.embassylegacy.weatherapp.ui.shared.SharedViewModel
import com.embassylegacy.weatherapp.utils.*
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.math.roundToInt


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val LOCATION_PERMISSION_REQUEST_CODE = 2000
    private val homeViewModel: HomeViewModel by viewModels()
    private val locationViewModel : LocationViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()
    private lateinit var favouriteLocation : FavouriteLocation

    private var _binding: FragmentHomeBinding? = null
    var adapter: WeatherAdapter? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        observeCurrentAndForecastWeather()
        handleNetworkChanges()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        initialisePlacesSDK()
        binding.btnAddToFav!!.setOnClickListener {
            favouriteLocation?.apply {
                sharedViewModel.insert(favouriteLocation)
            }

            binding.btnAddToFav!!.visibility = View.GONE

        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun prepRequestLocationUpdates() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestLocationUpdates()
        } else {
            val permissionRequest = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permissionRequest, LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                requestLocationUpdates()
            }

        }
    }


    private fun requestLocationUpdates() {

        locationViewModel.getLocationLiveData().observe(requireActivity(), Observer {
            it ?:return@Observer
                if (homeViewModel.currentLiveData.value is State.Error || homeViewModel.currentLiveData.value == null) {
                    getCurrentWeather(it.latitude,it.longitude,"metric")
                }
                if (homeViewModel.forecastLiveData.value is State.Error || homeViewModel.forecastLiveData.value == null) {
                getForecastWeather(it.latitude, it.longitude, "metric")
                }

        })
    }


    private fun refresh() {
        locationViewModel.getLocationLiveData().observe(requireActivity(), Observer {
            it ?:return@Observer
                getCurrentWeather(it.latitude,it.longitude,"metric")
                getForecastWeather(it.latitude,it.longitude, "metric")
        })
    }

    fun initView() {
        binding.run {
            swipeRefreshLayout.setOnRefreshListener {
                refresh()
            }
        }

         //If Current State isn't `Success` then reload posts.
        homeViewModel.currentLiveData.value?.let { currentState ->
            if (!currentState.isSuccessful()) {
                prepRequestLocationUpdates()
            }
        }
        homeViewModel.forecastLiveData.value?.let { currentState ->
            if (!currentState.isSuccessful()) {
                prepRequestLocationUpdates()
            }
        }

   }

    private fun observeCurrentAndForecastWeather() {

        homeViewModel.currentLiveData.observe(this) { state ->
            when (state) {
                is State.Loading -> {showLoading(true)}
                is State.Success -> {
                    if (state.data !=null) {
                        showLoading(false)
                        binding.textLocation.text = state.data.name.toString()
                        binding.textDesc.text = state.data.weather?.get(0)?.description.toString()
                        if(state.data.weather?.get(0)?.description.toString().equals("clear sky")){
                            binding.swipeRefreshLayout.setBackgroundResource(R.drawable.forest_sunny);
                        }
                        val temp = state.data.main?.tempMax?.let { it.roundToInt().toInt() }
                        binding.textTemp.text = (temp.toString() + "\u00B0")
                    }
                }
                is State.Error -> {
                    showToast(state.message)
                    showLoading(false)
                }
            }
        }
        homeViewModel.forecastLiveData.observe(this) { state ->
            when (state) {
                is State.Loading -> {showLoading(true)}
                is State.Success -> {
                    if (state.data !=null) {
                        showLoading(false)
                        adapter =
                            WeatherAdapter(
                                state.data
                            )
                        binding.weeklyRecyclerview.adapter = adapter
                        val mLayoutManager = LinearLayoutManager(
                            context,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        binding.weeklyRecyclerview.layoutManager = mLayoutManager
                    }
                }
                is State.Error -> {
                    showToast(state.message)
                    showLoading(false)
                }
            }
        }

    }

    private fun getCurrentWeather(lat:String, lon : String, units:String) = homeViewModel.getCurrentWeather(lat, lon, units)

    private fun getForecastWeather(lat:String, lon : String, units:String) = homeViewModel.getForecastWeather(lat, lon, units)

    private fun showLoading(isLoading: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = isLoading
    }




    /**
     * Observe network changes i.e. Internet Connectivity
     */
    private fun handleNetworkChanges() {



        NetworkUtils.getNetworkLiveData(requireActivity()).observe(this) { isConnected ->

            if (!isConnected) {

                binding.textViewNetworkStatus.text =
                    getString(R.string.text_no_connectivity)
                binding.networkStatusLayout.apply {
                    show()
                    getColorRes(R.color.colorStatusNotConnected)?.let { setBackgroundColor(it) }
                }
                refresh()

            } else {

                prepRequestLocationUpdates()
                binding.textViewNetworkStatus.text = getString(R.string.text_connectivity)
                binding.networkStatusLayout.apply {
                    getColorRes(R.color.colorStatusConnected)?.let { setBackgroundColor(it) }

                    animate()
                        .alpha(1f)
                        .setStartDelay(ANIMATION_DURATION)
                        .setDuration(ANIMATION_DURATION)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                hide()
                            }
                        })
                }
            }
        }
    }

    companion object {
        const val ANIMATION_DURATION = 1000L
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    private fun initialisePlacesSDK(){

        val ai: ApplicationInfo = activity?.applicationContext!!.packageManager
            .getApplicationInfo(
                activity?.applicationContext!!.packageName,
                PackageManager.GET_META_DATA
            )
        val value = ai.metaData["com.google.android.geo.API_KEY"]
        val apiKey = value.toString()


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
        autocompleteSupportFragment1!!.setHint("");
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

                binding.btnAddToFav?.visibility = View.VISIBLE

                // Text view where we will
                // append the information that we fetch
                //val textView: TextView? = binding.tv1

                // Information about the place
                val name = place.name
                val address = place.address
                val id = place.id.toString()
                val latlng = place.latLng
                val latitude = latlng!!.latitude
                val longitude = latlng!!.longitude
                val phoneNumber  = place.phoneNumber

                val isOpenStatus: String = if (place.isOpen == true) {
                    "Open"
                } else {
                    "Closed"
                }
                val rating = place.rating
                val userRatings = place.userRatingsTotal

                favouriteLocation = FavouriteLocation(id,name,address,longitude,latitude,phoneNumber,isOpenStatus,rating, userRatings)

                getCurrentWeather(latitude.toString(),longitude.toString(),"metric")
                getForecastWeather(latitude.toString(),longitude.toString(), "metric")




            }

            override fun onError(status: Status) {
                Toast.makeText(
                    activity?.applicationContext!!,
                    "Some error occurred",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })


    }

}

