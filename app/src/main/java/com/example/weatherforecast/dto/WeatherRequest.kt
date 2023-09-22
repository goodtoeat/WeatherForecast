package com.example.weatherforecast.dto

data class WeatherRequest(val latitude: Double,
                           val longitude: Double,
                           val apiKey: String = "8a39776ce73bbd8bdc2d7955c1495572")