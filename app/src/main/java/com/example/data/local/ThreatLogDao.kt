package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ThreatLogDao {

  @Query("SELECT * FROM threat_logs ORDER BY timestampEpoch DESC")
  fun getAllThreatLogs(): Flow<List<ThreatLogEntity>>

  @Query("SELECT * FROM threat_logs WHERE severity = :severity ORDER BY timestampEpoch DESC")
  fun getThreatLogsBySeverity(severity: String): Flow<List<ThreatLogEntity>>

  @Query("SELECT * FROM threat_logs WHERE category = :category ORDER BY timestampEpoch DESC")
  fun getThreatLogsByCategory(category: String): Flow<List<ThreatLogEntity>>

  @Query("SELECT COUNT(*) FROM threat_logs")
  fun getThreatCount(): Flow<Int>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertThreatLog(threat: ThreatLogEntity): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertThreatLogs(threats: List<ThreatLogEntity>)

  @Query("DELETE FROM threat_logs WHERE id = :id")
  suspend fun deleteThreatLogById(id: Long)

  @Query("DELETE FROM threat_logs")
  suspend fun clearAllThreatLogs()
}
