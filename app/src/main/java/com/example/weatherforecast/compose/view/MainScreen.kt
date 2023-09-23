package com.example.weatherforecast.compose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.weatherforecast.compose.theme.SkyBlue
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun MainScreen() {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(SkyBlue)
    }

    Column(
        modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 當前溫度
        CurrentScreen()
        HourScreen()
        DailyScreen()
    }
}