package com.example.weatherforecast.ui.compoment.main

import Forecast
import WeatherForecast
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.data.DataRepositorySource
import com.example.weatherforecast.data.Resource
import com.example.weatherforecast.dto.DirectGeoItem
import com.example.weatherforecast.dto.GeoRequest
import com.example.weatherforecast.dto.WeatherCurrently
import com.example.weatherforecast.dto.LocationRequest
import com.example.weatherforecast.dto.ReverseGeocoding
import com.example.weatherforecast.ui.base.BaseViewModel
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

    private val forecast24HPrivate = MutableLiveData<List<Forecast>>()
    val forecast24H: LiveData<List<Forecast>> get() = forecast24HPrivate

    private val forecast5DayPrivate = MutableLiveData<List<Forecast>>()
    val forecast5Day: LiveData<List<Forecast>> get() = forecast5DayPrivate

    private val reverseGeocodingPrivate = MutableLiveData<ReverseGeocoding>()
    val reverseGeocoding: LiveData<ReverseGeocoding> get() = reverseGeocodingPrivate

    private val directGeoPrivate = MutableLiveData<List<DirectGeoItem>>()
    val directGeo: LiveData<List<DirectGeoItem>> get() = directGeoPrivate

    private val openSearchDialogPrivate = MutableLiveData(false)

    val openSearchDialog: LiveData<Boolean> = openSearchDialogPrivate

    private val cityTextForSearchPrivate = MutableLiveData("")
    val cityTextForSearch : LiveData<String> = cityTextForSearchPrivate

    /**
     * Error handling as UI
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showSnackBarPrivate = MutableLiveData("")
    val showSnackBar: LiveData<String> get() = showSnackBarPrivate

    fun getCurrentlyData(latitude:Double = 25.034645232960177, longitude:Double = 121.56117851104277){
        viewModelScope.launch {
            dataRepository.requestCurrentWeather(LocationRequest(latitude = latitude, longitude = longitude)).collect {
                processData(it) {
                    currentlyDataPrivate.value = it
                }
            }
        }
    }

    fun getForecastData(latitude:Double = 25.034645232960177, longitude:Double = 121.56117851104277){
        viewModelScope.launch {
            dataRepository.requestForecastWeather(LocationRequest(latitude = latitude, longitude = longitude)).collect {
                processData(it) {
                    get24Hour(it.data!!)
                    get5Day(it.data)
                }
            }
        }
    }

    fun getReverseGeocoding(latitude:Double = 25.034645232960177, longitude:Double = 121.56117851104277){
        viewModelScope.launch {
            dataRepository.requestReverseGeocoding(LocationRequest(latitude = latitude, longitude = longitude)).collect {
                processData(it) {
                    reverseGeocodingPrivate.value = it.data!!
                }
            }
        }
    }

    fun getDirectGeo(query: String){
        viewModelScope.launch {
            dataRepository.requestDirectGeo(GeoRequest(query = query)).collect {
                processData(it) {
                    directGeoPrivate.value = it.data!!
                }
            }
        }
    }

    fun switchSearchDialog(){
        openSearchDialogPrivate.value = openSearchDialogPrivate.value == false
    }

    private fun get24Hour(forecast: WeatherForecast){
        //取前8組數據共24小時
        forecast24HPrivate.value = forecast.list.subList(0,8)
    }

    private fun get5Day(forecast: WeatherForecast){
        //取得GMT+8是11點的第一筆數據當作起始點, 用來查找每日11點的數據
        val startIndex = forecast.list.filterIndexed { _, item ->
            getHour(item.dt.toLong()) == 8
        }.map { forecast.list.indexOf(it) }.first()
        //每8組數據取第1組當代表, 免費api共可取得5筆
        forecast5DayPrivate.value = forecast.list.filterIndexed { index, _ ->
            index % 8 == startIndex
        }
    }

    fun setSearchText(text: String){
        cityTextForSearchPrivate.value = text
    }

    private fun processData(resource: Resource<*>, action: ()->Unit){
        when (resource){
            is Resource.Success -> {
                action.invoke()
            }
            is Resource.DataError -> {
                val error = errorManager.getError(resource.errorCode!!)
                showSnackBarPrivate.value = String.format(error.description, resource.errorCode)
            }
            is Resource.Loading -> {}
        }
    }

    fun closeSnackBar(){
        showSnackBarPrivate.value = ""
    }

}