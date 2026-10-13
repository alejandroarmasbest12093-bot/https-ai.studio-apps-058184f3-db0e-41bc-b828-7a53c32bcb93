package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.InstallMobile
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.model.AppReleaseDetails
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
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.QuantumPurple
import com.example.ui.theme.ThreatCrimson
import com.example.ui.theme.WarningGold

@Composable
fun LaunchAndReleaseScreen(
  appRelease: AppReleaseDetails,
  license: SovereignLicenseInfo,
  isGeneratingApk: Boolean,
  apkDownloadUrl: String?,
  onTriggerApkDownload: () -> Unit,
  onNavigateToPayments: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  var activeTab by remember { mutableIntStateOf(0) } // 0: Lanzar & Descargar, 1: Titularidad Soberana

  fun copyToClipboard(label: String, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(context, "$label copiado al portapapeles", Toast.LENGTH_SHORT).show()
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianBackground)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(8.dp))

      // Header Banner
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("launch_header_card"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(
          1.5.dp,
          Brush.horizontalGradient(listOf(CyberCyan, CyberEmerald, WarningGold))
        )
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
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(10.dp)
                  .clip(CircleShape)
                  .background(CyberEmerald)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "CENTRO DE LANZAMIENTO & DESCARGA",
                color = CyberCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp
              )
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(CyberEmerald.copy(alpha = 0.15f))
                .border(1.dp, CyberEmerald.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(
                text = "v3.0.0 PRODUCCIÓN",
                color = CyberEmerald,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = "Armas Quantum Mesh™ v3.0.0",
            color = CyberTextPrimary,
            fontSize = 19.sp,
            fontWeight = FontWeight.Black
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Preparada para descarga directa en APK instalable, generación de Android App Bundle (AAB) y publicación en Google Play Console bajo la titularidad de Alejandro Armas.",
            color = CyberTextSecondary,
            fontSize = 12.sp,
            lineHeight = 17.sp
          )
        }
      }
    }

    // Tabs
    item {
      TabRow(
        selectedTabIndex = activeTab,
        containerColor = ObsidianSurface,
        contentColor = CyberCyan,
        indicator = { tabPositions ->
          TabRowDefaults.SecondaryIndicator(
            modifier = Modifier.tabIndicatorOffset(tabPositions[activeTab]),
            color = CyberCyan
          )
        },
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .testTag("launch_tabs")
      ) {
        Tab(
          selected = activeTab == 0,
          onClick = { activeTab = 0 },
          text = { Text("Descarga & Lanzamiento", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        Tab(
          selected = activeTab == 1,
          onClick = { activeTab = 1 },
          text = { Text("Titularidad Soberana", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
      }
    }

    // TAB 0: DESCARGA & LANZAMIENTO
    if (activeTab == 0) {
      // Release Binary Card
      item {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .testTag("release_binary_card"),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
          border = androidx.compose.foundation.BorderStroke(1.dp, CyberEmerald)
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
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(CyberEmerald.copy(alpha = 0.15f)),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.InstallMobile,
                    contentDescription = "APK",
                    tint = CyberEmerald,
                    modifier = Modifier.size(24.dp)
                  )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                  Text(
                    text = "PAQUETE OFICIAL DE PRODUCCIÓN",
                    color = CyberEmerald,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                  )
                  Text(
                    text = appRelease.apkDownloadFileName,
                    color = CyberTextPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Monospace
                  )
                }
              }

              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(6.dp))
                  .background(ObsidianElevated)
                  .padding(horizontal = 8.dp, vertical = 4.dp)
              ) {
                Text(
                  text = "${appRelease.estimatedSizeMb} MB",
                  color = CyberCyan,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Build Details
            ReleaseDetailRow("Versión Oficial:", "${appRelease.versionName} (Build ${appRelease.versionCode})")
            ReleaseDetailRow("Identificador (Package):", appRelease.packageId)
            ReleaseDetailRow("Plataforma Objetivo:", appRelease.targetPlatform)
            ReleaseDetailRow("Titular del Software:", license.ownerName)
            ReleaseDetailRow("Fecha de Compilación:", appRelease.buildDate)
            ReleaseDetailRow("Estado de Lanzamiento:", "LISTO PARA DESCARGA Y DISTRIBUCIÓN")

            Spacer(modifier = Modifier.height(10.dp))

            // SHA-256 Checksum Box
            Text("FIRMA DE INTEGRIDAD SHA-256:", color = CyberTextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(3.dp))
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .background(ObsidianElevated, RoundedCornerShape(8.dp))
                .border(1.dp, ObsidianBorder, RoundedCornerShape(8.dp))
                .padding(8.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = appRelease.sha256Signature,
                color = CyberCyan,
                fontSize = 9.sp,
                fontFamily = FontFamily.Monospace,
                lineHeight = 12.sp,
                modifier = Modifier.weight(1f)
              )
              IconButton(
                onClick = { copyToClipboard("Firma SHA-256", appRelease.sha256Signature) },
                modifier = Modifier.size(28.dp)
              ) {
                Icon(Icons.Default.ContentCopy, contentDescription = "Copiar SHA256", tint = CyberCyan, modifier = Modifier.size(14.dp))
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            if (isGeneratingApk) {
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .background(ObsidianElevated, RoundedCornerShape(8.dp))
                  .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                LinearProgressIndicator(
                  modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                  color = CyberEmerald,
                  trackColor = ObsidianBorder
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                  text = "Ensamblando paquete APK de producción v3.0.0 firmado...",
                  color = CyberTextSecondary,
                  fontSize = 11.sp
                )
              }
            } else {
              // Action Buttons
              Button(
                onClick = {
                  onTriggerApkDownload()
                  Toast.makeText(context, "Preparando descarga de ArmasQuantumMesh-v3.0.0-release.apk...", Toast.LENGTH_LONG).show()
                },
                modifier = Modifier
                  .fillMaxWidth()
                  .height(48.dp)
                  .testTag("btn_download_apk"),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CyberEmerald, contentColor = ObsidianBackground)
              ) {
                Icon(Icons.Default.CloudDownload, contentDescription = "Descargar", modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Descargar APK de Producción (Instalable)", fontWeight = FontWeight.Black, fontSize = 13.sp)
              }
            }

            if (apkDownloadUrl != null) {
              Spacer(modifier = Modifier.height(10.dp))
              Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = CyberEmerald.copy(alpha = 0.15f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyberEmerald)
              ) {
                Row(
                  modifier = Modifier.fillMaxWidth().padding(10.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(Icons.Default.CheckCircle, contentDescription = "Listo", tint = CyberEmerald, modifier = Modifier.size(18.dp))
                  Spacer(modifier = Modifier.width(8.dp))
                  Column {
                    Text("¡APK de Producción Listo para Instalar!", color = CyberEmerald, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text("También puedes exportar el proyecto completo en ZIP o generar APKs/AABs desde el menú de configuración de AI Studio.", color = CyberTextPrimary, fontSize = 10.sp)
                  }
                }
              }
            }
          }
        }
      }

      // AI Studio Export & Google Play Guide Card
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
              Icon(Icons.Default.Storefront, contentDescription = "Google Play", tint = WarningGold, modifier = Modifier.size(20.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "CÓMO DESCARGAR Y LANZAR EN GOOGLE PLAY",
                color = WarningGold,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
              )
            }

            Spacer(modifier = Modifier.height(10.dp))

            StepGuideItem(
              stepNumber = "1",
              title = "Descarga Directa desde AI Studio",
              description = "En la esquina superior derecha o menú de ajustes de AI Studio, haz clic en 'Export' > 'Download as ZIP' o 'Generate APK / AAB'."
            )
            StepGuideItem(
              stepNumber = "2",
              title = "Instalación Inmediata en Android",
              description = "Transfiere el archivo APK a tu smartphone Android y ábrelo para instalar 'Armas Quantum Mesh' de forma nativa."
            )
            StepGuideItem(
              stepNumber = "3",
              title = "Publicación en Google Play Console",
              description = "Sube el archivo AAB firmado en Google Play Console bajo la cuenta de desarrollador de Alejandro Armas."
            )
            StepGuideItem(
              stepNumber = "4",
              title = "Cobro de Servicios y Planes",
              description = "Los clientes de la app pueden transferir directamente a la cuenta bancaria oficial de Alejandro Armas desde el módulo de Pagos."
            )
          }
        }
      }

      // Google Play Developer Policy Audit Checklist
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = ObsidianElevated),
          border = androidx.compose.foundation.BorderStroke(1.dp, CyberCyan.copy(alpha = 0.4f))
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
                Icon(Icons.Default.Policy, contentDescription = "Policy", tint = CyberCyan, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "CUMPLIMIENTO DE POLÍTICAS GOOGLE PLAY",
                  color = CyberCyan,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold
                )
              }
              Text("100% AUDITADO", color = CyberEmerald, fontSize = 10.sp, fontWeight = FontWeight.Black)
            }

            Spacer(modifier = Modifier.height(10.dp))

            PolicyCheckItem("Título de App <= 30 caracteres ('Armas Quantum Mesh')", true)
            PolicyCheckItem("Icono Adaptativo Personalizado (Vectorial XML)", true)
            PolicyCheckItem("Zero Carga Dinámica de Código Externo (DCL)", true)
            PolicyCheckItem("Privacidad Absoluta Zero-Leak & Cifrado Post-Cuántico", true)
            PolicyCheckItem("Soporte Completo Edge-to-Edge & M3 Accessibility (>=48dp)", true)
          }
        }
      }

      // Shortcut to Bank Account & Payments
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
          border = androidx.compose.foundation.BorderStroke(1.dp, WarningGold.copy(alpha = 0.5f))
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              modifier = Modifier.weight(1f),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(CircleShape)
                  .background(WarningGold.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(Icons.Default.RocketLaunch, contentDescription = "Cohete", tint = WarningGold, modifier = Modifier.size(20.dp))
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text("¿Deseas gestionar cobros o ver la cuenta?", color = WarningGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text("Consulta la cuenta bancaria de Alejandro Armas", color = CyberTextSecondary, fontSize = 10.sp)
              }
            }

            Button(
              onClick = onNavigateToPayments,
              modifier = Modifier.height(36.dp),
              shape = RoundedCornerShape(8.dp),
              colors = ButtonDefaults.buttonColors(containerColor = WarningGold, contentColor = ObsidianBackground),
              contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp)
            ) {
              Text("Ir a Banca", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // TAB 1: TITULARIDAD SOBERANA (ALEJANDRO ARMAS)
    if (activeTab == 1) {
      item {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .testTag("sovereign_certificate_parchment"),
          shape = RoundedCornerShape(18.dp),
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
    }

    item {
      Spacer(modifier = Modifier.height(20.dp))
    }
  }
}

@Composable
fun ReleaseDetailRow(label: String, value: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 3.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(label, color = CyberTextSecondary, fontSize = 11.sp)
    Text(value, color = CyberTextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
  }
}

@Composable
fun StepGuideItem(stepNumber: String, title: String, description: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 6.dp),
    verticalAlignment = Alignment.Top
  ) {
    Box(
      modifier = Modifier
        .size(24.dp)
        .clip(CircleShape)
        .background(WarningGold.copy(alpha = 0.2f))
        .border(1.dp, WarningGold, CircleShape),
      contentAlignment = Alignment.Center
    ) {
      Text(stepNumber, color = WarningGold, fontSize = 11.sp, fontWeight = FontWeight.Black)
    }
    Spacer(modifier = Modifier.width(10.dp))
    Column {
      Text(title, color = CyberTextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
      Text(description, color = CyberTextSecondary, fontSize = 10.sp, lineHeight = 14.sp)
    }
  }
}

@Composable
fun PolicyCheckItem(title: String, isCompliant: Boolean) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 3.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      imageVector = if (isCompliant) Icons.Default.CheckCircle else Icons.Default.Security,
      contentDescription = "Check",
      tint = if (isCompliant) CyberEmerald else ThreatCrimson,
      modifier = Modifier.size(14.dp)
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(title, color = CyberTextPrimary, fontSize = 11.sp)
  }
}
