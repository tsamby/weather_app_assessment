package com.embassylegacy.weatherapp.data.remote.api


import com.embassylegacy.weatherapp.model.CurrentWeatherResponse
import com.embassylegacy.weatherapp.model.ForecastWeatherResponse
import okhttp3.Interceptor
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: String,
        @Query ("lon") lon : String,
        @Query ("appid") appid : String,
        @Query ("units") units : String

    ): Response<CurrentWeatherResponse>

    @GET("forecast")
    suspend fun getForecastWeather(
        @Query("lat") lat: String,
        @Query ("lon") lon : String,
        @Query ("appid") appid : String,
        @Query ("units") units : String
    ): Response<ForecastWeatherResponse>

    companion object {
        const val WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/"
        const val WEATHER_API_KEY = "6fde07f0dafd020dd3bd7803c2bc5398"
    }
}