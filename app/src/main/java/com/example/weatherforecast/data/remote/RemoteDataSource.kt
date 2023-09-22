package com.example.weatherforecast.data.remote

import com.example.weatherforecast.data.Resource
import com.example.weatherforecast.dto.WeatherData
import com.example.weatherforecast.dto.WeatherRequest

internal interface RemoteDataSource {
    suspend fun requestCurrentWeather(request: WeatherRequest): Resource<WeatherData>
}
