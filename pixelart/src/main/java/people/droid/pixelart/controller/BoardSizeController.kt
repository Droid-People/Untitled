package people.droid.pixelart.controller

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import people.droid.pixelart.PixelBoardActions
import people.droid.pixelart.maxPixelCount
import people.droid.pixelart.ui.theme.PixelArtMakerTheme

@Composable
fun PixelSizeController(
    pixel: Int,
    onChangePixelBoardSize: (PixelBoardActions) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
    ) {
        Icon(
            Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    onChangePixelBoardSize(PixelBoardActions.DECREASE_PIXEL_SIZE)
                }
        )
        val pixelSize = maxPixelCount / pixel
        Text(
            "$pixelSize X $pixelSize", fontSize = 24.sp, color = Color.Black, fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(8.dp)
                .border(2.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
        Icon(
            Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    onChangePixelBoardSize(PixelBoardActions.INCREASE_PIXEL_SIZE)
                }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PixelSizeControllerPreview() {
    PixelArtMakerTheme {
        PixelSizeController(20) {}
    }
}