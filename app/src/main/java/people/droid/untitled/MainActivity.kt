package people.droid.untitled

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
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
import people.droid.untitled.ui.EnterToLeftTransition
import people.droid.untitled.ui.EnterToRightTransition
import people.droid.untitled.ui.ExitToLeftTransition
import people.droid.untitled.ui.ExitToRightTransition
import people.droid.untitled.ui.theme.YellowBackground

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 시스템 창 삽입 여부를 비활성화
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val pixelArtViewModel = ViewModelProvider(this@MainActivity)[PixelArtViewModel::class.java]

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
                }
            }
        }
    }

    @Composable
    private fun ChangeSystemBarsTheme() {
        val barColor = YellowBackground.toArgb()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                barColor,
            ),
            navigationBarStyle = SystemBarStyle.dark(
                barColor,
            ),
        )
    }
}