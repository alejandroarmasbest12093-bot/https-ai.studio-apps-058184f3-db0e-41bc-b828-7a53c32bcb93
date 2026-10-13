package com.example.model

enum class ThreatSeverity {
  CRITICAL,
  HIGH,
  MEDIUM,
  LOW
}

enum class ThreatStatus {
  NEUTRALIZED,
  QUARANTINED,
  ANALYZING
}

data class ThreatIncident(
  val id: String,
  val threatName: String,
  val category: String, // "Ransomware", "Polymorphic Virus", "Zero-Day Kernel Exploit", "Quantum Decryption"
  val sourceVector: String,
  val severity: ThreatSeverity,
  val status: ThreatStatus,
  val timestamp: String,
  val aiMitigationLog: String,
  val signatureHash: String
)

data class QuantumNode(
  val id: String,
  val location: String,
  val ipMasked: String,
  val latencyMs: Int,
  val quantumFidelity: Double, // e.g. 99.999%
  val status: String,
  val activeBandwidthGbps: Double
)

data class EnterpriseApplication(
  val entityName: String,
  val industrySector: String,
  val estimatedBudgetMillions: Double,
  val hasHsmLevel4: Boolean,
  val hasSovereignKybClearance: Boolean,
  val isApproved: Boolean,
  val assignedNodeCluster: String?,
  val auditReport: String
)

data class SovereignLicenseInfo(
  val ownerName: String = "Alejandro Armas",
  val role: String = "Titular & Creador Absoluto de la Licencia Soberana",
  val softwareTitle: String = "Armas CyberDefense OS™ & Red Cuántica Inviolable (ASQN)",
  val patentReference: String = "PAT-ARMAS-2026-QUANTUM-ZERO-TRUST",
  val masterFingerprint: String = "ARMAS-9812-F02A-899C-KYBER1024-ROOT",
  val estimatedNetworkValuation: String = "$150,000,000+ USD",
  val minEnterpriseAccessFee: String = "$10,000,000 USD / Cluster",
  val licenseType: String = "Propiedad Intelectual Soberana Inmutable"
)

data class DefenseMatrixSetting(
  val id: String,
  val title: String,
  val description: String,
  var isEnabled: Boolean
)

data class AiSwarmAgent(
  val id: String,
  val codename: String,
  val role: String,
  val status: String = "EN PATRULLA ACTIVA",
  val threatsVaporized: Int,
  val privacyShieldScore: Double = 100.0,
  val consensusLatencyMs: Double,
  val specialty: String
)

data class PrivacyShieldMetrics(
  val privacyIndex: Int = 100,
  val telemetryCloaked: Double = 100.0,
  val zeroDataLeakGuaranteed: Boolean = true,
  val spywareBlockedCount: Int = 6840,
  val trackersAndBeaconsVaporized: Long = 492_320L,
  val dnsTunnelingBlockedCount: Int = 1290,
  val metadataFingerprintCloaking: String = "ARMAS-PQC-CLOAK-100%::ACTIVE",
  val lastPrivacySweepTimestamp: String = "En Tiempo Real (Constante)"
)

data class VirusCategoryBlockStatus(
  val id: String,
  val categoryName: String,
  val virusExamples: String,
  val blockRatePercentage: Double = 100.0,
  val isTotalBlockActive: Boolean = true,
  val totalBlocked: Int,
  val responseTimeMicroseconds: String
)

enum class FlowDirection(val label: String, val code: String) {
  INCOMING("Entrante (Ingress)", "IN"),
  OUTGOING("Saliente (Egress)", "OUT")
}

data class TrafficFlowPoint(
  val id: String,
  val timeLabel: String,
  val timestampEpoch: Long,
  val incomingBlockedMbps: Float,
  val outgoingBlockedMbps: Float,
  val packetsBlocked: Int,
  val primaryInterceptionAgent: String
)

data class BlockedPacketEvent(
  val id: String,
  val direction: FlowDirection,
  val sourceIp: String,
  val destinationIp: String,
  val protocol: String,
  val bandwidthKbps: Double,
  val threatType: String,
  val interceptingAgent: String,
  val timestamp: String,
  val isDnsOrExfil: Boolean = false
)

data class TrafficDashboardSummary(
  val currentIncomingBlockedMbps: Float = 142.4f,
  val currentOutgoingBlockedMbps: Float = 98.7f,
  val peakBlockedMbps: Float = 286.0f,
  val totalPacketsBlockedToday: Long = 1_842_910L,
  val totalIncomingDataBlockedGb: Double = 412.8,
  val totalOutgoingDataBlockedGb: Double = 289.4,
  val isRealTimeFeedLive: Boolean = true,
  val selectedFilter: FlowDirection? = null
)

data class ConnectedDeviceNode(
  val id: String,
  val name: String,
  val ip: String,
  val mac: String,
  val deviceType: String, // "ROUTER", "LOCAL_HOST", "WORKSTATION", "IOT", "SMARTPHONE"
  val latencyMs: Int,
  val threatStatus: String, // "SEGURO_EN_MALLA", "MONITORIZADO_IA", "AMENAZA_DESMANTELADA", "AISLADO_EN_CUARENTENA"
  val isRogue: Boolean = false,
  val isQuarantined: Boolean = false,
  val openPorts: List<Int> = listOf(80, 443)
)

data class ConnectedNetworkState(
  val networkName: String = "Wi-Fi 6 (Quantum Mesh Protocol)",
  val connectionType: String = "Wi-Fi 6 (802.11ax Ultra-Low Latency)",
  val isConnected: Boolean = true,
  val isMetered: Boolean = false,
  val localIpAddress: String = "192.168.1.108",
  val publicIpAddress: String = "185.199.110.153 (Armas Quantum Cloak)",
  val gatewayIp: String = "192.168.1.1",
  val subnetMask: String = "255.255.255.0 (/24)",
  val dnsServers: List<String> = listOf("1.1.1.1 (DNS over TLS)", "Armas Quantum Resolver (0.0.0.0 Zero-Leak)"),
  val linkSpeedMbps: Int = 1200,
  val signalStrengthDbm: Int = -42,
  val networkSecurityProtocol: String = "WPA3-Enterprise + Kyber-1024 PQC Tunnel",
  val isQuantumTunnelActive: Boolean = true,
  val isArpPoisoningShieldActive: Boolean = true,
  val isDnsCryptShieldActive: Boolean = true,
  val connectedDevices: List<ConnectedDeviceNode> = emptyList(),
  val liveDownloadSpeedMbps: Float = 284.5f,
  val liveUploadSpeedMbps: Float = 142.1f,
  val liveLatencyMs: Int = 3
)

data class ScannedAppDetails(
  val packageName: String,
  val appName: String,
  val versionName: String,
  val isSystemApp: Boolean,
  val riskScore: Int, // 0 to 100
  val riskLevel: String, // "SEGURO", "BAJO", "MEDIO", "CRÍTICO_VIRUS"
  val dangerousPermissions: List<String>,
  val threatSignatures: List<String>,
  val isDismantled: Boolean = false,
  val isQuarantined: Boolean = false,
  val virusType: String? = null,
  val installTime: String = "Detectado en Almacenamiento"
)

data class FullSystemScanState(
  val isScanning: Boolean = false,
  val isDismantling: Boolean = false,
  val scanProgress: Float = 1.0f,
  val currentScanningPackage: String = "",
  val totalAppsScanned: Int = 0,
  val safeAppsCount: Int = 0,
  val suspiciousAppsCount: Int = 0,
  val virusesDismantledCount: Int = 0,
  val networkThreatsNeutralizedCount: Int = 0,
  val lastScanTimestamp: String = "En Vivo (Continuo)",
  val dismantleConsoleLogs: List<String> = emptyList(),
  val activeFilter: String = "TODAS" // "TODAS", "VIRUS", "SISTEMA", "USUARIO"
)

data class BankAccountDetails(
  val holderName: String = "Alejandro Armas",
  val notificationEmail: String = "alejandroarmasbest12093@gmail.com",
  val bankName: String = "Banco Santander & JPMorgan Chase International",
  val accountNumber: String = "4829 0192 8847 2019",
  val clabe: String = "012180004829019288",
  val swiftBic: String = "BSCHMXMMXXX",
  val iban: String = "CH93 0076 2011 6238 5295 7",
  val routingNumber: String = "021000021",
  val branchName: String = "División de Banca Privada & Fondos Tecnológicos",
  val accountType: String = "Cuenta Corriente Corporativa de Inversión & Cobros",
  val supportedCurrencies: String = "USD, EUR, MXN, Bóveda USDC Cuántica",
  val cryptoVaultAddress: String = "0x71CA98F02A899C1024ARMASQUANTUMDEPOSIT94A",
  val taxId: String = "RFC / Tax ID: ARMA-980412-PQC-01",
  val memoInstruction: String = "Referencia obligatoria: ARMAS-[NUM_FACTURA]"
)

data class PaymentPlan(
  val id: String,
  val name: String,
  val badge: String,
  val tagline: String,
  val monthlyPriceUsd: Double,
  val annualPriceUsd: Double,
  val discountAnnualPercent: Int,
  val features: List<String>,
  val installmentOptions: List<String>,
  val isFeatured: Boolean = false
)

data class DepositTransaction(
  val id: String,
  val clientOrEntityName: String,
  val planName: String,
  val amountUsd: Double,
  val bankReference: String,
  val paymentMethod: String, // "CLABE / SPEI", "SWIFT / IBAN", "Depósito Ventanilla", "USDC / Quantum Ledger"
  val installmentPlan: String, // "Pago Único Contado", "3 Cuotas sin Interés", "6 Cuotas", "12 Cuotas"
  val timestamp: String,
  val status: String, // "ACREDITADO_EN_CUENTA", "EN_REVISION_BANCARIA", "PROCESADO"
  val digitalReceiptHash: String,
  val beneficiary: String = "Alejandro Armas"
)

data class AppReleaseDetails(
  val appName: String = "Armas Quantum Mesh",
  val versionName: String = "3.0.0-RELEASE",
  val versionCode: Int = 3,
  val targetPlatform: String = "Android 16 (API 36)",
  val minSdk: Int = 24,
  val buildDate: String = "Septiembre 2026",
  val packageId: String = "com.aistudio.armasmesh.qkrzyp",
  val sha256Signature: String = "9E:B4:72:C1:8A:F3:D2:11:4A:67:E9:52:10:8C:3B:4D:21:6F:09:A8:33:14:87:CD:6E:9B:41:2F:58:AA:12:00",
  val apkDownloadFileName: String = "ArmasQuantumMesh-v3.0.0-release.apk",
  val estimatedSizeMb: Double = 18.6,
  val playStoreReadinessScore: Int = 100,
  val releaseNotes: List<String> = listOf(
    "Motor de Red Cuántica Kyber-1024 con Zero-Leak garantizado al 100%",
    "Escáner y desmantelador absoluto de virus, ransomware y spyware",
    "Módulo de Cuenta Bancaria oficial a nombre de Alejandro Armas y planes de pago en cuotas",
    "Generación de comprobantes de depósito verificados y órdenes criptográficas",
    "Preparación de lanzamiento para Google Play Store y distribución directa de APK",
    "Enjambre IA de Auto-Evolución y Auto-Actualización continua de funcionalidades"
  )
)

enum class SwarmUpdateStatus {
  DISCOVERED,
  SYNTHESIZING,
  AUTO_APPLIED,
  ACTIVE
}

enum class SwarmRepairCategory {
  KERNEL_CRASH_PREVENTION,
  MEMORY_LEAK_HOTFIX,
  NETWORK_TIMEOUT_REMEDY,
  ZERO_DAY_DEFENSE_UPGRADE,
  BATTERY_PERFORMANCE_OPTIMIZATION,
  DATABASE_INTEGRITY_SHIELD
}

data class SwarmSelfRepairEvent(
  val id: String,
  val timestamp: String,
  val diagnosedIssue: String,
  val targetSubsystem: String,
  val severity: ThreatSeverity,
  val diagnosingDrone: String,
  val patchName: String,
  val resolutionDetails: String,
  val memoryFreedKb: Long = 0L,
  val isResolved: Boolean = true,
  val latencyRecoveredMs: Int = 0
)

data class AiSwarmEvolutionUpdate(
  val id: String,
  val title: String,
  val category: String,
  val discoveredByDrone: String,
  val discoveryTimestamp: String,
  val evolutionVersion: String,
  val status: SwarmUpdateStatus = SwarmUpdateStatus.ACTIVE,
  val description: String,
  val operationalImpact: String,
  val codeDiffSummary: String,
  val capabilitiesAdded: List<String>,
  val efficiencyGain: String,
  val isCustomDiscovered: Boolean = false,
  val isAutoRepairPatch: Boolean = false
)

data class SwarmDiscoveryTelemetry(
  val totalDiscoveries: Int = 8,
  val autoAppliedPatches: Int = 8,
  val faultsSelfHealedCount: Int = 14,
  val lastEvolutionTime: String = "Hace instantes",
  val swarmIntelligenceScore: Int = 998,
  val activeDroneCount: Int = 6,
  val autoEvolutionEnabled: Boolean = true,
  val autoSelfRepairEnabled: Boolean = true,
  val currentPhase: String = "Vigilancia Heurística y Síntesis Continua",
  val healthIntegrityPercent: Int = 100
)

data class BluetoothScannedDevice(
  val id: String,
  val deviceName: String,
  val macAddress: String,
  val rssi: Int,
  val deviceType: String,
  val isPaired: Boolean,
  val threatLevel: ThreatSeverity,
  val detectedVirusOrExploit: String? = null,
  val vulnerabilityDescription: String? = null,
  val isBlocked: Boolean = false,
  val lastSeen: String = "Detectado ahora",
  val encryptionProfile: String = "BLE Secure v5.2"
)

data class BluetoothScanState(
  val isScanning: Boolean = false,
  val isBluetoothSupported: Boolean = true,
  val isBluetoothEnabled: Boolean = true,
  val antiSniffingShieldActive: Boolean = true,
  val scannedDevices: List<BluetoothScannedDevice> = emptyList(),
  val totalThreatsFound: Int = 0,
  val blockedThreatsCount: Int = 0,
  val lastScanTimestamp: String = "Sin escaneo reciente",
  val scanProgress: Float = 0f,
  val activeScanStatusText: String? = null,
  val bluetoothConsoleLogs: List<String> = emptyList()
)

data class LeakedPrivacyLink(
  val id: String,
  val targetUrl: String,
  val titleOrSource: String,
  val exposedDataSummary: String,
  val category: String,
  val severity: ThreatSeverity,
  val isBlockedAndNeutralized: Boolean = true,
  val discoveredByDrone: String,
  val timestamp: String = "Detectado e interceptado en vivo",
  val countermeasureLog: String = "DNS Sinkholed & Solicitud de Eliminación PQC enviada"
)

data class TrackedPrivacyViolatingDevice(
  val id: String,
  val deviceName: String,
  val macOrImsi: String,
  val hardwareType: String,
  val threatVector: String,
  val signalStrengthRssi: Int,
  val proximityEstimate: String,
  val isNeutralized: Boolean = true,
  val neutralizationLog: String
)

data class SwarmPrivacyLiveTrackerState(
  val isTrackingLive: Boolean = true,
  val autoNeutralizeLeaks: Boolean = true,
  val antiDataBrokerFirewallActive: Boolean = true,
  val monitoredLinks: List<LeakedPrivacyLink> = emptyList(),
  val trackedDevices: List<TrackedPrivacyViolatingDevice> = emptyList(),
  val linksBlockedCount: Int = 18,
  val rogueDevicesNeutralizedCount: Int = 7,
  val dataPointsProtectedCount: Long = 148_920L,
  val isScanningForLeaks: Boolean = false,
  val scanProgress: Float = 0f,
  val activeScanStatus: String? = null,
  val liveTrackerLogs: List<String> = emptyList()
)

data class GpsLocationState(
  val isGpsAvailable: Boolean = true,
  val isGpsEnabled: Boolean = true,
  val hasPermission: Boolean = false,
  val isLocating: Boolean = false,
  val latitude: Double = 19.432608,
  val longitude: Double = -99.133209,
  val altitudeMeters: Double = 2240.5,
  val accuracyMeters: Float = 2.3f,
  val speedKmh: Float = 0.0f,
  val bearingDegrees: Float = 42.0f,
  val provider: String = "GPS Cuántico Satelital (Galileo/GPS L5)",
  val timestampString: String = "Tiempo Real Activo",
  val humanReadableParadero: String = "Zona Metropolitana Central • Paradero Protegido por Blindaje Soberano",
  val isLocationCloakedAgainstTrackers: Boolean = true,
  val locationQueriesBlockedCount: Int = 54,
  val locationAuditStatus: String = "Paradero Físico Cifrado (Ofuscación Activa)",
  val satellitesTracked: Int = 18,
  val telemetryLogs: List<String> = emptyList()
)

data class LegalEvidenceItem(
  val id: String,
  val category: String, // "UBICACIÓN Y PARADERO", "DISPOSITIVO INFRACTOR", "ENLACE DE FILTRACIÓN", "INCIDENTE CRÍTICO"
  val title: String,
  val technicalDetails: String,
  val timestampUtc: String,
  val sha256IntegrityHash: String,
  val chainOfCustodyStatus: String = "PRESERVADO_INMUTABLE"
)

data class LegalEvidenceDossierState(
  val caseNumber: String = "EXP-ARMAS-2026-LEG-001",
  val complainantName: String = "Alejandro Armas",
  val complainantEmail: String = "alejandroarmasbest12093@gmail.com",
  val targetRecipientAuthority: String = "Fiscalía Especializada en Delitos Cibernéticos & Policía Cibernética",
  val legalActionStatus: String = "EVIDENCIA CONGELADA Y LISTA PARA ENTREGA FORMAL",
  val generatedDateUtc: String = "2026-09-10 22:45 UTC",
  val masterChainOfCustodyHash: String = "SHA256:7B8F9A1098C023DAFE08126749A32155BFEC678129048123901A83749021BCCD",
  val evidenceItems: List<LegalEvidenceItem> = emptyList(),
  val legalSummaryNote: String = "Expediente técnico pericial compilado bajo estándares RFC 3227 e ISO/IEC 27037 para la persecución formal de delitos informáticos, espionaje no consentido, geolocalización ilícita y exfiltración de datos personales.",
  val isDossierExported: Boolean = false,
  val auditLogs: List<String> = emptyList()
)

data class DigitalAntigen(
  val id: String,
  val antigenName: String,
  val targetZeroDayVector: String,
  val threatClassification: String,
  val synthesisTimestamp: String,
  val dynamicBytecodeHash: String,
  val immunityScope: String, // "KERNEL_MEMORY_DEFENSE", "ZERO_CLICK_EXPLOIT_SINKHOLE", "BASEBAND_ISOLATION", "HARDWARE_REGISTRY_CLOAK"
  val neutralizationLatencyMs: Double = 0.04,
  val isActive: Boolean = true
)

data class NeuralDeceptionHologram(
  val id: String,
  val honeynetNodeName: String,
  val decoyTargetType: String, // "VIRTUAL_ROUTER_GATEWAY", "SYNTHETIC_GPS_BEACON", "GHOST_CREDENTIAL_VAULT", "DECEPTIVE_SOCKET_LISTENER"
  val activeIntrudersTrapped: Int,
  val syntheticBytesFed: Long,
  val telemetryHarvested: String,
  val isActive: Boolean = true
)

data class CognitiveAiDiagnosisResult(
  val queryInput: String,
  val riskScore: Int, // 0 to 100
  val threatVector: String,
  val mitreAttackTechnique: String,
  val deepAiExplanation: String,
  val synthesizedAntigen: DigitalAntigen?,
  val recommendedAction: String,
  val timestamp: String
)

data class QuantumNeuroDefenseState(
  val isNeuroDefenseActive: Boolean = true,
  val synapticFiringRateGhz: Double = 2.84,
  val zeroDayImmunityRate: Double = 99.99,
  val quantumMemoryMorphingActive: Boolean = true,
  val memoryMorphIntervalSeconds: Int = 15,
  val morphEntropySignature: String = "Q-MORPH-0x89FA::POLYMORPHIC_ADDRESS_SHUFFLED",
  val activeDigitalAntigens: List<DigitalAntigen> = emptyList(),
  val activeHolograms: List<NeuralDeceptionHologram> = emptyList(),
  val totalAntigensSynthesized: Int = 42,
  val zeroDaysVaporizedCount: Int = 19,
  val intrudersTrappedInHoneynet: Int = 7,
  val isAnalyzingWithGemini: Boolean = false,
  val lastDiagnosisResult: CognitiveAiDiagnosisResult? = null,
  val customGeminiApiKey: String = "",
  val isSynthesizingAntigen: Boolean = false,
  val neuroTelemetryLogs: List<String> = emptyList()
)

// =========================================================================
// CHIP SATELITAL ARMAS & TELECOMUNICACIÓN ORBITAL SOBERANA (100% DIRECT-TO-CELL)
// =========================================================================

enum class SatelliteCallState {
  CONNECTING,
  ORBITAL_LOCK,
  ACTIVE,
  TERMINATING
}

enum class SatelliteCallType {
  OUTGOING,
  INCOMING,
  SOS_EMERGENCY
}

data class ArmasSatelliteContact(
  val id: String,
  val name: String,
  val satelliteNumber: String,
  val role: String,
  val isDirectSovereignLine: Boolean = true
)

data class ArmasSatelliteCallSession(
  val callId: String,
  val targetNumber: String,
  val targetName: String,
  val destinationCountry: String = "Satélite Armas Global",
  val destinationFlagEmoji: String = "🌐",
  val callState: SatelliteCallState = SatelliteCallState.CONNECTING,
  val durationSeconds: Int = 0,
  val satelliteTransponder: String = "ARMAS-SAT-04 'GUARDIÁN DEL PACÍFICO'",
  val quantumEncryptionProtocol: String = "Kyber-1024 + Falcon-512 PQC Audio E2EE",
  val isMuted: Boolean = false,
  val isSpeakerOn: Boolean = false,
  val isAntiRecordingNoiseActive: Boolean = true,
  val isMicActive: Boolean = true,
  val micVolumeDb: Float = -24f,
  val audioWaveformBars: List<Float> = listOf(0.2f, 0.4f, 0.7f, 0.5f, 0.8f, 0.6f, 0.3f, 0.5f),
  val audioBitrateKbps: Int = 32,
  val orbitalPingMs: Int = 21,
  val dopplerCorrectionKHz: Double = -1.18
)

data class ArmasSatelliteCallLog(
  val id: String,
  val phoneNumber: String,
  val contactName: String,
  val callType: SatelliteCallType,
  val durationFormatted: String,
  val satelliteUsed: String,
  val timestamp: String,
  val countryFlag: String = "🌐",
  val encryptionConfirmed: Boolean = true
)

data class ArmasSatelliteChipState(
  val isChipActivated: Boolean = true,
  val chipBrand: String = "ARMAS SATELLITE CHIP v1.0",
  val chipEdition: String = "Sovereign Orbital Direct-to-Cell Edition",
  val holderName: String = "Alejandro Armas",
  val chipPhoneNumber: String = "+999 ARMAS-770-001",
  val iccid: String = "89999-ARMAS-SAT-2026-X889",
  val imsi: String = "999-01-778942103",
  val sovereignStatus: String = "100% SOBERANO (0 INTERMEDIARIOS / CERO OPERADORAS)",
  val signalDbM: Int = -68, // Excellent LEO satellite beam
  val signalBars: Int = 5,
  val activeSatelliteOrbit: String = "ARMAS-SAT-04 'GUARDIÁN DEL PACÍFICO'",
  val constellationSize: Int = 12,
  val orbitalAltitudeKm: Int = 550,
  val elevationDegrees: Double = 67.4,
  val azimuthDegrees: Double = 142.1,
  val dopplerFrequencyOffsetKHz: Double = -1.24,
  val laserInterSatelliteMeshActive: Boolean = true,
  val antiEavesdroppingQuantumAudioActive: Boolean = true,
  val bypassTerrestrialTowers: Boolean = true, // Zero towers, zero Stingrays/IMSI-catchers
  val uplinkFrequencyBand: String = "Banda S / Ka Direct-to-Handset (2.1 GHz)",
  val downlinkFrequencyBand: String = "Banda Ka Órbita Baja (2.2 GHz PQC)",
  val activeCall: ArmasSatelliteCallSession? = null,
  val dialpadNumber: String = "",
  val selectedCountryDial: CountryDialInfo = ArmasCountryDirectory.DEFAULT_COUNTRY,
  val micPermissionGranted: Boolean = false,
  val liveMicLevelDb: Float = -24f,
  val liveSpectrumBars: List<Float> = listOf(0.2f, 0.4f, 0.7f, 0.5f, 0.8f, 0.6f, 0.3f, 0.5f),
  val isInSOSBeaconMode: Boolean = false,
  val callHistory: List<ArmasSatelliteCallLog> = emptyList(),
  val defaultContacts: List<ArmasSatelliteContact> = emptyList(),
  val satelliteTelemetryLogs: List<String> = emptyList(),
  val messagingState: ArmasSatelliteMessagingState = ArmasSatelliteMessagingState(),
  val internetState: ArmasSatelliteInternetState = ArmasSatelliteInternetState(),
  val orbitalPasses: List<OrbitalSatellitePass> = listOf(
    OrbitalSatellitePass("ARMAS-SAT-04", "ARMAS-SAT-04 'GUARDIÁN DEL PACÍFICO'", 550, 67.4, 142.1, "ENLACE ACTIVO", 0),
    OrbitalSatellitePass("ARMAS-SAT-01", "ARMAS-SAT-01 'NODO ALFA'", 548, 48.2, 88.5, "APROXIMACIÓN ÓPTIMA", 4),
    OrbitalSatellitePass("ARMAS-SAT-07", "ARMAS-SAT-07 'AURORA BOREALIS'", 552, 34.0, 210.0, "EN ESPERA", 11),
    OrbitalSatellitePass("ARMAS-SAT-12", "ARMAS-SAT-12 'ESCUDO AUSTRAL'", 555, 22.1, 315.4, "EN ESPERA", 19)
  ),
  val voicePqcEncryptionAlgorithm: String = "Kyber-1024 + Salto Espectral PQC (250 µs)",
  val voiceBitrateKbps: Int = 32
)

data class OrbitalSatellitePass(
  val id: String,
  val satelliteName: String,
  val altitudeKm: Int,
  val elevationAngleDeg: Double,
  val azimuthDeg: Double,
  val passStatus: String,
  val timeToWindowMinutes: Int
)

enum class SatelliteMessageStatus {
  TRANSMITTING_TO_ORBIT,
  DELIVERED_LEO,
  CONFIRMED_PQC,
  FAILED
}

data class ArmasSatelliteMessage(
  val id: String,
  val recipient: String,
  val recipientName: String,
  val content: String,
  val timestamp: String,
  val isOutgoing: Boolean,
  val status: SatelliteMessageStatus = SatelliteMessageStatus.CONFIRMED_PQC,
  val protocol: String = "NIST ML-KEM-1024 SMS Beam",
  val satelliteTransponder: String = "ARMAS-SAT-04 'GUARDIÁN DEL PACÍFICO'",
  val pqcSignatureHash: String = "SHA3-512::KYBER1024::PQC-BEAM"
)

data class ArmasSatelliteMessagingState(
  val draftRecipient: String = "",
  val draftMessage: String = "",
  val isSending: Boolean = false,
  val selectedProtocol: String = "SBD-LEO PQC Direct-to-Cell",
  val messages: List<ArmasSatelliteMessage> = listOf(
    ArmasSatelliteMessage(
      id = "SAT-MSG-001",
      recipient = "+1 202 555 0199",
      recipientName = "Centro de Mando Orbital",
      content = "Enlace orbital asegurado. Claves Dilithium-5 negociadas exitosamente con la constelación ARMAS-SAT.",
      timestamp = "10:14",
      isOutgoing = false,
      status = SatelliteMessageStatus.CONFIRMED_PQC
    ),
    ArmasSatelliteMessage(
      id = "SAT-MSG-002",
      recipient = "+1 202 555 0199",
      recipientName = "Centro de Mando Orbital",
      content = "Confirmado. Transmisión Direct-to-Cell activa sin intermediarios terrestres.",
      timestamp = "10:15",
      isOutgoing = true,
      status = SatelliteMessageStatus.CONFIRMED_PQC
    )
  )
)

data class ArmasSatelliteInternetState(
  val isConnected: Boolean = true,
  val connectionMode: String = "LEO Direct-to-Cell (Banda Ka / S Híbrida)",
  val downloadSpeedMbps: Float = 148.5f,
  val uploadSpeedMbps: Float = 36.2f,
  val latencyPingMs: Int = 21,
  val jitterMs: Int = 2,
  val packetLossPercent: Float = 0.0f,
  val allocatedIp: String = "192.0.2.77 (IP Soberana LEO - Cero Rastreo Comercial)",
  val dnsProvider: String = "DNS Cuántico Privado 1.1.1.1 / 9.9.9.9 DoH (Zero Logs)",
  val vpnTunnelActive: Boolean = true,
  val vpnProtocol: String = "WireGuard PQC Quantum Tunnel",
  val dataUsedMb: Float = 842.6f,
  val quotaRemaining: String = "ILIMITADO SOBERANO",
  val isSpeedTesting: Boolean = false,
  val speedTestProgress: Float = 0f,
  val speedTestPhase: String = "Listo",
  val activeTrafficPacketsPerSec: Int = 1240,
  val gatewayUrl: String = "https://google.com"
)

/**
 * =====================================================================
 * PROTOCOLO SYNAPSE-Q™: COMUNICACIÓN TELEPÁTICA CUÁNTICA SINESTÉSICA
 * =====================================================================
 * Paradigma de comunicación no-verbal, no-lineal y no-alfanumérica inventado
 * para Armas Quantum Mesh. Modula vectores de intención pura en:
 * 1) Mandala Cuántico Cromático de Fotones Entrelazados (Color/Geometría Fractal)
 * 2) Resonancia Háptica Subatómica (Micro-pulsos de frecuencia táctil en la mano)
 * 3) Armónicos Solfeggio & Schumann (Tono senoidal puro 40Hz, 432Hz, 528Hz, etc.)
 * 4) Estados Bell Cuánticos (|Ψ⁺⟩ = (|00⟩ + |11⟩)/√2) con entrelazamiento EPR.
 * 5) Decodificador Neuro-Cognitivo Gemini para interpretación semántica en tiempo real.
 */

enum class SynapseWaveCategory(val label: String, val frequencyRange: String) {
  GAMMA_EUREKA("Onda Gamma - Revelación Eureka", "38.0 - 45.0 Hz"),
  BETA_WILL("Onda Beta - Voluntad & Blindaje", "20.0 - 28.0 Hz"),
  ALPHA_EMPATHY("Onda Alfa - Empatía & Conexión", "8.0 - 12.0 Hz"),
  THETA_VOID("Onda Theta - Serenidad & Vacío", "4.5 - 7.5 Hz"),
  SOLFEGGIO_TRANSFORMATION("Frecuencia Solfeggio - Transformación", "528.0 Hz"),
  SOLFEGGIO_UNIVERSAL("Armónico Universal - Resonancia Cósmica", "432.0 Hz"),
  ULTRA_TACTICAL("Ultra-Beta - Alerta Táctica Inminente", "32.0 - 36.0 Hz"),
  FREE_SINESTHESIA("Sinestesia Cuántica Libre", "Modulación Dinámica en Vivo")
}

data class SynapseIntention(
  val id: String,
  val title: String,
  val shortTag: String,
  val category: SynapseWaveCategory,
  val frequencyHz: Float,
  val colorPrimaryHex: Long,
  val colorSecondaryHex: Long,
  val bellStateFormula: String,
  val hapticPattern: List<Long>,
  val semanticDescription: String,
  val telepathicPerception: String
)

data class SynapsePulse(
  val id: String,
  val senderNode: String,
  val receiverNode: String,
  val intention: SynapseIntention,
  val timestamp: String,
  val quantumFidelity: Float = 99.9998f,
  val bellStatePurity: String = "Triplet Bell |Ψ⁺⟩",
  val isOutgoing: Boolean = true,
  val pqcHash: String = "SYNAPSE-EPR-98F1::KEM1024",
  val neuroAiDecodedMeaning: String = ""
)

data class SynapseQState(
  val activeIntention: SynapseIntention = PredefinedSynapseIntentions.LIST.first(),
  val targetNode: String = "Nodo Primario Alejandro Armas [ALPHA-Q]",
  val availableNodes: List<String> = listOf(
    "Nodo Primario Alejandro Armas [ALPHA-Q]",
    "Constelación Orbital ARMAS-SAT LEO",
    "Bóveda Cuántica Soberana Central",
    "Nodo Mando Estratégico Aliado",
    "Enlace Cerebral Synapse-Q Libre"
  ),
  val isTransmitting: Boolean = false,
  val isReceiving: Boolean = false,
  val transmissionProgress: Float = 0f,
  val isEntangled: Boolean = true,
  val entanglementFidelity: Float = 99.9998f,
  val hapticEnabled: Boolean = true,
  val harmonicAudioEnabled: Boolean = true,
  val pulses: List<SynapsePulse> = PredefinedSynapseIntentions.INITIAL_PULSES,
  val liveDynamicFrequencyHz: Float = 40.0f,
  val freeTouchCoordinates: Pair<Float, Float>? = null,
  val neuroAiDecodedInsight: String? = null,
  val isSynthesizingAi: Boolean = false,
  val isBroadcastMode: Boolean = false,
  val mentalCoherenceScore: Float = 99.4f,
  val customComposerOpen: Boolean = false,
  val customThoughtInput: String = "",
  val customSynthesizedIntentions: List<SynapseIntention> = emptyList()
)

object PredefinedSynapseIntentions {
  val EUREKA = SynapseIntention(
    id = "INT-01",
    title = "Revelación Eureka & Claridad Cognitiva",
    shortTag = "EUREKA",
    category = SynapseWaveCategory.GAMMA_EUREKA,
    frequencyHz = 40.0f,
    colorPrimaryHex = 0xFF00E5FF, // Cyan
    colorSecondaryHex = 0xFFB388FF, // Purple
    bellStateFormula = "|Ψ⁺⟩ = (|00⟩ + |11⟩) / √2",
    hapticPattern = listOf(0, 70, 40, 110, 40, 180),
    semanticDescription = "Transmite comprensión instantánea, salto cuántico intuitivo y resolución de problemas sin palabras.",
    telepathicPerception = "El receptor percibe una súbita descarga de luz interior y una certeza mental cristalina."
  )

  val STRATEGIC_WILL = SynapseIntention(
    id = "INT-02",
    title = "Voluntad Estratégica & Blindaje Mental",
    shortTag = "VOLUNTAD",
    category = SynapseWaveCategory.BETA_WILL,
    frequencyHz = 24.0f,
    colorPrimaryHex = 0xFFFFB703, // Gold
    colorSecondaryHex = 0xFFFF2A6D, // Crimson
    bellStateFormula = "|Φ⁺⟩ = (|00⟩ - |11⟩) / √2",
    hapticPattern = listOf(0, 120, 80, 120, 80, 250),
    semanticDescription = "Transmite firmeza inquebrantable, orden de avance prioritario y postura de soberanía inviolable.",
    telepathicPerception = "El receptor siente una pulsación densa, cálida y un impulso de seguridad absoluto en la espina dorsal."
  )

  val EMPATHY_HARMONY = SynapseIntention(
    id = "INT-03",
    title = "Empatía Cuántica & Conexión Álmica",
    shortTag = "EMPATÍA",
    category = SynapseWaveCategory.ALPHA_EMPATHY,
    frequencyHz = 8.5f,
    colorPrimaryHex = 0xFF00FF9D, // Emerald
    colorSecondaryHex = 0xFFFF69B4, // Hot Pink
    bellStateFormula = "|Ψ⁻⟩ = (|01⟩ - |10⟩) / √2",
    hapticPattern = listOf(0, 50, 120, 50, 120, 60),
    semanticDescription = "Transmite abrazo cuántico no-físico, resonancia emocional armónica y validación profunda del ser.",
    telepathicPerception = "El receptor experimenta alivio térmico en el pecho, sensación de compañía incondicional y sosiego."
  )

  val SERENE_VOID = SynapseIntention(
    id = "INT-04",
    title = "Serenidad Absoluta & Silencio Mental",
    shortTag = "VACÍO",
    category = SynapseWaveCategory.THETA_VOID,
    frequencyHz = 5.5f,
    colorPrimaryHex = 0xFF4A90E2, // Blue
    colorSecondaryHex = 0xFF00FF9D, // Emerald
    bellStateFormula = "|Φ⁻⟩ = (|01⟩ + |10⟩) / √2",
    hapticPattern = listOf(0, 40, 200, 40, 200, 40),
    semanticDescription = "Disuelve el estrés mental, colapsa el ruido de fondo y transporta al receptor a calma pura.",
    telepathicPerception = "El receptor siente relajación instantánea en los hombros y desaparición inmediata de la ansiedad."
  )

  val SOLFEGGIO_REPAIR = SynapseIntention(
    id = "INT-05",
    title = "Frecuencia 528Hz: Reparación y Amor Universal",
    shortTag = "528 Hz",
    category = SynapseWaveCategory.SOLFEGGIO_TRANSFORMATION,
    frequencyHz = 528.0f,
    colorPrimaryHex = 0xFF00FF9D, // Emerald
    colorSecondaryHex = 0xFFFFB703, // Gold
    bellStateFormula = "|Ω⟩ = (|00⟩ + e^(iπ/3)|11⟩) / √2",
    hapticPattern = listOf(0, 90, 60, 90, 60, 90, 60, 180),
    semanticDescription = "Resonancia bio-cuántica para coherencia celular, restauración de energía y transmutación de discordias.",
    telepathicPerception = "El receptor percibe una vibración sutil y expansiva que alinea su ritmo cardíaco."
  )

  val TACTICAL_ALERT = SynapseIntention(
    id = "INT-06",
    title = "Alerta Táctica & Detección de Amenaza",
    shortTag = "ALERTA",
    category = SynapseWaveCategory.ULTRA_TACTICAL,
    frequencyHz = 34.0f,
    colorPrimaryHex = 0xFFFF2A6D, // Crimson
    colorSecondaryHex = 0xFFFFB703, // Gold
    bellStateFormula = "|Ψ!⟩ = (|10⟩ + |01⟩) / √2",
    hapticPattern = listOf(0, 150, 40, 150, 40, 150, 40, 300),
    semanticDescription = "Aviso instantáneo de precaución máxima, blindaje de sentidos y postura de alerta sin emitir ruido.",
    telepathicPerception = "El receptor siente tres golpes secos en la mano y una activación refleja inmediata de agudeza sensorial."
  )

  val LIST = listOf(
    EUREKA,
    STRATEGIC_WILL,
    EMPATHY_HARMONY,
    SERENE_VOID,
    SOLFEGGIO_REPAIR,
    TACTICAL_ALERT
  )

  val INITIAL_PULSES = listOf(
    SynapsePulse(
      id = "SYN-PULSE-001",
      senderNode = "Nodo Alejandro Armas [ALPHA-Q]",
      receiverNode = "Constelación Orbital ARMAS-SAT",
      intention = EUREKA,
      timestamp = "11:20:12",
      quantumFidelity = 99.9999f,
      bellStatePurity = "Triplet Bell |Ψ⁺⟩ Puro",
      isOutgoing = true,
      neuroAiDecodedMeaning = "Decodificación: Se ha alcanzado un salto conceptual en la arquitectura soberana. Claridad operativa total."
    ),
    SynapsePulse(
      id = "SYN-PULSE-002",
      senderNode = "Constelación Orbital ARMAS-SAT",
      receiverNode = "Nodo Alejandro Armas [ALPHA-Q]",
      intention = EMPATHY_HARMONY,
      timestamp = "11:21:04",
      quantumFidelity = 99.9997f,
      bellStatePurity = "Singlet Bell |Ψ⁻⟩",
      isOutgoing = false,
      neuroAiDecodedMeaning = "Decodificación: Enlace simbiótico confirmado. Coherencia de fase en todos los cúbits orbitales."
    )
  )
}
