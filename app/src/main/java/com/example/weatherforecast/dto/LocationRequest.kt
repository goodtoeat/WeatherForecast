package com.example.weatherforecast.dto

data class LocationRequest(val latitude: Double,
                           val longitude: Double,
                           val units: String = "metric",
                           val lang: String = "zh_tw",
                           val apiKey: String = "8a39776ce73bbd8bdc2d7955c1495572"

)