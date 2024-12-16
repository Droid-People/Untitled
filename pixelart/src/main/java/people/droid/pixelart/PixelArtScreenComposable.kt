package people.droid.pixelart

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import people.droid.pixelart.ui.theme.PixelArtMakerTheme

const val PIXEL_ART_ROUTE = "pixel_art"

@Composable
fun PixelArtScreenComposable(navController: NavHostController, viewModel: PixelArtViewModel) {
    val activity = LocalContext.current as ComponentActivity

    PixelArtMakerTheme {
        PixelArtScreen(
            colorQueue = viewModel.deque,
            gridMap = viewModel.gridMap,
            pixelSizeFlow = viewModel.pixelSize,
            onChangePixelBoardSize = viewModel::changeBoardSize,
            onClearPixels = viewModel::clearPixels,
            onSharePixelImage = { bitmap ->
                viewModel.sharePixelImage(bitmap, activity.contentResolver) { intent ->
                    activity.startActivity(Intent.createChooser(intent,
                        activity.getString(R.string.share_image)))
                }
            },
            onClose = {
                navController.popBackStack()
            }
        )
    }
}