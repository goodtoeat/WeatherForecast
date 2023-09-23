package com.example.weatherforecast.data

import WeatherForecast
import com.example.weatherforecast.dto.WeatherCurrently
import com.example.weatherforecast.dto.LocationRequest
import com.example.weatherforecast.dto.ReverseGeocoding
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun requestCurrentWeather(request: LocationRequest): Flow<Resource<WeatherCurrently>>
    suspend fun requestForecastWeather(request: LocationRequest): Flow<Resource<WeatherForecast>>
    suspend fun requestReverseGeocoding(request: LocationRequest): Flow<Resource<ReverseGeocoding>>

}
