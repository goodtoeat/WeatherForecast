package com.example.weatherforecast.compose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.compose.theme.Guest
import com.example.weatherforecast.compose.theme.SkyBlue
import com.example.weatherforecast.ui.compoment.main.MainViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun MainScreen() {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(Guest)
        systemUiController.setNavigationBarColor(SkyBlue)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
//            .background(color = MaterialTheme.colors.background)
            .background(brush = Brush.linearGradient(
                colors = listOf(Guest, SkyBlue),
                start = Offset(0f, 0f),
                end = Offset(0f, 1200f) // 调整渐变的方向和高度
            ))
            .padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 當前溫度
        CurrentScreen()
        Spacer(modifier = Modifier.height(40.dp))
        HourScreen()
        Spacer(modifier = Modifier.height(20.dp))
        DailyScreen()
    }
}