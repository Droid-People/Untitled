package people.droid.roulette.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import people.droid.roulette.ui.theme.MyBabyPink

@Composable
fun Roulette(
    items: RouletteItems,
    state: RouletteState,
    rotation: Animatable<Float, AnimationVector1D> = Animatable(0f),
    onItemValueUpdate: (Int, String) -> Unit
) {

    BoxWithConstraints {
        val degreesPerItems = items.getDegreesPerItem()
        val size = min(this.maxHeight, this.maxWidth)
        Box(modifier = Modifier
            .size(size)
            .graphicsLayer {
                rotationZ = rotation.value
                transformOrigin = TransformOrigin.Center
            }) {
            items.getItems().forEachIndexed { index, item ->
                val text = remember {
                    mutableStateOf(item.value)
                }
                RouletteSlice(
                    modifier = Modifier.rotate(degrees = degreesPerItems * index),
                    size = size,
                    color = item.color,
                    degree = degreesPerItems,
                    content = {
                        when (state) {
                            RouletteState.SETTING -> RouletteItemTextField(text = text.value) {
                                text.value = it
                                onItemValueUpdate(index, it)
                            }

                            RouletteState.PROGRESSING -> Text(
                                text = item.value,
                                style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
                                fontSize = 24.sp
                            )

                            RouletteState.COMPLETE -> {
                                Text(
                                    text = item.value,
                                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
                                    fontSize = 24.sp
                                )
                            }
                        }

                    },
                    contentModifier =
                    when (state) {
                        RouletteState.SETTING -> Modifier.rotate(degrees = -degreesPerItems * index)
                        RouletteState.PROGRESSING -> Modifier
                        RouletteState.COMPLETE -> Modifier
                    },
                )
            }
        }

        Box(modifier = Modifier
            .align(Alignment.TopCenter)
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
                            .asComposePath(), color = MyBabyPink
                    )
                }

            })
    }

}


@Composable
fun RouletteSlice(
    modifier: Modifier,
    size: Dp,
    color: Color,
    degree: Float,
    content: @Composable () -> Unit,
    contentModifier: Modifier
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
            content()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun RoulettePreview() {
    Roulette(
        items = RouletteItems.create(3),
        state = RouletteState.SETTING,
        onItemValueUpdate = { _, _ -> })
}