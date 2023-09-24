package com.example.weatherforecast.compose.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherforecast.compose.theme.*
import com.example.weatherforecast.ui.compoment.main.MainViewModel

@Composable
fun input(text: MutableState<String>, hint: String, _enable: Boolean = true, _isError: Boolean = false, viewModel: MainViewModel = hiltViewModel()){
    var isFocus by remember { mutableStateOf(false)}
    var enable by remember { mutableStateOf(_enable)}
    var isError by remember { mutableStateOf(_isError)}
    var passwordVisibility: Boolean by remember { mutableStateOf(true) }

    var backgroundColor = TransparentGrayQuarternary
    if (text.value!="") backgroundColor = Color.Transparent
    if (!enable) backgroundColor = TransparentGrayTertiary
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { isFocus = it.isFocused },
        shape =  RoundedCornerShape(12.dp),
        textStyle = MaterialTheme.typography.h3,
        value = text.value,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = TextActive,
            backgroundColor = backgroundColor,
            disabledBorderColor = TransparentGrayTertiary,
            disabledPlaceholderColor = SolidGray400,
            focusedBorderColor = if (text.value=="") TextActive else SkyBlue,
            unfocusedBorderColor = if (text.value=="") TextActive else SkyBlue,
            focusedLabelColor = if (text.value=="") TextActive else SkyBlue,
            unfocusedLabelColor = if (text.value=="") TextActive else SkyBlue,
            cursorColor = SkyBlue,
            errorBorderColor = Negative,
            errorLabelColor = Negative,

        ),
        onValueChange = { text.value = it },
        label = { Text(text = hint, style = MaterialTheme.typography.h4)},
        singleLine = true,
        enabled = enable,
        isError = isError,
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { viewModel.getDirectGeo(text.value) }) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Default.Search,
                    contentDescription = "visible",
                    tint= if (text.value!="" || isFocus) TextActive else TextInactive
                )
            }
        },
        keyboardActions = KeyboardActions(
            onDone = {
                // 处理回车键被按下时的操作
                keyboardController?.hide() // 隐藏键盘
                viewModel.getDirectGeo(text.value)
            }
        ),
    )
}