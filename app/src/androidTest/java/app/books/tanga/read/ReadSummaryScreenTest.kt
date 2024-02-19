package app.books.tanga.read

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.feature.read.ReadSummaryScreen
import app.books.tanga.feature.read.ReadSummaryUiState
import app.books.tanga.feature.summary.SummaryContentState
import org.junit.Rule
import org.junit.Test

class ReadSummaryScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun readSummaryScreenRendersCorrectly() {
        val state = ReadSummaryUiState(
            summaryContentState = SummaryContentState(
                isFavorite = false,
                favoriteProgressState = ProgressState.Hide
            ),
            summaryTextContent = "Test content",
            progressState = ProgressState.Hide
        )

        composeTestRule.setContent {
            ReadSummaryScreen(
                state = state,
                onNavigateToPreviousScreen = {},
                onToggleFavorite = {},
                onNavigateToAudioPlayer = {},
                onFontSizeClick = {},
                onFontScaleChange = {}
            )
        }

        composeTestRule.onNodeWithTag("back_button", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("font_size", useUnmergedTree = true).assertExists()
    }

    @Test
    fun readSummaryScreenDisplaysMarkdownTextContent() {
        val markdownTextContent = "## Test Markdown Content"
        val state = ReadSummaryUiState(
            summaryContentState = SummaryContentState(
                isFavorite = false,
                favoriteProgressState = ProgressState.Hide
            ),
            summaryTextContent = markdownTextContent,
            progressState = ProgressState.Hide
        )

        composeTestRule.setContent {
            ReadSummaryScreen(
                state = state,
                onNavigateToPreviousScreen = {},
                onToggleFavorite = {},
                onNavigateToAudioPlayer = {},
                onFontSizeClick = {},
                onFontScaleChange = {}
            )
        }

        composeTestRule.onNodeWithText("Test Markdown Content").assertExists()
        composeTestRule.onNodeWithText(markdownTextContent).assertDoesNotExist()
    }

    @Test
    fun readSummaryScreenTriggersOnBackClick() {
        var backClicked = false
        val state = ReadSummaryUiState(
            summaryContentState = SummaryContentState(
                isFavorite = false,
                favoriteProgressState = ProgressState.Hide
            ),
            summaryTextContent = "Test content",
            progressState = ProgressState.Hide
        )

        composeTestRule.setContent {
            ReadSummaryScreen(
                state = state,
                onNavigateToPreviousScreen = { backClicked = true },
                onToggleFavorite = {},
                onNavigateToAudioPlayer = {},
                onFontSizeClick = {},
                onFontScaleChange = {}
            )
        }

        composeTestRule.onNodeWithTag("back_button", useUnmergedTree = true).performClick()

        assert(backClicked)
    }

    @Test
    fun readSummaryScreenTriggersOnFontSizeClick() {
        var fontSizeClicked = false
        val state = ReadSummaryUiState(
            summaryContentState = SummaryContentState(
                isFavorite = false,
                favoriteProgressState = ProgressState.Hide
            ),
            summaryTextContent = "Test content",
            progressState = ProgressState.Hide
        )

        composeTestRule.setContent {
            ReadSummaryScreen(
                state = state,
                onNavigateToPreviousScreen = {},
                onToggleFavorite = {},
                onNavigateToAudioPlayer = {},
                onFontSizeClick = { fontSizeClicked = true },
                onFontScaleChange = {}
            )
        }

        composeTestRule.onNodeWithTag("font_size", useUnmergedTree = true).performClick()

        assert(fontSizeClicked)
    }

    @Test
    fun readSummaryScreenTriggersOnToggleFavorite() {
        var favoriteToggled = false
        val state = ReadSummaryUiState(
            summaryContentState = SummaryContentState(
                isFavorite = false,
                favoriteProgressState = ProgressState.Hide
            ),
            summaryTextContent = "Test content",
            progressState = ProgressState.Hide
        )

        composeTestRule.setContent {
            ReadSummaryScreen(
                state = state,
                onNavigateToPreviousScreen = {},
                onToggleFavorite = { favoriteToggled = true },
                onNavigateToAudioPlayer = {},
                onFontSizeClick = {},
                onFontScaleChange = {}
            )
        }

        composeTestRule.onNodeWithTag("save_favorite").performClick()

        assert(favoriteToggled)
    }
}
