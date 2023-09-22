package com.example.weatherforecast.data

import com.example.weatherforecast.data.remote.RemoteData
import com.example.weatherforecast.dto.WeatherData
import com.example.weatherforecast.dto.WeatherRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DataRepository @Inject constructor(private val remoteRepository: RemoteData, private val ioDispatcher: CoroutineContext) :
    DataRepositorySource {

    override suspend fun requestCurrentWeather(request: WeatherRequest): Flow<Resource<WeatherData>> {
        return flow {
            emit(remoteRepository.requestCurrentWeather(request))
        }.flowOn(ioDispatcher)    }
}
