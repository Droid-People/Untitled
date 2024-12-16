package people.droid.untitled.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import people.droid.common.theme.UntitledTheme
import people.droid.untitled.R

const val DEVELOPERS_ROUTE = "developers"

@Composable
fun DeveloperScreen(navigateBack: () -> Unit = {}) {
    UntitledTheme {
        Scaffold { innerPadding ->
            HomeBackground(true)
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                IconButton(onClick = navigateBack) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 16.dp)
                ) {
                    val durationState1 = remember { mutableStateOf(3000) }
                    val durationState2 = remember { mutableStateOf(3000) }
                    val durationState3 = remember { mutableStateOf(3000) }
                    Profile("boring-km", R.drawable.star_butterfly, 0, durationState1) {
                        durationState1.value -= 100
                        if (durationState1.value < 100) {
                            durationState1.value = 3000
                        }
                    }
                    Profile("whk06061", R.drawable.hyegyeong_profile, 120, durationState2) {
                        durationState2.value -= 100
                        if (durationState2.value < 100) {
                            durationState2.value = 3000
                        }
                    }
                    Profile("yewon-yw", R.drawable.yewon_profile, 240, durationState3) {
                        durationState3.value -= 100
                        if (durationState3.value < 100) {
                            durationState3.value = 3000
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Profile(
    name: String,
    imageResId: Int,
    initialRotationValue: Int,
    durationState: MutableState<Int> = mutableStateOf(3000),
    onDurationChange: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable {
                onDurationChange()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val rotation = remember { Animatable(initialRotationValue.toFloat()) }

        // durationState 값이 변경될 때마다 새로운 애니메이션 시작
        LaunchedEffect(durationState.value) {
            rotation.animateTo(
                targetValue = rotation.value + 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = durationState.value, easing = LinearEasing)
                )
            )
        }

        Image(
            modifier = Modifier
                .rotate(rotation.value)
                .clip(RoundedCornerShape(45.dp))
                .size(120.dp),
            painter = painterResource(imageResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Text(text = name, fontSize = 36.sp, style = MaterialTheme.typography.bodyLarge)
        Text(
            text = "Android Developer",
            fontSize = 24.sp,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DeveloperScreenPreview() {
    DeveloperScreen()
}