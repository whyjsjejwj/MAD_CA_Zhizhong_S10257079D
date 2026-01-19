package np.ict.mad.molerushbasic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    var isLoggedIn by remember { mutableStateOf(false) }
                    var currentScreen by remember { mutableStateOf("game") }
                    var currentUser by remember { mutableStateOf("") }

                    if (!isLoggedIn) {

                        LoginScreen(
                            onLoginSuccess = { username ->
                                currentUser = username
                                isLoggedIn = true
                            }
                        )
                    } else {

                        if (currentScreen == "game") {
                            GameScreen(
                                modifier = Modifier.padding(innerPadding),
                                onNavigateToSettings = { currentScreen = "settings" }
                            )
                        } else {
                            SettingsScreen(
                                modifier = Modifier.padding(innerPadding),
                                onNavigateBack = { currentScreen = "game" }
                            )
                        }
                    }
                }
            }
        }
    }
}