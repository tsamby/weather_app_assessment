package com.embassylegacy.weatherapp.di.module

import android.app.Application
import android.content.Context
import com.embassylegacy.weatherapp.data.local.WeatherDatabase
import com.embassylegacy.weatherapp.data.local.dao.FavouriteLocationDao
import com.embassylegacy.weatherapp.data.repository.FavouriteLocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun provideFavouriteLocationDao(@ApplicationContext appContext: Context) : FavouriteLocationDao {
        return WeatherDatabase.getInstance(appContext).favouriteLocationDao()
    }

    @Singleton
    @Provides
    fun provideStudentDBRepository(residentDao: FavouriteLocationDao) = FavouriteLocationRepository(residentDao)

}
