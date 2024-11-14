package people.droid.pixelart.colorpicker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.ColorPalette(
    colorQueue: ArrayDeque<Color>,
    selectedColor: MutableState<Color>,
    showPopupMenu: MutableState<Boolean>
) {
    val selectedPaletteIndex = remember { mutableIntStateOf(-1) }
    val context = LocalContext.current
    val screenWidth =
        context.resources.displayMetrics.widthPixels / context.resources.displayMetrics.density
    val paletteWidth = (screenWidth * (3f / 4)) / 9

    // Color Palette 9x2
    Row(
        Modifier.Companion
            .align(Alignment.BottomCenter)
            .padding(8.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .background(Color.Black)
                .padding(2.dp)
        ) {
            for (i in 0 until 2) {
                Row {
                    for (j in 0 until 9) {
                        val elementColor = colorQueue.elementAtOrNull(i * 9 + j) ?: Color.White
                        val selectedBorderColor =
                            if (selectedPaletteIndex.intValue == i * 9 + j) Color.White else Color.Black
                        Box(
                            Modifier
                                .size(paletteWidth.dp)
                                .background(elementColor)
                                .border(1.dp, selectedBorderColor)
                                .clickable {
                                    selectedColor.value = elementColor
                                    selectedPaletteIndex.intValue = i * 9 + j
                                }
                        )
                    }
                }
            }
        }

        Spacer(Modifier.width(8.dp))

        Box(
            Modifier
                .size((paletteWidth * 2).dp)
                .clip(CircleShape)
                .background(selectedColor.value)
                .border(2.dp, Color.Black, CircleShape)
                .clickable {
                    showPopupMenu.value = !showPopupMenu.value
                }
        )
    }
}