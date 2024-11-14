package people.droid.untitled

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import people.droid.untitled.ui.theme.UntitledTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UntitledTheme {
                Scaffold {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                val intent = Intent(
                                    this@MainActivity,
                                    people.droid.pixelart.MainActivity::class.java
                                )
                                startActivity(intent)
                            }
                        ) {
                            Text("Pixel Art Maker")
                        }
                        Button(
                            onClick = {
                                val intent = Intent(
                                    this@MainActivity,
                                    people.droid.roulette.MainActivity::class.java
                                )
                                startActivity(intent)
                            }
                        ) {
                            Text("Roulette")
                        }
                        Button(
                            onClick = {
                                val intent = Intent(
                                    this@MainActivity,
                                    people.droid.puzzle.MainActivity::class.java
                                )
                                startActivity(intent)
                            }
                        ) {
                            Text("Puzzle")
                        }
                    }
                }
            }
        }
    }
}