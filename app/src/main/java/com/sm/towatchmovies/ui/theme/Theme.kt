package com.sm.towatchmovies.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ColorOutline = Color(0xFF343640)
private val CinemaColorScheme = darkColorScheme(
    primary = SoftWhite,
    onPrimary = Ink,
    secondary = MutedText,
    onSecondary = Ink,
    tertiary = Accent,
    background = Ink,
    onBackground = SoftWhite,
    surface = NightSurface,
    onSurface = SoftWhite,
    surfaceVariant = RaisedSurface,
    onSurfaceVariant = MutedText,
    outline = ColorOutline,
    error = Accent,
    onError = SoftWhite
)

@Composable
fun ToWatchMoviesTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CinemaColorScheme,
        typography = Typography,
        content = content
    )
}
