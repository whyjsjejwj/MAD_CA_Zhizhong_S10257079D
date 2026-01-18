package np.ict.mad.molerushbasic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MoleHole(
    isTarget: Boolean,
    isPlaying: Boolean,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isTarget) 8.dp else 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isTarget) Color(0xFFFF5252) else Color(0xFFF0F0F0)
        ),
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(enabled = isPlaying) {
                onClick()
            }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isTarget) {
                Text(
                    text = "🐹",
                    fontSize = 50.sp
                )
            }
        }
    }
}