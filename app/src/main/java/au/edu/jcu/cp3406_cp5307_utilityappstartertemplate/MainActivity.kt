package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.theme.CP3406_CP5603UtilityAppStarterTemplateTheme

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

@Preview(showBackground = true)
@Composable
fun UtilityAppPreview() {
    CP3406_CP5603UtilityAppStarterTemplateTheme {
        UtilityApp()
    }
}

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
                "Home" -> HomeScreen()

                "Run" -> RunScreen(
                    onSaveRun = { record ->
                        runRecords = runRecords + record
                    }
                )

                "Records" -> RecordsScreen(
                    runRecords = runRecords
                )

                "Settings" -> SettingsScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Home",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Current Location: Coming soon")
                Text("Google Map: Coming soon")
                Text("Weather: Coming soon")
                Text("Today Distance: 0.00 km")
                Text("Total Runs: 0")
                Text("Calories Burned: 0 kcal")
            }
        }
    }
}


@Composable
fun RunScreen( onSaveRun: (RunRecord) -> Unit) {
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
        Text(
            text = "Run Tracker",
            style = MaterialTheme.typography.headlineMedium
        )

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
                Text("Weather: Loading later")
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
            onClick = { runState = "Ended" },
            modifier = Modifier.fillMaxWidth(),
            enabled = runState == "Running" || runState == "Paused"
        ) {
            Text("End Run")
        }
    }
}

@Composable
fun RecordsScreen( runRecords: List<RunRecord>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Running Records", style = MaterialTheme.typography.headlineMedium)

        Card {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Example Record 1")
                Text("Distance: 3 km")
                Text("Time: 24 minutes")
                Text("Pace: 8.00 min/km")
            }
        }

        Card {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Example Record 2")
                Text("Distance: 5 km")
                Text("Time: 40 minutes")
                Text("Pace: 8.00 min/km")
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
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium
        )

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