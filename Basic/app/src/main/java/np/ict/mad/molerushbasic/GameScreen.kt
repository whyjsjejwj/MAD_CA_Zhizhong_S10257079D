package np.ict.mad.molerushbasic

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    onNavigateToSettings: () -> Unit
) {

    val context = LocalContext.current


    var score by remember { mutableIntStateOf(0) }
    var timeLeft by remember { mutableIntStateOf(30) }
    var isPlaying by remember { mutableStateOf(false) }
    var targetIndex by remember { mutableIntStateOf(-1) }
    var showGameOverDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = isPlaying) {
        if (isPlaying) {
            while (timeLeft > 0) {
                delay(1000L)
                timeLeft--
            }
            isPlaying = false

            showGameOverDialog = true
            targetIndex = -1
        }
    }

    LaunchedEffect(key1 = targetIndex, key2 = isPlaying) {
        if (isPlaying) {
            val randomDelay = Random.nextLong(700, 1000)
            delay(randomDelay)
            targetIndex = Random.nextInt(9)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "WHACK-A-MOLE",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            IconButton(onClick = onNavigateToSettings) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("SCORE", fontSize = 14.sp, color = Color.Gray)
                    Text("$score", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("TIME", fontSize = 14.sp, color = Color.Gray)
                    Text("${timeLeft}s", fontSize = 28.sp, fontWeight = FontWeight.Bold,
                        color = if (timeLeft < 10) Color.Red else Color.Black)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            userScrollEnabled = false,
            contentPadding = PaddingValues(4.dp),
            modifier = Modifier.fillMaxWidth().aspectRatio(1f)
        ) {
            items(9) { index ->
                MoleHole(
                    isTarget = (index == targetIndex),
                    isPlaying = isPlaying,
                    onClick = {
                        if (isPlaying && index == targetIndex) {
                            score++
                            targetIndex = Random.nextInt(9)
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                score = 0
                timeLeft = 30
                isPlaying = true
                showGameOverDialog = false
                targetIndex = Random.nextInt(9)
            },
            enabled = !isPlaying,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text(text = if (isPlaying) "PLAYING..." else "START GAME", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                isPlaying = false
                score = 0
                timeLeft = 30
                targetIndex = -1
            },
            enabled = isPlaying,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red, disabledContainerColor = Color.LightGray)
        ) {
            Text(text = "RESET GAME", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        if (showGameOverDialog) {
            AlertDialog(
                onDismissRequest = { showGameOverDialog = false },
                title = { Text("Game Over!") },
                text = { Text("Final Score: $score") },
                confirmButton = {
                    TextButton(onClick = { showGameOverDialog = false }) { Text("Close") }
                }
            )
        }
    }
}