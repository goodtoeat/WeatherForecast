package com.example.weatherforecast.compose.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


    @Preview(showBackground = true)
    @Composable
    fun ChipPreview() {
        MyChip(Color.Cyan){
            Text(
                text = "Dog",
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Left,
                color = Color.White
            )
        }
    }

    @Composable
    fun MyChip(backgroundColor: Color, content: @Composable BoxScope.()-> Unit) {
        Surface(
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(8.dp)
                .size(width = 60.dp, height = 30.dp)
        ) {
            Box(
                Modifier.fillMaxSize()
                    .background(backgroundColor)
            ) {
                content()
            }
        }
    }
