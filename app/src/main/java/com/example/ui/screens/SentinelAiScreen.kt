package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Coronavirus
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import android.content.Context
import com.example.model.AiSwarmAgent
import com.example.model.AiSwarmEvolutionUpdate
import com.example.model.EmailThreatType
import com.example.model.FlowDirection
import com.example.model.SwarmSelfRepairEvent
import com.example.model.SwarmUpdateStatus
import com.example.model.ThreatIncident
import com.example.model.VirusCategoryBlockStatus
import com.example.ui.components.AiSwarmPrivacyTrackerView
import com.example.ui.components.GpsLocationTelemetryView
import com.example.ui.components.LegalForensicDossierView
import com.example.ui.components.QuantumNeuroDefenseView
import com.example.ui.components.RealtimeTrafficDashboardView
import com.example.ui.components.ThreatIncidentCard
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
fun SentinelAiScreen(
  state: CyberUiState,
  onToggleSentinel: () -> Unit,
  onSimulateAttack: (String) -> Unit,
  onTriggerSwarmPurge: () -> Unit,
  onBoostPrivacy: () -> Unit,
  onSimulateVirusWave: (String) -> Unit,
  onToggleTrafficLive: () -> Unit = {},
  onSelectTrafficFilter: (FlowDirection?) -> Unit = {},
  onInjectTrafficBurst: () -> Unit = {},
  onDeleteThreatLog: (Long) -> Unit = {},
  onClearAllThreatLogs: () -> Unit = {},
  onRecordCustomThreatToRoom: (String) -> Unit = {},
  onToggleSystemTrayAlerts: () -> Unit = {},
  onTriggerTestNotificationAlert: () -> Unit = {},
  onTriggerSwarmEvolution: () -> Unit = {},
  onToggleAutoEvolution: () -> Unit = {},
  onTriggerSelfRepair: () -> Unit = {},
  onToggleAutoSelfRepair: () -> Unit = {},
  onRollbackSwarmUpdate: (String) -> Unit = {},
  onActivateSwarmUpdate: (String) -> Unit = {},
  onTriggerPrivacySwarmScan: () -> Unit = {},
  onNeutralizeLeakedLink: (String) -> Unit = {},
  onNeutralizeRogueDevice: (String) -> Unit = {},
  onToggleAutoNeutralizeLeaks: () -> Unit = {},
  onToggleDataBrokerFirewall: () -> Unit = {},
  onRefreshGpsLocation: (Context) -> Unit = {},
  onToggleLocationCloak: () -> Unit = {},
  onSimulateGpsLeakBlock: (String) -> Unit = {},
  onFreezeLegalEvidence: () -> Unit = {},
  onGetComplaintText: () -> String = { "" },
  onMarkLegalDossierExported: () -> Unit = {},
  onToggleNeuroDefense: () -> Unit = {},
  onToggleMemoryMorphing: () -> Unit = {},
  onSynthesizeImmediateAntigen: (String, String) -> Unit = { _, _ -> },
  onDeployHologram: (String, String) -> Unit = { _, _ -> },
  onAnalyzeGemini: (String) -> Unit = {},
  onSetCustomApiKey: (String) -> Unit = {},
  onNavigateToEmailShield: () -> Unit = {},
  onRunEmailAudit: () -> Unit = {},
  onSimulateEmailThreat: (EmailThreatType) -> Unit = {},
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableIntStateOf(0) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianBackground)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(8.dp))
      // Sentinel & Swarm Master Header Banner
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("sentinel_status_banner"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(
          1.5.dp,
          Brush.horizontalGradient(listOf(CyberEmerald, CyberCyan, QuantumPurple))
        )
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(46.dp)
                  .clip(RoundedCornerShape(12.dp))
                  .background(CyberEmerald.copy(alpha = 0.15f))
                  .border(1.dp, CyberEmerald, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Hub,
                  contentDescription = "Enjambre IA",
                  tint = CyberEmerald,
                  modifier = Modifier.size(26.dp)
                )
              }
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(
                  text = "ENJAMBRE IA SOBERANO & PRIVACIDAD",
                  color = CyberEmerald,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  letterSpacing = 1.sp
                )
                Text(
                  text = "6 Drones Cuánticos Activos 24/7",
                  color = CyberTextPrimary,
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Black
                )
              }
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(CyberEmerald.copy(alpha = 0.2f))
                .border(1.dp, CyberEmerald, RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Lock,
                  contentDescription = "Inviolable",
                  tint = CyberEmerald,
                  modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "INVIOLABLE",
                  color = CyberEmerald,
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Black
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = "Red Soberana de Alejandro Armas: Enjambre de 6 drones de IA coordinados por consenso post-cuántico. Bloqueo universal de todo virus, spyware, keyloggers y blindaje absoluto de privacidad sin fugas de datos.",
            color = CyberTextSecondary,
            fontSize = 11.sp,
            lineHeight = 16.sp
          )

          if (state.swarmPurgeMessage != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(CyberEmerald.copy(alpha = 0.15f))
                .border(1.dp, CyberEmerald, RoundedCornerShape(8.dp))
                .padding(10.dp)
            ) {
              Column {
                Text(
                  text = state.swarmPurgeMessage,
                  color = CyberEmerald,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold
                )
                if (state.isSwarmPurging) {
                  Spacer(modifier = Modifier.height(6.dp))
                  LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = CyberEmerald,
                    trackColor = ObsidianElevated
                  )
                }
              }
            }
          }
        }
      }
    }

    // Real-Time Notification WorkManager Status Strip
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("sentinel_workmanager_alert_strip"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(
          1.dp,
          if (state.systemTrayAlertsEnabled) ThreatCrimson.copy(alpha = 0.5f) else ObsidianBorder
        )
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 10.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = if (state.systemTrayAlertsEnabled) Icons.Default.NotificationsActive else Icons.Default.NotificationsOff,
              contentDescription = "Alertas Bandeja",
              tint = if (state.systemTrayAlertsEnabled) ThreatCrimson else CyberTextMuted,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = "ALERTAS WORKMANAGER: ${if (state.systemTrayAlertsEnabled) "BANDEJA ON" else "SILENCIADO"}",
                color = if (state.systemTrayAlertsEnabled) ThreatCrimson else CyberTextMuted,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "${state.totalAlertsDispatched} alertas emitidas en segundo plano",
                color = CyberTextSecondary,
                fontSize = 9.5.sp
              )
            }
          }

          Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Button(
              onClick = onTriggerTestNotificationAlert,
              modifier = Modifier
                .height(30.dp)
                .testTag("btn_sentinel_test_alert"),
              shape = RoundedCornerShape(6.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = ThreatCrimson,
                contentColor = Color.White
              ),
              contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp)
            ) {
              Text("Probar", fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }

            androidx.compose.material3.OutlinedButton(
              onClick = onToggleSystemTrayAlerts,
              modifier = Modifier
                .height(30.dp)
                .testTag("btn_sentinel_toggle_alerts"),
              shape = RoundedCornerShape(6.dp),
              border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder),
              colors = ButtonDefaults.outlinedButtonColors(
                contentColor = if (state.systemTrayAlertsEnabled) WarningGold else CyberEmerald
              ),
              contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp)
            ) {
              Text(
                text = if (state.systemTrayAlertsEnabled) "Pausar" else "Activar",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    }

    // Email Quantum Swarm Shield Master Card (Direct Link & Quick Actions)
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("sentinel_email_quantum_card"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(
          1.dp,
          Brush.horizontalGradient(listOf(CyberCyan, QuantumPurple))
        )
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
                  contentDescription = "Correo Enjambre",
                  tint = CyberCyan,
                  modifier = Modifier.size(20.dp)
                )
              }
              Column {
                Text(
                  text = "BLINDAJE CUÁNTICO DE CORREO ENJAMBRE",
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
              modifier = Modifier.testTag("sentinel_open_email_shield_btn")
            ) {
              Text("Abrir", fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
          }

          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "6 Micro-Drones de IA dedicados a la protección de cabeceras, micro-sandbox de detonación cero-day y anti-spoofing cuántico Dilithium-5.",
            fontSize = 10.sp,
            color = CyberTextSecondary
          )

          Spacer(modifier = Modifier.height(10.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            OutlinedButton(
              onClick = onRunEmailAudit,
              modifier = Modifier.weight(1f).height(32.dp),
              shape = RoundedCornerShape(6.dp),
              border = androidx.compose.foundation.BorderStroke(1.dp, CyberCyan),
              colors = ButtonDefaults.outlinedButtonColors(contentColor = CyberCyan),
              contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 6.dp)
            ) {
              Text("⚡ Auditar Correo", fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
              onClick = { onSimulateEmailThreat(EmailThreatType.SPEAR_PHISHING) },
              modifier = Modifier.weight(1f).height(32.dp),
              shape = RoundedCornerShape(6.dp),
              border = androidx.compose.foundation.BorderStroke(1.dp, QuantumPurple),
              colors = ButtonDefaults.outlinedButtonColors(contentColor = QuantumPurple),
              contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 6.dp)
            ) {
              Text("🎣 Probar Phishing", fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // Scrollable Tab Navigation
    item {
      ScrollableTabRow(
        selectedTabIndex = selectedTab,
        containerColor = ObsidianSurface,
        contentColor = CyberCyan,
        edgePadding = 0.dp,
        indicator = { tabPositions ->
          TabRowDefaults.SecondaryIndicator(
            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
            color = CyberCyan
          )
        }
      ) {
        val tabs = listOf(
          "🧠 Neuro-Defensa Cuántica IA (${state.quantumNeuroDefense.activeDigitalAntigens.size})",
          "Dossier Forense & Pruebas Legales (${state.legalEvidenceDossier.evidenceItems.size})",
          "Rastreo Enjambre & Links (${state.privacySwarmTracker.monitoredLinks.size})",
          "GPS & Paradero Soberano",
          "Auto-Evolución IA (${state.swarmEvolutionUpdates.size})",
          "Flujo de Tráfico (Recharts)",
          "Drones del Enjambre (${state.swarmAgents.size})",
          "Historial Room SQLite (${state.threatLogs.size})",
          "Privacidad (100%)",
          "Bloqueo Total Virus",
          "Amenazas (${state.recentThreats.size})",
          "🛡️ Blindaje Cuántico Correo (${state.emailQuantumShield.threatsNeutralized})"
        )
        tabs.forEachIndexed { index, title ->
          Tab(
            selected = selectedTab == index,
            onClick = { selectedTab = index },
            text = {
              Text(
                text = title,
                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                fontSize = 11.sp
              )
            }
          )
        }
      }
    }

    when (selectedTab) {
      0 -> {
        // TAB 0: NEURO-DEFENSA CUÁNTICA IA (ANTÍGENOS & INMUNIDAD ADAPTATIVA)
        item {
          QuantumNeuroDefenseView(
            neuroState = state.quantumNeuroDefense,
            onToggleNeuroDefense = onToggleNeuroDefense,
            onToggleMemoryMorphing = onToggleMemoryMorphing,
            onSynthesizeImmediateAntigen = onSynthesizeImmediateAntigen,
            onDeployHologram = onDeployHologram,
            onAnalyzeGemini = onAnalyzeGemini,
            onSetCustomApiKey = onSetCustomApiKey
          )
        }
      }

      1 -> {
        // TAB 1: DOSSIER FORENSE & PRUEBAS LEGALES PARA DENUNCIA FORMAL
        item {
          LegalForensicDossierView(
            dossierState = state.legalEvidenceDossier,
            gpsState = state.gpsLocationState,
            onFreezeEvidence = onFreezeLegalEvidence,
            getComplaintText = onGetComplaintText,
            onMarkExported = onMarkLegalDossierExported
          )
        }
      }

      2 -> {
        // TAB 2: ENJAMBRE IA RASTREADOR EN VIVO DE LINKS Y DISPOSITIVOS ESPÍA
        item {
          AiSwarmPrivacyTrackerView(
            state = state,
            onTriggerScan = onTriggerPrivacySwarmScan,
            onNeutralizeLink = onNeutralizeLeakedLink,
            onNeutralizeDevice = onNeutralizeRogueDevice,
            onToggleAutoNeutralize = onToggleAutoNeutralizeLeaks,
            onToggleFirewall = onToggleDataBrokerFirewall
          )
        }
      }

      3 -> {
        // TAB 3: GPS SOBERANO, UBICACIÓN EXACTA Y PARADERO DEL USUARIO
        item {
          GpsLocationTelemetryView(
            gpsState = state.gpsLocationState,
            onRefreshGps = onRefreshGpsLocation,
            onToggleCloak = onToggleLocationCloak,
            onBlockQuerySimulation = onSimulateGpsLeakBlock
          )
        }
      }

      4 -> {
        // TAB 3: AI SWARM AUTO-EVOLUTION & REAL-TIME SELF-UPDATING ENGINE
        item {
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .testTag("swarm_auto_evolution_banner"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
            border = androidx.compose.foundation.BorderStroke(
              1.5.dp,
              Brush.horizontalGradient(listOf(CyberEmerald, CyberCyan, QuantumPurple))
            )
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.RocketLaunch,
                    contentDescription = "Auto Evolución",
                    tint = CyberEmerald,
                    modifier = Modifier.size(22.dp)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = "AUTO-EVOLUCIÓN DEL ENJAMBRE IA",
                    color = CyberEmerald,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                  )
                }
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(CyberEmerald.copy(alpha = 0.15f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                  Text(
                    text = "AUTÓNOMO",
                    color = CyberEmerald,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }

              Spacer(modifier = Modifier.height(10.dp))
              Text(
                text = "Desarrollado por Alejandro Armas: El Enjambre de 6 drones no sólo neutraliza ataques; analiza continuamente vectores emergentes y genera en caliente nuevas capacidades y micro-parches de código que se activan inmediatamente en la app sin reinicios.",
                color = CyberTextSecondary,
                fontSize = 11.sp,
                lineHeight = 16.sp
              )

              Spacer(modifier = Modifier.height(14.dp))

              // Switch for 24/7 Autonomous Evolution
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(10.dp))
                  .background(ObsidianElevated)
                  .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = "Evolución Autónoma Continua 24/7",
                    color = CyberTextPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                  )
                  Text(
                    text = "La IA descubre y auto-aplica parches continuamente",
                    color = CyberTextMuted,
                    fontSize = 9.sp
                  )
                }
                Switch(
                  checked = state.swarmTelemetry.autoEvolutionEnabled,
                  onCheckedChange = { onToggleAutoEvolution() },
                  colors = SwitchDefaults.colors(
                    checkedThumbColor = CyberEmerald,
                    checkedTrackColor = CyberEmerald.copy(alpha = 0.3f),
                    uncheckedThumbColor = CyberTextMuted,
                    uncheckedTrackColor = ObsidianBorder
                  ),
                  modifier = Modifier.testTag("toggle_auto_evolution_switch")
                )
              }

              Spacer(modifier = Modifier.height(12.dp))

              // Trigger Discovery Button
              Button(
                onClick = onTriggerSwarmEvolution,
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("trigger_swarm_evolution_btn"),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CyberCyan),
                enabled = !state.isSwarmSynthesizing
              ) {
                Icon(
                  imageVector = if (state.isSwarmSynthesizing) Icons.Default.Sync else Icons.Default.AutoAwesome,
                  contentDescription = null,
                  tint = ObsidianBackground,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = if (state.isSwarmSynthesizing) "SINTETIZANDO & AUTO-COMPILANDO..." else "DISPARAR SÍNTESIS & DESCUBRIR NUEVA CAPACIDAD",
                  color = ObsidianBackground,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Black
                )
              }

              // Synthesis Progress Indicator if active
              AnimatedVisibility(visible = state.isSwarmSynthesizing) {
                Column(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                ) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                  ) {
                    Text(
                      text = state.swarmSynthesizingStage ?: "Sintetizando parches en caliente...",
                      color = CyberCyan,
                      fontSize = 10.sp,
                      fontWeight = FontWeight.SemiBold
                    )
                    Text(
                      text = "${(state.swarmSynthesizeProgress * 100).toInt()}%",
                      color = CyberEmerald,
                      fontSize = 10.sp,
                      fontFamily = FontFamily.Monospace,
                      fontWeight = FontWeight.Bold
                    )
                  }
                  Spacer(modifier = Modifier.height(6.dp))
                  LinearProgressIndicator(
                    progress = { state.swarmSynthesizeProgress },
                    modifier = Modifier
                      .fillMaxWidth()
                      .height(6.dp)
                      .clip(RoundedCornerShape(3.dp)),
                    color = CyberEmerald,
                    trackColor = ObsidianElevated
                  )
                }
              }
            }
          }
        }

        // Swarm Telemetry Metrics Bar
        item {
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              SwarmTelemetryPill(
                title = "Descubrimientos",
                value = "${state.swarmTelemetry.totalDiscoveries}",
                color = CyberEmerald,
                modifier = Modifier.weight(1f)
              )
              SwarmTelemetryPill(
                title = "Micro-Parches",
                value = "${state.swarmTelemetry.autoAppliedPatches}",
                color = CyberCyan,
                modifier = Modifier.weight(1f)
              )
              SwarmTelemetryPill(
                title = "Fallas Sanadas",
                value = "${state.swarmTelemetry.faultsSelfHealedCount}",
                color = WarningGold,
                modifier = Modifier.weight(1f)
              )
              SwarmTelemetryPill(
                title = "Salud Integridad",
                value = "${state.swarmTelemetry.healthIntegrityPercent}%",
                color = CyberEmerald,
                modifier = Modifier.weight(1f)
              )
            }
          }
        }

        // Swarm Self-Healing & Auto-Repair Card
        item {
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .testTag("swarm_auto_repair_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
            border = androidx.compose.foundation.BorderStroke(
              1.2.dp,
              Brush.horizontalGradient(listOf(WarningGold, CyberEmerald, CyberCyan))
            )
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.Healing,
                    contentDescription = "Auto Reparación",
                    tint = WarningGold,
                    modifier = Modifier.size(20.dp)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = "AUTO-REPARACIÓN & SANACIÓN DE FALLAS",
                    color = WarningGold,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                  )
                }
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(WarningGold.copy(alpha = 0.15f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                  Text(
                    text = if (state.swarmTelemetry.autoSelfRepairEnabled) "AUTO-HEAL ACTIVO" else "PAUSADO",
                    color = WarningGold,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }

              Spacer(modifier = Modifier.height(10.dp))
              Text(
                text = "El Enjambre IA inspecciona continuamente fugas de memoria, cuellos de botella en Room SQLite, anomalías en sockets y desincronizaciones para auto-modificar la app y parchar fallas en tiempo de ejecución sin reinicios.",
                color = CyberTextSecondary,
                fontSize = 11.sp,
                lineHeight = 16.sp
              )

              Spacer(modifier = Modifier.height(12.dp))

              // Switch for 24/7 Self Repair
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(10.dp))
                  .background(ObsidianElevated)
                  .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = "Auto-Corrección Continua de Fallas",
                    color = CyberTextPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                  )
                  Text(
                    text = "Detecta micro-anomalías y repara la app autónomamente",
                    color = CyberTextMuted,
                    fontSize = 9.sp
                  )
                }
                Switch(
                  checked = state.swarmTelemetry.autoSelfRepairEnabled,
                  onCheckedChange = { onToggleAutoSelfRepair() },
                  colors = SwitchDefaults.colors(
                    checkedThumbColor = WarningGold,
                    checkedTrackColor = WarningGold.copy(alpha = 0.3f),
                    uncheckedThumbColor = CyberTextMuted,
                    uncheckedTrackColor = ObsidianBorder
                  ),
                  modifier = Modifier.testTag("toggle_auto_repair_switch")
                )
              }

              Spacer(modifier = Modifier.height(12.dp))

              // Button to trigger immediate scan & self repair
              Button(
                onClick = onTriggerSelfRepair,
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("btn_trigger_self_repair"),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = WarningGold),
                enabled = !state.isSwarmAutoRepairing
              ) {
                Icon(
                  imageVector = if (state.isSwarmAutoRepairing) Icons.Default.Sync else Icons.Default.Healing,
                  contentDescription = null,
                  tint = ObsidianBackground,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = if (state.isSwarmAutoRepairing) "DIAGNOSTICANDO & AUTO-REPARANDO..." else "DIAGNOSTICAR & AUTO-REPARAR AHORA",
                  color = ObsidianBackground,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Black
                )
              }

              AnimatedVisibility(visible = state.isSwarmAutoRepairing) {
                Column(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                ) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                  ) {
                    Text(
                      text = "Dron Prometheus inyectando micro-parche en caliente...",
                      color = WarningGold,
                      fontSize = 10.sp,
                      fontWeight = FontWeight.SemiBold
                    )
                    Text(
                      text = "AUTO-FIX",
                      color = CyberEmerald,
                      fontSize = 10.sp,
                      fontFamily = FontFamily.Monospace,
                      fontWeight = FontWeight.Bold
                    )
                  }
                  Spacer(modifier = Modifier.height(6.dp))
                  LinearProgressIndicator(
                    modifier = Modifier
                      .fillMaxWidth()
                      .height(6.dp)
                      .clip(RoundedCornerShape(3.dp)),
                    color = WarningGold,
                    trackColor = ObsidianElevated
                  )
                }
              }
            }
          }
        }

        // Self-Repair Events Section
        item {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "FALLAS Y ANOMALÍAS AUTO-SANADAS (${state.swarmSelfRepairEvents.size})",
              color = WarningGold,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp
            )
            Text(
              text = "100% Sin Reiniciar",
              color = CyberEmerald,
              fontSize = 10.sp,
              fontWeight = FontWeight.SemiBold
            )
          }
        }

        items(state.swarmSelfRepairEvents) { event ->
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, WarningGold.copy(alpha = 0.4f))
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
                Row(
                  modifier = Modifier.weight(1f),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = CyberEmerald,
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = event.diagnosedIssue,
                    color = CyberTextPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(CyberEmerald.copy(alpha = 0.15f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                  Text(
                    text = "RESUELTO",
                    color = CyberEmerald,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black
                  )
                }
              }

              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "Subsistema: ${event.targetSubsystem} • ${event.timestamp}",
                color = CyberCyan,
                fontSize = 10.sp
              )

              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = event.resolutionDetails,
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
                  text = "Parche: ${event.patchName}",
                  color = WarningGold,
                  fontSize = 9.sp,
                  fontFamily = FontFamily.Monospace,
                  fontWeight = FontWeight.Bold
                )
                if (event.memoryFreedKb > 0) {
                  Text(
                    text = "RAM recuperada: ${event.memoryFreedKb / 1024} MB",
                    color = CyberEmerald,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
            }
          }
        }

        // Live App Updates Header
        item {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "NUEVAS CAPACIDADES DESCUBIERTAS POR LA IA (${state.swarmEvolutionUpdates.size})",
              color = CyberCyan,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp
            )
            Text(
              text = "Auto-Actualizado en Caliente",
              color = CyberEmerald,
              fontSize = 10.sp,
              fontWeight = FontWeight.SemiBold
            )
          }
        }

        // Discovered Updates Cards
        items(state.swarmEvolutionUpdates) { update ->
          SwarmEvolutionCard(
            update = update,
            onRollback = onRollbackSwarmUpdate,
            onActivate = onActivateSwarmUpdate
          )
        }

        // Realtime Console Logs Card
        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.Terminal,
                    contentDescription = "Terminal",
                    tint = CyberEmerald,
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "TERMINAL DE TELEMETRÍA DEL ENJAMBRE IA",
                    color = CyberEmerald,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                  )
                }
                Text(
                  text = "EN VIVO",
                  color = CyberEmerald,
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold,
                  fontFamily = FontFamily.Monospace
                )
              }
              Spacer(modifier = Modifier.height(10.dp))
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(8.dp))
                  .background(androidx.compose.ui.graphics.Color(0xFF070B10))
                  .border(0.5.dp, ObsidianBorder, RoundedCornerShape(8.dp))
                  .padding(10.dp)
              ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                  state.swarmEvolutionConsoleLogs.takeLast(8).forEach { log ->
                    Text(
                      text = log,
                      color = when {
                        log.contains("[DESCUBRIMIENTO]") || log.contains("[MICRO-PARCHE]") -> CyberEmerald
                        log.contains("[ADVERTENCIA]") -> WarningGold
                        log.contains("[ERROR]") || log.contains("[REVERTIDO]") -> ThreatCrimson
                        else -> CyberCyan
                      },
                      fontSize = 9.sp,
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

      5 -> {
        // TAB 5: REAL-TIME RECHARTS TRAFFIC FLOW DASHBOARD
        item {
          RealtimeTrafficDashboardView(
            trafficHistory = state.trafficHistory,
            blockedPackets = state.blockedPacketsFeed,
            summary = state.trafficSummary,
            onToggleLive = onToggleTrafficLive,
            onSelectFilter = onSelectTrafficFilter,
            onInjectBurst = onInjectTrafficBurst
          )
        }
      }

      6 -> {
        // TAB 6: AI SWARM DRONES
        item {
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
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "CONSENSO CUÁNTICO DEL ENJAMBRE",
                  color = CyberCyan,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  letterSpacing = 1.sp
                )
                Text(
                  text = "Latencia: 0.11 ms",
                  color = CyberEmerald,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  fontFamily = FontFamily.Monospace
                )
              }
              Spacer(modifier = Modifier.height(8.dp))
              Text(
                text = "Los 6 agentes operan en paralelo descentralizado, comunicándose por retículos cuánticos de cero latencia para anticipar ataques antes de que toquen el sistema.",
                color = CyberTextSecondary,
                fontSize = 11.sp,
                lineHeight = 15.sp
              )
              Spacer(modifier = Modifier.height(12.dp))

              Button(
                onClick = onTriggerSwarmPurge,
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("trigger_swarm_purge_button"),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CyberEmerald),
                enabled = !state.isSwarmPurging
              ) {
                Icon(
                  imageVector = Icons.Default.CleaningServices,
                  contentDescription = "Purgar",
                  tint = ObsidianBackground,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = if (state.isSwarmPurging) "EJECUTANDO PURGA DEL ENJAMBRE..." else "EJECUTAR PURGA TOTAL DEL ENJAMBRE IA",
                  color = ObsidianBackground,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Black
                )
              }
            }
          }
        }

        item {
          Text(
            text = "AGENTES DEFENSIVOS EN LÍNEA PERPETUA",
            color = CyberCyan,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
        }

        items(state.swarmAgents) { agent ->
          SwarmAgentCard(agent = agent)
        }
      }

      7 -> {
        // TAB 7: ROOM PERSISTENT THREAT LOGS (OFFLINE SQLITE DATABASE)
        item {
          RoomThreatHistoryScreen(
            threatLogs = state.threatLogs,
            onDeleteLog = onDeleteThreatLog,
            onClearAll = onClearAllThreatLogs,
            onSimulateTestThreat = onRecordCustomThreatToRoom
          )
        }
      }

      8 -> {
        // TAB 8: ABSOLUTE PRIVACY DEFENSE
        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, CyberEmerald)
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.VisibilityOff,
                    contentDescription = "Privacidad",
                    tint = CyberEmerald,
                    modifier = Modifier.size(20.dp)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = "ESCUDO DE PRIVACIDAD SOBERANA",
                    color = CyberEmerald,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black
                  )
                }
                Text(
                  text = "100% INTACTO",
                  color = CyberEmerald,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Black,
                  fontFamily = FontFamily.Monospace
                )
              }
              Spacer(modifier = Modifier.height(10.dp))
              Text(
                text = "Protegido por patente de Alejandro Armas: Toda comunicación y paquete de red está desprovisto de metadatos rastreables. Ningún proveedor de red, entidad externa ni spyware puede interceptar identidad o actividad.",
                color = CyberTextSecondary,
                fontSize = 11.sp,
                lineHeight = 16.sp
              )
              Spacer(modifier = Modifier.height(14.dp))

              Button(
                onClick = onBoostPrivacy,
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("boost_privacy_shield_button"),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CyberCyan)
              ) {
                Icon(
                  imageVector = Icons.Default.Shield,
                  contentDescription = "Reforzar",
                  tint = ObsidianBackground,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "REFORZAR ESCUDO DE PRIVACIDAD MÁXIMA",
                  color = ObsidianBackground,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Black
                )
              }
            }
          }
        }

        item {
          // Privacy Metrics Breakdown Grid
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              PrivacyMetricCard(
                title = "Índice de Privacidad",
                value = "${state.privacyMetrics.privacyIndex}%",
                subtitle = "Impenetrabilidad Total",
                color = CyberEmerald,
                modifier = Modifier.weight(1f)
              )
              PrivacyMetricCard(
                title = "Telemetría Ofuscada",
                value = "${state.privacyMetrics.telemetryCloaked}%",
                subtitle = "Zero-Leak Garantizado",
                color = CyberCyan,
                modifier = Modifier.weight(1f)
              )
            }
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              PrivacyMetricCard(
                title = "Spyware & Keyloggers",
                value = "${state.privacyMetrics.spywareBlockedCount}",
                subtitle = "Aislados y Destruidos",
                color = WarningGold,
                modifier = Modifier.weight(1f)
              )
              PrivacyMetricCard(
                title = "Rastreadores & Balizas",
                value = "${state.privacyMetrics.trackersAndBeaconsVaporized}",
                subtitle = "Pulverizados en Vivo",
                color = QuantumPurple,
                modifier = Modifier.weight(1f)
              )
            }
          }
        }

        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
            ) {
              Text(
                text = "PILARES DE PRIVACIDAD INVIOLABLE",
                color = CyberCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
              )
              Spacer(modifier = Modifier.height(8.dp))
              PrivacyPillarItem("1. Desarticulación de Spyware & Keyloggers", "Neutraliza Pegasus, AgentTesla y RedLine en memoria antes del registro de pulsaciones.")
              PrivacyPillarItem("2. Enmascaramiento Cuántico de Metadatos", "Firma paquetes con claves efímeras Kyber-1024 imposibles de correlacionar.")
              PrivacyPillarItem("3. Bloqueo Absoluto de DNS Tunneling", "Erradica cualquier canal encubierto que intente fugar información confidencial.")
              PrivacyPillarItem("4. Aislamiento Físico y Cero Telemetría Externa", "Cero envío de métricas de usuario a servidores de terceros o corporaciones.")
            }
          }
        }
      }

      9 -> {
        // TAB 9: UNIVERSAL VIRUS BLOCKER MATRIX & SIMULATION
        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, ThreatCrimson.copy(alpha = 0.6f))
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
                  text = "BANCO DE PRUEBAS DEL ENJAMBRE IA",
                  color = ThreatCrimson,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  letterSpacing = 1.sp
                )
                if (state.isSimulatingAttack) {
                  Text(
                    text = "DESMANTELANDO CEPAS...",
                    color = WarningGold,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = "Inyecta cepas de virus simuladas para verificar cómo los drones del Enjambre IA de Alejandro Armas las desarticulan en microsegundos:",
                color = CyberTextSecondary,
                fontSize = 11.sp
              )

              Spacer(modifier = Modifier.height(10.dp))

              Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                  VirusAttackButton(
                    title = "Spyware / Pegasus",
                    icon = Icons.Default.VisibilityOff,
                    onClick = { onSimulateVirusWave("spyware") },
                    modifier = Modifier.weight(1f),
                    enabled = !state.isSimulatingAttack
                  )
                  VirusAttackButton(
                    title = "Ransomware C2",
                    icon = Icons.Default.Coronavirus,
                    onClick = { onSimulateVirusWave("ransomware") },
                    modifier = Modifier.weight(1f),
                    enabled = !state.isSimulatingAttack
                  )
                }
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                  VirusAttackButton(
                    title = "Wiper Destructor",
                    icon = Icons.Default.BugReport,
                    onClick = { onSimulateVirusWave("wiper") },
                    modifier = Modifier.weight(1f),
                    enabled = !state.isSimulatingAttack
                  )
                  VirusAttackButton(
                    title = "Criptominero XMR",
                    icon = Icons.Default.Bolt,
                    onClick = { onSimulateVirusWave("cryptominer") },
                    modifier = Modifier.weight(1f),
                    enabled = !state.isSimulatingAttack
                  )
                }

                Button(
                  onClick = { onSimulateVirusWave("surge") },
                  modifier = Modifier
                    .fillMaxWidth()
                    .testTag("simulate_multivirus_surge_button"),
                  shape = RoundedCornerShape(8.dp),
                  colors = ButtonDefaults.buttonColors(containerColor = ThreatCrimson),
                  enabled = !state.isSimulatingAttack
                ) {
                  Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = "Asalto",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = "ASALTO MASIVO MULTI-VECTOR (5 CEPAS)",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black
                  )
                }
              }
            }
          }
        }

        item {
          Text(
            text = "MATRIZ UNIVERSAL DE VIRUS BLOQUEADOS (100% EFECTIVIDAD)",
            color = CyberCyan,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
        }

        items(state.virusCategories) { cat ->
          VirusCategoryCard(cat = cat)
        }
      }

      10 -> {
        // TAB 10: THREATS & QUARANTINE LOGS (IN-MEMORY SESSION)
        if (state.recentThreats.isEmpty()) {
          item {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
              contentAlignment = Alignment.Center
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = "Limpio",
                  tint = CyberEmerald,
                  modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                  text = "Cero virus activos en la red",
                  color = CyberTextSecondary,
                  fontSize = 13.sp
                )
              }
            }
          }
        } else {
          items(state.recentThreats) { incident ->
            ThreatIncidentCard(incident = incident)
          }
        }
      }

      11 -> {
        // TAB 11: BLINDAJE CUÁNTICO DE CORREO ELECTRÓNICO (ENJAMBRE IA)
        item {
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .testTag("tab_email_quantum_shield_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, CyberCyan.copy(alpha = 0.5f))
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Text(
                text = "ENJAMBRE IA DEDICADO A SEGURIDAD DE CORREO",
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                color = CyberCyan
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "Protección soberana de ${state.emailQuantumShield.protectedEmail} con criptografía post-cuántica y consenso distribuido.",
                fontSize = 11.sp,
                color = CyberTextSecondary
              )

              Spacer(modifier = Modifier.height(12.dp))
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                Button(
                  onClick = onNavigateToEmailShield,
                  colors = ButtonDefaults.buttonColors(containerColor = CyberCyan, contentColor = ObsidianBackground),
                  shape = RoundedCornerShape(8.dp),
                  modifier = Modifier.weight(1f)
                ) {
                  Text("GESTIONAR CORREO COMPLETO", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
                OutlinedButton(
                  onClick = onRunEmailAudit,
                  border = androidx.compose.foundation.BorderStroke(1.dp, CyberEmerald),
                  colors = ButtonDefaults.outlinedButtonColors(contentColor = CyberEmerald),
                  shape = RoundedCornerShape(8.dp),
                  modifier = Modifier.weight(1f)
                ) {
                  Text("AUDITORÍA CUÁNTICA", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
              }

              Spacer(modifier = Modifier.height(14.dp))
              Text(
                text = "DRONES DEL ENJAMBRE ASIGNADOS A CORREO (${state.emailQuantumShield.emailSwarmDrones.size}):",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = CyberTextMuted
              )
              Spacer(modifier = Modifier.height(6.dp))
              state.emailQuantumShield.emailSwarmDrones.forEach { drone ->
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(ObsidianElevated)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Column {
                    Text(
                      text = drone.codeName,
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Bold,
                      fontFamily = FontFamily.Monospace,
                      color = CyberCyan
                    )
                    Text(
                      text = "${drone.role} • ${drone.specialty}",
                      fontSize = 9.sp,
                      color = CyberTextSecondary
                    )
                  }
                  Text(
                    text = "${drone.threatsIntercepted} bloqueos",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = CyberEmerald
                  )
                }
              }
            }
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
fun SwarmAgentCard(agent: AiSwarmAgent) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = androidx.compose.foundation.BorderStroke(1.dp, CyberEmerald.copy(alpha = 0.35f))
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
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(8.dp)
              .clip(CircleShape)
              .background(CyberEmerald)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = agent.codename,
            color = CyberTextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(CyberEmerald.copy(alpha = 0.15f))
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(
            text = agent.status,
            color = CyberEmerald,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = agent.role,
        color = CyberCyan,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = agent.specialty,
        color = CyberTextMuted,
        fontSize = 10.sp,
        lineHeight = 14.sp
      )

      Spacer(modifier = Modifier.height(8.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = "Amenazas Vaporizadas: ${agent.threatsVaporized}",
          color = CyberTextSecondary,
          fontSize = 10.sp,
          fontWeight = FontWeight.Medium
        )
        Text(
          text = "Latencia Consenso: ${agent.consensusLatencyMs} ms",
          color = CyberEmerald,
          fontSize = 10.sp,
          fontFamily = FontFamily.Monospace
        )
      }
    }
  }
}

@Composable
fun PrivacyMetricCard(
  title: String,
  value: String,
  subtitle: String,
  color: Color,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianElevated),
    border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.4f))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
    ) {
      Text(
        text = title,
        color = CyberTextMuted,
        fontSize = 10.sp,
        fontWeight = FontWeight.SemiBold
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = value,
        color = color,
        fontSize = 18.sp,
        fontWeight = FontWeight.Black,
        fontFamily = FontFamily.Monospace
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = subtitle,
        color = CyberTextSecondary,
        fontSize = 9.sp
      )
    }
  }
}

@Composable
fun PrivacyPillarItem(title: String, description: String) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp)
  ) {
    Text(
      text = title,
      color = CyberTextPrimary,
      fontSize = 11.sp,
      fontWeight = FontWeight.Bold
    )
    Text(
      text = description,
      color = CyberTextMuted,
      fontSize = 10.sp,
      lineHeight = 14.sp
    )
  }
}

@Composable
fun VirusCategoryCard(cat: VirusCategoryBlockStatus) {
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
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = cat.categoryName,
          color = CyberTextPrimary,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold
        )
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(CyberEmerald.copy(alpha = 0.15f))
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(
            text = "BLOQUEADO 100%",
            color = CyberEmerald,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = "Cepas neutralizadas: ${cat.virusExamples}",
        color = CyberTextMuted,
        fontSize = 10.sp
      )

      Spacer(modifier = Modifier.height(6.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = "Total Bloqueados: ${cat.totalBlocked}",
          color = CyberTextSecondary,
          fontSize = 10.sp
        )
        Text(
          text = "Tiempo Respuesta: ${cat.responseTimeMicroseconds}",
          color = CyberCyan,
          fontSize = 10.sp,
          fontFamily = FontFamily.Monospace
        )
      }
    }
  }
}

@Composable
fun VirusAttackButton(
  title: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true
) {
  Button(
    onClick = onClick,
    modifier = modifier,
    shape = RoundedCornerShape(8.dp),
    colors = ButtonDefaults.buttonColors(containerColor = ObsidianElevated),
    border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder),
    enabled = enabled
  ) {
    Icon(
      imageVector = icon,
      contentDescription = title,
      tint = CyberCyan,
      modifier = Modifier.size(14.dp)
    )
    Spacer(modifier = Modifier.width(6.dp))
    Text(
      text = title,
      color = CyberTextPrimary,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      maxLines = 1
    )
  }
}

@Composable
fun SwarmTelemetryPill(
  title: String,
  value: String,
  color: androidx.compose.ui.graphics.Color,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(10.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 6.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = title,
        color = CyberTextMuted,
        fontSize = 9.sp,
        maxLines = 1
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = value,
        color = color,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        maxLines = 1
      )
    }
  }
}

@Composable
fun SwarmEvolutionCard(
  update: AiSwarmEvolutionUpdate,
  onRollback: (String) -> Unit,
  onActivate: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  val isActive = update.status == SwarmUpdateStatus.ACTIVE
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      if (isActive) CyberEmerald.copy(alpha = 0.5f) else WarningGold.copy(alpha = 0.5f)
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
          Icon(
            imageVector = if (isActive) Icons.Default.AutoAwesome else Icons.Default.Tune,
            contentDescription = null,
            tint = if (isActive) CyberEmerald else WarningGold,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = update.title,
            color = CyberTextPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
          )
        }
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (isActive) CyberEmerald.copy(alpha = 0.15f) else WarningGold.copy(alpha = 0.15f))
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Text(
            text = if (isActive) "ACTIVO EN CALIENTE" else "PAUSADO / STANDBY",
            color = if (isActive) CyberEmerald else WarningGold,
            fontSize = 9.sp,
            fontWeight = FontWeight.Black
          )
        }
      }

      Spacer(modifier = Modifier.height(6.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = "Descubierto por: ${update.discoveredByDrone}",
          color = CyberCyan,
          fontSize = 10.sp,
          fontWeight = FontWeight.Medium
        )
        Text(
          text = update.discoveryTimestamp,
          color = CyberTextMuted,
          fontSize = 10.sp,
          fontFamily = FontFamily.Monospace
        )
      }

      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = update.description,
        color = CyberTextSecondary,
        fontSize = 11.sp,
        lineHeight = 16.sp
      )

      Spacer(modifier = Modifier.height(8.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = "Impacto: ${update.operationalImpact}",
          color = CyberEmerald,
          fontSize = 10.sp,
          fontWeight = FontWeight.SemiBold
        )
        Text(
          text = "Eficiencia: ${update.efficiencyGain}",
          color = WarningGold,
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold
        )
      }

      Spacer(modifier = Modifier.height(8.dp))
      // Cryptographic diff code snippet
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(8.dp))
          .background(ObsidianElevated)
          .border(0.5.dp, ObsidianBorder, RoundedCornerShape(8.dp))
          .padding(8.dp)
      ) {
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "INYECCIÓN DE CÓDIGO AUTÓNOMO",
              color = CyberTextMuted,
              fontSize = 8.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp
            )
            Text(
              text = update.evolutionVersion,
              color = CyberCyan,
              fontSize = 8.sp,
              fontFamily = FontFamily.Monospace
            )
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = update.codeDiffSummary,
            color = CyberEmerald,
            fontSize = 9.sp,
            fontFamily = FontFamily.Monospace,
            lineHeight = 13.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))
      // Action button
      if (isActive) {
        OutlinedButton(
          onClick = { onRollback(update.id) },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("rollback_update_${update.id}"),
          shape = RoundedCornerShape(8.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, ThreatCrimson.copy(alpha = 0.7f))
        ) {
          Icon(
            imageVector = Icons.Default.History,
            contentDescription = "Rollback",
            tint = ThreatCrimson,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "REVERTIR / PAUSAR ESTA CAPACIDAD",
            color = ThreatCrimson,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
          )
        }
      } else {
        Button(
          onClick = { onActivate(update.id) },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("activate_update_${update.id}"),
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(containerColor = CyberEmerald)
        ) {
          Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = "Activar",
            tint = ObsidianBackground,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "RE-INYECTAR Y ACTIVAR EN CALIENTE",
            color = ObsidianBackground,
            fontSize = 10.sp,
            fontWeight = FontWeight.Black
          )
        }
      }
    }
  }
}

