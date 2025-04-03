package com.example.mandatoryassignment

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasNoClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mandatoryassignment.screens.LogIn

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.mandatoryassignment", appContext.packageName)
    }


    @Test
    fun loginTest() {

        composeTestRule.setContent {
            LogIn()
        }

        composeTestRule.onNode(hasText("Log In")and hasNoClickAction())
            .assertIsDisplayed()

        composeTestRule.onNode(hasText("Log In") and hasClickAction())
            .assertIsNotEnabled()

        composeTestRule.onNodeWithText("Username").performTextInput("test")

        composeTestRule.onNode(hasText("Log In") and hasClickAction())
            .assertIsNotEnabled()

        composeTestRule.onNodeWithText("Password").performTextInput("test")

        composeTestRule.onNode(hasText("Log In") and hasClickAction())
            .assertIsEnabled()

//        composeTestRule.onNode(hasText("Log In") and hasClickAction())
//            .performClick()
//
//        composeTestRule.waitUntil(timeoutMillis = 10000) {
//            composeTestRule.onAllNodesWithText("The email address is badly formatted.").fetchSemanticsNodes().isNotEmpty()
//        }
//
//        composeTestRule.onNodeWithText("The email address is badly formatted.")
//            .assertIsDisplayed()



    }

}