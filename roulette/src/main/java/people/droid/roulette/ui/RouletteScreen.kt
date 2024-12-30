package people.droid.roulette.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import people.droid.roulette.ui.component.Roulette
import people.droid.roulette.ui.component.RouletteButton
import people.droid.roulette.domain.model.RouletteItem
import people.droid.roulette.domain.model.RouletteItems
import people.droid.roulette.ui.component.RouletteSettingTextField
import people.droid.roulette.ui.model.RouletteState
import people.droid.roulette.ui.viewmodel.RouletteViewModel
import people.droid.roulette.ui.theme.RouletteTheme
import people.droid.roulette.ui.theme.RouletteBeige
import people.droid.roulette.ui.theme.RouletteBlue
import people.droid.roulette.ui.theme.RouletteBrown
import kotlinx.coroutines.launch
import people.droid.roulette.ui.theme.RoulettePink

const val ROULETTE_ROUTE = "roulette"

@Composable
fun RouletteScreen(
    popBackStack: () -> Unit,
    viewModel: RouletteViewModel
) {
    val focusManager = LocalFocusManager.current

    val uiState = viewModel.uiState.collectAsState().value
    val state = uiState.rouletteState
    val rotation = uiState.rotation

    var itemTexts by remember(uiState.rouletteItems) {
        mutableStateOf(uiState.rouletteItems.value.getItems().map { it.value })
    }
    var itemsFocusedInfo by remember(uiState.sliceCount) {
        mutableStateOf(List(uiState.sliceCount.value) { false })
    }
    val scope = rememberCoroutineScope()
    RouletteTheme {
        RouletteScreenUi(
            popBackStack = popBackStack,
            focusManager = focusManager,
            totalNumberTextFieldValue = uiState.sliceCount.value,
            totalNumberChange = {
                viewModel.updateNumber(it)
            },
            rouletteItems = uiState.rouletteItems.value,
            onRouletteItemChanged = { index, value ->
                val updatedItems = itemTexts.toMutableList().apply { this[index] = value }
                itemTexts = updatedItems
                viewModel.updateRouletteItemValue(index, value)
            },
            rotation = rotation,
            state = state,
            onResetButtonClick = {
                viewModel.resetGame()
                scope.launch {
                    viewModel.initializeRotation()
                }

            },
            onSpinButtonClick = {
                scope.launch {
                    viewModel.spin()
                }

            },
            realTarget = viewModel.getRealTarget(),
            itemTexts = itemTexts,
            itemsFocusedInfo = itemsFocusedInfo,
            onFocusChanged = { index, value ->
                val updatedItems = itemsFocusedInfo.toMutableList().apply { this[index] = value }
                itemsFocusedInfo = updatedItems
            }
        )
    }

}

@Composable
private fun RouletteScreenUi(
    popBackStack: () -> Unit,
    focusManager: FocusManager,
    totalNumberTextFieldValue: Int,
    totalNumberChange: (Int) -> Unit,
    rouletteItems: RouletteItems,
    rotation: Animatable<Float, AnimationVector1D>,
    onRouletteItemChanged: (Int, String) -> Unit,
    state: RouletteState,
    onResetButtonClick: () -> Unit,
    onSpinButtonClick: () -> Unit,
    realTarget: RouletteItem,
    itemTexts: List<String>,
    itemsFocusedInfo: List<Boolean>,
    onFocusChanged: (Int, Boolean) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager),
    ) { innerPadding ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(RouletteBeige)
                .padding(innerPadding)
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.Start),
                onClick = popBackStack,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = RouletteBrown
                )
            }
            RouletteTitle()
            Spacer(modifier = Modifier.height(20.dp))
            when (state) {

                RouletteState.SETTING -> {
                    TotalNumberInputScreen(number = totalNumberTextFieldValue, totalNumberChange)
                }

                RouletteState.PROGRESSING -> {
                    Spacer(modifier = Modifier.height(20.dp))
                    RestartRouletteButton(false, {})
                }

                RouletteState.COMPLETE -> {
                    Spacer(modifier = Modifier.height(20.dp))
                    RestartRouletteButton(
                        onResetButtonClick = onResetButtonClick,
                        onSpinAgainButtonClick = onSpinButtonClick
                    )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            Roulette(
                items = rouletteItems,
                rotation = rotation,
                state = state,
                onTextChanged = onRouletteItemChanged,
                itemTexts = itemTexts,
                itemsFocusedInfo = itemsFocusedInfo,
                onFocusChanged = onFocusChanged,
            )
            Spacer(modifier = Modifier.height(40.dp))
            Spacer(modifier = Modifier.height(20.dp))
            when (state) {
                RouletteState.SETTING ->
                    RotationRouletteButton(focusManager, onSpinButtonClick)

                RouletteState.PROGRESSING -> {
                    Text(
                        text = "Spinning in Progress...",
                        style = MaterialTheme.typography.titleMedium.copy(color = RouletteBrown)
                    )
                }

                RouletteState.COMPLETE -> {
                    Text(text =
                    buildAnnotatedString {
                        withStyle(
                            style = MaterialTheme.typography.titleLarge
                                .copy(color = RouletteBlue)
                                .toSpanStyle(),
                        ) {
                            append(realTarget.value)
                        }
                        withStyle(
                            style = MaterialTheme.typography.titleMedium
                                .copy(color = RouletteBrown)
                                .toSpanStyle()
                        ) {
                            append(" : You Got It!")
                        }
                    }
                    )


                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.padding(bottom = 30.dp),
                text = "by whk06061",
                style = MaterialTheme.typography.titleSmall.copy(color = RouletteBlue)
            )
        }
    }
}

@Composable
private fun RouletteTitle() {
    val style = MaterialTheme.typography.headlineLarge.toSpanStyle()
    val colors = listOf(RouletteBrown, RoulettePink, RouletteBlue)
    Text(text =
    buildAnnotatedString {
        arrayOf("Spin ", "the ", "Wheel!").forEachIndexed { index, c ->
            withStyle(style = style.copy(color = colors[index % colors.size])) {
                append(c)
            }
        }
    }
    )
}

@Composable
private fun Modifier.addFocusCleaner(focusManager: FocusManager): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            focusManager.clearFocus()
        })
    }
}

@Composable
private fun TotalNumberInputScreen(
    number: Int,
    onValueChange: (Int) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Total",
            style = MaterialTheme.typography.titleSmall,
            color = RouletteBrown
        )
        Spacer(modifier = Modifier.height(5.dp))
        RouletteSettingTextField(
            number = number,
            onValueChange = {
                onValueChange(it)
            }
        )
    }
}


@Composable
private fun RotationRouletteButton(
    focusManager: FocusManager,
    onClick: () -> Unit
) {
    RouletteButton(
        modifier = Modifier.width(200.dp),
        text = "Start",
        onClick = {
            focusManager.clearFocus()
            onClick()
        }
    )
}

@Composable
private fun RestartRouletteButton(
    enabled: Boolean = true,
    onResetButtonClick: () -> Unit = {},
    onSpinAgainButtonClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        RouletteButton(
            enabled = enabled,
            text = "Reset",
            onClick = onResetButtonClick
        )
        RouletteButton(
            enabled = enabled,
            text = "Spin Again",
            onClick = onSpinAgainButtonClick
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {

    val focusManager = LocalFocusManager.current
    val itemNumber = 3
    val items = RouletteItems.create(itemNumber)
    val rotation = remember {
        Animatable(0f)
    }
    RouletteTheme {
        RouletteScreenUi(
            popBackStack = {},
            focusManager = focusManager,
            totalNumberTextFieldValue = itemNumber,
            totalNumberChange = {},
            rouletteItems = items,
            rotation = rotation,
            onRouletteItemChanged = { _, _ -> },
            state = RouletteState.COMPLETE,
            onSpinButtonClick = {},
            realTarget = items.getItems()[0], onResetButtonClick = {},
            itemTexts = listOf(),
            itemsFocusedInfo = listOf(),
            onFocusChanged = { _, _ -> }
        )
    }
}