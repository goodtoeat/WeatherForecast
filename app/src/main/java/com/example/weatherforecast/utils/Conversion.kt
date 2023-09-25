package com.example.weatherforecast.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun removeFloat(temp: Double?): String{
    return String.format("%.0f", temp)
}

fun get12Hour(timestamp: Long): String{
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp * 1000
    val amPmFormat = SimpleDateFormat("a", Locale.getDefault())
    val hourFormat = SimpleDateFormat("hh", Locale.getDefault())
    val amPm = amPmFormat.format(calendar.time)
    val hour = hourFormat.format(calendar.time)
    return "${amPm}${hour}時"
}

fun getDate(timestamp: Long): String{
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp * 1000
    val dateFormat = SimpleDateFormat("MM / dd", Locale.getDefault())
    return dateFormat.format(calendar.time)
}

fun getWeek(timestamp: Long): String{
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp * 1000
    val dateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    return dateFormat.format(calendar.time)
}

fun getHour(timestamp: Long): Int{
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp * 1000
    return calendar.get(Calendar.HOUR_OF_DAY)
}