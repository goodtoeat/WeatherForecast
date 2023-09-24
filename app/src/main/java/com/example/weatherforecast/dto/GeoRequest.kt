package com.example.weatherforecast.dto

data class GeoRequest(val query: String = "",
                      val limit: Int = 5,
                      val apiKey: String = "8a39776ce73bbd8bdc2d7955c1495572"
)