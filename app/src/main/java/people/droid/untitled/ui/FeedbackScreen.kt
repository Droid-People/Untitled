package people.droid.untitled.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import people.droid.untitled.R
import people.droid.common.theme.StoryMilkyFontFamilyBold
import people.droid.common.theme.UntitledTheme
import people.droid.common.theme.YellowBackground

const val FEEDBACK_ROUTE = "feedback"

@Composable
fun FeedbackScreen(viewModel: FeedbackViewModel, goToHome: () -> Unit) {
    var text by remember { mutableStateOf("") }
    var isSendMessageScreenShown by remember { mutableStateOf(true) }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    LaunchedEffect(isSendMessageScreenShown) {
        if (!isSendMessageScreenShown) {
            delay(1500)
            goToHome()
            viewModel.resetUiState()
        }
    }
    FeedbackScreenUI(
        context,
        isSendMessageScreenShown,
        { isSendMessageScreenShown = it },
        uiState,
        text,
        goToHome,
        { text = it },
    ) {
        viewModel.postFeedback(text)
    }

}

@Composable
fun FeedbackScreenUI(
    context: Context,
    isSendMessageScreenShown: Boolean,
    onSendMessageScreenShownChanged: (Boolean) -> Unit,
    uiState: FeedbackUiState = FeedbackUiState(),
    text: String = "",
    goToHome: () -> Unit = {},
    onTextChange: (String) -> Unit = {},
    onSendButtonClicked: () -> Unit = {},
) {
    LaunchedEffect(uiState.isSuccess) {
        when (uiState.isSuccess) {
            true -> onSendMessageScreenShownChanged(false)
            false -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.fail_message_feedback_submission),
                    Toast.LENGTH_SHORT
                ).show()
                onSendMessageScreenShownChanged(true)
            }

            else -> onSendMessageScreenShownChanged(true)
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
                if (isSendMessageScreenShown) {
                    SendMessageScreen(text, onTextChange, onSendButtonClicked, goToHome)
                } else {
                    CompleteSendMessageScreen()
                }

            }

        }
    }
}

@Composable
fun SendMessageScreen(
    message: String = "",
    onTextChange: (String) -> Unit = {},
    onSendButtonClicked: () -> Unit = {},
    goToHome: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            modifier = Modifier.align(Alignment.Start),
            onClick = goToHome,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }
        Spacer(Modifier.height(50.dp))
        Text(stringResource(R.string.label_feedback), style = MaterialTheme.typography.titleLarge)
        Text(
            stringResource(R.string.description_feedback),
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
            Text(stringResource(R.string.label_submit_feedback_button), fontFamily = StoryMilkyFontFamilyBold)
        }
    }
}

@Composable
fun CompleteSendMessageScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(50.dp))
        Text(
            stringResource(R.string.success_message_feedback_submission),
            style = MaterialTheme.typography.headlineLarge
        )
        Image(
            modifier = Modifier
                .padding(20.dp)
                .size(100.dp),
            painter = painterResource(R.drawable.checked),
            contentDescription = null
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SendMessageScreenPreview() {
    UntitledTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            SendMessageScreen()
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CompleteSendMessageScreenPreview() {
    UntitledTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            CompleteSendMessageScreen()
        }

    }
}