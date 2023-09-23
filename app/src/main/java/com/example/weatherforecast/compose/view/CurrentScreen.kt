package com.example.weatherforecast.compose.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.weatherforecast.IMG_URL
import com.example.weatherforecast.ui.compoment.main.MainViewModel
import com.example.weatherforecast.utils.removeFloat

@Composable
fun CurrentScreen(viewModel: MainViewModel = hiltViewModel()){
    val currentlyData = viewModel.currentlyData.observeAsState()

    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("${String.format(IMG_URL, currentlyData.value?.data?.weather?.get(0)?.icon)}")
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.height(120.dp)
        )
        Text(
            modifier = Modifier,
            text = "${removeFloat(currentlyData.value?.data?.main?.temp)}°C",
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.secondary
        )
    }
    // 天氣狀況和感覺溫度
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "${currentlyData.value?.data?.weather?.get(0)?.description}",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.secondary)
        Text(text = "  |  ",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.secondary)
        Text(text = "體感溫度 ${removeFloat(currentlyData.value?.data?.main?.feels_like)}°C",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.secondary
        )
    }

    Spacer(modifier = Modifier.height(10.dp))

    // 風速和溫度範圍
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "風 ${removeFloat(currentlyData.value?.data?.wind?.speed)} 公里/小時",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.secondary,
        )
        Text(text = "  |  ",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.secondary,
        )
        Text(text = "${removeFloat(currentlyData.value?.data?.main?.temp_max)}°C / ${removeFloat(currentlyData.value?.data?.main?.temp_min)}°C",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.secondary,
        )
    }
}