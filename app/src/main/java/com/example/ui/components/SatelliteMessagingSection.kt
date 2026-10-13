package com.example.ui.components

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.SatelliteAlt
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material.icons.filled.Sos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ArmasSatelliteChipState
import com.example.model.ArmasSatelliteMessage
import com.example.model.SatelliteMessageStatus
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
fun SatelliteMessagingSection(
  satState: ArmasSatelliteChipState,
  onSendSatelliteMessage: (recipient: String, content: String, viaNativeSms: Boolean, context: android.content.Context) -> Unit,
  onUpdateDraftRecipient: (String) -> Unit,
  onUpdateDraftMessage: (String) -> Unit,
  onSendEmergencySosMessage: () -> Unit,
  onClearMessages: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val messaging = satState.messagingState

  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Tarjeta Informativa de Mensajería Orbital
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("card_satellite_messaging_header"),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
      border = BorderStroke(1.dp, Brush.horizontalGradient(listOf(CyberCyan, QuantumPurple)))
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
                .background(CyberCyan.copy(alpha = 0.15f))
                .border(1.dp, CyberCyan, RoundedCornerShape(10.dp)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Message,
                contentDescription = null,
                tint = CyberCyan,
                modifier = Modifier.size(24.dp)
              )
            }
            Column {
              Text(
                text = "MENSAJERÍA SATELITAL DIRECT-TO-CELL",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = CyberTextPrimary,
                letterSpacing = 0.8.sp
              )
              Text(
                text = "SMS Orbital Soberano & Transmisión Cifrada PQC",
                fontSize = 11.sp,
                color = CyberCyan
              )
            }
          }
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(CyberEmerald.copy(alpha = 0.2f))
              .border(1.dp, CyberEmerald, RoundedCornerShape(6.dp))
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Text("ÓRBITA LEO ACTIVA", fontSize = 9.sp, fontWeight = FontWeight.Black, color = CyberEmerald)
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = "Envía mensajes de texto directamente al haz de la constelación ARMAS-SAT sin antenas intermediarias ni operadoras comerciales. Compatible con SMS satelital directo y la aplicación de mensajes de Android.",
          fontSize = 11.sp,
          color = CyberTextSecondary,
          lineHeight = 16.sp
        )
      }
    }

    // 2. Redacción de Mensaje Satelital
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("card_compose_satellite_message"),
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
          text = "REDACCIÓN DE MENSAJE SATELITAL",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = CyberCyan,
          letterSpacing = 1.sp
        )

        // Contactos rápidos sugeridos
        if (satState.defaultContacts.isNotEmpty()) {
          Text(
            text = "Líneas VIP Soberanas (Un toque):",
            fontSize = 10.sp,
            color = CyberTextMuted
          )
          LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            items(satState.defaultContacts) { contact ->
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(8.dp))
                  .background(ObsidianElevated)
                  .border(1.dp, ObsidianBorder, RoundedCornerShape(8.dp))
                  .clickable { onUpdateDraftRecipient(contact.satelliteNumber) }
                  .padding(horizontal = 8.dp, vertical = 5.dp)
              ) {
                Text(
                  text = "${contact.name} (${contact.satelliteNumber})",
                  fontSize = 10.sp,
                  color = CyberCyan
                )
              }
            }
          }
        }

        // Campo de Destinatario
        OutlinedTextField(
          value = messaging.draftRecipient,
          onValueChange = onUpdateDraftRecipient,
          label = { Text("Número o Destinatario Satelital", fontSize = 11.sp) },
          placeholder = { Text("+999 ARMAS-770... o número celular", fontSize = 11.sp) },
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_satellite_message_recipient"),
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

        // Plantillas rápidas de mensajes
        Text(
          text = "Plantillas Rápidas:",
          fontSize = 10.sp,
          color = CyberTextMuted
        )
        LazyRow(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          val templates = listOf(
            "🛰️ Enlace satelital seguro activo desde el Chip Armas.",
            "📍 Coordenadas y telemetría confirmadas por LEO Beam.",
            "📡 Conexión directa establecida sin cobertura celular terrestre.",
            "🛡️ Canal cifrado con Post-Quantum ML-KEM-1024."
          )
          items(templates) { template ->
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(ObsidianElevated)
                .border(1.dp, ObsidianBorder, RoundedCornerShape(8.dp))
                .clickable { onUpdateDraftMessage(template) }
                .padding(horizontal = 8.dp, vertical = 5.dp)
            ) {
              Text(
                text = template,
                fontSize = 10.sp,
                color = CyberTextSecondary,
                maxLines = 1
              )
            }
          }
        }

        // Campo de Contenido del Mensaje
        OutlinedTextField(
          value = messaging.draftMessage,
          onValueChange = onUpdateDraftMessage,
          label = { Text("Contenido del Mensaje Satelital / SMS", fontSize = 11.sp) },
          placeholder = { Text("Escribe el mensaje a transmitir por la constelación...", fontSize = 11.sp) },
          minLines = 3,
          maxLines = 5,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_satellite_message_content"),
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

        // Botones de Transmisión
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Botón Satelital Directo
          Button(
            onClick = {
              onSendSatelliteMessage(messaging.draftRecipient, messaging.draftMessage, false, context)
            },
            enabled = messaging.draftMessage.isNotBlank(),
            modifier = Modifier
              .weight(1f)
              .height(48.dp)
              .testTag("button_send_satellite_message"),
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
              Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
              Text("TRANSMITIR SATÉLITE", fontSize = 11.sp, fontWeight = FontWeight.Black)
            }
          }

          // Botón SMS Nativo de Android
          OutlinedButton(
            onClick = {
              onSendSatelliteMessage(messaging.draftRecipient, messaging.draftMessage, true, context)
            },
            enabled = messaging.draftMessage.isNotBlank(),
            modifier = Modifier
              .weight(1f)
              .height(48.dp)
              .testTag("button_send_native_sms"),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, CyberEmerald),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = CyberEmerald)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Icon(Icons.Default.Sms, contentDescription = null, modifier = Modifier.size(16.dp))
              Text("ENVIAR POR SMS", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }
        }

        // Botón SOS de Emergencia vía SMS Satelital
        Button(
          onClick = onSendEmergencySosMessage,
          modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .testTag("button_send_emergency_sos_message"),
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = ThreatCrimson.copy(alpha = 0.9f),
            contentColor = Color.White
          )
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(Icons.Default.Sos, contentDescription = null, modifier = Modifier.size(18.dp))
            Text("TRANSMITIR BALIZA SOS POR SMS CON GPS", fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // 3. Buzón de Mensajes Satelitales (Conversación Orbital)
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("card_satellite_message_history"),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
      border = BorderStroke(1.dp, ObsidianBorder)
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "BUZÓN DE TRANSMISIONES SATELITALES",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = CyberCyan,
            letterSpacing = 1.sp
          )
          if (messaging.messages.isNotEmpty()) {
            Text(
              text = "Limpiar Buzón",
              fontSize = 11.sp,
              color = ThreatCrimson,
              modifier = Modifier
                .clickable { onClearMessages() }
                .padding(4.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (messaging.messages.isEmpty()) {
          Text(
            text = "No hay mensajes en la bandeja satelital. Redacta uno arriba para transmitirlo por la constelación LEO.",
            fontSize = 11.sp,
            color = CyberTextMuted,
            textAlign = TextAlign.Center,
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 20.dp)
          )
        } else {
          Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            messaging.messages.forEach { msg ->
              SatelliteMessageBubble(msg = msg)
            }
          }
        }
      }
    }
  }
}

@Composable
private fun SatelliteMessageBubble(msg: ArmasSatelliteMessage) {
  val isOut = msg.isOutgoing
  val containerColor = if (isOut) ObsidianElevated else CyberCyan.copy(alpha = 0.10f)
  val borderColor = if (isOut) ObsidianBorder else CyberCyan.copy(alpha = 0.35f)

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(containerColor)
      .border(1.dp, borderColor, RoundedCornerShape(12.dp))
      .padding(12.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        Icon(
          imageVector = if (isOut) Icons.Default.Send else Icons.Default.SatelliteAlt,
          contentDescription = null,
          tint = if (isOut) CyberEmerald else CyberCyan,
          modifier = Modifier.size(14.dp)
        )
        Text(
          text = if (isOut) "Hacia: ${msg.recipientName}" else "De: ${msg.recipientName}",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = CyberTextPrimary
        )
      }

      Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(msg.timestamp, fontSize = 10.sp, color = CyberTextMuted)
        when (msg.status) {
          SatelliteMessageStatus.TRANSMITTING_TO_ORBIT -> {
            Icon(Icons.Default.HourglassTop, contentDescription = null, tint = WarningGold, modifier = Modifier.size(12.dp))
          }
          SatelliteMessageStatus.DELIVERED_LEO -> {
            Icon(Icons.Default.Check, contentDescription = null, tint = CyberCyan, modifier = Modifier.size(12.dp))
          }
          SatelliteMessageStatus.CONFIRMED_PQC -> {
            Icon(Icons.Default.DoneAll, contentDescription = null, tint = CyberEmerald, modifier = Modifier.size(14.dp))
          }
          SatelliteMessageStatus.FAILED -> {
            Icon(Icons.Default.Sos, contentDescription = null, tint = ThreatCrimson, modifier = Modifier.size(12.dp))
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(6.dp))

    Text(
      text = msg.content,
      fontSize = 12.sp,
      color = CyberTextPrimary,
      lineHeight = 17.sp
    )

    Spacer(modifier = Modifier.height(6.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Icon(Icons.Default.Lock, contentDescription = null, tint = CyberCyan, modifier = Modifier.size(10.dp))
        Text(msg.protocol, fontSize = 9.sp, color = CyberCyan, fontFamily = FontFamily.Monospace)
      }
      Text(
        text = msg.pqcSignatureHash.take(18) + "...",
        fontSize = 8.sp,
        color = CyberTextMuted,
        fontFamily = FontFamily.Monospace
      )
    }
  }
}
