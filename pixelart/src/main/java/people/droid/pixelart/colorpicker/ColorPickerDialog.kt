package people.droid.pixelart.colorpicker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import people.droid.pixelart.ui.theme.PixelArtMakerTheme
import people.droid.pixelart.ui.theme.dialogBackgroundColor

/*
    출처
    https://github.com/V-Abhilash-1999/ComposeColorPicker
 */

@Composable
fun ColorPickerDialog(
    showMenu: Boolean = false,
    currentColor: Color = Color.Blue,
    onSelectedColor: (Color) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    val initialSatValOffset = remember { mutableStateOf(Offset.Zero) }
    val initialHueOffset = remember { mutableStateOf(Offset.Zero) }

    if (!showMenu) return

    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(dialogBackgroundColor)
                .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            val hsv = remember {
                val hsv = floatArrayOf(0f, 0f, 0f)
                android.graphics.Color.colorToHSV(currentColor.toArgb(), hsv)

                mutableStateOf(
                    Triple(hsv[0], hsv[1], hsv[2])
                )
            }

            val backgroundColor = remember(hsv.value) {
                mutableStateOf(Color.hsv(hsv.value.first, hsv.value.second, hsv.value.third))
            }

            val canvasWidth = getCanvasSize()

            SatValPanel(hue = hsv.value.first, canvasWidth = canvasWidth, initialOffset = initialSatValOffset.value) { sat, value, offset ->
                hsv.value = Triple(hsv.value.first, sat, value)
                initialSatValOffset.value = offset
            }

            Spacer(modifier = Modifier.height(16.dp))

            HueBar(canvasWidth= canvasWidth, initialOffset = initialHueOffset.value) { hue, offset ->
                hsv.value = Triple(hue, hsv.value.second, hsv.value.third)
                initialHueOffset.value = offset
            }

            Spacer(modifier = Modifier.height(16.dp))

            ElevatedButton(
                colors = ButtonDefaults.elevatedButtonColors(
                    contentColor = backgroundColor.value.getNegativeColor(),
                    containerColor = backgroundColor.value,
                ),
                border = BorderStroke(2.dp, Color.Black),
                onClick = {
                    onSelectedColor(backgroundColor.value)
                }
            ) {
                Icon(Icons.Rounded.Check, contentDescription = null)
            }
        }
    }
}

@Composable
private fun getCanvasSize(): Dp {
    val context = LocalContext.current
    val density = context.resources.displayMetrics.density
    val widthPixels = context.resources.displayMetrics.widthPixels
    val canvasWidth = (widthPixels / density).dp - 64.dp
    return canvasWidth
}

@Preview(showBackground = true, widthDp = 400, heightDp = 800)
@Composable
fun ColorPickerPreview() {
    PixelArtMakerTheme {
        ColorPickerDialog(showMenu = true)
    }
}
