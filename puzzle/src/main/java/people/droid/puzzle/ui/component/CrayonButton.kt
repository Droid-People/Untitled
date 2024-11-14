package people.droid.puzzle.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import people.droid.puzzle.R
import people.droid.puzzle.ui.theme.Typography

@Composable
fun CrayonButton(
    onClick: () -> Unit,
    enable: Boolean,
    color: ButtonColor,
    text: String,
    width: Int = 100
) {
    Box(
        modifier = Modifier.clickable { if (enable) onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.width(width.dp),
            painter = painterResource(id = if (enable) color.resId else R.drawable.gray_button),
            contentDescription = null
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            style = Typography.bodyMedium,
            color = Color.Black
        )
    }
}

enum class ButtonColor(val resId: Int) {
    ORANGE(R.drawable.orange_button),
    RED(R.drawable.red_button),
    BLUE(R.drawable.blue_button)
}