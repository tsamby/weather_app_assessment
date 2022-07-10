package com.embassylegacy.weatherapp.data.local.entity


import androidx.annotation.Nullable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sys(
    @Nullable
    val country: String?=null,
    //@Nullable
    //val id: Int?,
    val sunrise: Int?=null,
    val sunset: Int?=null,

    //val type: Int?=null,
    //val pod: String?
)