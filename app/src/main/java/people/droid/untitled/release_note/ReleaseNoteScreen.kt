package people.droid.untitled.release_note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import people.droid.untitled.ui.HomeBackground
import people.droid.untitled.ui.theme.UntitledTheme

const val RELEASE_NOTES_ROUTE = "release_notes"

@Composable
fun ReleaseNoteScreenComposable(viewModel: ReleaseNoteViewModel, navigateBack: () -> Unit = {}) {

    LaunchedEffect(Unit) {
        viewModel.loadReleaseNotes()
    }

    val releaseNoteState = viewModel.releaseNotes.collectAsState().value

    UntitledTheme {
        ReleaseNoteScreen(
            releaseNoteState = releaseNoteState,
            navigateBack = navigateBack
        )
    }
}

@Composable
fun ReleaseNoteScreen(releaseNoteState: ReleaseNoteState, navigateBack: () -> Unit = {}) {
    val releaseNotes = releaseNoteState.releaseNotes
    val errorMessage = releaseNoteState.errorMessage
    Scaffold {
        HomeBackground(true)
        IconButton(onClick = navigateBack) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 32.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Release Notes",
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 40.sp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        textAlign = TextAlign.Center,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    Modifier
                        .drawBehind {
                            val strokeWidth = 3.dp.toPx()
                            val halfStrokeWidth = strokeWidth / 2
                            // 둥근 사각형 테두리 그리기, 가운데는 투명하고 테두리만 그림
                            drawRoundRect(
                                color = Color.Black,
                                topLeft = Offset(halfStrokeWidth, halfStrokeWidth),
                                size = Size(
                                    width = size.width - strokeWidth,
                                    height = size.height - strokeWidth
                                ),
                                cornerRadius = CornerRadius(24.dp.toPx()),
                                style = Stroke(width = strokeWidth)
                            )
                        }
                        .padding(3.dp)
                ) {
                    releaseNotes.forEachIndexed { i, it ->
                        var shape = when (i) {
                            0 -> RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                            releaseNotes.size - 1 -> RoundedCornerShape(
                                bottomStart = 24.dp,
                                bottomEnd = 24.dp
                            )

                            else -> RoundedCornerShape(0.dp)
                        }
                        if (releaseNotes.size == 1) {
                            shape = RoundedCornerShape(24.dp)
                        }
                        Column(
                            modifier = Modifier
                                .clip(shape)
                                .background(Color.White.copy(alpha = 0.8f))
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = it.title,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = it.content,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewReleaseNoteScreen() {
    UntitledTheme {
        ReleaseNoteScreen(
            releaseNoteState = ReleaseNoteState(remember {
                mutableStateListOf(
                    ReleaseNote(
                        title = "Title 1",
                        content = "Content 1"
                    ),
                    ReleaseNote(
                        title = "Title 2",
                        content = "Content 2"
                    ),
                    ReleaseNote(
                        title = "Title 3",
                        content = "Content 3"
                    )
                )
            }, "Test Error Message")
        )
    }
}