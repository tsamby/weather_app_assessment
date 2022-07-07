package com.embassylegacy.weatherapp.di.module

import android.app.Application
import com.embassylegacy.weatherapp.data.local.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class WeatherDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = WeatherDatabase.getInstance(application)

    @Singleton
    @Provides
    fun provideCurrentWeatherDao(database: WeatherDatabase) = database.currentWeatherDao()

    @Singleton
    @Provides
    fun provideForecastWeatherDao(database: WeatherDatabase) = database.forecastWeatherDao()
}
