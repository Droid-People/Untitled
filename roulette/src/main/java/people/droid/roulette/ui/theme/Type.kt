package people.droid.roulette.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import people.droid.roulette.R

val fontFamily = FontFamily(
    Font(R.font.gamjaflower_regular, FontWeight.Normal),
)

val Typography = Typography(
    headlineLarge = TextStyle(
        fontSize = 38.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold
    ),
    titleSmall = TextStyle(
        fontSize = 20.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold
    ),
    titleMedium = TextStyle(
        fontSize = 24.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold
    ),
    titleLarge = TextStyle(
        fontSize = 30.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold
    )
)