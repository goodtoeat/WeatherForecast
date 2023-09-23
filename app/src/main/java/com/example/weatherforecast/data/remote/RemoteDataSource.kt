package com.example.weatherforecast.data.remote

import WeatherForecast
import com.example.weatherforecast.data.Resource
import com.example.weatherforecast.dto.WeatherCurrently
import com.example.weatherforecast.dto.WeatherRequest

internal interface RemoteDataSource {
    suspend fun requestCurrentWeather(request: WeatherRequest): Resource<WeatherCurrently>
    suspend fun requestForecastWeather(request: WeatherRequest): Resource<WeatherForecast>
}
