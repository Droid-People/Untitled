package people.droid.untitled.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import people.droid.untitled.R
import people.droid.untitled.ui.theme.UntitledTheme
import people.droid.untitled.ui.theme.YellowBackground

data class Feature(
    val title: String,
    val route: String,
)

@Composable
fun FeatureGrid(
    modifier: Modifier = Modifier,
    features: List<Feature>,
    navController: NavController,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.SpaceEvenly,
        contentPadding = PaddingValues(16.dp)
    ) {
        items(features) { feature ->
            FeatureItem(
                onClick = { navController.navigate(feature.route) },
                title = feature.title
            )
        }
    }
}

@Composable
fun FeatureItem(
    onClick: () -> Unit = {},
    title: String = "",
) {
    Column(
        modifier = Modifier
            .width(80.dp)
            .padding(vertical = 12.dp)
            .clickable { onClick() }
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .background(YellowBackground)
                .border(2.dp, Color.Black, CircleShape)
                .padding(8.dp),
            painter = painterResource(R.drawable.trash_01),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FeatureGridPreview() {
    UntitledTheme {
        FeatureGrid(
            features = listOf(
                Feature("feature1", "feature"),
                Feature("feature2", "feature"),
                Feature("feature3", "feature"),
                Feature("feature4", "feature")
            ),
            navController = rememberNavController()
        )
    }
}
