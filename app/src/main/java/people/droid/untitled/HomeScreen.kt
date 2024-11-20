package people.droid.untitled

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import people.droid.pixelart.PIXEL_ART_ROUTE
import people.droid.untitled.ui.theme.UntitledTheme

const val HOME_ROUTE = "home"

@Composable
fun HomeScreen(navController: NavController) {
    UntitledTheme {
        Column(
            modifier = Modifier.fillMaxSize().background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
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
        }
    }
}