package people.droid.roulette.domain.model

import androidx.compose.ui.graphics.Color
import people.droid.roulette.ui.theme.RouletteBlue
import people.droid.roulette.ui.theme.RoulettePink
import people.droid.roulette.ui.theme.RouletteYellow

class RouletteItems private constructor(
    private val items: MutableList<RouletteItem>
) {

    fun updateValue(index: Int, value: String) {
        this.items.forEachIndexed { i, item ->
            if (i == index) {
                items[i] = items[i].copy(value = value)
            }
        }
    }

    fun getItems(): List<RouletteItem> = items.toList()

    fun getDegreesPerItem(): Float = 360f / items.size

    companion object {

        private val colors = listOf(
            RouletteYellow,
            RouletteBlue,
            RoulettePink,
        )

        fun create(number: Int): RouletteItems {
            var previousColor: Color? = null
            val items = List(number) { index ->
                val color = when (index) {
                    0 -> colors[0]
                    number - 1 -> {
                        colors.filter { it != colors[0] && it != previousColor }[0]
                    }

                    else -> {
                        colors[index % colors.size]
                    }
                }
                previousColor = color
                RouletteItem(
                    color = color
                )
            }

            return RouletteItems(items.toMutableList())
        }
    }
}