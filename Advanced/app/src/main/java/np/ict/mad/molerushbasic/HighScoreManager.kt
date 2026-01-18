package np.ict.mad.molerushbasic

import android.content.Context
import android.content.SharedPreferences

class HighScoreManager(context: Context) {
    private val PREFS_NAME = "mole_rush_prefs"
    private val KEY_HIGH_SCORE = "high_score"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveHighScore(score: Int) {
        val currentHigh = getHighScore()
        if (score > currentHigh) {
            prefs.edit().putInt(KEY_HIGH_SCORE, score).apply()
        }
    }

    fun getHighScore(): Int {
        return prefs.getInt(KEY_HIGH_SCORE, 0)
    }

    fun clearHighScore() {
        prefs.edit().remove(KEY_HIGH_SCORE).apply()
    }
}