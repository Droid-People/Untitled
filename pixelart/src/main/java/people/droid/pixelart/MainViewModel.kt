package people.droid.pixelart

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@SuppressLint("AutoboxingStateCreation")
class MainViewModel: ViewModel() {
    val deque = ArrayDeque<Color>(18)
    val gridMap = mutableStateListOf<MutableList<Color>>()
    val pixelSize = MutableStateFlow(20)

    init {
        addAllWhite()

        // 빨주노초파남보검정
        deque.add(Color(0xFFFF0000))
        deque.add(Color(0xFFFF7F00))
        deque.add(Color(0xFFFFFF00))
        deque.add(Color(0xFF00FF00))
        deque.add(Color(0xFF007FFF))
        deque.add(Color(0xFF0000FF))
        deque.add(Color(0xFF7F00FF))
        deque.add(Color(0xFF000000))
    }

    private fun addAllWhite() {
        for (i in 0 until maxPixelCount) {
            val row = mutableListOf<Color>()
            for (j in 0 until maxPixelCount) {
                row.add(Color.White)
            }
            gridMap.add(row)
        }
    }

    fun changeBoardSize(actions: PixelBoardActions) {
        when (actions) {
            PixelBoardActions.INCREASE_PIXEL_SIZE -> {
                if (pixelSize.value >= 28) return
                pixelSize.value += 4
            }

            PixelBoardActions.DECREASE_PIXEL_SIZE -> {
                if (pixelSize.value <= 4) return
                pixelSize.value -= 4
            }
        }
    }

    fun clearPixels() {
        gridMap.clear()
        addAllWhite()
    }

    fun sharePixelImage(bitmap: Bitmap, contentResolver: ContentResolver, onStartActivity: (Intent) -> Unit) {
        contentResolver.apply {
            val imageUri = insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "PixelArt")
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            })
            openOutputStream(imageUri!!)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            }

            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, imageUri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                type = "image/*"
            }
            onStartActivity(shareIntent)
        }
    }
}