package com.example.weatherforecast.compose.view

import Forecast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.weatherforecast.IMG_URL
import com.example.weatherforecast.compose.theme.Guest
import com.example.weatherforecast.compose.theme.TransparentGrayDark
import com.example.weatherforecast.ui.compoment.main.MainViewModel
import com.example.weatherforecast.utils.getDate
import com.example.weatherforecast.utils.getWeek
import com.example.weatherforecast.utils.removeFloat

@Composable
fun DailyScreen(viewModel: MainViewModel = hiltViewModel()) {

    val forecast5Day = viewModel.forecast5Day.observeAsState()

    Box(
        modifier = Modifier
            .background(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colors.surface.copy(alpha = 0.2f) // 背景颜色半透明
            )
            .padding(start = 16.dp, end = 16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
        ) {
            forecast5Day.value?.let {
                items(forecast5Day.value!!){
                    DailyItem(forecast = it)
                }
            }
        }
    }


}

@Composable
fun DailyItem(forecast: Forecast){
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(
            modifier = Modifier.width(width = 75.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = getDate(forecast.dt.toLong()),
                style = MaterialTheme.typography.body2,
                color = TransparentGrayDark)
            Spacer(
                modifier = Modifier.height(3.dp),
            )
            Text(text = getWeek(forecast.dt.toLong()),
                style = MaterialTheme.typography.body2,
                color = TransparentGrayDark)
        }

        Spacer(
            modifier = Modifier.width(10.dp),
        )

        Box(modifier = Modifier.size(75.dp)){

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(String.format(IMG_URL, forecast.weather[0].icon))
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.height(75.dp)
            )

            Text(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset(y = (-18).dp),
                text = "${removeFloat(forecast.pop * 100)} %",
                style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.W700),
                color = Guest,
                textAlign = TextAlign.End,
            )
        }

        Spacer(
            modifier = Modifier.width(20.dp),
        )

        Text(
            modifier = Modifier.weight(1f),
            text = forecast.weather[0].description,
            style = MaterialTheme.typography.body2,
            color = TransparentGrayDark,
            textAlign = TextAlign.Center
        )



        Text(text = "${removeFloat(forecast.main.temp_min)}°C - ${removeFloat(forecast.main.temp_max)}°C",
            style = MaterialTheme.typography.body2,
            color = TransparentGrayDark,
        )
    }

}