package com.embassylegacy.weatherapp.data.repository

import androidx.annotation.WorkerThread
import com.embassylegacy.weatherapp.data.local.dao.FavouriteLocationDao
import com.embassylegacy.weatherapp.model.FavouriteLocation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavouriteLocationRepository @Inject constructor(private val favouriteLocationDao: FavouriteLocationDao) {

    //suspend fun  insertFavouriteLocation(favouriteLocation: FavouriteLocation) = favouriteLocationDao.insert(favouriteLocation)


    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertFavouriteLocation(favouriteLocation: FavouriteLocation) = favouriteLocationDao.insert(favouriteLocation)



    //suspend fun  fetchFavouriteLocations() = favouriteLocationDao.fetch()

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allFavouriteLocations: Flow<List<FavouriteLocation>> = favouriteLocationDao.fetch()
}