package com.example.weatherforecast.compose.widget

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BoxScope.CustomToast(
    message: String,
    onDismiss: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    // 创建一个自定义Snackbar
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.align(Alignment.BottomCenter)
    ) {
        Snackbar(
            action = {
                TextButton(
                    onClick = {
                        onDismiss()
                        snackbarHostState.currentSnackbarData?.dismiss()
                    }
                ) {
                    Text("關閉", color = Color.White)
                }
            },
            content = {
                Text(text = message)
            }
        )
    }

    // 显示Snackbar
    LaunchedEffect(message) {
        snackbarHostState.showSnackbar(
            message = message,
            actionLabel = null,
            duration = SnackbarDuration.Indefinite
        )
    }
}