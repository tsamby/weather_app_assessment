package com.embassylegacy.weatherapp.data.local.entity


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class City(
  //  val coord: Coord?,
    val country: String?,
    val id: Int?,
    val name: String?,
    val population: Int?,
    val sunrise: Int?,
    val sunset: Int?,
    val timezone: Int?
)