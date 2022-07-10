package com.embassylegacy.weatherapp.ui.favourites

import androidx.lifecycle.*
import com.embassylegacy.weatherapp.data.repository.FavouriteLocationRepository
import com.embassylegacy.weatherapp.model.FavouriteLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouritesViewModel @Inject constructor(private val favouriteLocationRepository: FavouriteLocationRepository):
    ViewModel() {

    val allFavouriteLocations: LiveData<List<FavouriteLocation>> = favouriteLocationRepository.allFavouriteLocations.asLiveData()

    fun insert(favouriteLocation: FavouriteLocation) = viewModelScope.launch {
        favouriteLocationRepository.insertFavouriteLocation(favouriteLocation)
    }

}