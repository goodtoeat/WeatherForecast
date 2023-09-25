package com.example.weatherforecast.compose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherforecast.R
import com.example.weatherforecast.compose.theme.TransparentDark
import com.example.weatherforecast.compose.widget.input
import com.example.weatherforecast.dto.DirectGeoItem
import com.example.weatherforecast.ui.compoment.main.MainViewModel


@Composable
fun SearchDialog(viewModel: MainViewModel = hiltViewModel()) {
    val openDialog = remember { mutableStateOf(true)  }
    val directGeo = viewModel.directGeo.observeAsState()

    if (openDialog.value) {
        Dialog(
            onDismissRequest = {
                viewModel.switchSearchDialog()
            },
            content = {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(color = TransparentDark, shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 16.dp)){
                    Column {
                        Spacer(modifier = Modifier.height(10.dp))
                        input("輸入城市")
                        Spacer(modifier = Modifier.height(10.dp))
                        LazyColumn{
                            directGeo.value?.let {
                                if (directGeo.value!!.isEmpty()) {
                                    item {
                                        EmptyItem()
                                    }
                                } else {
                                    items(directGeo.value!!) {
                                        SearchItem(it)
                                    }
                                }
                            }
                        }
                    }
                }

            },

        )
    }
}

@Composable
fun SearchItem(directGeoItem: DirectGeoItem, viewModel: MainViewModel = hiltViewModel()){
    TextButton(
        onClick = {
            viewModel.getCurrentlyData(latitude = directGeoItem.lat, longitude = directGeoItem.lon)
            viewModel.getForecastData(latitude = directGeoItem.lat, longitude = directGeoItem.lon)
            viewModel.getReverseGeocoding(latitude = directGeoItem.lat, longitude = directGeoItem.lon)
            viewModel.switchSearchDialog()
        }) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
            textAlign = TextAlign.Left,
            text = "${directGeoItem.local_names?.zh?:directGeoItem.name} ${directGeoItem.state?:""}",
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.secondary,
        )
    }
}

@Composable
fun EmptyItem(){
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
        textAlign = TextAlign.Left,
        text = stringResource(R.string.no_result),
        style = MaterialTheme.typography.h5,
        color = MaterialTheme.colors.secondary,
    )
}


