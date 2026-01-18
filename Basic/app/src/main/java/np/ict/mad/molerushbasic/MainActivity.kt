package np.ict.mad.molerushbasic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                var currentScreen by remember { mutableStateOf("game") }

                if (currentScreen == "game") {
                    GameScreen(
                        onNavigateToSettings = { }
                    )
                }
            }
        }
    }
}