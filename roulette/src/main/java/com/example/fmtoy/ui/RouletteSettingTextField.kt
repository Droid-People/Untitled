package com.example.fmtoy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.fmtoy.ui.theme.MyBrown
import com.example.fmtoy.ui.theme.TextFieldBackground

@Composable
fun RouletteSettingTextField(
    modifier: Modifier = Modifier,
    number: Int,
    onValueChange: (Int) -> Unit,
) {
    val isFocused = remember { mutableStateOf(false) }
    BasicTextField(
        modifier = modifier
            .onFocusChanged {
                isFocused.value = it.isFocused
            },
        value = number.toString(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = {
            if (it.isNotEmpty()) {
                val lastNumber = it.last().code - 48
                if (lastNumber in 2..9) onValueChange(lastNumber)
            } else onValueChange(number)

        }) {
        RouletteTextFieldCharContainer(
            text = number.toString(),
            isFocused.value
        )
    }
}


@Composable
fun RouletteTextFieldCharContainer(text: String, isFocused: Boolean) {
    Box(
        modifier = Modifier
            .size(
                width = 29.dp,
                height = 40.dp
            )
            .background(
                color = TextFieldBackground,
                shape = RoundedCornerShape(4.dp)
            )
            .run {
                if (isFocused) {
                    border(
                        width = 1.5.dp,
                        color = MyBrown,
                        shape = RoundedCornerShape(4.dp)
                    )
                } else this
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall.copy(
                color = MyBrown
            )
        )
    }
}