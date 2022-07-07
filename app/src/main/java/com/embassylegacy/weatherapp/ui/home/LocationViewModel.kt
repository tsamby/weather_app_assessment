package com.embassylegacy.weatherapp.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val locationLiveData = LocationLiveData(application)

    fun getLocationLiveData() = locationLiveData
}