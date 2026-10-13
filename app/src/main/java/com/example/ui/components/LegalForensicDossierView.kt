package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GpsLocationState
import com.example.model.LegalEvidenceDossierState
import com.example.model.LegalEvidenceItem
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberTextMuted
import com.example.ui.theme.CyberTextPrimary
import com.example.ui.theme.CyberTextSecondary
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianElevated
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.QuantumPurple
import com.example.ui.theme.ThreatCrimson
import com.example.ui.theme.WarningGold
import java.util.Locale

@Composable
fun LegalForensicDossierView(
  dossierState: LegalEvidenceDossierState,
  gpsState: GpsLocationState,
  onFreezeEvidence: () -> Unit,
  getComplaintText: () -> String,
  onMarkExported: () -> Unit
) {
  val context = LocalContext.current
  var showFullTextDialog by remember { mutableStateOf(false) }

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Encabezado Principal del Dossier Pericial
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("card_legal_dossier_header"),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
      border = androidx.compose.foundation.BorderStroke(
        1.5.dp,
        Brush.horizontalGradient(listOf(WarningGold, ThreatCrimson, QuantumPurple))
      )
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(WarningGold.copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Gavel,
                contentDescription = "Acciones Legales",
                tint = WarningGold,
                modifier = Modifier.size(24.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "EXPEDIENTE PERICIAL FORENSE",
                color = WarningGold,
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp
              )
              Text(
                text = "CADENA DE CUSTODIA ISO/IEC 27037 & RFC 3227",
                color = CyberTextMuted,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(ThreatCrimson.copy(alpha = 0.2f))
              .border(1.dp, ThreatCrimson.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text(
              text = "VALIDEZ JUDICIAL",
              color = ThreatCrimson,
              fontSize = 9.sp,
              fontWeight = FontWeight.Black
            )
          }
        }

        Text(
          text = dossierState.legalSummaryNote,
          color = CyberTextSecondary,
          fontSize = 11.5.sp,
          lineHeight = 16.sp
        )

        // Metadata técnica del caso
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
            Text("FOLIO / CAUSA:", color = CyberTextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text(dossierState.caseNumber, color = CyberCyan, fontSize = 10.5.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
          }
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("DENUNCIANTE / TITULAR:", color = CyberTextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text("${dossierState.complainantName} (${dossierState.complainantEmail})", color = CyberTextPrimary, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
          }
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("DESTINATARIO:", color = CyberTextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text(dossierState.targetRecipientAuthority, color = WarningGold, fontSize = 9.5.sp, fontWeight = FontWeight.SemiBold)
          }
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("FECHA UTC:", color = CyberTextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text(dossierState.generatedDateUtc, color = CyberTextSecondary, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
          }
          Column {
            Text("MÁSTER HASH SHA-256 (INMUTABILIDAD):", color = CyberTextMuted, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            Text(
              text = dossierState.masterChainOfCustodyHash,
              color = CyberEmerald,
              fontSize = 8.5.sp,
              fontFamily = FontFamily.Monospace,
              lineHeight = 11.sp
            )
          }
        }
      }
    }

    // Botones de Acciones Críticas Operativas
    Column(
      modifier = Modifier.fillMaxWidth(),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Button(
        onClick = {
          onFreezeEvidence()
          Toast.makeText(context, "Evidencia forense y paradero congelados con hash SHA-256 inmutable.", Toast.LENGTH_LONG).show()
        },
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("btn_freeze_legal_evidence"),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = ThreatCrimson,
          contentColor = Color.White
        )
      ) {
        Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "CONGELAR & SELLAR EVIDENCIA CRIPTOGRÁFICA",
          fontSize = 11.5.sp,
          fontWeight = FontWeight.Black,
          letterSpacing = 0.5.sp
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Button(
          onClick = {
            val text = getComplaintText()
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
            val clip = ClipData.newPlainText("Expediente Forense Legal Armas", text)
            clipboard?.setPrimaryClip(clip)
            onMarkExported()
            Toast.makeText(context, "¡Expediente copiado! Listo para anexar a la denuncia formal.", Toast.LENGTH_LONG).show()
          },
          modifier = Modifier
            .weight(1f)
            .height(44.dp)
            .testTag("btn_copy_legal_complaint"),
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = WarningGold,
            contentColor = Color.Black
          )
        ) {
          Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Copiar Denuncia", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }

        Button(
          onClick = {
            val text = getComplaintText()
            val sendIntent: Intent = Intent().apply {
              action = Intent.ACTION_SEND
              putExtra(Intent.EXTRA_TEXT, text)
              putExtra(Intent.EXTRA_SUBJECT, "Expediente Pericial Delitos Informáticos - ${dossierState.caseNumber}")
              type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, "Enviar Expediente Formal")
            context.startActivity(shareIntent)
            onMarkExported()
          },
          modifier = Modifier
            .weight(1f)
            .height(44.dp)
            .testTag("btn_share_legal_dossier"),
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = CyberCyan,
            contentColor = Color.Black
          )
        ) {
          Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Compartir / Enviar", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
      }
    }

    // Acta Certificada de Paradero y Coordenadas GPS del Afectado
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("card_legal_paradero_gps"),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
      border = androidx.compose.foundation.BorderStroke(1.dp, CyberCyan.copy(alpha = 0.5f))
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.GpsFixed, contentDescription = null, tint = CyberCyan, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "PARADERO CERTIFICADO DEL DENUNCIANTE",
              color = CyberCyan,
              fontSize = 11.sp,
              fontWeight = FontWeight.Black
            )
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(CyberEmerald.copy(alpha = 0.15f))
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text("GNSS L5 FIJADO", color = CyberEmerald, fontSize = 9.sp, fontWeight = FontWeight.Bold)
          }
        }

        Text(
          text = "Constancia física pericial de que el usuario se encontraba en la zona descrita, siendo víctima de intrusión no consentida y rastreo ilegítimo:",
          color = CyberTextSecondary,
          fontSize = 10.5.sp
        )

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(ObsidianElevated)
            .padding(10.dp),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column {
            Text("COORDENADAS GNSS:", color = CyberTextMuted, fontSize = 8.5.sp, fontWeight = FontWeight.Bold)
            Text(
              "${String.format(Locale.US, "%.6f", gpsState.latitude)}° N, ${String.format(Locale.US, "%.6f", Math.abs(gpsState.longitude))}° W",
              color = CyberTextPrimary,
              fontSize = 11.sp,
              fontFamily = FontFamily.Monospace,
              fontWeight = FontWeight.Bold
            )
          }
          Column(horizontalAlignment = Alignment.End) {
            Text("PRECISIÓN / ALTITUD:", color = CyberTextMuted, fontSize = 8.5.sp, fontWeight = FontWeight.Bold)
            Text(
              "±${String.format(Locale.US, "%.1f", gpsState.accuracyMeters)}m • ${gpsState.altitudeMeters.toInt()}m s.n.m.",
              color = CyberEmerald,
              fontSize = 10.sp,
              fontFamily = FontFamily.Monospace,
              fontWeight = FontWeight.Bold
            )
          }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Shield, contentDescription = null, tint = CyberEmerald, modifier = Modifier.size(13.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "${gpsState.humanReadableParadero} • Blindaje eBPF: ${if (gpsState.isLocationCloakedAgainstTrackers) "OFUSCACIÓN ACTIVA" else "PASIVA"}",
            color = CyberTextSecondary,
            fontSize = 9.5.sp
          )
        }
      }
    }

    // Catálogo de Pruebas Periciales Compiladas
    Column(
      modifier = Modifier.fillMaxWidth(),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Assignment, contentDescription = null, tint = WarningGold, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "ELEMENTOS PROBATORIOS FORMALES (${dossierState.evidenceItems.size})",
            color = WarningGold,
            fontSize = 12.sp,
            fontWeight = FontWeight.Black
          )
        }

        Text(
          text = "INMUTABLES",
          color = CyberEmerald,
          fontSize = 9.5.sp,
          fontWeight = FontWeight.Bold
        )
      }

      dossierState.evidenceItems.forEach { item ->
        LegalEvidenceItemCard(item = item)
      }
    }

    // Guía Legal de Actuación Formal
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("card_legal_action_guide"),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
      border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Security, contentDescription = null, tint = CyberEmerald, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "PASOS PARA RADICAR LA DENUNCIA FORMAL",
            color = CyberEmerald,
            fontSize = 11.sp,
            fontWeight = FontWeight.Black
          )
        }

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          LegalStepItem(
            stepNum = "1",
            title = "Exportar o Copiar este Expediente Digital",
            desc = "Utiliza el botón 'Copiar Denuncia' o 'Compartir' para tener el texto íntegro con sellos SHA-256."
          )
          LegalStepItem(
            stepNum = "2",
            title = "Presentar Denuncia ante Fiscalía / Policía Cibernética",
            desc = "Acude formalmente a la unidad especializada de delitos cibernéticos de tu localidad o canal de denuncia digital oficial."
          )
          LegalStepItem(
            stepNum = "3",
            title = "Solicitar Medidas de Protección y Cautelares",
            desc = "Pide al Ministerio Público el requerimiento judicial a los ISPs para la identificación de los operadores de los links y balizas interceptadas."
          )
          LegalStepItem(
            stepNum = "4",
            title = "Preservar el Dispositivo sin Manipulaciones",
            desc = "Mantén la app Armas Mesh activa para registrar cualquier reincidencia con cadena de custodia inmutable."
          )
        }
      }
    }

    // Bitácora Forense
    if (dossierState.auditLogs.isNotEmpty()) {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
          verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Text(
            text = "REGISTRO DE CADENA DE CUSTODIA FORENSE",
            color = CyberTextMuted,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
          )
          dossierState.auditLogs.take(6).forEach { log ->
            Text(
              text = log,
              color = CyberTextSecondary,
              fontSize = 9.5.sp,
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
private fun LegalEvidenceItemCard(item: LegalEvidenceItem) {
  val categoryColor = when (item.category) {
    "UBICACIÓN Y PARADERO CERTIFICADO" -> CyberCyan
    "DISPOSITIVO INFRACTOR IDENTIFICADO" -> ThreatCrimson
    "ENLACE DE FILTRACIÓN & EXFILTRACIÓN" -> WarningGold
    else -> QuantumPurple
  }

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("item_evidence_${item.id}"),
    shape = RoundedCornerShape(10.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(categoryColor.copy(alpha = 0.15f))
            .border(1.dp, categoryColor.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(
            text = item.category,
            color = categoryColor,
            fontSize = 8.5.sp,
            fontWeight = FontWeight.Black
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.CheckCircle, contentDescription = null, tint = CyberEmerald, modifier = Modifier.size(11.dp))
          Spacer(modifier = Modifier.width(3.dp))
          Text(
            text = item.chainOfCustodyStatus,
            color = CyberEmerald,
            fontSize = 8.5.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Text(
        text = item.title,
        color = CyberTextPrimary,
        fontSize = 11.5.sp,
        fontWeight = FontWeight.Bold
      )

      Text(
        text = item.technicalDetails,
        color = CyberTextSecondary,
        fontSize = 10.sp,
        lineHeight = 14.sp
      )

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(6.dp))
          .background(ObsidianElevated)
          .padding(6.dp)
      ) {
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("TIMESTAMP UTC: ${item.timestampUtc}", color = CyberTextMuted, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
            Text("ID: ${item.id}", color = CyberCyan, fontSize = 8.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
          }
          Text(
            text = item.sha256IntegrityHash,
            color = CyberEmerald,
            fontSize = 8.sp,
            fontFamily = FontFamily.Monospace
          )
        }
      }
    }
  }
}

@Composable
private fun LegalStepItem(stepNum: String, title: String, desc: String) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.Top
  ) {
    Box(
      modifier = Modifier
        .size(20.dp)
        .clip(CircleShape)
        .background(CyberCyan.copy(alpha = 0.2f)),
      contentAlignment = Alignment.Center
    ) {
      Text(stepNum, color = CyberCyan, fontSize = 10.sp, fontWeight = FontWeight.Black)
    }
    Spacer(modifier = Modifier.width(8.dp))
    Column {
      Text(title, color = CyberTextPrimary, fontSize = 10.5.sp, fontWeight = FontWeight.Bold)
      Text(desc, color = CyberTextSecondary, fontSize = 9.5.sp, lineHeight = 13.sp)
    }
  }
}
