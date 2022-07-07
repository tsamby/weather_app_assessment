package com.embassylegacy.weatherapp.model


import com.embassylegacy.weatherapp.data.local.entity.Rain
import com.embassylegacy.weatherapp.data.local.entity.Weather
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Current(
    @Json(name = "clouds")
    val clouds: Int? = null,
    @Json(name = "dew_point")
    val dewPoint: Double? = null,
    @Json(name = "dt")
    val dt: Int? = null,
    @Json(name = "feels_like")
    val feelsLike: Double? = null,
    @Json(name = "humidity")
    val humidity: Int? = null,
    @Json(name = "pressure")
    val pressure: Int? = null,
    @Json(name = "rain")
    val rain: Rain? = null,
    @Json(name = "sunrise")
    val sunrise: Int? = null,
    @Json(name = "sunset")
    val sunset: Int? = null,
    @Json(name = "temp")
    val temp: Double? = null,
    @Json(name = "uvi")
    val uvi: Int? = null,
    @Json(name = "visibility")
    val visibility: Int? = null,
    @Json(name = "weather")
    val weather: List<Weather>? = null,
    @Json(name = "wind_deg")
    val windDeg: Int? = null,
    @Json(name = "wind_speed")
    val windSpeed: Double? = null
)