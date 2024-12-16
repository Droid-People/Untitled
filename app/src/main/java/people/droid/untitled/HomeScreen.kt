package people.droid.untitled

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import people.droid.ads.ADS_ROUTE
import people.droid.pixelart.PIXEL_ART_ROUTE
import people.droid.puzzle.ui.screen.PUZZLE_ROUTE
import people.droid.roulette.ui.ROULETTE_ROUTE
import people.droid.untitled.release_note.RELEASE_NOTES_ROUTE
import people.droid.untitled.ui.DEVELOPERS_ROUTE
import people.droid.untitled.ui.FEEDBACK_ROUTE
import people.droid.untitled.ui.Feature
import people.droid.untitled.ui.FeatureGrid
import people.droid.untitled.ui.HomeBackground
import people.droid.untitled.ui.theme.UntitledTheme
import people.droid.untitled.utils.isInEditMode

const val HOME_ROUTE = "home"

@Composable
fun HomeScreen(onNavigate: (String) -> Unit = {}) {
    val features = listOf(
        Feature(title = "Pixel Art Maker", route = PIXEL_ART_ROUTE),
        Feature(title = "Roulette", route = ROULETTE_ROUTE),
        Feature(title = "Puzzle", route = PUZZLE_ROUTE),
        Feature(title = "Developers", route = DEVELOPERS_ROUTE),
        Feature(title = "Release Notes", route = RELEASE_NOTES_ROUTE),
        Feature(title = "Your Ideas", route = FEEDBACK_ROUTE),
        Feature(title = "Ads", route = ADS_ROUTE)
    )

    UntitledTheme {
        HomeBackground()
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp)
        ) {
            Image(
                modifier = Modifier.size(300.dp),
                painter = painterResource(R.drawable.main_banana),
                contentDescription = null
            )
            FeatureGrid(
                modifier = Modifier.weight(1f),
                features = features,
                onNavigate = onNavigate
            )
            if (isInEditMode()){
                Text("AdView", modifier = Modifier.height(50.dp).fillMaxWidth().background(Color.White))
            } else {
                AndroidView(    // Preview 모드에서 AndroidView를 사용하면 미리보기가 불가능하다.
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    factory = {
                        AdView(it).apply {
                            setAdSize(AdSize.BANNER)
                            setBannerAdUnitId()
                            loadAd(AdRequest.Builder().build())
                        }
                    },
                    update = {
                        it.loadAd(AdRequest.Builder().build())
                    }
                )
            }
        }
    }
}

private fun AdView.setBannerAdUnitId() {
    adUnitId = if (BuildConfig.DEBUG) {
        "ca-app-pub-3940256099942544/6300978111"
    } else {
        "ca-app-pub-4452713350716636/7691375888"
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    UntitledTheme {
        HomeScreen()
    }
}