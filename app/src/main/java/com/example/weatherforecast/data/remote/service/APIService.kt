package com.example.weatherforecast.data.remote.service

import com.example.weatherforecast.dto.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") unit: String?,
        @Query("lang") lang: String?,
        @Query("appid") apiKey: String?
    ): Response<WeatherData>
}
