package com.example.weatherforecast.dto

data class GeoRequest(val query: String = "",
                      val limit: Int = 5,
                      val apiKey: String = "e254105efb3b91a4f1c5a2c0bd9dfe17"
)