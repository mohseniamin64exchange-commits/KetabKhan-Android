package com.ketabkhan.reader.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

private val AppLightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = Surface,
    primaryContainer = SuccessBackground,
    onPrimaryContainer = Primary,
    secondary = PrimaryLight,
    onSecondary = Surface,
    secondaryContainer = SecondarySurface,
    onSecondaryContainer = TextPrimary,
    tertiary = StatusWarning,
    onTertiary = Surface,
    background = Background,
    onBackground = TextPrimary,
    surface = Surface,
    onSurface = TextPrimary,
    surfaceVariant = SecondarySurface,
    onSurfaceVariant = TextSecondary,
    outline = Border,
    outlineVariant = SecondarySurface,
    error = StatusError,
    onError = Surface
)

private val AppDarkColorScheme = darkColorScheme(
    primary = PrimaryLight,
    onPrimary = DarkBackground,
    primaryContainer = Primary,
    onPrimaryContainer = DarkText,
    secondary = NightAccent,
    onSecondary = DarkBackground,
    secondaryContainer = DarkSurface,
    onSecondaryContainer = DarkText,
    tertiary = NightAccent,
    onTertiary = DarkBackground,
    background = DarkBackground,
    onBackground = DarkText,
    surface = DarkSurface,
    onSurface = DarkText,
    surfaceVariant = DarkBorder,
    onSurfaceVariant = DarkText,
    outline = DarkBorder,
    outlineVariant = DarkSurface,
    error = StatusError,
    onError = Surface
)

@Composable
fun BookReaderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) AppDarkColorScheme else AppLightColorScheme

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
