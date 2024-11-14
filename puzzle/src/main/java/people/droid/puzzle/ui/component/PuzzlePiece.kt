package people.droid.puzzle.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import people.droid.puzzle.R
import people.droid.puzzle.ui.model.getPieceColorResId
import people.droid.puzzle.ui.theme.Typography
import kotlin.math.roundToInt

@Composable
fun PuzzlePiece(
    number: Int,
    boardOffset: Offset = Offset(0f, 0f),
    cellSize: Float,
    widthRange: IntRange,
    heightRange: IntRange,
    zIndex: Float = 0f,
    updateZIndex: () -> Unit = {},
    updateMatchCount: () -> Unit = {},
    onDrop: (Int) -> Boolean
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    var enable by remember { mutableStateOf(true) }
    val color by remember { mutableIntStateOf((0..3).random()) }
    val isVisible = Offset(offsetX, offsetY) != Offset.Zero

    val density = LocalDensity.current
    val pieceSize = with(density) { cellSize.toDp() - 5.dp }
    val pieceHalfSizePx = with(density) { (pieceSize / 2).toPx() }
    val fontSize = with(density) { (cellSize * (1 / 2f)).toSp() }

    LaunchedEffect(Unit) {
        offsetX = widthRange.random().toFloat()
        offsetY = heightRange.random().toFloat()
    }

    Box(
        modifier = Modifier
            .alpha(if (isVisible) 1f else 0f)
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .size(pieceSize)
            .zIndex(zIndex)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        if (!enable) return@detectDragGestures
                        updateZIndex()
                    },
                    onDrag = { change, dragAmount ->
                        if (enable) {
                            change.consume()
                            offsetX += dragAmount.x
                            offsetY += dragAmount.y
                        }
                    },
                    onDragEnd = {
                        val pieceCenter = Offset(
                            x = offsetX + pieceHalfSizePx,
                            y = offsetY + pieceHalfSizePx
                        )
                        val isSame = onDrop(number)
                        if (!isSame) return@detectDragGestures
                        if (pieceCenter.x in boardOffset.x..(boardOffset.x + cellSize) &&
                            pieceCenter.y in boardOffset.y..(boardOffset.y + cellSize)
                        ) {
                            offsetX = boardOffset.x + (cellSize / 2) - pieceHalfSizePx
                            offsetY = boardOffset.y + (cellSize / 2) - pieceHalfSizePx
                            enable = false
                            updateMatchCount()
                        }
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = if (enable) getPieceColorResId(color) else R.drawable.colored_gray),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Text(
            text = if (enable) number.toString() else "",
            style = Typography.bodyMedium,
            fontSize = fontSize,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PuzzlePiecePreview() {
    PuzzlePiece(
        number = 1,
        cellSize = 100f,
        widthRange = 0..0,
        heightRange = 0..0,
        onDrop = { false })
}