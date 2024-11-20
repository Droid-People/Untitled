package people.droid.untitled

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import people.droid.pixelart.PIXEL_ART_ROUTE
import people.droid.pixelart.PixelArtScreenComposable
import people.droid.pixelart.PixelArtViewModel
import people.droid.untitled.ui.EnterToLeftTransition
import people.droid.untitled.ui.EnterToRightTransition
import people.droid.untitled.ui.ExitToLeftTransition
import people.droid.untitled.ui.ExitToRightTransition

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pixelArtViewModel = ViewModelProvider(this@MainActivity)[PixelArtViewModel::class.java]

        setContent {
            val navController = rememberNavController()

            Scaffold {
                NavHost(navController = navController, startDestination = HOME_ROUTE) {
                    composable(
                        HOME_ROUTE,
                        enterTransition = EnterToLeftTransition(),
                        popEnterTransition = EnterToRightTransition(),
                        exitTransition = ExitToLeftTransition(),
                        popExitTransition = ExitToRightTransition()
                    ) {
                        HomeScreen(navController)
                    }
                    composable(
                        PIXEL_ART_ROUTE,
                        enterTransition = EnterToLeftTransition(),
                        popEnterTransition = EnterToRightTransition(),
                        exitTransition = ExitToLeftTransition(),
                        popExitTransition = ExitToRightTransition()
                    ) {
                        PixelArtScreenComposable(navController, pixelArtViewModel)
                    }
                }
            }

        }
    }
}