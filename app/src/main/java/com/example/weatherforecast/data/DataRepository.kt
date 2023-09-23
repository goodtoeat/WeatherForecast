package com.example.weatherforecast.data

import WeatherForecast
import com.example.weatherforecast.data.remote.RemoteData
import com.example.weatherforecast.dto.WeatherCurrently
import com.example.weatherforecast.dto.LocationRequest
import com.example.weatherforecast.dto.ReverseGeocoding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DataRepository @Inject constructor(private val remoteRepository: RemoteData, private val ioDispatcher: CoroutineContext) :
    DataRepositorySource {

    override suspend fun requestCurrentWeather(request: LocationRequest): Flow<Resource<WeatherCurrently>> {
        return flow {
            emit(remoteRepository.requestCurrentWeather(request))
        }.flowOn(ioDispatcher)
    }

    override suspend fun requestForecastWeather(request: LocationRequest): Flow<Resource<WeatherForecast>> {
        return flow {
            emit(remoteRepository.requestForecastWeather(request))
        }.flowOn(ioDispatcher)
    }

    override suspend fun requestReverseGeocoding(request: LocationRequest): Flow<Resource<ReverseGeocoding>> {
        return flow {
            emit(remoteRepository.requestReverseGeocoding(request))
        }.flowOn(ioDispatcher)
    }
}
