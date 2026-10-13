package com.example.ui.components

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.GpsNotFixed
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SatelliteAlt
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.model.GpsLocationState
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberTextMuted
import com.example.ui.theme.CyberTextPrimary
import com.example.ui.theme.CyberTextSecondary
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianElevated
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.QuantumPurple
import com.example.ui.theme.ThreatCrimson
import com.example.ui.theme.WarningGold
import java.util.Locale

@Composable
fun GpsLocationTelemetryView(
  gpsState: GpsLocationState,
  onRefreshGps: (Context) -> Unit,
  onToggleCloak: () -> Unit,
  onBlockQuerySimulation: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  val permissionLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestMultiplePermissions()
  ) { permissions ->
    val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
    val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
    if (fineGranted || coarseGranted) {
      onRefreshGps(context)
    }
  }

  LaunchedEffect(Unit) {
    val fineGranted = ContextCompat.checkSelfPermission(
      context,
      Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    val coarseGranted = ContextCompat.checkSelfPermission(
      context,
      Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    if (fineGranted || coarseGranted) {
      onRefreshGps(context)
    }
  }

  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Master GPS Telemetry Card
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("gps_master_telemetry_card"),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
      border = androidx.compose.foundation.BorderStroke(
        1.5.dp,
        Brush.horizontalGradient(listOf(CyberCyan, CyberEmerald, QuantumPurple))
      )
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(CyberCyan.copy(alpha = 0.15f))
                .border(1.dp, CyberCyan, RoundedCornerShape(12.dp)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.GpsFixed,
                contentDescription = "GPS Satelital",
                tint = CyberCyan,
                modifier = Modifier.size(26.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "GEOLOCALIZACIÓN GPS & TELEMETRÍA",
                color = CyberCyan,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
              )
              Text(
                text = "Ubicación Exacta & Paradero",
                color = CyberTextPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Black
              )
            }
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(CyberEmerald.copy(alpha = 0.15f))
              .border(1.dp, CyberEmerald, RoundedCornerShape(8.dp))
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(7.dp)
                  .clip(CircleShape)
                  .background(CyberEmerald)
              )
              Spacer(modifier = Modifier.width(5.dp))
              Text(
                text = "${gpsState.satellitesTracked} SATÉLITES L5",
                color = CyberEmerald,
                fontSize = 9.5.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }

        // Coordinates Main Display
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(ObsidianBackground)
            .border(1.dp, ObsidianBorder, RoundedCornerShape(12.dp))
            .padding(14.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "COORDENADAS SATELITALES FIJADAS",
                color = CyberTextMuted,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
              )
              Text(
                text = gpsState.timestampString,
                color = CyberEmerald,
                fontSize = 9.5.sp,
                fontFamily = FontFamily.Monospace
              )
            }

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Column {
                Text(
                  text = "LATITUD",
                  color = CyberCyan,
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold
                )
                Text(
                  text = String.format(Locale.US, "%.6f° N", gpsState.latitude),
                  color = CyberTextPrimary,
                  fontSize = 17.sp,
                  fontFamily = FontFamily.Monospace,
                  fontWeight = FontWeight.Black
                )
              }

              Column(horizontalAlignment = Alignment.End) {
                Text(
                  text = "LONGITUD",
                  color = CyberCyan,
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold
                )
                Text(
                  text = String.format(Locale.US, "%.6f° W", Math.abs(gpsState.longitude)),
                  color = CyberTextPrimary,
                  fontSize = 17.sp,
                  fontFamily = FontFamily.Monospace,
                  fontWeight = FontWeight.Black
                )
              }
            }

            // Accuracy, Altitude, Speed
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.PinDrop,
                  contentDescription = null,
                  tint = CyberEmerald,
                  modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "Precisión: ±${String.format(Locale.US, "%.1f", gpsState.accuracyMeters)}m",
                  color = CyberTextSecondary,
                  fontSize = 10.sp
                )
              }

              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Explore,
                  contentDescription = null,
                  tint = WarningGold,
                  modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "Alt: ${gpsState.altitudeMeters.toInt()}m s.n.m.",
                  color = CyberTextSecondary,
                  fontSize = 10.sp
                )
              }

              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Speed,
                  contentDescription = null,
                  tint = QuantumPurple,
                  modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "${String.format(Locale.US, "%.1f", gpsState.speedKmh)} km/h",
                  color = CyberTextSecondary,
                  fontSize = 10.sp
                )
              }
            }
          }
        }

        // Paradero del Usuario Card
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(ObsidianElevated)
            .border(1.dp, CyberEmerald.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
            .padding(12.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(CyberEmerald.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = CyberEmerald,
                modifier = Modifier.size(18.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "PARADERO FÍSICO DEL USUARIO",
                color = CyberEmerald,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = gpsState.humanReadableParadero,
                color = CyberTextPrimary,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.SemiBold
              )
            }
          }
        }

        // Action Buttons: Sincronizar GPS
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Button(
            onClick = {
              val fineGranted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
              ) == PackageManager.PERMISSION_GRANTED
              if (fineGranted) {
                onRefreshGps(context)
              } else {
                permissionLauncher.launch(
                  arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                  )
                )
              }
            },
            enabled = !gpsState.isLocating,
            modifier = Modifier
              .weight(1f)
              .height(42.dp)
              .testTag("btn_refresh_gps"),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = CyberCyan,
              contentColor = Color.Black
            )
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = if (gpsState.isLocating) Icons.Default.Sync else Icons.Default.Refresh,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = if (gpsState.isLocating) "Sintonizando..." else "Fijar Coordenadas GPS",
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          OutlinedButton(
            onClick = {
              onBlockQuerySimulation("Tracker Analytics AdMob (Sondeo background)")
            },
            modifier = Modifier
              .weight(1f)
              .height(42.dp)
              .testTag("btn_simulate_gps_leak_block"),
            shape = RoundedCornerShape(10.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = WarningGold)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Probar Bloqueo Espía",
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }

        // Blindaje Anti-Stalkerware Toggle Card
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = ObsidianElevated),
          border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (gpsState.isLocationCloakedAgainstTrackers) CyberEmerald.copy(alpha = 0.5f) else WarningGold.copy(alpha = 0.5f)
          )
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = if (gpsState.isLocationCloakedAgainstTrackers) Icons.Default.Lock else Icons.Default.Visibility,
                  contentDescription = null,
                  tint = if (gpsState.isLocationCloakedAgainstTrackers) CyberEmerald else WarningGold,
                  modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                  Text(
                    text = "Ofuscación de Coordenadas eBPF",
                    color = CyberTextPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                  )
                  Text(
                    text = if (gpsState.isLocationCloakedAgainstTrackers) "Anti-Stalkerware Activo (Ruido a trackers)" else "Coordenadas directas expuestas",
                    color = if (gpsState.isLocationCloakedAgainstTrackers) CyberEmerald else WarningGold,
                    fontSize = 10.sp
                  )
                }
              }

              Switch(
                checked = gpsState.isLocationCloakedAgainstTrackers,
                onCheckedChange = { onToggleCloak() },
                colors = SwitchDefaults.colors(
                  checkedThumbColor = CyberEmerald,
                  checkedTrackColor = CyberEmerald.copy(alpha = 0.3f),
                  uncheckedThumbColor = WarningGold,
                  uncheckedTrackColor = ObsidianSurface
                )
              )
            }

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = "Consultas espía neutralizadas:",
                color = CyberTextSecondary,
                fontSize = 10.5.sp
              )
              Text(
                text = "${gpsState.locationQueriesBlockedCount} bloqueos registrados",
                color = ThreatCrimson,
                fontSize = 10.5.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    }

    // MAPA GPS TÁCTICO & VECTOR DE UBICACIÓN
    TacticalGpsMapView(gpsState = gpsState)

    // Telemetry Logs Card
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
      border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Terminal,
            contentDescription = null,
            tint = CyberCyan,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "REGISTRO DE TELEMETRÍA GPS & PARADERO",
            color = CyberCyan,
            fontSize = 10.5.sp,
            fontWeight = FontWeight.Bold
          )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(ObsidianBackground)
            .border(1.dp, ObsidianBorder, RoundedCornerShape(8.dp))
            .padding(10.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            gpsState.telemetryLogs.take(6).forEach { logLine ->
              Text(
                text = logLine,
                color = when {
                  logLine.contains("🛑") -> ThreatCrimson
                  logLine.contains("🛰️") -> CyberCyan
                  logLine.contains("🌐") -> CyberEmerald
                  logLine.contains("⚠️") -> WarningGold
                  else -> CyberTextSecondary
                },
                fontSize = 9.5.sp,
                fontFamily = FontFamily.Monospace,
                lineHeight = 13.sp
              )
            }
          }
        }
      }
    }
  }
}
