package com.embassylegacy.weatherapp.data.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.embassylegacy.weatherapp.data.local.dao.CurrentWeatherDao
import com.embassylegacy.weatherapp.data.local.dao.ForecastWeatherDao
import com.embassylegacy.weatherapp.model.CurrentWeatherResponse
import com.embassylegacy.weatherapp.model.ForecastWeatherResponse

/**
 * Abstract Weather database.
 * It provides DAO [CurrentWeatherResponseDao] by using method [getWeatherDatabase].
 */
@Database(
    entities = [CurrentWeatherResponse::class, ForecastWeatherResponse::class],
    version = DatabaseMigrations.DB_VERSION
)
@TypeConverters(TypeConverter::class)
abstract class WeatherDatabase : RoomDatabase() {

    /**
     * @return [CurrentWeatherResponseDao] CurrentWeatherResponse Data Access Object.
     */
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun forecastWeatherDao() : ForecastWeatherDao

    companion object {
        const val DB_NAME = "weather_database"

        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration()
                    .addMigrations(*DatabaseMigrations.MIGRATIONS)
                .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}