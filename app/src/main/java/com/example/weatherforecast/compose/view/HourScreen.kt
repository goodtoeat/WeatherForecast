package com.example.weatherforecast.compose.view

import Forecast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.weatherforecast.IMG_URL
import com.example.weatherforecast.compose.theme.TransparentGrayDark
import com.example.weatherforecast.ui.compoment.main.MainViewModel
import com.example.weatherforecast.utils.get12Hour
import com.example.weatherforecast.utils.removeFloat

@Composable
fun HourScreen(viewModel: MainViewModel = hiltViewModel()) {

    val forecast24H = viewModel.forecast24H.observeAsState()

    Box(
        modifier = Modifier
            .background(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colors.surface.copy(alpha = 0.2f) // 背景颜色半透明
            ).padding(all = 16.dp)
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
        ) {
            forecast24H.value?.let {
                items(forecast24H.value!!) {
                    HourItem(forecast = it)
                }
            }
        }
    }
}

@Composable
fun HourItem(forecast: Forecast){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "${removeFloat(forecast.main.temp)}°C",
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.secondary,
            textAlign = TextAlign.Center
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(String.format(IMG_URL, forecast.weather?.get(0)?.icon))
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.height(60.dp)
        )
        Text(text = get12Hour(forecast.dt.toLong()),
            style = MaterialTheme.typography.body2,
            color = TransparentGrayDark,
            textAlign = TextAlign.Center
        )
    }

}