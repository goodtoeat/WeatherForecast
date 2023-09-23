package com.example.weatherforecast.ui.compoment.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import com.example.weatherforecast.data.Resource
import com.example.weatherforecast.dto.WeatherData
import com.example.weatherforecast.ui.base.BaseActivity
import com.example.weatherforecast.utils.SingleEvent
import com.example.weatherforecast.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.compose.setContent
import com.example.weatherforecast.compose.theme.WeatherTheme
import com.example.weatherforecast.compose.view.MainScreen


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherTheme{
                MainScreen()
            }
        }
        viewModel.getWeatherData()
    }

    override fun observeViewModel() {
        observe(viewModel.weatherData, ::refreshView)
        observeToast(viewModel.showToast)
        observeSnackBarMessages(viewModel.showSnackBar)
    }

    private fun refreshView(states: Resource<WeatherData>){
        when (states){
            is Resource.Success -> {

            }
            is Resource.DataError -> {
                states.errorCode?.let { viewModel.showSnackBarMessage(it) }
            }

            else -> {}
        }
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
//        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun observeSnackBarMessages(event: LiveData<SingleEvent<Any>>) {
//        binding.root.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }
}