package com.example.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioRecord
import android.media.AudioTrack
import android.media.MediaRecorder
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.PI
import kotlin.math.log10
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Motor de Sonido y Micrófono para el Chip Satelital Soberano Armas.
 * - Generador acústico de tonos DTMF y señalización satelital (AudioTrack en tiempo real).
 * - Tono de timbrado satelital orbital (Ringback Tone) y chimes de enlace seguro PQC.
 * - Captura en vivo del micrófono del usuario (AudioRecord) con medición de decibelios (dBFS)
 *   y visualizador espectral de 8 barras en tiempo real.
 */
class ArmasSatelliteAudioEngine(private val context: Context?) {

  companion object {
    private const val TAG = "ArmasAudioEngine"
    private const val SAMPLE_RATE = 16000
    private const val CHANNEL_CONFIG_OUT = AudioFormat.CHANNEL_OUT_MONO
    private const val CHANNEL_CONFIG_IN = AudioFormat.CHANNEL_IN_MONO
    private const val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT

    // Frecuencias estándar DTMF (ITU-T Q.23)
    private val DTMF_FREQUENCIES = mapOf(
      '1' to Pair(697, 1209),
      '2' to Pair(697, 1336),
      '3' to Pair(697, 1477),
      '4' to Pair(770, 1209),
      '5' to Pair(770, 1336),
      '6' to Pair(770, 1477),
      '7' to Pair(852, 1209),
      '8' to Pair(852, 1336),
      '9' to Pair(852, 1477),
      '*' to Pair(941, 1209),
      '0' to Pair(941, 1336),
      '#' to Pair(941, 1477),
      '+' to Pair(941, 1477)
    )
  }

  data class AudioSpectrumData(
    val isMicActive: Boolean = false,
    val micVolumeDb: Float = -60f,
    val amplitudeNorm: Float = 0f,
    val waveformBars: List<Float> = listOf(0.15f, 0.25f, 0.4f, 0.3f, 0.5f, 0.35f, 0.2f, 0.15f)
  )

  private val _audioSpectrum = MutableStateFlow(AudioSpectrumData())
  val audioSpectrum: StateFlow<AudioSpectrumData> = _audioSpectrum.asStateFlow()

  private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
  private var ringbackJob: Job? = null
  private var micCaptureJob: Job? = null
  private var audioRecord: AudioRecord? = null
  private val isRecording = AtomicBoolean(false)
  private var isCallMuted = false
  private var isSpeakerEnabled = false

  /**
   * Reproduce el tono DTMF estándar al pulsar cualquier tecla del teclado satelital.
   */
  fun playDtmfTone(digit: Char) {
    scope.launch {
      try {
        val freqs = DTMF_FREQUENCIES[digit] ?: Pair(800, 1200)
        val durationMs = 110
        val numSamples = (SAMPLE_RATE * durationMs) / 1000
        val buffer = ShortArray(numSamples)

        for (i in 0 until numSamples) {
          val t = i.toDouble() / SAMPLE_RATE
          // Envolvente de ataque y decaimiento suave para evitar chasquidos
          val envelope = when {
            i < 100 -> (i / 100.0)
            i > numSamples - 100 -> ((numSamples - i) / 100.0)
            else -> 1.0
          }
          val sample1 = sin(2.0 * PI * freqs.first * t)
          val sample2 = sin(2.0 * PI * freqs.second * t)
          val combined = ((sample1 + sample2) * 0.5 * envelope * Short.MAX_VALUE * 0.65).toInt()
          buffer[i] = combined.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
        }

        playPcmBuffer(buffer, SAMPLE_RATE)
      } catch (_: CancellationException) {
        // Cancelación normal
      } catch (e: Exception) {
        Log.w(TAG, "No se pudo reproducir tono DTMF: ${e.message}")
      }
    }
  }

  /**
   * Inicia el bucle de timbrado satelital (Ringback tone: "tuuut... tuuut...")
   */
  fun startRingbackTone() {
    stopRingbackTone()
    ringbackJob = scope.launch {
      try {
        val toneDurationMs = 1200
        val silenceDurationMs = 1800
        val numSamples = (SAMPLE_RATE * toneDurationMs) / 1000
        val buffer = ShortArray(numSamples)

        // Frecuencias estándar de timbrado 440 Hz + 480 Hz
        for (i in 0 until numSamples) {
          val t = i.toDouble() / SAMPLE_RATE
          val envelope = when {
            i < 200 -> (i / 200.0)
            i > numSamples - 200 -> ((numSamples - i) / 200.0)
            else -> 1.0
          }
          val sample1 = sin(2.0 * PI * 440.0 * t)
          val sample2 = sin(2.0 * PI * 480.0 * t)
          val combined = ((sample1 + sample2) * 0.5 * envelope * Short.MAX_VALUE * 0.55).toInt()
          buffer[i] = combined.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
        }

        while (isActive) {
          playPcmBuffer(buffer, SAMPLE_RATE)
          delay(silenceDurationMs.toLong())
        }
      } catch (_: CancellationException) {
        // Cancelación normal
      } catch (e: Exception) {
        Log.w(TAG, "Interrupción en tono de timbrado: ${e.message}")
      }
    }
  }

  /**
   * Detiene el tono de timbrado satelital.
   */
  fun stopRingbackTone() {
    ringbackJob?.cancel()
    ringbackJob = null
  }

  /**
   * Reproduce el chime de enlace seguro cuántico establecido (Kyber-1024 Locked).
   */
  fun playConnectedChime() {
    scope.launch {
      try {
        stopRingbackTone()

        // Doble arpegio de confirmación: 880 Hz seguido de 1320 Hz
        val part1Duration = 100
        val part2Duration = 180
        val samples1 = (SAMPLE_RATE * part1Duration) / 1000
        val samples2 = (SAMPLE_RATE * part2Duration) / 1000
        val buffer = ShortArray(samples1 + samples2)

        for (i in 0 until samples1) {
          val t = i.toDouble() / SAMPLE_RATE
          buffer[i] = (sin(2.0 * PI * 880.0 * t) * Short.MAX_VALUE * 0.6).toInt().toShort()
        }

        for (i in 0 until samples2) {
          val t = i.toDouble() / SAMPLE_RATE
          val env = (samples2 - i).toDouble() / samples2
          buffer[samples1 + i] = (sin(2.0 * PI * 1320.0 * t) * env * Short.MAX_VALUE * 0.65).toInt().toShort()
        }

        playPcmBuffer(buffer, SAMPLE_RATE)
      } catch (_: CancellationException) {
        // Cancelación normal
      } catch (e: Exception) {
        Log.w(TAG, "Error reproduciendo chime de conexión: ${e.message}")
      }
    }
  }

  /**
   * Reproduce el tono de llamada finalizada (3 beeps de desconexión).
   */
  fun playCallEndedSound() {
    scope.launch {
      try {
        stopRingbackTone()
        stopMicCapture()

        val beepDuration = 120
        val silenceDuration = 100
        val samples = (SAMPLE_RATE * beepDuration) / 1000
        val buffer = ShortArray(samples)

        for (i in 0 until samples) {
          val t = i.toDouble() / SAMPLE_RATE
          val s1 = sin(2.0 * PI * 480.0 * t)
          val s2 = sin(2.0 * PI * 620.0 * t)
          buffer[i] = ((s1 + s2) * 0.5 * Short.MAX_VALUE * 0.5).toInt().toShort()
        }

        repeat(3) {
          playPcmBuffer(buffer, SAMPLE_RATE)
          delay(silenceDuration.toLong())
        }
      } catch (_: CancellationException) {
        // Cancelación normal
      } catch (e: Exception) {
        Log.w(TAG, "Error reproduciendo tono de desconexión: ${e.message}")
      }
    }
  }

  /**
   * Reproduce pulso acústico de emergencia satelital SOS.
   */
  fun playSosAlertBeep() {
    scope.launch {
      try {
        val durationMs = 280
        val samples = (SAMPLE_RATE * durationMs) / 1000
        val buffer = ShortArray(samples)

        for (i in 0 until samples) {
          val t = i.toDouble() / SAMPLE_RATE
          // Frecuencia variable ascendente 950 Hz -> 1400 Hz
          val freq = 950.0 + (i.toDouble() / samples) * 450.0
          buffer[i] = (sin(2.0 * PI * freq * t) * Short.MAX_VALUE * 0.7).toInt().toShort()
        }

        playPcmBuffer(buffer, SAMPLE_RATE)
      } catch (_: CancellationException) {
        // Cancelación normal
      } catch (e: Exception) {
        Log.w(TAG, "Error reproduciendo alerta SOS: ${e.message}")
      }
    }
  }

  /**
   * Reproduce tono de resonancia sinestésica armónica para Synapse-Q.
   * Modula onda senoidal fundamental + armónicos dorados con envolvente suave.
   */
  fun playSynapseResonanceTone(freqHz: Double, durationMs: Long = 750) {
    scope.launch {
      try {
        val safeFreq = freqHz.coerceIn(20.0, 3000.0)
        val numSamples = (SAMPLE_RATE * durationMs) / 1000
        val buffer = ShortArray(numSamples.toInt())

        for (i in 0 until numSamples.toInt()) {
          val t = i.toDouble() / SAMPLE_RATE
          val envelope = when {
            i < 400 -> (i / 400.0)
            i > numSamples - 500 -> ((numSamples - i) / 500.0)
            else -> 1.0
          }
          val fundamental = sin(2.0 * PI * safeFreq * t)
          val harmonic1 = 0.3 * sin(2.0 * PI * (safeFreq * 1.5) * t)
          val harmonic2 = 0.15 * sin(2.0 * PI * (safeFreq * 2.0) * t)
          val combined = ((fundamental + harmonic1 + harmonic2) * 0.7 * envelope * Short.MAX_VALUE * 0.65).toInt()
          buffer[i] = combined.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
        }

        playPcmBuffer(buffer, SAMPLE_RATE)
      } catch (_: CancellationException) {
        // Cancelación normal
      } catch (e: Exception) {
        Log.w(TAG, "Error en tono de resonancia Synapse: ${e.message}")
      }
    }
  }

  /**
   * Dispara patrón háptico bio-rítmico en el hardware del dispositivo.
   */
  fun triggerSynapseHaptic(pattern: LongArray) {
    try {
      val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
      if (vibrator != null && vibrator.hasVibrator()) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          val effect = VibrationEffect.createWaveform(pattern, -1)
          vibrator.vibrate(effect)
        } else {
          @Suppress("DEPRECATION")
          vibrator.vibrate(pattern, -1)
        }
      }
    } catch (e: Exception) {
      Log.w(TAG, "Error ejecutando vibración háptica: ${e.message}")
    }
  }

  /**
   * Comienza la captura del micrófono del usuario para alimentar el espectro de voz en vivo.
   * Si no hay permiso o hardware disponible, genera espectro adaptativo seguro sin crash.
   */
  fun startMicCapture(isMuted: Boolean = false) {
    this.isCallMuted = isMuted
    if (isRecording.getAndSet(true)) return

    micCaptureJob?.cancel()
    micCaptureJob = scope.launch {
      val hasPermission = context != null && ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.RECORD_AUDIO
      ) == PackageManager.PERMISSION_GRANTED

      if (!hasPermission) {
        // Modo adaptativo simulado si no se ha concedido el permiso aún
        runSimulatedMicLoop()
        return@launch
      }

      try {
        val minBufferSize = AudioRecord.getMinBufferSize(
          SAMPLE_RATE,
          CHANNEL_CONFIG_IN,
          AUDIO_FORMAT
        ).coerceAtLeast(1024)

        audioRecord = AudioRecord(
          MediaRecorder.AudioSource.VOICE_COMMUNICATION,
          SAMPLE_RATE,
          CHANNEL_CONFIG_IN,
          AUDIO_FORMAT,
          minBufferSize
        )

        if (audioRecord?.state != AudioRecord.STATE_INITIALIZED) {
          Log.w(TAG, "AudioRecord no inicializado, usando modo adaptativo")
          safeReleaseRecord()
          if (isActive && isRecording.get()) {
            runSimulatedMicLoop()
          }
          return@launch
        }

        audioRecord?.startRecording()

        val buffer = ShortArray(512)
        var tick = 0

        while (isActive && isRecording.get()) {
          val record = audioRecord ?: break
          val read = try {
            record.read(buffer, 0, buffer.size)
          } catch (_: Exception) {
            -1
          }

          if (read > 0) {
            tick++
            if (isCallMuted) {
              _audioSpectrum.value = AudioSpectrumData(
                isMicActive = false,
                micVolumeDb = -60f,
                amplitudeNorm = 0f,
                waveformBars = List(8) { 0.05f }
              )
            } else {
              // Calcular RMS (Root Mean Square)
              var sum = 0.0
              for (i in 0 until read) {
                sum += buffer[i] * buffer[i]
              }
              val rms = sqrt(sum / read)
              val normalizedAmp = (rms / 8000.0).coerceIn(0.0, 1.0).toFloat()
              val db = if (rms > 1.0) {
                (20.0 * log10(rms / Short.MAX_VALUE)).toFloat().coerceIn(-60f, 0f)
              } else -60f

              // Generar 8 barras de ecualizador espectral acordes al nivel de voz
              val bars = (0 until 8).map { idx ->
                val freqWeight = when (idx) {
                  2, 3, 4 -> 1.25f // Frecuencias medias de la voz humana (300 - 3000 Hz)
                  1, 5 -> 1.05f
                  else -> 0.75f
                }
                val jitter = (sin((tick * 0.4) + idx).toFloat() * 0.15f)
                (normalizedAmp * freqWeight + 0.12f + jitter).coerceIn(0.08f, 1.0f)
              }

              _audioSpectrum.value = AudioSpectrumData(
                isMicActive = true,
                micVolumeDb = db,
                amplitudeNorm = normalizedAmp,
                waveformBars = bars
              )
            }
          }
          delay(40)
        }
      } catch (_: CancellationException) {
        // Cancelación de corrutina normal al finalizar o cancelar la llamada
      } catch (e: Exception) {
        Log.e(TAG, "Excepción en AudioRecord: ${e.message}")
        if (isActive && isRecording.get()) {
          runSimulatedMicLoop()
        }
      } finally {
        safeReleaseRecord()
      }
    }
  }

  /**
   * Bucle seguro en caso de que no haya micrófono físico o permiso concedido.
   */
  private suspend fun runSimulatedMicLoop() {
    isRecording.set(true)
    var step = 0
    try {
      while (kotlin.coroutines.coroutineContext.isActive && isRecording.get()) {
        step++
        if (isCallMuted) {
          _audioSpectrum.value = AudioSpectrumData(
            isMicActive = false,
            micVolumeDb = -60f,
            amplitudeNorm = 0f,
            waveformBars = List(8) { 0.05f }
          )
        } else {
          val baseAmp = 0.35f + (sin(step * 0.2).toFloat() * 0.25f).coerceAtLeast(0f)
          val bars = List(8) { idx ->
            (baseAmp * (0.6f + (idx % 3) * 0.2f) + (sin(step * 0.35 + idx).toFloat() * 0.1f)).coerceIn(0.12f, 0.95f)
          }
          val db = -28f + (baseAmp * 15f)
          _audioSpectrum.value = AudioSpectrumData(
            isMicActive = true,
            micVolumeDb = db,
            amplitudeNorm = baseAmp,
            waveformBars = bars
          )
        }
        delay(80)
      }
    } catch (_: CancellationException) {
      // Cancelación normal
    }
  }

  fun setMuted(muted: Boolean) {
    this.isCallMuted = muted
    if (muted) {
      _audioSpectrum.value = AudioSpectrumData(
        isMicActive = false,
        micVolumeDb = -60f,
        amplitudeNorm = 0f,
        waveformBars = List(8) { 0.05f }
      )
    }
  }

  fun setSpeakerOn(enabled: Boolean) {
    this.isSpeakerEnabled = enabled
    try {
      val audioManager = context?.getSystemService(Context.AUDIO_SERVICE) as? AudioManager
      audioManager?.isSpeakerphoneOn = enabled
    } catch (e: Exception) {
      Log.w(TAG, "No se pudo cambiar modo altavoz: ${e.message}")
    }
  }

  fun stopMicCapture() {
    isRecording.set(false)
    val job = micCaptureJob
    micCaptureJob = null
    job?.cancel()
    safeReleaseRecord()
    _audioSpectrum.value = AudioSpectrumData(
      isMicActive = false,
      micVolumeDb = -60f,
      amplitudeNorm = 0f,
      waveformBars = List(8) { 0.05f }
    )
  }

  private fun safeReleaseRecord() {
    try {
      val record = audioRecord
      audioRecord = null
      if (record != null) {
        if (record.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
          record.stop()
        }
        record.release()
      }
    } catch (_: Exception) {
      // Ignorar errores en liberación
    }
  }

  private suspend fun playPcmBuffer(buffer: ShortArray, sampleRate: Int) {
    var track: AudioTrack? = null
    try {
      val bufferSize = buffer.size * 2
      val attributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
        .build()

      val format = AudioFormat.Builder()
        .setSampleRate(sampleRate)
        .setChannelMask(CHANNEL_CONFIG_OUT)
        .setEncoding(AUDIO_FORMAT)
        .build()

      track = AudioTrack.Builder()
        .setAudioAttributes(attributes)
        .setAudioFormat(format)
        .setBufferSizeInBytes(bufferSize)
        .setTransferMode(AudioTrack.MODE_STATIC)
        .build()

      track.write(buffer, 0, buffer.size)
      track.play()

      val durationMs = (buffer.size * 1000L) / sampleRate
      delay(durationMs + 20)
    } catch (_: CancellationException) {
      // Cancelación normal
    } catch (e: Exception) {
      Log.w(TAG, "Error en AudioTrack: ${e.message}")
    } finally {
      try {
        if (track?.playState == AudioTrack.PLAYSTATE_PLAYING) {
          track.stop()
        }
        track?.release()
      } catch (_: Exception) {
        // Ignorar
      }
    }
  }

  fun release() {
    stopRingbackTone()
    stopMicCapture()
  }
}
