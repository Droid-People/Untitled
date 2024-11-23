package people.droid.untitled

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import people.droid.pixelart.PIXEL_ART_ROUTE
import people.droid.pixelart.PixelArtScreenComposable
import people.droid.pixelart.PixelArtViewModel
import people.droid.puzzle.ui.screen.PUZZLE_ROUTE
import people.droid.puzzle.ui.screen.PuzzleScreen
import people.droid.roulette.ui.ROULETTE_ROUTE
import people.droid.roulette.ui.RouletteScreen
import people.droid.roulette.ui.viewmodel.RouletteViewModel
import people.droid.untitled.ui.EnterToLeftTransition
import people.droid.untitled.ui.EnterToRightTransition
import people.droid.untitled.ui.ExitToLeftTransition
import people.droid.untitled.ui.ExitToRightTransition

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 시스템 창 삽입 여부를 비활성화
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val pixelArtViewModel = ViewModelProvider(this@MainActivity)[PixelArtViewModel::class.java]
        val rouletteViewModel = ViewModelProvider(this@MainActivity)[RouletteViewModel::class.java]

        lifecycleScope.launch(Dispatchers.IO) {
            MobileAds.initialize(this@MainActivity)
        }

        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            ChangeSystemBarsTheme()
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
                    composable(ROULETTE_ROUTE) {
                        RouletteScreen(
                            navController = navController,
                            viewModel = rouletteViewModel
                        )
                    }
                    composable(
                        PUZZLE_ROUTE,
                        enterTransition = EnterToLeftTransition(),
                        popEnterTransition = EnterToRightTransition(),
                        exitTransition = ExitToLeftTransition(),
                        popExitTransition = ExitToRightTransition()
                    ) {
                        PuzzleScreen(navigateBack = navController::popBackStack)
                    }
                }
            }
            LaunchedEffect(Unit) {
                WindowCompat.getInsetsController(window, window.decorView).run {
                    isAppearanceLightStatusBars = true
                    isAppearanceLightNavigationBars = false
                }
            }
        }
    }

    @Composable
    private fun ChangeSystemBarsTheme() {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
        )
    }
}