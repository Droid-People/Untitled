package people.droid.pixelart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import people.droid.pixelart.ui.theme.PixelArtMakerTheme
import people.droid.pixelart.ui.theme.dialogBackgroundColor

@Composable
fun PixelClearCheckDialog(showClearDialog: MutableState<Boolean> = mutableStateOf(false), onConfirm: () -> Unit = {}) {
    if (showClearDialog.value) {
        Dialog(
            onDismissRequest = { showClearDialog.value = false }
        ) {
            Column(
                Modifier
                    .clip(RoundedCornerShape(32.dp))
                    .background(dialogBackgroundColor)
                    .width(200.dp)
                    .height(200.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Rounded.Delete, contentDescription = null, tint = Color.Black, modifier = Modifier.size(60.dp))
                    Text("?", style = MaterialTheme.typography.titleLarge)
                }
                Spacer(Modifier.height(20.dp))
                Button(
                    modifier = Modifier.border(2.dp, Color.Black, RoundedCornerShape(32.dp)).width(100.dp),
                    onClick = {
                        showClearDialog.value = false
                        onConfirm()
                    }
                ) {
                    Icon(Icons.Rounded.Check, contentDescription = null, tint = Color.Black, modifier = Modifier.size(40.dp))
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PixelClearCheckDialogPreview() {
    PixelArtMakerTheme {
        PixelClearCheckDialog(showClearDialog = remember { mutableStateOf(true) })
    }
}