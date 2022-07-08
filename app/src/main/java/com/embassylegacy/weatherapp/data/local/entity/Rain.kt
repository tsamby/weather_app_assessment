package com.embassylegacy.weatherapp.data.local.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Rain(
    @Json(name = "3h")
    val h: Double?
)