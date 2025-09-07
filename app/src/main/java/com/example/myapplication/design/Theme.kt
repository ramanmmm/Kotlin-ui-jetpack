package com.example.myapplication.design

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightScheme = lightColorScheme(
    primary = ColorTokens.FabBlue,
    onPrimary = ColorTokens.OnFab,
    surface = ColorTokens.Surface,
    onSurface = ColorTokens.TextPrimary,
    outline = ColorTokens.Divider
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightScheme,
        typography = Typography(), // you can customize later
        content = content
    )
}