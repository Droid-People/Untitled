package people.droid.puzzle.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import people.droid.puzzle.ui.theme.Typography

@Composable
fun PuzzleCell(number: Int, cellSize: Dp = 80.dp) {
    val fontSize = with(LocalDensity.current) { (cellSize.toPx() * (5 / 8f)).toSp() }
    Box(
        modifier = Modifier
            .size(cellSize)
            .background(Color.White)
            .border(width = 1.dp, color = Color.Gray, shape = RectangleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$number",
            style = Typography.bodyMedium,
            fontSize = fontSize,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PuzzleCellPreview() {
    PuzzleCell(1)
}