package com.embassylegacy.weatherapp.data.local

import androidx.room.TypeConverter
import com.embassylegacy.weatherapp.data.local.entity.*
import com.embassylegacy.weatherapp.model.CurrentWeatherResponse
import com.google.gson.Gson
import com.squareup.moshi.Json
import org.json.JSONObject

class TypeConverter {

    @TypeConverter
    fun fromCoord(coord: Coord): String {
        return JSONObject().apply {
            put("lat", coord.lat)
            put("lon", coord.lon)
        }.toString()
    }

    @TypeConverter
    fun toCoord(coord: String): Coord {
        val json = JSONObject(coord)
        return Coord(json.get("lat") as String, json.get("lon") as String)
    }

    @TypeConverter
    fun fromClouds(clouds: Clouds): String {
        return JSONObject().apply {
            put("all", clouds.all)
        }.toString()
    }

    @TypeConverter
    fun toClouds(clouds: String): Clouds {
        val json = JSONObject(clouds)
        return Clouds(json.get("all") as Int)
    }

    @TypeConverter
    fun fromRain(rain: Rain?): String {
        return JSONObject().apply {
            put("h", rain?.h)
        }.toString()
    }

    @TypeConverter
    fun toRain(rain: String): Rain {
        val json = JSONObject(rain)
        return Rain(json.get("h") as Double)
    }

    @TypeConverter
    fun fromSnow(snow: Snow?): String {
        return JSONObject().apply {
            put("h", snow?.h)
        }.toString()
    }

    @TypeConverter
    fun toSnow(snow: String): Snow{
        val json = JSONObject(snow)
        return Snow(json.get("h") as Double)
    }

    @TypeConverter
    fun fromMain(main: Main): String {
        return JSONObject().apply {
            put("feelsLike", main.feelsLike)
            //put("grnd_level", main.grnd_level)
            put("humidity", main.humidity)
            put("pressure", main.pressure)
           // put("seaLevel", main.seaLevel)
            put("temp", main.temp)
          //  put("tempKf", main.tempKf)
            put("tempMax", main.tempMax)
          //  put("tempMin", main.tempMin)
        }.toString()
    }

    @TypeConverter
    fun toMain(main: String): Main {
        val json = JSONObject(main)
        return Main(json.get("feelsLike") as Double,
//            json.get("grnd_level") as Int,
            json.get("humidity") as Int,
            json.get("pressure") as Int,
            //json.get("seaLevel") as Int,
            json.get("temp") as Double,
           // json.get("tempKf") as Double,
            json.get("tempMax") as Double,
          //  json.get("tempMin") as Double
            )
    }




    @TypeConverter
    fun fromSys(sys: Sys): String {
        return JSONObject().apply {
            put("country", sys.country)
            put("id", sys.id)
            put("sunrise", sys.sunrise)
            put("sunset", sys.sunset)
            put("type", sys.type)
        }.toString()
    }

    @TypeConverter
    fun toSys(sys: String): Sys {
        val json = JSONObject(sys)
        return Sys(json.get("country") as String,
            json.get("id") as Int,
            json.get("sunrise") as Int,
            json.get("sunset") as Int,
            json.get("type") as Int,
            //json.get("pod") as String
        )
    }

    @TypeConverter
    fun fromWeather(weather: Weather): String {
        return JSONObject().apply {
            put("description", weather.description)
            put("icon", weather.icon)
            put("id", weather.id)
            put("main", weather.main)
        }.toString()
    }

    @TypeConverter
    fun toWeather(weather: String): Weather {
        val json = JSONObject(weather)
        return Weather(
            json.get("description") as String,
            json.get("icon") as String,
            json.get("id") as Int,
            json.get("main") as String
        )
    }

    @TypeConverter
    fun listToJson(value: List<Weather>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Weather>::class.java).toList()

    @TypeConverter
    fun currentListToJson(value: List<CurrentWeatherResponse>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToCurrentList(value: String) = Gson().fromJson(value, Array<CurrentWeatherResponse>::class.java).toList()

//    @TypeConverter
//    fun fromWind(wind: Wind): String {
//        return JSONObject().apply {
//            put("deg", wind.deg)
//            put("speed", wind.speed)
//        }.toString()
//    }
//
//    @TypeConverter
//    fun toWind(wind: String): Wind {
//        val json = JSONObject(wind)
//        return Wind(
//            json.get("deg") as Int,
//            json.get("speed") as Double
//        )
//    }

    @TypeConverter
    fun fromCity(city: City): String {
        return JSONObject().apply {
            //put("coord", city.coord)
            put("country", city.country)
            put("id", city.id)
            put("name", city.name)
            put("population", city.population)
            put("sunrise", city.sunrise)
            put("sunset", city.sunset)
            put("timezone", city.timezone)
        }.toString()
    }

    @TypeConverter
    fun toCity(city: String): City {
        val json = JSONObject(city)
        return City(
        //json.get("coord") as Coord,
        json.get("country") as String,
        json.get("id") as Int,
        json.get("name") as String,
        json.get("population") as Int,
        json.get("sunrise") as Int,
        json.get("sunset") as Int,
        json.get("timezone") as Int,
        )
    }


}