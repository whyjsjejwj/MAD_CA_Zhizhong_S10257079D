package np.ict.mad.molerushbasic

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Title
        Text(text = "Whack-A-Mole", fontSize = 24.sp)

        // 2. Stats Row (Score & Time)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Score: 0", fontSize = 20.sp)
            Text(text = "Time: 30", fontSize = 20.sp)
        }

        // 3. The 3x3 Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(350.dp)
        ) {
            items(9) {
                MoleHole(
                    isTarget = false, // Placeholder
                    isPlaying = false, // Placeholder
                    onClick = { }
                )
            }
        }

        // 4. Start Button
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text(text = "START GAME")
        }
    }
}