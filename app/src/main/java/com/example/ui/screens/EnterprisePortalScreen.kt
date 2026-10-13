package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import com.example.viewmodel.CyberUiState

@Composable
fun EnterprisePortalScreen(
  state: CyberUiState,
  onEvaluateApplication: (String, String, Double, Boolean, Boolean) -> Unit,
  onNavigateToBank: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  var companyName by remember { mutableStateOf("Banca Cuántica Suiza") }
  var selectedSector by remember { mutableStateOf("Finanzas & Banca") }
  var budgetMillions by remember { mutableFloatStateOf(15.0f) }
  var hasHsm by remember { mutableStateOf(true) }
  var hasClearance by remember { mutableStateOf(true) }

  val sectors = listOf(
    "Finanzas & Banca",
    "Aeroespacial & Defensa",
    "Gobierno & Soberanía",
    "Infraestructura Crítica",
    "Farmacéutica Global"
  )

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianBackground)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(8.dp))
      // Enterprise Tier Header
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(
          1.dp,
          Brush.horizontalGradient(listOf(WarningGold, CyberCyan))
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
            Text(
              text = "PORTAL DE ACCESO EMPRESARIAL",
              color = WarningGold,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp
            )
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(WarningGold.copy(alpha = 0.15f))
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(
                text = "TIER MILLONARIO ($10M+ USD)",
                color = WarningGold,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Privacidad Absoluta e Inviolable",
            color = CyberTextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Black
          )

          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "La red cuántica de Alejandro Armas está reservada exclusivamente para entidades de misión crítica que cumplan los requisitos de solvencia patrimonial ($10,000,000 USD mínimo por clúster), hardware criptográfico blindado y autorización directa del titular.",
            color = CyberTextSecondary,
            fontSize = 12.sp,
            lineHeight = 17.sp
          )
        }
      }
    }

    // Requirements Checklist
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianElevated),
        border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp)
        ) {
          Text(
            text = "REQUISITOS OBLIGATORIOS DE INGRESO",
            color = CyberCyan,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.height(10.dp))

          RequirementItem(
            title = "1. Solvencia Mínima: $10,000,000 USD",
            subtitle = "Contrato de infraestructura cuántica privada y fianza de cobertura."
          )
          RequirementItem(
            title = "2. Hardware Criptográfico HSM FIPS 140-3 Nivel 4",
            subtitle = "Aislamiento físico a prueba de manipulaciones en el datacenter cliente."
          )
          RequirementItem(
            title = "3. Acreditación de Seguridad Soberana KYB",
            subtitle = "Verificación de antecedentes de no-vulnerabilidad y soberanía operativa."
          )
          RequirementItem(
            title = "4. Firma Criptográfica de Alejandro Armas",
            subtitle = "Autorización del titular para aprovisionar el canal de retículos cuánticos."
          )
        }
      }
    }

    // Direct Access to Bank Account & Payment Plans of Alejandro Armas
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onNavigateToBank() }
          .testTag("enterprise_navigate_bank_banner"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianElevated),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberCyan.copy(alpha = 0.6f))
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
            Icon(
              imageVector = Icons.Default.AccountBalance,
              contentDescription = "Banco",
              tint = CyberCyan,
              modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "CUENTA BANCARIA & PLANES DE PAGO",
                color = CyberCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "Datos de depósito a nombre de Alejandro Armas, CLABE, SWIFT y planes en cuotas",
                color = CyberTextSecondary,
                fontSize = 10.sp
              )
            }
          }
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Ir a Pagos",
            tint = CyberCyan,
            modifier = Modifier.size(18.dp)
          )
        }
      }
    }

    // Interactive Enterprise Qualification Form
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("enterprise_audit_card"),
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
            text = "AUDITORÍA DE CALIFICACIÓN DE ENTIDAD",
            color = CyberCyan,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.height(12.dp))

          OutlinedTextField(
            value = companyName,
            onValueChange = { companyName = it },
            label = { Text("Nombre de la Empresa u Organización") },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("company_name_input"),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = CyberCyan,
              unfocusedBorderColor = ObsidianBorder,
              focusedTextColor = CyberTextPrimary,
              unfocusedTextColor = CyberTextPrimary
            ),
            singleLine = true
          )

          Spacer(modifier = Modifier.height(12.dp))
          Text(
            text = "Sector Estratégico",
            color = CyberTextSecondary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
          )
          Spacer(modifier = Modifier.height(6.dp))

          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            items(sectors) { sector ->
              FilterChip(
                selected = selectedSector == sector,
                onClick = { selectedSector = sector },
                label = { Text(sector, fontSize = 11.sp) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = CyberCyan.copy(alpha = 0.2f),
                  selectedLabelColor = CyberCyan
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "Presupuesto de Infraestructura:",
              color = CyberTextSecondary,
              fontSize = 11.sp
            )
            Text(
              text = "$${String.format(java.util.Locale.US, "%.1f", budgetMillions)} Millones USD",
              color = if (budgetMillions >= 10.0f) CyberEmerald else ThreatCrimson,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Slider(
            value = budgetMillions,
            onValueChange = { budgetMillions = it },
            valueRange = 1.0f..50.0f,
            modifier = Modifier.testTag("budget_slider"),
            colors = SliderDefaults.colors(
              thumbColor = if (budgetMillions >= 10.0f) CyberEmerald else ThreatCrimson,
              activeTrackColor = if (budgetMillions >= 10.0f) CyberEmerald else ThreatCrimson
            )
          )

          Spacer(modifier = Modifier.height(8.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Módulo HSM FIPS 140-3 Nivel 4",
                color = CyberTextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "Hardware anti-tamper verificado",
                color = CyberTextMuted,
                fontSize = 10.sp
              )
            }
            Switch(
              checked = hasHsm,
              onCheckedChange = { hasHsm = it },
              colors = SwitchDefaults.colors(checkedThumbColor = CyberCyan)
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Acreditación Soberana KYB",
                color = CyberTextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "Auditoría de integridad y confidencialidad",
                color = CyberTextMuted,
                fontSize = 10.sp
              )
            }
            Switch(
              checked = hasClearance,
              onCheckedChange = { hasClearance = it },
              colors = SwitchDefaults.colors(checkedThumbColor = CyberCyan)
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          Button(
            onClick = {
              onEvaluateApplication(
                companyName,
                selectedSector,
                budgetMillions.toDouble(),
                hasHsm,
                hasClearance
              )
            },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("submit_qualification_button"),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CyberCyan)
          ) {
            Icon(
              imageVector = Icons.Default.Gavel,
              contentDescription = "Calificar",
              tint = ObsidianBackground,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Ejecutar Auditoría de Calificación",
              color = ObsidianBackground,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }

    // Last Audit Result Banner
    state.lastAuditResult?.let { result ->
      item {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .testTag("audit_verdict_card"),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
          border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (result.isApproved) CyberEmerald else ThreatCrimson
          )
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = if (result.isApproved) Icons.Default.CheckCircle else Icons.Default.Cancel,
                contentDescription = "Resultado",
                tint = if (result.isApproved) CyberEmerald else ThreatCrimson,
                modifier = Modifier.size(24.dp)
              )
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = if (result.isApproved) "SOLICITUD APROBADA ($${result.estimatedBudgetMillions}M USD)" else "SOLICITUD NO CALIFICADA",
                color = if (result.isApproved) CyberEmerald else ThreatCrimson,
                fontSize = 14.sp,
                fontWeight = FontWeight.Black
              )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = result.auditReport,
              color = CyberTextSecondary,
              fontSize = 12.sp,
              lineHeight = 16.sp
            )
            if (result.isApproved && result.assignedNodeCluster != null) {
              Spacer(modifier = Modifier.height(8.dp))
              Text(
                text = "CLÚSTER ASIGNADO: ${result.assignedNodeCluster}",
                color = CyberCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
              )
            }
          }
        }
      }
    }

    // Enterprise Tiers Breakdown
    item {
      Text(
        text = "PLANES DE INFRAESTRUCTURA CUÁNTICA PRIVADA",
        color = CyberCyan,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      )
    }

    item {
      EnterpriseTierCard(
        title = "Clúster Estándar Empresarial",
        price = "$10,000,000 USD",
        description = "Canal cuántico directo con 1 nodo dedicado, centinela IA 24/7 y rotación de claves cada 15s.",
        color = CyberCyan
      )
    }

    item {
      EnterpriseTierCard(
        title = "Red Multinacional Soberana",
        price = "$25,000,000 USD",
        description = "Malla de 4 nodos cuánticos intercontinentales en enclaves blindados (Suiza, Tokio, USA, Alemania).",
        color = QuantumPurple
      )
    }

    item {
      EnterpriseTierCard(
        title = "Enclave Supremo de Defensa Soberana",
        price = "$50,000,000 USD",
        description = "Infraestructura cuántica absoluta para bancos centrales o entidades gubernamentales, con firma directa de Alejandro Armas.",
        color = WarningGold
      )
    }

    item {
      Spacer(modifier = Modifier.height(20.dp))
    }
  }
}

@Composable
fun RequirementItem(title: String, subtitle: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    verticalAlignment = Alignment.Top
  ) {
    Icon(
      imageVector = Icons.Default.CheckCircle,
      contentDescription = "Verificado",
      tint = CyberEmerald,
      modifier = Modifier
        .size(16.dp)
        .padding(top = 2.dp)
    )
    Spacer(modifier = Modifier.width(8.dp))
    Column {
      Text(
        text = title,
        color = CyberTextPrimary,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = subtitle,
        color = CyberTextMuted,
        fontSize = 10.sp
      )
    }
  }
}

@Composable
fun EnterpriseTierCard(
  title: String,
  price: String,
  description: String,
  color: Color
) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.4f))
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
          text = title,
          color = color,
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = description,
          color = CyberTextSecondary,
          fontSize = 11.sp,
          lineHeight = 15.sp
        )
      }
      Spacer(modifier = Modifier.width(10.dp))
      Text(
        text = price,
        color = CyberTextPrimary,
        fontSize = 13.sp,
        fontWeight = FontWeight.Black,
        fontFamily = FontFamily.Monospace
      )
    }
  }
}
