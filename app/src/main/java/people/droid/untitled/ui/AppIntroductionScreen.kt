package people.droid.untitled.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import people.droid.common.theme.UntitledTheme
import people.droid.common.theme.YellowBackground
import people.droid.untitled.R

const val APP_INTRODUCTION_ROUTE = "appIntroduction"

@Composable
fun AppIntroductionScreen(goToHome: () -> Unit = {}) {
    UntitledTheme {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .background(color = YellowBackground)
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.Start),
                    onClick = goToHome,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                Text(
                    text = "What The Banana?",
                    style = MaterialTheme.typography.headlineLarge
                )
                Image(
                    modifier = Modifier.size(200.dp),
                    painter = painterResource(R.drawable.main_banana),
                    contentDescription = "banana"
                )
                Text(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.app_description),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AppIntroductionScreenPreview() {
    AppIntroductionScreen()
}