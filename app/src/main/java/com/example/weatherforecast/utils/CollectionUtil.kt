package com.example.weatherforecast.utils

import Forecast
import WeatherForecast

/**
 * 輸入五天的天氣預報資料
 * 返回第一筆早上8點資料對應的index
 */
fun getStartIndexOf5Day(forecast: WeatherForecast): Int {
    return forecast.list.filterIndexed { _, item ->
        getHour(item.dt.toLong()) == 8
    }.map { forecast.list.indexOf(it) }.first()
}

/**
 * 輸入五天的天氣預報資料, 輸入startIndex
 * 每3小時一筆, 每天有8筆
 * 返回共5日的每日預報集合
 */
fun getEveryDayForecast(forecast: WeatherForecast, startIndex: Int): List<Forecast>{
    return forecast.list.filterIndexed { index, _ ->
        index % 8 == startIndex
    }
}

