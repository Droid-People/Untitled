package people.droid.untitled.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import people.droid.untitled.R

const val DEVELOPERS_ROUTE = "developers"

@Composable
fun DeveloperScreen(navigateBack: () -> Unit = {}) {
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            IconButton(onClick = navigateBack) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 16.dp)
            ) {
//                Profile("kangmin", )
                Profile("whk06061", R.drawable.hyegyeong_profile)
                Profile("yewon-yw", R.drawable.yewon_profile)
            }
        }
    }
}

@Composable
fun Profile(name: String, imageResId: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .size(80.dp),
            painter = painterResource(imageResId),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(text = name, fontSize = 30.sp)
        Text(text = "Android Developer", fontSize = 12.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun DeveloperScreenPreview() {
    DeveloperScreen()
}