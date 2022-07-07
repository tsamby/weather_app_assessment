package com.embassylegacy.weatherapp.di

import com.embassylegacy.weatherapp.data.remote.api.WeatherApiService
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val httpUrl: HttpUrl = original.url
        val url: HttpUrl = httpUrl.newBuilder()
            .addQueryParameter("appid", WeatherApiService.WEATHER_API_KEY)
            .build()
        val request: Request = original.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}

//token -> 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