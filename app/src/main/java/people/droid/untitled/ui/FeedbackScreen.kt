package people.droid.untitled.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import people.droid.untitled.R
import people.droid.untitled.ui.theme.StoryMilkyFontFamilyBold
import people.droid.untitled.ui.theme.UntitledTheme
import people.droid.untitled.ui.theme.YellowBackground

const val FEEDBACK_ROUTE = "feedback"

@Composable
fun FeedbackScreen(viewModel: FeedbackViewModel) {
    var text by remember { mutableStateOf("") }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    FeedbackScreenUI(
        context,
        uiState,
        viewModel::resetUiState,
        text,
        { text = it },
    ) {
        viewModel.postFeedback(text)
    }

}

@Composable
fun FeedbackScreenUI(
    context: Context,
    uiState: FeedbackUiState = FeedbackUiState(),
    resetUiState: () -> Unit = {},
    text: String = "",
    onTextChange: (String) -> Unit = {},
    onSendButtonClicked: () -> Unit = {}
) {
    var showSendMessageScreen by remember { mutableStateOf(false) }
    LaunchedEffect(uiState.isSuccess) {
        showSendMessageScreen = when (uiState.isSuccess) {
            true -> false
            false -> {
                Toast.makeText(context, "Submission failed... Please try again.", Toast.LENGTH_SHORT).show()
                true
            }
            else -> true
        }
    }
    UntitledTheme {
        Scaffold { innerPadding ->
            Box(
                modifier = Modifier
                    .background(color = YellowBackground)
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (showSendMessageScreen) {
                    SendMessageScreen(text, onTextChange, onSendButtonClicked)
                } else {
                    CompleteSendMessageScreen {
                        showSendMessageScreen = true
                        resetUiState()
                        onTextChange("")
                    }
                }

            }

        }
    }
}

@Composable
fun SendMessageScreen(message: String = "", onTextChange: (String) -> Unit = {}, onSendButtonClicked: () -> Unit = {}) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(50.dp))
        Text("Your Ideas", style = MaterialTheme.typography.headlineLarge)
        Text(
            "Let us know what features you'd like to see!\nWe'll do our best to add them!",
            textAlign = TextAlign.Center
        )
        BasicTextField(
            value = message,
            onValueChange = onTextChange
        ) {
            Box(
                Modifier
                    .padding(20.dp)
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .border(width = 1.dp, color = Color.Black, RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .heightIn(100.dp)
                    .padding(10.dp)
            ) {
                Text(message)
            }
        }
        Button(
            onClick = onSendButtonClicked,
            colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = Color.Black)
        ) {
            Text("SEND", fontFamily = StoryMilkyFontFamilyBold)
        }
    }
}

@Composable
fun CompleteSendMessageScreen(onSendMoreButtonClicked: () -> Unit = {}) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Spacer(Modifier.height(50.dp))
        Text("Successfully sent!", style = MaterialTheme.typography.headlineLarge)
        Image(
            modifier = Modifier
                .padding(20.dp)
                .size(100.dp),
            painter = painterResource(R.drawable.checked),
            contentDescription = null
        )
        Row {
            Button(
                onClick = onSendMoreButtonClicked,
                colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = Color.Black)
            ) {
                Text("Send more", fontFamily = StoryMilkyFontFamilyBold)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SendMessageScreenPreview() {
    UntitledTheme {
        SendMessageScreen()
    }
}

@Composable
@Preview(showBackground = true)
fun CompleteSendMessageScreenPreview() {
    UntitledTheme {
        Box(modifier = Modifier.fillMaxSize()){
            CompleteSendMessageScreen()
        }

    }
}