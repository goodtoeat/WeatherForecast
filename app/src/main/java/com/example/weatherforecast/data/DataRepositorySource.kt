package com.example.weatherforecast.data

import com.example.weatherforecast.dto.RiverData
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun doDataRequest(): Flow<Resource<RiverData>>
}
