package com.example.weatherforecast.data.remote.service

import WeatherForecast
import com.example.weatherforecast.dto.ReverseGeocoding
import com.example.weatherforecast.dto.WeatherCurrently
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("data/2.5/weather")
    suspend fun getCurrentlyWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") unit: String?,
        @Query("lang") lang: String?,
        @Query("appid") apiKey: String?
    ): Response<WeatherCurrently>

    @GET("data/2.5/forecast")//呼叫5天/3小時預報數據
    suspend fun getForecastWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") unit: String?,
        @Query("lang") lang: String?,
        @Query("appid") apiKey: String?
    ): Response<WeatherForecast>

    @GET("geo/1.0/reverse")//呼叫5天/3小時預報數據
    suspend fun getReverseGeocoding(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String?
    ): Response<ReverseGeocoding>
}
