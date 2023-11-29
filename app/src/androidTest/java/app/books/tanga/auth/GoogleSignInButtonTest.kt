package app.books.tanga.auth

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import app.books.tanga.R
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.feature.auth.AuthUiState
import app.books.tanga.feature.auth.GoogleSignInButton
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class GoogleSignInButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun googleSignInButton_displaysProgressIndicator() {
        composeTestRule.setContent {
            GoogleSignInButton(
                state = AuthUiState(googleSignInButtonProgressState = ProgressState.Show),
                onClick = {}
            )
        }
        composeTestRule.onNodeWithTag("ProgressIndicator").assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.auth_sign_in_with_google)).assertDoesNotExist()
    }

    @Test
    fun googleSignInButton_displaysSignInText() {
        composeTestRule.setContent {
            GoogleSignInButton(
                state = AuthUiState(googleSignInButtonProgressState = ProgressState.Hide),
                onClick = {}
            )
        }
        composeTestRule.onNodeWithTag("ProgressIndicator").assertDoesNotExist()
        composeTestRule.onNodeWithText(context.getString(R.string.auth_sign_in_with_google)).assertIsDisplayed()
    }

    @Test
    fun googleSignInButton_click_triggersOnClickCallback() {
        val onClickMock = mock<() -> Unit>()

        composeTestRule.setContent {
            GoogleSignInButton(
                state = AuthUiState(),
                onClick = onClickMock
            )
        }

        composeTestRule.onNodeWithText(context.getString(R.string.auth_sign_in_with_google)).performClick()

        verify(onClickMock).invoke()
    }

    @Test
    fun googleSignInButton_isDisabled() {
        composeTestRule.setContent {
            GoogleSignInButton(
                state = AuthUiState(googleSignInButtonProgressState = ProgressState.Show),
                onClick = {}
            )
        }

        composeTestRule.onNodeWithTag("GoogleSignInButton").assertIsNotEnabled()
    }

    @Test
    fun googleSignInButton_isEnabled() {
        composeTestRule.setContent {
            GoogleSignInButton(
                state = AuthUiState(googleSignInButtonProgressState = ProgressState.Hide),
                onClick = {}
            )
        }
        composeTestRule.onNodeWithTag("GoogleSignInButton").assertIsEnabled()
    }
}
