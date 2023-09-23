package com.example.weatherforecast.data

import WeatherForecast
import com.example.weatherforecast.dto.WeatherCurrently
import com.example.weatherforecast.dto.WeatherRequest
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun requestCurrentWeather(request: WeatherRequest): Flow<Resource<WeatherCurrently>>
    suspend fun requestForecastWeather(request: WeatherRequest): Flow<Resource<WeatherForecast>>

}
