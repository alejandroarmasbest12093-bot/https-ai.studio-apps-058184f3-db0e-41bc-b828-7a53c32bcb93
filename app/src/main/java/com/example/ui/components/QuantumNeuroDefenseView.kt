package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Biotech
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Grain
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CognitiveAiDiagnosisResult
import com.example.model.DigitalAntigen
import com.example.model.NeuralDeceptionHologram
import com.example.model.QuantumNeuroDefenseState
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberCyanMuted
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberTextMuted
import com.example.ui.theme.CyberTextPrimary
import com.example.ui.theme.CyberTextSecondary
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianElevated
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.QuantumPurple
import com.example.ui.theme.QuantumPurpleDark
import com.example.ui.theme.ThreatCrimson
import com.example.ui.theme.WarningGold
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun QuantumNeuroDefenseView(
  neuroState: QuantumNeuroDefenseState,
  onToggleNeuroDefense: () -> Unit,
  onToggleMemoryMorphing: () -> Unit,
  onSynthesizeImmediateAntigen: (String, String) -> Unit,
  onDeployHologram: (String, String) -> Unit,
  onAnalyzeGemini: (String) -> Unit,
  onSetCustomApiKey: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var searchQuery by remember { mutableStateOf("") }
  var showApiKeyConfig by remember { mutableStateOf(false) }
  var apiKeyInput by remember { mutableStateOf(neuroState.customGeminiApiKey) }
  var customAntigenName by remember { mutableStateOf("") }
  var customAntigenVector by remember { mutableStateOf("") }
  var showNewAntigenDialog by remember { mutableStateOf(false) }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. HERO HEADER CARD
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("neuro_defense_hero_card"),
      colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
      shape = RoundedCornerShape(16.dp),
      border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(QuantumPurple, CyberCyan)))
    ) {
      Column(modifier = Modifier.padding(18.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Box(
              modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(QuantumPurpleDark.copy(alpha = 0.5f))
                .border(1.5.dp, QuantumPurple, CircleShape),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Psychology,
                contentDescription = "Neuro-Defensa IA",
                tint = CyberCyan,
                modifier = Modifier.size(26.dp)
              )
            }
            Column {
              Text(
                text = "NEURO-DEFENSA CUÁNTICA IA",
                fontWeight = FontWeight.Black,
                color = CyberTextPrimary,
                fontSize = 17.sp,
                letterSpacing = 0.5.sp
              )
              Text(
                text = "Inmunidad Adaptativa con Gemini 3.5 Flash",
                color = QuantumPurple,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
              )
            }
          }

          Switch(
            checked = neuroState.isNeuroDefenseActive,
            onCheckedChange = { onToggleNeuroDefense() },
            modifier = Modifier.testTag("toggle_neuro_defense_switch"),
            colors = SwitchDefaults.colors(
              checkedThumbColor = ObsidianBackground,
              checkedTrackColor = CyberCyan,
              uncheckedThumbColor = CyberTextMuted,
              uncheckedTrackColor = ObsidianBorder
            )
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "Tecnología cognitiva de 5ª generación nunca antes creada: sintetiza antígenos digitales en tiempo real, aplica polimorfismo dinámico de memoria contra Zero-Days y atrapa espías en redes holográficas de contra-inteligencia.",
          color = CyberTextSecondary,
          fontSize = 12.sp,
          lineHeight = 17.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Quick Stats Badges
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          QuickStatBadge(
            label = "PULSOS SINÁPTICOS",
            value = "${"%.2f".format(neuroState.synapticFiringRateGhz)} GHz",
            accentColor = CyberCyan,
            modifier = Modifier.weight(1f)
          )
          QuickStatBadge(
            label = "INMUNIDAD ZERO-DAY",
            value = "${neuroState.zeroDayImmunityRate}%",
            accentColor = CyberEmerald,
            modifier = Modifier.weight(1f)
          )
          QuickStatBadge(
            label = "ANTÍGENOS ACTIVOS",
            value = "${neuroState.activeDigitalAntigens.size}",
            accentColor = QuantumPurple,
            modifier = Modifier.weight(1f)
          )
        }
      }
    }

    // 2. LIVE NEURAL SYNAPSES CANVAS
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("neural_canvas_card"),
      colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
      shape = RoundedCornerShape(16.dp),
      border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(ObsidianBorder, QuantumPurple.copy(alpha = 0.4f))))
    ) {
      Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Grain,
              contentDescription = null,
              tint = CyberCyan,
              modifier = Modifier.size(16.dp)
            )
            Text(
              text = "TOPOLOGÍA NEURO-SINÁPTICA EN VIVO",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = CyberTextPrimary
            )
          }

          Text(
            text = if (neuroState.isNeuroDefenseActive) "ESTADO: DISPARO ACTIVO" else "ESTADO: EN ESPERA",
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
            color = if (neuroState.isNeuroDefenseActive) CyberEmerald else WarningGold,
            fontWeight = FontWeight.Bold
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // The custom animated Canvas
        NeuralSynapseDefenseCanvas(
          isActive = neuroState.isNeuroDefenseActive,
          firingRateGhz = neuroState.synapticFiringRateGhz,
          modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Dynamic Memory Morphing Status Row
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(ObsidianElevated)
            .padding(horizontal = 12.dp, vertical = 8.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Memory,
              contentDescription = null,
              tint = QuantumPurple,
              modifier = Modifier.size(16.dp)
            )
            Column {
              Text(
                text = "Polimorfismo Dinámico de Memoria (15s)",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = CyberTextPrimary
              )
              Text(
                text = neuroState.morphEntropySignature,
                fontSize = 9.sp,
                fontFamily = FontFamily.Monospace,
                color = CyberCyan
              )
            }
          }

          Switch(
            checked = neuroState.quantumMemoryMorphingActive,
            onCheckedChange = { onToggleMemoryMorphing() },
            modifier = Modifier.testTag("toggle_memory_morphing_switch"),
            colors = SwitchDefaults.colors(
              checkedThumbColor = ObsidianBackground,
              checkedTrackColor = QuantumPurple,
              uncheckedThumbColor = CyberTextMuted,
              uncheckedTrackColor = ObsidianBorder
            )
          )
        }
      }
    }

    // 3. GEMINI 3.5 FLASH COGNITIVE AI DEEP ANALYZER
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("gemini_cognitive_card"),
      colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
      shape = RoundedCornerShape(16.dp),
      border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(CyberCyan.copy(alpha = 0.4f), ObsidianBorder)))
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(
              imageVector = Icons.Default.AutoAwesome,
              contentDescription = null,
              tint = CyberCyan,
              modifier = Modifier.size(18.dp)
            )
            Text(
              text = "ESCÁNER COGNITIVO GEMINI AI",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = CyberTextPrimary
            )
          }

          IconButton(
            onClick = { showApiKeyConfig = !showApiKeyConfig },
            modifier = Modifier.size(32.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Key,
              contentDescription = "Configurar API Key",
              tint = if (neuroState.customGeminiApiKey.isNotBlank()) CyberEmerald else CyberTextMuted,
              modifier = Modifier.size(18.dp)
            )
          }
        }

        Text(
          text = "Introduce cualquier URL, IP de router, comando, hash o sospecha para radiografía y síntesis inmediata de antígeno.",
          fontSize = 11.sp,
          color = CyberTextSecondary,
          modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
        )

        // Optional API Key Config Area
        AnimatedVisibility(visible = showApiKeyConfig) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 12.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(ObsidianElevated)
              .padding(10.dp)
          ) {
            Text(
              text = "Clave API Gemini (Opcional - modo nube directo):",
              fontSize = 10.sp,
              color = CyberCyan,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              OutlinedTextField(
                value = apiKeyInput,
                onValueChange = { apiKeyInput = it },
                placeholder = { Text("Pega tu API Key de Gemini aquí...", fontSize = 11.sp, color = CyberTextMuted) },
                singleLine = true,
                modifier = Modifier
                  .weight(1f)
                  .testTag("gemini_api_key_input"),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = CyberCyan,
                  unfocusedBorderColor = ObsidianBorder,
                  focusedTextColor = CyberTextPrimary,
                  unfocusedTextColor = CyberTextPrimary
                )
              )
              Button(
                onClick = {
                  onSetCustomApiKey(apiKeyInput.trim())
                  showApiKeyConfig = false
                },
                colors = ButtonDefaults.buttonColors(containerColor = CyberCyan, contentColor = ObsidianBackground),
                shape = RoundedCornerShape(8.dp)
              ) {
                Text("Guardar", fontSize = 11.sp, fontWeight = FontWeight.Bold)
              }
            }
            Text(
              text = "Si se deja vacío, el motor utiliza la inteligencia heurística cognitiva autónoma Armas PQC sin conexión.",
              fontSize = 9.sp,
              color = CyberTextMuted,
              modifier = Modifier.padding(top = 4.dp)
            )
          }
        }

        // Search/Diagnose Field
        OutlinedTextField(
          value = searchQuery,
          onValueChange = { searchQuery = it },
          placeholder = { Text("Ej. 192.168.1.254 o bit.ly/promo o exploit zero-day...", fontSize = 12.sp, color = CyberTextMuted) },
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("gemini_search_query_input"),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = CyberCyan,
            unfocusedBorderColor = ObsidianBorder,
            focusedTextColor = CyberTextPrimary,
            unfocusedTextColor = CyberTextPrimary,
            focusedContainerColor = ObsidianElevated,
            unfocusedContainerColor = ObsidianElevated
          )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Button(
            onClick = {
              if (searchQuery.isNotBlank()) {
                onAnalyzeGemini(searchQuery)
              }
            },
            enabled = searchQuery.isNotBlank() && !neuroState.isAnalyzingWithGemini,
            colors = ButtonDefaults.buttonColors(
              containerColor = CyberCyan,
              contentColor = ObsidianBackground,
              disabledContainerColor = ObsidianBorder
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
              .weight(1f)
              .testTag("run_gemini_diagnosis_button")
          ) {
            if (neuroState.isAnalyzingWithGemini) {
              CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                color = ObsidianBackground,
                strokeWidth = 2.dp
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text("Razonando...", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            } else {
              Icon(imageVector = Icons.Default.Psychology, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Escanear con Gemini AI", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }

          OutlinedButton(
            onClick = {
              searchQuery = listOf(
                "http://clandestine-tracker.xyz/beacon.php",
                "192.168.1.1 (ARP Spoofing Router Anomaly)",
                "Pegasus Zero-Click Memory Injection Vector",
                "Ghost Wi-Fi BSSID Probe 4A:2F:88:91:02"
              ).random()
            },
            colors = ButtonDefaults.outlinedButtonColors(contentColor = CyberCyan),
            border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.horizontalGradient(listOf(CyberCyan, QuantumPurple))),
            shape = RoundedCornerShape(8.dp)
          ) {
            Text("Probar", fontSize = 11.sp)
          }
        }

        // Diagnosis Result Display
        if (neuroState.lastDiagnosisResult != null) {
          Spacer(modifier = Modifier.height(14.dp))
          DiagnosisResultCard(result = neuroState.lastDiagnosisResult)
        }
      }
    }

    // 4. DIGITAL ANTIGENS CATÁLOGO & SÍNTESIS
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("digital_antigens_card"),
      colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
      shape = RoundedCornerShape(16.dp),
      border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(ObsidianBorder, CyberEmerald.copy(alpha = 0.4f))))
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Biotech,
              contentDescription = null,
              tint = CyberEmerald,
              modifier = Modifier.size(20.dp)
            )
            Text(
              text = "ANTÍGENOS DIGITALES EN CALIENTE (${neuroState.activeDigitalAntigens.size})",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = CyberTextPrimary
            )
          }

          Button(
            onClick = {
              onSynthesizeImmediateAntigen("Zero-Day Kernel Cage", "Mutación Heurística de Memoria No Detectada")
            },
            colors = ButtonDefaults.buttonColors(containerColor = CyberEmerald, contentColor = ObsidianBackground),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.testTag("synthesize_antigen_button")
          ) {
            if (neuroState.isSynthesizingAntigen) {
              CircularProgressIndicator(modifier = Modifier.size(14.dp), color = ObsidianBackground, strokeWidth = 2.dp)
            } else {
              Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Sintetizar", fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
          }
        }

        Text(
          text = "Vacunas lógicas generadas por IA que mutan el código en tiempo de ejecución para neutralizar exploits antes de que toquen el sistema.",
          fontSize = 11.sp,
          color = CyberTextSecondary,
          modifier = Modifier.padding(vertical = 8.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          neuroState.activeDigitalAntigens.forEach { antigen ->
            DigitalAntigenItemCard(antigen = antigen)
          }
        }
      }
    }

    // 5. HOLOGRAPHIC DECEPTION HONEYNET (MALLA DECEPTIVA)
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("deception_honeynet_card"),
      colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
      shape = RoundedCornerShape(16.dp),
      border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(ThreatCrimson.copy(alpha = 0.3f), ObsidianBorder)))
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Radar,
              contentDescription = null,
              tint = ThreatCrimson,
              modifier = Modifier.size(20.dp)
            )
            Text(
              text = "MALLA DECEPTIVA HOLOGRÁFICA (HONEYNET)",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = CyberTextPrimary
            )
          }

          OutlinedButton(
            onClick = {
              val types = listOf("VIRTUAL_ROUTER_GATEWAY", "SYNTHETIC_GPS_BEACON", "GHOST_CREDENTIAL_VAULT")
              val names = listOf("Señuelo Gateway MikroTik-PQC", "Señuelo Satélite Galileo-Cloak", "Señuelo Baseband-Decoy")
              val idx = (0..2).random()
              onDeployHologram(names[idx], types[idx])
            },
            colors = ButtonDefaults.outlinedButtonColors(contentColor = ThreatCrimson),
            border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.horizontalGradient(listOf(ThreatCrimson, WarningGold))),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.testTag("deploy_honeynet_decoy_button")
          ) {
            Icon(imageVector = Icons.Default.TrackChanges, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Nuevo Señuelo", fontSize = 10.sp, fontWeight = FontWeight.Bold)
          }
        }

        Text(
          text = "Proyecta espejos cuánticos falsos para que los atacantes y routers espía interactúen con datos simulados mientras extraemos su firma forense.",
          fontSize = 11.sp,
          color = CyberTextSecondary,
          modifier = Modifier.padding(vertical = 8.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          neuroState.activeHolograms.forEach { holo ->
            HolographicDecoyItemCard(hologram = holo)
          }
        }
      }
    }

    // 6. TELEMETRÍA Y LOGS EN VIVO
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("neuro_logs_card"),
      colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
      shape = RoundedCornerShape(16.dp),
      border = CardDefaults.outlinedCardBorder()
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Sensors,
            contentDescription = null,
            tint = CyberCyan,
            modifier = Modifier.size(18.dp)
          )
          Text(
            text = "REGISTRO DE DISPARO SINÁPTICO Y MUTACIONES",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = CyberTextPrimary
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(ObsidianBackground)
            .padding(12.dp),
          verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          neuroState.neuroTelemetryLogs.take(8).forEach { log ->
            Text(
              text = log,
              fontSize = 10.sp,
              fontFamily = FontFamily.Monospace,
              color = when {
                log.contains("SINTETIZADO") || log.contains("DISPARO") -> CyberEmerald
                log.contains("MUTACIÓN") -> QuantumPurple
                log.contains("SEÑUELO") || log.contains("DIAGNÓSTICO") -> CyberCyan
                log.contains("PAUSA") -> WarningGold
                else -> CyberTextSecondary
              }
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))
  }
}

// -------------------------------------------------------------------------
// CUSTOM ANIMATED NEURAL SYNAPSE CANVAS
// -------------------------------------------------------------------------

@Composable
fun NeuralSynapseDefenseCanvas(
  isActive: Boolean,
  firingRateGhz: Double,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "NeuralCanvasTransition")
  val pulseProgress by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 3200, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "NeuralPulse"
  )

  val rotationAngle by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 18000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "NeuralRotation"
  )

  Canvas(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .background(ObsidianBackground)
      .border(1.dp, ObsidianBorder, RoundedCornerShape(12.dp))
  ) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val center = Offset(canvasWidth / 2f, canvasHeight / 2f)

    // Draw background grid lines
    val gridStep = 35f
    var x = 0f
    while (x < canvasWidth) {
      drawLine(
        color = ObsidianBorder.copy(alpha = 0.35f),
        start = Offset(x, 0f),
        end = Offset(x, canvasHeight),
        strokeWidth = 1f
      )
      x += gridStep
    }
    var y = 0f
    while (y < canvasHeight) {
      drawLine(
        color = ObsidianBorder.copy(alpha = 0.35f),
        start = Offset(0f, y),
        end = Offset(canvasWidth, y),
        strokeWidth = 1f
      )
      y += gridStep
    }

    // Outer cybernetic reticle ring
    drawCircle(
      color = if (isActive) QuantumPurple.copy(alpha = 0.25f) else ObsidianBorder,
      radius = canvasHeight * 0.42f,
      center = center,
      style = Stroke(
        width = 1.5f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), phase = rotationAngle)
      )
    )

    // Inner reticle ring
    drawCircle(
      color = if (isActive) CyberCyan.copy(alpha = 0.3f) else ObsidianBorder,
      radius = canvasHeight * 0.25f,
      center = center,
      style = Stroke(
        width = 1.5f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f), phase = -rotationAngle * 1.5f)
      )
    )

    // Define fixed neural constellation nodes
    val nodeCount = 7
    val nodeRadius = canvasHeight * 0.38f
    val nodePoints = mutableListOf<Offset>()

    for (i in 0 until nodeCount) {
      val angleRad = Math.toRadians((i * (360.0 / nodeCount) + (rotationAngle * 0.2)).toDouble())
      val nx = center.x + (nodeRadius * cos(angleRad)).toFloat()
      val ny = center.y + (nodeRadius * 0.75 * sin(angleRad)).toFloat()
      nodePoints.add(Offset(nx, ny))
    }

    // Draw synapses (connections from center to perimeter nodes and between neighbors)
    nodePoints.forEachIndexed { index, nodePos ->
      // Connection to center
      drawLine(
        color = if (isActive) CyberCyan.copy(alpha = 0.35f) else ObsidianBorder,
        start = center,
        end = nodePos,
        strokeWidth = 1.2f
      )

      // Connection to next neighbor
      val nextPos = nodePoints[(index + 1) % nodeCount]
      drawLine(
        color = if (isActive) QuantumPurple.copy(alpha = 0.25f) else ObsidianBorder.copy(alpha = 0.5f),
        start = nodePos,
        end = nextPos,
        strokeWidth = 1f
      )

      // Active energy packet travelling along the synapse
      if (isActive) {
        val packetOffsetProgress = ((pulseProgress + (index.toFloat() / nodeCount)) % 1f)
        val packetPos = Offset(
          center.x + (nodePos.x - center.x) * packetOffsetProgress,
          center.y + (nodePos.y - center.y) * packetOffsetProgress
        )
        drawCircle(
          color = CyberEmerald,
          radius = 3.5f,
          center = packetPos
        )
      }

      // Perimeter node dot
      drawCircle(
        color = if (isActive) QuantumPurple else CyberTextMuted,
        radius = 5.5f,
        center = nodePos
      )
      drawCircle(
        color = ObsidianBackground,
        radius = 2.5f,
        center = nodePos
      )
    }

    // Center Core Hub (Brain nucleus)
    drawCircle(
      brush = Brush.radialGradient(
        colors = listOf(
          if (isActive) CyberCyan.copy(alpha = 0.5f) else ObsidianBorder,
          Color.Transparent
        ),
        center = center,
        radius = 28f
      ),
      radius = 28f,
      center = center
    )

    drawCircle(
      color = if (isActive) CyberCyan else ObsidianBorder,
      radius = 9f,
      center = center
    )
    drawCircle(
      color = ObsidianBackground,
      radius = 4f,
      center = center
    )
  }
}

// -------------------------------------------------------------------------
// ITEM CARDS & SUB-COMPONENTS
// -------------------------------------------------------------------------

@Composable
fun QuickStatBadge(
  label: String,
  value: String,
  accentColor: Color,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .clip(RoundedCornerShape(8.dp))
      .background(ObsidianElevated)
      .border(1.dp, ObsidianBorder, RoundedCornerShape(8.dp))
      .padding(8.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = label,
      fontSize = 8.sp,
      fontWeight = FontWeight.Bold,
      color = CyberTextMuted,
      textAlign = TextAlign.Center,
      maxLines = 1
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = value,
      fontSize = 12.sp,
      fontWeight = FontWeight.Black,
      color = accentColor,
      fontFamily = FontFamily.Monospace,
      textAlign = TextAlign.Center
    )
  }
}

@Composable
fun DiagnosisResultCard(result: CognitiveAiDiagnosisResult) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(ObsidianElevated)
      .border(1.dp, CyberCyan.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
      .padding(14.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Icon(
          imageVector = Icons.Default.CheckCircle,
          contentDescription = null,
          tint = CyberEmerald,
          modifier = Modifier.size(16.dp)
        )
        Text(
          text = "DIAGNÓSTICO COGNITIVO EMITIDO",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = CyberTextPrimary
        )
      }

      Text(
        text = "RIESGO: ${result.riskScore}%",
        fontSize = 11.sp,
        fontWeight = FontWeight.Black,
        fontFamily = FontFamily.Monospace,
        color = if (result.riskScore > 80) ThreatCrimson else WarningGold
      )
    }

    Spacer(modifier = Modifier.height(6.dp))

    LinearProgressIndicator(
      progress = { result.riskScore / 100f },
      modifier = Modifier
        .fillMaxWidth()
        .height(4.dp)
        .clip(RoundedCornerShape(2.dp)),
      color = if (result.riskScore > 80) ThreatCrimson else WarningGold,
      trackColor = ObsidianBorder
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
      text = "Vector: ${result.threatVector}",
      fontSize = 11.sp,
      fontWeight = FontWeight.SemiBold,
      color = CyberCyan
    )

    Text(
      text = "MITRE ATT&CK: ${result.mitreAttackTechnique}",
      fontSize = 10.sp,
      fontFamily = FontFamily.Monospace,
      color = QuantumPurple,
      modifier = Modifier.padding(top = 2.dp)
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
      text = result.deepAiExplanation,
      fontSize = 11.sp,
      color = CyberTextSecondary,
      lineHeight = 16.sp
    )

    Spacer(modifier = Modifier.height(10.dp))

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(6.dp))
        .background(ObsidianBackground)
        .padding(8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Icon(
        imageVector = Icons.Default.Security,
        contentDescription = null,
        tint = CyberEmerald,
        modifier = Modifier.size(14.dp)
      )
      Text(
        text = "Antígeno Inyectado: ${result.synthesizedAntigen?.antigenName ?: "Parche Activo"}",
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        color = CyberEmerald,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
    }
  }
}

@Composable
fun DigitalAntigenItemCard(antigen: DigitalAntigen) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(10.dp))
      .background(ObsidianElevated)
      .border(1.dp, ObsidianBorder, RoundedCornerShape(10.dp))
      .padding(12.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.weight(1f)
      ) {
        Box(
          modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(CyberEmerald)
        )
        Text(
          text = antigen.antigenName,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = CyberTextPrimary,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      }

      Text(
        text = "${antigen.neutralizationLatencyMs} ms",
        fontSize = 10.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        color = CyberEmerald
      )
    }

    Spacer(modifier = Modifier.height(4.dp))

    Text(
      text = "Vector: ${antigen.targetZeroDayVector}",
      fontSize = 10.sp,
      color = CyberTextSecondary
    )

    Spacer(modifier = Modifier.height(4.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = antigen.dynamicBytecodeHash.take(24) + "...",
        fontSize = 9.sp,
        fontFamily = FontFamily.Monospace,
        color = CyberCyanMuted
      )

      Text(
        text = antigen.immunityScope,
        fontSize = 8.sp,
        fontWeight = FontWeight.Bold,
        color = QuantumPurple,
        fontFamily = FontFamily.Monospace
      )
    }
  }
}

@Composable
fun HolographicDecoyItemCard(hologram: NeuralDeceptionHologram) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(10.dp))
      .background(ObsidianElevated)
      .border(1.dp, ObsidianBorder, RoundedCornerShape(10.dp))
      .padding(12.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.weight(1f)
      ) {
        Icon(
          imageVector = Icons.Default.TrackChanges,
          contentDescription = null,
          tint = ThreatCrimson,
          modifier = Modifier.size(14.dp)
        )
        Text(
          text = hologram.honeynetNodeName,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = CyberTextPrimary,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      }

      Text(
        text = "${hologram.activeIntrudersTrapped} Atacantes Atrapados",
        fontSize = 9.sp,
        fontWeight = FontWeight.Bold,
        color = ThreatCrimson,
        fontFamily = FontFamily.Monospace
      )
    }

    Spacer(modifier = Modifier.height(4.dp))

    Text(
      text = hologram.telemetryHarvested,
      fontSize = 10.sp,
      color = CyberTextSecondary
    )

    Spacer(modifier = Modifier.height(4.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "Bytes ficticios emitidos: ${hologram.syntheticBytesFed} B",
        fontSize = 9.sp,
        fontFamily = FontFamily.Monospace,
        color = CyberTextMuted
      )

      Text(
        text = "SEÑUELO ACTIVO",
        fontSize = 8.sp,
        fontWeight = FontWeight.Bold,
        color = CyberEmerald,
        fontFamily = FontFamily.Monospace
      )
    }
  }
}
