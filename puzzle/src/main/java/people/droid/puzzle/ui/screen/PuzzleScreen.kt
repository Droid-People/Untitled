package people.droid.puzzle.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import people.droid.puzzle.R
import people.droid.puzzle.ui.component.ButtonColor
import people.droid.puzzle.ui.component.CrayonButton
import people.droid.puzzle.ui.component.PuzzleGridBoard
import people.droid.puzzle.ui.component.PuzzlePiece

const val PUZZLE_ROUTE = "puzzle"

@Composable
fun PuzzleScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val isLandscape = isLandscape()
    val adjustedScreenWidth = with(density) {
        if (isLandscape) configuration.screenHeightDp.dp.toPx()
        else configuration.screenWidthDp.dp.toPx()
    }
    val screenWidth = with(density) { configuration.screenWidthDp.dp.toPx() }

    var boardOffset by remember { mutableStateOf(Offset.Zero) }
    var uiOffset by remember { mutableStateOf(Offset.Zero) }

    var boardSize by remember { mutableIntStateOf(3) }
    var boardsInfo by remember { mutableStateOf(emptyList<Pair<Offset, Int>>()) }
    val cellSize = adjustedScreenWidth / (boardSize * 2)

    var highestZIndex by remember { mutableFloatStateOf(0f) }
    val zIndex = remember { mutableStateMapOf<Int, Float>() }
    var start by remember { mutableStateOf(false) }
    var matchCount by remember { mutableIntStateOf(0) }

    val (widthRange, heightRange) = remember(uiOffset) {
        val minHeight = boardOffset.y + cellSize * boardSize
        val adjustedMaxHeight = uiOffset.y - cellSize
        val maxHeight = if (adjustedMaxHeight < minHeight) minHeight else adjustedMaxHeight
        val maxWidth = screenWidth - cellSize
        0..maxWidth.toInt() to minHeight.toInt()..maxHeight.toInt()
    }

    InitBoardInfoLaunchedEffect(
        boardSize = boardSize,
        boardOffset = boardOffset,
        cellSize = cellSize
    ) { info ->
        boardsInfo = info
    }

    Scaffold { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
            )
            IconButton(onClick = navigateBack) {
                Image(
                    painter = painterResource(R.drawable.back_button),
                    contentDescription = null
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 100.dp)
                    .onGloballyPositioned { coordinates ->
                        val offset = coordinates.positionInParent()
                        boardOffset = Offset(offset.x, offset.y)
                    }
            ) {
                PuzzleGridBoard(
                    offset = boardOffset,
                    boardSize = boardSize,
                    cellSize = with(density) { cellSize.toDp() }
                )
            }

            PuzzlePieceGenerator(
                start = start,
                boardsInfo = boardsInfo,
                cellSize = cellSize,
                boardSize = boardSize,
                widthRange = widthRange,
                heightRange = heightRange,
                zIndex = zIndex,
                updateZIndex = { idx -> zIndex[idx] = ++highestZIndex },
                updateMatchCount = {
                    matchCount++
                    if (matchCount == boardSize * boardSize) {
                        scope.launch {
                            delay(500)
                            start = false
                        }
                    }
                },
                onDrop = { idx, number ->
                    boardsInfo[idx].second == number
                }
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .onGloballyPositioned { coordinates ->
                        val offset = coordinates.positionInParent()
                        uiOffset = Offset(offset.x, offset.y)
                    }
                    .padding(bottom = 30.dp)
            ) {
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    CrayonButton(
                        width = 60,
                        onClick = { if (boardSize > 2) boardSize -= 1 },
                        enable = !start,
                        color = ButtonColor.ORANGE,
                        text = stringResource(id = R.string.minus_board_size)
                    )
                    Spacer(modifier = Modifier.size(18.dp))
                    CrayonButton(
                        width = 60,
                        onClick = { if (boardSize < 5) boardSize += 1 },
                        enable = !start,
                        color = ButtonColor.ORANGE,
                        text = stringResource(id = R.string.plus_board_size)
                    )
                }
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    CrayonButton(
                        onClick = {
                            if (!start) start = true
                            matchCount = 0
                        },
                        enable = !start,
                        color = ButtonColor.BLUE,
                        text = stringResource(id = R.string.start_button)
                    )
                    Spacer(modifier = Modifier.size(18.dp))
                    CrayonButton(
                        onClick = { if (start) start = false },
                        enable = start,
                        color = ButtonColor.RED,
                        text = stringResource(id = R.string.reset_button)
                    )
                }
            }
        }
    }
}

@Composable
fun InitBoardInfoLaunchedEffect(
    boardSize: Int,
    boardOffset: Offset,
    cellSize: Float,
    initBoardInfo: (List<Pair<Offset, Int>>) -> Unit
) {
    LaunchedEffect(boardSize, boardOffset) {
        if (boardOffset != Offset.Zero) {
            val boardsInfo = List(boardSize * boardSize) { idx ->
                val row = idx / boardSize
                val column = idx % boardSize
                val offset = Offset(
                    x = boardOffset.x + column * cellSize,
                    y = boardOffset.y + row * cellSize
                )
                offset to (idx + 1)
            }
            initBoardInfo(boardsInfo)
        }
    }
}

@Composable
fun PuzzlePieceGenerator(
    start: Boolean,
    boardsInfo: List<Pair<Offset, Int>>,
    boardSize: Int,
    cellSize: Float,
    widthRange: IntRange,
    heightRange: IntRange,
    zIndex: Map<Int, Float>,
    updateZIndex: (Int) -> Unit,
    updateMatchCount: () -> Unit,
    onDrop: (Int, Int) -> Boolean,
) {
    if (start && boardsInfo.isNotEmpty()) {
        repeat(boardSize * boardSize) { idx ->
            PuzzlePiece(
                number = idx + 1,
                boardOffset = boardsInfo[idx].first,
                cellSize = cellSize,
                widthRange = widthRange,
                heightRange = heightRange,
                zIndex = zIndex[idx] ?: 0f,
                updateZIndex = { updateZIndex(idx) },
                updateMatchCount = updateMatchCount,
                onDrop = { number -> onDrop(idx, number) }
            )
        }
    }
}

@Composable
fun isLandscape(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}

@Preview(showBackground = true)
@Composable
fun PuzzleScreenPreview() {
    PuzzleScreen()
}