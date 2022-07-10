package com.embassylegacy.weatherapp.model



import androidx.room.Entity
import androidx.room.PrimaryKey
import com.embassylegacy.weatherapp.data.local.entity.*
import com.embassylegacy.weatherapp.model.CurrentWeatherResponse.Companion.TABLE_NAME
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass



@Entity(tableName = TABLE_NAME)
@JsonClass(generateAdapter = true)
data class CurrentWeatherResponse(
    @PrimaryKey(autoGenerate = false)
    var cur_id :Int = CURRENT_WEATHER_ID,
    val base: String?,
    val clouds: Clouds?,
   @Json(name = "dt_txt")
    val dtTxt: String?,
    val cod: Int?,
    val coord: Coord?,
    val pop: Double?,
    val dt: Int?,
    val id: Int?,
    val main: Main?,
    val name: String?,
   // val rain: Rain?,
   // val snow: Snow?,
    val sys: Sys?,
    val timezone: Int?,
    val visibility: Int?,
    val weather: List<Weather>?,
    //val wind: Wind?
){
   companion object{
       const val TABLE_NAME = "current_weather"
       const val CURRENT_WEATHER_ID = 0
   }
}

