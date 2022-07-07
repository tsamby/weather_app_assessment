package com.embassylegacy.weatherapp.data.local.dao




import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.embassylegacy.weatherapp.model.ForecastWeatherResponse
import com.embassylegacy.weatherapp.model.ForecastWeatherResponse.Companion.FORECAST_WEATHER_ID
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastWeatherDao {

    /**
     * Inserts [forecastWeatherResponse] into the [ForecastWeatherResponse.TABLE_NAME] table.
     * Duplicate values are replaced in the table.
     * @param forecastWeatherResponse ForecastWeatherResponse
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(forecastWeatherResponse: ForecastWeatherResponse)

    /**
     * Fetches forecast weather from the [ForecastWeatherResponse.TABLE_NAME] table.
     * @return [Flow]
     */
    @Query("select * from forecast_weather where forecast_id = $FORECAST_WEATHER_ID")
    fun getForecastWeather(): Flow<ForecastWeatherResponse>
}