package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = NeonGreen,
    secondary = NeonGreen,
    tertiary = NeonGreen,

    background = DarkBlack,
    surface = DarkBlack,

    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black,

    onBackground = NeonGreen,
    onSurface = NeonGreen
)

@Composable
fun CP3406_CP5603UtilityAppStarterTemplateTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}