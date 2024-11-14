package people.droid.roulette.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RouletteItemTextField(text:String, onValueChange: (String) -> Unit) {

    BasicTextField(
        value = text,
        onValueChange = onValueChange,
    ) {
        Box(
            Modifier
                .defaultMinSize(minWidth = 40.dp)
                .drawBehind {
                    drawRoundRect(
                        color = Color.White,
                        style = Stroke(
                            width = 6f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                        )
                    )
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