package com.embassylegacy.weatherapp.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.embassylegacy.weatherapp.data.local.entity.City
import com.squareup.moshi.JsonClass

@Entity(tableName = ForecastWeatherResponse.TABLE_NAME)
@JsonClass(generateAdapter = true)
data class ForecastWeatherResponse(
    @PrimaryKey(autoGenerate = false)
    var forecast_id :Int = FORECAST_WEATHER_ID,
    val city: City?,
    val cnt: Int?,
    val cod: String?,
    val list: List<CurrentWeatherResponse>?,
    val message: Int?
){
    companion object{
        const val TABLE_NAME = "forecast_weather"
        const val FORECAST_WEATHER_ID = 0
    }
}