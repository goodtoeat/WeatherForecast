package com.example.weatherforecast.utils

fun convertFahrenheitToCelsius(fahrenheit: Double?): String{
    fahrenheit?.let {
        val celsius =  (fahrenheit - 32)*5/9
        return  String.format("%.1f", celsius)
    }
    return "0"
}

fun tempFormat(temp: Double?): String{
    return String.format("%.0f", temp)
}