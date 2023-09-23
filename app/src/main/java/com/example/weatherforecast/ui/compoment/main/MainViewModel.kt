package com.example.weatherforecast.ui.compoment.main

import Forecast
import WeatherForecast
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.data.DataRepositorySource
import com.example.weatherforecast.data.Resource
import com.example.weatherforecast.dto.WeatherCurrently
import com.example.weatherforecast.dto.WeatherRequest
import com.example.weatherforecast.ui.base.BaseViewModel
import com.example.weatherforecast.utils.SingleEvent
import com.example.weatherforecast.utils.getHour
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRepository: DataRepositorySource
): BaseViewModel() {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val currentlyDataPrivate = MutableLiveData<Resource<WeatherCurrently>>()
    val currentlyData: LiveData<Resource<WeatherCurrently>> get() = currentlyDataPrivate

    private val forecastDataPrivate = MutableLiveData<Resource<WeatherForecast>>()
    val forecastData: LiveData<Resource<WeatherForecast>> get() = forecastDataPrivate

    private val forecast24HPrivate = MutableLiveData<List<Forecast>>()
    val forecast24H: LiveData<List<Forecast>> get() = forecast24HPrivate

    private val forecast5DayPrivate = MutableLiveData<List<Forecast>>()
    val forecast5Day: LiveData<List<Forecast>> get() = forecast5DayPrivate


    /**
     * Error handling as UI
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showSnackBarPrivate = MutableLiveData<SingleEvent<Any>>()
    val showSnackBar: LiveData<SingleEvent<Any>> get() = showSnackBarPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate

    fun getCurrentlyData(){
        viewModelScope.launch {
            dataRepository.requestCurrentWeather(WeatherRequest(latitude = 25.02657, longitude = 121.41254)).collect {
                currentlyDataPrivate.value = it
            }
        }
    }

    fun getForecastData(){
        viewModelScope.launch {
            dataRepository.requestForecastWeather(WeatherRequest(latitude = 25.02657, longitude = 121.41254)).collect {
                get24Hour(it.data!!)
                get5Day(it.data!!)
            }
        }
    }

    private fun get24Hour(forecast: WeatherForecast){
        //取前8組數據共24小時
        forecast24HPrivate.value = forecast.list.subList(0,8)
    }

    private fun get5Day(forecast: WeatherForecast){
        //取得GMT+8是11點的第一筆數據當作起始點, 用來查找每日11點的數據
        val startIndex = forecast.list.filterIndexed { index, item ->
            getHour(item.dt.toLong()) == 11
        }.map { forecast.list.indexOf(it) }.first()
        //每8組數據取第1組當代表, 共5筆 = 5天
        forecast5DayPrivate.value = forecast.list.filterIndexed { index, _ ->
            index % 8 == startIndex
        }
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = SingleEvent(error.description)
    }

    fun showSnackBarMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showSnackBarPrivate.value = SingleEvent(error.description)
    }
}