package com.embassylegacy.weatherapp.data.local.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Temp(
    @Json(name = "day")
    val day: Double? = null,
    @Json(name = "eve")
    val eve: Double? = null,
    @Json(name = "max")
    val max: Double? = null,
    @Json(name = "min")
    val min: Double? = null,
    @Json(name = "morn")
    val morn: Double? = null,
    @Json(name = "night")
    val night: Double? = null
)