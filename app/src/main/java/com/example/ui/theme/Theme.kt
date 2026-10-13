package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val CyberColorScheme = darkColorScheme(
  primary = CyberCyan,
  onPrimary = ObsidianBackground,
  primaryContainer = ObsidianElevated,
  onPrimaryContainer = CyberCyan,
  secondary = CyberEmerald,
  onSecondary = ObsidianBackground,
  secondaryContainer = ObsidianSurfaceVariant,
  onSecondaryContainer = CyberEmerald,
  tertiary = QuantumPurple,
  onTertiary = ObsidianBackground,
  background = ObsidianBackground,
  onBackground = CyberTextPrimary,
  surface = ObsidianSurface,
  onSurface = CyberTextPrimary,
  surfaceVariant = ObsidianSurfaceVariant,
  onSurfaceVariant = CyberTextSecondary,
  error = ThreatCrimson,
  onError = ObsidianBackground,
  outline = ObsidianBorder
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true,
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = CyberColorScheme,
    typography = Typography,
    content = content
  )
}

