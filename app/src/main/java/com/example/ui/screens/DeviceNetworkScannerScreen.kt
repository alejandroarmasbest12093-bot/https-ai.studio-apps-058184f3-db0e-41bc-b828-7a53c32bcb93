package com.example.ui.screens

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.BluetoothConnected
import androidx.compose.material.icons.filled.BluetoothSearching
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.Lan
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Router
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiTethering
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.model.BluetoothScannedDevice
import com.example.model.BluetoothScanState
import com.example.model.ConnectedDeviceNode
import com.example.model.ConnectedNetworkState
import com.example.model.FullSystemScanState
import com.example.model.ScannedAppDetails
import com.example.model.ThreatSeverity
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
import com.example.ui.theme.QuantumPurpleDark
import com.example.ui.theme.ThreatCrimson
import com.example.ui.theme.ThreatCrimsonDark
import com.example.ui.theme.WarningGold
import com.example.viewmodel.CyberSecurityViewModel
import com.example.viewmodel.CyberUiState

@Composable
fun DeviceNetworkScannerScreen(
  uiState: CyberUiState,
  viewModel: CyberSecurityViewModel
) {
  val context = LocalContext.current
  var searchQuery by remember { mutableStateOf("") }

  val bluetoothPermissionsLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestMultiplePermissions()
  ) { _ ->
    viewModel.scanBluetoothForViruses(context)
  }

  val onStartBluetoothScan: () -> Unit = {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      bluetoothPermissionsLauncher.launch(
        arrayOf(
          Manifest.permission.BLUETOOTH_SCAN,
          Manifest.permission.BLUETOOTH_CONNECT
        )
      )
    } else {
      bluetoothPermissionsLauncher.launch(
        arrayOf(
          Manifest.permission.ACCESS_FINE_LOCATION,
          Manifest.permission.ACCESS_COARSE_LOCATION
        )
      )
    }
  }

  LaunchedEffect(Unit) {
    viewModel.refreshConnectedNetwork(context)
  }

  val infiniteTransition = rememberInfiniteTransition(label = "pulse_radar")
  val pulseAlpha by infiniteTransition.animateFloat(
    initialValue = 0.4f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(1200, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulse_alpha"
  )

  val filteredApps = remember(uiState.scannedApps, uiState.systemScanState.activeFilter, searchQuery) {
    uiState.scannedApps.filter { app ->
      val matchesFilter = when (uiState.systemScanState.activeFilter) {
        "VIRUS" -> app.riskLevel == "CRÍTICO_VIRUS" || app.virusType != null || app.isDismantled
        "SISTEMA" -> app.isSystemApp
        "USUARIO" -> !app.isSystemApp
        else -> true
      }
      val matchesSearch = if (searchQuery.isBlank()) true else {
        app.appName.contains(searchQuery, ignoreCase = true) ||
          app.packageName.contains(searchQuery, ignoreCase = true) ||
          app.virusType?.contains(searchQuery, ignoreCase = true) == true
      }
      matchesFilter && matchesSearch
    }
  }

  val totalViruses = uiState.scannedApps.count { it.riskLevel == "CRÍTICO_VIRUS" && !it.isDismantled }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(ObsidianBackground)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = PaddingValues(vertical = 16.dp)
  ) {
    // Top Title & Badges
    item {
      Column {
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
                .background(if (totalViruses > 0) ThreatCrimson.copy(alpha = pulseAlpha) else CyberEmerald.copy(alpha = pulseAlpha))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "RED CONECTADA & ESCÁNER TOTAL",
              color = CyberCyan,
              fontSize = 15.sp,
              fontWeight = FontWeight.Black,
              letterSpacing = 1.sp
            )
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(if (uiState.connectedNetwork.isQuantumTunnelActive) CyberEmerald.copy(alpha = 0.15f) else WarningGold.copy(alpha = 0.15f))
              .border(
                1.dp,
                if (uiState.connectedNetwork.isQuantumTunnelActive) CyberEmerald.copy(alpha = 0.6f) else WarningGold.copy(alpha = 0.6f),
                RoundedCornerShape(8.dp)
              )
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text(
              text = if (uiState.connectedNetwork.isQuantumTunnelActive) "TÚNEL CUÁNTICO ACTIVO" else "TÚNEL EN ESPERA",
              color = if (uiState.connectedNetwork.isQuantumTunnelActive) CyberEmerald else WarningGold,
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = "Acceso en vivo a la red conectada, auditoría heurística de todas las aplicaciones instaladas y desmantelamiento total de virus en memoria y subred.",
          color = CyberTextSecondary,
          fontSize = 11.sp,
          lineHeight = 16.sp
        )
      }
    }

    // Master Action Buttons Card (Desmantelar y Eliminar Virus)
    item {
      MasterEradicationCard(
        scanState = uiState.systemScanState,
        totalViruses = totalViruses,
        onDismantleAll = { viewModel.dismantleAndPurgeVirusesDeviceAndNetwork() },
        onScanAllApps = { viewModel.scanAllApplications(context) }
      )
    }

    // Connected Network Card (Live Network Access)
    item {
      ConnectedNetworkCard(
        network = uiState.connectedNetwork,
        onRefresh = { viewModel.refreshConnectedNetwork(context) },
        onToggleTunnel = { viewModel.toggleQuantumTunnel() },
        onFlushDns = { viewModel.flushDnsAndArpCache() }
      )
    }

    // Connected Subnet Devices Card
    item {
      ConnectedDevicesCard(
        devices = uiState.connectedNetwork.connectedDevices,
        onToggleIsolate = { viewModel.isolateNetworkDevice(it) }
      )
    }

    // Bluetooth RF & Virus Scanner Card
    item {
      BluetoothVirusScannerCard(
        bluetoothState = uiState.bluetoothScanState,
        onStartScan = onStartBluetoothScan,
        onDismantleAllViruses = { viewModel.dismantleAllBluetoothViruses() },
        onBlockDevice = { viewModel.blockAndPurgeBluetoothThreat(it) },
        onToggleAntiSniffing = { viewModel.toggleBluetoothAntiSniffingShield() }
      )
    }

    // Live Dismantle Terminal Console
    item {
      LiveDismantleConsoleCard(logs = uiState.systemScanState.dismantleConsoleLogs)
    }

    // Applications Scanner Section Header & Filters
    item {
      Column(modifier = Modifier.fillMaxWidth()) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Radar,
              contentDescription = "Radar",
              tint = CyberCyan,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "AUDITORÍA DE APLICACIONES (${uiState.scannedApps.size})",
              color = CyberTextPrimary,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Text(
            text = "Actualizado: ${uiState.systemScanState.lastScanTimestamp}",
            color = CyberTextMuted,
            fontSize = 10.sp
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Search bar
        OutlinedTextField(
          value = searchQuery,
          onValueChange = { searchQuery = it },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("app_search_input"),
          placeholder = { Text("Buscar aplicación, paquete o virus...", fontSize = 12.sp, color = CyberTextMuted) },
          leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Buscar", tint = CyberTextSecondary, modifier = Modifier.size(16.dp))
          },
          singleLine = true,
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = CyberCyan,
            unfocusedBorderColor = ObsidianBorder,
            focusedTextColor = CyberTextPrimary,
            unfocusedTextColor = CyberTextPrimary,
            focusedContainerColor = ObsidianSurface,
            unfocusedContainerColor = ObsidianSurface
          ),
          shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Filter chips
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          val filters = listOf(
            "TODAS" to "Todas (${uiState.scannedApps.size})",
            "VIRUS" to "Virus & Amenazas ($totalViruses)",
            "USUARIO" to "Usuario",
            "SISTEMA" to "Sistema"
          )
          filters.forEach { (code, label) ->
            val selected = uiState.systemScanState.activeFilter == code
            FilterChip(
              selected = selected,
              onClick = { viewModel.setAppFilter(code) },
              label = {
                Text(
                  text = label,
                  fontSize = 10.sp,
                  fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                  color = if (selected) (if (code == "VIRUS") ThreatCrimson else CyberCyan) else CyberTextSecondary
                )
              },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = if (code == "VIRUS") ThreatCrimson.copy(alpha = 0.2f) else CyberCyan.copy(alpha = 0.2f),
                containerColor = ObsidianSurface
              ),
              border = FilterChipDefaults.filterChipBorder(
                enabled = true,
                selected = selected,
                borderColor = if (selected) (if (code == "VIRUS") ThreatCrimson else CyberCyan) else ObsidianBorder
              ),
              shape = RoundedCornerShape(8.dp)
            )
          }
        }
      }
    }

    // List of Scanned Apps
    if (filteredApps.isEmpty()) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
          border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(ObsidianBorder, ObsidianBorder)))
        ) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(24.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "No se encontraron aplicaciones para este filtro.",
              color = CyberTextMuted,
              fontSize = 12.sp
            )
          }
        }
      }
    } else {
      items(filteredApps, key = { it.packageName }) { app ->
        AppAuditItemCard(
          app = app,
          onDisinfect = { viewModel.disinfectSingleApp(app.packageName) },
          onUninstall = { viewModel.launchUninstallAppIntent(context, app.packageName) },
          onQuarantine = { viewModel.quarantineApp(app.packageName) }
        )
      }
    }
  }
}

@Composable
fun MasterEradicationCard(
  scanState: FullSystemScanState,
  totalViruses: Int,
  onDismantleAll: () -> Unit,
  onScanAllApps: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("master_eradication_card"),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    shape = RoundedCornerShape(16.dp),
    border = CardDefaults.outlinedCardBorder().copy(
      brush = Brush.horizontalGradient(
        if (totalViruses > 0) listOf(ThreatCrimson.copy(alpha = 0.7f), WarningGold.copy(alpha = 0.7f))
        else listOf(CyberEmerald.copy(alpha = 0.7f), CyberCyan.copy(alpha = 0.7f))
      )
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
              .size(36.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(if (totalViruses > 0) ThreatCrimson.copy(alpha = 0.15f) else CyberEmerald.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = if (totalViruses > 0) Icons.Default.Warning else Icons.Default.Shield,
              contentDescription = "Estado",
              tint = if (totalViruses > 0) ThreatCrimson else CyberEmerald,
              modifier = Modifier.size(20.dp)
            )
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = if (totalViruses > 0) "AMENAZAS DETECTADAS ($totalViruses)" else "DISPOSITIVO & RED PURIFICADOS",
              color = if (totalViruses > 0) ThreatCrimson else CyberEmerald,
              fontSize = 13.sp,
              fontWeight = FontWeight.Black,
              letterSpacing = 0.5.sp
            )
            Text(
              text = if (totalViruses > 0) "Virus detectados listos para desmantelamiento" else "100% libre de virus, spyware y balizas C2",
              color = CyberTextSecondary,
              fontSize = 10.sp
            )
          }
        }
      }

      // Progress bar if scanning or dismantling
      if (scanState.isScanning || scanState.isDismantling) {
        Spacer(modifier = Modifier.height(14.dp))
        LinearProgressIndicator(
          progress = { scanState.scanProgress },
          modifier = Modifier
            .fillMaxWidth()
            .height(6.dp)
            .clip(RoundedCornerShape(3.dp)),
          color = if (scanState.isDismantling) ThreatCrimson else CyberCyan,
          trackColor = ObsidianBorder
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = scanState.currentScanningPackage,
            color = CyberTextSecondary,
            fontSize = 10.sp,
            maxLines = 1
          )
          Text(
            text = "${(scanState.scanProgress * 100).toInt()}%",
            color = CyberCyan,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Master Action Button: DESMANTELAR Y ELIMINAR VIRUS EN DISPOSITIVO Y RED
      Button(
        onClick = onDismantleAll,
        enabled = !scanState.isDismantling,
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
          .testTag("btn_dismantle_all_viruses"),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = if (totalViruses > 0) ThreatCrimson else CyberEmeraldDark,
          contentColor = Color.White
        )
      ) {
        if (scanState.isDismantling) {
          CircularProgressIndicator(
            modifier = Modifier.size(18.dp),
            color = Color.White,
            strokeWidth = 2.dp
          )
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = "DESMANTELANDO VIRUS EN TODO EL DISPOSITIVO Y RED...",
            fontSize = 11.sp,
            fontWeight = FontWeight.Black
          )
        } else {
          Icon(
            imageVector = Icons.Default.CleaningServices,
            contentDescription = "Limpiar",
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = if (totalViruses > 0) "DESMANTELAR Y ELIMINAR VIRUS EN DISPOSITIVO Y RED" else "RE-DESINFECTAR DISPOSITIVO Y RED",
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 0.5.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Secondary Action: ESCANEAR TODAS LAS APLICACIONES
      OutlinedButton(
        onClick = onScanAllApps,
        enabled = !scanState.isScanning,
        modifier = Modifier
          .fillMaxWidth()
          .height(44.dp)
          .testTag("btn_scan_all_apps"),
        shape = RoundedCornerShape(12.dp),
        border = ButtonDefaults.outlinedButtonBorder().copy(brush = Brush.horizontalGradient(listOf(CyberCyan, CyberCyanMuted))),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = CyberCyan)
      ) {
        if (scanState.isScanning) {
          CircularProgressIndicator(
            modifier = Modifier.size(16.dp),
            color = CyberCyan,
            strokeWidth = 2.dp
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text("ESCANEANDO TODAS LAS APLICACIONES...", fontSize = 10.sp, fontWeight = FontWeight.Bold)
        } else {
          Icon(Icons.Default.Radar, contentDescription = "Escanear", modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text("ESCANEAR TODAS LAS APLICACIONES EN VIVO", fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun ConnectedNetworkCard(
  network: ConnectedNetworkState,
  onRefresh: () -> Unit,
  onToggleTunnel: () -> Unit,
  onFlushDns: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("connected_network_card"),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    shape = RoundedCornerShape(14.dp),
    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(CyberCyan.copy(alpha = 0.5f), ObsidianBorder)))
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(34.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(CyberCyan.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(Icons.Default.Wifi, contentDescription = "WiFi", tint = CyberCyan, modifier = Modifier.size(18.dp))
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = network.networkName,
              color = CyberTextPrimary,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = network.connectionType,
              color = CyberTextSecondary,
              fontSize = 10.sp
            )
          }
        }

        IconButton(
          onClick = onRefresh,
          modifier = Modifier.size(32.dp).testTag("btn_refresh_network")
        ) {
          Icon(Icons.Default.Refresh, contentDescription = "Refrescar", tint = CyberCyan, modifier = Modifier.size(16.dp))
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Network Parameters Grid
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        NetworkParamBox(
          modifier = Modifier.weight(1f),
          label = "IP LOCAL",
          value = network.localIpAddress,
          color = CyberCyan
        )
        NetworkParamBox(
          modifier = Modifier.weight(1f),
          label = "GATEWAY",
          value = network.gatewayIp,
          color = CyberTextPrimary
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        NetworkParamBox(
          modifier = Modifier.weight(1f),
          label = "SUBRED / MÁSCARA",
          value = network.subnetMask,
          color = CyberTextSecondary
        )
        NetworkParamBox(
          modifier = Modifier.weight(1f),
          label = "DNS SEGURO",
          value = network.dnsServers.firstOrNull() ?: "1.1.1.1 (DoT)",
          color = QuantumPurple
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Live Telemetry Bandwidth Gauges
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(10.dp))
          .background(ObsidianElevated)
          .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text("BAJADA EN VIVO", color = CyberTextMuted, fontSize = 8.sp, fontWeight = FontWeight.Bold)
          Text(
            text = "%.1f Mbps".format(network.liveDownloadSpeedMbps),
            color = CyberCyan,
            fontSize = 12.sp,
            fontWeight = FontWeight.Black
          )
        }
        Column {
          Text("SUBIDA EN VIVO", color = CyberTextMuted, fontSize = 8.sp, fontWeight = FontWeight.Bold)
          Text(
            text = "%.1f Mbps".format(network.liveUploadSpeedMbps),
            color = QuantumPurple,
            fontSize = 12.sp,
            fontWeight = FontWeight.Black
          )
        }
        Column {
          Text("LATENCIA PING", color = CyberTextMuted, fontSize = 8.sp, fontWeight = FontWeight.Bold)
          Text(
            text = "${network.liveLatencyMs} ms",
            color = CyberEmerald,
            fontSize = 12.sp,
            fontWeight = FontWeight.Black
          )
        }
        Column {
          Text("ENLACE", color = CyberTextMuted, fontSize = 8.sp, fontWeight = FontWeight.Bold)
          Text(
            text = "${network.linkSpeedMbps}M",
            color = CyberTextPrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Black
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Quick Network Actions
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        OutlinedButton(
          onClick = onToggleTunnel,
          modifier = Modifier.weight(1f).height(36.dp).testTag("btn_toggle_tunnel"),
          shape = RoundedCornerShape(8.dp),
          border = ButtonDefaults.outlinedButtonBorder().copy(
            brush = Brush.horizontalGradient(
              if (network.isQuantumTunnelActive) listOf(CyberEmerald, CyberEmerald) else listOf(WarningGold, WarningGold)
            )
          ),
          contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = "Túnel",
            tint = if (network.isQuantumTunnelActive) CyberEmerald else WarningGold,
            modifier = Modifier.size(13.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = if (network.isQuantumTunnelActive) "Túnel Activo" else "Activar Túnel",
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = if (network.isQuantumTunnelActive) CyberEmerald else WarningGold
          )
        }

        OutlinedButton(
          onClick = onFlushDns,
          modifier = Modifier.weight(1f).height(36.dp).testTag("btn_flush_dns"),
          shape = RoundedCornerShape(8.dp),
          border = ButtonDefaults.outlinedButtonBorder().copy(brush = Brush.horizontalGradient(listOf(CyberCyan, CyberCyan))),
          contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
          Icon(Icons.Default.Dns, contentDescription = "DNS", tint = CyberCyan, modifier = Modifier.size(13.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Purgar DNS & ARP", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = CyberCyan)
        }
      }
    }
  }
}

@Composable
fun NetworkParamBox(
  modifier: Modifier = Modifier,
  label: String,
  value: String,
  color: Color
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(8.dp))
      .background(ObsidianElevated)
      .padding(horizontal = 10.dp, vertical = 6.dp)
  ) {
    Column {
      Text(text = label, color = CyberTextMuted, fontSize = 8.sp, fontWeight = FontWeight.Bold)
      Text(
        text = value,
        color = color,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        maxLines = 1
      )
    }
  }
}

@Composable
fun ConnectedDevicesCard(
  devices: List<ConnectedDeviceNode>,
  onToggleIsolate: (String) -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("connected_devices_card"),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    shape = RoundedCornerShape(14.dp),
    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(ObsidianBorder, ObsidianBorder)))
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Lan, contentDescription = "LAN", tint = CyberEmerald, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "DISPOSITIVOS EN SUBRED LOCAL (${devices.size})",
            color = CyberTextPrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
          )
        }
        Text(
          text = "Rango 192.168.1.0/24",
          color = CyberTextMuted,
          fontSize = 9.sp
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      devices.forEach { device ->
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (device.isRogue || device.threatStatus == "AMENAZA_DETECTADA") ThreatCrimson.copy(alpha = 0.12f) else ObsidianElevated)
            .border(
              1.dp,
              if (device.isRogue || device.threatStatus == "AMENAZA_DETECTADA") ThreatCrimson.copy(alpha = 0.4f) else ObsidianBorder,
              RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 10.dp, vertical = 8.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
          ) {
            val icon = when (device.deviceType) {
              "ROUTER" -> Icons.Default.Router
              "LOCAL_HOST" -> Icons.Default.PhoneAndroid
              "WORKSTATION" -> Icons.Default.Laptop
              "IOT" -> Icons.Default.Devices
              else -> Icons.Default.WifiTethering
            }
            Icon(
              imageVector = icon,
              contentDescription = device.deviceType,
              tint = if (device.isRogue || device.threatStatus == "AMENAZA_DETECTADA") ThreatCrimson else CyberCyan,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = device.name,
                color = if (device.isRogue || device.threatStatus == "AMENAZA_DETECTADA") ThreatCrimson else CyberTextPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
              )
              Text(
                text = "${device.ip} • MAC: ${device.mac} • ${device.latencyMs}ms",
                color = CyberTextSecondary,
                fontSize = 9.sp
              )
            }
          }

          // Isolate / Status button
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(
                if (device.isQuarantined) ThreatCrimson.copy(alpha = 0.2f)
                else if (device.threatStatus == "AMENAZA_DETECTADA") ThreatCrimson.copy(alpha = 0.3f)
                else CyberEmerald.copy(alpha = 0.15f)
              )
              .clickable { onToggleIsolate(device.id) }
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text(
              text = if (device.isQuarantined) "AISLADO" else if (device.threatStatus == "AMENAZA_DETECTADA") "DESMANTELAR" else "SEGURO",
              color = if (device.isQuarantined || device.threatStatus == "AMENAZA_DETECTADA") ThreatCrimson else CyberEmerald,
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }
  }
}

@Composable
fun LiveDismantleConsoleCard(logs: List<String>) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("live_dismantle_console"),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurfaceVariant),
    shape = RoundedCornerShape(12.dp),
    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(ObsidianBorder, ObsidianBorder)))
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Terminal, contentDescription = "Terminal", tint = CyberCyan, modifier = Modifier.size(15.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "CONSOLA DE DESMANTELAMIENTO & PURGA EN VIVO",
            color = CyberCyan,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
          )
        }
        Box(
          modifier = Modifier
            .size(6.dp)
            .clip(CircleShape)
            .background(CyberEmerald)
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Column(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(8.dp))
          .background(Color(0xFF03060A))
          .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        val displayedLogs = logs.take(6)
        if (displayedLogs.isEmpty()) {
          Text(
            text = "[SISTEMA] Esperando comandos de purga...",
            color = CyberTextMuted,
            fontSize = 9.sp,
            fontFamily = FontFamily.Monospace
          )
        } else {
          displayedLogs.forEach { log ->
            Text(
              text = log,
              color = if (log.contains("FASE") || log.contains("COMPLETADO")) CyberEmerald
              else if (log.contains("amenazas") || log.contains("virus") || log.contains("Rogue")) ThreatCrimson
              else CyberTextSecondary,
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

@Composable
fun AppAuditItemCard(
  app: ScannedAppDetails,
  onDisinfect: () -> Unit,
  onUninstall: () -> Unit,
  onQuarantine: () -> Unit
) {
  val isThreat = app.riskLevel == "CRÍTICO_VIRUS" && !app.isDismantled

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("app_item_${app.packageName}"),
    colors = CardDefaults.cardColors(
      containerColor = if (isThreat) ThreatCrimson.copy(alpha = 0.08f) else ObsidianSurface
    ),
    shape = RoundedCornerShape(12.dp),
    border = CardDefaults.outlinedCardBorder().copy(
      brush = Brush.horizontalGradient(
        if (isThreat) listOf(ThreatCrimson.copy(alpha = 0.7f), ThreatCrimson.copy(alpha = 0.3f))
        else if (app.isDismantled) listOf(CyberEmerald.copy(alpha = 0.6f), CyberCyan.copy(alpha = 0.4f))
        else listOf(ObsidianBorder, ObsidianBorder)
      )
    )
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Row(
          modifier = Modifier.weight(1f),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // App Icon / Initial Badge
          Box(
            modifier = Modifier
              .size(38.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(
                if (isThreat) ThreatCrimson.copy(alpha = 0.2f)
                else if (app.isDismantled) CyberEmerald.copy(alpha = 0.2f)
                else CyberCyan.copy(alpha = 0.12f)
              ),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = if (isThreat) Icons.Default.BugReport
              else if (app.isDismantled) Icons.Default.CheckCircle
              else if (app.isSystemApp) Icons.Default.Security
              else Icons.Default.PhoneAndroid,
              contentDescription = app.appName,
              tint = if (isThreat) ThreatCrimson else if (app.isDismantled) CyberEmerald else CyberCyan,
              modifier = Modifier.size(20.dp)
            )
          }

          Spacer(modifier = Modifier.width(10.dp))

          Column {
            Text(
              text = app.appName,
              color = CyberTextPrimary,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              maxLines = 1
            )
            Text(
              text = app.packageName,
              color = CyberTextSecondary,
              fontSize = 9.sp,
              maxLines = 1
            )
            Text(
              text = "v${app.versionName} • ${if (app.isSystemApp) "Sistema Android" else "App Instalada"}",
              color = CyberTextMuted,
              fontSize = 8.sp
            )
          }
        }

        // Status Badge
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(
              if (isThreat) ThreatCrimson.copy(alpha = 0.2f)
              else if (app.isDismantled) CyberEmerald.copy(alpha = 0.2f)
              else CyberCyan.copy(alpha = 0.15f)
            )
            .border(
              1.dp,
              if (isThreat) ThreatCrimson.copy(alpha = 0.6f)
              else if (app.isDismantled) CyberEmerald.copy(alpha = 0.6f)
              else CyberCyan.copy(alpha = 0.4f),
              RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 6.dp, vertical = 3.dp)
        ) {
          Text(
            text = if (isThreat) "VIRUS DETECTADO" else if (app.isDismantled) "DESMANTELADO" else "SEGURO",
            color = if (isThreat) ThreatCrimson else if (app.isDismantled) CyberEmerald else CyberCyan,
            fontSize = 8.sp,
            fontWeight = FontWeight.Black
          )
        }
      }

      // Virus or Threat Signature Details
      if (app.virusType != null || app.threatSignatures.isNotEmpty()) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(ObsidianElevated)
            .padding(horizontal = 8.dp, vertical = 5.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = if (isThreat) Icons.Default.Warning else Icons.Default.Shield,
            contentDescription = "Firma",
            tint = if (isThreat) ThreatCrimson else CyberEmerald,
            modifier = Modifier.size(12.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = if (isThreat) "Firma: ${app.virusType ?: app.threatSignatures.joinToString(", ")}"
            else "Firma: ${app.threatSignatures.joinToString(", ")}",
            color = if (isThreat) ThreatCrimson else CyberEmerald,
            fontSize = 9.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1
          )
        }
      }

      // Dangerous permissions tags
      if (app.dangerousPermissions.isNotEmpty()) {
        Spacer(modifier = Modifier.height(6.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          app.dangerousPermissions.take(4).forEach { perm ->
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(ObsidianElevated)
                .padding(horizontal = 5.dp, vertical = 2.dp)
            ) {
              Text(
                text = perm,
                color = if (isThreat) ThreatCrimson.copy(alpha = 0.9f) else CyberTextSecondary,
                fontSize = 7.sp,
                fontWeight = FontWeight.Medium
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Action Buttons Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        if (isThreat) {
          Button(
            onClick = onDisinfect,
            modifier = Modifier.weight(1f).height(34.dp).testTag("btn_disinfect_${app.packageName}"),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ThreatCrimson, contentColor = Color.White),
            contentPadding = PaddingValues(horizontal = 6.dp)
          ) {
            Icon(Icons.Default.CleaningServices, contentDescription = "Desmantelar", modifier = Modifier.size(12.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Desmantelar Virus", fontSize = 9.sp, fontWeight = FontWeight.Bold)
          }
        }

        if (!app.isSystemApp) {
          OutlinedButton(
            onClick = onUninstall,
            modifier = Modifier.weight(1f).height(34.dp).testTag("btn_uninstall_${app.packageName}"),
            shape = RoundedCornerShape(8.dp),
            border = ButtonDefaults.outlinedButtonBorder().copy(brush = Brush.horizontalGradient(listOf(ThreatCrimson, ThreatCrimsonDark))),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = ThreatCrimson),
            contentPadding = PaddingValues(horizontal = 6.dp)
          ) {
            Icon(Icons.Default.Delete, contentDescription = "Desinstalar", modifier = Modifier.size(12.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Desinstalar APK", fontSize = 9.sp, fontWeight = FontWeight.Bold)
          }
        }

        OutlinedButton(
          onClick = onQuarantine,
          modifier = Modifier.weight(1f).height(34.dp).testTag("btn_quarantine_${app.packageName}"),
          shape = RoundedCornerShape(8.dp),
          border = ButtonDefaults.outlinedButtonBorder().copy(brush = Brush.horizontalGradient(listOf(QuantumPurple, QuantumPurpleDark))),
          colors = ButtonDefaults.outlinedButtonColors(contentColor = QuantumPurple),
          contentPadding = PaddingValues(horizontal = 6.dp)
        ) {
          Icon(Icons.Default.Block, contentDescription = "Cuarentena", modifier = Modifier.size(12.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text(if (app.isQuarantined) "En Bóveda" else "Cuarentena", fontSize = 9.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun BluetoothVirusScannerCard(
  bluetoothState: BluetoothScanState,
  onStartScan: () -> Unit,
  onDismantleAllViruses: () -> Unit,
  onBlockDevice: (String) -> Unit,
  onToggleAntiSniffing: () -> Unit
) {
  var showConsoleLogs by remember { mutableStateOf(false) }
  val unblockedThreats = bluetoothState.scannedDevices.count { it.detectedVirusOrExploit != null && !it.isBlocked }

  val infiniteTransition = rememberInfiniteTransition(label = "bt_radar")
  val radarSweepAlpha by infiniteTransition.animateFloat(
    initialValue = 0.3f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(900, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "bt_radar_pulse"
  )

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("bluetooth_virus_scanner_card"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      if (bluetoothState.totalThreatsFound > 0 && unblockedThreats > 0) ThreatCrimson.copy(alpha = 0.7f)
      else CyberCyan.copy(alpha = 0.5f)
    )
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp)
    ) {
      // Top Header Row
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
              .clip(RoundedCornerShape(10.dp))
              .background(
                if (unblockedThreats > 0) ThreatCrimson.copy(alpha = 0.18f)
                else CyberCyan.copy(alpha = 0.15f)
              ),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = if (bluetoothState.isScanning) Icons.Default.BluetoothSearching else Icons.Default.Bluetooth,
              contentDescription = "Bluetooth Scanner",
              tint = if (unblockedThreats > 0) ThreatCrimson else CyberCyan,
              modifier = Modifier.size(22.dp)
            )
          }

          Spacer(modifier = Modifier.width(10.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "ESCÁNER BLUETOOTH & VIRUS RF",
                color = if (unblockedThreats > 0) ThreatCrimson else CyberCyan,
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.8.sp
              )
              Spacer(modifier = Modifier.width(6.dp))
              Box(
                modifier = Modifier
                  .size(7.dp)
                  .clip(CircleShape)
                  .background(
                    if (bluetoothState.isScanning) WarningGold.copy(alpha = radarSweepAlpha)
                    else if (unblockedThreats > 0) ThreatCrimson.copy(alpha = radarSweepAlpha)
                    else CyberEmerald
                  )
              )
            }
            Text(
              text = "${bluetoothState.scannedDevices.size} emisores detectados • ${bluetoothState.totalThreatsFound} virus/exploits • ${bluetoothState.blockedThreatsCount} neutralizados",
              color = CyberTextSecondary,
              fontSize = 10.sp
            )
          }
        }

        // Anti-Sniffing Stealth Button
        IconButton(
          onClick = onToggleAntiSniffing,
          modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (bluetoothState.antiSniffingShieldActive) CyberEmerald.copy(alpha = 0.15f) else WarningGold.copy(alpha = 0.15f))
            .testTag("btn_toggle_anti_sniffing")
        ) {
          Icon(
            imageVector = if (bluetoothState.antiSniffingShieldActive) Icons.Default.VisibilityOff else Icons.Default.Visibility,
            contentDescription = "Blindaje Anti-Sniffing",
            tint = if (bluetoothState.antiSniffingShieldActive) CyberEmerald else WarningGold,
            modifier = Modifier.size(18.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Status Badges Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(6.dp))
            .background(ObsidianElevated)
            .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
          Column {
            Text(text = "Antena BLE 2.4GHz", color = CyberTextMuted, fontSize = 8.sp)
            Text(
              text = if (bluetoothState.isBluetoothEnabled) "Sintonizada (v5.2)" else "Standby / RF Seguro",
              color = if (bluetoothState.isBluetoothEnabled) CyberCyan else WarningGold,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(6.dp))
            .background(ObsidianElevated)
            .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
          Column {
            Text(text = "Virus en Radiofrecuencia", color = CyberTextMuted, fontSize = 8.sp)
            Text(
              text = if (unblockedThreats > 0) "$unblockedThreats ACTIVOS" else "0 (Limpio)",
              color = if (unblockedThreats > 0) ThreatCrimson else CyberEmerald,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
        Box(
          modifier = Modifier
            .weight(1.1f)
            .clip(RoundedCornerShape(6.dp))
            .background(ObsidianElevated)
            .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
          Column {
            Text(text = "Blindaje Anti-Sniffing", color = CyberTextMuted, fontSize = 8.sp)
            Text(
              text = if (bluetoothState.antiSniffingShieldActive) "Modo Invisible 100%" else "Visible",
              color = if (bluetoothState.antiSniffingShieldActive) CyberEmerald else WarningGold,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      // Scanning Progress Bar & Text
      if (bluetoothState.isScanning) {
        Spacer(modifier = Modifier.height(10.dp))
        LinearProgressIndicator(
          progress = { bluetoothState.scanProgress },
          modifier = Modifier
            .fillMaxWidth()
            .height(5.dp)
            .clip(RoundedCornerShape(3.dp)),
          color = CyberCyan,
          trackColor = ObsidianBorder
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = bluetoothState.activeScanStatusText ?: "Escaneando canales Bluetooth y espectro BLE...",
          color = CyberCyan,
          fontSize = 10.sp,
          fontFamily = FontFamily.Monospace
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Action Buttons
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Button(
          onClick = onStartScan,
          enabled = !bluetoothState.isScanning,
          modifier = Modifier
            .weight(1f)
            .height(38.dp)
            .testTag("btn_scan_bluetooth_devices"),
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = CyberCyan,
            contentColor = ObsidianBackground,
            disabledContainerColor = CyberCyan.copy(alpha = 0.3f),
            disabledContentColor = ObsidianBackground
          ),
          contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
          if (bluetoothState.isScanning) {
            CircularProgressIndicator(modifier = Modifier.size(14.dp), color = ObsidianBackground, strokeWidth = 2.dp)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Escaneando...", fontSize = 10.sp, fontWeight = FontWeight.Bold)
          } else {
            Icon(Icons.Default.Radar, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Escanear Bluetooth & Beacons", fontSize = 10.sp, fontWeight = FontWeight.Bold)
          }
        }

        if (unblockedThreats > 0) {
          Button(
            onClick = onDismantleAllViruses,
            modifier = Modifier
              .weight(1f)
              .height(38.dp)
              .testTag("btn_purge_all_bluetooth_viruses"),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ThreatCrimson, contentColor = Color.White),
            contentPadding = PaddingValues(horizontal = 8.dp)
          ) {
            Icon(Icons.Default.CleaningServices, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Desmantelar Virus BT ($unblockedThreats)", fontSize = 10.sp, fontWeight = FontWeight.Bold)
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Devices List
      Text(
        text = "DISPOSITIVOS & EMISORES BLUETOOTH EN RANGO (${bluetoothState.scannedDevices.size})",
        color = CyberTextPrimary,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold
      )

      Spacer(modifier = Modifier.height(8.dp))

      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        bluetoothState.scannedDevices.forEach { device ->
          BluetoothDeviceRowCard(
            device = device,
            onBlock = { onBlockDevice(device.id) }
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Console logs toggle
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(6.dp))
          .clickable { showConsoleLogs = !showConsoleLogs }
          .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Terminal, contentDescription = null, tint = CyberTextMuted, modifier = Modifier.size(13.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Consola de Radiofrecuencia eBPF (${bluetoothState.bluetoothConsoleLogs.size} eventos)",
            color = CyberTextMuted,
            fontSize = 10.sp
          )
        }
        Text(
          text = if (showConsoleLogs) "Ocultar" else "Ver registros",
          color = CyberCyan,
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold
        )
      }

      if (showConsoleLogs) {
        Spacer(modifier = Modifier.height(6.dp))
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF03070C))
            .border(1.dp, ObsidianBorder, RoundedCornerShape(8.dp))
            .padding(8.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
            bluetoothState.bluetoothConsoleLogs.take(10).forEach { log ->
              Text(
                text = log,
                color = when {
                  log.contains("VIRUS", ignoreCase = true) || log.contains("AMENAZA", ignoreCase = true) -> ThreatCrimson
                  log.contains("BLOQUEO", ignoreCase = true) || log.contains("FINALIZADO", ignoreCase = true) -> CyberEmerald
                  log.contains("Alerta", ignoreCase = true) -> WarningGold
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

@Composable
fun BluetoothDeviceRowCard(
  device: BluetoothScannedDevice,
  onBlock: () -> Unit
) {
  val isThreat = device.detectedVirusOrExploit != null
  val isBlocked = device.isBlocked

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("bt_device_${device.id}"),
    shape = RoundedCornerShape(10.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (isThreat && !isBlocked) ThreatCrimson.copy(alpha = 0.08f) else ObsidianElevated
    ),
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      when {
        isBlocked -> CyberEmerald.copy(alpha = 0.4f)
        isThreat -> ThreatCrimson.copy(alpha = 0.6f)
        else -> ObsidianBorder
      }
    )
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
        Row(
          modifier = Modifier.weight(1f),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(32.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(
                when {
                  isBlocked -> CyberEmerald.copy(alpha = 0.15f)
                  isThreat -> ThreatCrimson.copy(alpha = 0.18f)
                  else -> CyberCyan.copy(alpha = 0.12f)
                }
              ),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = when {
                isBlocked -> Icons.Default.CheckCircle
                isThreat -> Icons.Default.Dangerous
                device.isPaired -> Icons.Default.BluetoothConnected
                else -> Icons.Default.Sensors
              },
              contentDescription = null,
              tint = when {
                isBlocked -> CyberEmerald
                isThreat -> ThreatCrimson
                else -> CyberCyan
              },
              modifier = Modifier.size(18.dp)
            )
          }

          Spacer(modifier = Modifier.width(8.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = device.deviceName,
                color = CyberTextPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
              if (device.isPaired) {
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(CyberCyan.copy(alpha = 0.15f))
                    .padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                  Text("EMPAREJADO", color = CyberCyan, fontSize = 7.sp, fontWeight = FontWeight.Black)
                }
              }
            }
            Text(
              text = "${device.macAddress} • ${device.deviceType} • ${device.lastSeen}",
              color = CyberTextSecondary,
              fontSize = 9.sp
            )
          }
        }

        // RSSI dBm Badge
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(ObsidianSurface)
            .border(1.dp, ObsidianBorder, RoundedCornerShape(6.dp))
            .padding(horizontal = 6.dp, vertical = 3.dp)
        ) {
          Text(
            text = "${device.rssi} dBm",
            color = when {
              device.rssi > -50 -> CyberEmerald
              device.rssi > -70 -> WarningGold
              else -> CyberTextMuted
            },
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
          )
        }
      }

      // Threat / Safe details
      Spacer(modifier = Modifier.height(6.dp))

      if (isThreat) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(if (isBlocked) CyberEmerald.copy(alpha = 0.1f) else ThreatCrimson.copy(alpha = 0.12f))
            .padding(8.dp)
        ) {
          Column {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = if (isBlocked) Icons.Default.CheckCircle else Icons.Default.BugReport,
                  contentDescription = null,
                  tint = if (isBlocked) CyberEmerald else ThreatCrimson,
                  modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = device.detectedVirusOrExploit ?: "Virus RF",
                  color = if (isBlocked) CyberEmerald else ThreatCrimson,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Black
                )
              }

              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(if (isBlocked) CyberEmerald.copy(alpha = 0.2f) else ThreatCrimson.copy(alpha = 0.2f))
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text(
                  text = if (isBlocked) "NEUTRALIZADO" else "AMENAZA ACTIVA",
                  color = if (isBlocked) CyberEmerald else ThreatCrimson,
                  fontSize = 8.sp,
                  fontWeight = FontWeight.Black
                )
              }
            }

            if (device.vulnerabilityDescription != null) {
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = device.vulnerabilityDescription,
                color = CyberTextSecondary,
                fontSize = 9.sp,
                lineHeight = 13.sp
              )
            }

            if (!isBlocked) {
              Spacer(modifier = Modifier.height(6.dp))
              Button(
                onClick = onBlock,
                modifier = Modifier
                  .fillMaxWidth()
                  .height(30.dp)
                  .testTag("btn_block_bt_${device.id}"),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ThreatCrimson, contentColor = Color.White),
                contentPadding = PaddingValues(horizontal = 6.dp)
              ) {
                Icon(Icons.Default.Block, contentDescription = null, modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Bloquear y Neutralizar Emisor", fontSize = 9.sp, fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      } else {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(CyberEmerald.copy(alpha = 0.08f))
            .padding(horizontal = 8.dp, vertical = 4.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(Icons.Default.CheckCircle, contentDescription = null, tint = CyberEmerald, modifier = Modifier.size(12.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "VERIFICADO SEGURO • ${device.encryptionProfile}",
            color = CyberEmerald,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }
  }
}
