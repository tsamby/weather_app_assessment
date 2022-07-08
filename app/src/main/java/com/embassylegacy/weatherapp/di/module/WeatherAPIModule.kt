package com.embassylegacy.weatherapp.di.module



import android.content.Context
import androidx.viewbinding.BuildConfig
import com.embassylegacy.weatherapp.data.local.WeatherDatabase
import com.embassylegacy.weatherapp.data.local.dao.FavouriteLocationDao
import com.embassylegacy.weatherapp.data.remote.api.WeatherApiService
import com.embassylegacy.weatherapp.data.repository.FavouriteLocationRepository
import com.embassylegacy.weatherapp.di.ApiKeyInterceptor
import com.embassylegacy.weatherapp.di.qualifiers.ApiKeyInterceptorOkHttpClient

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class WeatherAPIModule {


    @Provides
    fun provideBaseUrl() = WeatherApiService.WEATHER_API_URL

    @ApiKeyInterceptorOkHttpClient
    @Singleton
    @Provides
    fun providesApiKeyInterceptor(): Interceptor = ApiKeyInterceptor()

    @Singleton
    @Provides
    fun providesOkhttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @ApiKeyInterceptorOkHttpClient apiKeyInterceptor: Interceptor
    ) =if (BuildConfig.DEBUG){
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }else {
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

//    @Provides
//    @Singleton
//    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
//        val loggingInterceptor = HttpLoggingInterceptor()
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//        OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor)
//            .build()
//    } else OkHttpClient
//        .Builder()
//        .build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

//    @Provides
//    @Singleton
//    fun provideApiService(retrofit: Retrofit): WeatherApiService = retrofit.create(WeatherApiService::class.java)

    @Singleton
    @Provides
    fun provideApiService(): WeatherApiService = Retrofit.Builder()
        .baseUrl(WeatherApiService.WEATHER_API_URL)
        .addConverterFactory(
            MoshiConverterFactory.create()
        )
        .build()
        .create(WeatherApiService::class.java)




}