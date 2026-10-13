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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SovereignLicenseInfo
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
import com.example.ui.theme.WarningGold

@Composable
fun LicenseCertificateScreen(
  license: SovereignLicenseInfo,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianBackground)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(8.dp))

      // Main Certificate Parchment Card
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("sovereign_license_certificate_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(
          2.dp,
          Brush.verticalGradient(listOf(WarningGold, CyberCyan, CyberEmerald))
        )
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Box(
            modifier = Modifier
              .size(56.dp)
              .clip(CircleShape)
              .background(WarningGold.copy(alpha = 0.15f))
              .border(1.5.dp, WarningGold, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.WorkspacePremium,
              contentDescription = "Insignia Soberana",
              tint = WarningGold,
              modifier = Modifier.size(32.dp)
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          Text(
            text = "CERTIFICADO DE TITULARIDAD SOBERANA",
            color = WarningGold,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
          )

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = "Registro Oficial de Software y Red",
            color = CyberTextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Black
          )

          Spacer(modifier = Modifier.height(14.dp))

          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(ObsidianElevated)
              .border(1.dp, ObsidianBorder, RoundedCornerShape(12.dp))
              .padding(14.dp),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = "TITULAR Y CREADOR EXCLUSIVO:",
                color = CyberEmerald,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = license.ownerName,
                color = CyberTextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = license.role,
                color = CyberTextSecondary,
                fontSize = 11.sp,
                textAlign = TextAlign.Center
              )
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          CertificateField(label = "SOFTWARE REGISTRADO", value = license.softwareTitle)
          CertificateField(label = "CÓDIGO DE PATENTE CRIPTOGRÁFICA", value = license.patentReference)
          CertificateField(label = "HUELLA RAÍZ MAESTRA (HSM ROOT)", value = license.masterFingerprint)
          CertificateField(label = "VALUACIÓN ESTIMADA DE LA RED", value = license.estimatedNetworkValuation)
          CertificateField(label = "PISO DE CONTRATACIÓN EMPRESARIAL", value = license.minEnterpriseAccessFee)
          CertificateField(label = "RÉGIMEN LEGAL Y CRIPTOGRÁFICO", value = license.licenseType)

          Spacer(modifier = Modifier.height(14.dp))

          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(10.dp))
              .background(CyberEmerald.copy(alpha = 0.08f))
              .border(1.dp, CyberEmerald.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
              .padding(12.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Verified,
                contentDescription = "Verificado",
                tint = CyberEmerald,
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = "La red cuántica, sus algoritmos post-cuánticos, su Centinela IA activo y todos los componentes de software pertenecen de manera irrevocable a Alejandro Armas.",
                color = CyberEmerald,
                fontSize = 11.sp,
                lineHeight = 15.sp
              )
            }
          }
        }
      }
    }

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
            .padding(16.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Gavel,
              contentDescription = "Marco Legal",
              tint = CyberCyan,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "DECLARACIÓN DE INVIOLABILIDAD",
              color = CyberCyan,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp
            )
          }
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Esta red implementa matemáticas de retículos de dimensión infinita (Learning With Errors - LWE), combinada con distribución cuántica de claves (QKD) y el Centinela IA autónomo. No existe computadora cuántica ni método algorítmico conocido que pueda vulnerar su perímetro de seguridad.",
            color = CyberTextSecondary,
            fontSize = 11.sp,
            lineHeight = 16.sp
          )
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(20.dp))
    }
  }
}

@Composable
fun CertificateField(label: String, value: String) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp)
  ) {
    Text(
      text = label,
      color = CyberTextMuted,
      fontSize = 9.sp,
      fontWeight = FontWeight.Bold,
      letterSpacing = 0.5.sp
    )
    Text(
      text = value,
      color = CyberTextPrimary,
      fontSize = 12.sp,
      fontWeight = FontWeight.SemiBold,
      fontFamily = if (value.contains("::") || value.contains("ARMAS-")) FontFamily.Monospace else FontFamily.Default
    )
  }
}
