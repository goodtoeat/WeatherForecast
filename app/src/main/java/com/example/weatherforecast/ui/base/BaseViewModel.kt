package com.example.weatherforecast.ui.base

import androidx.lifecycle.ViewModel
import com.example.weatherforecast.usecase.errors.ErrorManager
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
    @Inject
    lateinit var errorManager: ErrorManager
}
