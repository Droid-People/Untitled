package people.droid.puzzle.ui.model

import people.droid.puzzle.R

enum class PieceColor(val index: Int, val resId: Int) {
    RED(0, R.drawable.colored_red),
    YELLOW(1, R.drawable.colored_yellow),
    GREEN(2, R.drawable.colored_green),
    BLUE(3, R.drawable.colored_blue);
}

fun getPieceColorResId(index: Int): Int =
    (PieceColor.entries.find { it.index == index } ?: PieceColor.GREEN).resId