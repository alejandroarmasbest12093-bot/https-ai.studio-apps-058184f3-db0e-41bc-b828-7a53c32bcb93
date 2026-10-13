package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.model.DepositTransaction
import com.example.model.PaymentPlan
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
import com.example.viewmodel.CyberUiState

@Composable
fun BankAndPaymentsScreen(
  uiState: CyberUiState,
  onRecordDeposit: (String, String, Double, String, String, String) -> Unit,
  onClearReceiptDialog: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val bank = uiState.bankAccount
  var selectedTab by remember { mutableIntStateOf(0) } // 0: Cuenta Bancaria, 1: Planes & Cuotas, 2: Registrar Depósito, 3: Historial
  var isAnnualSelected by remember { mutableStateOf(true) }

  // Form states
  var clientNameInput by remember { mutableStateOf("") }
  var selectedPlanName by remember { mutableStateOf("Red PyME & Despachos") }
  var depositAmountInput by remember { mutableStateOf("2490") }
  var referenceInput by remember { mutableStateOf("") }
  var selectedPaymentMethod by remember { mutableStateOf("CLABE / SPEI") }
  var selectedInstallmentScheme by remember { mutableStateOf("Pago Contado Anual") }

  // Receipt Modal State
  var viewingReceipt by remember { mutableStateOf<DepositTransaction?>(null) }
  val activeReceipt = uiState.lastDepositReceipt ?: viewingReceipt

  fun copyToClipboard(label: String, value: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, value)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(context, "$label copiado al portapapeles", Toast.LENGTH_SHORT).show()
  }

  fun copyAllBankDetails() {
    val fullText = """
      CUENTA BANCARIA OFICIAL PARA DEPÓSITOS DE SERVICIOS
      Titular: ${bank.holderName}
      Correo para Notificaciones: ${bank.notificationEmail}
      Banco: ${bank.bankName}
      Cuenta: ${bank.accountNumber}
      CLABE Interbancaria: ${bank.clabe}
      SWIFT / BIC: ${bank.swiftBic}
      IBAN: ${bank.iban}
      Routing (ABA): ${bank.routingNumber}
      Monedas: ${bank.supportedCurrencies}
      Tax ID / RFC: ${bank.taxId}
      Instrucción: ${bank.memoInstruction}
      Bóveda USDC: ${bank.cryptoVaultAddress}
    """.trimIndent()
    copyToClipboard("Datos Bancarios Completos de Alejandro Armas", fullText)
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
          .testTag("bank_screen_header_card"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        border = androidx.compose.foundation.BorderStroke(
          1.dp,
          Brush.horizontalGradient(listOf(WarningGold, CyberCyan, CyberEmerald))
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
                text = "CENTRO DE PAGOS & BANCA OFICIAL",
                color = WarningGold,
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
                text = "CUENTA ACTIVA",
                color = CyberEmerald,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = "Cuenta a Nombre de Alejandro Armas",
            color = CyberTextPrimary,
            fontSize = 19.sp,
            fontWeight = FontWeight.Black
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Depósito directo para la contratación de licencias soberanas, planes mensuales, financiamiento en cuotas y clústeres empresariales de ciberseguridad cuántica.",
            color = CyberTextSecondary,
            fontSize = 12.sp,
            lineHeight = 17.sp
          )
          Spacer(modifier = Modifier.height(8.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.Email, contentDescription = "Email", tint = CyberCyan, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = bank.notificationEmail,
              color = CyberCyan,
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold
            )
          }
        }
      }
    }

    // Sub Navigation Tabs
    item {
      TabRow(
        selectedTabIndex = selectedTab,
        containerColor = ObsidianSurface,
        contentColor = CyberCyan,
        indicator = { tabPositions ->
          TabRowDefaults.SecondaryIndicator(
            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
            color = WarningGold
          )
        },
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .testTag("bank_tabs")
      ) {
        Tab(
          selected = selectedTab == 0,
          onClick = { selectedTab = 0 },
          text = { Text("Datos Bancarios", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        Tab(
          selected = selectedTab == 1,
          onClick = { selectedTab = 1 },
          text = { Text("Planes & Cuotas", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        Tab(
          selected = selectedTab == 2,
          onClick = { selectedTab = 2 },
          text = { Text("Pagar / Depositar", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
        Tab(
          selected = selectedTab == 3,
          onClick = { selectedTab = 3 },
          text = { Text("Depósitos (${uiState.depositTransactions.size})", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
        )
      }
    }

    // TAB 0: CUENTA BANCARIA A NOMBRE DE ALEJANDRO ARMAS
    if (selectedTab == 0) {
      item {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .testTag("official_bank_account_card"),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
          border = androidx.compose.foundation.BorderStroke(1.5.dp, WarningGold)
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
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(WarningGold.copy(alpha = 0.15f)),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.AccountBalance,
                    contentDescription = "Banco",
                    tint = WarningGold,
                    modifier = Modifier.size(24.dp)
                  )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                  Text(
                    text = "BANCO RECEPTOR PRINCIPAL",
                    color = WarningGold,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                  )
                  Text(
                    text = bank.bankName,
                    color = CyberTextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black
                  )
                }
              }

              IconButton(
                onClick = { copyAllBankDetails() },
                modifier = Modifier
                  .size(36.dp)
                  .testTag("btn_copy_all_bank")
              ) {
                Icon(
                  imageVector = Icons.Default.ContentCopy,
                  contentDescription = "Copiar todos los datos",
                  tint = WarningGold
                )
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Beneficiary / Holder
            BankDetailField(
              label = "TITULAR DE LA CUENTA (BENEFICIARIO OFICIAL):",
              value = bank.holderName,
              highlightColor = CyberEmerald,
              onCopy = { copyToClipboard("Titular", bank.holderName) },
              testTag = "bank_holder_field"
            )

            // CLABE Interbancaria (SPEI)
            BankDetailField(
              label = "CLABE INTERBANCARIA (SPEI / LATAM):",
              value = bank.clabe,
              highlightColor = CyberCyan,
              onCopy = { copyToClipboard("CLABE Interbancaria", bank.clabe) },
              testTag = "bank_clabe_field"
            )

            // Account Number
            BankDetailField(
              label = "NÚMERO DE CUENTA CORRIENTE:",
              value = bank.accountNumber,
              highlightColor = CyberTextPrimary,
              onCopy = { copyToClipboard("Número de Cuenta", bank.accountNumber) },
              testTag = "bank_acc_field"
            )

            // SWIFT / BIC
            BankDetailField(
              label = "CÓDIGO SWIFT / BIC (INTERNACIONAL):",
              value = bank.swiftBic,
              highlightColor = QuantumPurple,
              onCopy = { copyToClipboard("SWIFT/BIC", bank.swiftBic) },
              testTag = "bank_swift_field"
            )

            // IBAN
            BankDetailField(
              label = "CÓDIGO IBAN (EUROPA & GLOBAL):",
              value = bank.iban,
              highlightColor = CyberCyan,
              onCopy = { copyToClipboard("IBAN", bank.iban) },
              testTag = "bank_iban_field"
            )

            // Routing
            BankDetailField(
              label = "ROUTING NUMBER (ABA / ACH EE.UU.):",
              value = bank.routingNumber,
              highlightColor = CyberTextPrimary,
              onCopy = { copyToClipboard("Routing ABA", bank.routingNumber) },
              testTag = "bank_routing_field"
            )

            // RFC / TAX ID
            BankDetailField(
              label = "IDENTIFICACIÓN FISCAL / RFC:",
              value = bank.taxId,
              highlightColor = CyberTextSecondary,
              onCopy = { copyToClipboard("Tax ID", bank.taxId) },
              testTag = "bank_tax_field"
            )

            // Monedas Aceptadas
            Spacer(modifier = Modifier.height(4.dp))
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .background(ObsidianElevated, RoundedCornerShape(8.dp))
                .padding(10.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text("Monedas Aceptadas:", color = CyberTextSecondary, fontSize = 11.sp)
              Text(bank.supportedCurrencies, color = CyberEmerald, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Copy all Button
            Button(
              onClick = { copyAllBankDetails() },
              modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .testTag("btn_copy_full_bank_sheet"),
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = WarningGold, contentColor = ObsidianBackground)
            ) {
              Icon(Icons.Default.ContentCopy, contentDescription = "Copiar", modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text("Copiar Ficha Bancaria de Depósito Completa", fontWeight = FontWeight.Black, fontSize = 12.sp)
            }
          }
        }
      }

      // Crypto Quantum Vault Deposit Box
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = ObsidianElevated),
          border = androidx.compose.foundation.BorderStroke(1.dp, QuantumPurple.copy(alpha = 0.4f))
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.AccountBalanceWallet, contentDescription = "Crypto", tint = QuantumPurple, modifier = Modifier.size(20.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "BÓVEDA CRIPTO / LEDGER CUÁNTICO (USDC / USDT)",
                color = QuantumPurple,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Depósitos en stablecoins (USDC / USDT) a nombre de Alejandro Armas en redes seguras con verificación Kyber-1024:",
              color = CyberTextSecondary,
              fontSize = 11.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .background(ObsidianSurface, RoundedCornerShape(8.dp))
                .border(1.dp, ObsidianBorder, RoundedCornerShape(8.dp))
                .padding(10.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = bank.cryptoVaultAddress,
                color = CyberCyan,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.weight(1f)
              )
              IconButton(
                onClick = { copyToClipboard("Bóveda USDC Alejandro Armas", bank.cryptoVaultAddress) },
                modifier = Modifier.size(30.dp)
              ) {
                Icon(Icons.Default.ContentCopy, contentDescription = "Copiar Wallet", tint = CyberCyan, modifier = Modifier.size(16.dp))
              }
            }
          }
        }
      }
    }

    // TAB 1: PLANES DE PAGO Y OPCIONES DE FINANCIAMIENTO
    if (selectedTab == 1) {
      item {
        // Toggle Annual vs Monthly
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .background(ObsidianSurface, RoundedCornerShape(12.dp))
            .border(1.dp, ObsidianBorder, RoundedCornerShape(12.dp))
            .padding(6.dp),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Button(
            onClick = { isAnnualSelected = false },
            modifier = Modifier.weight(1f).height(38.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = if (!isAnnualSelected) CyberCyan else Color.Transparent,
              contentColor = if (!isAnnualSelected) ObsidianBackground else CyberTextSecondary
            )
          ) {
            Text("Pago Mensual", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }

          Spacer(modifier = Modifier.width(8.dp))

          Button(
            onClick = { isAnnualSelected = true },
            modifier = Modifier.weight(1f).height(38.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = if (isAnnualSelected) WarningGold else Color.Transparent,
              contentColor = if (isAnnualSelected) ObsidianBackground else CyberTextSecondary
            )
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text("Pago Anual (Ahorra 17%)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }

      items(uiState.paymentPlans) { plan ->
        val displayPrice = if (isAnnualSelected) {
          "$${formatPrice(plan.annualPriceUsd)} USD / año"
        } else {
          "$${formatPrice(plan.monthlyPriceUsd)} USD / mes"
        }

        Card(
          modifier = Modifier
            .fillMaxWidth()
            .testTag("plan_card_${plan.id}"),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
          border = androidx.compose.foundation.BorderStroke(
            if (plan.isFeatured) 1.5.dp else 1.dp,
            if (plan.isFeatured) CyberEmerald else ObsidianBorder
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
              Column {
                Text(
                  text = plan.name,
                  color = CyberTextPrimary,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Black
                )
                Text(
                  text = plan.tagline,
                  color = CyberTextSecondary,
                  fontSize = 11.sp
                )
              }

              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(6.dp))
                  .background(if (plan.isFeatured) CyberEmerald.copy(alpha = 0.15f) else QuantumPurple.copy(alpha = 0.15f))
                  .border(1.dp, if (plan.isFeatured) CyberEmerald else QuantumPurple, RoundedCornerShape(6.dp))
                  .padding(horizontal = 8.dp, vertical = 3.dp)
              ) {
                Text(
                  text = plan.badge,
                  color = if (plan.isFeatured) CyberEmerald else QuantumPurple,
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Price Tag
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .background(ObsidianElevated, RoundedCornerShape(10.dp))
                .padding(horizontal = 12.dp, vertical = 10.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text("Inversión de Servicio:", color = CyberTextMuted, fontSize = 10.sp)
                Text(
                  text = displayPrice,
                  color = if (plan.isFeatured) CyberEmerald else WarningGold,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Black,
                  fontFamily = FontFamily.Monospace
                )
              }
              if (isAnnualSelected && plan.discountAnnualPercent > 0) {
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(CyberEmerald.copy(alpha = 0.2f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                  Text("-${plan.discountAnnualPercent}% Descuento", color = CyberEmerald, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
              }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Installment Financing Options
            Text(
              text = "OPCIONES DE PAGO EN CUOTAS:",
              color = CyberCyan,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            plan.installmentOptions.forEach { opt ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(Icons.Default.CreditCard, contentDescription = "Cuotas", tint = CyberCyan, modifier = Modifier.size(13.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(opt, color = CyberTextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Medium)
              }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Features Checklist
            Text(
              text = "BENEFICIOS INCLUIDOS:",
              color = CyberTextMuted,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            plan.features.forEach { feature ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 2.dp),
                verticalAlignment = Alignment.Top
              ) {
                Icon(Icons.Default.CheckCircle, contentDescription = "Check", tint = CyberEmerald, modifier = Modifier.size(13.dp).padding(top = 1.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(feature, color = CyberTextSecondary, fontSize = 11.sp)
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Select & Go to Pay
            Button(
              onClick = {
                selectedPlanName = plan.name
                depositAmountInput = if (isAnnualSelected) plan.annualPriceUsd.toInt().toString() else plan.monthlyPriceUsd.toInt().toString()
                selectedInstallmentScheme = plan.installmentOptions.firstOrNull() ?: "Pago Contado"
                selectedTab = 2 // Switch to deposit form
              },
              modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
                .testTag("btn_select_plan_${plan.id}"),
              shape = RoundedCornerShape(8.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = if (plan.isFeatured) CyberEmerald else CyberCyan,
                contentColor = ObsidianBackground
              )
            ) {
              Icon(Icons.Default.Payments, contentDescription = "Pagar", modifier = Modifier.size(15.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Depositar para ${plan.name}",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
              )
            }
          }
        }
      }
    }

    // TAB 2: FORMULARIO INTERACTIVO PARA REGISTRAR DEPÓSITO
    if (selectedTab == 2) {
      item {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .testTag("deposit_form_card"),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
          border = androidx.compose.foundation.BorderStroke(1.dp, CyberCyan)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(18.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(CircleShape)
                  .background(CyberCyan.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(Icons.Default.FactCheck, contentDescription = "Formulario", tint = CyberCyan, modifier = Modifier.size(20.dp))
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = "REGISTRO DE DEPÓSITO DE PAGO",
                  color = CyberCyan,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  letterSpacing = 1.sp
                )
                Text(
                  text = "Acreditar a nombre de Alejandro Armas",
                  color = CyberTextPrimary,
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Black
                )
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Beneficiary Reminder Box
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .background(ObsidianElevated, RoundedCornerShape(8.dp))
                .border(1.dp, WarningGold.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                .padding(10.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(Icons.Default.AccountBalance, contentDescription = "Banco", tint = WarningGold, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text("Depósito a cuenta de:", color = CyberTextMuted, fontSize = 9.sp)
                Text("Alejandro Armas • CLABE: ${bank.clabe}", color = WarningGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
              }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Client Name Field
            OutlinedTextField(
              value = clientNameInput,
              onValueChange = { clientNameInput = it },
              label = { Text("Nombre o Entidad Depositante") },
              placeholder = { Text("Ej: Banco Quantum / Carlos López") },
              modifier = Modifier
                .fillMaxWidth()
                .testTag("client_name_input"),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CyberCyan,
                unfocusedBorderColor = ObsidianBorder,
                focusedTextColor = CyberTextPrimary,
                unfocusedTextColor = CyberTextPrimary
              ),
              singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Plan Selector Chips
            Text("Plan a Contratar:", color = CyberTextSecondary, fontSize = 11.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
              uiState.paymentPlans.forEach { plan ->
                FilterChip(
                  selected = selectedPlanName == plan.name,
                  onClick = {
                    selectedPlanName = plan.name
                    depositAmountInput = plan.annualPriceUsd.toInt().toString()
                    selectedInstallmentScheme = plan.installmentOptions.firstOrNull() ?: "Pago Contado"
                  },
                  label = { Text("${plan.name} ($${formatPrice(plan.annualPriceUsd)} USD)") },
                  colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = CyberCyan.copy(alpha = 0.2f),
                    selectedLabelColor = CyberCyan
                  )
                )
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Deposit Amount Input
            OutlinedTextField(
              value = depositAmountInput,
              onValueChange = { depositAmountInput = it },
              label = { Text("Monto del Pago (USD)") },
              modifier = Modifier
                .fillMaxWidth()
                .testTag("deposit_amount_input"),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CyberCyan,
                unfocusedBorderColor = ObsidianBorder,
                focusedTextColor = CyberTextPrimary,
                unfocusedTextColor = CyberTextPrimary
              ),
              singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Payment Scheme (Cuotas)
            Text("Esquema de Cuotas Seleccionado:", color = CyberTextSecondary, fontSize = 11.sp)
            Spacer(modifier = Modifier.height(6.dp))
            val schemes = listOf("Pago Contado Anual", "3 Cuotas sin Interés", "6 Cuotas Mensuales", "12 Cuotas Mensuales", "4 Hitos Trimestrales ($2.5M USD c/u)")
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
              schemes.forEach { scheme ->
                FilterChip(
                  selected = selectedInstallmentScheme == scheme,
                  onClick = { selectedInstallmentScheme = scheme },
                  label = { Text(scheme, fontSize = 11.sp) },
                  colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = WarningGold.copy(alpha = 0.2f),
                    selectedLabelColor = WarningGold
                  )
                )
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Payment Method
            Text("Método de Pago Empleado:", color = CyberTextSecondary, fontSize = 11.sp)
            Spacer(modifier = Modifier.height(6.dp))
            val methods = listOf("CLABE / SPEI", "SWIFT / IBAN Internacional", "Depósito en Ventanilla Bancaria", "USDC Cuántico")
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              methods.take(2).forEach { method ->
                FilterChip(
                  selected = selectedPaymentMethod == method,
                  onClick = { selectedPaymentMethod = method },
                  label = { Text(method, fontSize = 10.sp) },
                  colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = CyberEmerald.copy(alpha = 0.2f),
                    selectedLabelColor = CyberEmerald
                  )
                )
              }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              methods.drop(2).forEach { method ->
                FilterChip(
                  selected = selectedPaymentMethod == method,
                  onClick = { selectedPaymentMethod = method },
                  label = { Text(method, fontSize = 10.sp) },
                  colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = CyberEmerald.copy(alpha = 0.2f),
                    selectedLabelColor = CyberEmerald
                  )
                )
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Tracking Reference Field
            OutlinedTextField(
              value = referenceInput,
              onValueChange = { referenceInput = it },
              label = { Text("Número de Rastreo / Folio Bancario") },
              placeholder = { Text("Ej: SPEI-8921004 o REF-9941") },
              modifier = Modifier
                .fillMaxWidth()
                .testTag("deposit_reference_input"),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CyberCyan,
                unfocusedBorderColor = ObsidianBorder,
                focusedTextColor = CyberTextPrimary,
                unfocusedTextColor = CyberTextPrimary
              ),
              singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Submit Button
            Button(
              onClick = {
                val amount = depositAmountInput.toDoubleOrNull() ?: 290.0
                val client = if (clientNameInput.isBlank()) "Cliente Verificado" else clientNameInput
                val ref = if (referenceInput.isBlank()) "SPEI-${(100000..999999).random()}" else referenceInput

                onRecordDeposit(
                  client,
                  selectedPlanName,
                  amount,
                  ref,
                  selectedPaymentMethod,
                  selectedInstallmentScheme
                )
                Toast.makeText(context, "Depósito acreditado a la cuenta de Alejandro Armas", Toast.LENGTH_LONG).show()
                selectedTab = 3 // Switch to History
              },
              modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("btn_submit_deposit"),
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = CyberEmerald, contentColor = ObsidianBackground)
            ) {
              Icon(Icons.Default.VerifiedUser, contentDescription = "Validar", modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text("Registrar Depósito & Generar Recibo Digital", fontWeight = FontWeight.Black, fontSize = 13.sp)
            }
          }
        }
      }
    }

    // TAB 3: HISTORIAL DE DEPÓSITOS ACREDITADOS
    if (selectedTab == 3) {
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "HISTORIAL DE DEPÓSITOS BANCARIOS",
            color = CyberCyan,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          Text(
            text = "${uiState.depositTransactions.size} Transacciones Acreditadas",
            color = CyberEmerald,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      items(uiState.depositTransactions) { tx ->
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .clickable { viewingReceipt = tx }
            .testTag("tx_card_${tx.id}"),
          shape = RoundedCornerShape(12.dp),
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
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Receipt, contentDescription = "Tx", tint = CyberEmerald, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = tx.id,
                  color = CyberCyan,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  fontFamily = FontFamily.Monospace
                )
              }

              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(6.dp))
                  .background(CyberEmerald.copy(alpha = 0.15f))
                  .padding(horizontal = 8.dp, vertical = 3.dp)
              ) {
                Text(
                  text = tx.status,
                  color = CyberEmerald,
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(tx.clientOrEntityName, color = CyberTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Text("${tx.planName} • ${tx.installmentPlan}", color = CyberTextSecondary, fontSize = 11.sp)
              }
              Text(
                text = "$${formatPrice(tx.amountUsd)} USD",
                color = WarningGold,
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace
              )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text("Folio: ${tx.bankReference} (${tx.paymentMethod})", color = CyberTextMuted, fontSize = 10.sp)
              Text("Beneficiario: ${tx.beneficiary}", color = CyberEmerald, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(tx.timestamp, color = CyberTextMuted, fontSize = 9.sp)
              Text("Ver Recibo Criptográfico >", color = CyberCyan, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(20.dp))
    }
  }

  // Cryptographic Receipt Dialog
  activeReceipt?.let { receipt ->
    AlertDialog(
      onDismissRequest = {
        viewingReceipt = null
        onClearReceiptDialog()
      },
      title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.ReceiptLong, contentDescription = "Recibo", tint = WarningGold, modifier = Modifier.size(22.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text("RECIBO DIGITAL SOBERANO", color = WarningGold, fontSize = 14.sp, fontWeight = FontWeight.Black)
        }
      },
      text = {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .background(ObsidianElevated, RoundedCornerShape(10.dp))
            .border(1.dp, WarningGold.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
            .padding(14.dp)
        ) {
          Text("COMPROBANTE OFICIAL DE DEPÓSITO", color = CyberCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
          Spacer(modifier = Modifier.height(8.dp))
          ReceiptRow("Folio Transacción:", receipt.id)
          ReceiptRow("Beneficiario:", receipt.beneficiary)
          ReceiptRow("Depositante:", receipt.clientOrEntityName)
          ReceiptRow("Servicio / Plan:", receipt.planName)
          ReceiptRow("Monto Acreditado:", "$${formatPrice(receipt.amountUsd)} USD")
          ReceiptRow("Esquema de Cuotas:", receipt.installmentPlan)
          ReceiptRow("Método de Pago:", receipt.paymentMethod)
          ReceiptRow("Referencia Bancaria:", receipt.bankReference)
          ReceiptRow("Fecha de Depósito:", receipt.timestamp)
          ReceiptRow("Estado Bancario:", receipt.status)

          Spacer(modifier = Modifier.height(8.dp))
          Text("SELLO CRIPTOGRÁFICO KYBER-1024:", color = CyberTextMuted, fontSize = 9.sp, fontWeight = FontWeight.Bold)
          Text(
            text = receipt.digitalReceiptHash,
            color = CyberEmerald,
            fontSize = 9.sp,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(top = 2.dp)
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            val receiptText = """
              RECIBO DIGITAL DE DEPÓSITO
              Folio: ${receipt.id}
              Beneficiario: ${receipt.beneficiary}
              Depositante: ${receipt.clientOrEntityName}
              Plan: ${receipt.planName}
              Monto: $${formatPrice(receipt.amountUsd)} USD
              Cuotas: ${receipt.installmentPlan}
              Método: ${receipt.paymentMethod}
              Referencia: ${receipt.bankReference}
              Fecha: ${receipt.timestamp}
              Firma Criptográfica: ${receipt.digitalReceiptHash}
            """.trimIndent()
            copyToClipboard("Recibo de Depósito ${receipt.id}", receiptText)
            viewingReceipt = null
            onClearReceiptDialog()
          },
          colors = ButtonDefaults.buttonColors(containerColor = WarningGold, contentColor = ObsidianBackground)
        ) {
          Icon(Icons.Default.ContentCopy, contentDescription = "Copiar", modifier = Modifier.size(14.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Copiar Recibo")
        }
      },
      dismissButton = {
        OutlinedButton(
          onClick = {
            viewingReceipt = null
            onClearReceiptDialog()
          }
        ) {
          Text("Cerrar", color = CyberTextSecondary)
        }
      },
      containerColor = ObsidianSurface,
      shape = RoundedCornerShape(16.dp)
    )
  }
}

@Composable
fun BankDetailField(
  label: String,
  value: String,
  highlightColor: Color,
  onCopy: () -> Unit,
  testTag: String
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp)
      .testTag(testTag)
  ) {
    Text(
      text = label,
      color = CyberTextMuted,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(2.dp))
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(ObsidianElevated, RoundedCornerShape(8.dp))
        .border(1.dp, ObsidianBorder, RoundedCornerShape(8.dp))
        .padding(horizontal = 10.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = value,
        color = highlightColor,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace,
        modifier = Modifier.weight(1f)
      )
      IconButton(
        onClick = onCopy,
        modifier = Modifier.size(26.dp)
      ) {
        Icon(
          imageVector = Icons.Default.ContentCopy,
          contentDescription = "Copiar $label",
          tint = highlightColor.copy(alpha = 0.8f),
          modifier = Modifier.size(14.dp)
        )
      }
    }
  }
}

@Composable
fun ReceiptRow(label: String, value: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 2.dp),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(label, color = CyberTextSecondary, fontSize = 10.sp)
    Text(value, color = CyberTextPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
  }
}

private fun formatPrice(amount: Double): String {
  return if (amount >= 1_000_000) {
    String.format(java.util.Locale.US, "%,.0f", amount)
  } else if (amount >= 1000) {
    String.format(java.util.Locale.US, "%,.0f", amount)
  } else {
    String.format(java.util.Locale.US, "%.0f", amount)
  }
}
