package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.SatelliteAlt
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ArmasSatelliteChipState
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
fun SatelliteInternetSection(
  satState: ArmasSatelliteChipState,
  onToggleInternet: (Boolean?) -> Unit,
  onRunSpeedTest: () -> Unit,
  onToggleVpn: () -> Unit,
  onSetDnsProvider: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val internet = satState.internetState
  var customUrlInput by remember { mutableStateOf("https://www.google.com") }

  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Tarjeta Principal de Alimentación de Internet Satelital
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("card_satellite_internet_master"),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
      border = BorderStroke(
        1.5.dp,
        Brush.horizontalGradient(
          if (internet.isConnected) listOf(CyberEmerald, CyberCyan, QuantumPurple)
          else listOf(ObsidianBorder, CyberTextMuted)
        )
      )
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
                .size(46.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(if (internet.isConnected) CyberEmerald.copy(alpha = 0.2f) else ObsidianElevated)
                .border(
                  1.dp,
                  if (internet.isConnected) CyberEmerald else ObsidianBorder,
                  RoundedCornerShape(12.dp)
                ),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Public,
                contentDescription = null,
                tint = if (internet.isConnected) CyberEmerald else CyberTextMuted,
                modifier = Modifier.size(26.dp)
              )
            }
            Column {
              Text(
                text = "INTERNET SATELITAL DIRECT-TO-CELL",
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = CyberTextPrimary,
                letterSpacing = 0.8.sp
              )
              Text(
                text = if (internet.isConnected) "CONECTADO A CONSTELACIÓN LEO" else "STANDBY (DESCONECTADO)",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = if (internet.isConnected) CyberEmerald else CyberTextMuted
              )
            }
          }

          Switch(
            checked = internet.isConnected,
            onCheckedChange = { onToggleInternet(it) },
            colors = SwitchDefaults.colors(
              checkedThumbColor = ObsidianBackground,
              checkedTrackColor = CyberEmerald,
              uncheckedThumbColor = CyberTextMuted,
              uncheckedTrackColor = ObsidianElevated
            ),
            modifier = Modifier.testTag("switch_toggle_satellite_internet")
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        HorizontalDivider(color = ObsidianBorder)

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column {
            Text("MODALIDAD:", fontSize = 9.sp, color = CyberTextMuted)
            Text(internet.connectionMode, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = CyberCyan)
          }
          Column(horizontalAlignment = Alignment.End) {
            Text("CUOTA DE DATOS:", fontSize = 9.sp, color = CyberTextMuted)
            Text(internet.quotaRemaining, fontSize = 11.sp, fontWeight = FontWeight.Black, color = WarningGold)
          }
        }
      }
    }

    // 2. Métricas de Rendimiento en Tiempo Real (Velocidad, Ping, IP)
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("card_satellite_metrics_grid"),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
      border = BorderStroke(1.dp, ObsidianBorder)
    ) {
      Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
          text = "MÉTRICAS DEL HAZ DE DATOS EN VIVO",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = CyberCyan,
          letterSpacing = 1.sp
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          // Descarga
          MetricTile(
            icon = Icons.Default.Download,
            label = "DESCARGA LEO",
            value = if (internet.isConnected) "${internet.downloadSpeedMbps} Mbps" else "0.0 Mbps",
            color = CyberEmerald,
            modifier = Modifier.weight(1f)
          )

          // Subida
          MetricTile(
            icon = Icons.Default.Upload,
            label = "SUBIDA UPLINK",
            value = if (internet.isConnected) "${internet.uploadSpeedMbps} Mbps" else "0.0 Mbps",
            color = CyberCyan,
            modifier = Modifier.weight(1f)
          )
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          // Latencia
          MetricTile(
            icon = Icons.Default.Speed,
            label = "LATENCIA (PING)",
            value = if (internet.isConnected) "${internet.latencyPingMs} ms" else "--",
            color = WarningGold,
            modifier = Modifier.weight(1f)
          )

          // Jitter
          MetricTile(
            icon = Icons.Default.SatelliteAlt,
            label = "JITTER ORBITAL",
            value = if (internet.isConnected) "${internet.jitterMs} ms" else "--",
            color = QuantumPurple,
            modifier = Modifier.weight(1f)
          )
        }

        // Detalles de Red y Privacidad
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(ObsidianElevated)
            .padding(12.dp),
          verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("IP Soberana Asignada:", fontSize = 10.sp, color = CyberTextMuted)
            Text(
              internet.allocatedIp,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = CyberTextPrimary,
              fontFamily = FontFamily.Monospace
            )
          }
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("Proveedor DNS Cuántico:", fontSize = 10.sp, color = CyberTextMuted)
            Text(
              internet.dnsProvider,
              fontSize = 9.sp,
              fontWeight = FontWeight.Medium,
              color = CyberCyan
            )
          }
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("Túnel VPN PQC:", fontSize = 10.sp, color = CyberTextMuted)
            Text(
              if (internet.vpnTunnelActive) "WireGuard Post-Quantum ACTIVO" else "DIRECTO",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = if (internet.vpnTunnelActive) CyberEmerald else WarningGold
            )
          }
        }
      }
    }

    // 3. Test de Velocidad Satelital en Vivo (Speedtest)
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("card_satellite_speedtest"),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
      border = BorderStroke(1.dp, ObsidianBorder)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "SPEEDTEST SATELITAL LEO DIRECT-TO-CELL",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = CyberCyan,
            letterSpacing = 1.sp
          )
          if (internet.isSpeedTesting) {
            Text("Midiendo...", fontSize = 10.sp, color = WarningGold, fontWeight = FontWeight.Bold)
          }
        }

        if (internet.isSpeedTesting) {
          LinearProgressIndicator(
            progress = { internet.speedTestProgress },
            modifier = Modifier
              .fillMaxWidth()
              .height(6.dp)
              .clip(RoundedCornerShape(3.dp)),
            color = CyberEmerald,
            trackColor = ObsidianElevated
          )
          Text(
            text = internet.speedTestPhase,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace,
            color = CyberTextSecondary
          )
        }

        Button(
          onClick = onRunSpeedTest,
          enabled = internet.isConnected && !internet.isSpeedTesting,
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("button_run_satellite_speedtest"),
          shape = RoundedCornerShape(12.dp),
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
            Icon(Icons.Default.RocketLaunch, contentDescription = null, modifier = Modifier.size(18.dp))
            Text(
              if (internet.isSpeedTesting) "EJECUTANDO SPEEDTEST..." else "INICIAR SPEEDTEST SATELITAL",
              fontSize = 12.sp,
              fontWeight = FontWeight.Black
            )
          }
        }
      }
    }

    // 4. Navegación Web Satelital (Abrir Sitios Reales en Android)
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("card_satellite_web_launcher"),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
      border = BorderStroke(1.dp, Brush.horizontalGradient(listOf(CyberCyan, ObsidianBorder)))
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          Icon(Icons.Default.Language, contentDescription = null, tint = CyberCyan, modifier = Modifier.size(18.dp))
          Text(
            text = "PUERTA DE ENLACE WEB SATELITAL",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = CyberCyan,
            letterSpacing = 1.sp
          )
        }

        Text(
          text = "Abre sitios web en tu navegador Android utilizando el canal de internet satelital LEO activo:",
          fontSize = 11.sp,
          color = CyberTextSecondary
        )

        // Botones de acceso rápido a sitios web populares
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          QuickWebButton(
            title = "Google",
            url = "https://www.google.com",
            context = context,
            modifier = Modifier.weight(1f)
          )
          QuickWebButton(
            title = "Test 1.1.1.1",
            url = "https://1.1.1.1/help",
            context = context,
            modifier = Modifier.weight(1f)
          )
          QuickWebButton(
            title = "Fast.com",
            url = "https://fast.com",
            context = context,
            modifier = Modifier.weight(1f)
          )
        }

        // Campo para ingresar cualquier URL personalizada
        OutlinedTextField(
          value = customUrlInput,
          onValueChange = { customUrlInput = it },
          label = { Text("URL o Dirección Web", fontSize = 11.sp) },
          placeholder = { Text("https://...", fontSize = 11.sp) },
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_satellite_web_url"),
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

        Button(
          onClick = {
            try {
              val target = if (customUrlInput.startsWith("http://") || customUrlInput.startsWith("https://")) {
                customUrlInput
              } else {
                "https://$customUrlInput"
              }
              val intent = Intent(Intent.ACTION_VIEW, Uri.parse(target)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
              }
              context.startActivity(intent)
            } catch (_: Exception) {}
          },
          enabled = customUrlInput.isNotBlank(),
          modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .testTag("button_open_browser_satellite"),
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = CyberCyan,
            contentColor = ObsidianBackground
          )
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(Icons.Default.OpenInBrowser, contentDescription = null, modifier = Modifier.size(18.dp))
            Text("ABRIR EN NAVEGADOR DE ANDROID", fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // 5. Ajustes de Seguridad y Túnel Cuántico
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("card_satellite_security_controls"),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
      border = BorderStroke(1.dp, ObsidianBorder)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Text(
          text = "SEGURIDAD DE CANAL & PRIVACIDAD",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = CyberCyan,
          letterSpacing = 1.sp
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Icons.Default.VpnKey, contentDescription = null, tint = CyberEmerald, modifier = Modifier.size(18.dp))
            Column {
              Text("Túnel VPN PQC (WireGuard Orbit)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = CyberTextPrimary)
              Text("Cifrado extremo a extremo directo al satélite", fontSize = 10.sp, color = CyberTextMuted)
            }
          }
          Switch(
            checked = internet.vpnTunnelActive,
            onCheckedChange = { onToggleVpn() },
            colors = SwitchDefaults.colors(
              checkedThumbColor = ObsidianBackground,
              checkedTrackColor = CyberEmerald,
              uncheckedThumbColor = CyberTextMuted,
              uncheckedTrackColor = ObsidianElevated
            )
          )
        }

        HorizontalDivider(color = ObsidianBorder)

        Text("Servidor DNS Post-Cuántico Seleccionado:", fontSize = 10.sp, color = CyberTextMuted)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          val dnsOptions = listOf(
            "1.1.1.1 (Cloudflare DoH)" to "1.1.1.1 / 1.0.0.1",
            "9.9.9.9 (Quad9 Anti-Malware)" to "9.9.9.9 / 149.112.112.112",
            "DNS Soberano Armas" to "10.99.0.1 (Soberano LEO)"
          )
          dnsOptions.forEach { (name, label) ->
            val isSelected = internet.dnsProvider.contains(name.take(7))
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(if (isSelected) CyberCyan.copy(alpha = 0.2f) else ObsidianElevated)
                .border(1.dp, if (isSelected) CyberCyan else ObsidianBorder, RoundedCornerShape(8.dp))
                .clickable { onSetDnsProvider(name) }
                .padding(vertical = 8.dp, horizontal = 4.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = name.split(" ").first(),
                fontSize = 10.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) CyberCyan else CyberTextSecondary,
                textAlign = TextAlign.Center
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun MetricTile(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  label: String,
  value: String,
  color: Color,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .background(ObsidianElevated)
      .border(1.dp, ObsidianBorder, RoundedCornerShape(12.dp))
      .padding(12.dp)
  ) {
    Column {
      Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
        Text(label, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = CyberTextMuted)
      }
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = value,
        fontSize = 14.sp,
        fontWeight = FontWeight.Black,
        color = CyberTextPrimary,
        fontFamily = FontFamily.Monospace
      )
    }
  }
}

@Composable
private fun QuickWebButton(
  title: String,
  url: String,
  context: android.content.Context,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(8.dp))
      .background(ObsidianElevated)
      .border(1.dp, ObsidianBorder, RoundedCornerShape(8.dp))
      .clickable {
        try {
          val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
          }
          context.startActivity(intent)
        } catch (_: Exception) {}
      }
      .padding(vertical = 10.dp),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = title,
      fontSize = 11.sp,
      fontWeight = FontWeight.Bold,
      color = CyberCyan
    )
  }
}
