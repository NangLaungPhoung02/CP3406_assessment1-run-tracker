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
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.foundation.clickable


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
    var showGoalScreen by remember { mutableStateOf(false) }
    var isDarkMode by remember { mutableStateOf(true) }
    var showPremiumScreen by remember { mutableStateOf(false) }
    var showProfileScreen by remember { mutableStateOf(false) }

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
                "Records" -> {
                    if (showPremiumScreen) {
                        PremiumScreen(
                            onBack = { showPremiumScreen = false }
                        )
                    } else if (showGoalScreen) {
                        GoalScreen(
                            onBack = { showGoalScreen = false },
                            onUpgradeClick = { showPremiumScreen = true }
                        )
                    } else {
                        RecordsScreen(
                            runRecords = runRecords,
                            onSetGoalClick = { showGoalScreen = true }
                        )
                    }
                }
                "Settings" -> {
                    if (showProfileScreen) {
                        ProfileScreen(
                            onBack = { showProfileScreen = false }
                        )
                    } else {
                        SettingsScreen(
                            isDarkMode = isDarkMode,
                            onDarkModeChange = { isDarkMode = it },
                            onProfileClick = { showProfileScreen = true }
                        )
                    }
                }
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Column {

                Text(
                    text = "Running Tracker",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Track your run, goals and progress",
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Black.copy(alpha = 0.75f)
                    )
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Today Overview", color = Color.White)
                        Spacer(modifier = Modifier.height(12.dp))

                        Text("🏃 Total Runs: ${runRecords.size}", color = Color(0xFF39FF14))
                        Text("📍 Today Distance: 0.00 km", color = Color(0xFF39FF14))
                        Text("🔥 Total Calories: 0 kcal", color = Color(0xFF39FF14))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Black.copy(alpha = 0.75f)
                    )
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Weather Condition", color = Color.White)
                        Spacer(modifier = Modifier.height(12.dp))

                        Text("☁️ Condition: Partly Cloudy", color = Color(0xFF39FF14))
                        Text("🌡 Temperature: 28°C", color = Color(0xFF39FF14))
                        Text("💨 Wind: Light", color = Color(0xFF39FF14))
                        Text("✅ Good time for a short run", color = Color(0xFF39FF14))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Black.copy(alpha = 0.75f)
                    )
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Motivation", color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Every step counts. Keep moving forward.",
                            color = Color(0xFF39FF14)
                        )
                    }
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

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.Black.copy(alpha = 0.75f)
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("TIME", color = Color.White)
                Text(
                    text = formattedTime,
                    style = MaterialTheme.typography.displayMedium,
                    color = Color(0xFF39FF14)
                )
                Text("Status: $runState", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            StatCard("Distance", "0.00 km", Modifier.weight(1f))
            Spacer(modifier = Modifier.width(10.dp))
            StatCard("Pace", "0:00 /km", Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            StatCard("Speed", "0.0 km/h", Modifier.weight(1f))
            Spacer(modifier = Modifier.width(10.dp))
            StatCard("Calories", "0 kcal", Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            StatCard("Steps", "0", Modifier.weight(1f))
            Spacer(modifier = Modifier.width(10.dp))
            StatCard("Weather", "Coming soon", Modifier.weight(1f))
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

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { runState = "Paused" },
                modifier = Modifier.weight(1f),
                enabled = runState == "Running"
            ) {
                Text("Pause")
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = { runState = "Running" },
                modifier = Modifier.weight(1f),
                enabled = runState == "Paused"
            ) {
                Text("Resume")
            }
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
fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.Black.copy(alpha = 0.75f)
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Text(title, color = Color.White)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, color = Color(0xFF39FF14))
        }
    }
}

data class FakeRunRecord(
    val date: String,
    val distance: String,
    val time: String,
    val pace: String,
    val calories: String
)

@Composable
fun RecordsScreen(
    runRecords: List<RunRecord>,
    onSetGoalClick: () -> Unit
)  {
    val sampleRecords = listOf(
        FakeRunRecord("Today", "3.20 km", "22:15", "6'57\" /km", "180 kcal"),
        FakeRunRecord("Yesterday", "2.50 km", "18:40", "7'28\" /km", "140 kcal"),
        FakeRunRecord("Mon, 8 Jun", "5.00 km", "36:10", "7'14\" /km", "310 kcal")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp)
    ) {
        Text(
            text = "Run Records",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF39FF00)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onSetGoalClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF39FF00),
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Set Goal",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        SummaryCard()

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Recent Runs",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF39FF00)
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(sampleRecords) { record ->
                FakeRunRecordCard(record)
            }
        }
    }
}
@Composable
fun SummaryCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF101010)
        ),
        border = BorderStroke(1.dp, Color(0xFF39FF00))
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = "This Week Summary",
                color = Color(0xFF39FF00),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SummaryItem("Distance", "10.7 km")
                SummaryItem("Time", "1h 17m")
                SummaryItem("Calories", "630")
            }
        }
    }
}

@Composable
fun SummaryItem(title: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = title,
            color = Color.Gray,
            fontSize = 13.sp
        )
    }
}

@Composable
fun FakeRunRecordCard(record: FakeRunRecord) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF141414)
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = record.date,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = record.distance,
                    color = Color(0xFF39FF00),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RecordInfo("Time", record.time)
                RecordInfo("Pace", record.pace)
                RecordInfo("Calories", record.calories)
            }
        }
    }
}

@Composable
fun RecordInfo(title: String, value: String) {
    Column {
        Text(
            text = title,
            color = Color.Gray,
            fontSize = 12.sp
        )

        Text(
            text = value,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun GoalScreen(
    onBack: () -> Unit,
    onUpgradeClick: () -> Unit
) {
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
                Button(onClick = onUpgradeClick) {
                    Text("Upgrade to Premium")
                }
            }
        }
    }
}

@Composable
fun PremiumScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp)
    ) {
        Button(
            onClick = onBack,
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF39FF00),
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Back to Goals",
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Run Premium",
            color = Color(0xFF39FF00),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Upgrade your running experience",
            color = Color.LightGray,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(26.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF343139)
            ),
            border = BorderStroke(1.dp, Color(0xFF39FF00))
        ) {
            Column(
                modifier = Modifier.padding(22.dp)
            ) {
                Text(
                    text = "⭐ Premium Plan",
                    color = Color(0xFF39FF00),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "$4.99 / month",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Cancel anytime",
                    color = Color.LightGray,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(22.dp))

                PremiumFeature("Advanced weight tracking")
                PremiumFeature("Personal running goals")
                PremiumFeature("Progress charts")
                PremiumFeature("Calories and pace analytics")
                PremiumFeature("Coaching tips")
                PremiumFeature("Unlimited goal history")

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF39FF00),
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Start Premium",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "This is a demo premium page for the assignment. No real payment is required.",
            color = Color.Gray,
            fontSize = 13.sp
        )
    }
}

@Composable
fun PremiumFeature(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 6.dp)
    ) {
        Text(
            text = "✓",
            color = Color(0xFF39FF00),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

@Composable
fun SettingsScreen(
    isDarkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit, onProfileClick :() -> Unit
) {

    var autoPause by remember { mutableStateOf(false) }
    var audioCues by remember { mutableStateOf(true) }
    var currentPace by remember { mutableStateOf(true) }
    var cadence by remember { mutableStateOf(false) }
    var calories by remember { mutableStateOf(true) }
    var steps by remember { mutableStateOf(true) }

    val backgroundColor = if (isDarkMode) Color.Black else Color(0xFFF5F5F5)
    val cardColor = if (isDarkMode) Color(0xFF343139) else Color.White
    val textColor = if (isDarkMode) Color(0xFF39FF00) else Color(0xFF111111)
    val subTextColor = if (isDarkMode) Color.LightGray else Color.DarkGray
    val switchColor = Color(0xFF39FF00)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    )  {
        Text(
            text = "Settings",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )

        Spacer(modifier = Modifier.height(20.dp))

        SettingSwitchItem(
            title = "Dark Mode",
            checked = isDarkMode,
            onCheckedChange = onDarkModeChange,
            cardColor = cardColor,
            textColor = textColor,
            switchColor = switchColor
        )

        Spacer(modifier = Modifier.height(12.dp))

        SettingSwitchItem(
            title = "Auto Pause",
            checked = autoPause,
            onCheckedChange = { autoPause = it },
            cardColor = cardColor,
            textColor = textColor,
            switchColor = switchColor
        )

        Spacer(modifier = Modifier.height(12.dp))

        SettingSwitchItem(
            title = "Audio Cues",
            checked = audioCues,
            onCheckedChange = { audioCues = it },
            cardColor = cardColor,
            textColor = textColor,
            switchColor = switchColor
        )

        Spacer(modifier = Modifier.height(12.dp))

        SettingSwitchItem(
            title = "Current Pace",
            checked = currentPace,
            onCheckedChange = { currentPace = it },
            cardColor = cardColor,
            textColor = textColor,
            switchColor = switchColor
        )

        Spacer(modifier = Modifier.height(12.dp))

        SettingSwitchItem(
            title = "Cadence",
            checked = cadence,
            onCheckedChange = { cadence = it },
            cardColor = cardColor,
            textColor = textColor,
            switchColor = switchColor
        )

        Spacer(modifier = Modifier.height(12.dp))

        SettingSwitchItem(
            title = "Calories",
            checked = calories,
            onCheckedChange = { calories = it },
            cardColor = cardColor,
            textColor = textColor,
            switchColor = switchColor
        )

        Spacer(modifier = Modifier.height(12.dp))

        SettingSwitchItem(
            title = "Steps",
            checked = steps,
            onCheckedChange = { steps = it },
            cardColor = cardColor,
            textColor = textColor,
            switchColor = switchColor
        )

        Spacer(modifier = Modifier.height(22.dp))

        UserProfileCard(
            isDarkMode = isDarkMode,
            cardColor = cardColor,
            textColor = textColor,
            subTextColor = subTextColor,
            onProfileClick = onProfileClick
        )
    }
}

@Composable
fun SettingSwitchItem(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    cardColor: Color,
    textColor: Color,
    switchColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                color = textColor,
                fontSize = 19.sp,
                fontWeight = FontWeight.Medium
            )

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Black,
                    checkedTrackColor = switchColor,
                    uncheckedThumbColor = Color.Gray,
                    uncheckedTrackColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun UserProfileCard(
    isDarkMode: Boolean,
    cardColor: Color,
    textColor: Color,
    subTextColor: Color,
    onProfileClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProfileClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "User Profile",
                    tint = textColor,
                    modifier = Modifier.size(58.dp)
                )

                Spacer(modifier = Modifier.width(14.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Navy",
                        color = textColor,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Runner Account",
                        color = subTextColor,
                        fontSize = 14.sp
                    )

                    Text(
                        text = "Free Plan",
                        color = subTextColor,
                        fontSize = 13.sp
                    )
                }

                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Open Profile",
                    tint = subTextColor
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Divider(
                color = if (isDarkMode) Color.DarkGray else Color.LightGray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ProfileStatItem(
                    title = "Total Runs",
                    value = "12",
                    textColor = textColor,
                    subTextColor = subTextColor
                )

                ProfileStatItem(
                    title = "Distance",
                    value = "36.8 km",
                    textColor = textColor,
                    subTextColor = subTextColor
                )

                ProfileStatItem(
                    title = "Calories",
                    value = "2,430",
                    textColor = textColor,
                    subTextColor = subTextColor
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53935),
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Log Out"
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Log Out",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ProfileStatItem(
    title: String,
    value: String,
    textColor: Color,
    subTextColor: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            color = textColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = title,
            color = subTextColor,
            fontSize = 12.sp
        )
    }
}

@Composable
fun ProfileScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp)
    ) {
        Button(
            onClick = onBack,
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF39FF00),
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Back to Settings",
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "User Profile",
            color = Color(0xFF39FF00),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF343139)
            ),
            border = BorderStroke(1.dp, Color(0xFF39FF00))
        ) {
            Column(
                modifier = Modifier.padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    tint = Color(0xFF39FF00),
                    modifier = Modifier.size(90.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Navy",
                    color = Color(0xFF39FF00),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Runner Account",
                    color = Color.LightGray,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(22.dp))

                ProfileDetailRow("Plan", "Free Plan")
                ProfileDetailRow("Total Runs", "12")
                ProfileDetailRow("Total Distance", "36.8 km")
                ProfileDetailRow("Calories Burned", "2,430 kcal")
                ProfileDetailRow("Goal Progress", "35%")
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE53935),
                contentColor = Color.White
            )
        ) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Log Out"
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Log Out",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ProfileDetailRow(title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 9.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            color = Color.LightGray,
            fontSize = 16.sp
        )

        Text(
            text = value,
            color = Color(0xFF39FF00),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
@Preview(showBackground = true)
@Composable
fun UtilityAppPreview() {
    CP3406_CP5603UtilityAppStarterTemplateTheme {
        UtilityApp()
    }
}