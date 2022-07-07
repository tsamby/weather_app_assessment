package com.embassylegacy.weatherapp.data.repository;

import android.util.Log
import androidx.annotation.MainThread
import com.embassylegacy.weatherapp.data.local.dao.CurrentWeatherDao
import com.embassylegacy.weatherapp.data.local.dao.ForecastWeatherDao
import com.embassylegacy.weatherapp.data.remote.api.WeatherApiService
import com.embassylegacy.weatherapp.model.CurrentWeatherResponse
import com.embassylegacy.weatherapp.model.ForecastWeatherResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject


public interface CurrentWeatherRepository {
    fun getCurrentWeatherEntry(lat:String, lon : String, units: String): Flow<Resource<CurrentWeatherResponse>>
    fun getForecastWeatherEntry(lat:String, lon : String, units: String): Flow<Resource<ForecastWeatherResponse>>
}

/**
 * Singleton repository for fetching data from remote and storing it in database
 * for offline capability. This is Single source of data.
 */
@ExperimentalCoroutinesApi
class DefaultCurrentWeatherRepository @Inject constructor(
    private val currentWeatherDao: CurrentWeatherDao,
    private val forecastWeatherDao: ForecastWeatherDao,
    private val weatherApiService: WeatherApiService
) : CurrentWeatherRepository {

    /**
     * Fetched the currentWeather from network and stored it in database. At the end, data from persistence
     * storage is fetched and emitted.
     */
    override fun getCurrentWeatherEntry(lat:String, lon : String, units : String): Flow<Resource<CurrentWeatherResponse>> {

        return object : NetworkBoundRepository<CurrentWeatherResponse, CurrentWeatherResponse>() {

            override suspend fun saveRemoteData(response:CurrentWeatherResponse) = currentWeatherDao.insert(response)

            override fun fetchFromLocal(): Flow<CurrentWeatherResponse> = currentWeatherDao.getCurrentWeather()

            override suspend fun fetchFromRemote(): Response<CurrentWeatherResponse> = weatherApiService.getCurrentWeather(lat,lon, WeatherApiService.WEATHER_API_KEY,units)
        }.asFlow()
    }



    override fun getForecastWeatherEntry(lat:String, lon : String, units : String): Flow<Resource<ForecastWeatherResponse>> {

        return object : NetworkBoundRepository<ForecastWeatherResponse, ForecastWeatherResponse>() {

            override suspend fun saveRemoteData(response:ForecastWeatherResponse) = forecastWeatherDao.insert(response)

            override fun fetchFromLocal(): Flow<ForecastWeatherResponse> = forecastWeatherDao.getForecastWeather()

            override suspend fun fetchFromRemote(): Response<ForecastWeatherResponse> = weatherApiService.getForecastWeather(lat,lon, WeatherApiService.WEATHER_API_KEY,units )
        }.asFlow()
    }


}
