package com.embassylegacy.weatherapp.model


import com.embassylegacy.weatherapp.data.local.entity.Daily
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DailyWeatherResponse(
    @Json(name = "current")
    val current: Current? = null,
    @Json(name = "daily")
    val daily: List<Daily>? = null,
    @Json(name = "lat")
    val lat: Double? = null,
    @Json(name = "lon")
    val lon: Double? = null,
    @Json(name = "timezone")
    val timezone: String? = null,
    @Json(name = "timezone_offset")
    val timezoneOffset: Int? = null
)