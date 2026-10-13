package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BlockedPacketEvent
import com.example.model.FlowDirection
import com.example.model.TrafficDashboardSummary
import com.example.model.TrafficFlowPoint
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberTextMuted
import com.example.ui.theme.CyberTextPrimary
import com.example.ui.theme.CyberTextSecondary
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianElevated
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.QuantumPurple
import com.example.ui.theme.ThreatCrimson
import com.example.ui.theme.WarningGold

@Composable
fun RealtimeTrafficDashboardView(
  trafficHistory: List<TrafficFlowPoint>,
  blockedPackets: List<BlockedPacketEvent>,
  summary: TrafficDashboardSummary,
  onToggleLive: () -> Unit,
  onSelectFilter: (FlowDirection?) -> Unit,
  onInjectBurst: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. KPI Metric Summary Cards
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      TrafficMetricCard(
        title = "Flujo Entrante Bloqueado",
        value = "${"%.1f".format(summary.currentIncomingBlockedMbps)} Mbps",
        subtext = "Ingress total: ${"%.1f".format(summary.totalIncomingDataBlockedGb)} GB",
        direction = FlowDirection.INCOMING,
        accentColor = CyberCyan,
        modifier = Modifier.weight(1f)
      )

      TrafficMetricCard(
        title = "Flujo Saliente Bloqueado",
        value = "${"%.1f".format(summary.currentOutgoingBlockedMbps)} Mbps",
        subtext = "Egress total: ${"%.1f".format(summary.totalOutgoingDataBlockedGb)} GB",
        direction = FlowDirection.OUTGOING,
        accentColor = QuantumPurple,
        modifier = Modifier.weight(1f)
      )
    }

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      SecondaryStatCard(
        title = "Pico Máximo Filtrado",
        value = "${"%.1f".format(summary.peakBlockedMbps)} Mbps",
        caption = "Capacidad Swarm: 10 Gbps",
        color = WarningGold,
        modifier = Modifier.weight(1f)
      )
      SecondaryStatCard(
        title = "Paquetes Destruidos",
        value = "${summary.totalPacketsBlockedToday}",
        caption = "100.0% Tasa de Supresión",
        color = CyberEmerald,
        modifier = Modifier.weight(1f)
      )
    }

    // 2. Recharts Interactive Chart
    RechartsTrafficChart(
      dataPoints = trafficHistory,
      isLive = summary.isRealTimeFeedLive,
      selectedFilter = summary.selectedFilter,
      onToggleLive = onToggleLive,
      onSelectFilter = onSelectFilter,
      onInjectBurst = onInjectBurst
    )

    // 3. Protocol Vectors Breakdown
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
      border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(14.dp)
      ) {
        Text(
          text = "VECTORES DE TRÁFICO NEUTRALIZADOS",
          color = CyberCyan,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(10.dp))

        ProtocolProgressRow("DNS Tunneling & Canales Clandestinos", 0.34f, "34%", CyberCyan)
        ProtocolProgressRow("HTTPS / TLS 1.3 Balizas C2 & Spyware", 0.28f, "28%", QuantumPurple)
        ProtocolProgressRow("QUIC / UDP Ingress Exploits", 0.22f, "22%", ThreatCrimson)
        ProtocolProgressRow("TCP-SYN Floods & Escaneo de Puertos", 0.16f, "16%", WarningGold)
      }
    }

    // 4. Real-time Blocked Packets Feed Title
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "REGISTRO EN VIVO: PAQUETES NEUTRALIZADOS",
        color = CyberCyan,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      )
      Text(
        text = "${blockedPackets.size} Eventos Recientes",
        color = CyberTextMuted,
        fontSize = 10.sp,
        fontFamily = FontFamily.Monospace
      )
    }

    // Filtered packet feed
    val filteredPackets = if (summary.selectedFilter == null) {
      blockedPackets
    } else {
      blockedPackets.filter { it.direction == summary.selectedFilter }
    }

    if (filteredPackets.isEmpty()) {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianElevated)
      ) {
        Box(
          modifier = Modifier.fillMaxWidth().padding(24.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "Sin paquetes en este filtro de dirección",
            color = CyberTextMuted,
            fontSize = 11.sp
          )
        }
      }
    } else {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        filteredPackets.forEach { event ->
          BlockedPacketEventCard(event = event)
        }
      }
    }
  }
}

@Composable
fun TrafficMetricCard(
  title: String,
  value: String,
  subtext: String,
  direction: FlowDirection,
  accentColor: Color,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = androidx.compose.foundation.BorderStroke(1.dp, accentColor.copy(alpha = 0.4f))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = title,
          color = CyberTextMuted,
          fontSize = 10.sp,
          fontWeight = FontWeight.SemiBold,
          maxLines = 1
        )
        Icon(
          imageVector = if (direction == FlowDirection.INCOMING) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
          contentDescription = direction.label,
          tint = accentColor,
          modifier = Modifier.size(14.dp)
        )
      }

      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = value,
        color = accentColor,
        fontSize = 18.sp,
        fontWeight = FontWeight.Black,
        fontFamily = FontFamily.Monospace
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = subtext,
        color = CyberTextSecondary,
        fontSize = 9.sp
      )
    }
  }
}

@Composable
fun SecondaryStatCard(
  title: String,
  value: String,
  caption: String,
  color: Color,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianElevated),
    border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
    ) {
      Text(
        text = title,
        color = CyberTextMuted,
        fontSize = 10.sp
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = value,
        color = color,
        fontSize = 14.sp,
        fontWeight = FontWeight.Black,
        fontFamily = FontFamily.Monospace
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = caption,
        color = CyberTextSecondary,
        fontSize = 9.sp
      )
    }
  }
}

@Composable
fun ProtocolProgressRow(label: String, progress: Float, percentText: String, color: Color) {
  Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(text = label, color = CyberTextSecondary, fontSize = 10.sp)
      Text(text = percentText, color = color, fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
    Spacer(modifier = Modifier.height(4.dp))
    LinearProgressIndicator(
      progress = { progress },
      modifier = Modifier.fillMaxWidth().height(5.dp).clip(RoundedCornerShape(3.dp)),
      color = color,
      trackColor = ObsidianElevated
    )
  }
}

@Composable
fun BlockedPacketEventCard(event: BlockedPacketEvent) {
  val isIncoming = event.direction == FlowDirection.INCOMING
  val directionColor = if (isIncoming) CyberCyan else QuantumPurple

  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(10.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(directionColor.copy(alpha = 0.15f))
              .border(1.dp, directionColor.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
              .padding(horizontal = 5.dp, vertical = 2.dp)
          ) {
            Text(
              text = if (isIncoming) "INGRESS (ENTRANTE)" else "EGRESS (SALIENTE)",
              color = directionColor,
              fontSize = 9.sp,
              fontWeight = FontWeight.Black
            )
          }

          Spacer(modifier = Modifier.width(6.dp))

          Text(
            text = event.protocol,
            color = CyberTextMuted,
            fontSize = 9.sp,
            fontFamily = FontFamily.Monospace
          )
        }

        Text(
          text = event.timestamp,
          color = CyberTextMuted,
          fontSize = 9.sp,
          fontFamily = FontFamily.Monospace
        )
      }

      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = event.threatType,
        color = CyberTextPrimary,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold
      )

      Spacer(modifier = Modifier.height(3.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = "${event.sourceIp}  →  ${event.destinationIp}",
          color = CyberTextSecondary,
          fontSize = 9.sp,
          fontFamily = FontFamily.Monospace,
          maxLines = 1
        )
        Text(
          text = "${"%.1f".format(event.bandwidthKbps)} Kbps",
          color = WarningGold,
          fontSize = 9.sp,
          fontFamily = FontFamily.Monospace
        )
      }

      Spacer(modifier = Modifier.height(4.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Shield,
            contentDescription = "Interceptado",
            tint = CyberEmerald,
            modifier = Modifier.size(11.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "Bloqueado por: ${event.interceptingAgent}",
            color = CyberEmerald,
            fontSize = 9.sp,
            fontWeight = FontWeight.Medium
          )
        }

        Text(
          text = "SUPRIMIDO 100%",
          color = CyberEmerald,
          fontSize = 8.sp,
          fontWeight = FontWeight.Black
        )
      }
    }
  }
}
