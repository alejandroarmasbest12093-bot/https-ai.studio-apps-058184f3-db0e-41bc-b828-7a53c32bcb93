package com.example.service

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class AegisVoiceRecognitionService(private val context: Context) : RecognitionListener, TextToSpeech.OnInitListener {

  private var speechRecognizer: SpeechRecognizer? = null
  private var textToSpeech: TextToSpeech? = null
  private var isTtsInitialized = false

  private val _isListening = MutableStateFlow(false)
  val isListening: StateFlow<Boolean> = _isListening.asStateFlow()

  private val _recognizedText = MutableStateFlow("")
  val recognizedText: StateFlow<String> = _recognizedText.asStateFlow()

  private val _rmsDb = MutableStateFlow(0f)
  val rmsDb: StateFlow<Float> = _rmsDb.asStateFlow()

  private val _voiceStatus = MutableStateFlow("Listo para escuchar comandos de voz.")
  val voiceStatus: StateFlow<String> = _voiceStatus.asStateFlow()

  private var onCommandRecognizedCallback: ((String) -> Unit)? = null

  init {
    initSpeechRecognizer()
    initTts()
  }

  private fun initSpeechRecognizer() {
    try {
      if (SpeechRecognizer.isRecognitionAvailable(context)) {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
          setRecognitionListener(this@AegisVoiceRecognitionService)
        }
        _voiceStatus.value = "Módulo de voz Aegis inicializado con éxito."
      } else {
        _voiceStatus.value = "Reconocimiento de voz nativo no disponible en este hardware. Modo simulación activo."
      }
    } catch (e: Exception) {
      Log.e("AegisVoice", "Error initializing speech recognizer: ${e.message}")
      _voiceStatus.value = "Aviso: Reconocedor de voz operando en modo secundario."
    }
  }

  private fun initTts() {
    try {
      textToSpeech = TextToSpeech(context, this)
    } catch (e: Exception) {
      Log.e("AegisVoice", "Error initializing TTS: ${e.message}")
    }
  }

  override fun onInit(status: Int) {
    if (status == TextToSpeech.SUCCESS) {
      try {
        val result = textToSpeech?.setLanguage(Locale("es", "ES"))
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
          textToSpeech?.language = Locale.US
        }
        isTtsInitialized = true
      } catch (e: Exception) {
        Log.e("AegisVoice", "TTS language configuration: ${e.message}")
      }
    }
  }

  fun setOnCommandListener(listener: (String) -> Unit) {
    onCommandRecognizedCallback = listener
  }

  fun startListening() {
    if (!SpeechRecognizer.isRecognitionAvailable(context) || speechRecognizer == null) {
      // Re-create if null
      try {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
          setRecognitionListener(this@AegisVoiceRecognitionService)
        }
      } catch (e: Exception) {
        _voiceStatus.value = "Servicio de voz no disponible directamente."
      }
    }

    val recognizer = speechRecognizer
    if (recognizer == null) {
      _voiceStatus.value = "Hardware de reconocimiento no disponible."
      return
    }

    try {
      val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES")
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "es-ES")
        putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, "es")
        putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
      }
      recognizer.startListening(intent)
      _isListening.value = true
      _recognizedText.value = ""
      _voiceStatus.value = "Escuchando comando de voz para Aegis Nexus..."
    } catch (e: Exception) {
      _isListening.value = false
      _voiceStatus.value = "Error al iniciar escucha: ${e.message}"
    }
  }

  fun stopListening() {
    try {
      speechRecognizer?.stopListening()
    } catch (e: Exception) {
      Log.e("AegisVoice", "Error stopping: ${e.message}")
    }
    _isListening.value = false
    _voiceStatus.value = "Escucha detenida."
  }

  fun speakResponse(text: String) {
    if (!isTtsInitialized || textToSpeech == null) return
    try {
      textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "aegis_nexus_tts_${System.currentTimeMillis()}")
    } catch (e: Exception) {
      Log.e("AegisVoice", "Error speaking text: ${e.message}")
    }
  }

  fun simulateVoiceCommand(command: String) {
    _isListening.value = false
    _recognizedText.value = command
    _voiceStatus.value = "Comando procesado: '$command'"
    onCommandRecognizedCallback?.invoke(command)
  }

  // RecognitionListener Callbacks
  override fun onReadyForSpeech(params: Bundle?) {
    _isListening.value = true
    _voiceStatus.value = "Micrófono abierto: di tu orden para Aegis Nexus."
  }

  override fun onBeginningOfSpeech() {
    _voiceStatus.value = "Captando audio..."
  }

  override fun onRmsChanged(rmsdB: Float) {
    _rmsDb.value = rmsdB
  }

  override fun onBufferReceived(buffer: ByteArray?) {}

  override fun onEndOfSpeech() {
    _isListening.value = false
    _voiceStatus.value = "Procesando orden de voz..."
  }

  override fun onError(error: Int) {
    _isListening.value = false
    val errorMsg = when (error) {
      SpeechRecognizer.ERROR_AUDIO -> "Error de grabación de audio."
      SpeechRecognizer.ERROR_CLIENT -> "Error del cliente de voz."
      SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Permiso de micrófono no concedido."
      SpeechRecognizer.ERROR_NETWORK -> "Error de red en reconocimiento."
      SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Tiempo de espera agotado."
      SpeechRecognizer.ERROR_NO_MATCH -> "No se reconoció ninguna orden con claridad."
      SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Reconocedor ocupado. Intenta de nuevo."
      SpeechRecognizer.ERROR_SERVER -> "Error del servidor de voz."
      SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "Sin entrada de voz detectada."
      else -> "Código de estado de voz: $error"
    }
    _voiceStatus.value = errorMsg
  }

  override fun onResults(results: Bundle?) {
    _isListening.value = false
    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
    val bestMatch = matches?.firstOrNull() ?: ""
    if (bestMatch.isNotBlank()) {
      _recognizedText.value = bestMatch
      _voiceStatus.value = "Comando recibido: \"$bestMatch\""
      onCommandRecognizedCallback?.invoke(bestMatch)
    } else {
      _voiceStatus.value = "Comando vacío o no captado."
    }
  }

  override fun onPartialResults(partialResults: Bundle?) {
    val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
    val partial = matches?.firstOrNull()
    if (!partial.isNullOrBlank()) {
      _recognizedText.value = partial
    }
  }

  override fun onEvent(eventType: Int, params: Bundle?) {}

  fun release() {
    try {
      speechRecognizer?.destroy()
      speechRecognizer = null
    } catch (e: Exception) {
      Log.e("AegisVoice", "Error destroying recognizer: ${e.message}")
    }
    try {
      textToSpeech?.stop()
      textToSpeech?.shutdown()
      textToSpeech = null
    } catch (e: Exception) {
      Log.e("AegisVoice", "Error shutting down TTS: ${e.message}")
    }
  }
}
