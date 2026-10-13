package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CallReceived
import androidx.compose.material.icons.filled.CallMade
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Stream
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PredefinedSynapseIntentions
import com.example.model.SynapseIntention
import com.example.model.SynapsePulse
import com.example.model.SynapseQState
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
import kotlin.math.cos
import kotlin.math.sin

/**
 * =========================================================================
 * SYNAPSE-Q™: PROTOCOLO DE COMUNICACIÓN TELEPÁTICA CUÁNTICA SINESTÉSICA
 * =========================================================================
 * Una nueva forma de comunicarse jamás antes creada por la humanidad:
 * Transmite pensamientos, emociones y decisiones complejas de forma pura,
 * sin intermediación de lenguaje verbal, palabras escritas ni voz hablada.
 *
 * Utiliza:
 * 1) Mandala Cuántico Cromático generativo en tiempo real (Espectro Fotónico).
 * 2) Firma Háptica Bio-Rítmica (micro-pulsos que transmiten intención en la palma de la mano).
 * 3) Armónicos Acústicos Solfeggio / Schumann (40Hz Gamma, 528Hz, 432Hz).
 * 4) Colapso instantáneo de Estados Bell de Entrelazamiento Cuántico EPR.
 * 5) Decodificador Neuro-Cognitivo Asistido por Inteligencia Artificial.
 */
@Composable
fun SynapseQScreen(
  state: SynapseQState,
  onSelectIntention: (SynapseIntention) -> Unit,
  onSelectTargetNode: (String) -> Unit,
  onToggleHaptic: () -> Unit,
  onToggleHarmonicAudio: () -> Unit,
  onTransmitPulse: () -> Unit,
  onSimulateReceivePulse: () -> Unit,
  onFreeTouchUpdate: (Float, Float) -> Unit,
  onDecodePulseWithAi: (SynapsePulse) -> Unit,
  onClearHistory: () -> Unit,
  onToggleBroadcastMode: () -> Unit = {},
  onBoostCoherence: () -> Unit = {},
  onToggleCustomComposer: () -> Unit = {},
  onUpdateCustomThought: (String) -> Unit = {},
  onSynthesizeCustomThought: () -> Unit = {}
) {
  var showTargetNodeMenu by remember { mutableStateOf(false) }

  // Animación infinita para el Mandala Cuántico
  val infiniteTransition = rememberInfiniteTransition(label = "synapse_mandala_anim")
  val rotationAnim by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 14000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "mandala_rotation"
  )
  val pulseGlowAnim by infiniteTransition.animateFloat(
    initialValue = 0.8f,
    targetValue = 1.25f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 2200, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "mandala_pulse_glow"
  )

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(ObsidianBackground)
      .padding(horizontal = 14.dp)
      .testTag("screen_synapse_q"),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(6.dp))
      SynapseHeaderSection(
        hapticEnabled = state.hapticEnabled,
        audioEnabled = state.harmonicAudioEnabled,
        onToggleHaptic = onToggleHaptic,
        onToggleAudio = onToggleHarmonicAudio
      )
    }

    // BARRA DE COHERENCIA MENTAL Y MODO BROADCAST MULTI-NODO
    item {
      SynapseCoherenceAndBroadcastBar(
        state = state,
        onBoostCoherence = onBoostCoherence,
        onToggleBroadcast = onToggleBroadcastMode
      )
    }

    // MANDALA CUÁNTICO INTERACTIVO (NÚCLEO SINESTÉSICO)
    item {
      SynapseMandalaSection(
        state = state,
        rotationDeg = rotationAnim,
        glowScale = pulseGlowAnim,
        onTouchCoords = onFreeTouchUpdate
      )
    }

    // SINTETIZADOR DE INTENCIÓN PERSONALIZADA CON IA
    item {
      SynapseCustomThoughtComposer(
        state = state,
        onToggleComposer = onToggleCustomComposer,
        onUpdateThought = onUpdateCustomThought,
        onSynthesize = onSynthesizeCustomThought
      )
    }

    // SELECTOR DE INTENCIONES PRE-VERBALES PURAS (PREDEFINIDAS + SINTETIZADAS)
    item {
      SynapseIntentionsPaletteSection(
        activeIntention = state.activeIntention,
        intentions = PredefinedSynapseIntentions.LIST + state.customSynthesizedIntentions,
        onSelect = onSelectIntention
      )
    }

    // CONSOLA DE TELETRANSPORTACIÓN CUÁNTICA
    item {
      SynapseTransmissionConsole(
        state = state,
        showNodeMenu = showTargetNodeMenu,
        onToggleNodeMenu = { showTargetNodeMenu = !showTargetNodeMenu },
        onSelectNode = {
          onSelectTargetNode(it)
          showTargetNodeMenu = false
        },
        onTransmit = onTransmitPulse,
        onReceiveSimulation = onSimulateReceivePulse
      )
    }

    // DECODIFICADOR NEURO-COGNITIVO GEMINI
    item {
      SynapseNeuroAiDecoderCard(
        decodedInsight = state.neuroAiDecodedInsight,
        isSynthesizing = state.isSynthesizingAi,
        activeIntention = state.activeIntention
      )
    }

    // HISTORIAL DE PULSOS TELEPÁTICOS ENTRELAZADOS
    item {
      SynapsePulseHistorySection(
        pulses = state.pulses,
        onDecodePulse = onDecodePulseWithAi,
        onClear = onClearHistory
      )
    }

    item {
      Spacer(modifier = Modifier.height(28.dp))
    }
  }
}

/**
 * Encabezado con estado del enlace cuántico e interruptores bio-rítmicos.
 */
@Composable
private fun SynapseHeaderSection(
  hapticEnabled: Boolean,
  audioEnabled: Boolean,
  onToggleHaptic: () -> Unit,
  onToggleAudio: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("card_synapse_header"),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    shape = RoundedCornerShape(18.dp),
    border = BorderStroke(1.dp, Brush.horizontalGradient(listOf(QuantumPurple, CyberCyan)))
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
              .size(38.dp)
              .clip(CircleShape)
              .background(QuantumPurple.copy(alpha = 0.2f))
              .border(1.dp, QuantumPurple, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(Icons.Default.Stream, contentDescription = null, tint = CyberCyan, modifier = Modifier.size(20.dp))
          }
          Column {
            Text(
              text = "SYNAPSE-Q™",
              fontSize = 17.sp,
              fontWeight = FontWeight.Black,
              color = CyberTextPrimary,
              letterSpacing = 1.sp
            )
            Text(
              text = "Comunicación Telepática Cuántica",
              fontSize = 11.sp,
              color = CyberCyan,
              fontWeight = FontWeight.Medium
            )
          }
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(CyberEmerald.copy(alpha = 0.15f))
            .border(1.dp, CyberEmerald, RoundedCornerShape(20.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Box(
              modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(CyberEmerald)
            )
            Text("EPR Bell Locked", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = CyberEmerald)
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = "Una nueva forma de comunicarse creada para trascender el lenguaje verbal: transmite intenciones complejas instantáneas mediante resonancia cuántica fotónica, háptica y armónica.",
        fontSize = 11.5.sp,
        color = CyberTextSecondary,
        lineHeight = 16.sp
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Switches Háptico y Acústico
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // Switch Háptico
        Row(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(ObsidianElevated)
            .border(1.dp, ObsidianBorder, RoundedCornerShape(12.dp))
            .clickable { onToggleHaptic() }
            .padding(horizontal = 10.dp, vertical = 6.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(
              Icons.Default.Vibration,
              contentDescription = null,
              tint = if (hapticEnabled) CyberCyan else CyberTextMuted,
              modifier = Modifier.size(16.dp)
            )
            Text("Tacto Háptico", fontSize = 11.sp, color = CyberTextPrimary, fontWeight = FontWeight.Bold)
          }
          Switch(
            checked = hapticEnabled,
            onCheckedChange = { onToggleHaptic() },
            modifier = Modifier.testTag("switch_synapse_haptic"),
            colors = SwitchDefaults.colors(
              checkedThumbColor = CyberCyan,
              checkedTrackColor = CyberCyan.copy(alpha = 0.3f),
              uncheckedThumbColor = CyberTextMuted,
              uncheckedTrackColor = ObsidianSurface
            )
          )
        }

        // Switch Acústico Solfeggio
        Row(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(ObsidianElevated)
            .border(1.dp, ObsidianBorder, RoundedCornerShape(12.dp))
            .clickable { onToggleAudio() }
            .padding(horizontal = 10.dp, vertical = 6.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(
              if (audioEnabled) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
              contentDescription = null,
              tint = if (audioEnabled) QuantumPurple else CyberTextMuted,
              modifier = Modifier.size(16.dp)
            )
            Text("Solfeggio", fontSize = 11.sp, color = CyberTextPrimary, fontWeight = FontWeight.Bold)
          }
          Switch(
            checked = audioEnabled,
            onCheckedChange = { onToggleAudio() },
            modifier = Modifier.testTag("switch_synapse_audio"),
            colors = SwitchDefaults.colors(
              checkedThumbColor = QuantumPurple,
              checkedTrackColor = QuantumPurple.copy(alpha = 0.3f),
              uncheckedThumbColor = CyberTextMuted,
              uncheckedTrackColor = ObsidianSurface
            )
          )
        }
      }
    }
  }
}

/**
 * Mandala Cuántico Generativo en Jetpack Compose Canvas.
 * Responde al tacto del usuario modificando frecuencia de onda y generando chispas cuánticas.
 */
@Composable
private fun SynapseMandalaSection(
  state: SynapseQState,
  rotationDeg: Float,
  glowScale: Float,
  onTouchCoords: (Float, Float) -> Unit
) {
  val intention = state.activeIntention
  val primaryColor = Color(intention.colorPrimaryHex)
  val secondaryColor = Color(intention.colorSecondaryHex)

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("card_synapse_mandala"),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    shape = RoundedCornerShape(20.dp),
    border = BorderStroke(1.dp, ObsidianBorder)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "MANDALA DE ENTROPY ZERO",
            fontSize = 10.sp,
            color = CyberCyan,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
          )
          Text(
            text = "Espectro Fotónico Interactivo",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = CyberTextPrimary
          )
        }
        Text(
          text = "${String.format("%.1f", state.liveDynamicFrequencyHz)} Hz",
          fontSize = 15.sp,
          fontFamily = FontFamily.Monospace,
          fontWeight = FontWeight.Black,
          color = primaryColor
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Canvas del Mandala Cuántico
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(240.dp)
          .clip(RoundedCornerShape(16.dp))
          .background(ObsidianBackground)
          .border(1.dp, primaryColor.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
          .pointerInput(Unit) {
            detectDragGestures { change, _ ->
              change.consume()
              val xNorm = (change.position.x / size.width).coerceIn(0f, 1f)
              val yNorm = (change.position.y / size.height).coerceIn(0f, 1f)
              onTouchCoords(xNorm, yNorm)
            }
          }
          .pointerInput(Unit) {
            detectTapGestures { offset ->
              val xNorm = (offset.x / size.width).coerceIn(0f, 1f)
              val yNorm = (offset.y / size.height).coerceIn(0f, 1f)
              onTouchCoords(xNorm, yNorm)
            }
          },
        contentAlignment = Alignment.Center
      ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
          val centerX = size.width / 2f
          val centerY = size.height / 2f
          val maxRadius = (minOf(size.width, size.height) / 2f) * 0.85f

          // Anillo externo de brillo pulsante
          drawCircle(
            brush = Brush.radialGradient(
              colors = listOf(primaryColor.copy(alpha = 0.25f * glowScale), Color.Transparent),
              center = Offset(centerX, centerY),
              radius = maxRadius * glowScale
            ),
            radius = maxRadius * glowScale,
            center = Offset(centerX, centerY)
          )

          // Ondas cuánticas concéntricas con rotación matemática
          val ringsCount = 6
          for (r in 1..ringsCount) {
            val ringRadius = (maxRadius / ringsCount) * r
            val ringStroke = 1.2f + (r * 0.3f)
            val ringAlpha = 0.2f + (0.6f / r)

            drawCircle(
              color = if (r % 2 == 0) primaryColor.copy(alpha = ringAlpha) else secondaryColor.copy(alpha = ringAlpha),
              radius = ringRadius,
              center = Offset(centerX, centerY),
              style = Stroke(
                width = ringStroke,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f + r * 3, 8f + r * 2), rotationDeg * (if (r % 2 == 0) 1f else -1f))
              )
            )
          }

          // Rayos de fotones entrelazados (Mandala de 8 o 12 espines cuánticos)
          val petals = 12
          for (i in 0 until petals) {
            val angle = Math.toRadians((rotationDeg + (i * (360f / petals))).toDouble())
            val px = centerX + (cos(angle) * maxRadius * 0.82f).toFloat()
            val py = centerY + (sin(angle) * maxRadius * 0.82f).toFloat()

            drawLine(
              brush = Brush.linearGradient(
                colors = listOf(primaryColor.copy(alpha = 0.9f), secondaryColor.copy(alpha = 0.2f)),
                start = Offset(centerX, centerY),
                end = Offset(px, py)
              ),
              start = Offset(centerX, centerY),
              end = Offset(px, py),
              strokeWidth = 1.8f,
              cap = StrokeCap.Round
            )

            // Nodos cúbit orbitales
            drawCircle(
              color = if (i % 2 == 0) primaryColor else secondaryColor,
              radius = 3.5f * glowScale,
              center = Offset(px, py)
            )
          }

          // Núcleo cuántico central (Singlet / Triplet State)
          drawCircle(
            color = primaryColor,
            radius = 16f * glowScale,
            center = Offset(centerX, centerY)
          )
          drawCircle(
            color = Color.White,
            radius = 6f,
            center = Offset(centerX, centerY)
          )

          // Punto de toque táctil interactivo (si el usuario está deslizando su dedo)
          state.freeTouchCoordinates?.let { (tx, ty) ->
            val touchPx = tx * size.width
            val touchPy = ty * size.height
            drawCircle(
              color = WarningGold.copy(alpha = 0.4f),
              radius = 24f,
              center = Offset(touchPx, touchPy)
            )
            drawCircle(
              color = WarningGold,
              radius = 6f,
              center = Offset(touchPx, touchPy)
            )
          }
        }

        // Overlay instructivo sobre el Canvas
        Box(
          modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(ObsidianSurface.copy(alpha = 0.85f))
            .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
          Text(
            text = "Toca o arrastra para modular la onda bio-cuántica libre",
            fontSize = 9.5.sp,
            color = CyberTextSecondary
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Telemetría cuántica en vivo
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Column {
          Text("ESTADO DE BELL", fontSize = 9.sp, color = CyberTextMuted, fontWeight = FontWeight.Bold)
          Text(intention.bellStateFormula, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = secondaryColor)
        }
        Column(horizontalAlignment = Alignment.End) {
          Text("FIDELIDAD CUÁNTICA", fontSize = 9.sp, color = CyberTextMuted, fontWeight = FontWeight.Bold)
          Text("${state.entanglementFidelity}% EPR Locked", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CyberEmerald)
        }
      }
    }
  }
}

/**
 * Paleta horizontal y tarjetas de intenciones pre-verbales puras.
 */
@Composable
private fun SynapseIntentionsPaletteSection(
  activeIntention: SynapseIntention,
  intentions: List<SynapseIntention>,
  onSelect: (SynapseIntention) -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("section_synapse_intentions")
  ) {
    Text(
      text = "VECTORES DE INTENCIÓN PRE-VERBAL",
      fontSize = 11.sp,
      fontWeight = FontWeight.Black,
      color = CyberCyan,
      letterSpacing = 1.sp
    )
    Text(
      text = "Elige la matriz de pensamiento puro a transmitir al nodo remoto:",
      fontSize = 11.sp,
      color = CyberTextSecondary
    )

    Spacer(modifier = Modifier.height(10.dp))

    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      items(intentions) { item ->
        val isSelected = item.id == activeIntention.id
        val cardBorderColor = if (isSelected) Color(item.colorPrimaryHex) else ObsidianBorder
        val cardBg = if (isSelected) ObsidianElevated else ObsidianSurface

        Card(
          modifier = Modifier
            .width(190.dp)
            .clickable { onSelect(item) }
            .testTag("intention_card_${item.shortTag}"),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = cardBg),
          border = BorderStroke(if (isSelected) 1.5.dp else 1.dp, cardBorderColor)
        ) {
          Column(
            modifier = Modifier.padding(12.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(8.dp))
                  .background(Color(item.colorPrimaryHex).copy(alpha = 0.2f))
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text(
                  text = item.shortTag,
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Black,
                  color = Color(item.colorPrimaryHex)
                )
              }

              if (isSelected) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color(item.colorPrimaryHex), modifier = Modifier.size(16.dp))
              }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = item.title,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = CyberTextPrimary,
              maxLines = 2,
              lineHeight = 15.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
              text = "${item.frequencyHz} Hz • ${item.category.label}",
              fontSize = 9.5.sp,
              color = CyberTextMuted
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = item.semanticDescription,
              fontSize = 10.sp,
              color = CyberTextSecondary,
              lineHeight = 13.sp,
              maxLines = 3
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Detalle de la percepción telepática en la conciencia
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(Color(activeIntention.colorPrimaryHex).copy(alpha = 0.08f))
        .border(1.dp, Color(activeIntention.colorPrimaryHex).copy(alpha = 0.3f), RoundedCornerShape(12.dp))
        .padding(12.dp)
    ) {
      Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Icon(
          Icons.Default.Lightbulb,
          contentDescription = null,
          tint = Color(activeIntention.colorPrimaryHex),
          modifier = Modifier.size(18.dp)
        )
        Column {
          Text(
            text = "PERCEPCIÓN DIRECTA EN CONCIENCIA:",
            fontSize = 9.5.sp,
            fontWeight = FontWeight.Black,
            color = Color(activeIntention.colorPrimaryHex)
          )
          Text(
            text = activeIntention.telepathicPerception,
            fontSize = 11.sp,
            color = CyberTextPrimary,
            lineHeight = 15.sp
          )
        }
      }
    }
  }
}

/**
 * Consola de teletransportación cuántica de la intención con selección de nodo y botones de acción.
 */
@Composable
private fun SynapseTransmissionConsole(
  state: SynapseQState,
  showNodeMenu: Boolean,
  onToggleNodeMenu: () -> Unit,
  onSelectNode: (String) -> Unit,
  onTransmit: () -> Unit,
  onReceiveSimulation: () -> Unit
) {
  val intention = state.activeIntention
  val primaryColor = Color(intention.colorPrimaryHex)

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("card_synapse_console"),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    shape = RoundedCornerShape(18.dp),
    border = BorderStroke(1.dp, ObsidianBorder)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Text(
        text = "CANAL DE TRANSMISIÓN DE PENSAMIENTO",
        fontSize = 10.sp,
        fontWeight = FontWeight.Black,
        color = CyberCyan,
        letterSpacing = 1.sp
      )

      Spacer(modifier = Modifier.height(8.dp))

      // Selector de Nodo Remoto
      Box(modifier = Modifier.fillMaxWidth()) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(ObsidianElevated)
            .border(1.dp, ObsidianBorder, RoundedCornerShape(12.dp))
            .clickable { onToggleNodeMenu() }
            .padding(horizontal = 12.dp, vertical = 10.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(Icons.Default.Sensors, contentDescription = null, tint = CyberCyan, modifier = Modifier.size(16.dp))
            Column {
              Text("Nodo Receptor Entrelazado", fontSize = 9.sp, color = CyberTextMuted)
              Text(state.targetNode, fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = CyberTextPrimary)
            }
          }
          Text("CAMBIAR", fontSize = 9.5.sp, fontWeight = FontWeight.Black, color = CyberCyan)
        }

        DropdownMenu(
          expanded = showNodeMenu,
          onDismissRequest = onToggleNodeMenu,
          modifier = Modifier.background(ObsidianElevated)
        ) {
          state.availableNodes.forEach { node ->
            DropdownMenuItem(
              text = { Text(node, fontSize = 11.5.sp, color = CyberTextPrimary) },
              onClick = { onSelectNode(node) }
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Barra de progreso si está transmitiendo
      if (state.isTransmitting) {
        Column(modifier = Modifier.fillMaxWidth()) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("Colapsando función de onda cuántica...", fontSize = 10.5.sp, color = primaryColor)
            Text("${(state.transmissionProgress * 100).toInt()}%", fontSize = 10.5.sp, color = primaryColor, fontWeight = FontWeight.Bold)
          }
          Spacer(modifier = Modifier.height(6.dp))
          LinearProgressIndicator(
            progress = { state.transmissionProgress },
            modifier = Modifier
              .fillMaxWidth()
              .height(6.dp)
              .clip(RoundedCornerShape(4.dp)),
            color = primaryColor,
            trackColor = ObsidianBorder
          )
          Spacer(modifier = Modifier.height(10.dp))
        }
      }

      // BOTÓN PRINCIPAL: COLAPSAR Y TRANSMITIR
      Button(
        onClick = onTransmit,
        enabled = !state.isTransmitting && !state.isReceiving,
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
          .shadow(10.dp, RoundedCornerShape(26.dp), spotColor = primaryColor)
          .testTag("button_transmit_synapse"),
        shape = RoundedCornerShape(26.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = primaryColor,
          contentColor = ObsidianBackground
        )
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          if (state.isTransmitting) {
            CircularProgressIndicator(modifier = Modifier.size(20.dp), color = ObsidianBackground, strokeWidth = 2.dp)
            Text("TRANSMITIENDO ENLACE EPR...", fontSize = 12.sp, fontWeight = FontWeight.Black)
          } else {
            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, modifier = Modifier.size(18.dp))
            Text(
              text = "COLAPSAR Y TRANSMITIR PENSAMIENTO",
              fontSize = 12.sp,
              fontWeight = FontWeight.Black,
              letterSpacing = 0.5.sp
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // BOTÓN SECUNDARIO: SIMULAR RECEPCIÓN DE ONDA
      OutlinedButton(
        onClick = onReceiveSimulation,
        enabled = !state.isTransmitting && !state.isReceiving,
        modifier = Modifier
          .fillMaxWidth()
          .height(42.dp)
          .testTag("button_simulate_receive_synapse"),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, QuantumPurple),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = QuantumPurple)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          if (state.isReceiving) {
            CircularProgressIndicator(modifier = Modifier.size(14.dp), color = QuantumPurple, strokeWidth = 2.dp)
            Text("SINTONIZANDO CÚBITS...", fontSize = 11.sp, fontWeight = FontWeight.Bold)
          } else {
            Icon(Icons.Default.CallReceived, contentDescription = null, modifier = Modifier.size(15.dp))
            Text("PROBAR RECEPCIÓN SINESTÉSICA ENTRANTE", fontSize = 10.5.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}

/**
 * Decodificador Neuro-Cognitivo Asistido por Inteligencia Artificial Gemini.
 */
@Composable
private fun SynapseNeuroAiDecoderCard(
  decodedInsight: String?,
  isSynthesizing: Boolean,
  activeIntention: SynapseIntention
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("card_synapse_neuro_decoder"),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    shape = RoundedCornerShape(18.dp),
    border = BorderStroke(1.dp, Brush.horizontalGradient(listOf(CyberCyan.copy(alpha = 0.4f), QuantumPurple.copy(alpha = 0.4f))))
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(Icons.Default.Psychology, contentDescription = null, tint = QuantumPurple, modifier = Modifier.size(18.dp))
          Text(
            text = "DECODIFICADOR NEURO-COGNITIVO",
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            color = CyberTextPrimary,
            letterSpacing = 0.5.sp
          )
        }
        Text("AI Synthesis", fontSize = 9.sp, color = CyberCyan, fontWeight = FontWeight.Bold)
      }

      Spacer(modifier = Modifier.height(8.dp))

      if (isSynthesizing) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          CircularProgressIndicator(modifier = Modifier.size(18.dp), color = CyberCyan, strokeWidth = 2.dp)
          Spacer(modifier = Modifier.width(8.dp))
          Text("Traduciendo vector cuántico multidimensional...", fontSize = 11.sp, color = CyberTextSecondary)
        }
      } else if (!decodedInsight.isNullOrBlank()) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(ObsidianBackground)
            .border(1.dp, ObsidianBorder, RoundedCornerShape(12.dp))
            .padding(12.dp)
        ) {
          Text(
            text = decodedInsight,
            fontSize = 11.sp,
            color = CyberTextPrimary,
            fontFamily = FontFamily.Monospace,
            lineHeight = 16.sp
          )
        }
      } else {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(ObsidianBackground)
            .border(1.dp, ObsidianBorder, RoundedCornerShape(12.dp))
            .padding(12.dp)
        ) {
          Text(
            text = "Modula un pensamiento o transmite un pulso cuántico. El decodificador traducirá la intención pura en conceptos semánticos tangibles.",
            fontSize = 10.5.sp,
            color = CyberTextMuted,
            lineHeight = 14.sp
          )
        }
      }
    }
  }
}

/**
 * Historial cronológico de pulsos de pensamiento transmitidos y recibidos.
 */
@Composable
private fun SynapsePulseHistorySection(
  pulses: List<SynapsePulse>,
  onDecodePulse: (SynapsePulse) -> Unit,
  onClear: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("section_synapse_history")
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "BITÁCORA DE RESONANCIA TELEPÁTICA",
        fontSize = 11.sp,
        fontWeight = FontWeight.Black,
        color = CyberCyan,
        letterSpacing = 1.sp
      )
      if (pulses.isNotEmpty()) {
        Text(
          text = "LIMPIAR",
          fontSize = 10.sp,
          color = ThreatCrimson,
          fontWeight = FontWeight.Bold,
          modifier = Modifier
            .clickable { onClear() }
            .testTag("button_clear_synapse_history")
        )
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    if (pulses.isEmpty()) {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, ObsidianBorder)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Icon(Icons.Default.ClearAll, contentDescription = null, tint = CyberTextMuted, modifier = Modifier.size(28.dp))
          Spacer(modifier = Modifier.height(6.dp))
          Text("No hay transmisiones telepáticas registradas.", fontSize = 11.sp, color = CyberTextMuted)
        }
      }
    } else {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        pulses.forEach { pulse ->
          val pColor = Color(pulse.intention.colorPrimaryHex)
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .testTag("card_synapse_pulse_${pulse.id}"),
            colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, pColor.copy(alpha = 0.35f))
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
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
                    if (pulse.isOutgoing) Icons.Default.CallMade else Icons.Default.CallReceived,
                    contentDescription = null,
                    tint = if (pulse.isOutgoing) CyberCyan else QuantumPurple,
                    modifier = Modifier.size(15.dp)
                  )
                  Text(
                    text = if (pulse.isOutgoing) "TRANSMISIÓN SALIENTE" else "RESONANCIA RECIBIDA",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    color = if (pulse.isOutgoing) CyberCyan else QuantumPurple
                  )
                }
                Text(pulse.timestamp, fontSize = 9.5.sp, color = CyberTextMuted)
              }

              Spacer(modifier = Modifier.height(6.dp))

              Text(
                text = pulse.intention.title,
                fontSize = 12.5.sp,
                fontWeight = FontWeight.Bold,
                color = CyberTextPrimary
              )

              Spacer(modifier = Modifier.height(2.dp))

              Text(
                text = "${pulse.intention.frequencyHz} Hz • ${pulse.bellStatePurity}",
                fontSize = 9.5.sp,
                fontFamily = FontFamily.Monospace,
                color = pColor
              )

              Spacer(modifier = Modifier.height(6.dp))

              Text(
                text = pulse.neuroAiDecodedMeaning,
                fontSize = 10.5.sp,
                color = CyberTextSecondary,
                lineHeight = 14.sp
              )

              Spacer(modifier = Modifier.height(8.dp))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "PQC Hash: ${pulse.pqcHash}",
                  fontSize = 8.5.sp,
                  fontFamily = FontFamily.Monospace,
                  color = CyberTextMuted
                )

                Text(
                  text = "DECODIFICAR CON IA",
                  fontSize = 9.5.sp,
                  fontWeight = FontWeight.Black,
                  color = CyberCyan,
                  modifier = Modifier.clickable { onDecodePulse(pulse) }
                )
              }
            }
          }
        }
      }
    }
  }
}

// -----------------------------------------------------------------------------
// Componente: Barra de Coherencia Mental y Modo Broadcast Multi-Nodo
// -----------------------------------------------------------------------------

@Composable
private fun SynapseCoherenceAndBroadcastBar(
  state: SynapseQState,
  onBoostCoherence: () -> Unit,
  onToggleBroadcast: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("card_synapse_coherence_broadcast"),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp, vertical = 10.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Coherencia Mental
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
          .clickable { onBoostCoherence() }
          .padding(4.dp)
      ) {
        Box(
          modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(CyberEmerald.copy(alpha = 0.15f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(Icons.Default.Fingerprint, contentDescription = null, tint = CyberEmerald, modifier = Modifier.size(18.dp))
        }
        Column {
          Text("COHERENCIA MENTAL", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = CyberTextMuted)
          Text("${String.format(java.util.Locale.US, "%.1f", state.mentalCoherenceScore)}% Óptima (Toca para Calibrar)", fontSize = 11.sp, fontWeight = FontWeight.Black, color = CyberEmerald)
        }
      }

      // Modo Broadcast Multi-Nodo
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Column(horizontalAlignment = Alignment.End) {
          Text("BROADCAST MULTI-NODO", fontSize = 8.5.sp, fontWeight = FontWeight.Bold, color = if (state.isBroadcastMode) QuantumPurple else CyberTextMuted)
          Text(if (state.isBroadcastMode) "Enjambre Completo" else "Nodo Único", fontSize = 10.sp, color = CyberTextSecondary)
        }
        Switch(
          checked = state.isBroadcastMode,
          onCheckedChange = { onToggleBroadcast() },
          colors = SwitchDefaults.colors(
            checkedThumbColor = ObsidianBackground,
            checkedTrackColor = QuantumPurple,
            uncheckedThumbColor = CyberTextMuted,
            uncheckedTrackColor = ObsidianBorder
          ),
          modifier = Modifier.testTag("switch_synapse_broadcast")
        )
      }
    }
  }
}

// -----------------------------------------------------------------------------
// Componente: Sintetizador de Intención Personalizada con IA
// -----------------------------------------------------------------------------

@Composable
private fun SynapseCustomThoughtComposer(
  state: SynapseQState,
  onToggleComposer: () -> Unit,
  onUpdateThought: (String) -> Unit,
  onSynthesize: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("card_synapse_custom_composer"),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = androidx.compose.foundation.BorderStroke(1.dp, Brush.horizontalGradient(listOf(QuantumPurple.copy(alpha = 0.6f), CyberCyan.copy(alpha = 0.6f))))
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
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = CyberCyan, modifier = Modifier.size(18.dp))
          Text("SINTETIZADOR DE INTENCIÓN LIBRE", fontSize = 11.sp, fontWeight = FontWeight.Black, color = CyberCyan)
        }
        Text(
          text = if (state.customComposerOpen) "CERRAR" else "CREAR NUEVA",
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          color = WarningGold,
          modifier = Modifier
            .clickable { onToggleComposer() }
            .padding(4.dp)
        )
      }

      if (state.customComposerOpen) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
          text = "Escribe cualquier directiva o pensamiento en tus propias palabras. El Enjambre IA calculará matemáticamente su frecuencia armónica (Hz), estado Bell y firma háptica para transmitirla sin palabras:",
          fontSize = 11.sp,
          color = CyberTextSecondary,
          lineHeight = 15.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = state.customThoughtInput,
          onValueChange = onUpdateThought,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_custom_thought"),
          placeholder = { Text("Ej: Blindaje absoluto de la familia y paz incondicional...", fontSize = 11.sp, color = CyberTextMuted) },
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = QuantumPurple,
            unfocusedBorderColor = ObsidianBorder,
            focusedTextColor = CyberTextPrimary,
            unfocusedTextColor = CyberTextPrimary,
            cursorColor = CyberCyan
          ),
          shape = RoundedCornerShape(10.dp),
          maxLines = 3
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
          onClick = onSynthesize,
          enabled = state.customThoughtInput.isNotBlank() && !state.isSynthesizingAi,
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = QuantumPurple, contentColor = Color.White),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("btn_synthesize_custom_thought")
        ) {
          if (state.isSynthesizingAi) {
            CircularProgressIndicator(modifier = Modifier.size(14.dp), strokeWidth = 2.dp, color = Color.White)
            Spacer(modifier = Modifier.width(6.dp))
            Text("CALCULANDO ONDA CUÁNTICA CON IA...", fontSize = 10.5.sp, fontWeight = FontWeight.Bold)
          } else {
            Icon(Icons.Default.Bolt, contentDescription = null, modifier = Modifier.size(15.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("SINTETIZAR ONDA & AGREGAR A PALETA", fontSize = 10.5.sp, fontWeight = FontWeight.Black)
          }
        }
      }
    }
  }
}
