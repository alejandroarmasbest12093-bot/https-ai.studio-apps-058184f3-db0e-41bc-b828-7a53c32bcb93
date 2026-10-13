package com.example

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.example.model.AegisNexusProtocol
import com.example.viewmodel.CyberSecurityViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class AegisVoiceRecognitionTest {

  private lateinit var viewModel: CyberSecurityViewModel

  @Before
  fun setUp() {
    val application = ApplicationProvider.getApplicationContext<Application>()
    viewModel = CyberSecurityViewModel(application)
  }

  @Test
  fun testAegisNexusProtocolsInitialization() {
    val state = viewModel.uiState.value.aegisVoiceState
    assertEquals(AegisNexusProtocol.entries.size, state.activeProtocols.size)
    assertTrue(state.activeProtocols[AegisNexusProtocol.ZERO_TRUST_MESH] == true)
    assertTrue(state.activeProtocols[AegisNexusProtocol.SYNAPSE_Q_ENTANGLEMENT] == true)
  }

  @Test
  fun testVoiceCommandDeployShield() {
    viewModel.processVoiceCommand("activar blindaje maximo y kyber 1024")
    val state = viewModel.uiState.value.aegisVoiceState
    val lastResult = state.lastCommandResult
    assertNotNull(lastResult)
    assertEquals("DEPLOY_MASTER_SHIELD", lastResult?.detectedIntent)
    assertEquals(AegisNexusProtocol.QUANTUM_KYBER_TUNNEL, lastResult?.affectedProtocol)
    assertTrue(lastResult?.isSuccess == true)
  }

  @Test
  fun testVoiceCommandToggleProtocol() {
    val initialStatus = viewModel.uiState.value.aegisVoiceState.activeProtocols[AegisNexusProtocol.AI_SWARM_DEFENSE] ?: true
    viewModel.toggleNexusProtocol(AegisNexusProtocol.AI_SWARM_DEFENSE)
    val toggledStatus = viewModel.uiState.value.aegisVoiceState.activeProtocols[AegisNexusProtocol.AI_SWARM_DEFENSE]
    assertEquals(!initialStatus, toggledStatus)
  }

  @Test
  fun testVoiceCommandSynapsePulse() {
    viewModel.processVoiceCommand("transmitir pulso cuántico de resonancia synapse")
    val state = viewModel.uiState.value.aegisVoiceState
    val lastResult = state.lastCommandResult
    assertNotNull(lastResult)
    assertEquals("TRIGGER_SYNAPSE_Q", lastResult?.detectedIntent)
    assertEquals(AegisNexusProtocol.SYNAPSE_Q_ENTANGLEMENT, lastResult?.affectedProtocol)
  }
}
