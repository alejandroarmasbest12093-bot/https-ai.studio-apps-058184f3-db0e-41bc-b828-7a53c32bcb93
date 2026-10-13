package com.example

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [34])
class ExampleRobolectricTest {
  @get:Rule
  val composeTestRule = createAndroidComposeRule<MainActivity>()

  @Test
  fun testNavigateToAllScreens() {
    composeTestRule.waitForIdle()
    for (screen in CyberNavScreen.entries) {
      println("Testing screen: ${screen.name} with tag: ${screen.tag}")
      composeTestRule.onNodeWithTag(screen.tag).performClick()
      composeTestRule.waitForIdle()
    }
  }
}
