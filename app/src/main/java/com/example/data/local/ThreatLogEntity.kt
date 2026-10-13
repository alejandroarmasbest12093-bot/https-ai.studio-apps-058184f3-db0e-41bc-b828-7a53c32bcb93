package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "threat_logs")
data class ThreatLogEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val threatUid: String,
  val threatName: String,
  val category: String,
  val sourceVector: String,
  val severity: String, // CRITICAL, HIGH, MEDIUM, LOW
  val status: String, // NEUTRALIZED, QUARANTINED, ANALYZING
  val timestamp: String,
  val timestampEpoch: Long = System.currentTimeMillis(),
  val aiMitigationLog: String,
  val signatureHash: String,
  val interceptingAgent: String? = null,
  val targetAsset: String? = null,
  val actionTaken: String = "Neutralizado y Almacenado en Bóveda Criptográfica"
)
