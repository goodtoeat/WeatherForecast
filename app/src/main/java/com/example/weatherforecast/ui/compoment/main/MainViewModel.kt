package com.example.weatherforecast.ui.compoment.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.data.DataRepositorySource
import com.example.weatherforecast.data.Resource
import com.example.weatherforecast.dto.WeatherData
import com.example.weatherforecast.dto.WeatherRequest
import com.example.weatherforecast.ui.base.BaseViewModel
import com.example.weatherforecast.utils.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRepository: DataRepositorySource
): BaseViewModel() {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val weatherDataPrivate = MutableLiveData<Resource<WeatherData>>()
    val weatherData: LiveData<Resource<WeatherData>> get() = weatherDataPrivate


    /**
     * Error handling as UI
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showSnackBarPrivate = MutableLiveData<SingleEvent<Any>>()
    val showSnackBar: LiveData<SingleEvent<Any>> get() = showSnackBarPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate

    open fun getWeatherData(){
        viewModelScope.launch {
            dataRepository.requestCurrentWeather(WeatherRequest(latitude = 25.02657, longitude = 121.41254)).collect {
                weatherDataPrivate.value = it
            }
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