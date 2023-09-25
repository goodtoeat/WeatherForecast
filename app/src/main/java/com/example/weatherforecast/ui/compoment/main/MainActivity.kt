package com.example.weatherforecast.ui.compoment.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Bundle
import androidx.activity.viewModels
import com.example.weatherforecast.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import com.example.weatherforecast.compose.theme.WeatherTheme
import com.example.weatherforecast.compose.view.MainScreen
import com.google.android.gms.location.LocationServices


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                    getLocation(this@MainActivity)
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                    getLocation(this@MainActivity)
                }
                else -> {
                    // No location access granted.
                    // 預設 EA 辦公室座標天氣
                    viewModel.getCurrentlyData()
                    viewModel.getForecastData()
                    viewModel.getReverseGeocoding()
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ))

        setContent {
            WeatherTheme(){
                MainScreen()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocation(context: Context){
        var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    viewModel.getCurrentlyData(latitude, longitude)
                    viewModel.getForecastData(latitude, longitude)
                    viewModel.getReverseGeocoding(latitude, longitude)
                }
            }.addOnFailureListener{
                viewModel.getCurrentlyData()
                viewModel.getForecastData()
                viewModel.getReverseGeocoding()
            }
    }
}