package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EnhancedEncryption
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.model.ArmasQuantumEmailState
import com.example.model.EmailAuditItem
import com.example.model.EmailSwarmDrone
import com.example.model.EmailThreatIncident
import com.example.model.EmailThreatType
import com.example.model.GhostEmailAlias
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
fun EmailQuantumShieldScreen(
  state: ArmasQuantumEmailState,
  onSetProtectedEmail: (String) -> Unit,
  onToggleShield: () -> Unit,
  onRunAudit: () -> Unit,
  onSimulateThreat: (EmailThreatType) -> Unit,
  onGenerateSeal: (String, String) -> Unit,
  onClearSeal: () -> Unit,
  onToggleRule: (String) -> Unit,
  onAddSecondaryEmail: (String) -> Unit,
  onRemoveSecondaryEmail: (String) -> Unit,
  onClearThreats: () -> Unit,
  onGenerateGhostAlias: () -> Unit = {},
  onDestroyGhostAlias: (String) -> Unit = {},
  onAnalyzeSandboxAttachment: (String) -> Unit = {},
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  var showEditEmailDialog by remember { mutableStateOf(false) }
  var showAddSecondaryDialog by remember { mutableStateOf(false) }
  var showSealDialog by remember { mutableStateOf(false) }

  var emailInput by remember { mutableStateOf(state.protectedEmail) }
  var secondaryEmailInput by remember { mutableStateOf("") }
  var sealSubjectInput by remember { mutableStateOf("Comunicado Oficial Criptográfico") }
  var sealSnippetInput by remember { mutableStateOf("Documento e instrucciones firmadas bajo protocolo post-cuántico soberano.") }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianBackground)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(8.dp))
      // Master Quantum Header Card
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("email_quantum_header_card"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = BorderStroke(
          1.5.dp,
          Brush.horizontalGradient(listOf(CyberCyan, QuantumPurple, CyberEmerald))
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
                  .background(CyberCyan.copy(alpha = 0.15f))
                  .border(1.dp, CyberCyan, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.MarkEmailRead,
                  contentDescription = "Escudo Cuántico de Correo",
                  tint = CyberCyan,
                  modifier = Modifier.size(26.dp)
                )
              }
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(
                  text = "BLINDAJE CUÁNTICO DE CORREO",
                  color = CyberCyan,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Black,
                  letterSpacing = 1.sp
                )
                Text(
                  text = "Enjambre de IA Soberano • ${state.holderName}",
                  color = CyberTextSecondary,
                  fontSize = 11.sp
                )
              }
            }

            Switch(
              checked = state.isSwarmShieldActive,
              onCheckedChange = { onToggleShield() },
              modifier = Modifier.testTag("toggle_email_shield_switch"),
              colors = SwitchDefaults.colors(
                checkedThumbColor = CyberEmerald,
                checkedTrackColor = CyberEmerald.copy(alpha = 0.3f),
                uncheckedThumbColor = CyberTextMuted,
                uncheckedTrackColor = ObsidianElevated
              )
            )
          }

          Spacer(modifier = Modifier.height(12.dp))
          Text(
            text = "Protección de grado militar post-cuántica (NIST Kyber-1024 y Dilithium-5) contra spear-phishing de última generación, spoofing de remitente, malware en adjuntos detonados en micro-sandbox y fuga de credenciales.",
            color = CyberTextSecondary,
            fontSize = 11.sp,
            lineHeight = 16.sp
          )

          Spacer(modifier = Modifier.height(10.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(CyberEmerald.copy(alpha = 0.12f))
                .border(1.dp, CyberEmerald.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Text(
                text = if (state.isSwarmShieldActive) "🛡️ ESCUDO ACTIVO (100%)" else "⚠️ ESCUDO EN PAUSA",
                color = if (state.isSwarmShieldActive) CyberEmerald else ThreatCrimson,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
              )
            }
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(QuantumPurple.copy(alpha = 0.12f))
                .border(1.dp, QuantumPurple.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Text(
                text = "🔐 ML-KEM-1024 + ML-DSA",
                color = QuantumPurple,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
              )
            }
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(CyberCyan.copy(alpha = 0.12f))
                .border(1.dp, CyberCyan.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Text(
                text = "⚡ ENJAMBRE: 6 NODOS IA",
                color = CyberCyan,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    }

    // Active Protected Email Card with Edit & Audit Actions
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("active_protected_email_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = BorderStroke(1.dp, ObsidianBorder)
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
                imageVector = Icons.Default.AlternateEmail,
                contentDescription = "Correo Protegido",
                tint = CyberEmerald,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "CORREO PRINCIPAL PROTEGIDO",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = CyberTextMuted
              )
            }
            IconButton(
              onClick = {
                emailInput = state.protectedEmail
                showEditEmailDialog = true
              },
              modifier = Modifier
                .size(28.dp)
                .testTag("edit_protected_email_btn")
            ) {
              Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Modificar Correo",
                tint = CyberCyan,
                modifier = Modifier.size(16.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(6.dp))
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(10.dp))
              .background(ObsidianBackground)
              .border(1.dp, CyberEmerald.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
              .padding(horizontal = 14.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = state.protectedEmail,
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                color = CyberTextPrimary
              )
              Text(
                text = "ID Llave Cuántica: ${state.quantumSignatureKeyId}",
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                color = CyberCyan
              )
            }
            Box(
              modifier = Modifier
                .clip(CircleShape)
                .background(CyberEmerald.copy(alpha = 0.2f))
                .border(1.dp, CyberEmerald, CircleShape)
                .padding(6.dp)
            ) {
              Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Blindado",
                tint = CyberEmerald,
                modifier = Modifier.size(16.dp)
              )
            }
          }

          if (state.secondaryEmails.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
              text = "Alias / Correos Secundarios Protegidos (${state.secondaryEmails.size}):",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = CyberTextMuted
            )
            Spacer(modifier = Modifier.height(4.dp))
            state.secondaryEmails.forEach { secEmail ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 2.dp)
                  .clip(RoundedCornerShape(6.dp))
                  .background(ObsidianElevated)
                  .padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "• $secEmail",
                  fontSize = 11.sp,
                  fontFamily = FontFamily.Monospace,
                  color = CyberTextSecondary
                )
                Icon(
                  imageVector = Icons.Default.Close,
                  contentDescription = "Eliminar",
                  tint = ThreatCrimson,
                  modifier = Modifier
                    .size(14.dp)
                    .clickable { onRemoveSecondaryEmail(secEmail) }
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
              onClick = { onRunAudit() },
              enabled = !state.isDeepAuditing,
              modifier = Modifier
                .weight(1f)
                .testTag("run_email_audit_btn"),
              colors = ButtonDefaults.buttonColors(containerColor = CyberCyan.copy(alpha = 0.15f)),
              border = BorderStroke(1.dp, CyberCyan),
              shape = RoundedCornerShape(8.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Auditar",
                tint = CyberCyan,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = if (state.isDeepAuditing) "AUDITANDO..." else "AUDITORÍA CUÁNTICA",
                color = CyberCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }

            OutlinedButton(
              onClick = { showAddSecondaryDialog = true },
              modifier = Modifier
                .weight(1f)
                .testTag("add_secondary_email_btn"),
              colors = ButtonDefaults.outlinedButtonColors(contentColor = QuantumPurple),
              border = BorderStroke(1.dp, QuantumPurple),
              shape = RoundedCornerShape(8.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar",
                tint = QuantumPurple,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "AGREGAR CORREO",
                color = QuantumPurple,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          AnimatedVisibility(visible = state.isDeepAuditing) {
            Column(modifier = Modifier.padding(top = 10.dp)) {
              Text(
                text = state.auditStatusMessage ?: "Sincronizando enjambre...",
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                color = CyberCyan
              )
              Spacer(modifier = Modifier.height(4.dp))
              LinearProgressIndicator(
                progress = { state.auditProgress },
                modifier = Modifier.fillMaxWidth(),
                color = CyberCyan,
                trackColor = ObsidianElevated
              )
            }
          }
        }
      }
    }

    // Interactive Threat Attack Simulator Banner (Test Swarm Response)
    if (state.simulatedAttackLog != null) {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(ThreatCrimson.copy(alpha = 0.15f))
            .border(1.dp, ThreatCrimson, RoundedCornerShape(12.dp))
            .padding(12.dp)
            .testTag("simulated_attack_alert_box")
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Shield,
              contentDescription = "Interceptado",
              tint = ThreatCrimson,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = state.simulatedAttackLog,
              color = ThreatCrimson,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }

    // Alias Fantasma Efímeros (Escudos Anti-Rastreo)
    item {
      GhostEmailAliasesCard(
        aliases = state.ghostAliases,
        onGenerate = onGenerateGhostAlias,
        onDestroy = onDestroyGhostAlias
      )
    }

    // Micro-Sandbox Cuántico de Detonación de Adjuntos
    item {
      QuantumSandboxDetonationCard(
        state = state,
        onAnalyze = onAnalyzeSandboxAttachment
      )
    }

    // Telemetry & KPI Counters
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        EmailKpiCard(
          title = "CORREOS INSPECCIONADOS",
          value = "${state.totalEmailsInspected}",
          subtitle = "Zero-Latency Filter",
          color = CyberCyan,
          modifier = Modifier.weight(1f)
        )
        EmailKpiCard(
          title = "AMENAZAS NEUTRALIZADAS",
          value = "${state.threatsNeutralized}",
          subtitle = "100% Tasa Éxito",
          color = CyberEmerald,
          modifier = Modifier.weight(1f)
        )
        EmailKpiCard(
          title = "PÍXELES ESPÍA PURGADOS",
          value = "${state.spyPixelsPurged}",
          subtitle = "Telemetría Destruida",
          color = QuantumPurple,
          modifier = Modifier.weight(1f)
        )
      }
    }

    // Section: AI Swarm Drones for Email
    item {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Hub,
              contentDescription = "Enjambre",
              tint = CyberCyan,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "ENJAMBRE IA DE SEGURIDAD DE CORREO (${state.emailSwarmDrones.size} NODOS)",
              fontSize = 12.sp,
              fontWeight = FontWeight.Black,
              letterSpacing = 0.5.sp,
              color = CyberTextPrimary
            )
          }
          Text(
            text = "Latencia: ${state.swarmConsensusLatencyMs}ms",
            fontSize = 10.sp,
            color = CyberEmerald,
            fontFamily = FontFamily.Monospace
          )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = "Red distribuida de micro-agentes que inspeccionan cabeceras, ejecutan sandboxes efímeros y barren bases de datos clandestinas.",
          fontSize = 10.sp,
          color = CyberTextSecondary
        )
      }
    }

    item {
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        items(state.emailSwarmDrones) { drone ->
          EmailSwarmDroneCard(drone = drone)
        }
      }
    }

    // Section: Threat Attack Simulation Bench (Test AI Swarm in Live Combat)
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("email_threat_simulator_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = BorderStroke(1.dp, ObsidianBorder)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.BugReport,
              contentDescription = "Simulador",
              tint = WarningGold,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "BANCO DE PRUEBAS: SIMULAR ATAQUES CONTRA EL ENJAMBRE",
              fontSize = 11.sp,
              fontWeight = FontWeight.Black,
              color = WarningGold
            )
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Lanza vectores de ataque reales para comprobar la intercepción autónoma del enjambre sobre ${state.protectedEmail}:",
            fontSize = 10.sp,
            color = CyberTextSecondary
          )

          Spacer(modifier = Modifier.height(10.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            SimulationButton(
              label = "🎣 Spear-Phishing",
              onClick = { onSimulateThreat(EmailThreatType.SPEAR_PHISHING) },
              modifier = Modifier.weight(1f)
            )
            SimulationButton(
              label = "🔐 Robo Credenciales",
              onClick = { onSimulateThreat(EmailThreatType.CREDENTIAL_HARVESTER) },
              modifier = Modifier.weight(1f)
            )
          }
          Spacer(modifier = Modifier.height(6.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            SimulationButton(
              label = "💣 Adjunto Malware",
              onClick = { onSimulateThreat(EmailThreatType.MALWARE_ATTACHMENT) },
              modifier = Modifier.weight(1f)
            )
            SimulationButton(
              label = "👤 Suplantación CEO",
              onClick = { onSimulateThreat(EmailThreatType.CEO_FRAUD_SPOOFING) },
              modifier = Modifier.weight(1f)
            )
          }
          Spacer(modifier = Modifier.height(6.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            SimulationButton(
              label = "👁️ Píxel Espía",
              onClick = { onSimulateThreat(EmailThreatType.SPY_TRACKER) },
              modifier = Modifier.weight(1f)
            )
            SimulationButton(
              label = "🔗 Enlace Ofuscado",
              onClick = { onSimulateThreat(EmailThreatType.SUSPICIOUS_REDIRECT) },
              modifier = Modifier.weight(1f)
            )
          }
        }
      }
    }

    // Section: Quantum Digital Signature Seal Generator (Sello Cuántico Armas)
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("quantum_signature_seal_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = BorderStroke(1.dp, QuantumPurple.copy(alpha = 0.5f))
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
                imageVector = Icons.Default.VpnKey,
                contentDescription = "Sello Cuántico",
                tint = QuantumPurple,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "SELLO DIGITAL POST-CUÁNTICO (NIST DILITHIUM-5)",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = QuantumPurple
              )
            }
            Text(
              text = "SHA3-512",
              fontSize = 9.sp,
              fontFamily = FontFamily.Monospace,
              color = CyberTextMuted
            )
          }

          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "Genera un sello criptográfico para adjuntar a correos salientes desde ${state.protectedEmail}. Garantiza al destinatario autenticidad absoluta, impidiendo suplantaciones y alteraciones.",
            fontSize = 10.sp,
            color = CyberTextSecondary
          )

          Spacer(modifier = Modifier.height(10.dp))
          Button(
            onClick = { showSealDialog = true },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("generate_quantum_seal_btn"),
            colors = ButtonDefaults.buttonColors(containerColor = QuantumPurple.copy(alpha = 0.2f)),
            border = BorderStroke(1.dp, QuantumPurple),
            shape = RoundedCornerShape(8.dp)
          ) {
            Icon(
              imageVector = Icons.Default.EnhancedEncryption,
              contentDescription = "Firmar",
              tint = QuantumPurple,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "GENERAR SELLO CRIPTOGRÁFICO PARA CORREO",
              color = QuantumPurple,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
          }

          if (state.generatedQuantumSealText != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(ObsidianBackground)
                .border(1.dp, QuantumPurple.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                .padding(10.dp)
            ) {
              Column {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = "SELLO GENERADO LISTO PARA COPIAR:",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = CyberEmerald
                  )
                  Row {
                    IconButton(
                      onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        clipboard.setPrimaryClip(ClipData.newPlainText("Armas Quantum Seal", state.generatedQuantumSealText))
                        Toast.makeText(context, "Sello copiado al portapapeles", Toast.LENGTH_SHORT).show()
                      },
                      modifier = Modifier.size(24.dp)
                    ) {
                      Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copiar",
                        tint = CyberCyan,
                        modifier = Modifier.size(14.dp)
                      )
                    }
                    IconButton(
                      onClick = { onClearSeal() },
                      modifier = Modifier.size(24.dp)
                    ) {
                      Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar",
                        tint = ThreatCrimson,
                        modifier = Modifier.size(14.dp)
                      )
                    }
                  }
                }
                Text(
                  text = state.generatedQuantumSealText,
                  fontSize = 9.sp,
                  fontFamily = FontFamily.Monospace,
                  color = CyberTextPrimary,
                  lineHeight = 13.sp
                )
              }
            }
          }
        }
      }
    }

    // Section: Autonomous Swarm Defense Rules
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("email_rules_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = BorderStroke(1.dp, ObsidianBorder)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          Text(
            text = "REGLAS PROACTIVAS DEL ENJAMBRE IA",
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            color = CyberCyan
          )
          Spacer(modifier = Modifier.height(10.dp))

          EmailRuleToggle(
            title = "Purga Automática de Píxeles Espía (Spy Pixels)",
            description = "Elimina balizas transparentes y rastreadores de lectura sin alterar el diseño del correo.",
            checked = state.autoPurgeSpyPixels,
            onCheckedChange = { onToggleRule("spy_pixels") }
          )
          EmailRuleToggle(
            title = "Micro-Sandbox Cuántico para Adjuntos Cero-Day",
            description = "Detona archivos .pdf, .zip, .exe o macros Office en contenedor aislado antes de descargar.",
            checked = state.autoDetonateSandbox,
            onCheckedChange = { onToggleRule("sandbox") }
          )
          EmailRuleToggle(
            title = "Anti-Spoofing Estricto DMARC/DKIM Cuántico",
            description = "Bloquea correos fraudulentos que intenten usar el nombre o dominio de Alejandro Armas.",
            checked = state.enforceAntiSpoofingDmarc,
            onCheckedChange = { onToggleRule("dmarc") }
          )
          EmailRuleToggle(
            title = "Bóveda Zero-Knowledge para Cifrado de Mensajes",
            description = "Aplica cifrado post-cuántico local sin almacenar llaves en servidores de terceros.",
            checked = state.zeroKnowledgeVaultEnabled,
            onCheckedChange = { onToggleRule("zero_knowledge") }
          )
        }
      }
    }

    // Section: Audit Checklist
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("email_audit_checklist_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = BorderStroke(1.dp, ObsidianBorder)
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
            Text(
              text = "MATRIZ DE AUDITORÍA POST-CUÁNTICA",
              fontSize = 11.sp,
              fontWeight = FontWeight.Black,
              color = CyberEmerald
            )
            Text(
              text = state.lastAuditTimestamp,
              fontSize = 9.sp,
              color = CyberTextMuted
            )
          }
          Spacer(modifier = Modifier.height(10.dp))

          state.auditChecklist.forEach { audit ->
            EmailAuditRow(item = audit)
            Spacer(modifier = Modifier.height(6.dp))
          }
        }
      }
    }

    // Section: Feed of Neutralized Email Threats
    item {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Shield,
              contentDescription = "Amenazas",
              tint = ThreatCrimson,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "REGISTRO DE AMENAZAS NEUTRALIZADAS (${state.threatIncidents.size})",
              fontSize = 12.sp,
              fontWeight = FontWeight.Black,
              color = CyberTextPrimary
            )
          }

          if (state.threatIncidents.isNotEmpty()) {
            IconButton(
              onClick = { onClearThreats() },
              modifier = Modifier.size(24.dp)
            ) {
              Icon(
                imageVector = Icons.Default.DeleteSweep,
                contentDescription = "Limpiar Registro",
                tint = CyberTextMuted,
                modifier = Modifier.size(16.dp)
              )
            }
          }
        }
      }
    }

    if (state.threatIncidents.isEmpty()) {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(ObsidianSurface)
            .padding(24.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "Sin amenazas pendientes. El Enjambre IA mantiene el correo ${state.protectedEmail} 100% limpio.",
            color = CyberTextMuted,
            fontSize = 11.sp
          )
        }
      }
    } else {
      items(state.threatIncidents) { incident ->
        EmailThreatIncidentCard(incident = incident)
      }
    }

    item {
      Spacer(modifier = Modifier.height(24.dp))
    }
  }

  // Dialog to change main protected email
  if (showEditEmailDialog) {
    AlertDialog(
      onDismissRequest = { showEditEmailDialog = false },
      title = {
        Text(
          text = "Proteger Nuevo Correo Electrónico",
          color = CyberCyan,
          fontWeight = FontWeight.Bold,
          fontSize = 14.sp
        )
      },
      text = {
        Column {
          Text(
            text = "Ingresa la dirección de correo que deseas blindar con el Enjambre Cuántico IA. Se recalcularán las llaves Kyber-1024 y Dilithium-5 de inmediato.",
            color = CyberTextSecondary,
            fontSize = 11.sp
          )
          Spacer(modifier = Modifier.height(10.dp))
          OutlinedTextField(
            value = emailInput,
            onValueChange = { emailInput = it },
            label = { Text("Correo Electrónico") },
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("edit_email_input_field"),
            colors = OutlinedTextFieldDefaults.colors(
              focusedTextColor = CyberTextPrimary,
              unfocusedTextColor = CyberTextPrimary,
              focusedBorderColor = CyberCyan,
              unfocusedBorderColor = ObsidianBorder
            )
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (emailInput.isNotBlank()) {
              onSetProtectedEmail(emailInput)
              showEditEmailDialog = false
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = CyberCyan),
          shape = RoundedCornerShape(8.dp)
        ) {
          Text("PROTEGER", color = ObsidianBackground, fontWeight = FontWeight.Bold)
        }
      },
      dismissButton = {
        OutlinedButton(
          onClick = { showEditEmailDialog = false },
          shape = RoundedCornerShape(8.dp)
        ) {
          Text("CANCELAR", color = CyberTextMuted)
        }
      },
      containerColor = ObsidianSurface
    )
  }

  // Dialog to add secondary protected email
  if (showAddSecondaryDialog) {
    AlertDialog(
      onDismissRequest = { showAddSecondaryDialog = false },
      title = {
        Text(
          text = "Agregar Alias / Correo Secundario",
          color = QuantumPurple,
          fontWeight = FontWeight.Bold,
          fontSize = 14.sp
        )
      },
      text = {
        Column {
          Text(
            text = "Agrega otra cuenta o alias para incluirla bajo el mismo enjambre de protección cuántica sin costo adicional.",
            color = CyberTextSecondary,
            fontSize = 11.sp
          )
          Spacer(modifier = Modifier.height(10.dp))
          OutlinedTextField(
            value = secondaryEmailInput,
            onValueChange = { secondaryEmailInput = it },
            label = { Text("alias@ejemplo.com") },
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("add_secondary_email_input"),
            colors = OutlinedTextFieldDefaults.colors(
              focusedTextColor = CyberTextPrimary,
              unfocusedTextColor = CyberTextPrimary,
              focusedBorderColor = QuantumPurple,
              unfocusedBorderColor = ObsidianBorder
            )
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (secondaryEmailInput.isNotBlank()) {
              onAddSecondaryEmail(secondaryEmailInput)
              secondaryEmailInput = ""
              showAddSecondaryDialog = false
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = QuantumPurple),
          shape = RoundedCornerShape(8.dp)
        ) {
          Text("AGREGAR", color = Color.White, fontWeight = FontWeight.Bold)
        }
      },
      dismissButton = {
        OutlinedButton(
          onClick = { showAddSecondaryDialog = false },
          shape = RoundedCornerShape(8.dp)
        ) {
          Text("CANCELAR", color = CyberTextMuted)
        }
      },
      containerColor = ObsidianSurface
    )
  }

  // Dialog to generate quantum email seal
  if (showSealDialog) {
    AlertDialog(
      onDismissRequest = { showSealDialog = false },
      title = {
        Text(
          text = "Firmar Mensaje con Sello Cuántico",
          color = QuantumPurple,
          fontWeight = FontWeight.Bold,
          fontSize = 14.sp
        )
      },
      text = {
        Column {
          Text(
            text = "Genera un sello inmutable con firma digital Dilithium-5 y digest SHA3-512 para tu correo saliente:",
            color = CyberTextSecondary,
            fontSize = 11.sp
          )
          Spacer(modifier = Modifier.height(8.dp))
          OutlinedTextField(
            value = sealSubjectInput,
            onValueChange = { sealSubjectInput = it },
            label = { Text("Asunto del Correo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
              focusedTextColor = CyberTextPrimary,
              unfocusedTextColor = CyberTextPrimary,
              focusedBorderColor = QuantumPurple,
              unfocusedBorderColor = ObsidianBorder
            )
          )
          Spacer(modifier = Modifier.height(8.dp))
          OutlinedTextField(
            value = sealSnippetInput,
            onValueChange = { sealSnippetInput = it },
            label = { Text("Extracto / Contenido a Firmar") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3,
            colors = OutlinedTextFieldDefaults.colors(
              focusedTextColor = CyberTextPrimary,
              unfocusedTextColor = CyberTextPrimary,
              focusedBorderColor = QuantumPurple,
              unfocusedBorderColor = ObsidianBorder
            )
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            onGenerateSeal(sealSubjectInput, sealSnippetInput)
            showSealDialog = false
          },
          colors = ButtonDefaults.buttonColors(containerColor = QuantumPurple),
          shape = RoundedCornerShape(8.dp)
        ) {
          Text("GENERAR SELLO", color = Color.White, fontWeight = FontWeight.Bold)
        }
      },
      dismissButton = {
        OutlinedButton(
          onClick = { showSealDialog = false },
          shape = RoundedCornerShape(8.dp)
        ) {
          Text("CANCELAR", color = CyberTextMuted)
        }
      },
      containerColor = ObsidianSurface
    )
  }
}

@Composable
fun EmailKpiCard(
  title: String,
  value: String,
  subtitle: String,
  color: Color,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = BorderStroke(1.dp, ObsidianBorder)
  ) {
    Column(modifier = Modifier.padding(10.dp)) {
      Text(
        text = title,
        fontSize = 8.sp,
        fontWeight = FontWeight.Bold,
        color = CyberTextMuted,
        maxLines = 1
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = value,
        fontSize = 15.sp,
        fontWeight = FontWeight.Black,
        fontFamily = FontFamily.Monospace,
        color = color
      )
      Text(
        text = subtitle,
        fontSize = 8.sp,
        color = CyberTextSecondary,
        maxLines = 1
      )
    }
  }
}

@Composable
fun EmailSwarmDroneCard(drone: EmailSwarmDrone) {
  Card(
    modifier = Modifier
      .width(200.dp)
      .testTag("drone_card_${drone.id}"),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = BorderStroke(1.dp, CyberCyan.copy(alpha = 0.3f))
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = drone.codeName,
          fontSize = 11.sp,
          fontWeight = FontWeight.Black,
          fontFamily = FontFamily.Monospace,
          color = CyberCyan
        )
        Box(
          modifier = Modifier
            .clip(CircleShape)
            .background(CyberEmerald)
            .size(6.dp)
        )
      }
      Text(
        text = drone.role,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        color = CyberTextPrimary,
        maxLines = 1
      )
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = drone.specialty,
        fontSize = 9.sp,
        color = CyberTextSecondary,
        lineHeight = 12.sp,
        maxLines = 2
      )
      Spacer(modifier = Modifier.height(8.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = drone.pqcModule,
          fontSize = 8.sp,
          fontFamily = FontFamily.Monospace,
          color = QuantumPurple
        )
        Text(
          text = "${drone.threatsIntercepted} bloqueos",
          fontSize = 8.sp,
          fontWeight = FontWeight.Bold,
          color = CyberEmerald
        )
      }
    }
  }
}

@Composable
fun SimulationButton(
  label: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  OutlinedButton(
    onClick = onClick,
    modifier = modifier.height(34.dp),
    shape = RoundedCornerShape(8.dp),
    border = BorderStroke(1.dp, ObsidianBorder),
    colors = ButtonDefaults.outlinedButtonColors(
      containerColor = ObsidianElevated,
      contentColor = CyberTextPrimary
    )
  ) {
    Text(
      text = label,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      maxLines = 1
    )
  }
}

@Composable
fun EmailRuleToggle(
  title: String,
  description: String,
  checked: Boolean,
  onCheckedChange: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 6.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = title,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = CyberTextPrimary
      )
      Text(
        text = description,
        fontSize = 9.sp,
        color = CyberTextSecondary,
        lineHeight = 13.sp
      )
    }
    Spacer(modifier = Modifier.width(10.dp))
    Switch(
      checked = checked,
      onCheckedChange = { onCheckedChange() },
      colors = SwitchDefaults.colors(
        checkedThumbColor = CyberEmerald,
        checkedTrackColor = CyberEmerald.copy(alpha = 0.3f),
        uncheckedThumbColor = CyberTextMuted,
        uncheckedTrackColor = ObsidianElevated
      )
    )
  }
}

@Composable
fun EmailAuditRow(item: EmailAuditItem) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(8.dp))
      .background(ObsidianBackground)
      .padding(horizontal = 10.dp, vertical = 8.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = if (item.isSecure) Icons.Default.CheckCircle else Icons.Default.Warning,
          contentDescription = null,
          tint = if (item.isSecure) CyberEmerald else WarningGold,
          modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = item.title,
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          color = CyberTextPrimary
        )
      }
      Text(
        text = item.description,
        fontSize = 9.sp,
        color = CyberTextSecondary,
        lineHeight = 12.sp
      )
    }
    Box(
      modifier = Modifier
        .clip(RoundedCornerShape(4.dp))
        .background(CyberEmerald.copy(alpha = 0.15f))
        .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
      Text(
        text = item.score,
        fontSize = 9.sp,
        fontWeight = FontWeight.Black,
        fontFamily = FontFamily.Monospace,
        color = CyberEmerald
      )
    }
  }
}

@Composable
fun EmailThreatIncidentCard(incident: EmailThreatIncident) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("threat_incident_${incident.id}"),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = BorderStroke(1.dp, ThreatCrimson.copy(alpha = 0.4f))
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(ThreatCrimson.copy(alpha = 0.15f))
            .border(1.dp, ThreatCrimson.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(
            text = incident.threatType.label.uppercase(),
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = ThreatCrimson
          )
        }
        Text(
          text = incident.timestamp,
          fontSize = 9.sp,
          fontFamily = FontFamily.Monospace,
          color = CyberTextMuted
        )
      }

      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = "Asunto: ${incident.subject}",
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = CyberTextPrimary
      )
      Text(
        text = "Remitente: ${incident.sender}",
        fontSize = 10.sp,
        fontFamily = FontFamily.Monospace,
        color = WarningGold
      )

      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = incident.details,
        fontSize = 9.sp,
        color = CyberTextSecondary,
        lineHeight = 13.sp
      )

      Spacer(modifier = Modifier.height(6.dp))
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(6.dp))
          .background(ObsidianBackground)
          .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "🛡️ ${incident.mitigationAction}",
          fontSize = 9.sp,
          fontWeight = FontWeight.Bold,
          color = CyberEmerald
        )
        Text(
          text = "NODO: ${incident.droneInterceptor}",
          fontSize = 8.sp,
          fontFamily = FontFamily.Monospace,
          color = CyberCyan
        )
      }
    }
  }
}

// -----------------------------------------------------------------------------
// Componente: Alias Fantasma Efímeros (Escudos Anti-Rastreo)
// -----------------------------------------------------------------------------

@Composable
private fun GhostEmailAliasesCard(
  aliases: List<GhostEmailAlias>,
  onGenerate: () -> Unit,
  onDestroy: (String) -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("card_ghost_email_aliases"),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = BorderStroke(1.dp, Brush.horizontalGradient(listOf(QuantumPurple.copy(alpha = 0.6f), CyberCyan.copy(alpha = 0.6f))))
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
          Icon(Icons.Default.VisibilityOff, contentDescription = null, tint = QuantumPurple, modifier = Modifier.size(18.dp))
          Text("ALIAS FANTASMA EFÍMEROS", fontSize = 11.sp, fontWeight = FontWeight.Black, color = QuantumPurple)
        }
        Button(
          onClick = onGenerate,
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(containerColor = QuantumPurple, contentColor = Color.White),
          contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 4.dp),
          modifier = Modifier.testTag("btn_generate_ghost_alias")
        ) {
          Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("CREAR ALIAS", fontSize = 9.5.sp, fontWeight = FontWeight.Bold)
        }
      }

      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = "Direcciones máscara desechables que enrutan de forma cuántica cifrada a tu bandeja soberana sin exponer jamás tu correo real ni metadatos.",
        fontSize = 10.sp,
        color = CyberTextSecondary
      )

      Spacer(modifier = Modifier.height(10.dp))

      if (aliases.isEmpty()) {
        Text("No hay alias activos. Toca 'CREAR ALIAS' para generar uno.", fontSize = 10.sp, color = CyberTextMuted)
      } else {
        aliases.forEach { alias ->
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 3.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(ObsidianElevated)
              .border(0.5.dp, ObsidianBorder, RoundedCornerShape(8.dp))
              .padding(horizontal = 10.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(alias.aliasAddress, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, color = CyberTextPrimary)
              Text("${alias.expiresAt} • ${alias.emailsForwarded} correos recibidos", fontSize = 9.sp, color = CyberTextMuted)
            }
            IconButton(
              onClick = { onDestroy(alias.id) },
              modifier = Modifier.size(28.dp)
            ) {
              Icon(Icons.Default.Close, contentDescription = "Destruir", tint = ThreatCrimson, modifier = Modifier.size(16.dp))
            }
          }
        }
      }
    }
  }
}

// -----------------------------------------------------------------------------
// Componente: Micro-Sandbox Cuántico de Detonación de Adjuntos
// -----------------------------------------------------------------------------

@Composable
private fun QuantumSandboxDetonationCard(
  state: ArmasQuantumEmailState,
  onAnalyze: (String) -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("card_quantum_sandbox_detonation"),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = BorderStroke(1.dp, Brush.horizontalGradient(listOf(ThreatCrimson.copy(alpha = 0.5f), WarningGold.copy(alpha = 0.5f))))
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
          Icon(Icons.Default.BugReport, contentDescription = null, tint = ThreatCrimson, modifier = Modifier.size(18.dp))
          Text("MICRO-SANDBOX DE DETONACIÓN CUÁNTICA", fontSize = 11.sp, fontWeight = FontWeight.Black, color = ThreatCrimson)
        }
      }

      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = "Ejecuta adjuntos y binarios sospechosos en un micro-contenedor aislado en memoria RAM cuántica efímera para neutralizar payloads y exploits antes de abrirlos.",
        fontSize = 10.sp,
        color = CyberTextSecondary
      )

      Spacer(modifier = Modifier.height(10.dp))

      Button(
        onClick = { onAnalyze("informe_militar_armas.pdf") },
        enabled = !state.isAnalyzingSandboxAttachment,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = ThreatCrimson.copy(alpha = 0.2f), contentColor = ThreatCrimson),
        border = BorderStroke(1.dp, ThreatCrimson),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("btn_analyze_attachment_sandbox")
      ) {
        if (state.isAnalyzingSandboxAttachment) {
          androidx.compose.material3.CircularProgressIndicator(modifier = Modifier.size(14.dp), strokeWidth = 2.dp, color = ThreatCrimson)
          Spacer(modifier = Modifier.width(6.dp))
          Text("DETONANDO EN SANDBOX SEGURO...", fontSize = 10.sp, fontWeight = FontWeight.Bold)
        } else {
          Icon(Icons.Default.Shield, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("DETONAR ADJUNTO DE PRUEBA EN SANDBOX", fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
      }

      state.lastSandboxAnalysisResult?.let { result ->
        Spacer(modifier = Modifier.height(8.dp))
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(CyberEmerald.copy(alpha = 0.1f))
            .border(0.5.dp, CyberEmerald.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .padding(10.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = CyberEmerald, modifier = Modifier.size(16.dp))
            Text(result, fontSize = 9.5.sp, color = CyberTextPrimary, lineHeight = 14.sp)
          }
        }
      }
    }
  }
}
