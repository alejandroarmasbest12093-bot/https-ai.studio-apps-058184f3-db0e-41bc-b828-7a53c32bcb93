package com.example

import com.example.model.AiSwarmAgent
import com.example.model.PrivacyShieldMetrics
import com.example.model.VirusCategoryBlockStatus
import com.example.viewmodel.CyberSecurityViewModel
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun aiSwarm_initialState_hasActiveDronesAndMaxPrivacy() {
    val viewModel = CyberSecurityViewModel()
    val state = viewModel.uiState.value

    assertEquals(6, state.swarmAgents.size)
    assertEquals(100, state.privacyMetrics.privacyIndex)
    assertEquals(100.0, state.privacyMetrics.telemetryCloaked, 0.001)
    assertTrue(state.privacyMetrics.zeroDataLeakGuaranteed)

    // All virus categories have 100% block rate
    assertTrue(state.virusCategories.isNotEmpty())
    state.virusCategories.forEach { category ->
      assertEquals(100.0, category.blockRatePercentage, 0.001)
      assertTrue(category.isTotalBlockActive)
    }
  }

  @Test
  fun boostPrivacyShield_maintainsAbsoluteZeroLeak() {
    val viewModel = CyberSecurityViewModel()
    viewModel.boostPrivacyShield()
    val state = viewModel.uiState.value

    assertEquals(100, state.privacyMetrics.privacyIndex)
    assertTrue(state.privacyMetrics.zeroDataLeakGuaranteed)
    assertEquals("ARMAS-PQC-CLOAK-100%::ACTIVE", state.privacyMetrics.metadataFingerprintCloaking)
  }

  @Test
  fun realtimeTrafficDashboard_initializesAndHandlesFilterAndBurst() {
    val viewModel = CyberSecurityViewModel()
    val initialState = viewModel.uiState.value

    // Verify initial traffic state
    assertTrue("Traffic history should not be empty", initialState.trafficHistory.isNotEmpty())
    assertTrue("Blocked packets feed should not be empty", initialState.blockedPacketsFeed.isNotEmpty())
    assertTrue("Total blocked packets should be greater than 0", initialState.trafficSummary.totalPacketsBlockedToday > 0)
    assertTrue(initialState.trafficSummary.isRealTimeFeedLive)

    // Test pause/resume
    viewModel.toggleTrafficLiveFeed()
    assertFalse(viewModel.uiState.value.trafficSummary.isRealTimeFeedLive)
    viewModel.toggleTrafficLiveFeed()
    assertTrue(viewModel.uiState.value.trafficSummary.isRealTimeFeedLive)

    // Test traffic filter
    viewModel.setTrafficFilter(com.example.model.FlowDirection.INCOMING)
    assertEquals(com.example.model.FlowDirection.INCOMING, viewModel.uiState.value.trafficSummary.selectedFilter)

    // Test burst injection
    val previousTotal = viewModel.uiState.value.trafficSummary.totalPacketsBlockedToday
    viewModel.injectMaliciousTrafficBurst()
    val afterBurstState = viewModel.uiState.value
    assertTrue(afterBurstState.trafficSummary.totalPacketsBlockedToday > previousTotal)
  }

  @Test
  fun connectedNetwork_and_appScanner_initializeAndDismantleViruses() {
    val viewModel = CyberSecurityViewModel()
    val initialState = viewModel.uiState.value

    // Connected Network checks
    assertTrue("Connected network should have devices", initialState.connectedNetwork.connectedDevices.isNotEmpty())
    assertTrue("Local IP address should be set", initialState.connectedNetwork.localIpAddress.isNotBlank())
    assertTrue("Gateway IP should be set", initialState.connectedNetwork.gatewayIp.isNotBlank())

    // App Scanner checks
    assertTrue("Scanned apps should not be empty", initialState.scannedApps.isNotEmpty())
    val suspiciousInitial = initialState.scannedApps.count { it.riskLevel == "CRÍTICO_VIRUS" }
    assertTrue("Should detect virus samples initially", suspiciousInitial > 0)

    // Test single app disinfection
    val targetApp = initialState.scannedApps.first { it.riskLevel == "CRÍTICO_VIRUS" }
    viewModel.disinfectSingleApp(targetApp.packageName)
    val disinfectedApp = viewModel.uiState.value.scannedApps.first { it.packageName == targetApp.packageName }
    assertTrue("Disinfected app should be dismantled", disinfectedApp.isDismantled)
    assertEquals("SEGURO", disinfectedApp.riskLevel)

    // Test device isolation
    val firstDevice = initialState.connectedNetwork.connectedDevices.first()
    viewModel.isolateNetworkDevice(firstDevice.id)
    val isolatedDevice = viewModel.uiState.value.connectedNetwork.connectedDevices.first { it.id == firstDevice.id }
    assertTrue("Device should be quarantined/isolated", isolatedDevice.isQuarantined)

    // Test quantum tunnel toggle
    val tunnelBefore = viewModel.uiState.value.connectedNetwork.isQuantumTunnelActive
    viewModel.toggleQuantumTunnel()
    assertEquals(!tunnelBefore, viewModel.uiState.value.connectedNetwork.isQuantumTunnelActive)
  }
}
