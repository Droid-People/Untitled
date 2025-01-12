package people.droid.roulette.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import people.droid.roulette.domain.model.RouletteItems
import people.droid.roulette.ui.model.RouletteState
import people.droid.roulette.ui.theme.RouletteBabyPink

@Composable
fun Roulette(
    items: RouletteItems,
    state: RouletteState,
    rotation: Animatable<Float, AnimationVector1D> = Animatable(0f),
    onTextChanged: (Int, String) -> Unit,
    itemTexts: List<String> = listOf(),
    itemsFocusedInfo: List<Boolean> = listOf(),
    onFocusChanged: (Int, Boolean) -> Unit = { _, _ -> }
) {

    // Box의 기능을 모두 포함하면서 Layout의 Constraint에 접근할 수 있도록 만들어진 layout
    // 화면 크기에 맞춘 룰렛을 구현하기 위해 사용
    BoxWithConstraints {
        val degreesPerItems = items.getDegreesPerItem()
        val size = min(this.maxHeight, this.maxWidth) - 25.dp
        Box(modifier = Modifier
            .size(size)
            .graphicsLayer {
                rotationZ = rotation.value
                transformOrigin = TransformOrigin.Center
            }) {
            items.getItems().forEachIndexed { index, item ->
                RouletteSlice(
                    modifier = Modifier.rotate(degrees = degreesPerItems * index),
                    size = size,
                    color = item.color,
                    degree = degreesPerItems,
                    contentModifier =
                    when (state) {
                        RouletteState.SETTING -> Modifier.rotate(degrees = -degreesPerItems * index)
                        RouletteState.PROGRESSING -> Modifier
                        RouletteState.COMPLETE -> Modifier
                    },
                    state = state,
                    text = itemTexts[index],
                    onTextChanged = { onTextChanged(index, it) },
                    item = item,
                    isFocused = itemsFocusedInfo[index],
                    onFocusChanged = { onFocusChanged(index, it) }
                )
            }
        }
        RouletteStopper(Modifier.align(Alignment.TopCenter), size)
    }
}

@Composable
fun RouletteStopper(modifier: Modifier, size: Dp) {
    Box(modifier = modifier
        .drawWithContent {
            val rotationAngle = 90f
            rotate(rotationAngle, pivot = Offset(0f, 0f)) {
                val roundedPolygon = RoundedPolygon(
                    numVertices = 3,
                    radius = size.value / 6,
                    centerX = -10f,
                    centerY = 0f,
                    rounding = CornerRounding(10f)
                )
                drawPath(
                    roundedPolygon
                        .toPath()
                        .asComposePath(), color = RouletteBabyPink
                )
            }
        })
}


@Preview(showBackground = true)
@Composable
fun RoulettePreview() {
    Roulette(
        items = RouletteItems.create(3),
        state = RouletteState.SETTING,
        onTextChanged = { _, _ -> },
        itemTexts = listOf("", "", ""),
        itemsFocusedInfo = listOf(false, false, false),
        onFocusChanged = {_,_ ->},

    )
}