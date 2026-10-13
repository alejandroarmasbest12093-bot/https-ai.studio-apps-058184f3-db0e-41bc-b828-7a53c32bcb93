package com.example.data.repository

import com.example.data.local.ThreatLogDao
import com.example.data.local.ThreatLogEntity
import com.example.model.ThreatIncident
import com.example.model.ThreatSeverity
import com.example.model.ThreatStatus
import kotlinx.coroutines.flow.Flow

class ThreatLogRepository(private val threatLogDao: ThreatLogDao) {

  val allThreatLogs: Flow<List<ThreatLogEntity>> = threatLogDao.getAllThreatLogs()

  val totalThreatCount: Flow<Int> = threatLogDao.getThreatCount()

  suspend fun insertThreat(threat: ThreatLogEntity): Long {
    return threatLogDao.insertThreatLog(threat)
  }

  suspend fun insertThreats(threats: List<ThreatLogEntity>) {
    threatLogDao.insertThreatLogs(threats)
  }

  suspend fun recordIncident(
    incident: ThreatIncident,
    interceptingAgent: String? = null,
    targetAsset: String? = null
  ): Long {
    val entity = ThreatLogEntity(
      threatUid = incident.id,
      threatName = incident.threatName,
      category = incident.category,
      sourceVector = incident.sourceVector,
      severity = incident.severity.name,
      status = incident.status.name,
      timestamp = incident.timestamp,
      timestampEpoch = System.currentTimeMillis(),
      aiMitigationLog = incident.aiMitigationLog,
      signatureHash = incident.signatureHash,
      interceptingAgent = interceptingAgent,
      targetAsset = targetAsset,
      actionTaken = if (incident.status == ThreatStatus.QUARANTINED) {
        "Aislado en Bóveda Criptográfica Cuántica"
      } else {
        "Neutralizado y Vaporizado por Centinela IA"
      }
    )
    return threatLogDao.insertThreatLog(entity)
  }

  suspend fun deleteThreatById(id: Long) {
    threatLogDao.deleteThreatLogById(id)
  }

  suspend fun clearAll() {
    threatLogDao.clearAllThreatLogs()
  }
}
