package app.books.tanga.summary

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import app.books.tanga.R
import app.books.tanga.entity.SummaryId
import app.books.tanga.feature.summary.details.HandleEvents
import app.books.tanga.feature.summary.details.SummaryDetailsUiEvent
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class SummaryDetailsScreenHandleEventsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testNavigateToAuthEvent() {
        val onNavigateToAuthMock = mockk<() -> Unit>(relaxed = true)

        composeTestRule.setContent {
            HandleEvents(
                event = SummaryDetailsUiEvent.NavigateTo.ToAuth,
                onNavigateToAuth = onNavigateToAuthMock,
                onNavigateToPreviousScreen = {},
                onNavigateToAudioPlayer = {},
                onNavigateToRecommendedSummaryDetails = {}
            )
        }

        // Await the effect
        composeTestRule.waitForIdle()

        // Verify that the navigation to auth callback is called
        verify { onNavigateToAuthMock.invoke() }
    }

    @Test
    fun testNavigateToPreviousScreenEvent() {
        val onNavigateToPreviousScreenMock = mockk<() -> Unit>(relaxed = true)

        composeTestRule.setContent {
            HandleEvents(
                event = SummaryDetailsUiEvent.NavigateTo.ToPrevious,
                onNavigateToAuth = {},
                onNavigateToPreviousScreen = onNavigateToPreviousScreenMock,
                onNavigateToAudioPlayer = {},
                onNavigateToRecommendedSummaryDetails = {}
            )
        }

        // Await the effect
        composeTestRule.waitForIdle()

        // Verify that the navigation to previous screen callback is called
        verify { onNavigateToPreviousScreenMock.invoke() }
    }

    @Test
    fun testNavigateToAudioPlayerEvent() {
        val summaryId = SummaryId("1")
        val onNavigateToAudioPlayerMock = mockk<(SummaryId) -> Unit>(relaxed = true)

        composeTestRule.setContent {
            HandleEvents(
                event = SummaryDetailsUiEvent.NavigateTo.ToAudioPlayer(summaryId),
                onNavigateToAuth = {},
                onNavigateToPreviousScreen = {},
                onNavigateToAudioPlayer = onNavigateToAudioPlayerMock,
                onNavigateToRecommendedSummaryDetails = {}
            )
        }

        // Await the effect
        composeTestRule.waitForIdle()

        // Verify that the navigation to audio player callback is called with the correct summaryId
        verify { onNavigateToAudioPlayerMock.invoke(summaryId) }
    }

    @Test
    fun testNavigateToRecommendedSummaryDetailsEvent() {
        val summaryId = SummaryId("1")
        val onNavigateToRecommendedSummaryDetailsMock = mockk<(SummaryId) -> Unit>(relaxed = true)

        composeTestRule.setContent {
            HandleEvents(
                event = SummaryDetailsUiEvent.NavigateTo.ToSummaryDetails(summaryId),
                onNavigateToAuth = {},
                onNavigateToPreviousScreen = {},
                onNavigateToAudioPlayer = {},
                onNavigateToRecommendedSummaryDetails = onNavigateToRecommendedSummaryDetailsMock
            )
        }

        // Await the effect
        composeTestRule.waitForIdle()

        // Verify that the navigation to recommended summary details callback is called with the correct summaryId
        verify { onNavigateToRecommendedSummaryDetailsMock.invoke(summaryId) }
    }

    @Test
    fun testShowAuthSuggestionEvent() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        composeTestRule.setContent {
            HandleEvents(
                event = SummaryDetailsUiEvent.ShowAuthSuggestion(),
                onNavigateToAuth = {},
                onNavigateToPreviousScreen = {},
                onNavigateToAudioPlayer = {},
                onNavigateToRecommendedSummaryDetails = {}
            )
        }

        // Await the effect
        composeTestRule.waitForIdle()

        // Verify that the AuthSuggestionBottomSheet is shown
        composeTestRule.onNodeWithTag("auth_suggestion_bottom_sheet").assertExists()
        composeTestRule.onNodeWithText(context.getString(R.string.auth_suggestion_title)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.auth_suggestion_description)).assertIsDisplayed()
    }
}
