package people.droid.pixelart

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Picture
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import people.droid.pixelart.colorpicker.ColorPalette
import people.droid.pixelart.colorpicker.ColorPickerDialog
import people.droid.pixelart.controller.PixelSizeController
import people.droid.pixelart.ui.ScaffoldWithBox
import people.droid.pixelart.ui.theme.PixelArtMakerTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PixelArtScreen(
    colorQueue: ArrayDeque<Color>,
    gridMap: SnapshotStateList<MutableList<Color>>,
    pixelSizeFlow: MutableStateFlow<Int> = MutableStateFlow(20),
    onChangePixelBoardSize: (PixelBoardActions) -> Unit = {},
    onClearPixels: () -> Unit = {},
    onSharePixelImage: (Bitmap) -> Unit = {},
    onClose: () -> Unit = {}
) {
    val showClearDialog = remember { mutableStateOf(false) }
    val selectedColor = remember { mutableStateOf(Color.White) }
    val showPopupMenu = remember { mutableStateOf(false) }
    val picture = remember { Picture() }
    val context = LocalContext.current
    val screenWidth =
        context.resources.displayMetrics.widthPixels / context.resources.displayMetrics.density
    val boardSizeDp = (screenWidth - 40).dp

    val pixelSize = pixelSizeFlow.collectAsState()

    ScaffoldWithBox {
        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(16.dp).size(30.dp).clickable { onClose() })

        Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Pixel", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.secondary)
            Text("by boring-km", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.secondary)
            Box(
                Modifier
                    .width(boardSizeDp)
                    .height(boardSizeDp + 100.dp)) {
                Column(
                    Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        Modifier
                            .border(
                                1.dp,
                                Color.LightGray.copy(alpha = 0.5f),
                                RoundedCornerShape(8.dp)
                            )
                            .size(boardSizeDp)
                    ) {
                        PixelCanvas(
                            boardSizeDp, pixelSize, gridMap, picture
                        ) { i, j ->
                            gridMap[i] = gridMap[i]
                                .toMutableList()
                                .apply { this[j] = selectedColor.value }
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Row {
                        ClearButton {
                            showClearDialog.value = true
                        }
                        ShareButton(picture, onSharePixelImage)
                    }
                }
            }
            PixelSizeController(pixelSize.value, onChangePixelBoardSize)
        }

        ColorPalette(colorQueue, selectedColor, showPopupMenu)
    }

    ColorPickerDialog(
        showPopupMenu.value,
        currentColor = selectedColor.value,
        onSelectedColor = { color ->
            selectedColor.value = color
            showPopupMenu.value = false
            colorQueue.addFirst(color)
        },
        onDismiss = {
            showPopupMenu.value = false
        }
    )

    PixelClearCheckDialog(showClearDialog, onClearPixels)
}



@Composable
private fun ShareButton(
    picture: Picture,
    onSharePixelImage: (Bitmap) -> Unit
) {
    Box(modifier = Modifier
        .padding(vertical = 4.dp, horizontal = 4.dp)
        .clickable {
            val bitmap = makeBitmap(picture)
            onSharePixelImage(bitmap)
        }) {
        Icon(
            Icons.Rounded.Share,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
private fun ClearButton(onClearPixels: () -> Unit) {
    Box(modifier = Modifier
        .padding(vertical = 4.dp, horizontal = 4.dp)
        .clickable {
            onClearPixels()
        }) {
        Icon(
            Icons.Rounded.Delete,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

private fun makeBitmap(picture: Picture): Bitmap {
    val bitmap = Bitmap.createBitmap(
        picture.width,
        picture.height,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    canvas.drawColor(android.graphics.Color.WHITE)
    canvas.drawPicture(picture)
    return bitmap
}



@Preview(showBackground = true, widthDp = 400)
@Composable
fun MainScreenLightPreview() {
    PixelArtMakerTheme(darkTheme = false) {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val gridMap = remember { mutableStateListOf<MutableList<Color>>() }
            for (i in 0 until 16) {
                val row = mutableListOf<Color>()
                for (j in 0 until 16) {
                    row.add(Color.White)
                }
                gridMap.add(row)
            }
            PixelArtScreen(
                ArrayDeque(List(18) { Color.White }),
                gridMap,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 400)
@Composable
fun MainScreenDarkPreview() {
    PixelArtMakerTheme(darkTheme = true) {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val gridMap = remember { mutableStateListOf<MutableList<Color>>() }
            for (i in 0 until 16) {
                val row = mutableListOf<Color>()
                for (j in 0 until 16) {
                    row.add(Color.White)
                }
                gridMap.add(row)
            }
            PixelArtScreen(
                ArrayDeque(List(18) { Color.White }),
                gridMap,
            )
        }
    }
}


