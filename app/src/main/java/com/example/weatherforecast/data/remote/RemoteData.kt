package com.example.weatherforecast.data.remote

import WeatherForecast
import com.example.weatherforecast.data.Resource
import com.example.weatherforecast.data.remote.service.APIService
import com.example.weatherforecast.dto.WeatherCurrently
import com.example.weatherforecast.dto.WeatherRequest
import com.example.weatherforecast.error.NETWORK_ERROR
import com.example.weatherforecast.error.NO_INTERNET_CONNECTION
import com.example.weatherforecast.utils.NetworkConnectivity
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


class RemoteData @Inject
constructor(private val serviceGenerator: ServiceGenerator, private val networkConnectivity: NetworkConnectivity) :
    RemoteDataSource {
    override suspend fun requestCurrentWeather(
        request: WeatherRequest): Resource<WeatherCurrently> {
        val dataService = serviceGenerator.createService(APIService::class.java)
        return when (val response = processCall {
            dataService.getCurrentlyWeather(
                request.latitude, request.longitude, request.units, request.lang, request.apiKey) }) {
            is WeatherCurrently -> {
                Resource.Success(data = response)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun requestForecastWeather(request: WeatherRequest): Resource<WeatherForecast> {
        val dataService = serviceGenerator.createService(APIService::class.java)
        return when (val response = processCall {
            dataService.getForecastWeather(
                request.latitude, request.longitude, request.units, request.lang, request.apiKey) }) {
            is WeatherForecast -> {
                Resource.Success(data = response)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}
