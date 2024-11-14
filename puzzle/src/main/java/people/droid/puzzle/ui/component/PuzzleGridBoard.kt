package people.droid.puzzle.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PuzzleGridBoard(modifier: Modifier = Modifier, offset: Offset, boardSize: Int, cellSize: Dp) {
    var boardOffset by remember { mutableStateOf(offset) }

    Box(modifier = modifier) {
        LazyColumn(
            modifier = modifier
                .align(Alignment.Center)
                .onGloballyPositioned { coordinates ->
                    val offset = coordinates.positionInParent()
                    boardOffset = Offset(offset.x, offset.y)
                }
        ) {
            items(boardSize) { column ->
                LazyRow {
                    items(boardSize) { row ->
                        val cellNumber = (column * boardSize) + row + 1
                        PuzzleCell(cellNumber, cellSize)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PuzzleGridBoardPreview() {
    PuzzleGridBoard(offset = Offset(0f, 0f), boardSize = 3, cellSize = 80.dp)
}