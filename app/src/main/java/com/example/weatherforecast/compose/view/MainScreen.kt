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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
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
    val density = LocalDensity.current.density

    val animation = rememberInfiniteTransition(label = "")
    //背景顏色動畫offset
    val colorOffset by animation.animateFloat(
        initialValue = 350f,
        targetValue = 600f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    //發光體動畫用 offset
    val sumOffset by animation.animateFloat(
        initialValue = 70f,
        targetValue = 150f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box (modifier = Modifier
        .fillMaxSize()
        .background(
            //背景的漸層藍色
            brush = Brush.linearGradient(
                colors = listOf(Guest, SkyBlue),
                start = Offset(0f, 400f),
                end = Offset(0f, 400f + colorOffset) // 调整渐变的方向和高度
            )
        )
    ){
        //發光體,月亮或太陽
        Box(
            modifier = Modifier
                .offset(x = -1 * sumOffset.dp/2 + 20.dp, y = -1 * sumOffset.dp/2 + 20.dp)
                .size(sumOffset.dp)
                .background(shape = RoundedCornerShape(sumOffset.dp),
                    brush = Brush.radialGradient(
                    colors = listOf(Color(0xFFF3F80A), Color(0x20F3F80A)),
                    center = Offset(density*(sumOffset.dp).value/2, density*(sumOffset.dp).value/2),
                    radius = density*(sumOffset.dp).value/2
                ))

        ) {}
        Column(modifier = Modifier
            .fillMaxSize()
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