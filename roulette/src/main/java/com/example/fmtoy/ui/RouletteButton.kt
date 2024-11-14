package com.example.fmtoy.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fmtoy.ui.theme.MyBrown

@Composable
fun RouletteButton(modifier: Modifier = Modifier, onClick: () -> Unit, text: String, enabled:Boolean = true) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MyBrown
        ),
        onClick = onClick,
        enabled = enabled
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = Color.White
        )
    }
}