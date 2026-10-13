package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.BluetoothSearching
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.DataExploration
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.LinkOff
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SatelliteAlt
import androidx.compose.material.icons.filled.Stream
import java.util.Locale
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.example.ui.components.CyberMetricCard
import com.example.ui.components.OwnerSovereignHeader
import com.example.ui.components.QuantumMeshRadar
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
fun OverviewScreen(
  state: CyberUiState,
  onDeepScanClick: () -> Unit,
  onSimulateThreatClick: () -> Unit,
  onNavigateToCertificate: () -> Unit,
  onNavigateToScanner: () -> Unit = {},
  onNavigateToSentinel: () -> Unit = {},
  onNavigateToSatellite: () -> Unit = {},
  onNavigateToEmailShield: () -> Unit = {},
  onNavigateToSynapseQ: () -> Unit = {},
  onTestNotificationClick: () -> Unit = {},
  onToggleNotificationAlerts: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianBackground)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(8.dp))
      // Sovereign Ownership Banner
      OwnerSovereignHeader(
        license = state.license,
        onViewCertificateClick = onNavigateToCertificate
      )
    }

    // Main Quantum Radar and Inviolable Status Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("quantum_radar_hub_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "RED CUÁNTICA SOBERANA",
                color = CyberCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
              )
              Text(
                text = "Arquitectura Inviolable",
                color = CyberTextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
              )
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(CyberEmerald.copy(alpha = 0.15f))
                .border(1.dp, CyberEmerald.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(CyberEmerald)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                  text = "ENCRIPTADO POST-CUÁNTICO",
                  color = CyberEmerald,
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          QuantumMeshRadar(
            isScanning = state.isScanningNetwork,
            isThreatDetected = state.isSimulatingAttack
          )

          Spacer(modifier = Modifier.height(16.dp))

          // Ephemeral key rotation display
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(10.dp))
              .background(ObsidianElevated)
              .border(1.dp, ObsidianBorder, RoundedCornerShape(10.dp))
              .padding(10.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "CLAVE EFÍMERA CUÁNTICA ACTIVA (Kyber-1024)",
                  color = CyberTextMuted,
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold
                )
                Text(
                  text = state.activeEphemeralKey,
                  color = CyberCyan,
                  fontSize = 10.sp,
                  fontFamily = FontFamily.Monospace,
                  maxLines = 1
                )
              }
              Spacer(modifier = Modifier.width(8.dp))
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(6.dp))
                  .background(CyberCyan.copy(alpha = 0.15f))
                  .padding(horizontal = 8.dp, vertical = 4.dp)
              ) {
                Text(
                  text = "Rotación: ${state.ephemeralKeyCountdownSeconds}s",
                  color = CyberCyan,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }

          if (state.isScanningNetwork) {
            Spacer(modifier = Modifier.height(12.dp))
            LinearProgressIndicator(
              progress = { state.networkScanProgress },
              modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
              color = CyberCyan,
              trackColor = ObsidianBorder
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Escaneando topología de retículos cuánticos... ${(state.networkScanProgress * 100).toInt()}%",
              color = CyberTextSecondary,
              fontSize = 10.sp
            )
          }
        }
      }
    }

    // Quick Actions
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Button(
          onClick = onDeepScanClick,
          modifier = Modifier
            .weight(1f)
            .testTag("scan_network_button"),
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = CyberCyan),
          enabled = !state.isScanningNetwork
        ) {
          Icon(
            imageVector = Icons.Default.DataExploration,
            contentDescription = "Escanear",
            tint = ObsidianBackground,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = if (state.isScanningNetwork) "Auditando..." else "Auditar Red",
            color = ObsidianBackground,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
          )
        }

        OutlinedButton(
          onClick = onSimulateThreatClick,
          modifier = Modifier
            .weight(1f)
            .testTag("test_sentinel_button"),
          shape = RoundedCornerShape(10.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, ThreatCrimson),
          enabled = !state.isSimulatingAttack
        ) {
          Icon(
            imageVector = Icons.Default.BugReport,
            contentDescription = "Test Amenaza",
            tint = ThreatCrimson,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = if (state.isSimulatingAttack) "Repeliendo..." else "Probar IA",
            color = ThreatCrimson,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }

    // 4 Tactical Metric Cards
    item {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          CyberMetricCard(
            title = "Enjambre IA & Privacidad",
            value = "6 DRONES / 100%",
            subtext = "Privacidad & Antivirus 24/7",
            icon = Icons.Default.Shield,
            accentColor = CyberEmerald,
            modifier = Modifier.weight(1f)
          )
          CyberMetricCard(
            title = "Ataques Repelidos",
            value = "%,d".format(state.threatsBlockedTotal),
            subtext = "Cero brechas históricas",
            icon = Icons.Default.Lock,
            accentColor = CyberCyan,
            modifier = Modifier.weight(1f)
          )
        }
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          CyberMetricCard(
            title = "Paquetes Analizados",
            value = "%,d".format(state.packetsScanned),
            subtext = "Rendimiento a velocidad de línea",
            icon = Icons.Default.Speed,
            accentColor = QuantumPurple,
            modifier = Modifier.weight(1f)
          )
          CyberMetricCard(
            title = "Fidelidad Cuántica",
            value = "${state.quantumEntropyPercentage}%",
            subtext = "Imposible de desencriptar",
            icon = Icons.Default.Hub,
            accentColor = WarningGold,
            modifier = Modifier.weight(1f)
          )
        }
      }
    }

    // Quantum Neuro-Defense Breakthrough Banner
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("overview_neuro_defense_card"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, Brush.horizontalGradient(listOf(QuantumPurple, CyberCyan)))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(QuantumPurple.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Psychology,
                  contentDescription = null,
                  tint = CyberCyan,
                  modifier = Modifier.size(20.dp)
                )
              }
              Column {
                Text(
                  text = "NEURO-DEFENSA CUÁNTICA IA",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  color = CyberTextPrimary
                )
                Text(
                  text = "Gemini 3.5 Flash • Antígenos Inmuno-IA",
                  fontSize = 10.sp,
                  color = QuantumPurple
                )
              }
            }

            Button(
              onClick = onNavigateToSentinel,
              colors = ButtonDefaults.buttonColors(containerColor = CyberCyan, contentColor = ObsidianBackground),
              shape = RoundedCornerShape(8.dp)
            ) {
              Text("Abrir Malla", fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Text(
              text = "• Inmunidad: ${state.quantumNeuroDefense.zeroDayImmunityRate}%",
              fontSize = 10.sp,
              color = CyberEmerald,
              fontFamily = FontFamily.Monospace
            )
            Text(
              text = "• Antígenos: ${state.quantumNeuroDefense.activeDigitalAntigens.size}",
              fontSize = 10.sp,
              color = CyberCyan,
              fontFamily = FontFamily.Monospace
            )
            Text(
              text = "• Malla: ${if (state.quantumNeuroDefense.isNeuroDefenseActive) "ACTIVA" else "PAUSA"}",
              fontSize = 10.sp,
              color = if (state.quantumNeuroDefense.isNeuroDefenseActive) CyberEmerald else WarningGold,
              fontFamily = FontFamily.Monospace
            )
          }
        }
      }
    }

    // Quantum AI Swarm Email Protection Card (Alejandro Armas)
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("overview_email_quantum_shield_card"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, Brush.horizontalGradient(listOf(CyberCyan, QuantumPurple)))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(CyberCyan.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.MarkEmailRead,
                  contentDescription = "Correo PQC",
                  tint = CyberCyan,
                  modifier = Modifier.size(20.dp)
                )
              }
              Column {
                Text(
                  text = "CORREO BLINDADO CON ENJAMBRE IA",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = CyberCyan
                )
                Text(
                  text = state.emailQuantumShield.protectedEmail,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Black,
                  fontFamily = FontFamily.Monospace,
                  color = CyberTextPrimary
                )
              }
            }

            Button(
              onClick = onNavigateToEmailShield,
              colors = ButtonDefaults.buttonColors(containerColor = CyberCyan, contentColor = ObsidianBackground),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.testTag("overview_goto_email_shield_btn")
            ) {
              Text("Gestionar", fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Text(
              text = "• Kyber-1024: ${if (state.emailQuantumShield.isSwarmShieldActive) "ACTIVO" else "PAUSA"}",
              fontSize = 10.sp,
              color = if (state.emailQuantumShield.isSwarmShieldActive) CyberEmerald else ThreatCrimson,
              fontFamily = FontFamily.Monospace
            )
            Text(
              text = "• Amenazas: ${state.emailQuantumShield.threatsNeutralized}",
              fontSize = 10.sp,
              color = CyberEmerald,
              fontFamily = FontFamily.Monospace
            )
            Text(
              text = "• Dark Web: LIMPIO",
              fontSize = 10.sp,
              color = CyberCyan,
              fontFamily = FontFamily.Monospace
            )
          }
        }
      }
    }

    // Armas Satellite Chip Sovereign Calling Banner
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("overview_satellite_chip_banner_card"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, Brush.horizontalGradient(listOf(WarningGold, CyberCyan)))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(WarningGold.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.SatelliteAlt,
                  contentDescription = null,
                  tint = WarningGold,
                  modifier = Modifier.size(20.dp)
                )
              }
              Column {
                Text(
                  text = "CHIP SATELITAL ARMAS",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  color = CyberTextPrimary
                )
                Text(
                  text = "Llamadas 100% Satelitales • 0 Operadoras",
                  fontSize = 10.sp,
                  color = WarningGold
                )
              }
            }

            Button(
              onClick = onNavigateToSatellite,
              colors = ButtonDefaults.buttonColors(containerColor = WarningGold, contentColor = ObsidianBackground),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.testTag("btn_open_satellite_from_overview")
            ) {
              Text("Llamar", fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Text(
              text = "• Enlace: ${state.satelliteChipState.signalDbM} dBm LEO",
              fontSize = 10.sp,
              color = CyberEmerald,
              fontFamily = FontFamily.Monospace
            )
            Text(
              text = "• Número: ${state.satelliteChipState.chipPhoneNumber}",
              fontSize = 10.sp,
              color = CyberCyan,
              fontFamily = FontFamily.Monospace
            )
            Text(
              text = "• Bypass: ${if (state.satelliteChipState.bypassTerrestrialTowers) "100% SOBERANO" else "MIXTO"}",
              fontSize = 10.sp,
              color = if (state.satelliteChipState.bypassTerrestrialTowers) CyberEmerald else WarningGold,
              fontFamily = FontFamily.Monospace
            )
          }
        }
      }
    }

    // Synapse-Q™: Breakthrough Non-Verbal Telepathic Communication Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("overview_synapse_q_banner_card"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, Brush.horizontalGradient(listOf(QuantumPurple, CyberCyan)))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(QuantumPurple.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Stream,
                  contentDescription = null,
                  tint = CyberCyan,
                  modifier = Modifier.size(20.dp)
                )
              }
              Column {
                Text(
                  text = "SYNAPSE-Q™ PROTOCOL",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  color = CyberTextPrimary
                )
                Text(
                  text = "Resonancia Telepática Cuántica • Sin Palabras",
                  fontSize = 10.sp,
                  color = CyberCyan
                )
              }
            }

            Button(
              onClick = onNavigateToSynapseQ,
              colors = ButtonDefaults.buttonColors(containerColor = QuantumPurple, contentColor = Color.White),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.testTag("btn_open_synapse_from_overview")
            ) {
              Text(
                text = "CONECTAR",
                fontSize = 10.5.sp,
                fontWeight = FontWeight.Black
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "Un nuevo paradigma revolucionario de comunicación: transmite matrices completas de pensamiento e intención instantánea colapsando estados cuánticos de Bell, combinados con bio-háptica en la mano y frecuencias armónicas Solfeggio.",
            fontSize = 11.sp,
            color = CyberTextSecondary,
            lineHeight = 15.sp
          )

          Spacer(modifier = Modifier.height(8.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Text(
              text = "• Enlace: ${state.synapseQState.entanglementFidelity}% EPR",
              fontSize = 10.sp,
              color = CyberEmerald,
              fontFamily = FontFamily.Monospace
            )
            Text(
              text = "• Onda: ${state.synapseQState.activeIntention.category.label}",
              fontSize = 10.sp,
              color = CyberCyan,
              fontFamily = FontFamily.Monospace
            )
            Text(
              text = "• Frecuencia: ${state.synapseQState.activeIntention.frequencyHz} Hz",
              fontSize = 10.sp,
              color = WarningGold,
              fontFamily = FontFamily.Monospace
            )
          }
        }
      }
    }

    // Scanner & Live Network Dismantle Banner
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("overview_scanner_banner_card"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, Brush.horizontalGradient(listOf(ThreatCrimson, CyberCyan)))
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(ThreatCrimson.copy(alpha = 0.18f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Radar,
                contentDescription = "Radar",
                tint = ThreatCrimson,
                modifier = Modifier.size(20.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "RED CONECTADA & ESCÁNER TOTAL",
                color = CyberCyan,
                fontSize = 12.sp,
                fontWeight = FontWeight.Black
              )
              Text(
                text = "${state.connectedNetwork.networkName} • ${state.scannedApps.size} apps auditadas",
                color = CyberTextSecondary,
                fontSize = 10.sp
              )
            }
          }

          Button(
            onClick = onNavigateToScanner,
            modifier = Modifier.height(36.dp).testTag("btn_go_to_scanner"),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ThreatCrimson, contentColor = Color.White),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp)
          ) {
            Icon(Icons.Default.CleaningServices, contentDescription = "Desmantelar", modifier = Modifier.size(13.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Abrir Escáner", fontSize = 10.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // Centinela Bluetooth & Escáner de Virus RF
    item {
      val btState = state.bluetoothScanState
      val unblockedBtThreats = btState.scannedDevices.count { it.detectedVirusOrExploit != null && !it.isBlocked }

      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("overview_bluetooth_sentinel_banner"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(
          1.dp,
          if (unblockedBtThreats > 0) ThreatCrimson.copy(alpha = 0.7f) else CyberCyan.copy(alpha = 0.4f)
        )
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(if (unblockedBtThreats > 0) ThreatCrimson.copy(alpha = 0.18f) else CyberCyan.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = if (btState.isScanning) Icons.Default.BluetoothSearching else Icons.Default.Bluetooth,
                contentDescription = "Bluetooth Sentinel",
                tint = if (unblockedBtThreats > 0) ThreatCrimson else CyberCyan,
                modifier = Modifier.size(20.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = "CENTINELA BLUETOOTH & VIRUS RF",
                  color = if (unblockedBtThreats > 0) ThreatCrimson else CyberCyan,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Black
                )
              }
              Text(
                text = if (unblockedBtThreats > 0)
                  "⚠️ $unblockedBtThreats virus en rango (BrakTooth / BlueBorne)"
                else
                  "${btState.scannedDevices.size} dispositivos • Blindaje Stealth 100%",
                color = if (unblockedBtThreats > 0) ThreatCrimson else CyberTextSecondary,
                fontSize = 10.sp
              )
            }
          }

          Button(
            onClick = onNavigateToScanner,
            modifier = Modifier.height(36.dp).testTag("btn_go_to_bluetooth_scanner"),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = if (unblockedBtThreats > 0) ThreatCrimson else CyberCyan,
              contentColor = if (unblockedBtThreats > 0) Color.White else ObsidianBackground
            ),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp)
          ) {
            Icon(Icons.Default.Radar, contentDescription = null, modifier = Modifier.size(13.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Escanear BT", fontSize = 10.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // Enjambre IA: Auto-Actualización y Auto-Reparación de Fallas
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("overview_swarm_self_repair_banner"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(
          1.2.dp,
          Brush.horizontalGradient(listOf(WarningGold, CyberEmerald, CyberCyan))
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
            Row(
              modifier = Modifier.weight(1f),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(WarningGold.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Healing,
                  contentDescription = "Auto Reparación",
                  tint = WarningGold,
                  modifier = Modifier.size(20.dp)
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "ENJAMBRE IA: AUTO-REPARACIÓN & EVOLUCIÓN",
                    color = WarningGold,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.8.sp
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Box(
                    modifier = Modifier
                      .size(6.dp)
                      .clip(CircleShape)
                      .background(CyberEmerald)
                  )
                }
                Text(
                  text = "${state.swarmTelemetry.faultsSelfHealedCount} fallas auto-sanadas • ${state.swarmEvolutionUpdates.size} capacidades descubiertas",
                  color = CyberTextSecondary,
                  fontSize = 10.sp
                )
              }
            }

            Button(
              onClick = onNavigateToSentinel,
              modifier = Modifier.height(34.dp).testTag("btn_overview_go_to_swarm_repair"),
              shape = RoundedCornerShape(8.dp),
              colors = ButtonDefaults.buttonColors(containerColor = WarningGold, contentColor = ObsidianBackground),
              contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp)
            ) {
              Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(13.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Abrir Enjambre", fontSize = 10.sp, fontWeight = FontWeight.Black)
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Mini metrics bar
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(6.dp))
                .background(ObsidianElevated)
                .padding(horizontal = 8.dp, vertical = 5.dp)
            ) {
              Column {
                Text(text = "Salud Sistema", color = CyberTextMuted, fontSize = 8.sp)
                Text(text = "${state.swarmTelemetry.healthIntegrityPercent}% Óptimo", color = CyberEmerald, fontSize = 10.sp, fontWeight = FontWeight.Bold)
              }
            }
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(6.dp))
                .background(ObsidianElevated)
                .padding(horizontal = 8.dp, vertical = 5.dp)
            ) {
              Column {
                Text(text = "Auto-Parches", color = CyberTextMuted, fontSize = 8.sp)
                Text(text = "${state.swarmTelemetry.autoAppliedPatches} Inyectados", color = CyberCyan, fontSize = 10.sp, fontWeight = FontWeight.Bold)
              }
            }
            Box(
              modifier = Modifier
                .weight(1.2f)
                .clip(RoundedCornerShape(6.dp))
                .background(ObsidianElevated)
                .padding(horizontal = 8.dp, vertical = 5.dp)
            ) {
              Column {
                Text(text = "Auto-Sanación", color = CyberTextMuted, fontSize = 8.sp)
                Text(
                  text = if (state.swarmTelemetry.autoSelfRepairEnabled) "Activa 24/7" else "Pausada",
                  color = if (state.swarmTelemetry.autoSelfRepairEnabled) WarningGold else CyberTextMuted,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }
        }
      }
    }

    // Enjambre IA en Vivo: Rastreo de Enlaces y Violaciones de Privacidad
    item {
      val swarmState = state.privacySwarmTracker
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("overview_privacy_swarm_tracker_banner"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(
          1.2.dp,
          Brush.horizontalGradient(listOf(QuantumPurple, CyberCyan, CyberEmerald))
        )
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              modifier = Modifier.weight(1f),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(QuantumPurple.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Psychology,
                  contentDescription = "Enjambre IA",
                  tint = QuantumPurple,
                  modifier = Modifier.size(20.dp)
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "ENJAMBRE IA: RASTREO DE ENLACES & DATOS",
                    color = QuantumPurple,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.6.sp
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Box(
                    modifier = Modifier
                      .size(6.dp)
                      .clip(CircleShape)
                      .background(if (swarmState.isTrackingLive) CyberEmerald else ThreatCrimson)
                  )
                }
                Text(
                  text = "${swarmState.linksBlockedCount} enlaces interceptados • ${swarmState.rogueDevicesNeutralizedCount} balizas aisladas",
                  color = CyberTextSecondary,
                  fontSize = 10.sp
                )
              }
            }

            Button(
              onClick = onNavigateToSentinel,
              modifier = Modifier
                .height(36.dp)
                .testTag("btn_go_to_swarm_tracker"),
              shape = RoundedCornerShape(8.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = QuantumPurple,
                contentColor = Color.Black
              ),
              contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp)
            ) {
              Icon(Icons.Default.LinkOff, contentDescription = null, modifier = Modifier.size(13.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Auditar Links", fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(ObsidianElevated)
                .padding(8.dp)
            ) {
              Column {
                Text("FILTRACIONES EVITADAS", color = ThreatCrimson, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                Text("${swarmState.linksBlockedCount} URLs purgadas", color = CyberTextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Black)
              }
            }
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(ObsidianElevated)
                .padding(8.dp)
            ) {
              Column {
                Text("DISPOSITIVOS ESPÍA", color = WarningGold, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                Text("${swarmState.rogueDevicesNeutralizedCount} silenciados", color = CyberTextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Black)
              }
            }
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(ObsidianElevated)
                .padding(8.dp)
            ) {
              Column {
                Text("DATOS BLINDADOS", color = CyberEmerald, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                Text("${swarmState.dataPointsProtectedCount / 1000}k puntos", color = CyberTextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Black)
              }
            }
          }
        }
      }
    }

    // Geolocalización GPS en Vivo & Paradero del Usuario
    item {
      val gps = state.gpsLocationState
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("overview_gps_location_banner"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(
          1.2.dp,
          Brush.horizontalGradient(listOf(CyberCyan, CyberEmerald))
        )
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              modifier = Modifier.weight(1f),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(CyberCyan.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.GpsFixed,
                  contentDescription = "GPS",
                  tint = CyberCyan,
                  modifier = Modifier.size(20.dp)
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "GPS SOBERANO & PARADERO EXACTO",
                    color = CyberCyan,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.6.sp
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Box(
                    modifier = Modifier
                      .size(6.dp)
                      .clip(CircleShape)
                      .background(CyberEmerald)
                  )
                }
                Text(
                  text = "${String.format(Locale.US, "%.5f", gps.latitude)}° N, ${String.format(Locale.US, "%.5f", Math.abs(gps.longitude))}° W • ±${String.format(Locale.US, "%.1f", gps.accuracyMeters)}m",
                  color = CyberTextSecondary,
                  fontSize = 10.sp,
                  fontFamily = FontFamily.Monospace
                )
              }
            }

            Button(
              onClick = onNavigateToSentinel,
              modifier = Modifier
                .height(36.dp)
                .testTag("btn_go_to_gps_telemetry"),
              shape = RoundedCornerShape(8.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = CyberCyan,
                contentColor = Color.Black
              ),
              contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp)
            ) {
              Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(13.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Ver GPS", fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
          }

          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(ObsidianElevated)
              .border(1.dp, CyberEmerald.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
              .padding(8.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = null,
                tint = CyberEmerald,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "${gps.humanReadableParadero} • Anti-Stalkerware: ${if (gps.isLocationCloakedAgainstTrackers) "ACTIVO" else "OFF"}",
                color = CyberEmerald,
                fontSize = 9.5.sp,
                fontWeight = FontWeight.SemiBold
              )
            }
          }
        }
      }
    }

    // Acciones Críticas: Expediente Forense y Pruebas para Denuncia Formal
    item {
      val dossier = state.legalEvidenceDossier
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("overview_legal_evidence_banner"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(
          1.2.dp,
          Brush.horizontalGradient(listOf(WarningGold, ThreatCrimson))
        )
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              modifier = Modifier.weight(1f),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(WarningGold.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Gavel,
                  contentDescription = "Acciones Legales",
                  tint = WarningGold,
                  modifier = Modifier.size(20.dp)
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "ACCIONES CRÍTICAS & PRUEBAS LEGALES",
                    color = WarningGold,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.6.sp
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Box(
                    modifier = Modifier
                      .size(6.dp)
                      .clip(CircleShape)
                      .background(ThreatCrimson)
                  )
                }
                Text(
                  text = "${dossier.evidenceItems.size} elementos probatorios sellados con hash SHA-256 inmutable",
                  color = CyberTextSecondary,
                  fontSize = 10.sp
                )
              }
            }

            Button(
              onClick = onNavigateToSentinel,
              modifier = Modifier
                .height(36.dp)
                .testTag("btn_go_to_legal_dossier"),
              shape = RoundedCornerShape(8.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = WarningGold,
                contentColor = Color.Black
              ),
              contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp)
            ) {
              Icon(Icons.Default.Gavel, contentDescription = null, modifier = Modifier.size(13.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Expediente", fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
          }

          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(ObsidianElevated)
              .padding(8.dp)
          ) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
              Text(
                text = "DESTINO: ${dossier.targetRecipientAuthority}",
                color = WarningGold,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "CADENA DE CUSTODIA: ${dossier.masterChainOfCustodyHash.take(48)}...",
                color = CyberEmerald,
                fontSize = 8.sp,
                fontFamily = FontFamily.Monospace
              )
            }
          }
        }
      }
    }

    // Room Database SQLite Persistent Vault Quick-Banner
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("overview_room_database_banner"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberEmerald.copy(alpha = 0.5f))
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(CyberEmerald.copy(alpha = 0.18f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Storage,
                contentDescription = "Base de Datos Room",
                tint = CyberEmerald,
                modifier = Modifier.size(20.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = "BASE DE DATOS ROOM (SQLITE)",
                  color = CyberEmerald,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Black,
                  letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                  modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(CyberEmerald)
                )
              }
              Text(
                text = "${state.threatLogs.size} incidentes históricos persistidos offline",
                color = CyberTextSecondary,
                fontSize = 10.sp
              )
            }
          }

          Button(
            onClick = onNavigateToSentinel,
            modifier = Modifier.height(34.dp).testTag("btn_go_to_room_history"),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CyberEmerald, contentColor = ObsidianBackground),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp)
          ) {
            Icon(Icons.Default.Storage, contentDescription = "Ver Logs", modifier = Modifier.size(13.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Ver Historial Room", fontSize = 10.sp, fontWeight = FontWeight.Black)
          }
        }
      }
    }

    // Real-Time Notification System (WorkManager) System Tray Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("overview_workmanager_notification_card"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(
          1.2.dp,
          if (state.systemTrayAlertsEnabled) ThreatCrimson.copy(alpha = 0.6f) else ObsidianBorder
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
            Row(
              modifier = Modifier.weight(1f),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(38.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(
                    if (state.systemTrayAlertsEnabled) ThreatCrimson.copy(alpha = 0.2f)
                    else ObsidianBorder.copy(alpha = 0.3f)
                  ),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = if (state.systemTrayAlertsEnabled) Icons.Default.NotificationsActive else Icons.Default.NotificationsOff,
                  contentDescription = "Alertas de Ciberamenaza",
                  tint = if (state.systemTrayAlertsEnabled) ThreatCrimson else CyberTextMuted,
                  modifier = Modifier.size(20.dp)
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "ALERTAS WORKMANAGER (BANDEJA DEL SISTEMA)",
                    color = if (state.systemTrayAlertsEnabled) ThreatCrimson else CyberTextMuted,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.8.sp
                  )
                }
                Text(
                  text = "Alertas inmediatas en tiempo real ante virus o incursiones",
                  color = CyberTextSecondary,
                  fontSize = 10.sp
                )
              }
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(
                  if (state.systemTrayAlertsEnabled) CyberEmerald.copy(alpha = 0.15f)
                  else ThreatCrimson.copy(alpha = 0.15f)
                )
                .border(
                  1.dp,
                  if (state.systemTrayAlertsEnabled) CyberEmerald.copy(alpha = 0.5f)
                  else ThreatCrimson.copy(alpha = 0.5f),
                  RoundedCornerShape(6.dp)
                )
                .padding(horizontal = 7.dp, vertical = 3.dp)
            ) {
              Text(
                text = if (state.systemTrayAlertsEnabled) "ACTIVO" else "PAUSADO",
                color = if (state.systemTrayAlertsEnabled) CyberEmerald else ThreatCrimson,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Detail info box
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(ObsidianElevated)
              .padding(10.dp)
          ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = "Despachador Inmediato:",
                  color = CyberTextMuted,
                  fontSize = 10.sp
                )
                Text(
                  text = "WorkManager CoroutineWorker (Expedited)",
                  color = CyberCyan,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  fontFamily = FontFamily.Monospace
                )
              }
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = "Alertas Emitidas:",
                  color = CyberTextMuted,
                  fontSize = 10.sp
                )
                Text(
                  text = "${state.totalAlertsDispatched} notificadas a bandeja",
                  color = WarningGold,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold
                )
              }
              if (state.lastDispatchedNotification != null) {
                Text(
                  text = "Última: ${state.lastDispatchedNotification}",
                  color = CyberEmerald,
                  fontSize = 10.sp,
                  fontFamily = FontFamily.Monospace
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Button(
              onClick = onTestNotificationClick,
              modifier = Modifier
                .weight(1f)
                .height(36.dp)
                .testTag("btn_test_workmanager_notification"),
              shape = RoundedCornerShape(8.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = ThreatCrimson,
                contentColor = Color.White
              ),
              contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp)
            ) {
              Icon(
                imageVector = Icons.Default.NotificationsActive,
                contentDescription = "Probar Alerta",
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Probar Alerta en Bandeja",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }

            OutlinedButton(
              onClick = onToggleNotificationAlerts,
              modifier = Modifier
                .height(36.dp)
                .testTag("btn_toggle_workmanager_alerts"),
              shape = RoundedCornerShape(8.dp),
              border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder),
              colors = ButtonDefaults.outlinedButtonColors(
                contentColor = if (state.systemTrayAlertsEnabled) WarningGold else CyberEmerald
              ),
              contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp)
            ) {
              Text(
                text = if (state.systemTrayAlertsEnabled) "Pausar" else "Activar",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    }

    // Real-Time Blocked Traffic Banner (Recharts Live Stream)
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("overview_traffic_summary_card"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberCyan.copy(alpha = 0.4f))
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
                  .background(CyberEmerald)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "TRÁFICO BLOQUEADO POR ENJAMBRE IA (RECHARTS)",
                color = CyberCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
              )
            }
            Text(
              text = "EN VIVO",
              color = CyberEmerald,
              fontSize = 10.sp,
              fontWeight = FontWeight.Black
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(ObsidianElevated)
                .padding(10.dp)
            ) {
              Column {
                Text("Entrante (Ingress Bloqueado)", color = CyberTextMuted, fontSize = 9.sp)
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = "${"%.1f".format(state.trafficSummary.currentIncomingBlockedMbps)} Mbps",
                  color = CyberCyan,
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Black,
                  fontFamily = FontFamily.Monospace
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text("DDoS & Exploits Pulverizados", color = CyberTextSecondary, fontSize = 8.sp)
              }
            }

            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(ObsidianElevated)
                .padding(10.dp)
            ) {
              Column {
                Text("Saliente (Egress Bloqueado)", color = CyberTextMuted, fontSize = 9.sp)
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = "${"%.1f".format(state.trafficSummary.currentOutgoingBlockedMbps)} Mbps",
                  color = QuantumPurple,
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Black,
                  fontFamily = FontFamily.Monospace
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text("Spyware & Exfil Neutralizados", color = CyberTextSecondary, fontSize = 8.sp)
              }
            }
          }
        }
      }
    }

    // Enterprise Nodes Section
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "ENCLAVES EMPRESARIALES ACTIVOS",
          color = CyberCyan,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp
        )
        Text(
          text = "${state.nodes.size} Nodos Encriptados",
          color = CyberTextMuted,
          fontSize = 11.sp
        )
      }
    }

    items(state.nodes) { node ->
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
          ) {
            Box(
              modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(CyberEmerald)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = node.location,
                color = CyberTextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "${node.id} • Latencia: ${node.latencyMs}ms • Q-Fidelity: ${node.quantumFidelity}%",
                color = CyberTextMuted,
                fontSize = 10.sp
              )
            }
          }
          Text(
            text = "${node.activeBandwidthGbps} Gbps",
            color = CyberCyan,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
          )
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(20.dp))
    }
  }
}
