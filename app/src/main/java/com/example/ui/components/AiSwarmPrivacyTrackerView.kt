package com.example.ui.components

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
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LinkOff
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VpnLock
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LeakedPrivacyLink
import com.example.model.ThreatSeverity
import com.example.model.TrackedPrivacyViolatingDevice
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
import com.example.viewmodel.CyberUiState

@Composable
fun AiSwarmPrivacyTrackerView(
  state: CyberUiState,
  onTriggerScan: () -> Unit,
  onNeutralizeLink: (String) -> Unit,
  onNeutralizeDevice: (String) -> Unit,
  onToggleAutoNeutralize: () -> Unit,
  onToggleFirewall: () -> Unit,
  modifier: Modifier = Modifier
) {
  val tracker = state.privacySwarmTracker

  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Master Status & Drone Control Card
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("swarm_privacy_master_card"),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
      border = androidx.compose.foundation.BorderStroke(
        1.5.dp,
        Brush.horizontalGradient(listOf(QuantumPurple, CyberCyan, CyberEmerald))
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
                .background(QuantumPurple.copy(alpha = 0.15f))
                .border(1.dp, QuantumPurple, RoundedCornerShape(12.dp)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Psychology,
                contentDescription = "Enjambre IA Rastreador",
                tint = QuantumPurple,
                modifier = Modifier.size(26.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "ENJAMBRE IA EN VIVO",
                color = QuantumPurple,
                fontSize = 10.5.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
              )
              Text(
                text = "Rastreo & Purga de Privacidad",
                color = CyberTextPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Black
              )
            }
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(
                if (tracker.isTrackingLive) CyberEmerald.copy(alpha = 0.15f) else ThreatCrimson.copy(alpha = 0.15f)
              )
              .border(
                1.dp,
                if (tracker.isTrackingLive) CyberEmerald else ThreatCrimson,
                RoundedCornerShape(8.dp)
              )
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(7.dp)
                  .clip(CircleShape)
                  .background(if (tracker.isTrackingLive) CyberEmerald else ThreatCrimson)
              )
              Spacer(modifier = Modifier.width(5.dp))
              Text(
                text = if (tracker.isTrackingLive) "VIGILANCIA EN VIVO" else "PAUSADO",
                color = if (tracker.isTrackingLive) CyberEmerald else ThreatCrimson,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }

        Text(
          text = "Patrulla heurística de 6 drones cuánticos escaneando la web, brokers de datos, pastebins y balizas de proximidad en busca de identificadores de usuario o enlaces que violen la privacidad.",
          color = CyberTextSecondary,
          fontSize = 11.5.sp,
          lineHeight = 16.sp
        )

        // Metrics Trio
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(10.dp))
              .background(ObsidianElevated)
              .border(1.dp, ThreatCrimson.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
              .padding(10.dp)
          ) {
            Column {
              Text(
                text = "LINKS BLOQUEADOS",
                color = ThreatCrimson,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "${tracker.linksBlockedCount}",
                color = CyberTextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black
              )
              Text(
                text = "Exfiltraciones neutralizadas",
                color = CyberTextMuted,
                fontSize = 8.5.sp
              )
            }
          }

          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(10.dp))
              .background(ObsidianElevated)
              .border(1.dp, WarningGold.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
              .padding(10.dp)
          ) {
            Column {
              Text(
                text = "DISPOSITIVOS ESPÍA",
                color = WarningGold,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "${tracker.rogueDevicesNeutralizedCount}",
                color = CyberTextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black
              )
              Text(
                text = "Balizas e IMSI aisladas",
                color = CyberTextMuted,
                fontSize = 8.5.sp
              )
            }
          }

          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(10.dp))
              .background(ObsidianElevated)
              .border(1.dp, CyberEmerald.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
              .padding(10.dp)
          ) {
            Column {
              Text(
                text = "DATOS SALVADOS",
                color = CyberEmerald,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "${tracker.dataPointsProtectedCount / 1000}k",
                color = CyberTextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black
              )
              Text(
                text = "Telemetría protegida",
                color = CyberTextMuted,
                fontSize = 8.5.sp
              )
            }
          }
        }

        // Action controls
        AnimatedVisibility(visible = tracker.isScanningForLeaks) {
          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = tracker.activeScanStatus ?: "Buscando fugas de privacidad...",
                color = QuantumPurple,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "${(tracker.scanProgress * 100).toInt()}%",
                color = CyberCyan,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
              )
            }
            LinearProgressIndicator(
              progress = { tracker.scanProgress },
              modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
              color = QuantumPurple,
              trackColor = ObsidianElevated
            )
          }
        }

        Button(
          onClick = onTriggerScan,
          enabled = !tracker.isScanningForLeaks,
          modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .testTag("btn_trigger_swarm_privacy_scan"),
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = QuantumPurple,
            contentColor = Color.Black
          )
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            Icon(
              imageVector = if (tracker.isScanningForLeaks) Icons.Default.Sync else Icons.Default.Radar,
              contentDescription = null,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = if (tracker.isScanningForLeaks) "ENJAMBRE RASTREANDO EN VIVO..." else "Lanzar Barrido en Vivo de Fugas y Enlaces",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        // Toggles
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Auto-Neutralización Cero-Latencia",
              color = CyberTextPrimary,
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold
            )
            Text(
              text = "Corta enlaces y balizas espía sin esperar confirmación",
              color = CyberTextMuted,
              fontSize = 10.sp
            )
          }
          Switch(
            checked = tracker.autoNeutralizeLeaks,
            onCheckedChange = { onToggleAutoNeutralize() },
            colors = SwitchDefaults.colors(
              checkedThumbColor = CyberEmerald,
              checkedTrackColor = CyberEmerald.copy(alpha = 0.3f),
              uncheckedThumbColor = CyberTextMuted,
              uncheckedTrackColor = ObsidianElevated
            )
          )
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Firewall Anti-Data Brokers",
              color = CyberTextPrimary,
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold
            )
            Text(
              text = "Bloquea >4,000 dominios y trackers que compran telemetría",
              color = CyberTextMuted,
              fontSize = 10.sp
            )
          }
          Switch(
            checked = tracker.antiDataBrokerFirewallActive,
            onCheckedChange = { onToggleFirewall() },
            colors = SwitchDefaults.colors(
              checkedThumbColor = CyberCyan,
              checkedTrackColor = CyberCyan.copy(alpha = 0.3f),
              uncheckedThumbColor = CyberTextMuted,
              uncheckedTrackColor = ObsidianElevated
            )
          )
        }
      }
    }

    // Section 1: Enlaces Interceptados
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.LinkOff,
            contentDescription = null,
            tint = ThreatCrimson,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "ENLACES DONDE VIOLAN LA PRIVACIDAD (${tracker.monitoredLinks.size})",
            color = CyberTextPrimary,
            fontSize = 12.5.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
          )
        }

        Text(
          text = "Intercepción DNS Activa",
          color = CyberEmerald,
          fontSize = 10.sp,
          fontWeight = FontWeight.SemiBold
        )
      }

      tracker.monitoredLinks.forEach { link ->
        LeakedLinkCard(
          link = link,
          onNeutralize = { onNeutralizeLink(link.id) }
        )
      }
    }

    // Section 2: Dispositivos Rastreadores & Balizas
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.LocalPolice,
            contentDescription = null,
            tint = WarningGold,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "DISPOSITIVOS RASTREADORES INTERCEPTADOS (${tracker.trackedDevices.size})",
            color = CyberTextPrimary,
            fontSize = 12.5.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
          )
        }

        Text(
          text = "Inhibición BLE/IMSI",
          color = WarningGold,
          fontSize = 10.sp,
          fontWeight = FontWeight.SemiBold
        )
      }

      tracker.trackedDevices.forEach { device ->
        RogueDeviceCard(
          device = device,
          onNeutralize = { onNeutralizeDevice(device.id) }
        )
      }
    }

    // Console Logs
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
            text = "TELEMETRÍA EN VIVO DEL ENJAMBRE",
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
            tracker.liveTrackerLogs.take(6).forEach { logLine ->
              Text(
                text = logLine,
                color = when {
                  logLine.contains("🛑") -> ThreatCrimson
                  logLine.contains("✅") -> CyberEmerald
                  logLine.contains("🔍") -> CyberCyan
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

@Composable
fun LeakedLinkCard(
  link: LeakedPrivacyLink,
  onNeutralize: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      if (link.isBlockedAndNeutralized) CyberEmerald.copy(alpha = 0.5f) else ThreatCrimson.copy(alpha = 0.7f)
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
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(ThreatCrimson.copy(alpha = 0.15f))
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(
            text = link.category.uppercase(),
            color = ThreatCrimson,
            fontSize = 8.5.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(
              if (link.isBlockedAndNeutralized) CyberEmerald.copy(alpha = 0.15f) else ThreatCrimson.copy(alpha = 0.15f)
            )
            .border(
              1.dp,
              if (link.isBlockedAndNeutralized) CyberEmerald else ThreatCrimson,
              RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(
            text = if (link.isBlockedAndNeutralized) "NEUTRALIZADO & SINKHOLED" else "EXPUESTO",
            color = if (link.isBlockedAndNeutralized) CyberEmerald else ThreatCrimson,
            fontSize = 8.5.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Text(
        text = link.titleOrSource,
        color = CyberTextPrimary,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold
      )

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(6.dp))
          .background(ObsidianBackground)
          .padding(8.dp)
      ) {
        Text(
          text = link.targetUrl,
          color = ThreatCrimson,
          fontSize = 10.sp,
          fontFamily = FontFamily.Monospace,
          fontWeight = FontWeight.Medium
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "DATOS EXPUESTOS:",
            color = WarningGold,
            fontSize = 8.5.sp,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = link.exposedDataSummary,
            color = CyberTextSecondary,
            fontSize = 10.sp
          )
        }

        Column(horizontalAlignment = Alignment.End) {
          Text(
            text = "DESCUBIERTO POR:",
            color = CyberCyan,
            fontSize = 8.5.sp,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = link.discoveredByDrone,
            color = CyberTextPrimary,
            fontSize = 9.5.sp,
            fontWeight = FontWeight.Medium
          )
        }
      }

      Text(
        text = "🛡️ ${link.countermeasureLog}",
        color = CyberEmerald,
        fontSize = 9.5.sp,
        fontFamily = FontFamily.Monospace
      )

      if (!link.isBlockedAndNeutralized) {
        Button(
          onClick = onNeutralize,
          modifier = Modifier
            .fillMaxWidth()
            .height(34.dp),
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = ThreatCrimson,
            contentColor = Color.White
          ),
          contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
          Text("Neutralizar Enlace & Forzar Baja DNS", fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun RogueDeviceCard(
  device: TrackedPrivacyViolatingDevice,
  onNeutralize: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      if (device.isNeutralized) WarningGold.copy(alpha = 0.5f) else ThreatCrimson.copy(alpha = 0.7f)
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
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(WarningGold.copy(alpha = 0.15f))
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(
            text = device.hardwareType.uppercase(),
            color = WarningGold,
            fontSize = 8.5.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(CyberEmerald.copy(alpha = 0.15f))
            .border(1.dp, CyberEmerald, RoundedCornerShape(6.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(
            text = if (device.isNeutralized) "INHIBIDO & AISLADO" else "TRANSMITIENDO",
            color = CyberEmerald,
            fontSize = 8.5.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Text(
        text = device.deviceName,
        color = CyberTextPrimary,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold
      )

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = "Identificador: ${device.macOrImsi}",
          color = CyberCyan,
          fontSize = 10.sp,
          fontFamily = FontFamily.Monospace
        )
        Text(
          text = "Señal: ${device.signalStrengthRssi} dBm (${device.proximityEstimate})",
          color = CyberTextMuted,
          fontSize = 10.sp
        )
      }

      Text(
        text = "Vector de espionaje: ${device.threatVector}",
        color = CyberTextSecondary,
        fontSize = 10.sp,
        lineHeight = 14.sp
      )

      Text(
        text = "⚡ Contramedida: ${device.neutralizationLog}",
        color = CyberEmerald,
        fontSize = 9.5.sp,
        fontFamily = FontFamily.Monospace
      )
    }
  }
}
