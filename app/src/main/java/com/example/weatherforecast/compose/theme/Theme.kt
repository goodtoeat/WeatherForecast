package com.example.weatherforecast.compose.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun WeatherTheme(content: @Composable () -> Unit) {
    val lightBlue = Color.Blue
    val darkBlue = Guest
    val lightGray = Color.LightGray

    MaterialTheme(
        colors = lightColors(
            primary = lightBlue,
            primaryVariant = darkBlue,
            onPrimary = lightGray
        ),
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

private fun lightColors(
    primary: Color,
    primaryVariant: Color,
    onPrimary: Color
): Colors {
    return Colors(
        primary = primary,
        primaryVariant = primaryVariant,
        onPrimary = onPrimary,
        secondary = Color.White,
        secondaryVariant = Color.White,
        onSecondary = Color.Black,
        surface = Color.White,
        onSurface = Color.Black,
        background = SkyBlue,
        onBackground = SkyBlueAccent,
        error = Color.Red,
        onError = Color.White,
        isLight = true
    )
}