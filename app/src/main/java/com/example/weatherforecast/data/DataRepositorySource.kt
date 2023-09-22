package com.example.weatherforecast.data

import com.example.weatherforecast.dto.WeatherData
import com.example.weatherforecast.dto.WeatherRequest
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun requestCurrentWeather(request: WeatherRequest): Flow<Resource<WeatherData>>
}
