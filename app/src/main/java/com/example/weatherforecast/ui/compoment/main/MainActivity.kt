package com.example.weatherforecast.ui.compoment.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import com.example.weatherforecast.databinding.ActivityMainBinding
import com.example.weatherforecast.data.Resource
import com.example.weatherforecast.dto.WeatherData
import com.example.weatherforecast.ui.base.BaseActivity
import com.example.weatherforecast.utils.SingleEvent
import com.example.weatherforecast.utils.observe
import com.example.weatherforecast.utils.setupSnackbar
import com.example.weatherforecast.utils.showToast
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getWeatherRawData()
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
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun observeSnackBarMessages(event: LiveData<SingleEvent<Any>>) {
        binding.root.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }
}