package com.example.model

enum class EmailThreatType(val label: String, val severityLevel: String) {
  SPEAR_PHISHING("Spear-Phishing Cuántico", "CRÍTICO"),
  CREDENTIAL_HARVESTER("Robo de Credenciales & 2FA", "ALTO"),
  MALWARE_ATTACHMENT("Adjunto Cero-Day / Troyano", "CRÍTICO"),
  CEO_FRAUD_SPOOFING("Suplantación de Identidad / BEC", "ALTO"),
  SPY_TRACKER("Píxel de Rastreo Espía (Spy Pixel)", "MEDIO"),
  DATA_EXFILTRATION("Intento de Fuga de Información", "CRÍTICO"),
  SUSPICIOUS_REDIRECT("Enlace Acortado Redirigido", "MEDIO")
}

data class EmailSwarmDrone(
  val id: String,
  val codeName: String,
  val role: String,
  val status: String = "VIGILANCIA ACTIVA",
  val efficiencyPercentage: Int = 100,
  val threatsIntercepted: Int = 0,
  val specialty: String,
  val pqcModule: String = "NIST Kyber-1024"
)

data class EmailThreatIncident(
  val id: String,
  val timestamp: String,
  val sender: String,
  val subject: String,
  val threatType: EmailThreatType,
  val riskScore: Int,
  val mitigationAction: String,
  val droneInterceptor: String,
  val details: String,
  val isResolved: Boolean = true
)

data class EmailAuditItem(
  val id: String,
  val title: String,
  val status: String,
  val score: String,
  val description: String,
  val isSecure: Boolean
)

data class ArmasQuantumEmailState(
  val protectedEmail: String = "alejandroarmasbest12093@gmail.com",
  val holderName: String = "Alejandro Armas",
  val isSwarmShieldActive: Boolean = true,
  val securityGrade: String = "NIST NIVEL 5 (MÁXIMA SEGURIDAD POST-CUÁNTICA)",
  val pqcProtocol: String = "ML-KEM (Kyber-1024) + ML-DSA (Dilithium-5)",
  val swarmConsensusLatencyMs: Int = 3,
  val totalEmailsInspected: Long = 3450L,
  val threatsNeutralized: Int = 29,
  val spyPixelsPurged: Int = 412,
  val quarantinedPayloads: Int = 6,
  val darkWebBreachStatus: String = "CERO FILTRACIONES (0 LEAKS DETECTADOS)",
  val monitoredDatabasesCount: Int = 620,
  val lastAuditTimestamp: String = "Hoy 16:20 UTC",
  val isDeepAuditing: Boolean = false,
  val auditProgress: Float = 0f,
  val auditStatusMessage: String? = null,
  val quantumSignatureKeyId: String = "PQC-ARMAS-MAIL-994102-X",
  val quantumSealHash: String = "3f8b91a27e0c4516d7a988b0f1e2d3c4b5a68798e1f2a3b4c5d6e7f8a9b0c1d2",
  val generatedQuantumSealText: String? = null,
  val simulatedAttackInProgress: Boolean = false,
  val simulatedAttackLog: String? = null,
  val autoPurgeSpyPixels: Boolean = true,
  val autoDetonateSandbox: Boolean = true,
  val enforceAntiSpoofingDmarc: Boolean = true,
  val zeroKnowledgeVaultEnabled: Boolean = true,
  val emailSwarmDrones: List<EmailSwarmDrone> = emptyList(),
  val threatIncidents: List<EmailThreatIncident> = emptyList(),
  val auditChecklist: List<EmailAuditItem> = emptyList(),
  val secondaryEmails: List<String> = emptyList(),
  val ghostAliases: List<GhostEmailAlias> = listOf(
    GhostEmailAlias(
      id = "GHOST-01",
      aliasAddress = "ghost-7841@armas-quantum.sec",
      createdAt = "Hoy 10:30 UTC",
      expiresAt = "Expira en 22h",
      autoDestructHours = 24,
      emailsForwarded = 2
    ),
    GhostEmailAlias(
      id = "GHOST-02",
      aliasAddress = "ghost-9923@armas-quantum.sec",
      createdAt = "Ayer 18:15 UTC",
      expiresAt = "Expira en 4h",
      autoDestructHours = 24,
      emailsForwarded = 5
    )
  ),
  val isAnalyzingSandboxAttachment: Boolean = false,
  val lastSandboxAnalysisResult: String? = null
)

data class GhostEmailAlias(
  val id: String,
  val aliasAddress: String,
  val targetForwardEmail: String = "alejandroarmasbest12093@gmail.com",
  val createdAt: String,
  val expiresAt: String,
  val autoDestructHours: Int = 24,
  val emailsForwarded: Int = 0,
  val isDestroyed: Boolean = false
)
