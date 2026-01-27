package np.ict.mad.molerushbasic.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun register(user: User)

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUser(username: String): User?

    @Query("UPDATE users SET highScore = :newScore WHERE username = :username")
    suspend fun updateScore(username: String, newScore: Int)

    @Query("SELECT * FROM users ORDER BY highScore DESC LIMIT 10")
    suspend fun getTopScores(): List<User>
}