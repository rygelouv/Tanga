package app.books.tanga.auth

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import app.books.tanga.R
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.feature.auth.AuthScreen
import app.books.tanga.feature.auth.AuthUiEvent
import app.books.tanga.feature.auth.AuthUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class AuthScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockOnAuthSkip: () -> Unit = mock()
    private val mockOnGoogleSignInButtonClick: () -> Unit = mock()
    private val mockOnAuthSuccess: () -> Unit = mock()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun skipAuthenticationButton_click_triggersCallback() {
        composeTestRule.setContent {
            AuthScreen(
                state = AuthUiState(),
                events = AuthUiEvent.Empty,
                onAuthSkip = mockOnAuthSkip,
                onAuthSuccess = {},
                onGoogleSignInButtonClick = {},
                onGoogleSignInComplete = {}
            )
        }

        composeTestRule.onNodeWithText(context.getString(R.string.auth_skip)).performClick()

        verify(mockOnAuthSkip).invoke()
    }

    @Test
    fun progressIndicatorVisible_whenSkipInProgress() {
        val state = AuthUiState(skipProgressState = ProgressState.Show)

        launchAuthScreen(state = state)

        composeTestRule.onNodeWithTag("ProgressIndicator").assertExists()
    }

    @Test
    fun googleSignInButton_click_triggersCallback() {
        launchAuthScreen()

        composeTestRule.onNodeWithText(context.getString(R.string.auth_sign_in_with_google)).performClick()

        verify(mockOnGoogleSignInButtonClick).invoke()
    }

    @Test
    fun navigateToHomeEvent_triggersNavigation() {
        launchAuthScreen(events = AuthUiEvent.NavigateTo.ToHomeScreen)

        verify(mockOnAuthSuccess).invoke()
    }

    private fun launchAuthScreen(state: AuthUiState = AuthUiState(), events: AuthUiEvent = AuthUiEvent.Empty) {
        composeTestRule.setContent {
            AuthScreen(
                state = state,
                events = events,
                onAuthSkip = {},
                onAuthSuccess = mockOnAuthSuccess,
                onGoogleSignInButtonClick = mockOnGoogleSignInButtonClick,
                onGoogleSignInComplete = {}
            )
        }
    }
}
