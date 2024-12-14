package people.droid.ads.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import people.droid.ads.BANNER_AD_ROUTE
import people.droid.ads.INTERSTITIAL_ROUTE
import people.droid.ads.NATIVE_ROUTE
import people.droid.ads.R
import people.droid.ads.ui.component.BackButton
import people.droid.ads.ui.theme.backgroundYellow

@Composable
fun AdsScreen(navigate: (String) -> Unit, navigateBack: () -> Unit) {
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundYellow)
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            BackButton(modifier = Modifier.align(Alignment.TopStart), navigateBack = navigateBack)
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 32.dp),
                text = stringResource(R.string.support_us),
                fontSize = 30.sp
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(modifier = Modifier.noRippleClickable { navigate(BANNER_AD_ROUTE) }) {
                    BananaItem(70.dp)
                }
                Row(modifier = Modifier.noRippleClickable { navigate(INTERSTITIAL_ROUTE) }) {
                    BananaItem(120.dp)
                }
                Row(modifier = Modifier.noRippleClickable { navigate(NATIVE_ROUTE) }) {
                    repeat(3) { BananaItem(70.dp) }
                }
            }
        }
    }
}

@Composable
fun BananaItem(size: Dp) {
    Image(
        modifier = Modifier.size(size),
        painter = painterResource(R.drawable.banana),
        contentDescription = null
    )
}

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}

@Preview(showBackground = true)
@Composable
fun AdsScreenPreview() {
    AdsScreen(navigate = {}, navigateBack = {})
}
