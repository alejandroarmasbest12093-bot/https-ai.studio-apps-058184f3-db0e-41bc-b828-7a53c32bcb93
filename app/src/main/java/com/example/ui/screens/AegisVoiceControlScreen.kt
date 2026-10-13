package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.model.AegisNexusProtocol
import com.example.model.AegisVoiceCommandResult
import com.example.model.AegisVoiceState
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

@Composable
fun AegisVoiceControlScreen(
  state: AegisVoiceState,
  onToggleListening: () -> Unit,
  onToggleTts: () -> Unit,
  onToggleProtocol: (AegisNexusProtocol) -> Unit,
  onResetProtocols: (Boolean) -> Unit,
  onSimulateCommand: (String) -> Unit
) {
  val context = LocalContext.current
  val micPermissionLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestPermission()
  ) { isGranted ->
    if (isGranted) {
      onToggleListening()
    }
  }

  val infiniteTransition = rememberInfiniteTransition(label = "VoicePulse")
  val pulseScale by infiniteTransition.animateFloat(
    initialValue = 1f,
    targetValue = if (state.isListening) 1.25f else 1.05f,
    animationSpec = infiniteRepeatable(
      animation = tween(800, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "PulseScale"
  )

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(ObsidianBackground)
      .padding(16.dp)
      .testTag("aegis_voice_screen"),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Header Banner
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("aegis_voice_header_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(
          1.dp,
          Brush.horizontalGradient(listOf(CyberCyan, QuantumPurple, WarningGold))
        )
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(10.dp)
                  .clip(CircleShape)
                  .background(if (state.isListening) ThreatCrimson else CyberEmerald)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "AEGIS NEXUS VOICE SERVICE",
                fontSize = 15.sp,
                fontWeight = FontWeight.Black,
                color = CyberCyan,
                letterSpacing = 1.2.sp
              )
            }
            IconButton(
              onClick = onToggleTts,
              modifier = Modifier.testTag("toggle_tts_btn")
            ) {
              Icon(
                imageVector = if (state.isTtsFeedbackEnabled) Icons.AutoMirrored.Filled.VolumeUp else Icons.Default.VolumeOff,
                contentDescription = "TTS Feedback",
                tint = if (state.isTtsFeedbackEnabled) WarningGold else CyberTextMuted
              )
            }
          }

          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "Reconocimiento y procesamiento de comandos en lenguaje natural para el control soberano de protocolos de red.",
            fontSize = 11.5.sp,
            color = CyberTextSecondary,
            lineHeight = 16.sp
          )
        }
      }
    }

    // 2. Interactive Voice Orb & Live Audio Visualizer
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("aegis_mic_control_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(130.dp)
          ) {
            // Halo pulse
            if (state.isListening) {
              Box(
                modifier = Modifier
                  .size(120.dp)
                  .scale(pulseScale)
                  .clip(CircleShape)
                  .background(CyberCyan.copy(alpha = 0.2f))
              )
              Box(
                modifier = Modifier
                  .size(100.dp)
                  .scale(pulseScale * 0.9f)
                  .clip(CircleShape)
                  .background(ThreatCrimson.copy(alpha = 0.25f))
              )
            }

            // Central Mic Button
            Box(
              contentAlignment = Alignment.Center,
              modifier = Modifier
                .size(76.dp)
                .clip(CircleShape)
                .background(
                  Brush.radialGradient(
                    colors = if (state.isListening) listOf(ThreatCrimson, ObsidianElevated)
                    else listOf(CyberCyan, ObsidianElevated)
                  )
                )
                .border(
                  2.dp,
                  if (state.isListening) ThreatCrimson else CyberCyan,
                  CircleShape
                )
                .clickable {
                  val hasPerm = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.RECORD_AUDIO
                  ) == PackageManager.PERMISSION_GRANTED
                  if (hasPerm) {
                    onToggleListening()
                  } else {
                    micPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                  }
                }
                .testTag("aegis_mic_button")
            ) {
              Icon(
                imageVector = if (state.isListening) Icons.Default.Mic else Icons.Default.MicOff,
                contentDescription = "Micrófono Aegis Nexus",
                tint = Color.White,
                modifier = Modifier.size(34.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))
          Text(
            text = if (state.isListening) "ESCUCHANDO ORDEN DE VOZ..." else "TOCA PARA HABLAR",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = if (state.isListening) ThreatCrimson else CyberCyan,
            letterSpacing = 1.sp
          )

          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = state.voiceStatusMessage,
            fontSize = 11.sp,
            color = CyberTextSecondary,
            modifier = Modifier.padding(horizontal = 8.dp),
            lineHeight = 15.sp
          )

          if (state.recognizedSpeechText.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(ObsidianElevated)
                .border(0.5.dp, CyberCyan.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
              Text(
                text = "Reconocido: \"${state.recognizedSpeechText}\"",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = WarningGold,
                fontFamily = FontFamily.Monospace
              )
            }
          }
        }
      }
    }

    // 3. Aegis Nexus Network Protocols Matrix
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "PROTOCOLOS AEGIS NEXUS",
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold,
          color = CyberTextPrimary,
          letterSpacing = 1.sp
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          Button(
            onClick = { onResetProtocols(true) },
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = CyberEmerald.copy(alpha = 0.2f),
              contentColor = CyberEmerald
            ),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 2.dp),
            modifier = Modifier.testTag("reset_protocols_btn")
          ) {
            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(12.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Todos ON", fontSize = 9.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    items(AegisNexusProtocol.entries) { protocol ->
      val isActive = state.activeProtocols[protocol] ?: true
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("protocol_card_${protocol.id}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(
          1.dp,
          if (isActive) CyberCyan.copy(alpha = 0.5f) else ObsidianBorder
        )
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(8.dp)
                  .clip(CircleShape)
                  .background(if (isActive) CyberEmerald else ThreatCrimson)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = protocol.protocolName,
                fontSize = 12.5.sp,
                fontWeight = FontWeight.Bold,
                color = if (isActive) CyberTextPrimary else CyberTextMuted
              )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "${protocol.code} • ${protocol.standard}",
              fontSize = 10.sp,
              color = CyberCyan,
              fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
              text = protocol.description,
              fontSize = 10.5.sp,
              color = CyberTextSecondary,
              lineHeight = 14.sp
            )
          }
          Spacer(modifier = Modifier.width(12.dp))
          Switch(
            checked = isActive,
            onCheckedChange = { onToggleProtocol(protocol) },
            colors = SwitchDefaults.colors(
              checkedThumbColor = ObsidianBackground,
              checkedTrackColor = CyberCyan,
              uncheckedThumbColor = CyberTextMuted,
              uncheckedTrackColor = ObsidianElevated
            ),
            modifier = Modifier.testTag("protocol_switch_${protocol.id}")
          )
        }
      }
    }

    // 4. Quick Natural Language Voice Commands
    item {
      Text(
        text = "ÓRDENES EN LENGUAJE NATURAL (EJEMPLOS DE VOZ)",
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = CyberTextPrimary,
        letterSpacing = 0.8.sp
      )
    }

    item {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        QuickCommandButton(
          title = "\"Activar blindaje máximo y Kyber-1024\"",
          desc = "Despliega el escudo maestro soberano y sincroniza el enjambre.",
          color = WarningGold,
          onClick = { onSimulateCommand("Activar blindaje máximo y Kyber-1024") },
          tag = "quick_cmd_shield"
        )
        QuickCommandButton(
          title = "\"Escanear integridad profunda de la red\"",
          desc = "Inicia el barrido exhaustivo de puertos y Zero-Trust Mesh.",
          color = CyberCyan,
          onClick = { onSimulateCommand("Escanear integridad profunda de la red") },
          tag = "quick_cmd_scan"
        )
        QuickCommandButton(
          title = "\"Transmitir pulso cuántico Synapse-Q\"",
          desc = "Emite resonancia telepática cuántica a la constelación orbital.",
          color = QuantumPurple,
          onClick = { onSimulateCommand("Transmitir pulso cuántico Synapse-Q") },
          tag = "quick_cmd_synapse"
        )
        QuickCommandButton(
          title = "\"Verificar enlace orbital satelital LEO\"",
          desc = "Alinea baliza Direct-to-Cell para tráfico seguro.",
          color = CyberEmerald,
          onClick = { onSimulateCommand("Verificar enlace orbital satelital LEO") },
          tag = "quick_cmd_sat"
        )
        QuickCommandButton(
          title = "\"Pausar patrulla de Enjambre IA\"",
          desc = "Pausa temporalmente las rondas autónomas perimetrales.",
          color = ThreatCrimson,
          onClick = { onSimulateCommand("Pausar patrulla de Enjambre IA") },
          tag = "quick_cmd_pause_swarm"
        )
      }
    }

    // 5. Command Execution History
    if (state.commandHistory.isNotEmpty()) {
      item {
        Text(
          text = "HISTORIAL DE COMANDOS DE VOZ EJECUTADOS",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = CyberTextPrimary,
          letterSpacing = 0.8.sp
        )
      }

      items(state.commandHistory) { cmd ->
        CommandHistoryCard(cmd)
      }
    }
  }
}

@Composable
private fun QuickCommandButton(
  title: String,
  desc: String,
  color: Color,
  onClick: () -> Unit,
  tag: String
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .testTag(tag),
    shape = RoundedCornerShape(10.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.35f))
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(32.dp)
          .clip(CircleShape)
          .background(color.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(Icons.Default.Bolt, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
      }
      Spacer(modifier = Modifier.width(10.dp))
      Column(modifier = Modifier.weight(1f)) {
        Text(title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CyberTextPrimary)
        Text(desc, fontSize = 10.sp, color = CyberTextSecondary)
      }
      Text("EJECUTAR", fontSize = 9.sp, fontWeight = FontWeight.Black, color = color)
    }
  }
}

@Composable
private fun CommandHistoryCard(cmd: AegisVoiceCommandResult) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("voice_history_item_${cmd.timestamp}"),
    shape = RoundedCornerShape(10.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = androidx.compose.foundation.BorderStroke(0.5.dp, ObsidianBorder)
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = if (cmd.isSuccess) Icons.Default.CheckCircle else Icons.Default.Warning,
            contentDescription = null,
            tint = if (cmd.isSuccess) CyberEmerald else ThreatCrimson,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = cmd.detectedIntent,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = CyberCyan,
            fontFamily = FontFamily.Monospace
          )
        }
        Text(
          text = cmd.timestamp,
          fontSize = 10.sp,
          color = CyberTextMuted
        )
      }

      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = "Voz: \"${cmd.commandText}\"",
        fontSize = 11.5.sp,
        fontWeight = FontWeight.Medium,
        color = WarningGold
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = cmd.actionTaken,
        fontSize = 11.sp,
        color = CyberTextSecondary
      )
    }
  }
}
