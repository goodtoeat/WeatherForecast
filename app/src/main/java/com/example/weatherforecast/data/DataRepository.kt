package com.example.weatherforecast.data

import WeatherForecast
import com.example.weatherforecast.data.remote.RemoteData
import com.example.weatherforecast.dto.WeatherCurrently
import com.example.weatherforecast.dto.WeatherRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DataRepository @Inject constructor(private val remoteRepository: RemoteData, private val ioDispatcher: CoroutineContext) :
    DataRepositorySource {

    override suspend fun requestCurrentWeather(request: WeatherRequest): Flow<Resource<WeatherCurrently>> {
        return flow {
            emit(remoteRepository.requestCurrentWeather(request))
        }.flowOn(ioDispatcher)
    }

    override suspend fun requestForecastWeather(request: WeatherRequest): Flow<Resource<WeatherForecast>> {
        return flow {
            emit(remoteRepository.requestForecastWeather(request))
        }.flowOn(ioDispatcher)
    }
}
