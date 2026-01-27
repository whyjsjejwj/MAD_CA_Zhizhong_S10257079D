package np.ict.mad.molerushbasic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import np.ict.mad.molerushbasic.data.AppDatabase
import np.ict.mad.molerushbasic.data.User

@Composable
fun LeaderboardScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    val userDao = remember { AppDatabase.getDatabase(context).userDao() }

    var topPlayers by remember { mutableStateOf(emptyList<User>()) }

    LaunchedEffect(Unit) {
        topPlayers = userDao.getTopScores()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🏆 Hall of Fame 🏆", fontSize = 32.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth().background(Color.LightGray, RoundedCornerShape(4.dp)).padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Rank", fontWeight = FontWeight.Bold)
            Text("Player", fontWeight = FontWeight.Bold)
            Text("Score", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            itemsIndexed(topPlayers) { index, user ->
                LeaderboardRow(rank = index + 1, user = user)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = onNavigateBack, modifier = Modifier.fillMaxWidth()) {
            Text("BACK TO GAME")
        }
    }
}

@Composable
fun LeaderboardRow(rank: Int, user: User) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("#$rank")
            Text(user.username, fontWeight = FontWeight.Bold)
            Text("${user.highScore} pts")
        }
    }
}