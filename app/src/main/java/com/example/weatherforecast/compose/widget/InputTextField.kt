package com.example.weatherforecast.compose.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherforecast.compose.theme.*
import com.example.weatherforecast.ui.compoment.main.MainViewModel

@Composable
fun input(hint: String, _enable: Boolean = true, _isError: Boolean = false, viewModel: MainViewModel = hiltViewModel()){
    var isFocus by remember { mutableStateOf(false)}
    val enable by remember { mutableStateOf(_enable)}
    val isError by remember { mutableStateOf(_isError)}
    val passwordVisibility: Boolean by remember { mutableStateOf(true) }
    val text = viewModel.cityTextForSearch.observeAsState()
    var backgroundColor = TransparentGrayQuarternary
    if (viewModel.cityTextForSearch.value!="") backgroundColor = Color.Transparent
    if (!enable) backgroundColor = TransparentGrayTertiary
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester) // 分配 FocusRequester
            .onFocusChanged { isFocus = it.isFocused },

        shape =  RoundedCornerShape(12.dp),
        textStyle = MaterialTheme.typography.h3,
        value = text.value!!,
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
        onValueChange = {
            viewModel.setSearchText(it) },
        label = { Text(text = hint, style = MaterialTheme.typography.h4)},
        singleLine = true,
        enabled = enable,
        isError = isError,
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { viewModel.getDirectGeo(text.value!!) }) {
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
                keyboardController?.hide() // 隐藏键盘
                viewModel.getDirectGeo(text.value!!)
            }
        ),
    )

    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
        onDispose { }
    }
}