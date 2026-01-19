package np.ict.mad.molerushbasic

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import np.ict.mad.molerushbasic.data.AppDatabase
import np.ict.mad.molerushbasic.data.User

@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val database = remember { AppDatabase.getDatabase(context) }
    val userDao = database.userDao()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Wack-A-Mole Login", fontSize = 30.sp, style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                scope.launch {
                    val user = userDao.getUser(username)
                    if (user != null && user.password == password) {
                        Toast.makeText(context, "Welcome back!", Toast.LENGTH_SHORT).show()
                        onLoginSuccess(username)
                    } else {
                        Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show()
                    }
                }
            }) {
                Text("LOGIN")
            }

            OutlinedButton(onClick = {
                scope.launch {
                    val existingUser = userDao.getUser(username)
                    if (existingUser != null) {
                        Toast.makeText(context, "Username already exists!", Toast.LENGTH_SHORT).show()
                    } else {
                        if (username.isNotEmpty() && password.isNotEmpty()) {
                            userDao.register(User(username = username, password = password))
                            Toast.makeText(context, "Account created! Please Login.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Please enter details", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }) {
                Text("REGISTER")
            }
        }
    }
}