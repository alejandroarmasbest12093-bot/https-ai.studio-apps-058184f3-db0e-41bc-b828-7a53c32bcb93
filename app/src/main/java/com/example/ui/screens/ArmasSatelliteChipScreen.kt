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
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.CellTower
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContactPhone
import androidx.compose.material.icons.filled.Dialpad
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.SatelliteAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SimCard
import androidx.compose.material.icons.filled.Sos
import androidx.compose.material.icons.filled.Stream
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import android.content.Intent
import android.net.Uri
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.PhoneForwarded
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import com.example.ui.components.SatelliteMessagingSection
import com.example.ui.components.SatelliteInternetSection
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.model.ArmasCountryDirectory
import com.example.model.CountryDialInfo
import com.example.model.ArmasSatelliteCallLog
import com.example.model.ArmasSatelliteCallSession
import com.example.model.ArmasSatelliteChipState
import com.example.model.ArmasSatelliteContact
import com.example.model.SatelliteCallState
import com.example.model.SatelliteCallType
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberCyanMuted
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberEmeraldDark
import com.example.ui.theme.CyberTextMuted
import com.example.ui.theme.CyberTextPrimary
import com.example.ui.theme.CyberTextSecondary
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianElevated
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.QuantumPurple
import com.example.ui.theme.ThreatCrimson
import com.example.ui.theme.WarningGold
import com.example.viewmodel.CyberUiState

private val GoldSimContact = Color(0xFFFFD166)
private val GoldSimDark = Color(0xFFB58D20)
private val OrbitBlue = Color(0xFF00B4D8)

@Composable
fun ArmasSatelliteChipScreen(
  state: CyberUiState,
  onDialpadInput: (String) -> Unit,
  onDialpadBackspace: () -> Unit,
  onDialpadClear: () -> Unit,
  onSetDialpadNumber: (String) -> Unit,
  onSelectCountry: (CountryDialInfo) -> Unit = {},
  onSetMicPermissionGranted: (Boolean) -> Unit = {},
  onStartCall: (String, String) -> Unit,
  onEndCall: () -> Unit,
  onToggleMute: () -> Unit,
  onToggleSpeaker: () -> Unit,
  onToggleAntiRecording: () -> Unit,
  onToggleBypassTowers: () -> Unit,
  onToggleLaserMesh: () -> Unit,
  onToggleChipActivation: () -> Unit,
  onTriggerSos: () -> Unit,
  onCancelSos: () -> Unit,
  onClearHistory: () -> Unit,
  onSendSatelliteMessage: (recipient: String, content: String, viaNativeSms: Boolean, context: android.content.Context) -> Unit = { _, _, _, _ -> },
  onUpdateDraftRecipient: (String) -> Unit = {},
  onUpdateDraftMessage: (String) -> Unit = {},
  onSendEmergencySosMessage: () -> Unit = {},
  onClearMessages: () -> Unit = {},
  onToggleSatelliteInternet: (Boolean?) -> Unit = {},
  onRunSpeedTest: () -> Unit = {},
  onToggleVpn: () -> Unit = {},
  onSetDnsProvider: (String) -> Unit = {},
  onNavigateToSynapseQ: () -> Unit = {}
) {
  val satState = state.satelliteChipState
  var selectedTab by remember { mutableIntStateOf(0) } // 0: Teclado, 1: Mensajes, 2: Internet, 3: Contactos VIP, 4: Historial
  var showCountryPicker by remember { mutableStateOf(false) }

  val context = LocalContext.current

  val micPermissionLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestPermission()
  ) { isGranted ->
    onSetMicPermissionGranted(isGranted)
  }

  LaunchedEffect(Unit) {
    val granted = ContextCompat.checkSelfPermission(
      context,
      Manifest.permission.RECORD_AUDIO
    ) == PackageManager.PERMISSION_GRANTED
    onSetMicPermissionGranted(granted)
  }

  if (showCountryPicker) {
    CountryPickerDialog(
      currentCountry = satState.selectedCountryDial,
      onSelectCountry = { country ->
        onSelectCountry(country)
        showCountryPicker = false
      },
      onDismiss = { showCountryPicker = false }
    )
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(ObsidianBackground)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item { Spacer(modifier = Modifier.height(6.dp)) }

    // Header Soberano
    item {
      HeaderSatelliteSection(satState = satState, onToggleActivation = onToggleChipActivation)
    }

    // Banner de Permiso de Micrófono si no ha sido otorgado
    if (!satState.micPermissionGranted) {
      item {
        MicrophonePermissionBanner(
          onRequestPermission = {
            micPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
          }
        )
      }
    }

    // Si hay una llamada activa o conectando, mostrar la pantalla de llamada en vivo
    satState.activeCall?.let { activeCall ->
      item {
        ActiveSatelliteCallBanner(
          call = activeCall,
          satState = satState,
          onEndCall = onEndCall,
          onToggleMute = onToggleMute,
          onToggleSpeaker = onToggleSpeaker,
          onToggleAntiRecording = onToggleAntiRecording
        )
      }
    }

    // Tarjeta Física / Cuántica del CHIP SATELITAL ARMAS
    item {
      ArmasQuantumSimCardVisual(satState = satState)
    }

    // Modos Soberanos de Telecomunicación (Bypass de operadoras)
    item {
      SovereignTelecomControlsCard(
        satState = satState,
        onToggleBypassTowers = onToggleBypassTowers,
        onToggleLaserMesh = onToggleLaserMesh
      )
    }

    // Radar de Pases Orbitales de la Constelación Armas LEO
    item {
      OrbitalSatellitePassesRadarCard(satState = satState)
    }

    // Acceso directo a Comunicación Telepática Cuántica Synapse-Q
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("card_satellite_to_synapse_q"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, Brush.horizontalGradient(listOf(QuantumPurple, CyberCyan)))
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Box(
              modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(QuantumPurple.copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(Icons.Default.Stream, contentDescription = null, tint = CyberCyan, modifier = Modifier.size(18.dp))
            }
            Column {
              Text("NUEVA FORMA DE COMUNICARSE", fontSize = 9.sp, fontWeight = FontWeight.Black, color = CyberCyan)
              Text("SYNAPSE-Q™ Telepatía Cuántica", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CyberTextPrimary)
              Text("Transmisión de pensamiento y háptica pura", fontSize = 9.5.sp, color = CyberTextMuted)
            }
          }
          Button(
            onClick = onNavigateToSynapseQ,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = QuantumPurple, contentColor = Color.White),
            modifier = Modifier.testTag("btn_satellite_go_to_synapse")
          ) {
            Text("ABRIR", fontSize = 10.5.sp, fontWeight = FontWeight.Black)
          }
        }
      }
    }

    // Botón / Banner de SOS Satelital Armas
    item {
      SatelliteSosBanner(
        satState = satState,
        onTriggerSos = onTriggerSos,
        onCancelSos = onCancelSos
      )
    }

    // Selector de Pestañas (Teclado / Mensajes / Internet / Líneas VIP / Historial)
    item {
      ScrollableTabRow(
        selectedTabIndex = selectedTab,
        containerColor = ObsidianSurface,
        contentColor = CyberCyan,
        edgePadding = 8.dp,
        indicator = { tabPositions ->
          TabRowDefaults.SecondaryIndicator(
            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
            color = CyberCyan
          )
        },
        divider = { HorizontalDivider(color = ObsidianBorder) }
      ) {
        Tab(
          selected = selectedTab == 0,
          onClick = { selectedTab = 0 },
          text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Dialpad, contentDescription = null, modifier = Modifier.size(15.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Teclado / Llamadas", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }
        )
        Tab(
          selected = selectedTab == 1,
          onClick = { selectedTab = 1 },
          text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Message, contentDescription = null, modifier = Modifier.size(15.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Mensajería Satelital", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }
        )
        Tab(
          selected = selectedTab == 2,
          onClick = { selectedTab = 2 },
          text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Public, contentDescription = null, modifier = Modifier.size(15.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Internet Satelital", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }
        )
        Tab(
          selected = selectedTab == 3,
          onClick = { selectedTab = 3 },
          text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.ContactPhone, contentDescription = null, modifier = Modifier.size(15.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Líneas VIP", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }
        )
        Tab(
          selected = selectedTab == 4,
          onClick = { selectedTab = 4 },
          text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.History, contentDescription = null, modifier = Modifier.size(15.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Historial", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }
        )
      }
    }

    when (selectedTab) {
      0 -> {
        // Teclado telefónico satelital y marcador Android
        item {
          SatelliteDialpadSection(
            dialpadNumber = satState.dialpadNumber,
            selectedCountry = satState.selectedCountryDial,
            onInput = onDialpadInput,
            onBackspace = onDialpadBackspace,
            onClear = onDialpadClear,
            onOpenCountryPicker = { showCountryPicker = true },
            onSelectCountry = onSelectCountry,
            onCall = { onStartCall(satState.dialpadNumber, "") }
          )
        }
      }
      1 -> {
        // Mensajería Satelital Direct-to-Cell & SMS
        item {
          SatelliteMessagingSection(
            satState = satState,
            onSendSatelliteMessage = onSendSatelliteMessage,
            onUpdateDraftRecipient = onUpdateDraftRecipient,
            onUpdateDraftMessage = onUpdateDraftMessage,
            onSendEmergencySosMessage = onSendEmergencySosMessage,
            onClearMessages = onClearMessages
          )
        }
      }
      2 -> {
        // Internet Satelital LEO Direct-to-Cell & Speedtest
        item {
          SatelliteInternetSection(
            satState = satState,
            onToggleInternet = onToggleSatelliteInternet,
            onRunSpeedTest = onRunSpeedTest,
            onToggleVpn = onToggleVpn,
            onSetDnsProvider = onSetDnsProvider
          )
        }
      }
      3 -> {
        // Directorio de Líneas Soberanas VIP
        item {
          Text(
            text = "LÍNEAS SOBERANAS DIRECTAS (MARCACIÓN DE UN TOQUE)",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = CyberCyan,
            letterSpacing = 1.sp
          )
        }
        items(satState.defaultContacts) { contact ->
          SovereignContactItem(
            contact = contact,
            onCall = {
              onSetDialpadNumber(contact.satelliteNumber)
              onStartCall(contact.satelliteNumber, contact.name)
            }
          )
        }
      }
      4 -> {
        // Historial de llamadas y telemetría de satélites
        item {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "REGISTRO DE LLAMADAS SATELITALES",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = CyberCyan,
              letterSpacing = 1.sp
            )
            if (satState.callHistory.isNotEmpty()) {
              Text(
                text = "Limpiar",
                fontSize = 11.sp,
                color = ThreatCrimson,
                modifier = Modifier
                  .clickable { onClearHistory() }
                  .padding(4.dp)
              )
            }
          }
        }

        if (satState.callHistory.isEmpty()) {
          item {
            Card(
              modifier = Modifier.fillMaxWidth(),
              colors = CardDefaults.cardColors(containerColor = ObsidianSurface)
            ) {
              Text(
                text = "No hay registros de llamadas recientes en la bóveda satelital.",
                fontSize = 12.sp,
                color = CyberTextMuted,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
              )
            }
          }
        } else {
          items(satState.callHistory) { log ->
            SatelliteCallHistoryItem(log = log)
          }
        }

        // Telemetría espacial en vivo
        item {
          SatelliteTelemetryConsole(satState = satState)
        }
      }
    }

    item { Spacer(modifier = Modifier.height(30.dp)) }
  }
}

// -----------------------------------------------------------------------------
// Componente: Encabezado de la Pantalla Satelital
// -----------------------------------------------------------------------------

@Composable
private fun HeaderSatelliteSection(
  satState: ArmasSatelliteChipState,
  onToggleActivation: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("satellite_header_card"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = BorderStroke(1.dp, Brush.horizontalGradient(listOf(WarningGold.copy(alpha = 0.8f), CyberCyan)))
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
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
              .size(42.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(WarningGold.copy(alpha = 0.15f))
              .border(1.dp, WarningGold, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.SatelliteAlt,
              contentDescription = null,
              tint = WarningGold,
              modifier = Modifier.size(24.dp)
            )
          }
          Column {
            Text(
              text = "CHIP SATELITAL ARMAS",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = CyberTextPrimary,
              letterSpacing = 0.8.sp
            )
            Text(
              text = "100% Satelital • Direct-to-Cell • Cero Operadoras",
              fontSize = 11.sp,
              color = CyberCyan
            )
          }
        }

        Switch(
          checked = satState.isChipActivated,
          onCheckedChange = { onToggleActivation() },
          colors = SwitchDefaults.colors(
            checkedThumbColor = ObsidianBackground,
            checkedTrackColor = CyberEmerald,
            uncheckedThumbColor = CyberTextMuted,
            uncheckedTrackColor = ObsidianBorder
          ),
          modifier = Modifier.testTag("switch_chip_activation")
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Barra de telemetría de satélite en órbita
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
          Box(
            modifier = Modifier
              .size(8.dp)
              .clip(CircleShape)
              .background(if (satState.isChipActivated) CyberEmerald else ThreatCrimson)
          )
          Text(
            text = if (satState.isChipActivated) "ENLACE LEO ACTIVO: ${satState.activeSatelliteOrbit}" else "MODO STANDBY",
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            color = if (satState.isChipActivated) CyberEmerald else ThreatCrimson
          )
        }

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Icon(Icons.Default.Sensors, contentDescription = null, tint = CyberCyan, modifier = Modifier.size(13.dp))
          Text(
            text = "${satState.signalDbM} dBm (Óptimo)",
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
            color = CyberCyan
          )
        }
      }
    }
  }
}

// -----------------------------------------------------------------------------
// Componente: Tarjeta Visual Holográfica "ARMAS SATELLITE CHIP"
// -----------------------------------------------------------------------------

@Composable
private fun ArmasQuantumSimCardVisual(satState: ArmasSatelliteChipState) {
  val infiniteTransition = rememberInfiniteTransition(label = "HoloTransition")
  val shimmerOffset by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1000f,
    animationSpec = infiniteRepeatable(
      animation = tween(4000, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "Shimmer"
  )

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .shadow(12.dp, RoundedCornerShape(20.dp), spotColor = WarningGold.copy(alpha = 0.4f))
      .testTag("armas_sim_card_visual"),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = BorderStroke(1.5.dp, Brush.linearGradient(
      listOf(GoldSimContact, OrbitBlue, WarningGold, CyberCyan)
    ))
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          Brush.radialGradient(
            colors = listOf(ObsidianSurfaceVariant, ObsidianSurface, ObsidianBackground),
            radius = 600f
          )
        )
        .padding(18.dp)
    ) {
      Column {
        // Fila Superior: Marca Armas y Tipo de Chip
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(WarningGold),
              contentAlignment = Alignment.Center
            ) {
              Text("A", color = ObsidianBackground, fontWeight = FontWeight.Black, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = satState.chipBrand,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                color = WarningGold,
                letterSpacing = 1.sp
              )
              Text(
                text = satState.chipEdition,
                fontSize = 9.sp,
                color = CyberCyan
              )
            }
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(CyberEmeraldDark.copy(alpha = 0.3f))
              .border(1.dp, CyberEmerald, RoundedCornerShape(6.dp))
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = "DIRECT-TO-CELL LEO",
              fontSize = 8.sp,
              fontWeight = FontWeight.Bold,
              color = CyberEmerald
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Gráfico Central: Pines Metálicos Dorados del Chip SIM Cuántico
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Microchip SIM contact pads dibujados
          Box(
            modifier = Modifier
              .size(width = 68.dp, height = 54.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(
                Brush.linearGradient(
                  colors = listOf(GoldSimContact, GoldSimDark, GoldSimContact)
                )
              )
              .border(1.dp, WarningGold, RoundedCornerShape(8.dp))
          ) {
            // Líneas grabadas en el microchip SIM
            Canvas(modifier = Modifier.fillMaxSize()) {
              val w = size.width
              val h = size.height
              val strokeWidth = 2f
              val linePaint = Color(0xFF5A4400)

              // Trazos internos geométricos de los contactos del chip
              drawRoundRect(
                color = linePaint,
                topLeft = Offset(w * 0.28f, h * 0.22f),
                size = Size(w * 0.44f, h * 0.56f),
                cornerRadius = CornerRadius(4f, 4f),
                style = Stroke(width = strokeWidth)
              )
              // Línea horizontal central
              drawLine(linePaint, Offset(0f, h * 0.5f), Offset(w * 0.28f, h * 0.5f), strokeWidth = strokeWidth)
              drawLine(linePaint, Offset(w * 0.72f, h * 0.5f), Offset(w, h * 0.5f), strokeWidth = strokeWidth)
              // Líneas verticales
              drawLine(linePaint, Offset(w * 0.5f, 0f), Offset(w * 0.5f, h * 0.22f), strokeWidth = strokeWidth)
              drawLine(linePaint, Offset(w * 0.5f, h * 0.78f), Offset(w * 0.5f, h), strokeWidth = strokeWidth)
            }
          }

          // Íconos de señal cuántica satelital
          Column(horizontalAlignment = Alignment.End) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.CellTower, contentDescription = null, tint = ThreatCrimson, modifier = Modifier.size(14.dp))
              Text(" 0% Terrestre", fontSize = 10.sp, color = ThreatCrimson, fontWeight = FontWeight.Bold)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.SatelliteAlt, contentDescription = null, tint = CyberEmerald, modifier = Modifier.size(14.dp))
              Text(" 100% Satélite Armas", fontSize = 10.sp, color = CyberEmerald, fontWeight = FontWeight.Bold)
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Número telefónico satelital del chip
        Text(
          text = satState.chipPhoneNumber,
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace,
          color = CyberTextPrimary,
          letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Datos del Titular e Identificadores Satelitales
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column {
            Text("TITULAR SOBERANO", fontSize = 8.sp, color = CyberTextMuted, fontWeight = FontWeight.Bold)
            Text(satState.holderName, fontSize = 12.sp, color = CyberTextPrimary, fontWeight = FontWeight.Bold)
          }

          Column(horizontalAlignment = Alignment.End) {
            Text("ICCID SATELITAL", fontSize = 8.sp, color = CyberTextMuted, fontWeight = FontWeight.Bold)
            Text(satState.iccid, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = CyberCyan)
          }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "IMSI: ${satState.imsi}",
            fontSize = 9.sp,
            fontFamily = FontFamily.Monospace,
            color = CyberTextMuted
          )
          Text(
            text = "SEGURIDAD: Kyber-1024 PQC",
            fontSize = 9.sp,
            fontFamily = FontFamily.Monospace,
            color = QuantumPurple
          )
        }
      }
    }
  }
}

// -----------------------------------------------------------------------------
// Componente: Pantalla de Llamada Satelital en Vivo (Activa o Conectando)
// -----------------------------------------------------------------------------

@Composable
private fun ActiveSatelliteCallBanner(
  call: ArmasSatelliteCallSession,
  satState: ArmasSatelliteChipState,
  onEndCall: () -> Unit,
  onToggleMute: () -> Unit,
  onToggleSpeaker: () -> Unit,
  onToggleAntiRecording: () -> Unit
) {
  val minutes = call.durationSeconds / 60
  val seconds = call.durationSeconds % 60
  val timeFormatted = String.format("%02d:%02d", minutes, seconds)

  val infinitePulse = rememberInfiniteTransition(label = "pulse")
  val pulseAlpha by infinitePulse.animateFloat(
    initialValue = 0.3f,
    targetValue = 0.9f,
    animationSpec = infiniteRepeatable(
      animation = tween(800, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "AlphaPulse"
  )

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .shadow(16.dp, RoundedCornerShape(18.dp), spotColor = CyberEmerald.copy(alpha = 0.5f))
      .testTag("active_satellite_call_card"),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = BorderStroke(2.dp, Brush.horizontalGradient(listOf(CyberEmerald, CyberCyan, WarningGold)))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Estado de Enlace
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Box(
          modifier = Modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(
              when (call.callState) {
                SatelliteCallState.CONNECTING -> WarningGold.copy(alpha = pulseAlpha)
                SatelliteCallState.ORBITAL_LOCK -> CyberCyan.copy(alpha = pulseAlpha)
                SatelliteCallState.ACTIVE -> CyberEmerald.copy(alpha = pulseAlpha)
                SatelliteCallState.TERMINATING -> ThreatCrimson
              }
            )
        )
        Text(
          text = when (call.callState) {
            SatelliteCallState.CONNECTING -> "BUSCANDO HAZ DIRECT-TO-CELL..."
            SatelliteCallState.ORBITAL_LOCK -> "BLOQUEO ORBITAL FIJADO (LOCK)"
            SatelliteCallState.ACTIVE -> "LLAMADA SATELITAL ACTIVA 🛰️"
            SatelliteCallState.TERMINATING -> "CERRANDO ENLACE..."
          },
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace,
          color = when (call.callState) {
            SatelliteCallState.CONNECTING -> WarningGold
            SatelliteCallState.ORBITAL_LOCK -> CyberCyan
            SatelliteCallState.ACTIVE -> CyberEmerald
            SatelliteCallState.TERMINATING -> ThreatCrimson
          }
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Badge de País de Destino Soberano
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
          .clip(RoundedCornerShape(12.dp))
          .background(ObsidianElevated)
          .border(1.dp, ObsidianBorder, RoundedCornerShape(12.dp))
          .padding(horizontal = 10.dp, vertical = 4.dp)
      ) {
        Text(call.destinationFlagEmoji, fontSize = 16.sp)
        Text(
          text = "${call.destinationCountry.uppercase()} • ENLACE ORBITAL ARMAS",
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          color = CyberCyan
        )
      }

      Spacer(modifier = Modifier.height(6.dp))

      // Nombre y número del destinatario
      Text(
        text = call.targetName,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = CyberTextPrimary,
        textAlign = TextAlign.Center
      )
      Text(
        text = call.targetNumber,
        fontSize = 14.sp,
        fontFamily = FontFamily.Monospace,
        color = CyberCyan
      )

      Spacer(modifier = Modifier.height(8.dp))

      // Cronómetro en vivo
      Text(
        text = timeFormatted,
        fontSize = 26.sp,
        fontWeight = FontWeight.Black,
        fontFamily = FontFamily.Monospace,
        color = CyberEmerald
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Animación de Onda de Espectro de Audio Satelital en Vivo (Micrófono Real)
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(44.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(ObsidianElevated),
        contentAlignment = Alignment.Center
      ) {
        Row(
          horizontalArrangement = Arrangement.spacedBy(4.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          val spectrumBars = if (satState.liveSpectrumBars.isNotEmpty()) satState.liveSpectrumBars else call.audioWaveformBars
          val extendedBars = (spectrumBars + spectrumBars.reversed() + spectrumBars.take(8)).take(24)

          extendedBars.forEach { level ->
            val barHeight = if (call.callState == SatelliteCallState.ACTIVE && !call.isMuted) {
              (level * 36f + 4f).coerceIn(4f, 40f).dp
            } else {
              4.dp
            }
            Box(
              modifier = Modifier
                .width(4.dp)
                .height(barHeight)
                .clip(RoundedCornerShape(2.dp))
                .background(
                  brush = if (call.isMuted) {
                    Brush.verticalGradient(listOf(ThreatCrimson.copy(alpha = 0.5f), ThreatCrimson))
                  } else {
                    Brush.verticalGradient(listOf(CyberCyan, CyberEmerald))
                  }
                )
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Estado y decibelios del Micrófono y Altavoz
      Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = if (call.isMuted) Icons.Default.MicOff else Icons.Default.Mic,
            contentDescription = null,
            tint = if (call.isMuted) ThreatCrimson else CyberEmerald,
            modifier = Modifier.size(13.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = if (call.isMuted) "MIC SILENCIADO (MUTE)"
                   else String.format(java.util.Locale.getDefault(), "MIC EN VIVO: %.1f dBFS", satState.liveMicLevelDb),
            fontSize = 9.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            color = if (call.isMuted) ThreatCrimson else CyberEmerald
          )
        }

        Text(
          text = if (call.isSpeakerOn) "🔊 ALTAVOZ ACTIVADO" else "📞 AURICULAR PRIVADO",
          fontSize = 9.sp,
          fontFamily = FontFamily.Monospace,
          color = if (call.isSpeakerOn) CyberCyan else CyberTextMuted
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Parámetros técnicos del transpondedor satelital
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text("SATÉLITE", fontSize = 8.sp, color = CyberTextMuted)
          Text(call.satelliteTransponder.take(13), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = CyberTextPrimary)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text("DOPPLER", fontSize = 8.sp, color = CyberTextMuted)
          Text(String.format("%.2f kHz", call.dopplerCorrectionKHz), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = CyberCyan)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text("LATENCIA LEO", fontSize = 8.sp, color = CyberTextMuted)
          Text("${call.orbitalPingMs} ms", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = CyberEmerald)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text("CIFRADO", fontSize = 8.sp, color = CyberTextMuted)
          Text("Kyber-1024", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = QuantumPurple)
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Botones de control de llamada: Mute, Speaker, Ruido Cuántico, Colgar
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Silenciar Micrófono
        IconButton(
          onClick = onToggleMute,
          modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(if (call.isMuted) ThreatCrimson.copy(alpha = 0.2f) else ObsidianElevated)
            .border(1.dp, if (call.isMuted) ThreatCrimson else ObsidianBorder, CircleShape)
            .testTag("button_call_mute")
        ) {
          Icon(
            imageVector = if (call.isMuted) Icons.Default.MicOff else Icons.Default.Mic,
            contentDescription = "Mute",
            tint = if (call.isMuted) ThreatCrimson else CyberTextPrimary
          )
        }

        // Altavoz Satelital
        IconButton(
          onClick = onToggleSpeaker,
          modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(if (call.isSpeakerOn) CyberCyan.copy(alpha = 0.2f) else ObsidianElevated)
            .border(1.dp, if (call.isSpeakerOn) CyberCyan else ObsidianBorder, CircleShape)
            .testTag("button_call_speaker")
        ) {
          Icon(
            imageVector = if (call.isSpeakerOn) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
            contentDescription = "Speaker",
            tint = if (call.isSpeakerOn) CyberCyan else CyberTextPrimary
          )
        }

        // Ruido Cuántico Anti-Grabación
        IconButton(
          onClick = onToggleAntiRecording,
          modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(if (call.isAntiRecordingNoiseActive) QuantumPurple.copy(alpha = 0.2f) else ObsidianElevated)
            .border(1.dp, if (call.isAntiRecordingNoiseActive) QuantumPurple else ObsidianBorder, CircleShape)
            .testTag("button_call_anti_recording")
        ) {
          Icon(
            imageVector = Icons.Default.Shield,
            contentDescription = "Anti-Recording Noise",
            tint = if (call.isAntiRecordingNoiseActive) QuantumPurple else CyberTextMuted
          )
        }

        // Botón Colgar Llamada
        IconButton(
          onClick = onEndCall,
          modifier = Modifier
            .size(58.dp)
            .clip(CircleShape)
            .background(ThreatCrimson)
            .shadow(8.dp, CircleShape)
            .testTag("button_end_satellite_call")
        ) {
          Icon(
            imageVector = Icons.Default.CallEnd,
            contentDescription = "Colgar",
            tint = Color.White,
            modifier = Modifier.size(28.dp)
          )
        }
      }
    }
  }
}

// -----------------------------------------------------------------------------
// Componente: Controles de Telecomunicación Soberana (Bypass de Operadoras)
// -----------------------------------------------------------------------------

@Composable
private fun SovereignTelecomControlsCard(
  satState: ArmasSatelliteChipState,
  onToggleBypassTowers: () -> Unit,
  onToggleLaserMesh: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("sovereign_telecom_card"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = BorderStroke(1.dp, ObsidianBorder)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(
        text = "MODO DE TELECOMUNICACIÓN 100% INDEPENDIENTE",
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = WarningGold,
        letterSpacing = 0.8.sp
      )
      Spacer(modifier = Modifier.height(12.dp))

      // Switch: Bypass Total de Torres Celulares y Operadoras
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.CellTower, contentDescription = null, tint = ThreatCrimson, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Bypass Total de Operadoras Terrestres",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = CyberTextPrimary
            )
          }
          Text(
            text = "Ignora antenas 2G/3G/4G/5G de Claro, Movistar, AT&T. Inmune a interceptores IMSI-Catcher y pinchazos gubernamentales.",
            fontSize = 10.sp,
            color = CyberTextSecondary
          )
        }
        Switch(
          checked = satState.bypassTerrestrialTowers,
          onCheckedChange = { onToggleBypassTowers() },
          colors = SwitchDefaults.colors(
            checkedThumbColor = ObsidianBackground,
            checkedTrackColor = CyberEmerald
          ),
          modifier = Modifier.testTag("switch_bypass_terrestrial")
        )
      }

      HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = ObsidianBorder)

      // Switch: Enlace Óptico Láser Inter-Satelital (ISL)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Public, contentDescription = null, tint = CyberCyan, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Malla Láser Óptica Inter-Satelital (ISL)",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = CyberTextPrimary
            )
          }
          Text(
            text = "El tráfico de voz viaja en el vacío espacial de satélite a satélite a 299,792 km/s sin tocar estaciones en tierra.",
            fontSize = 10.sp,
            color = CyberTextSecondary
          )
        }
        Switch(
          checked = satState.laserInterSatelliteMeshActive,
          onCheckedChange = { onToggleLaserMesh() },
          colors = SwitchDefaults.colors(
            checkedThumbColor = ObsidianBackground,
            checkedTrackColor = CyberCyan
          ),
          modifier = Modifier.testTag("switch_laser_mesh")
        )
      }
    }
  }
}

// -----------------------------------------------------------------------------
// Componente: Teclado Numérico Satelital (Dialpad)
// -----------------------------------------------------------------------------

@Composable
private fun SatelliteDialpadSection(
  dialpadNumber: String,
  selectedCountry: CountryDialInfo,
  onInput: (String) -> Unit,
  onBackspace: () -> Unit,
  onClear: () -> Unit,
  onOpenCountryPicker: () -> Unit,
  onSelectCountry: (CountryDialInfo) -> Unit,
  onCall: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("satellite_dialpad_card"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = BorderStroke(1.dp, ObsidianBorder)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Selector principal de País
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(10.dp))
          .background(ObsidianElevated)
          .border(1.dp, ObsidianBorder, RoundedCornerShape(10.dp))
          .clickable { onOpenCountryPicker() }
          .padding(horizontal = 12.dp, vertical = 8.dp)
          .testTag("button_open_country_picker"),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.weight(1f)
        ) {
          Text(selectedCountry.flagEmoji, fontSize = 22.sp)
          Column {
            Text(
              text = selectedCountry.name,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = CyberTextPrimary,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )
            Text(
              text = "Prefijo: ${selectedCountry.dialCode} • Enlace Satelital Directo",
              fontSize = 10.sp,
              color = CyberCyan
            )
          }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text("Cambiar", fontSize = 11.sp, color = CyberEmerald, fontWeight = FontWeight.Bold)
          Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = CyberEmerald)
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Barra rápida de países populares para marcación de un toque
      LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        val quickList = listOf(
          ArmasCountryDirectory.DEFAULT_COUNTRY,
          ArmasCountryDirectory.ALL_COUNTRIES.first { it.isoCode == "MX" },
          ArmasCountryDirectory.ALL_COUNTRIES.first { it.isoCode == "US" },
          ArmasCountryDirectory.ALL_COUNTRIES.first { it.isoCode == "ES" },
          ArmasCountryDirectory.ALL_COUNTRIES.first { it.isoCode == "CO" },
          ArmasCountryDirectory.ALL_COUNTRIES.first { it.isoCode == "AR" },
          ArmasCountryDirectory.ALL_COUNTRIES.first { it.isoCode == "DE" },
          ArmasCountryDirectory.ALL_COUNTRIES.first { it.isoCode == "JP" },
          ArmasCountryDirectory.ALL_COUNTRIES.first { it.isoCode == "GB" },
          ArmasCountryDirectory.ALL_COUNTRIES.first { it.isoCode == "BR" }
        )

        items(quickList) { country ->
          val isSelected = selectedCountry.isoCode == country.isoCode
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(if (isSelected) CyberEmerald.copy(alpha = 0.2f) else ObsidianElevated)
              .border(1.dp, if (isSelected) CyberEmerald else ObsidianBorder, RoundedCornerShape(8.dp))
              .clickable { onSelectCountry(country) }
              .padding(horizontal = 8.dp, vertical = 5.dp)
          ) {
            Text(
              text = "${country.flagEmoji} ${country.dialCode}",
              fontSize = 11.sp,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
              color = if (isSelected) CyberEmerald else CyberTextSecondary
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Pantalla del número marcado
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(ObsidianElevated)
          .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = if (dialpadNumber.isEmpty()) "Ingresa número satelital..." else dialpadNumber,
          fontSize = if (dialpadNumber.length > 14) 16.sp else 20.sp,
          fontFamily = FontFamily.Monospace,
          fontWeight = FontWeight.Bold,
          color = if (dialpadNumber.isEmpty()) CyberTextMuted else CyberTextPrimary,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          modifier = Modifier.weight(1f)
        )

        if (dialpadNumber.isNotEmpty()) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
              onClick = onBackspace,
              modifier = Modifier.size(32.dp).testTag("button_dialpad_backspace")
            ) {
              Icon(Icons.Default.Backspace, contentDescription = "Borrar", tint = CyberTextSecondary, modifier = Modifier.size(18.dp))
            }
          }
        }
      }

      // Detección automática en tiempo real de país según el número marcado
      val detected = ArmasCountryDirectory.detectCountry(dialpadNumber)
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 6.dp, start = 4.dp, end = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Destino: ${detected.flagEmoji} ${detected.name} (${detected.dialCode})",
          fontSize = 10.sp,
          fontFamily = FontFamily.Monospace,
          fontWeight = FontWeight.Medium,
          color = CyberCyan
        )
        if (dialpadNumber.isNotEmpty()) {
          Text(
            text = "Limpiar",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = ThreatCrimson,
            modifier = Modifier
              .clickable { onClear() }
              .padding(horizontal = 4.dp, vertical = 2.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Botones del Teclado Numérico (Matriz 3x4)
      val keys = listOf(
        listOf("1" to "", "2" to "ABC", "3" to "DEF"),
        listOf("4" to "GHI", "5" to "JKL", "6" to "MNO"),
        listOf("7" to "PQRS", "8" to "TUV", "9" to "WXYZ"),
        listOf("*" to "SAT", "0" to "+", "#" to "PQC")
      )

      keys.forEach { rowKeys ->
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
          horizontalArrangement = Arrangement.SpaceEvenly
        ) {
          rowKeys.forEach { (digit, subtext) ->
            DialpadKeyButton(
              digit = digit,
              subtext = subtext,
              onClick = { onInput(digit) }
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Botón rápido de signo más (+) para marcación internacional
      Row(
        modifier = Modifier.fillMaxWidth(0.85f),
        horizontalArrangement = Arrangement.Center
      ) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(ObsidianElevated)
            .border(1.dp, ObsidianBorder, RoundedCornerShape(10.dp))
            .clickable { onInput("+") }
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .testTag("dialpad_key_plus"),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "+ Prefijo Internacional",
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            color = CyberCyan
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Botón principal de llamada satelital
      Button(
        onClick = onCall,
        enabled = dialpadNumber.isNotEmpty(),
        modifier = Modifier
          .fillMaxWidth(0.9f)
          .height(52.dp)
          .shadow(10.dp, RoundedCornerShape(28.dp), spotColor = CyberEmerald)
          .testTag("button_start_satellite_call"),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = CyberEmerald,
          contentColor = ObsidianBackground,
          disabledContainerColor = ObsidianElevated,
          disabledContentColor = CyberTextMuted
        )
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(22.dp))
          Text(
            text = "LLAMAR POR SATÉLITE ARMAS",
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 0.5.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Enlaces directos a Marcador Telefónico y Mensajes de Android
      val dialerContext = LocalContext.current
      Row(
        modifier = Modifier.fillMaxWidth(0.9f),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        OutlinedButton(
          onClick = {
            if (dialpadNumber.isNotBlank()) {
              try {
                val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${Uri.encode(dialpadNumber)}")).apply {
                  flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                dialerContext.startActivity(dialIntent)
              } catch (_: Exception) {}
            }
          },
          enabled = dialpadNumber.isNotEmpty(),
          modifier = Modifier
            .weight(1f)
            .height(42.dp)
            .testTag("button_dial_native_phone"),
          shape = RoundedCornerShape(12.dp),
          border = BorderStroke(1.dp, CyberCyan),
          colors = ButtonDefaults.outlinedButtonColors(contentColor = CyberCyan)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(Icons.Default.PhoneForwarded, contentDescription = null, modifier = Modifier.size(16.dp))
            Text("MARCADOR TELÉFONO", fontSize = 10.sp, fontWeight = FontWeight.Bold)
          }
        }

        OutlinedButton(
          onClick = {
            if (dialpadNumber.isNotBlank()) {
              try {
                val smsIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:${Uri.encode(dialpadNumber)}")).apply {
                  flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                dialerContext.startActivity(smsIntent)
              } catch (_: Exception) {}
            }
          },
          enabled = dialpadNumber.isNotEmpty(),
          modifier = Modifier
            .weight(1f)
            .height(42.dp)
            .testTag("button_sms_native_phone"),
          shape = RoundedCornerShape(12.dp),
          border = BorderStroke(1.dp, WarningGold),
          colors = ButtonDefaults.outlinedButtonColors(contentColor = WarningGold)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(Icons.Default.Sms, contentDescription = null, modifier = Modifier.size(16.dp))
            Text("ENVIAR SMS", fontSize = 10.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}

// -----------------------------------------------------------------------------
// Componente: Selector de País para Marcación Satelital
// -----------------------------------------------------------------------------

@Composable
fun CountryPickerDialog(
  currentCountry: CountryDialInfo,
  onSelectCountry: (CountryDialInfo) -> Unit,
  onDismiss: () -> Unit
) {
  var searchQuery by remember { mutableStateOf("") }
  val filteredCountries = remember(searchQuery) {
    if (searchQuery.isBlank()) {
      ArmasCountryDirectory.ALL_COUNTRIES
    } else {
      ArmasCountryDirectory.ALL_COUNTRIES.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
        it.dialCode.contains(searchQuery) ||
        it.isoCode.contains(searchQuery, ignoreCase = true)
      }
    }
  }

  AlertDialog(
    onDismissRequest = onDismiss,
    containerColor = ObsidianSurface,
    shape = RoundedCornerShape(18.dp),
    title = {
      Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Icon(Icons.Default.Language, contentDescription = null, tint = CyberCyan)
        Text(
          text = "Seleccionar País para Llamada",
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          color = CyberTextPrimary
        )
      }
    },
    text = {
      Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
          value = searchQuery,
          onValueChange = { searchQuery = it },
          placeholder = { Text("Buscar por país o prefijo (+34, México...)", fontSize = 12.sp, color = CyberTextMuted) },
          leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = CyberCyan, modifier = Modifier.size(18.dp)) },
          singleLine = true,
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = CyberTextPrimary,
            unfocusedTextColor = CyberTextPrimary,
            focusedBorderColor = CyberCyan,
            unfocusedBorderColor = ObsidianBorder,
            focusedContainerColor = ObsidianElevated,
            unfocusedContainerColor = ObsidianElevated
          )
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
          modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
        ) {
          items(filteredCountries) { country ->
            val isSelected = country.isoCode == currentCountry.isoCode
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(if (isSelected) CyberCyan.copy(alpha = 0.15f) else Color.Transparent)
                .clickable { onSelectCountry(country) }
                .padding(horizontal = 12.dp, vertical = 10.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(country.flagEmoji, fontSize = 22.sp)
                Column {
                  Text(country.name, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = CyberTextPrimary)
                  Text(country.isoCode, fontSize = 10.sp, color = CyberTextMuted)
                }
              }
              Text(country.dialCode, fontSize = 13.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, color = CyberEmerald)
            }
            HorizontalDivider(color = ObsidianBorder.copy(alpha = 0.5f))
          }
        }
      }
    },
    confirmButton = {
      Button(
        onClick = onDismiss,
        colors = ButtonDefaults.buttonColors(containerColor = ObsidianElevated, contentColor = CyberTextPrimary)
      ) {
        Text("Cerrar")
      }
    }
  )
}

// -----------------------------------------------------------------------------
// Componente: Banner de Permiso de Micrófono para Llamadas
// -----------------------------------------------------------------------------

@Composable
fun MicrophonePermissionBanner(onRequestPermission: () -> Unit) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("mic_permission_banner"),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianElevated),
    border = BorderStroke(1.dp, WarningGold)
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
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.weight(1f)
      ) {
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(WarningGold.copy(alpha = 0.15f))
            .border(1.dp, WarningGold, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(Icons.Default.Mic, contentDescription = null, tint = WarningGold, modifier = Modifier.size(18.dp))
        }
        Column {
          Text(
            text = "Permiso de Micrófono Requerido",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = WarningGold
          )
          Text(
            text = "Para transmitir audio de voz cifrado en vivo a la constelación orbital.",
            fontSize = 10.sp,
            color = CyberTextSecondary
          )
        }
      }

      Button(
        onClick = onRequestPermission,
        colors = ButtonDefaults.buttonColors(containerColor = WarningGold, contentColor = ObsidianBackground),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.testTag("btn_request_mic_permission")
      ) {
        Text("Activar", fontSize = 11.sp, fontWeight = FontWeight.Bold)
      }
    }
  }
}

@Composable
private fun DialpadKeyButton(
  digit: String,
  subtext: String,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .size(width = 82.dp, height = 56.dp)
      .clip(RoundedCornerShape(14.dp))
      .background(ObsidianElevated)
      .border(1.dp, ObsidianBorder, RoundedCornerShape(14.dp))
      .clickable { onClick() }
      .testTag("dialpad_key_$digit"),
    contentAlignment = Alignment.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(
        text = digit,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = CyberTextPrimary
      )
      if (subtext.isNotEmpty()) {
        Text(
          text = subtext,
          fontSize = 8.sp,
          fontWeight = FontWeight.Medium,
          color = CyberTextMuted
        )
      }
    }
  }
}

// -----------------------------------------------------------------------------
// Componente: Ítem de Contacto Soberano VIP
// -----------------------------------------------------------------------------

@Composable
private fun SovereignContactItem(
  contact: ArmasSatelliteContact,
  onCall: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("contact_item_${contact.id}"),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = BorderStroke(1.dp, ObsidianBorder)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Box(
          modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(OrbitBlue.copy(alpha = 0.15f))
            .border(1.dp, OrbitBlue, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(Icons.Default.SatelliteAlt, contentDescription = null, tint = OrbitBlue, modifier = Modifier.size(18.dp))
        }
        Column {
          Text(contact.name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = CyberTextPrimary)
          Text(contact.satelliteNumber, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = CyberCyan)
          Text(contact.role, fontSize = 9.sp, color = CyberTextMuted)
        }
      }

      Button(
        onClick = onCall,
        colors = ButtonDefaults.buttonColors(containerColor = CyberEmerald, contentColor = ObsidianBackground),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.testTag("btn_call_${contact.id}")
      ) {
        Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text("Llamar", fontSize = 11.sp, fontWeight = FontWeight.Bold)
      }
    }
  }
}

// -----------------------------------------------------------------------------
// Componente: Ítem del Historial de Llamadas Satelitales
// -----------------------------------------------------------------------------

@Composable
private fun SatelliteCallHistoryItem(log: ArmasSatelliteCallLog) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("call_history_${log.id}"),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = BorderStroke(1.dp, ObsidianBorder)
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
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Box(
          modifier = Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(
              when (log.callType) {
                SatelliteCallType.OUTGOING -> CyberEmerald.copy(alpha = 0.15f)
                SatelliteCallType.INCOMING -> CyberCyan.copy(alpha = 0.15f)
                SatelliteCallType.SOS_EMERGENCY -> ThreatCrimson.copy(alpha = 0.15f)
              }
            ),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = when (log.callType) {
              SatelliteCallType.OUTGOING -> Icons.Default.Call
              SatelliteCallType.INCOMING -> Icons.Default.Call
              SatelliteCallType.SOS_EMERGENCY -> Icons.Default.Sos
            },
            contentDescription = null,
            tint = when (log.callType) {
              SatelliteCallType.OUTGOING -> CyberEmerald
              SatelliteCallType.INCOMING -> CyberCyan
              SatelliteCallType.SOS_EMERGENCY -> ThreatCrimson
            },
            modifier = Modifier.size(16.dp)
          )
        }
        Column {
          Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            if (log.countryFlag.isNotEmpty()) {
              Text(log.countryFlag, fontSize = 13.sp)
            }
            Text(log.contactName, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CyberTextPrimary)
          }
          Text(log.phoneNumber, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = CyberTextSecondary)
          Text("${log.satelliteUsed} • ${log.timestamp}", fontSize = 9.sp, color = CyberTextMuted)
        }
      }

      Column(horizontalAlignment = Alignment.End) {
        Text(log.durationFormatted, fontSize = 11.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, color = CyberTextPrimary)
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Lock, contentDescription = null, tint = QuantumPurple, modifier = Modifier.size(10.dp))
          Spacer(modifier = Modifier.width(2.dp))
          Text("Kyber-1024", fontSize = 8.sp, color = QuantumPurple)
        }
      }
    }
  }
}

// -----------------------------------------------------------------------------
// Componente: Baliza de Emergencia SOS Satelital Armas
// -----------------------------------------------------------------------------

@Composable
private fun SatelliteSosBanner(
  satState: ArmasSatelliteChipState,
  onTriggerSos: () -> Unit,
  onCancelSos: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("satellite_sos_card"),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = BorderStroke(1.dp, if (satState.isInSOSBeaconMode) ThreatCrimson else ObsidianBorder)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.weight(1f)
      ) {
        Box(
          modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(ThreatCrimson.copy(alpha = 0.2f))
            .border(1.dp, ThreatCrimson, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(Icons.Default.Sos, contentDescription = null, tint = ThreatCrimson, modifier = Modifier.size(20.dp))
        }
        Column {
          Text(
            text = "SOS SATELITAL DIRECTO (406 MHz)",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (satState.isInSOSBeaconMode) ThreatCrimson else CyberTextPrimary
          )
          Text(
            text = if (satState.isInSOSBeaconMode) "TRANSMITIENDO BALIZA DE RESCATE..." else "Baliza de auxilio global sin cobertura celular",
            fontSize = 9.sp,
            color = CyberTextMuted
          )
        }
      }

      if (satState.isInSOSBeaconMode) {
        Button(
          onClick = onCancelSos,
          colors = ButtonDefaults.buttonColors(containerColor = ThreatCrimson, contentColor = Color.White),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.testTag("button_cancel_sos")
        ) {
          Text("Cancelar", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
      } else {
        OutlinedButton(
          onClick = onTriggerSos,
          colors = ButtonDefaults.outlinedButtonColors(contentColor = ThreatCrimson),
          border = BorderStroke(1.dp, ThreatCrimson),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.testTag("button_trigger_sos")
        ) {
          Text("Activar SOS", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

// -----------------------------------------------------------------------------
// Componente: Consola de Telemetría Satelital Espacial
// -----------------------------------------------------------------------------

@Composable
private fun SatelliteTelemetryConsole(satState: ArmasSatelliteChipState) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("satellite_telemetry_console"),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurfaceVariant),
    border = BorderStroke(1.dp, ObsidianBorder)
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text("CONSOLA DE TELEMETRÍA ORBITAL ARMAS", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = CyberCyan)
        Text("12 Nodos LEO", fontSize = 9.sp, color = CyberEmerald, fontFamily = FontFamily.Monospace)
      }

      Spacer(modifier = Modifier.height(8.dp))

      satState.satelliteTelemetryLogs.take(4).forEach { log ->
        Text(
          text = log,
          fontSize = 9.sp,
          fontFamily = FontFamily.Monospace,
          color = CyberTextSecondary,
          modifier = Modifier.padding(vertical = 2.dp)
        )
      }
    }
  }
}

// -----------------------------------------------------------------------------
// Componente: Radar de Pases Orbitales y Salto de Frecuencia PQC
// -----------------------------------------------------------------------------

@Composable
private fun OrbitalSatellitePassesRadarCard(satState: ArmasSatelliteChipState) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("card_orbital_satellite_radar"),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = BorderStroke(1.dp, Brush.horizontalGradient(listOf(WarningGold.copy(alpha = 0.5f), CyberCyan.copy(alpha = 0.5f))))
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
          Icon(Icons.Default.Radar, contentDescription = null, tint = WarningGold, modifier = Modifier.size(18.dp))
          Text("RADAR DE CONSTELACIÓN ORBITAL ARMAS", fontSize = 11.sp, fontWeight = FontWeight.Black, color = WarningGold)
        }
        Text("DIRECT-TO-CELL", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = CyberEmerald)
      }

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = "Monitoreo en tiempo real de pases y enlaces ópticos inter-satelitales (Laser ISL). Enlace: ${satState.uplinkFrequencyBand} con cifrado PQC.",
        fontSize = 10.sp,
        color = CyberTextSecondary
      )

      Spacer(modifier = Modifier.height(10.dp))

      satState.orbitalPasses.forEach { pass ->
        val isActive = pass.passStatus.contains("DIRECTA", ignoreCase = true) || pass.passStatus.contains("ACTIVO", ignoreCase = true)
        val statusColor = if (isActive) CyberEmerald else CyberCyan

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(if (isActive) CyberEmerald.copy(alpha = 0.08f) else ObsidianElevated)
            .border(0.5.dp, if (isActive) CyberEmerald.copy(alpha = 0.4f) else ObsidianBorder, RoundedCornerShape(10.dp))
            .padding(10.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Box(
                  modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(statusColor)
                )
                Text(pass.satelliteName, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CyberTextPrimary)
                Text("• ${pass.altitudeKm} km", fontSize = 9.5.sp, color = CyberTextMuted)
              }
              Spacer(modifier = Modifier.height(3.dp))
              Text("Elev: ${pass.elevationAngleDeg}° | Az: ${pass.azimuthDeg}° | Ventana: LEO Direct", fontSize = 9.sp, fontFamily = FontFamily.Monospace, color = CyberTextSecondary)
            }

            Column(horizontalAlignment = Alignment.End) {
              Text(pass.passStatus, fontSize = 9.5.sp, fontWeight = FontWeight.Black, color = statusColor)
              Text("Ventana: ${pass.timeToWindowMinutes} min", fontSize = 8.5.sp, color = CyberTextMuted)
            }
          }
        }
      }
    }
  }
}
