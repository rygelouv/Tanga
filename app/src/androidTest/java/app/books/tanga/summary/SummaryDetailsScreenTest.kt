package app.books.tanga.summary

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import app.books.tanga.R
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.entity.SummaryId
import app.books.tanga.feature.summary.SummaryUi
import app.books.tanga.feature.summary.details.SummaryDetailsScreen
import app.books.tanga.feature.summary.details.SummaryDetailsScreenContainer
import app.books.tanga.feature.summary.details.SummaryDetailsUiState
import app.books.tanga.feature.summary.details.SummaryDetailsViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class SummaryDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val viewModel: SummaryDetailsViewModel = mockk(relaxed = true)

    private val state1 = SummaryDetailsUiState(
        summary = SummaryUi(
            id = SummaryId("1"),
            title = "Atomic Habits",
            author = "James Clear",
            coverUrl = "https://cover.url",
            duration = "00:10"
        ),
        isFavorite = false,
        progressState = ProgressState.Hide,
        favoriteProgressState = ProgressState.Hide,
        recommendations = emptyList()
    )

    @Test
    fun verifyTopBarHasCorrectButtons() {
        launchSummaryDetailsScreen()

        composeTestRule.onNodeWithTag("save_favorite").assertExists()
        composeTestRule.onNodeWithTag("back_button", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("share", useUnmergedTree = true).assertExists()
    }

    @Test
    fun verifyHeaderHasCorrectContent() {
        launchSummaryDetailsScreen()

        composeTestRule.onNodeWithTag("summary_cover_image").assertExists()
        composeTestRule.onNodeWithText(state1.summary!!.title).assertExists()
        composeTestRule.onAllNodesWithText(state1.summary!!.author).onFirst().assertExists()
        composeTestRule.onNodeWithText(context.getString(R.string.summary_duration, state1.summary!!.duration))
            .assertExists()
    }

    @Test
    fun verifySummaryActionsButtonsBehaveCorrectly() {
        val summaryWithTwoUrls = SummaryUi(
            id = SummaryId("1"),
            title = "Atomic Habits",
            author = "James Clear",
            coverUrl = "https://cover.url",
            duration = "00:10",
            audioUrl = "https://audio.url",
            textUrl = "https://text.url",
        )
        val state = state1.copy(summary = summaryWithTwoUrls)

        launchSummaryDetailsScreen(state)

        composeTestRule.onNodeWithTag("read_button").assertExists().assertHasClickAction()
        composeTestRule.onNodeWithTag("listen_button").assertExists().assertHasClickAction()
        composeTestRule.onNodeWithTag("watch_button").assertExists().assertHasNoClickAction()
        composeTestRule.onNodeWithTag("visualize_button").assertExists().assertHasNoClickAction()
    }

    @Test
    fun verifyIntroductionSectionHasCorrectContent() {
        val smallSynopsis = "This is a summary of the book Atomic Habits."
        val summaryWithSynopsis = state1.summary!!.copy(synopsis = smallSynopsis)
        val state = state1.copy(summary = summaryWithSynopsis)

        launchSummaryDetailsScreen(state)

        composeTestRule.onNodeWithText("Introduction").assertExists()
        composeTestRule.onNodeWithText(summaryWithSynopsis.synopsis!!).assertExists()
        composeTestRule.onNodeWithText("Show more").assertDoesNotExist()
    }

    @Test
    fun verifySynopsisShowMoreButtonBehavesCorrectly() {
        val bigSynopsis = "This is a summary of the book Atomic Habits. " +
            "Once upon a time, there was a book called Atomic Habits. " +
            "It was a very good book and everyone loved it. That's why you should buy this book for yourself " +
            "and for your loved ones. This book is so good that they cannot ignore you ;) " +
            "In the realm of book, this book is the best book ever written. You will never found a book such as" +
            "this book. By the way, Tanga is the only app where you can find an excellent summary of this book. " +
            "So, what are you waiting for? Download Tanga now and enjoy this summary."
        val summaryWithSynopsis = state1.summary!!.copy(synopsis = bigSynopsis)
        val state = state1.copy(summary = summaryWithSynopsis)

        launchSummaryDetailsScreen(state)

        composeTestRule.onNodeWithText("Show more").assertExists().assertHasClickAction()
        composeTestRule.onNodeWithText("Show less").assertDoesNotExist()
        composeTestRule.onNodeWithText("Show more").performClick()
        composeTestRule.onNodeWithText("Show more").assertDoesNotExist()
        composeTestRule.onNodeWithText("Show less").assertExists()
        composeTestRule.onNodeWithText(bigSynopsis).assertExists()
    }

    @Test
    fun verifyAuthorSectionHasCorrectContent() {
        launchSummaryDetailsScreen()

        composeTestRule.onNodeWithTag("author_image").assertExists()
        composeTestRule.onAllNodesWithText(state1.summary!!.author)[1].assertExists()
    }

    @Test
    fun verifyPurchaseButtonBehavesCorrectly() {
        val summaryWithPurchaseUrl = state1.summary!!.copy(purchaseBookUrl = "https://purchase.url")
        val state = state1.copy(summary = summaryWithPurchaseUrl)

        launchSummaryDetailsScreen(state)

        composeTestRule.onNodeWithTag("purchase_button").assertExists().assertHasClickAction()
    }

    @Test
    fun verifyRecommendationsSectionHasCorrectContent() {
        val stateWithRecommendations = state1.copy(
            recommendations = listOf(
                SummaryUi(
                    id = SummaryId("2"),
                    title = "The Power of Habit",
                    author = "Charles Duhigg",
                    coverUrl = "https://cover.url",
                    duration = "00:10"
                ),
                SummaryUi(
                    id = SummaryId("3"),
                    title = "The 7 Habits of Highly Effective People",
                    author = "Stephen R. Covey",
                    coverUrl = "https://cover.url",
                    duration = "00:10"
                ),
                SummaryUi(
                    id = SummaryId("4"),
                    title = "Deep Work",
                    author = "Cal Newport",
                    coverUrl = "https://cover.url",
                    duration = "00:10"
                )
            )
        )

        launchSummaryDetailsScreen(stateWithRecommendations)

        composeTestRule.onNodeWithText("The Power of Habit").assertExists()
        composeTestRule.onNodeWithText("The 7 Habits of Highly Effective People").assertExists()
        composeTestRule.onNodeWithText("Deep Work").assertExists()
    }

    @Test
    fun verifyPlayFloatingActionButtonBehavesCorrectly() {
        launchSummaryDetailsScreen()

        composeTestRule.onNodeWithTag("play_button").assertExists().assertHasClickAction()
    }

    @Test
    fun verifySummaryDetailsScreenContainerRendersContentCorrectly() {
        val summaryId = SummaryId("1")

        every { viewModel.state } returns MutableStateFlow(state1).asStateFlow()
        every { viewModel.events } returns flowOf()
        every { viewModel.loadSummary(summaryId) } returns Unit
        every { viewModel.toggleFavorite() } returns Unit
        every { viewModel.onPlayClick() } returns Unit

        composeTestRule.setContent {
            SummaryDetailsScreenContainer(
                summaryId = summaryId,
                viewModel = viewModel,
                onNavigateToAuth = {},
                onNavigateToPreviousScreen = {},
                onNavigateToAudioPlayer = {},
                onNavigateToReadSummaryScreen = {},
                onNavigateToRecommendedSummaryDetails = {}
            )
        }

        composeTestRule.onNodeWithText(state1.summary!!.title).assertExists()
        composeTestRule.onAllNodesWithText(state1.summary!!.author).onFirst().assertExists()
        composeTestRule.onNodeWithText(context.getString(R.string.summary_duration, state1.summary!!.duration))
            .assertExists()

        composeTestRule.onNodeWithTag("play_button").assertExists().assertHasClickAction()
        composeTestRule.onNodeWithTag("play_button").performClick()

        verify(exactly = 1) { viewModel.onPlayClick() }

        composeTestRule.onNodeWithTag("save_favorite").assertExists().assertHasClickAction()
        composeTestRule.onNodeWithTag("save_favorite").performClick()

        verify(exactly = 1) { viewModel.toggleFavorite() }
    }

    private fun launchSummaryDetailsScreen(state: SummaryDetailsUiState = state1) {
        composeTestRule.setContent {
            SummaryDetailsScreen(
                state = state,
                onBackClick = {},
                onPlayClick = { viewModel.onPlayClick() },
                onLoadSummary = {},
                onToggleFavorite = {},
                onReadClick = {},
                onRecommendationClick = {}
            )
        }
    }
}
