package com.embassylegacy.weatherapp.data.remote.api

import com.embassylegacy.weatherapp.BuildConfig
import com.embassylegacy.weatherapp.model.CurrentWeatherResponse
import com.embassylegacy.weatherapp.model.ForecastWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherApiService {

    @Headers("X-Api-Key: $WEATHER_API_KEY")
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: String,
        @Query ("lon") lon : String,
        @Query ("units") units : String

    ): Response<CurrentWeatherResponse>

    @Headers("X-Api-Key: $WEATHER_API_KEY")
    @GET("forecast")
    suspend fun getForecastWeather(
        @Query("lat") lat: String,
        @Query ("lon") lon : String,
        @Query ("units") units : String
    ): Response<ForecastWeatherResponse>

    companion object {
        const val WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/"
        const val WEATHER_API_KEY = BuildConfig.WEATHER_API_KEY
    }
}