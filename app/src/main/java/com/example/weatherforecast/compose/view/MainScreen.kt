package com.example.weatherforecast.compose.view

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherforecast.compose.theme.Guest
import com.example.weatherforecast.compose.theme.SkyBlue
import com.example.weatherforecast.compose.widget.CustomToast
import com.example.weatherforecast.ui.compoment.main.MainViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(Guest)
        systemUiController.setNavigationBarColor(SkyBlue)
    }

    val openSearchDialog = viewModel.openSearchDialog.observeAsState()
    val showToast = viewModel.showSnackBar.observeAsState()
    var gradientOffset by remember { mutableStateOf(400f) }
    val colorAnimation = rememberInfiniteTransition(label = "")
    val colorOffset by colorAnimation.animateFloat(
        initialValue = 350f,
        targetValue = 600f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
//            .background(color = MaterialTheme.colors.background)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Guest, SkyBlue),
                        start = Offset(0f, gradientOffset),
                        end = Offset(0f, gradientOffset + colorOffset) // 调整渐变的方向和高度
                    )
                )
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CurrentScreen()
            Spacer(modifier = Modifier.height(20.dp))
            HourScreen()
            Spacer(modifier = Modifier.height(20.dp))
            DailyScreen()
        }

        if (openSearchDialog.value == true) {
            SearchDialog()
        }

        if (showToast.value != null && showToast.value != "") {
            CustomToast(
                message = showToast.value as String,
                onDismiss = { viewModel.closeSnackBar() }
            )
        }
    }
}