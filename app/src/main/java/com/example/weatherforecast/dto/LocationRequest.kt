package com.example.weatherforecast.dto

data class LocationRequest(val latitude: Double,
                           val longitude: Double,
                           val units: String = "metric",
                           val lang: String = "zh_tw",
                           val apiKey: String = "e254105efb3b91a4f1c5a2c0bd9dfe17"

)