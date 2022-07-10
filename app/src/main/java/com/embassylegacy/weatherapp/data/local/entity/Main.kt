package com.embassylegacy.weatherapp.data.local.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.annotation.Nullable

@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "feels_like")
    val feelsLike: Double?,
    @Nullable
    //@Json(name = "grnd_level")
    //val grnd_level: Int?=0,
    val humidity: Int?,
    val pressure: Int?,
    //@Json(name = "sea_level")
    //val seaLevel: Int?,
    //val temp: Double?,
    //@Json(name = "temp_kf")
    //val tempKf: Double?,
    @Json(name = "temp_max")
    val tempMax: Double?
  //  @Json(name = "temp_min")
   // val tempMin: Double?
)

