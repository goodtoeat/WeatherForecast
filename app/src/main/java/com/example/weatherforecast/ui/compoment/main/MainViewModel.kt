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
import com.example.weatherforecast.utils.getEveryDayForecast
import com.example.weatherforecast.utils.getStartIndexOf5Day
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRepository: DataRepositorySource
): BaseViewModel() {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)

    /**
     * 當前天氣
     */
    private val currentlyDataPrivate = MutableLiveData<Resource<WeatherCurrently>>()
    val currentlyData: LiveData<Resource<WeatherCurrently>> get() = currentlyDataPrivate

    /**
     * 24小時天氣 from 5天3小時數據
     */
    private val forecast24HPrivate = MutableLiveData<List<Forecast>>()
    val forecast24H: LiveData<List<Forecast>> get() = forecast24HPrivate

    /**
     * 5日天氣 from 5天3小時數據
     */
    private val forecast5DayPrivate = MutableLiveData<List<Forecast>>()
    val forecast5Day: LiveData<List<Forecast>> get() = forecast5DayPrivate

    /**
     * 由經緯度取得的城市名稱
     */
    private val reverseGeocodingPrivate = MutableLiveData<ReverseGeocoding>()
    val reverseGeocoding: LiveData<ReverseGeocoding> get() = reverseGeocodingPrivate

    /**
     * 由搜尋字串獲得的城市名稱集合
     */
    private val directGeoPrivate = MutableLiveData<List<DirectGeoItem>>()
    val directGeo: LiveData<List<DirectGeoItem>> get() = directGeoPrivate

    /**
     * 判斷是否開啟搜尋功能
     */
    private val openSearchDialogPrivate = MutableLiveData(false)

    val openSearchDialog: LiveData<Boolean> = openSearchDialogPrivate

    /**
     * 搜尋關鍵字
     */
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
        //取得GMT+8是八點的第一筆數據當作起始點, 用來查找每日11點的數據
        val startIndex = getStartIndexOf5Day(forecast)
        //每8組數據取第1組當代表, 免費api共可取得5筆
        forecast5DayPrivate.value = getEveryDayForecast(forecast, startIndex)
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