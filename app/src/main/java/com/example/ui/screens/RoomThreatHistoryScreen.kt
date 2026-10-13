package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.ThreatLogEntity
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
fun RoomThreatHistoryScreen(
  threatLogs: List<ThreatLogEntity>,
  onDeleteLog: (Long) -> Unit,
  onClearAll: () -> Unit,
  onSimulateTestThreat: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var searchQuery by remember { mutableStateOf("") }
  var selectedFilter by remember { mutableStateOf("TODAS") }

  val filteredLogs = remember(threatLogs, searchQuery, selectedFilter) {
    threatLogs.filter { log ->
      val matchesSearch = searchQuery.isBlank() ||
        log.threatName.contains(searchQuery, ignoreCase = true) ||
        log.category.contains(searchQuery, ignoreCase = true) ||
        log.sourceVector.contains(searchQuery, ignoreCase = true) ||
        log.signatureHash.contains(searchQuery, ignoreCase = true) ||
        (log.interceptingAgent?.contains(searchQuery, ignoreCase = true) == true)

      val matchesFilter = when (selectedFilter) {
        "CRÍTICAS" -> log.severity == "CRITICAL"
        "ALTAS" -> log.severity == "HIGH"
        "CUARENTENA" -> log.status == "QUARANTINED"
        "NEUTRALIZADAS" -> log.status == "NEUTRALIZED"
        else -> true
      }

      matchesSearch && matchesFilter
    }
  }

  val criticalCount = remember(threatLogs) { threatLogs.count { it.severity == "CRITICAL" } }
  val highCount = remember(threatLogs) { threatLogs.count { it.severity == "HIGH" } }
  val quarantinedCount = remember(threatLogs) { threatLogs.count { it.status == "QUARANTINED" } }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .background(ObsidianBackground)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    Spacer(modifier = Modifier.height(8.dp))
    // Master Room Header Banner
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("room_database_banner_card"),
      shape = RoundedCornerShape(18.dp),
      colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
      border = androidx.compose.foundation.BorderStroke(
        1.5.dp,
        Brush.horizontalGradient(listOf(CyberCyan, CyberEmerald, QuantumPurple))
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
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(CyberCyan.copy(alpha = 0.15f))
                .border(1.dp, CyberCyan, RoundedCornerShape(12.dp)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Storage,
                contentDescription = "Room DB",
                tint = CyberCyan,
                modifier = Modifier.size(24.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(CyberEmerald)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "BASE DE DATOS LOCAL ROOM",
                  color = CyberCyan,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Black,
                  letterSpacing = 1.sp
                )
              }
              Text(
                text = "Registro Histórico de Amenazas",
                color = CyberTextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(CyberEmerald.copy(alpha = 0.15f))
              .border(1.dp, CyberEmerald.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text(
              text = "${threatLogs.size} REGISTROS",
              color = CyberEmerald,
              fontSize = 10.sp,
              fontWeight = FontWeight.Black
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
          text = "Persistencia local en Room SQLite (armas_cyber_security.db): almacena de forma inmutable todas las amenazas, virus y ataques neutralizados por el Enjambre IA para auditoría forense post-cuántica.",
          color = CyberTextSecondary,
          fontSize = 11.sp,
          lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Stats Metrics Row
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          RoomStatBadge(
            label = "Total Histórico",
            value = "${threatLogs.size}",
            color = CyberCyan,
            modifier = Modifier.weight(1f)
          )
          RoomStatBadge(
            label = "Críticas",
            value = "$criticalCount",
            color = ThreatCrimson,
            modifier = Modifier.weight(1f)
          )
          RoomStatBadge(
            label = "Altas",
            value = "$highCount",
            color = WarningGold,
            modifier = Modifier.weight(1f)
          )
          RoomStatBadge(
            label = "Bóveda",
            value = "$quarantinedCount",
            color = QuantumPurple,
            modifier = Modifier.weight(1f)
          )
        }
      }
    }

    // Quick injection toolbar & clear database actions
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
            text = "INYECCIÓN DE MUESTRA A BASE DE DATOS",
            color = CyberCyan,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.8.sp
          )
          if (threatLogs.isNotEmpty()) {
            OutlinedButton(
              onClick = onClearAll,
              modifier = Modifier
                .height(32.dp)
                .testTag("btn_clear_room_db"),
              shape = RoundedCornerShape(6.dp),
              colors = ButtonDefaults.outlinedButtonColors(contentColor = ThreatCrimson),
              border = androidx.compose.foundation.BorderStroke(1.dp, ThreatCrimson.copy(alpha = 0.6f)),
              contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
            ) {
              Icon(Icons.Default.DeleteSweep, contentDescription = "Limpiar DB", modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Limpiar DB", fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Button(
            onClick = { onSimulateTestThreat("ransomware") },
            modifier = Modifier
              .weight(1f)
              .height(36.dp)
              .testTag("btn_record_ransomware"),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ThreatCrimson),
            contentPadding = PaddingValues(horizontal = 6.dp)
          ) {
            Icon(Icons.Default.BugReport, contentDescription = null, modifier = Modifier.size(13.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("+ Ransomware", fontSize = 10.sp, fontWeight = FontWeight.Bold)
          }

          Button(
            onClick = { onSimulateTestThreat("spyware") },
            modifier = Modifier
              .weight(1f)
              .height(36.dp)
              .testTag("btn_record_spyware"),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = QuantumPurple),
            contentPadding = PaddingValues(horizontal = 6.dp)
          ) {
            Icon(Icons.Default.Psychology, contentDescription = null, modifier = Modifier.size(13.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("+ Spyware", fontSize = 10.sp, fontWeight = FontWeight.Bold)
          }

          Button(
            onClick = { onSimulateTestThreat("quantum") },
            modifier = Modifier
              .weight(1f)
              .height(36.dp)
              .testTag("btn_record_quantum"),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CyberCyan, contentColor = ObsidianBackground),
            contentPadding = PaddingValues(horizontal = 6.dp)
          ) {
            Icon(Icons.Default.Shield, contentDescription = null, modifier = Modifier.size(13.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("+ ZeroDay", fontSize = 10.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // Search and Filter controls
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("room_search_field"),
        placeholder = {
          Text("Buscar por nombre, firma hash, vector o dron...", color = CyberTextMuted, fontSize = 11.sp)
        },
        leadingIcon = {
          Icon(Icons.Default.Search, contentDescription = "Buscar", tint = CyberCyan, modifier = Modifier.size(18.dp))
        },
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = CyberCyan,
          unfocusedBorderColor = ObsidianBorder,
          focusedTextColor = CyberTextPrimary,
          unfocusedTextColor = CyberTextPrimary,
          focusedContainerColor = ObsidianSurface,
          unfocusedContainerColor = ObsidianSurface
        ),
        singleLine = true
      )

      // Filter chips row
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        val filters = listOf("TODAS", "CRÍTICAS", "ALTAS", "CUARENTENA", "NEUTRALIZADAS")
        items(filters) { filter ->
          val isSelected = selectedFilter == filter
          FilterChip(
            selected = isSelected,
            onClick = { selectedFilter = filter },
            label = {
              Text(
                text = filter,
                fontSize = 10.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
              )
            },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = CyberCyan.copy(alpha = 0.2f),
              selectedLabelColor = CyberCyan,
              containerColor = ObsidianElevated,
              labelColor = CyberTextMuted
            ),
            border = FilterChipDefaults.filterChipBorder(
              borderColor = if (isSelected) CyberCyan else ObsidianBorder,
              selectedBorderColor = CyberCyan,
              enabled = true,
              selected = isSelected
            )
          )
        }
      }
    }

    // Threat count summary header
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "HISTORIAL EN TABLA SQLITE (${filteredLogs.size})",
        color = CyberCyan,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      )
      Text(
        text = "Persistido en Disco",
        color = CyberEmerald,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold
      )
    }

    // List of saved threat entities
    if (filteredLogs.isEmpty()) {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
      ) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = "Sin registros",
              tint = CyberEmerald,
              modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = if (threatLogs.isEmpty()) "No hay amenazas en la base de datos Room" else "No hay amenazas que coincidan con el filtro",
              color = CyberTextPrimary,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Presiona los botones superiores para registrar y persistir amenazas.",
              color = CyberTextSecondary,
              fontSize = 11.sp
            )
          }
        }
      }
    } else {
      filteredLogs.forEach { log ->
        RoomThreatEntityCard(
          entity = log,
          onDelete = { onDeleteLog(log.id) }
        )
      }
    }

    Spacer(modifier = Modifier.height(24.dp))
  }
}

@Composable
fun RoomStatBadge(
  label: String,
  value: String,
  color: Color,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(8.dp))
      .background(ObsidianElevated)
      .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
      .padding(vertical = 6.dp, horizontal = 4.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(
        text = value,
        color = color,
        fontSize = 14.sp,
        fontWeight = FontWeight.Black,
        fontFamily = FontFamily.Monospace
      )
      Text(
        text = label,
        color = CyberTextMuted,
        fontSize = 8.sp,
        fontWeight = FontWeight.Medium,
        maxLines = 1
      )
    }
  }
}

@Composable
fun RoomThreatEntityCard(
  entity: ThreatLogEntity,
  onDelete: () -> Unit,
  modifier: Modifier = Modifier
) {
  var isExpanded by remember { mutableStateOf(false) }

  val severityColor = when (entity.severity) {
    "CRITICAL" -> ThreatCrimson
    "HIGH" -> WarningGold
    "MEDIUM" -> CyberCyan
    else -> CyberEmerald
  }

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("room_threat_card_${entity.id}")
      .clickable { isExpanded = !isExpanded },
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      if (entity.severity == "CRITICAL") ThreatCrimson.copy(alpha = 0.4f) else ObsidianBorder
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
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f)
        ) {
          Box(
            modifier = Modifier
              .size(8.dp)
              .clip(CircleShape)
              .background(severityColor)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Column {
            Text(
              text = entity.threatName,
              color = CyberTextPrimary,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace
            )
            Text(
              text = "${entity.category} • ${entity.timestamp}",
              color = CyberTextMuted,
              fontSize = 10.sp
            )
          }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(severityColor.copy(alpha = 0.15f))
              .border(1.dp, severityColor.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = entity.severity,
              color = severityColor,
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Spacer(modifier = Modifier.width(4.dp))

          IconButton(
            onClick = onDelete,
            modifier = Modifier
              .size(28.dp)
              .testTag("btn_delete_threat_${entity.id}")
          ) {
            Icon(
              imageVector = Icons.Default.Delete,
              contentDescription = "Eliminar de Room",
              tint = CyberTextMuted,
              modifier = Modifier.size(15.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Vector & Intercepting Agent
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(6.dp))
          .background(ObsidianElevated)
          .padding(8.dp)
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "Vector: ${entity.sourceVector}",
              color = CyberTextSecondary,
              fontSize = 10.sp,
              maxLines = 1
            )
            Text(
              text = "ID Room: #${entity.id}",
              color = CyberCyan,
              fontSize = 9.sp,
              fontFamily = FontFamily.Monospace
            )
          }
          if (entity.interceptingAgent != null) {
            Text(
              text = "Interceptado por: ${entity.interceptingAgent}",
              color = CyberEmerald,
              fontSize = 10.sp,
              fontWeight = FontWeight.SemiBold
            )
          }
        }
      }

      // Expandable details (AI Mitigation Log & Signature Hash)
      AnimatedVisibility(visible = isExpanded) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(6.dp))
              .background(ObsidianElevated)
              .border(1.dp, CyberEmerald.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
              .padding(8.dp)
          ) {
            Column {
              Text(
                text = "LOG DE MITIGACIÓN IA SOBERANA:",
                color = CyberEmerald,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = entity.aiMitigationLog,
                color = CyberTextSecondary,
                fontSize = 10.sp,
                lineHeight = 14.sp
              )
            }
          }

          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(6.dp))
              .background(ObsidianElevated)
              .padding(8.dp)
          ) {
            Column {
              Text(
                text = "FIRMA HASH CRIPTOGRÁFICA (SHA-256):",
                color = CyberCyan,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = entity.signatureHash,
                color = CyberCyan,
                fontSize = 9.sp,
                fontFamily = FontFamily.Monospace,
                lineHeight = 12.sp
              )
            }
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Estado: ${entity.status} • Acción: ${entity.actionTaken}",
              color = CyberTextMuted,
              fontSize = 9.sp
            )
            Text(
              text = "UID: ${entity.threatUid}",
              color = CyberTextMuted,
              fontSize = 9.sp,
              fontFamily = FontFamily.Monospace
            )
          }
        }
      }

      if (!isExpanded) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = "Toca para ver detalles forenses, firma SHA-256 y mitigación IA...",
          color = CyberTextMuted,
          fontSize = 9.sp
        )
      }
    }
  }
}
