package com.embassylegacy.weatherapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.embassylegacy.weatherapp.data.repository.CurrentWeatherRepository
import com.embassylegacy.weatherapp.model.CurrentWeatherResponse
import com.embassylegacy.weatherapp.model.ForecastWeatherResponse
import com.embassylegacy.weatherapp.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * ViewModel for [HomeFragment]
 */

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(private val currentWeatherRepository: CurrentWeatherRepository) :
    ViewModel() {

    private val _currentLiveData = MutableLiveData<State<CurrentWeatherResponse>>()
    val currentLiveData: LiveData<State<CurrentWeatherResponse>> = _currentLiveData

    private val _forecastLiveData = MutableLiveData<State<ForecastWeatherResponse>>()
    val forecastLiveData: LiveData<State<ForecastWeatherResponse>> = _forecastLiveData

//    private val _forecastLiveData: MutableStateFlow<State<ForecastWeatherResponse>> = MutableStateFlow(State.loading())
//    val forecastLiveData: StateFlow<State<ForecastWeatherResponse>> = _forecastLiveData



    fun getCurrentWeather(lat:String, lon : String, units:String) {
        Log.d("buhle","getCurrentWeather in viewmodel")
        viewModelScope.launch {
            currentWeatherRepository.getCurrentWeatherEntry(lat,lon,units)
                .onStart { _currentLiveData.value = State.loading() }
                .map { resource -> State.fromResource(resource) }
                .collect { state -> _currentLiveData.value = state  }
        }
    }

//
    fun getForecastWeather(lat:String, lon : String, units:String) {
        Log.d("buhle"," getForecastWeather in viewmodel")
        viewModelScope.launch {
            currentWeatherRepository.getForecastWeatherEntry(lat,lon,units)
                .onStart { _forecastLiveData.value = State.loading() }
                .map { resource -> State.fromResource(resource)
                }.collect { state -> _forecastLiveData.value = state}


        }
    }

//    fun getForecastWeather(lat:String, lon : String, units:String) {
//        Log.d("buhle","getForecastWeather in viewmodel")
//        viewModelScope.launch {
//            currentWeatherRepository.getForecastWeatherEntry(lat,lon,units)
//                .map { resource -> State.fromResource(resource) }
//                .collect { state ->  _forecastLiveData.value = state }
//        }
//    }

//    fun getForecastWeather(lat:String, lon : String, units:String) {
//        viewModelScope.launch {
//            currentWeatherRepository.getForecastWeatherEntry(lat,lon,units)
//                .map { resource -> State.fromResource(resource) }
//                .collect { state -> _forecastLiveData.value = state }
//        }
//    }
}