package people.droid.roulette.ui.viewmodel

import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import people.droid.roulette.domain.model.RouletteItem
import people.droid.roulette.domain.model.RouletteItems
import people.droid.roulette.ui.model.RouletteState
import people.droid.roulette.ui.model.RouletteUiState
import kotlin.random.Random

class RouletteViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RouletteUiState())
    val uiState = _uiState.asStateFlow()

    fun updateNumber(number: Int) {
        val newItems = RouletteItems.create(number)
        val currentItems = _uiState.value.rouletteItems.value.getItems()
        val minNum = minOf(newItems.getItems().size, currentItems.size)

        (0 until minNum).forEach {
            newItems.updateValue(it, currentItems[it].value)
        }
        _uiState.update {
            it.copy(
                sliceCount = mutableIntStateOf(number),
                rouletteItems = mutableStateOf(newItems)
            )
        }
    }

    private fun makeTargetSection() {
        val section = Random.nextInt(0, _uiState.value.sliceCount.value)
        _uiState.update {
            it.copy(
                targetSection = section
            )
        }
    }

    fun getRealTarget(): RouletteItem {
        val number = _uiState.value.sliceCount
        val targetSection = _uiState.value.targetSection
        return _uiState.value.rouletteItems.value.getItems()[(number.value - targetSection) % number.value]
    }

    fun updateRouletteItemValue(index: Int, value: String) {
        _uiState.value.rouletteItems.value.updateValue(index, value)
    }

    private fun makeRouletteStateProgressing() {
        _uiState.update {
            it.copy(
                rouletteState = RouletteState.PROGRESSING
            )
        }
    }

    private fun makeRouletteStateComplete() {
        _uiState.update {
            it.copy(
                rouletteState = RouletteState.COMPLETE
            )
        }
    }

    fun resetGame() {
        _uiState.update {
            it.copy(
                rouletteState = RouletteState.SETTING,
            )
        }
    }

    suspend fun initializeRotation() {
        _uiState.value.rotation.snapTo(0f)
    }

    suspend fun spin() {
        val baseRotation = 360 * Random.nextInt(3, 6)
        makeTargetSection()
        initializeRotation()
        makeRouletteStateProgressing()
        _uiState.value.rotation.animateTo(
            targetValue = (baseRotation + (_uiState.value.targetSection * (360 / _uiState.value.sliceCount.value))).toFloat(),
            animationSpec = tween(
                durationMillis = 3000,
                easing = EaseOut
            )
        )
        makeRouletteStateComplete()
    }
}