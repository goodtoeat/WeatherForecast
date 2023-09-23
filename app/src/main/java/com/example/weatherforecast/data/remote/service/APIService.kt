package com.example.weatherforecast.data.remote.service

import WeatherForecast
import com.example.weatherforecast.dto.WeatherCurrently
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("weather")
    suspend fun getCurrentlyWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") unit: String?,
        @Query("lang") lang: String?,
        @Query("appid") apiKey: String?
    ): Response<WeatherCurrently>

    @GET("forecast")//呼叫5天/3小時預報數據
    suspend fun getForecastWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") unit: String?,
        @Query("lang") lang: String?,
        @Query("appid") apiKey: String?
    ): Response<WeatherForecast>
}
