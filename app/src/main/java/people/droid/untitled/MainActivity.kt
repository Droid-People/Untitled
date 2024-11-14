package people.droid.untitled

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import people.droid.untitled.ui.theme.UntitledTheme

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UntitledTheme {

            }
        }
    }
}