package people.droid.pixelart

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import people.droid.pixelart.ui.theme.PixelArtMakerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setContent {
            PixelArtMakerTheme {
                MainScreen(
                    colorQueue = viewModel.deque,
                    gridMap = viewModel.gridMap,
                    pixelSizeFlow = viewModel.pixelSize,
                    onChangePixelBoardSize = viewModel::changeBoardSize,
                    onClearPixels = viewModel::clearPixels,
                    onSharePixelImage = { bitmap ->
                        viewModel.sharePixelImage(bitmap, contentResolver) { intent ->
                            startActivity(Intent.createChooser(intent, "Share Image"))
                        }
                    }
                )
            }
        }
    }
}
