package people.droid.roulette.ui.model

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import people.droid.roulette.domain.model.RouletteItems

data class RouletteUiState(
    val sliceCount: MutableState<Int> = mutableIntStateOf(2),
    val targetSection:Int = 1,
    val rouletteState: RouletteState = RouletteState.SETTING,
    val rouletteItems: MutableState<RouletteItems> = mutableStateOf(RouletteItems.create(2)) ,
    val rotation:Animatable<Float, AnimationVector1D> = Animatable(0f)
)

enum class RouletteState{
    SETTING, PROGRESSING, COMPLETE
}