package com.embassylegacy.weatherapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.embassylegacy.weatherapp.model.CurrentWeatherResponse
import com.embassylegacy.weatherapp.model.CurrentWeatherResponse.Companion.CURRENT_WEATHER_ID
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentWeatherDao {

    /**
     * Inserts [currentWeatherResponse] into the [CurrentWeatherResponse.TABLE_NAME] table.
     * Duplicate values are replaced in the table.
     * @param currentWeatherResponse CurrentWeatherResponse
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currentWeatherResponse: CurrentWeatherResponse)

    /**
     * Fetch current weather from the [CurrentWeatherResponse.TABLE_NAME] table.
     * @return [Flow]
     */
    @Query("select * from current_weather where cur_id = $CURRENT_WEATHER_ID")
    fun getCurrentWeather(): Flow<CurrentWeatherResponse>
}