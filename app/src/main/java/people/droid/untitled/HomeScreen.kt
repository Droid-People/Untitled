package people.droid.untitled

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import people.droid.pixelart.PIXEL_ART_ROUTE
import people.droid.puzzle.ui.screen.PUZZLE_ROUTE
import people.droid.roulette.ui.ROULETTE_ROUTE
import people.droid.untitled.ui.HomeBackground
import people.droid.untitled.ui.theme.UntitledTheme

const val HOME_ROUTE = "home"

@Composable
fun HomeScreen(navController: NavController) {
    UntitledTheme {
        HomeBackground()
        Column(
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()
        ) {
            Button(onClick = {
                navController.navigate(PIXEL_ART_ROUTE)
            }) {
                Text("Pixel Art Maker")
            }
            Button(onClick = {
                navController.navigate(ROULETTE_ROUTE)
            }) {
                Text("Roulette")
            }
            Button(onClick = {
                navController.navigate(PUZZLE_ROUTE)
            }) {
                Text("Puzzle")
            }
            AndroidView(modifier = Modifier.height(50.dp).fillMaxWidth(), factory = {
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
            }, update = {
                it.loadAd(AdRequest.Builder().build())
            })
        }
    }
}
