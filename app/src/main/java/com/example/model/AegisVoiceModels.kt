package com.example.model

enum class AegisNexusProtocol(
  val id: String,
  val protocolName: String,
  val code: String,
  val standard: String,
  val description: String,
  val defaultActive: Boolean = true
) {
  ZERO_TRUST_MESH(
    id = "PROT-01",
    protocolName = "Zero-Trust Mesh & MNP-1024",
    code = "ZTM-1024",
    standard = "NIST FIPS 203 / 204",
    description = "Enrutamiento cuántico sin confianza con verificación continua de identidad nodo a nodo."
  ),
  QUANTUM_KYBER_TUNNEL(
    id = "PROT-02",
    protocolName = "Túnel PQC Kyber-1024 WireGuard",
    code = "KYBER-VPN",
    standard = "RFC 8937 / Post-Quantum E2EE",
    description = "Cifrado post-cuántico para paquetes en tránsito resistente a ataques Shor y Grover."
  ),
  SYNAPSE_Q_ENTANGLEMENT(
    id = "PROT-03",
    protocolName = "Resonancia Cuántica Synapse-Q™",
    code = "SYN-Q-EPR",
    standard = "Protocolo Bell Telepático Cuántico",
    description = "Intercambio instantáneo de pulsos armónicos mediante pares de Bell entrelazados."
  ),
  ORBITAL_SATELLITE_LEO(
    id = "PROT-04",
    protocolName = "Enlace Orbital LEO Direct-to-Cell",
    code = "LEO-SBD",
    standard = "3GPP Rel-17 NTN / Beamforming",
    description = "Protocolo de baliza satelital para voz, SMS e Internet sin torres terrestres."
  ),
  AI_SWARM_DEFENSE(
    id = "PROT-05",
    protocolName = "Defensa Activa Enjambre IA Centinela",
    code = "SWARM-AI",
    standard = "Protocolo de Consenso Autónomo Armas",
    description = "Patrullaje autónomo 24/7 y vaporización inmediata de vectores Zero-Day y ransomware."
  ),
  QUANTUM_VAULT_SEAL(
    id = "PROT-06",
    protocolName = "Sellado de Bóveda Cuántica Soberana",
    code = "VAULT-SEAL",
    standard = "FIPS 140-3 Level 4 HSM",
    description = "Aislamiento criptográfico y almacenamiento inmutable de secretos y credenciales."
  )
}

data class AegisVoiceCommandResult(
  val commandText: String,
  val timestamp: String,
  val detectedIntent: String,
  val actionTaken: String,
  val isSuccess: Boolean,
  val affectedProtocol: AegisNexusProtocol? = null
)

data class AegisVoiceState(
  val isListening: Boolean = false,
  val recognizedSpeechText: String = "",
  val soundLevelRms: Float = 0f,
  val voiceStatusMessage: String = "Sistema de voz Aegis Nexus en espera. Toca el micrófono o di un comando.",
  val lastCommandResult: AegisVoiceCommandResult? = null,
  val commandHistory: List<AegisVoiceCommandResult> = emptyList(),
  val isTtsFeedbackEnabled: Boolean = true,
  val activeProtocols: Map<AegisNexusProtocol, Boolean> = AegisNexusProtocol.entries.associateWith { true },
  val isContinuousListeningMode: Boolean = false
)
