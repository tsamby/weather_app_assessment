package com.embassylegacy.weatherapp.utils

import com.embassylegacy.weatherapp.R

/**
 * returns image icon for respective weather condition
 */
object WeatherIconManager {
    fun getIcon(weatherIcon: String?, weatherDescription: String): Int {
        var icon: Int
        when (weatherIcon) {
            "01d" -> icon = R.drawable.sunny_light_color_96dp
            "01n" -> icon = R.drawable.clear_night_light_color_96dp
            "02d" -> icon = R.drawable.mostly_sunny_light_color_96dp
            "02n" -> icon = R.drawable.mostly_clear_night_light_color_96dp
            "03d" -> icon = R.drawable.partly_cloudy_light_color_96dp
            "03n" -> icon = R.drawable.partly_cloudy_night_light_color_96dp
            "04d" -> {
                icon = R.drawable.cloudy_light_color_96dp
                if (weatherDescription.contains("broken")) {
                    icon = R.drawable.mostly_cloudy_day_light_color_96dp
                }
            }
            "04n" -> {
                icon = R.drawable.cloudy_light_color_96dp
                if (weatherDescription.contains("broken")) {
                    icon = R.drawable.mostly_cloudy_night_light_color_96dp
                }
            }
            "09d", "09n" -> icon = R.drawable.showers_rain_light_color_96dp
            "10d" -> icon = R.drawable.scattered_showers_day_light_color_96dp
            "10n" -> icon = R.drawable.scattered_showers_night_light_color_96dp
            "11d", "11n" -> icon = R.drawable.strong_tstorms_light_color_96dp
            "13d", "13n" -> icon = R.drawable.snow_showers_snow_light_color_96dp
            "50d", "50n" -> icon = R.drawable.haze_fog_dust_smoke_light_color_96dp
            else -> icon = R.drawable.wintry_mix_rain_snow_light_color_96dp
        }
        return icon
    }
}
