package people.droid.ads.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BackButton(modifier: Modifier = Modifier, navigateBack: () -> Unit) {
    IconButton(
        modifier = modifier,
        onClick = navigateBack
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}