package com.example.weatherforecast.data.remote

import WeatherForecast
import com.example.weatherforecast.data.Resource
import com.example.weatherforecast.data.remote.service.APIService
import com.example.weatherforecast.dto.DirectGeo
import com.example.weatherforecast.dto.GeoRequest
import com.example.weatherforecast.dto.WeatherCurrently
import com.example.weatherforecast.dto.LocationRequest
import com.example.weatherforecast.dto.ReverseGeocoding
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
        request: LocationRequest): Resource<WeatherCurrently> {
        val dataService = serviceGenerator.createService(APIService::class.java)
        return when (val response = processCall {
            dataService.getCurrentlyWeather(
                latitude = request.latitude, longitude = request.longitude, unit = request.units, lang = request.lang, apiKey = request.apiKey) }) {
            is WeatherCurrently -> {
                Resource.Success(data = response)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun requestForecastWeather(request: LocationRequest): Resource<WeatherForecast> {
        val dataService = serviceGenerator.createService(APIService::class.java)
        return when (val response = processCall {
            dataService.getForecastWeather(
                latitude = request.latitude, longitude = request.longitude, unit = request.units, lang = request.lang, apiKey = request.apiKey) }) {
            is WeatherForecast -> {
                Resource.Success(data = response)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun requestReverseGeocoding(request: LocationRequest): Resource<ReverseGeocoding> {
        val dataService = serviceGenerator.createService(APIService::class.java)
        return when (val response = processCall {
            dataService.getReverseGeocoding(
                latitude = request.latitude, longitude = request.longitude, apiKey = request.apiKey) }) {
            is ReverseGeocoding -> {
                Resource.Success(data = response)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun requestDirectGeo(request: GeoRequest): Resource<DirectGeo> {
        val dataService = serviceGenerator.createService(APIService::class.java)
        return when (val response = processCall {
            dataService.getDirectGeo(
                query = request.query, limit = request.limit, apiKey = request.apiKey) }) {
            is DirectGeo -> {
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
