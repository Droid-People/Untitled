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
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.MobileAds
import people.droid.ads.ADS_ROUTE
import people.droid.ads.adsNavGraph
import people.droid.ads.ui.screen.AdsScreen
import people.droid.ads.viewmodel.NativeAdViewModel
import people.droid.pixelart.PIXEL_ART_ROUTE
import people.droid.pixelart.PixelArtScreenComposable
import people.droid.pixelart.PixelArtViewModel
import people.droid.puzzle.ui.screen.PUZZLE_ROUTE
import people.droid.puzzle.ui.screen.PuzzleScreen
import people.droid.roulette.ui.ROULETTE_ROUTE
import people.droid.roulette.ui.RouletteScreen
import people.droid.roulette.ui.viewmodel.RouletteViewModel
import people.droid.untitled.data.FirebaseDB
import people.droid.untitled.data.RemoteFeedbackRepository
import people.droid.untitled.release_note.RELEASE_NOTES_ROUTE
import people.droid.untitled.release_note.ReleaseNoteScreenComposable
import people.droid.untitled.release_note.ReleaseNoteViewModel
import people.droid.untitled.ui.DEVELOPERS_ROUTE
import people.droid.untitled.ui.DeveloperScreen
import people.droid.untitled.ui.EnterToLeftTransition
import people.droid.untitled.ui.EnterToRightTransition
import people.droid.untitled.ui.ExitToLeftTransition
import people.droid.untitled.ui.ExitToRightTransition
import people.droid.untitled.ui.FEEDBACK_ROUTE
import people.droid.untitled.ui.FeedbackScreen
import people.droid.untitled.ui.FeedbackViewModel


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val pixelArtViewModel = ViewModelProvider(this@MainActivity)[PixelArtViewModel::class.java]
        val rouletteViewModel = ViewModelProvider(this@MainActivity)[RouletteViewModel::class.java]
        val releaseNoteViewModel = ViewModelProvider(this@MainActivity)[ReleaseNoteViewModel::class.java]
        val feedbackViewModel = ViewModelProvider.create(
            this@MainActivity,
            FeedbackViewModel.Factory,
            MutableCreationExtras().apply {
                set(FeedbackViewModel.FEEDBACK_REPOSITORY_KEY, RemoteFeedbackRepository(FirebaseDB()))
            }
        )[FeedbackViewModel::class.java]
        val nativeAdViewModel = ViewModelProvider(this@MainActivity)[NativeAdViewModel::class.java]

        MobileAds.initialize(this@MainActivity)

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
                        HomeScreen(navController::navigate)
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
                    composable(
                        ROULETTE_ROUTE,
                        enterTransition = EnterToLeftTransition(),
                        popEnterTransition = EnterToRightTransition(),
                        exitTransition = ExitToLeftTransition(),
                        popExitTransition = ExitToRightTransition()
                    ) {
                        RouletteScreen(
                            popBackStack = navController::popBackStack,
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
                    composable(
                        DEVELOPERS_ROUTE,
                        enterTransition = EnterToLeftTransition(),
                        popEnterTransition = EnterToRightTransition(),
                        exitTransition = ExitToLeftTransition(),
                        popExitTransition = ExitToRightTransition()
                    ) {
                        DeveloperScreen(navigateBack = navController::popBackStack)
                    }
                    composable(
                        RELEASE_NOTES_ROUTE,
                        enterTransition = EnterToLeftTransition(),
                        popEnterTransition = EnterToRightTransition(),
                        exitTransition = ExitToLeftTransition(),
                        popExitTransition = ExitToRightTransition()
                    ) {
                        ReleaseNoteScreenComposable(
                            viewModel = releaseNoteViewModel,
                            navigateBack = navController::popBackStack
                        )
                    }
                    composable(
                        FEEDBACK_ROUTE,
                        enterTransition = EnterToLeftTransition(),
                        popEnterTransition = EnterToRightTransition(),
                        exitTransition = ExitToLeftTransition(),
                        popExitTransition = ExitToRightTransition()
                    ) {
                        FeedbackScreen(feedbackViewModel, navController::popBackStack)
                    }
                    composable(
                        ADS_ROUTE,
                        enterTransition = EnterToLeftTransition(),
                        popEnterTransition = EnterToRightTransition(),
                        exitTransition = ExitToLeftTransition(),
                        popExitTransition = ExitToRightTransition()
                    ) {
                        AdsScreen(navigate = navController::navigate, navigateBack = navController::popBackStack)
                    }
                    adsNavGraph(navController::popBackStack, nativeAdViewModel)
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