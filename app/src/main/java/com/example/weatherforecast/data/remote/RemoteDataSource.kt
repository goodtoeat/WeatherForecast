package com.example.weatherforecast.data.remote

import com.example.weatherforecast.data.Resource
import com.example.weatherforecast.dto.RiverData

internal interface RemoteDataSource {
    suspend fun requestData(): Resource<RiverData>
}
