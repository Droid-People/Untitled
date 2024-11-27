package people.droid.untitled

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import people.droid.pixelart.PIXEL_ART_ROUTE
import people.droid.puzzle.ui.screen.PUZZLE_ROUTE
import people.droid.roulette.ui.ROULETTE_ROUTE
import people.droid.untitled.ui.Feature
import people.droid.untitled.ui.FeatureGrid
import people.droid.untitled.ui.HomeBackground
import people.droid.untitled.ui.theme.UntitledTheme

const val HOME_ROUTE = "home"

@Composable
fun HomeScreen(navController: NavController) {
    val features = listOf(
        Feature(title = "Pixel Art Maker", route = PIXEL_ART_ROUTE),
        Feature(title = "Roulette", route = ROULETTE_ROUTE),
        Feature(title = "Puzzle", route = PUZZLE_ROUTE),
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
                navController = navController
            )
            AndroidView(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                factory = {
                    AdView(it).apply {
                        setAdSize(AdSize.BANNER)
                        @Suppress("KotlinConstantConditions")
                        if (BuildConfig.BUILD_TYPE == "debug") {
                            adUnitId = "ca-app-pub-3940256099942544/6300978111"
                        } else {
                            adUnitId = "ca-app-pub-4452713350716636/7691375888"
                        }
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
