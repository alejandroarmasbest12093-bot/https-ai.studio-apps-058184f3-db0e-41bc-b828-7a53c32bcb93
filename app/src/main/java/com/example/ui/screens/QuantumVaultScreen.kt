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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.EnhancedEncryption
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.viewmodel.CyberUiState

@Composable
fun QuantumVaultScreen(
  state: CyberUiState,
  onEncryptPayload: (String) -> Unit,
  onDecryptPayload: (String) -> Unit,
  onToggleRule: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var plainTextToEncrypt by remember { mutableStateOf("Acuerdo de Fusión y Claves de Seguridad $10M - Estrictamente Confidencial") }
  var cipherTextToDecrypt by remember { mutableStateOf("") }
  val clipboardManager: ClipboardManager = LocalClipboardManager.current

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianBackground)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(8.dp))
      // Tool Suite Header
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, QuantumPurple.copy(alpha = 0.5f))
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "SUITE DE HERRAMIENTAS CRIPTOGRÁFICAS",
              color = QuantumPurple,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp
            )
            Icon(
              imageVector = Icons.Default.EnhancedEncryption,
              contentDescription = "Bóveda",
              tint = QuantumPurple,
              modifier = Modifier.size(20.dp)
            )
          }

          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Bóveda & Cifrado Post-Cuántica",
            color = CyberTextPrimary,
            fontSize = 17.sp,
            fontWeight = FontWeight.Black
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Herramientas operativas de la red de Alejandro Armas: Encripta cargas de datos corporativos contra cualquier intento de espionaje o hackeo cuántico.",
            color = CyberTextSecondary,
            fontSize = 12.sp,
            lineHeight = 16.sp
          )
        }
      }
    }

    // Encryption Utility
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("encryption_tool_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          Text(
            text = "1. CIFRADOR DE RETÍCULOS CUÁNTICOS (KYBER-1024)",
            color = CyberCyan,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = plainTextToEncrypt,
            onValueChange = { plainTextToEncrypt = it },
            label = { Text("Texto o Carga Confidencial a Cifrar") },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("payload_input"),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = CyberCyan,
              unfocusedBorderColor = ObsidianBorder,
              focusedTextColor = CyberTextPrimary,
              unfocusedTextColor = CyberTextPrimary
            ),
            minLines = 2
          )

          Spacer(modifier = Modifier.height(10.dp))

          Button(
            onClick = { onEncryptPayload(plainTextToEncrypt) },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("encrypt_button"),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CyberCyan)
          ) {
            Icon(
              imageVector = Icons.Default.Lock,
              contentDescription = "Cifrar",
              tint = ObsidianBackground,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Cifrar Carga con Licencia Soberana de Alejandro Armas",
              color = ObsidianBackground,
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp
            )
          }

          if (state.encryptedVaultOutput.isNotBlank()) {
            Spacer(modifier = Modifier.height(12.dp))
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(ObsidianElevated)
                .border(1.dp, CyberCyan.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                .padding(10.dp)
            ) {
              Column {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = "CRIPTOGRAMA RESULTANTE (INVIOLABLE):",
                    color = CyberEmerald,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                  )
                  IconButton(
                    onClick = {
                      clipboardManager.setText(AnnotatedString(state.encryptedVaultOutput))
                      cipherTextToDecrypt = state.encryptedVaultOutput
                    },
                    modifier = Modifier.size(24.dp)
                  ) {
                    Icon(
                      imageVector = Icons.Default.ContentCopy,
                      contentDescription = "Copiar",
                      tint = CyberCyan,
                      modifier = Modifier.size(16.dp)
                    )
                  }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = state.encryptedVaultOutput,
                  color = CyberCyan,
                  fontSize = 10.sp,
                  fontFamily = FontFamily.Monospace,
                  lineHeight = 14.sp
                )
              }
            }
          }
        }
      }
    }

    // Decryption Utility
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("decryption_tool_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          Text(
            text = "2. VERIFICADOR Y DESENCRIPTADOR DE FIRMA SOBERANA",
            color = QuantumPurple,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = cipherTextToDecrypt,
            onValueChange = { cipherTextToDecrypt = it },
            label = { Text("Pegar Criptograma de la Red Armas") },
            placeholder = { Text("ARMAS-LATTICE-1024::...") },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("decrypt_input"),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = QuantumPurple,
              unfocusedBorderColor = ObsidianBorder,
              focusedTextColor = CyberTextPrimary,
              unfocusedTextColor = CyberTextPrimary
            ),
            minLines = 2
          )

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedButton(
            onClick = { onDecryptPayload(cipherTextToDecrypt) },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("decrypt_button"),
            shape = RoundedCornerShape(8.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, QuantumPurple)
          ) {
            Icon(
              imageVector = Icons.Default.LockOpen,
              contentDescription = "Desencriptar",
              tint = QuantumPurple,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Verificar Integridad & Desencriptar",
              color = QuantumPurple,
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp
            )
          }

          if (state.decryptedVaultOutput.isNotBlank()) {
            Spacer(modifier = Modifier.height(10.dp))
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(ObsidianElevated)
                .border(
                  1.dp,
                  if (state.decryptedVaultOutput.startsWith("ERROR")) ThreatCrimson else CyberEmerald,
                  RoundedCornerShape(8.dp)
                )
                .padding(10.dp)
            ) {
              Text(
                text = state.decryptedVaultOutput,
                color = if (state.decryptedVaultOutput.startsWith("ERROR")) ThreatCrimson else CyberEmerald,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
              )
            }
          }
        }
      }
    }

    // Defense Matrix Rules
    item {
      Text(
        text = "MATRIZ DE REGLAS ACTIVAS DEL CORTAFUEGOS",
        color = CyberCyan,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      )
    }

    items(state.defenseSettings) { setting ->
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = setting.title,
              color = CyberTextPrimary,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = setting.description,
              color = CyberTextMuted,
              fontSize = 11.sp,
              lineHeight = 15.sp
            )
          }
          Spacer(modifier = Modifier.width(8.dp))
          Switch(
            checked = setting.isEnabled,
            onCheckedChange = { onToggleRule(setting.id) },
            colors = SwitchDefaults.colors(
              checkedThumbColor = CyberEmerald,
              checkedTrackColor = CyberEmerald.copy(alpha = 0.2f)
            )
          )
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(20.dp))
    }
  }
}
