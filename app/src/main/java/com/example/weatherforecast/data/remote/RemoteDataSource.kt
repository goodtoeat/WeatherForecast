package com.example.weatherforecast.data.remote

import WeatherForecast
import com.example.weatherforecast.data.Resource
import com.example.weatherforecast.dto.DirectGeo
import com.example.weatherforecast.dto.GeoRequest
import com.example.weatherforecast.dto.ReverseGeocoding
import com.example.weatherforecast.dto.WeatherCurrently
import com.example.weatherforecast.dto.LocationRequest

internal interface RemoteDataSource {
    suspend fun requestCurrentWeather(request: LocationRequest): Resource<WeatherCurrently>
    suspend fun requestForecastWeather(request: LocationRequest): Resource<WeatherForecast>
    suspend fun requestReverseGeocoding(request: LocationRequest): Resource<ReverseGeocoding>
    suspend fun requestDirectGeo(request: GeoRequest): Resource<DirectGeo>
}
