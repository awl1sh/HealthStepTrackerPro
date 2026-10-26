package com.awl1sh.healthsteptrackerpro.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    primary = Color(0xFF7C4DFF),
    secondary = Color(0xFF00C2FF),
    background = Color(0xFF0F172A),
    surface = Color(0xFF111827),
    tertiary = Color(0xFF00E676),
    onPrimary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColors = lightColorScheme(
    primary = Color(0xFF5E35B1),
    secondary = Color(0xFF1E88E5),
    background = Color(0xFFF4F7FB),
    surface = Color(0xFFFFFFFF),
    tertiary = Color(0xFF2E7D32),
    onPrimary = Color.White,
    onBackground = Color(0xFF111827),
    onSurface = Color(0xFF111827)
)

@Composable
fun HealthTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
