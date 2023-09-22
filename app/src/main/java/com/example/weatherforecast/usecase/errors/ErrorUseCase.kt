package com.example.weatherforecast.usecase.errors

import com.example.weatherforecast.error.Error

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}
