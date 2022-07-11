package com.embassylegacy.weatherapp.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.lifecycle.LiveData
import com.embassylegacy.weatherapp.data.local.entity.LocationDetails
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class LocationLiveData(context : Context) : LiveData<LocationDetails>() {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
//        fusedLocationClient.lastLocation.addOnSuccessListener {
//                location: Location  -> location?.also {
//            setLocationData(it)
//        }
//        }


        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if (location != null) {
                    setLocationData(location)
                }
            }



        startLocationUpdates()
    }

    override fun onInactive() {
        super.onInactive()
        // turn off location updates
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper())
    }

    private  val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            locationResult ?: return

            for (location in locationResult.locations){
                setLocationData(location)
            }
        }
    }

    /**
     * If we have received a location update, this function will be called.
     */
    private fun setLocationData(location: Location) {
        if (location != null) {
            value = LocationDetails(location.longitude.toString(), location.latitude.toString())
        }
    }


    companion object{
        val ONE_HOUR : Long = 60000 * 60
        val locationRequest : LocationRequest = LocationRequest.create().apply {
            interval = ONE_HOUR
            fastestInterval = ONE_HOUR/4
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }
}