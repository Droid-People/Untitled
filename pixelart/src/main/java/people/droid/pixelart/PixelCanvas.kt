package people.droid.pixelart

import android.annotation.SuppressLint
import android.graphics.Picture
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

const val maxPixelCount = 160

@Composable
fun PixelCanvas(
    boardSizeDp: Dp = 400.dp,
    @SuppressLint("AutoboxingStateCreation") pixelSize: State<Int> = mutableStateOf(20),
    gridMap: SnapshotStateList<MutableList<Color>>,
    picture: Picture,
    callback: (Int, Int) -> Unit = { _, _ -> },
) {
    var pixels = maxPixelCount / pixelSize.value

    fun PointerInputScope.drawPixel(
        offsetX: Float,
        offsetY: Float,
        callback: (Int, Int) -> Unit
    ) {
        pixels = maxPixelCount / pixelSize.value

        val i = ((offsetY / size.height) * pixels).toInt()
        val j = ((offsetX / size.width) * pixels).toInt()

        Log.i("PixelCanvas", "i: $i, j: $j")

        if (i in 0 until pixels && j in 0 until pixels) {
            callback(i, j)
        }
    }

    Canvas(
        Modifier
            .size(boardSizeDp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .pointerInput(Unit) {
                detectTapGestures {
                    drawPixel(it.x, it.y) { a, b ->
                        callback(a, b)
                    }
                }
            }
            .pointerInput(Unit) {
                // drag color
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    val offset = change.previousPosition
                    val offsetX = offset.x + dragAmount.x
                    val offsetY = offset.y + dragAmount.y

                    drawPixel(offsetX, offsetY) { a, b ->
                        callback(a, b)
                    }
                }
            }
            .drawWithCache {
                val width = size.width.toInt()
                val height = size.height.toInt()
                onDrawWithContent {
                    val pictureCanvas = Canvas(
                        picture.beginRecording(width, height)
                    )
                    draw(this, this.layoutDirection, pictureCanvas, this.size) {
                        this@onDrawWithContent.drawContent()
                    }
                    picture.endRecording()
                    drawIntoCanvas { canvas ->
                        canvas.nativeCanvas.drawPicture(picture)
                    }
                }
            }
    ) {
        val cellWidth = size.width / pixels
        val cellHeight = size.height / pixels

        for (i in 0 until pixels) {
            for (j in 0 until pixels) {
                val x = j * cellWidth.toInt().toFloat()
                val y = i * cellHeight.toInt().toFloat()
                drawRect(
                    color = gridMap[i][j],
                    topLeft = Offset(x, y),
                    size = Size(cellWidth, cellHeight),
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PixelCanvasPreview() {
    val gridMap = remember { mutableStateListOf<MutableList<Color>>() }
    for (i in 0 until 16) {
        val row = mutableListOf<Color>()
        for (j in 0 until 16) {
            row.add(Color.White)
        }
        gridMap.add(row)
    }
    Box(Modifier.size(400.dp)) {
        PixelCanvas(gridMap = gridMap, picture = Picture())
    }
}