package people.droid.roulette.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import people.droid.roulette.domain.model.RouletteItem
import people.droid.roulette.ui.model.RouletteState


@Composable
fun RouletteSlice(
    modifier: Modifier,
    size: Dp,
    color: Color,
    degree: Float,
    contentModifier: Modifier,
    state: RouletteState,
    text:String,
    onTextChanged:(String) -> Unit,
    item:RouletteItem,
    isFocused:Boolean,
    onFocusChanged:(Boolean)->Unit
) {

    Box(
        modifier = modifier.size(size)
    ) {
        Canvas(
            modifier = Modifier.size(size)
        ) {
            drawArc(
                color = color,
                startAngle = -90f - (degree / 2),
                sweepAngle = degree,
                useCenter = true,
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = size / 2)
                .then(contentModifier)
        ) {
            if (state == RouletteState.SETTING){
                RouletteItemTextField(text = text, isFocused = isFocused, onFocusChanged = onFocusChanged) {
                    onTextChanged(it)
                }
            }else {
                Text(
                    text = item.value,
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
                    fontSize = 24.sp
                )
            }
        }

    }
}