package com.example.weatherforecast.dto

class DirectGeo :ArrayList<DirectGeoItem>()

data class DirectGeoItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNamesDirect,
    val lon: Double,
    val name: String,
    val state: String
)

data class LocalNamesDirect(
    val en: String,
    val ja: String,
    val ko: String,
    val zh: String
)