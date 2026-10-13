package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SovereignLicenseInfo
import com.example.model.ThreatIncident
import com.example.model.ThreatSeverity
import com.example.model.ThreatStatus
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberCyanMuted
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
fun OwnerSovereignHeader(
  license: SovereignLicenseInfo,
  modifier: Modifier = Modifier,
  onViewCertificateClick: () -> Unit = {}
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("owner_sovereign_card")
      .clickable { onViewCertificateClick() },
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      Brush.horizontalGradient(listOf(CyberCyan, CyberEmerald, QuantumPurple))
    )
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)
      ) {
        Box(
          modifier = Modifier
            .size(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.radialGradient(listOf(CyberCyan.copy(alpha = 0.25f), Color.Transparent)))
            .border(1.dp, CyberCyan, RoundedCornerShape(12.dp)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.VerifiedUser,
            contentDescription = "Sello Soberano",
            tint = CyberCyan,
            modifier = Modifier.size(24.dp)
          )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "PROPIEDAD Y SOFTWARE REGISTRADO",
              color = CyberEmerald,
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp
            )
          }
          Text(
            text = license.ownerName,
            color = CyberTextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Black
          )
          Text(
            text = license.role,
            color = CyberTextSecondary,
            fontSize = 11.sp,
            lineHeight = 14.sp
          )
        }
      }

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(20.dp))
          .background(CyberEmerald.copy(alpha = 0.12f))
          .border(1.dp, CyberEmerald.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
          .padding(horizontal = 10.dp, vertical = 5.dp)
      ) {
        Text(
          text = "ROOT 0",
          color = CyberEmerald,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold
        )
      }
    }
  }
}

@Composable
fun QuantumMeshRadar(
  modifier: Modifier = Modifier,
  isScanning: Boolean = false,
  isThreatDetected: Boolean = false
) {
  val infiniteTransition = rememberInfiniteTransition(label = "RadarTransition")
  val pulseRadius by infiniteTransition.animateFloat(
    initialValue = 0.2f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 2400, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "PulseRadius"
  )

  val pulseAlpha by infiniteTransition.animateFloat(
    initialValue = 0.8f,
    targetValue = 0.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 2400, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "PulseAlpha"
  )

  val beamRotation by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 3500, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "BeamRotation"
  )

  val baseColor = when {
    isThreatDetected -> ThreatCrimson
    isScanning -> WarningGold
    else -> CyberCyan
  }

  Box(
    modifier = modifier
      .size(160.dp)
      .clip(CircleShape)
      .background(ObsidianElevated)
      .border(1.dp, ObsidianBorder, CircleShape),
    contentAlignment = Alignment.Center
  ) {
    Canvas(modifier = Modifier.size(150.dp)) {
      val center = Offset(size.width / 2, size.height / 2)
      val maxRadius = size.width / 2

      // Concentric circles
      drawCircle(
        color = baseColor.copy(alpha = 0.15f),
        radius = maxRadius * 0.33f,
        center = center,
        style = Stroke(width = 1.dp.toPx())
      )
      drawCircle(
        color = baseColor.copy(alpha = 0.20f),
        radius = maxRadius * 0.66f,
        center = center,
        style = Stroke(width = 1.dp.toPx())
      )
      drawCircle(
        color = baseColor.copy(alpha = 0.30f),
        radius = maxRadius * 0.95f,
        center = center,
        style = Stroke(width = 1.5.dp.toPx())
      )

      // Crosshairs
      drawLine(
        color = baseColor.copy(alpha = 0.15f),
        start = Offset(center.x, 0f),
        end = Offset(center.x, size.height),
        strokeWidth = 1.dp.toPx()
      )
      drawLine(
        color = baseColor.copy(alpha = 0.15f),
        start = Offset(0f, center.y),
        end = Offset(size.width, center.y),
        strokeWidth = 1.dp.toPx()
      )

      // Expanding wave pulse
      drawCircle(
        color = baseColor.copy(alpha = pulseAlpha),
        radius = maxRadius * pulseRadius,
        center = center,
        style = Stroke(width = 2.dp.toPx())
      )

      // Core anchor
      drawCircle(
        color = baseColor,
        radius = 5.dp.toPx(),
        center = center
      )
    }

    Column(
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Icon(
        imageVector = Icons.Default.Shield,
        contentDescription = "Shield Radar",
        tint = baseColor,
        modifier = Modifier.size(28.dp)
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = if (isScanning) "AUDITANDO" else "INVIOLABLE",
        color = baseColor,
        fontSize = 9.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      )
    }
  }
}

@Composable
fun CyberMetricCard(
  title: String,
  value: String,
  subtext: String,
  icon: ImageVector,
  accentColor: Color,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = title,
          color = CyberTextSecondary,
          fontSize = 11.sp,
          fontWeight = FontWeight.Medium
        )
        Icon(
          imageVector = icon,
          contentDescription = title,
          tint = accentColor,
          modifier = Modifier.size(18.dp)
        )
      }
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = value,
        color = CyberTextPrimary,
        fontSize = 18.sp,
        fontWeight = FontWeight.Black
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = subtext,
        color = CyberTextMuted,
        fontSize = 10.sp,
        maxLines = 1
      )
    }
  }
}

@Composable
fun ThreatIncidentCard(
  incident: ThreatIncident,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("threat_card_${incident.id}"),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianElevated),
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      if (incident.severity == ThreatSeverity.CRITICAL) ThreatCrimson.copy(alpha = 0.4f) else ObsidianBorder
    )
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(8.dp)
              .clip(CircleShape)
              .background(if (incident.severity == ThreatSeverity.CRITICAL) ThreatCrimson else WarningGold)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = incident.threatName,
            color = CyberTextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
          )
        }

        val (statusText, statusBg, statusColor) = when (incident.status) {
          ThreatStatus.NEUTRALIZED -> Triple("NEUTRALIZADO", CyberEmerald.copy(alpha = 0.15f), CyberEmerald)
          ThreatStatus.QUARANTINED -> Triple("EN CUARENTENA", QuantumPurple.copy(alpha = 0.15f), QuantumPurple)
          ThreatStatus.ANALYZING -> Triple("ANALIZANDO", WarningGold.copy(alpha = 0.15f), WarningGold)
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(statusBg)
            .padding(horizontal = 6.dp, vertical = 3.dp)
        ) {
          Text(
            text = statusText,
            color = statusColor,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = "${incident.category} • ${incident.sourceVector}",
        color = CyberTextMuted,
        fontSize = 11.sp
      )

      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = incident.aiMitigationLog,
        color = CyberTextSecondary,
        fontSize = 11.sp,
        lineHeight = 15.sp
      )

      Spacer(modifier = Modifier.height(6.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = "HASH: ${incident.signatureHash.take(14)}...",
          color = CyberCyanMuted,
          fontSize = 9.sp,
          fontFamily = FontFamily.Monospace
        )
        Text(
          text = incident.timestamp,
          color = CyberTextMuted,
          fontSize = 9.sp
        )
      }
    }
  }
}
