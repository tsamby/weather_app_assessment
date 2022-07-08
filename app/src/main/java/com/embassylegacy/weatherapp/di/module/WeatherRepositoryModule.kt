package com.embassylegacy.weatherapp.di.module

import com.embassylegacy.weatherapp.data.repository.CurrentWeatherRepository
import com.embassylegacy.weatherapp.data.repository.DefaultCurrentWeatherRepository
import com.embassylegacy.weatherapp.data.repository.FavouriteLocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Currently WeatherRepositoryModule is only used in ViewModels.
 */
@ExperimentalCoroutinesApi
@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class WeatherRepositoryModule {
    @ActivityRetainedScoped
    @Binds
    abstract fun bindWeatherRepository(repository: DefaultCurrentWeatherRepository): CurrentWeatherRepository

}