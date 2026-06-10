package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.theme.CP3406_CP5603UtilityAppStarterTemplateTheme
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CP3406_CP5603UtilityAppStarterTemplateTheme {
                UtilityApp()
            }
        }
    }
}

data class RunRecord(
    val date: String,
    val time: String,
    val duration: String,
    val distance: String,
    val pace: String,
    val calories: String
)

@Composable
fun UtilityApp() {
    var selectedTab by remember { mutableStateOf("Home") }
    var runRecords by remember { mutableStateOf(listOf<RunRecord>()) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedTab == "Home",
                    onClick = { selectedTab = "Home" }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.PlayArrow, contentDescription = "Run") },
                    label = { Text("Run") },
                    selected = selectedTab == "Run",
                    onClick = { selectedTab = "Run" }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "Records") },
                    label = { Text("Records") },
                    selected = selectedTab == "Records",
                    onClick = { selectedTab = "Records" }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    selected = selectedTab == "Settings",
                    onClick = { selectedTab = "Settings" }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                "Home" -> HomeScreen(runRecords)
                "Run" -> RunScreen { record ->
                    runRecords = runRecords + record
                }
                "Records" -> RecordsScreen(runRecords)
                "Settings" -> SettingsScreen()
            }
        }
    }
}

@Composable
fun HomeScreen(runRecords: List<RunRecord>) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.running_background),
            contentDescription = "Running background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text = "Running Tracker",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Quick Statistics")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Total Runs: ${runRecords.size}")
                    Text("Today Distance: 0.00 km")
                    Text("Total Calories: 0 kcal")
                    Text("Weather: Coming soon")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Motivation")
                    Text("Every run gets you closer to your goal.")
                }
            }
        }
    }
}

@Composable
fun RunScreen(
    onSaveRun: (RunRecord) -> Unit
) {
    var runState by remember { mutableStateOf("Not Started") }
    var elapsedSeconds by remember { mutableIntStateOf(0) }

    LaunchedEffect(runState) {
        while (runState == "Running") {
            delay(1000)
            elapsedSeconds++
        }
    }

    val hours = elapsedSeconds / 3600
    val minutes = (elapsedSeconds % 3600) / 60
    val seconds = elapsedSeconds % 60
    val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Run Tracker", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Status: $runState")
                Text("Time: $formattedTime")
                Text("Distance: 0.00 km")
                Text("Pace: 0:00 /km")
                Text("Speed: 0.0 km/h")
                Text("Calories: 0 kcal")
                Text("Steps: 0")
                Text("Weather: Coming soon")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                elapsedSeconds = 0
                runState = "Running"
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = runState == "Not Started" || runState == "Ended"
        ) {
            Text("Start Run")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { runState = "Paused" },
            modifier = Modifier.fillMaxWidth(),
            enabled = runState == "Running"
        ) {
            Text("Pause")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { runState = "Running" },
            modifier = Modifier.fillMaxWidth(),
            enabled = runState == "Paused"
        ) {
            Text("Resume")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                runState = "Ended"

                val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
                val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

                onSaveRun(
                    RunRecord(
                        date = currentDate,
                        time = currentTime,
                        duration = formattedTime,
                        distance = "0.00 km",
                        pace = "0:00 /km",
                        calories = "0 kcal"
                    )
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = runState == "Running" || runState == "Paused"
        ) {
            Text("End Run")
        }
    }
}

@Composable
fun RecordsScreen(runRecords: List<RunRecord>) {
    var showGoalScreen by remember { mutableStateOf(false) }

    if (showGoalScreen) {
        GoalScreen {
            showGoalScreen = false
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text("Run Records", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { showGoalScreen = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Set Goal")
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (runRecords.isEmpty()) {
                Text("No run records yet.")
            } else {
                runRecords.forEach { record ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Date: ${record.date}")
                            Text("Time: ${record.time}")
                            Text("Duration: ${record.duration}")
                            Text("Distance: ${record.distance}")
                            Text("Pace: ${record.pace}")
                            Text("Calories: ${record.calories}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GoalScreen(onBack: () -> Unit) {
    var targetWeight by remember { mutableStateOf("") }
    var targetDays by remember { mutableStateOf("") }
    var useKg by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Button(onClick = onBack) {
            Text("Back to Records")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Running Goals", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = targetWeight,
            onValueChange = { targetWeight = it },
            label = {
                Text(if (useKg) "Target Weight (kg)" else "Target Weight (lb)")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = targetDays,
            onValueChange = { targetDays = it },
            label = { Text("Target Days") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Use KG")
            Spacer(modifier = Modifier.width(10.dp))
            Switch(
                checked = useKg,
                onCheckedChange = { useKg = it }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Goal Progress")

        LinearProgressIndicator(
            progress = { 0.35f },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("35% Completed")

        Spacer(modifier = Modifier.height(30.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("⭐ Premium Goal Tracking")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Unlock advanced weight tracking, coaching, progress charts, and detailed analytics.")
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = { }) {
                    Text("Upgrade to Premium")
                }
            }
        }
    }
}

@Composable
fun SettingsScreen() {
    var autoPause by remember { mutableStateOf(false) }
    var audioCues by remember { mutableStateOf(true) }
    var currentPace by remember { mutableStateOf(true) }
    var cadence by remember { mutableStateOf(false) }
    var calories by remember { mutableStateOf(true) }
    var steps by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        SettingSwitch("Auto Pause", autoPause) { autoPause = it }
        SettingSwitch("Audio Cues", audioCues) { audioCues = it }
        SettingSwitch("Current Pace", currentPace) { currentPace = it }
        SettingSwitch("Cadence", cadence) { cadence = it }
        SettingSwitch("Calories", calories) { calories = it }
        SettingSwitch("Steps", steps) { steps = it }

        Spacer(modifier = Modifier.height(20.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Profile")
                Text("Account")
            }
        }
    }
}

@Composable
fun SettingSwitch(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title)
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UtilityAppPreview() {
    CP3406_CP5603UtilityAppStarterTemplateTheme {
        UtilityApp()
    }
}