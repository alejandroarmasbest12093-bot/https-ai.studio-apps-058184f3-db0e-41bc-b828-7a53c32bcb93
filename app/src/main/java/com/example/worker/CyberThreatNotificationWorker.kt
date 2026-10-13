package com.example.worker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.MainActivity

/**
 * WorkManager Worker responsible for generating high-priority system tray notifications
 * in real-time when the AI identifies and intercepts a cyber threat, virus, or intrusion.
 */
class CyberThreatNotificationWorker(
  context: Context,
  workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

  companion object {
    const val TAG = "ThreatNotificationWorker"
    const val CHANNEL_ID = "cyber_threat_alerts_channel"
    const val CHANNEL_NAME = "Alertas de Ciberamenazas IA (Escudo Armas)"

    const val KEY_THREAT_ID = "key_threat_id"
    const val KEY_THREAT_NAME = "key_threat_name"
    const val KEY_CATEGORY = "key_category"
    const val KEY_SEVERITY = "key_severity"
    const val KEY_SOURCE_VECTOR = "key_source_vector"
    const val KEY_TIMESTAMP = "key_timestamp"
    const val KEY_INTERCEPTING_AGENT = "key_intercepting_agent"
    const val KEY_AI_MITIGATION = "key_ai_mitigation"
    const val KEY_SIGNATURE_HASH = "key_signature_hash"
  }

  override suspend fun doWork(): Result {
    val threatId = inputData.getString(KEY_THREAT_ID) ?: "THREAT-${System.currentTimeMillis()}"
    val threatName = inputData.getString(KEY_THREAT_NAME) ?: "Amenaza Cibernética Crítica"
    val category = inputData.getString(KEY_CATEGORY) ?: "Intrusión Heurística Neutralizada"
    val severity = inputData.getString(KEY_SEVERITY) ?: "CRITICAL"
    val sourceVector = inputData.getString(KEY_SOURCE_VECTOR) ?: "Vector de Red No Autorizado"
    val timestamp = inputData.getString(KEY_TIMESTAMP) ?: "En Tiempo Real"
    val interceptingAgent = inputData.getString(KEY_INTERCEPTING_AGENT) ?: "Centinela IA Autónomo 24/7"
    val aiMitigation = inputData.getString(KEY_AI_MITIGATION) ?: "Vector bloqueado en memoria y purgado irreversiblemente."
    val signatureHash = inputData.getString(KEY_SIGNATURE_HASH) ?: "UNKNOWN_HASH"

    Log.i(TAG, "Ejecutando Worker de Notificación para amenaza interceptada: $threatName ($threatId)")

    try {
      createNotificationChannel()
      showSystemTrayNotification(
        threatId = threatId,
        threatName = threatName,
        category = category,
        severity = severity,
        sourceVector = sourceVector,
        timestamp = timestamp,
        interceptingAgent = interceptingAgent,
        aiMitigation = aiMitigation,
        signatureHash = signatureHash
      )
      return Result.success()
    } catch (e: Exception) {
      Log.e(TAG, "Error emitiendo notificación de amenaza en system tray: ${e.message}", e)
      return Result.failure()
    }
  }

  private fun createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        ?: return

      val existingChannel = notificationManager.getNotificationChannel(CHANNEL_ID)
      if (existingChannel == null) {
        val channel = NotificationChannel(
          CHANNEL_ID,
          CHANNEL_NAME,
          NotificationManager.IMPORTANCE_HIGH
        ).apply {
          description = "Notificaciones prioritarias en tiempo real cuando el Enjambre IA y Centinela bloquean virus o amenazas cibernéticas."
          enableLights(true)
          lightColor = Color.RED
          enableVibration(true)
          vibrationPattern = longArrayOf(0, 180, 100, 260)
          setShowBadge(true)
          lockscreenVisibility = android.app.Notification.VISIBILITY_PUBLIC
        }
        notificationManager.createNotificationChannel(channel)
      }
    }
  }

  private fun showSystemTrayNotification(
    threatId: String,
    threatName: String,
    category: String,
    severity: String,
    sourceVector: String,
    timestamp: String,
    interceptingAgent: String,
    aiMitigation: String,
    signatureHash: String
  ) {
    // Check permission for Android 13+ (Tiramisu)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      val hasPermission = ContextCompat.checkSelfPermission(
        applicationContext,
        Manifest.permission.POST_NOTIFICATIONS
      ) == PackageManager.PERMISSION_GRANTED
      if (!hasPermission) {
        Log.w(TAG, "Permiso POST_NOTIFICATIONS no concedido aún. Omitiendo visualización en bandeja del sistema.")
        return
      }
    }

    val intent = Intent(applicationContext, MainActivity::class.java).apply {
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
      putExtra("EXTRA_NAV_SCREEN", "SENTINEL")
      putExtra("EXTRA_THREAT_ID", threatId)
    }

    val pendingIntent = PendingIntent.getActivity(
      applicationContext,
      threatId.hashCode(),
      intent,
      PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val severityEmoji = when (severity.uppercase()) {
      "CRITICAL" -> "🚨"
      "HIGH" -> "⚠️"
      else -> "🛡️"
    }

    val notificationTitle = "$severityEmoji [AMENAZA BLOQUEADA] $threatName"
    val notificationSubText = "Centinela IA • $interceptingAgent"

    val bigTextContent = buildString {
      append("🛡️ Agente Interceptor: $interceptingAgent\n")
      append("💥 Vector de Incursión: $sourceVector\n")
      append("⚠️ Nivel de Riesgo: $severity • $category\n")
      append("🧠 Acción IA: $aiMitigation\n")
      append("⏱️ Timestamp: $timestamp\n")
      if (signatureHash.isNotEmpty() && signatureHash != "UNKNOWN_HASH") {
        append("🔑 Firma Hash: ${signatureHash.take(18)}...")
      }
    }

    val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
      .setSmallIcon(android.R.drawable.stat_notify_error)
      .setContentTitle(notificationTitle)
      .setContentText("$category • $sourceVector")
      .setSubText(notificationSubText)
      .setStyle(
        NotificationCompat.BigTextStyle()
          .setBigContentTitle(notificationTitle)
          .setSummaryText(category)
          .bigText(bigTextContent)
      )
      .setPriority(NotificationCompat.PRIORITY_HIGH)
      .setCategory(NotificationCompat.CATEGORY_ALARM)
      .setColor(0xFFE53935.toInt()) // High contrast crimson red
      .setAutoCancel(true)
      .setContentIntent(pendingIntent)
      .setDefaults(NotificationCompat.DEFAULT_ALL)

    val notificationManager = NotificationManagerCompat.from(applicationContext)
    val notificationId = (threatId.hashCode() and 0x7FFFFFFF)

    notificationManager.notify(notificationId, notificationBuilder.build())
    Log.i(TAG, "Notificación en bandeja del sistema emitida con éxito. ID: $notificationId, Amenaza: $threatName")
  }
}
