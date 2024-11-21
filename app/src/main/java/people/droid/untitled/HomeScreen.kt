package people.droid.untitled

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import people.droid.pixelart.PIXEL_ART_ROUTE
import people.droid.untitled.ui.HomeBackground
import people.droid.untitled.ui.theme.UntitledTheme

const val HOME_ROUTE = "home"

@Composable
fun HomeScreen(navController: NavController) {
    UntitledTheme {
        HomeBackground()
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    navController.navigate(PIXEL_ART_ROUTE)
                }
            ) {
                Text("Pixel Art Maker")
            }
            Button(
                onClick = {
                    //                    val intent = Intent(
                    //                        this@MainActivity,
                    //                        people.droid.roulette.MainActivity::class.java
                    //                    )
                    //                    startActivity(intent)
                }
            ) {
                Text("Roulette")
            }
            Button(
                onClick = {
                    //                    val intent = Intent(
                    //                        this@MainActivity,
                    //                        people.droid.puzzle.MainActivity::class.java
                    //                    )
                    //                    startActivity(intent)
                }
            ) {
                Text("Puzzle")
            }
            //            AndroidView(
            //                modifier = Modifier.height(50.dp).fillMaxWidth(),
            //                factory = {
            //                    AdView(it).apply {
            //                        setAdSize(AdSize.BANNER)
            //                        adUnitId = "ca-app-pub-4452713350716636/7691375888"
            //                        loadAd(AdRequest.Builder().build())
            //                    }
            //                },
            //                update = {
            //                    it.loadAd(AdRequest.Builder().build())
            //                }
            //            )
        }
    }
}
