package people.droid.untitled.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import people.droid.untitled.R
import people.droid.untitled.ui.theme.UntitledTheme
import people.droid.untitled.ui.theme.YellowBackground

@Composable
fun HomeBackground() {
    Box(
        Modifier
            .fillMaxSize()
            .background(YellowBackground)
    ) {

        val bananaImage = ImageBitmap.imageResource(R.drawable.small_banana)
        val trash01 = ImageBitmap.imageResource(R.drawable.trash_01)
        val trash02 = ImageBitmap.imageResource(R.drawable.trash_02)

        val infiniteTransition = rememberInfiniteTransition(label = "banana")
        // rotation
        val rotation by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(3000, easing = LinearEasing),
            ),
            label = "rotation"
        )

        // 범위 0 ~ size.width - bananaImage.width 안에서 겹치지 않게 랜덤으로 10개의 x, y 좌표를 담은 Offset List 생성
        val context = LocalContext.current
        val screenWidth = context.resources.displayMetrics.widthPixels
        val screenHeight = context.resources.displayMetrics.heightPixels
        val randomOffsetList = List(20) {
            Offset(
                (0..(screenWidth - bananaImage.width)).random().toFloat(),
                (0..(screenHeight - bananaImage.height)).random().toFloat()
            )
        }

        Canvas(Modifier.fillMaxSize()) {

            randomOffsetList.subList(0, 12).forEach {
                with(drawContext.canvas) {
                    save()
                    rotate(rotation, pivot = it) {
                        drawImage(
                            image  =  bananaImage,
                            topLeft = Offset(it.x - bananaImage.width/2, it.y - bananaImage.height/2),
                        )
                        restore()
                    }
                }
            }
            randomOffsetList.subList(12, 16).forEach {
                with(drawContext.canvas) {
                    save()
                    rotate(rotation, pivot = it) {
                        drawImage(
                            image  =  trash01,
                            topLeft = Offset(it.x - bananaImage.width/2, it.y - bananaImage.height/2),
                        )
                        restore()
                    }
                }
            }
            randomOffsetList.subList(16, 20).forEach {
                with(drawContext.canvas) {
                    save()
                    rotate(rotation, pivot = it) {
                        drawImage(
                            image  =  trash02,
                            topLeft = Offset(it.x - bananaImage.width/2, it.y - bananaImage.height/2),
                        )
                        restore()
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeBackgroundPreview() {
    UntitledTheme {
        HomeBackground()
    }
}
