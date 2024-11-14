package people.droid.pixelart.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ScaffoldWithBox(content: @Composable BoxScope.() -> Unit) {
    Scaffold {
        Box(
            Modifier
                .padding(it)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {
            content()
        }
    }
}