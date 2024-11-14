package com.example.fmtoy.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D

data class RouletteUiState(
    val number: Int = 2,
    val targetSection:Int = 1,
    val state: RouletteState = RouletteState.SETTING,
    val items: RouletteItems = RouletteItems.create(2),
    val rotation:Animatable<Float, AnimationVector1D> = Animatable(0f)
)

enum class RouletteState{
    SETTING, PROGRESSING, COMPLETE
}