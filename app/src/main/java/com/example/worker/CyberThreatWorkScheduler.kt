package com.example.worker

import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.model.ThreatIncident
import com.example.model.ThreatSeverity
import com.example.model.ThreatStatus
import java.util.UUID

/**
 * Utility to schedule immediate background alerts through Android WorkManager
 * whenever the AI Sentinel or Swarm intercepts a cyber threat.
 */
object CyberThreatWorkScheduler {

  private const val TAG = "CyberThreatScheduler"

  private fun safeLogI(tag: String, msg: String) {
    try {
      Log.i(tag, msg)
    } catch (t: Throwable) {
      // Fallback in unit test environments without Android mocks
    }
  }

  private fun safeLogE(tag: String, msg: String, tr: Throwable? = null) {
    try {
      Log.e(tag, msg, tr)
    } catch (t: Throwable) {
      // Fallback in unit test environments without Android mocks
    }
  }

  fun enqueueThreatAlert(
    context: Context,
    incident: ThreatIncident,
    interceptingAgent: String = "Centinela IA Autónomo 24/7"
  ) {
    try {
      val inputData = workDataOf(
        CyberThreatNotificationWorker.KEY_THREAT_ID to incident.id,
        CyberThreatNotificationWorker.KEY_THREAT_NAME to incident.threatName,
        CyberThreatNotificationWorker.KEY_CATEGORY to incident.category,
        CyberThreatNotificationWorker.KEY_SEVERITY to incident.severity.name,
        CyberThreatNotificationWorker.KEY_SOURCE_VECTOR to incident.sourceVector,
        CyberThreatNotificationWorker.KEY_TIMESTAMP to incident.timestamp,
        CyberThreatNotificationWorker.KEY_INTERCEPTING_AGENT to interceptingAgent,
        CyberThreatNotificationWorker.KEY_AI_MITIGATION to incident.aiMitigationLog,
        CyberThreatNotificationWorker.KEY_SIGNATURE_HASH to incident.signatureHash
      )

      val workRequest = OneTimeWorkRequestBuilder<CyberThreatNotificationWorker>()
        .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
        .setInputData(inputData)
        .addTag("cyber_threat_alert")
        .addTag(incident.id)
        .build()

      WorkManager.getInstance(context).enqueue(workRequest)
      safeLogI(TAG, "Tarea WorkManager encolada para alerta inmediata: ${incident.threatName}")
    } catch (e: Exception) {
      safeLogE(TAG, "Fallo al encolar WorkManager: ${e.message}", e)
    }
  }

  fun enqueueQuickTestAlert(context: Context) {
    val sampleIncident = ThreatIncident(
      id = "ALERT-${(1000..9999).random()}",
      threatName = "Ransomware.LockBit.QuantumVanish",
      category = "Ataque Criptográfico de Extorsión",
      sourceVector = "Conexión C2 Ingress (Puerto 443 Heurístico)",
      severity = ThreatSeverity.CRITICAL,
      status = ThreatStatus.NEUTRALIZED,
      timestamp = "Inmediato (WorkManager)",
      aiMitigationLog = "Intento de intrusión interceptado y neutralizado en tiempo real por el Enjambre IA de Alejandro Armas.",
      signatureHash = UUID.randomUUID().toString().replace("-", "")
    )
    enqueueThreatAlert(
      context = context,
      incident = sampleIncident,
      interceptingAgent = "Centinela IA Autónomo 24/7"
    )
  }
}
