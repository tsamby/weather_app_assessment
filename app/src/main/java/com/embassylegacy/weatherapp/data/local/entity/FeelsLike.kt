package com.embassylegacy.weatherapp.data.local.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeelsLike(
    @Json(name = "day")
    val day: Double? = null,
    @Json(name = "eve")
    val eve: Double? = null,
    @Json(name = "morn")
    val morn: Double? = null,
    @Json(name = "night")
    val night: Double? = null
)