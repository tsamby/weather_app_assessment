package com.embassylegacy.weatherapp.ui.home

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.embassylegacy.weatherapp.R
import com.embassylegacy.weatherapp.databinding.FragmentHomeBinding
import com.embassylegacy.weatherapp.model.State
import com.embassylegacy.weatherapp.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.math.roundToInt


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val LOCATION_PERMISSION_REQUEST_CODE = 2000
    private val homeViewModel: HomeViewModel by viewModels()
    private val locationViewModel : LocationViewModel by viewModels()
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
        Log.d("flowtest","requestLocationUpdates()")
        locationViewModel.getLocationLiveData().observe(requireActivity(), Observer {
            it ?:return@Observer

                if (homeViewModel.currentLiveData.value is State.Error || homeViewModel.currentLiveData.value == null) {
                    getCurrentWeather(it.latitude,it.longitude,"metric")
                }

                if (homeViewModel.forecastLiveData.value is State.Error || homeViewModel.forecastLiveData.value == null) {
                getForecastWeather(it.latitude, it.longitude, "metric")
                }

//            Log.d("buhle error",""+ (homeViewModel.forecastLiveData.value is State.Error))
//            Log.d("buhle success",""+ (homeViewModel.forecastLiveData.value is State.Success))
//            Log.d("buhle loading",""+ (homeViewModel.forecastLiveData.value is State.Loading))

//            lifecycleScope.launch {
//                homeViewModel.forecastLiveData.collect {
//                   Log.d("buhle", ""+ it.isLoading())
//                   // Log.d("flowtest", "homeViewModel.forecastLiveData.collect")
//                }
//            }

            //this is called plenty of times

            //Log.d("buhle", homeViewModel.forecastLiveData.value)


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
               // prepRequestLocationUpdates()
               // requestLocationUpdates()
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
//                        binding.weeklyRecyclerview.addItemDecoration(
//                            DividerItemDecoration(
//                                binding.weeklyRecyclerview.context,
//                                DividerItemDecoration.VERTICAL
//                            )
//                        )


                    }
                }
                is State.Error -> {
                    showToast(state.message)
                    showLoading(false)
                }
            }
        }

//        lifecycleScope.launch {
//                homeViewModel.forecastLiveData.collect { state ->
//                    when (state) {
//                        is State.Loading -> showLoading(true)
//                        is State.Success -> {
//                            if (state.data !=null) {
//                                showLoading(false)
//
//                                adapter =
//                                    WeatherAdapter(
//                                        state.data
//                                    )
//                                binding.weeklyRecyclerview.adapter = adapter
//                                val mLayoutManager = LinearLayoutManager(
//                                    context,
//                                    LinearLayoutManager.VERTICAL,
//                                    false
//                                )
//                                binding.weeklyRecyclerview.layoutManager = mLayoutManager
//
//
//                            }
//                        }
//                        is State.Error -> {
//                            showToast(state.message)
//                            showLoading(false)
//                        }
//                    }
//                }
//
//        }

    }


//    @InternalCoroutinesApi
//    private fun observePosts() {
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                homeViewModel.forecastLiveData.collect{ state ->
//                    when (state) {
//                        is State.Loading -> showLoading(true)
//                        is State.Success -> {
//
//                        }
//                        is State.Error -> {
//                            showToast(state.message)
//                            showLoading(false)
//                        }
//                    }
//                }
//            }
//        }
//    }


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

}

