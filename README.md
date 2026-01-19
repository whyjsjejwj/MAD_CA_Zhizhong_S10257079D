# MAD-CA-Wack-A-Mole
This is a development done to satisfy the assignment portion for module “Mobile App development” under Ngee Ann Polytechnic for AY24/25.

## Project Overview
The project is divided into two distinct applications to demonstrate progression from basic concepts to advanced architecture:

### 1. Basic Version
* **Location:** `Basic/` folder
* **Package Name:** `np.ict.mad.molerushbasic`
* **Key Features:**
    * Classic 3x3 Grid Wack-a-Mole gameplay.
    * Countdown Timer (30 seconds) using `LaunchedEffect`.
    * Real-time scoring system.
    * **Data Persistence:** Uses `SharedPreferences` to save and retrieve the High Score locally.
    * Simple, responsive UI built with Jetpack Compose.

### 2. Advanced Version
* **Location:** `Advanced/` folder
* **Package Name:** `np.ict.mad.molerushadvanced` (Application ID)
* **Key Features:**
    * **User Authentication:** Full **Register** and **Login** system.
    * **Database Integration:** Implemented **Room Database** for secure storage of User credentials and personal High Scores.
    * **Advanced Navigation:** Logic to handle flow between `LoginScreen` -> `GameScreen` -> `SettingsScreen`.
    * **Architecture:** Separation of concerns using Data Access Objects (DAO), Entity classes, and Repository patterns.
    * **Settings Menu:** Dedicated screen for game configuration (framework).

---

## Implemented Features
| Feature | Basic Version | Advanced Version |
| :--- | :--- | :--- |
| **UI Framework** | Jetpack Compose | Jetpack Compose |
| **State Management** | `mutableStateOf` | `mutableStateOf` & Data Classes |
| **Data Storage** | `SharedPreferences` | **Room Database** |
| **Navigation** | Conditional Rendering (`if/else`) | Screen State Logic (Login/Game/Settings) |
| **Authentication** | None | **Login & Registration** |

---

## Use of AI Tools and Learning Reflection
### 1. AI Tools Used
**ChatGPT / Gemini** were used as the debugging assistants. The AI helped explain complex concepts (like Room Database setup) and troubleshoot build errors, while I wrote and integrated the final code logic.

### 2. Example Prompts Used
Specific questions I asked during development included:
* *"My app is running the old version even after I changed the code. How do I fix the package name mismatch?"*
* *"How do I copy an Android project to a new folder without breaking the gradle build?"*
* *"How do I implement a Login Screen using Room Database in Jetpack Compose?"*
* *"What is the correct KSP version for my Kotlin installation?"*

### 3. Code Generation & Refinement
The AI provided initial code snippets for individual components (like the Timer or the Authentication setup), but I had to significantly modify and integrate them to build a cohesive application architecture.

#### **Example A: Evolving the Game Loop Logic**
**The AI Suggestion:**
The AI initially provided a simple countdown timer loop. However, this was insufficient because the "Mole" needed to move at a different speed than the timer (e.g., the tierm ticks every 1 second, but the mole moves every 0.7 seconds).

*Initial AI Snippet (Single Loop):*
```kotlin
// AI generated simple loop
while (timeLeft > 0) {
    delay(1000)
    timeLeft--
    // Mole moves at the same speed as the timer (Problematic)
    currentMoleIndex = Random.nextInt(9)
}
```
**My Refinement:** I modified the code to use two separate LaunchedEffect coroutines. This decoupled the timer from the game mechanics, allowing the Mole to move randomly and faster than the countdown clock without freezing the UI.

*Refined Code:*
```kotlin
LaunchedEffect(isPlaying) {
    while (timeLeft > 0) {
        delay(1000)
        timeLeft--
    }
    isPlaying = false
}

// 2. Mole Movement Loop (Runs independently, faster)
LaunchedEffect(isPlaying) {
    while (timeLeft > 0) {
        delay(Random.nextLong(700, 1000))
        currentMoleIndex = Random.nextInt(9)
    }
}
```

#### **Example B: Handling Duplicate User Registration**
**The AI Suggestion:**
The AI provided a basic `register` function that directly inserted user data into the Room database. However, this logic was flawed because it didn't check if a username already existed. If a user tried to register a name that was already taken, the app would crash due to a **Primary Key Constraint violation**.

*Initial AI Snippet (Unsafe Insert):*
```kotlin
// AI suggestion: Inserts blindly
Button(onClick = {
    // CRITICAL BUG: This crashes the app if "username" already exists in the DB
    userDao.register(User(username = username, password = password))
})
```

**My Refinement:** I refined the code to implement a "Check-Then-Act" validation flow. I wrapped the database operation in a Coroutine and added a pre-check query (getUser). The app now verifies if the username exists before attempting to insert it. If it exists, it gracefully shows a Toast error instead of crashing.

*Refined Code:*
```kotlin
scope.launch {
    val existingUser = userDao.getUser(username)
    
    if (existingUser != null) {
        // 2. Handle duplicate error gracefully
        Toast.makeText(context, "Username already exists!", Toast.LENGTH_SHORT).show()
    } else {
        // 3. Only insert if unique
        userDao.register(User(username = username, password = password))
        Toast.makeText(context, "Account Created", Toast.LENGTH_SHORT).show()
    }
}
```

---

### 4. Key Takeaways
* **File Management:** I learned that you cannot just copy-paste an entire Android project folder; you must exclude the `.gradle` and `build` folders to avoid "Path Too Long" errors on Windows.
* **State Management:** I gained a better understanding of how `var currentScreen by remember { mutableStateOf(...) }` controls the entire app flow without needing complex navigation libraries.
* **Database Safety:** I learned that `SharedPreferences` is good for simple high scores, but `Room Database` is necessary for structured user data like passwords and accounts.

---

## How to Run
1.  Open **Android Studio**.
2.  Select **Open** and choose either the `Basic` or `Advanced` folder.
3.  Allow Gradle to Sync.
4.  Select a device (Emulator) and click **Run**.