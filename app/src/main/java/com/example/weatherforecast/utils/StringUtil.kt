package com.example.weatherforecast.utils

/**
 * 輸入的數字一律轉換為整數的字串
 */
fun removeFloat(temp: Double?): String{
    return String.format("%.0f", temp)
}