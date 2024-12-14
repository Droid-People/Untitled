package people.droid.roulette.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RouletteItemTextField(text: String, onValueChange: (String) -> Unit) {

    var textFieldValueState by remember(text) {
        mutableStateOf(TextFieldValue(text = text, selection = TextRange(text.length)))
    }
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        value = textFieldValueState,
        onValueChange = {
            onValueChange(it.text)
            textFieldValueState = it
        },
        modifier = Modifier.onFocusChanged {
            isFocused = it.isFocused
        }
    ) {
        Box(
            Modifier
                .defaultMinSize(minWidth = 40.dp)
                .drawBehind {
                    if (isFocused){
                        drawRoundRect(
                            color = Color.White.copy(alpha = 0.3f),
                            style = Fill,
                        )
                        drawRoundRect(
                            color = Color.White,
                            style = Stroke(
                                width = 6f,
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                            ),
                        )
                    } else{
                        drawRoundRect(
                            color = Color.White,
                            style = Stroke(
                                width = 6f,
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                            ),
                        )
                    }

                }
        ) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
                fontSize = 24.sp
            )
        }
    }
}