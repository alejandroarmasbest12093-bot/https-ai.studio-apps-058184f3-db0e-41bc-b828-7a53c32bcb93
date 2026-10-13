package com.example

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.EnhancedEncryption
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.SatelliteAlt
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Stream
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.ui.screens.ArmasSatelliteChipScreen
import com.example.ui.screens.BankAndPaymentsScreen
import com.example.ui.screens.DeviceNetworkScannerScreen
import com.example.ui.screens.EmailQuantumShieldScreen
import com.example.ui.screens.EnterprisePortalScreen
import com.example.ui.screens.LicenseCertificateScreen
import com.example.ui.screens.OverviewScreen
import com.example.ui.screens.AegisVoiceControlScreen
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.activity.compose.BackHandler
import com.example.ui.screens.QuantumVaultScreen
import com.example.ui.screens.SentinelAiScreen
import com.example.ui.screens.SynapseQScreen
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberTextMuted
import com.example.ui.theme.CyberTextPrimary
import com.example.ui.theme.CyberTextSecondary
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianElevated
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.QuantumPurple
import com.example.ui.theme.ThreatCrimson
import com.example.ui.theme.WarningGold
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodel.CyberSecurityViewModel
import com.example.viewmodel.CyberUiState

enum class CyberNavScreen(val title: String, val icon: ImageVector, val tag: String) {
  OVERVIEW("Red", Icons.Default.Hub, "nav_overview"),
  SYNAPSE_Q("Synapse-Q", Icons.Default.Stream, "nav_synapse_q"),
  SATELLITE("Chip Armas", Icons.Default.SatelliteAlt, "nav_satellite"),
  EMAIL_SHIELD("Correo IA", Icons.Default.MarkEmailRead, "nav_email_shield"),
  SCANNER("Escáner", Icons.Default.Radar, "nav_scanner"),
  SENTINEL("Enjambre IA", Icons.Default.Psychology, "nav_sentinel"),
  PAYMENTS("Pagos & Banco", Icons.Default.AccountBalance, "nav_payments"),
  ENTERPRISE("Empresas", Icons.Default.CorporateFare, "nav_enterprise"),
  VAULT("Bóveda", Icons.Default.EnhancedEncryption, "nav_vault"),
  CERTIFICATE("Titular", Icons.Default.WorkspacePremium, "nav_certificate"),
  VOICE_NEXUS("Voz Aegis", Icons.Default.RecordVoiceOver, "nav_voice_nexus")
}

class MainActivity : ComponentActivity() {

  private val viewModel: CyberSecurityViewModel by viewModels {
    object : ViewModelProvider.Factory {
      @Suppress("UNCHECKED_CAST")
      override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CyberSecurityViewModel(application) as T
      }
    }
  }
  private var targetScreen by mutableStateOf(CyberNavScreen.OVERVIEW)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    handleNotificationIntent(intent)
    setContent {
      MyApplicationTheme {
        CyberApp(
          viewModel = viewModel,
          initialScreen = targetScreen
        )
      }
    }
  }

  override fun onNewIntent(intent: Intent) {
    super.onNewIntent(intent)
    setIntent(intent)
    handleNotificationIntent(intent)
  }

  private fun handleNotificationIntent(intent: Intent?) {
    if (intent?.getStringExtra("EXTRA_NAV_SCREEN") == "SENTINEL") {
      targetScreen = CyberNavScreen.SENTINEL
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CyberApp(
  viewModel: CyberSecurityViewModel,
  initialScreen: CyberNavScreen = CyberNavScreen.OVERVIEW
) {
  val uiState by viewModel.uiState.collectAsState()
  var currentScreen by rememberSaveable { mutableStateOf(initialScreen) }
  val context = LocalContext.current

  // Permission Launcher for Android 13+ (POST_NOTIFICATIONS)
  val notificationPermissionLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestPermission()
  ) { isGranted ->
    // Notification permission granted or denied
  }

  LaunchedEffect(Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      val isGranted = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.POST_NOTIFICATIONS
      ) == PackageManager.PERMISSION_GRANTED
      if (!isGranted) {
        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
      }
    }
  }

  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .background(ObsidianBackground),
    contentWindowInsets = WindowInsets.safeDrawing,
    topBar = {
      TopAppBar(
        title = {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
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
                  text = "ARMAS QUANTUM MESH",
                  color = CyberCyan,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Black,
                  letterSpacing = 1.sp
                )
              }
              Text(
                text = "Licencia Soberana: Alejandro Armas",
                color = CyberTextSecondary,
                fontSize = 10.sp
              )
            }

            Row(
              horizontalArrangement = Arrangement.spacedBy(6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              // WorkManager System Tray Notification Pill Button
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(8.dp))
                  .background(
                    if (uiState.systemTrayAlertsEnabled) ThreatCrimson.copy(alpha = 0.15f)
                    else ObsidianBorder.copy(alpha = 0.3f)
                  )
                  .border(
                    1.dp,
                    if (uiState.systemTrayAlertsEnabled) ThreatCrimson.copy(alpha = 0.6f)
                    else ObsidianBorder,
                    RoundedCornerShape(8.dp)
                  )
                  .clickable { viewModel.triggerTestThreatAlert() }
                  .padding(horizontal = 6.dp, vertical = 4.dp)
                  .testTag("top_bar_alert_test_btn")
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = if (uiState.systemTrayAlertsEnabled) Icons.Default.NotificationsActive else Icons.Default.NotificationsOff,
                    contentDescription = "Alerta WorkManager",
                    tint = if (uiState.systemTrayAlertsEnabled) ThreatCrimson else CyberTextMuted,
                    modifier = Modifier.size(12.dp)
                  )
                  Spacer(modifier = Modifier.width(3.dp))
                  Text(
                    text = if (uiState.systemTrayAlertsEnabled) "TRAY ON" else "TRAY OFF",
                    color = if (uiState.systemTrayAlertsEnabled) ThreatCrimson else CyberTextMuted,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }

              // Sentinel AI Active Badge
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(8.dp))
                  .background(if (uiState.isSentinelAiActive) CyberEmerald.copy(alpha = 0.15f) else ThreatCrimson.copy(alpha = 0.15f))
                  .border(
                    1.dp,
                    if (uiState.isSentinelAiActive) CyberEmerald.copy(alpha = 0.6f) else ThreatCrimson.copy(alpha = 0.6f),
                    RoundedCornerShape(8.dp)
                  )
                  .clickable { viewModel.toggleSentinelAi() }
                  .padding(horizontal = 8.dp, vertical = 4.dp)
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = "Shield",
                    tint = if (uiState.isSentinelAiActive) CyberEmerald else ThreatCrimson,
                    modifier = Modifier.size(12.dp)
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = if (uiState.isSentinelAiActive) "IA ACTIVA" else "IA PAUSADA",
                    color = if (uiState.isSentinelAiActive) CyberEmerald else ThreatCrimson,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
            }
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = ObsidianSurface,
          titleContentColor = CyberTextPrimary
        )
      )
    },
    bottomBar = {
      ScrollableTabRow(
        selectedTabIndex = currentScreen.ordinal,
        modifier = Modifier.testTag("cyber_bottom_navigation"),
        containerColor = ObsidianSurface,
        contentColor = CyberCyan,
        edgePadding = 8.dp,
        indicator = { tabPositions ->
          if (currentScreen.ordinal < tabPositions.size) {
            TabRowDefaults.SecondaryIndicator(
              modifier = Modifier.tabIndicatorOffset(tabPositions[currentScreen.ordinal]),
              color = CyberCyan
            )
          }
        }
      ) {
        CyberNavScreen.entries.forEach { screen ->
          val selected = currentScreen == screen
          Tab(
            modifier = Modifier.testTag(screen.tag),
            selected = selected,
            onClick = { currentScreen = screen },
            icon = {
              Icon(
                imageVector = screen.icon,
                contentDescription = screen.title,
                tint = if (selected) CyberCyan else CyberTextMuted,
                modifier = Modifier.size(20.dp)
              )
            },
            text = {
              Text(
                text = screen.title,
                fontSize = 10.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                color = if (selected) CyberCyan else CyberTextMuted,
                maxLines = 1
              )
            }
          )
        }
      }
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(ObsidianBackground)
        .padding(innerPadding)
    ) {
      // HUD Holográfico Global y Botón Maestro 1-Tap Shield Max
      GlobalSovereignHudBar(
        state = uiState,
        onToggleHud = { viewModel.toggleHoloHud() },
        onMasterShieldClick = { viewModel.triggerMasterQuantumShieldOptimization() },
        onNavigateToVoice = { currentScreen = CyberNavScreen.VOICE_NEXUS }
      )

      Box(modifier = Modifier.weight(1f)) {
        when (currentScreen) {
          CyberNavScreen.OVERVIEW -> {
            OverviewScreen(
              state = uiState,
              onDeepScanClick = { viewModel.runDeepIntegrityScan() },
              onSimulateThreatClick = { viewModel.simulateAttack("ransomware") },
              onNavigateToCertificate = { currentScreen = CyberNavScreen.CERTIFICATE },
              onNavigateToScanner = { currentScreen = CyberNavScreen.SCANNER },
              onNavigateToSentinel = { currentScreen = CyberNavScreen.SENTINEL },
              onNavigateToSatellite = { currentScreen = CyberNavScreen.SATELLITE },
              onNavigateToEmailShield = { currentScreen = CyberNavScreen.EMAIL_SHIELD },
              onNavigateToSynapseQ = { currentScreen = CyberNavScreen.SYNAPSE_Q },
              onTestNotificationClick = { viewModel.triggerTestThreatAlert() },
              onToggleNotificationAlerts = { viewModel.toggleSystemTrayAlerts() }
            )
          }
        CyberNavScreen.SYNAPSE_Q -> {
          SynapseQScreen(
            state = uiState.synapseQState,
            onSelectIntention = { intention -> viewModel.selectSynapseIntention(intention) },
            onSelectTargetNode = { node -> viewModel.selectSynapseTargetNode(node) },
            onToggleHaptic = { viewModel.toggleSynapseHaptic() },
            onToggleHarmonicAudio = { viewModel.toggleSynapseHarmonicAudio() },
            onTransmitPulse = { viewModel.transmitSynapsePulse() },
            onSimulateReceivePulse = { viewModel.simulateReceiveIncomingSynapsePulse() },
            onFreeTouchUpdate = { x, y -> viewModel.updateFreeTouchSynapse(x, y) },
            onDecodePulseWithAi = { pulse -> viewModel.decodePulseWithAi(pulse) },
            onClearHistory = { viewModel.clearSynapseHistory() },
            onToggleBroadcastMode = { viewModel.toggleSynapseBroadcastMode() },
            onBoostCoherence = { viewModel.boostMentalCoherence() },
            onToggleCustomComposer = { viewModel.toggleSynapseCustomComposer() },
            onUpdateCustomThought = { viewModel.updateSynapseCustomThought(it) },
            onSynthesizeCustomThought = { viewModel.synthesizeCustomIntentionWithAi() }
          )
        }
        CyberNavScreen.EMAIL_SHIELD -> {
          EmailQuantumShieldScreen(
            state = uiState.emailQuantumShield,
            onSetProtectedEmail = { email -> viewModel.setProtectedEmail(email) },
            onToggleShield = { viewModel.toggleEmailSwarmShield() },
            onRunAudit = { viewModel.runDeepEmailQuantumAudit() },
            onSimulateThreat = { threatType -> viewModel.simulateEmailThreat(threatType) },
            onGenerateSeal = { subject, snippet -> viewModel.generateEmailQuantumSignatureSeal(subject, snippet) },
            onClearSeal = { viewModel.clearGeneratedSeal() },
            onToggleRule = { ruleKey -> viewModel.toggleEmailRule(ruleKey) },
            onAddSecondaryEmail = { email -> viewModel.addSecondaryProtectedEmail(email) },
            onRemoveSecondaryEmail = { email -> viewModel.removeSecondaryProtectedEmail(email) },
            onClearThreats = { viewModel.clearEmailThreatIncidents() },
            onGenerateGhostAlias = { viewModel.generateNewGhostEmailAlias() },
            onDestroyGhostAlias = { aliasId -> viewModel.destroyGhostEmailAlias(aliasId) },
            onAnalyzeSandboxAttachment = { fileName -> viewModel.analyzeAttachmentInQuantumSandbox(fileName) }
          )
        }
        CyberNavScreen.SCANNER -> {
          DeviceNetworkScannerScreen(
            uiState = uiState,
            viewModel = viewModel
          )
        }
        CyberNavScreen.SENTINEL -> {
          SentinelAiScreen(
            state = uiState,
            onToggleSentinel = { viewModel.toggleSentinelAi() },
            onSimulateAttack = { threatType -> viewModel.simulateAttack(threatType) },
            onTriggerSwarmPurge = { viewModel.triggerSwarmPurge() },
            onBoostPrivacy = { viewModel.boostPrivacyShield() },
            onSimulateVirusWave = { virusType -> viewModel.simulateVirusWaveAttack(virusType) },
            onToggleTrafficLive = { viewModel.toggleTrafficLiveFeed() },
            onSelectTrafficFilter = { filter -> viewModel.setTrafficFilter(filter) },
            onInjectTrafficBurst = { viewModel.injectMaliciousTrafficBurst() },
            onDeleteThreatLog = { id -> viewModel.deleteThreatLog(id) },
            onClearAllThreatLogs = { viewModel.clearAllThreatLogs() },
            onRecordCustomThreatToRoom = { type -> viewModel.recordCustomThreatToRoom(type) },
            onToggleSystemTrayAlerts = { viewModel.toggleSystemTrayAlerts() },
            onTriggerTestNotificationAlert = { viewModel.triggerTestThreatAlert() },
            onTriggerSwarmEvolution = { viewModel.triggerSwarmEvolutionCycle() },
            onToggleAutoEvolution = { viewModel.toggleAutoEvolution() },
            onTriggerSelfRepair = { viewModel.triggerSwarmSelfRepairCycle() },
            onToggleAutoSelfRepair = { viewModel.toggleAutoSelfRepair() },
            onRollbackSwarmUpdate = { updateId -> viewModel.rollbackSwarmUpdate(updateId) },
            onActivateSwarmUpdate = { updateId -> viewModel.activateSwarmUpdate(updateId) },
            onTriggerPrivacySwarmScan = { viewModel.triggerSwarmPrivacyLeakScan() },
            onNeutralizeLeakedLink = { linkId -> viewModel.interceptAndNeutralizeLeakedLink(linkId) },
            onNeutralizeRogueDevice = { devId -> viewModel.neutralizePrivacyViolatingDevice(devId) },
            onToggleAutoNeutralizeLeaks = { viewModel.toggleAutoNeutralizeLeaks() },
            onToggleDataBrokerFirewall = { viewModel.toggleDataBrokerFirewall() },
            onRefreshGpsLocation = { ctx -> viewModel.refreshGpsLocation(ctx) },
            onToggleLocationCloak = { viewModel.toggleLocationPrivacyCloak() },
            onSimulateGpsLeakBlock = { reqName -> viewModel.blockUnauthorizedLocationAttempt(reqName) },
            onFreezeLegalEvidence = { viewModel.freezeAndCompileLegalEvidenceDossier() },
            onGetComplaintText = { viewModel.generateFormalLegalComplaintText() },
            onMarkLegalDossierExported = { viewModel.markLegalDossierExported() },
            onToggleNeuroDefense = { viewModel.toggleNeuroDefense() },
            onToggleMemoryMorphing = { viewModel.toggleMemoryMorphing() },
            onSynthesizeImmediateAntigen = { name, vector -> viewModel.synthesizeImmediateAntigen(name, vector) },
            onDeployHologram = { name, type -> viewModel.deployHolographicDecoy(name, type) },
            onAnalyzeGemini = { query -> viewModel.analyzeWithGeminiCognitiveAi(query) },
            onSetCustomApiKey = { key -> viewModel.setCustomGeminiApiKey(key) },
            onNavigateToEmailShield = { currentScreen = CyberNavScreen.EMAIL_SHIELD },
            onRunEmailAudit = { viewModel.runDeepEmailQuantumAudit() },
            onSimulateEmailThreat = { threatType -> viewModel.simulateEmailThreat(threatType) }
          )
        }
        CyberNavScreen.SATELLITE -> {
          ArmasSatelliteChipScreen(
            state = uiState,
            onDialpadInput = { digit -> viewModel.dialpadInput(digit) },
            onDialpadBackspace = { viewModel.dialpadBackspace() },
            onDialpadClear = { viewModel.dialpadClear() },
            onSetDialpadNumber = { number -> viewModel.setDialpadNumber(number) },
            onSelectCountry = { country -> viewModel.selectCountryDial(country) },
            onSetMicPermissionGranted = { granted -> viewModel.setMicPermissionGranted(granted) },
            onStartCall = { number, name -> viewModel.startSatelliteCall(number, name) },
            onEndCall = { viewModel.endSatelliteCall() },
            onToggleMute = { viewModel.toggleCallMute() },
            onToggleSpeaker = { viewModel.toggleCallSpeaker() },
            onToggleAntiRecording = { viewModel.toggleCallAntiRecordingNoise() },
            onToggleBypassTowers = { viewModel.toggleBypassTerrestrialTowers() },
            onToggleLaserMesh = { viewModel.toggleLaserInterSatelliteMesh() },
            onToggleChipActivation = { viewModel.toggleChipActivation() },
            onTriggerSos = { viewModel.triggerSatelliteEmergencySos() },
            onCancelSos = { viewModel.cancelSatelliteEmergencySos() },
            onClearHistory = { viewModel.clearSatelliteCallHistory() },
            onSendSatelliteMessage = { recipient, content, viaNativeSms, ctx ->
              viewModel.sendSatelliteMessage(recipient, content, viaNativeSms, ctx)
            },
            onUpdateDraftRecipient = { recipient -> viewModel.updateSatelliteDraftRecipient(recipient) },
            onUpdateDraftMessage = { msg -> viewModel.updateSatelliteDraftMessage(msg) },
            onSendEmergencySosMessage = { viewModel.sendEmergencySosSatelliteMessage() },
            onClearMessages = { viewModel.clearSatelliteMessages() },
            onToggleSatelliteInternet = { enable -> viewModel.toggleSatelliteInternet(enable) },
            onRunSpeedTest = { viewModel.runSatelliteSpeedTest() },
            onToggleVpn = { viewModel.toggleSatelliteVpn() },
            onSetDnsProvider = { dns -> viewModel.setSatelliteDns(dns) },
            onNavigateToSynapseQ = { currentScreen = CyberNavScreen.SYNAPSE_Q }
          )
        }
        CyberNavScreen.PAYMENTS -> {
          BankAndPaymentsScreen(
            uiState = uiState,
            onRecordDeposit = { clientName, planName, amount, ref, method, installment ->
              viewModel.recordDepositPayment(clientName, planName, amount, ref, method, installment)
            },
            onClearReceiptDialog = { viewModel.clearLastDepositReceipt() }
          )
        }
        CyberNavScreen.ENTERPRISE -> {
          EnterprisePortalScreen(
            state = uiState,
            onEvaluateApplication = { name, sector, budget, hsm, clearance ->
              viewModel.evaluateEnterpriseQualification(name, sector, budget, hsm, clearance)
            },
            onNavigateToBank = { currentScreen = CyberNavScreen.PAYMENTS }
          )
        }
        CyberNavScreen.VAULT -> {
          QuantumVaultScreen(
            state = uiState,
            onEncryptPayload = { plainText -> viewModel.encryptPayload(plainText) },
            onDecryptPayload = { cipherText -> viewModel.decryptPayload(cipherText) },
            onToggleRule = { ruleId -> viewModel.toggleDefenseSetting(ruleId) }
          )
        }
        CyberNavScreen.CERTIFICATE -> {
          BackHandler { currentScreen = CyberNavScreen.OVERVIEW }
          LicenseCertificateScreen(
            license = uiState.license
          )
        }
        CyberNavScreen.VOICE_NEXUS -> {
          BackHandler { currentScreen = CyberNavScreen.OVERVIEW }
          AegisVoiceControlScreen(
            state = uiState.aegisVoiceState,
            onToggleListening = { viewModel.toggleVoiceListening() },
            onToggleTts = { viewModel.toggleTtsFeedback() },
            onToggleProtocol = { protocol -> viewModel.toggleNexusProtocol(protocol) },
            onResetProtocols = { enableAll -> viewModel.resetAllNexusProtocols(enableAll) },
            onSimulateCommand = { cmd -> viewModel.simulateVoiceCommandExecution(cmd) }
          )
        }
      }
    }
  }
}

  uiState.masterShieldSummary?.let { summary ->
    AlertDialog(
      onDismissRequest = { viewModel.dismissMasterShieldSummary() },
      title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.CheckCircle, contentDescription = null, tint = CyberEmerald)
          Spacer(modifier = Modifier.width(8.dp))
          Text("BLINDAJE CUÁNTICO TOTAL", color = CyberTextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
      },
      text = {
        Text(summary, color = CyberTextSecondary, fontSize = 12.sp, lineHeight = 18.sp)
      },
      confirmButton = {
        Button(
          onClick = { viewModel.dismissMasterShieldSummary() },
          colors = ButtonDefaults.buttonColors(containerColor = CyberEmerald, contentColor = ObsidianBackground)
        ) {
          Text("CONFIRMAR SOBERANÍA", fontWeight = FontWeight.Bold)
        }
      },
      containerColor = ObsidianSurface,
      shape = RoundedCornerShape(16.dp)
    )
  }
}

@Composable
private fun GlobalSovereignHudBar(
  state: CyberUiState,
  onToggleHud: () -> Unit,
  onMasterShieldClick: () -> Unit,
  onNavigateToVoice: () -> Unit = {}
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 12.dp, vertical = 6.dp)
      .testTag("global_sovereign_hud_card"),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = androidx.compose.foundation.BorderStroke(1.dp, Brush.horizontalGradient(listOf(CyberCyan.copy(alpha = 0.5f), WarningGold.copy(alpha = 0.5f))))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clickable { onToggleHud() }
            .padding(vertical = 4.dp),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Box(
            modifier = Modifier
              .size(8.dp)
              .clip(CircleShape)
              .background(if (state.isSentinelAiActive) CyberEmerald else ThreatCrimson)
          )
          Text(
            text = "HUD CUÁNTICO SOBERANO",
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            color = CyberCyan,
            letterSpacing = 0.8.sp
          )
          Icon(
            imageVector = if (state.isHoloHudExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
            contentDescription = null,
            tint = CyberTextMuted,
            modifier = Modifier.size(16.dp)
          )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
          IconButton(
            onClick = onNavigateToVoice,
            modifier = Modifier
              .size(32.dp)
              .clip(CircleShape)
              .background(if (state.aegisVoiceState.isListening) ThreatCrimson.copy(alpha = 0.2f) else CyberCyan.copy(alpha = 0.15f))
              .border(1.dp, if (state.aegisVoiceState.isListening) ThreatCrimson else CyberCyan.copy(alpha = 0.6f), CircleShape)
              .testTag("hud_voice_shortcut_btn")
          ) {
            Icon(
              imageVector = Icons.Default.RecordVoiceOver,
              contentDescription = "Voz Aegis Nexus",
              tint = if (state.aegisVoiceState.isListening) ThreatCrimson else CyberCyan,
              modifier = Modifier.size(16.dp)
            )
          }

          Button(
            onClick = onMasterShieldClick,
            enabled = !state.isMasterShieldOptimizing,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = WarningGold,
              contentColor = ObsidianBackground
            ),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp),
            modifier = Modifier.testTag("btn_master_shield_max")
          ) {
            if (state.isMasterShieldOptimizing) {
              CircularProgressIndicator(modifier = Modifier.size(12.dp), strokeWidth = 2.dp, color = ObsidianBackground)
              Spacer(modifier = Modifier.width(6.dp))
              Text("BLINDANDO...", fontSize = 10.sp, fontWeight = FontWeight.Black)
            } else {
              Icon(Icons.Default.Bolt, contentDescription = null, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("1-TAP SHIELD MAX", fontSize = 10.sp, fontWeight = FontWeight.Black)
            }
          }
        }
      }

      AnimatedVisibility(visible = state.isHoloHudExpanded) {
        Column(modifier = Modifier.padding(top = 8.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            HudPillItem(
              label = "PQC KYBER-1024",
              sub = "100% Inmune",
              color = CyberEmerald,
              modifier = Modifier.weight(1f)
            )
            HudPillItem(
              label = "CHIP SATELITAL",
              sub = "${state.satelliteChipState.activeSatelliteOrbit.take(14)}...",
              color = WarningGold,
              modifier = Modifier.weight(1f)
            )
          }
          Spacer(modifier = Modifier.height(6.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            HudPillItem(
              label = "SYNAPSE-Q",
              sub = "EPR ${state.synapseQState.entanglementFidelity}%",
              color = QuantumPurple,
              modifier = Modifier.weight(1f)
            )
            HudPillItem(
              label = "BLINDAJE CORREO",
              sub = "${state.emailQuantumShield.threatsNeutralized} Neutralizadas",
              color = CyberCyan,
              modifier = Modifier.weight(1f)
            )
          }
          Spacer(modifier = Modifier.height(6.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            HudPillItem(
              label = "VOZ AEGIS NEXUS",
              sub = if (state.aegisVoiceState.isListening) "Escuchando micrófono..." else "Protocolos 100% listos",
              color = if (state.aegisVoiceState.isListening) ThreatCrimson else CyberCyan,
              modifier = Modifier.weight(1f)
            )
          }
        }
      }
    }
  }
}

@Composable
private fun HudPillItem(label: String, sub: String, color: Color, modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(8.dp))
      .background(ObsidianElevated)
      .border(0.5.dp, color.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
      .padding(horizontal = 8.dp, vertical = 5.dp)
  ) {
    Column {
      Text(label, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = color)
      Text(sub, fontSize = 8.5.sp, color = CyberTextSecondary, maxLines = 1)
    }
  }
}
