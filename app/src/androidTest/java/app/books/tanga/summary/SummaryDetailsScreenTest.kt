package app.books.tanga.summary

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.entity.SummaryId
import app.books.tanga.feature.summary.SummaryUi
import app.books.tanga.feature.summary.details.SummaryDetailsScreen
import app.books.tanga.feature.summary.details.SummaryDetailsUiState
import org.junit.Rule
import org.junit.Test

class SummaryDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verifyTopBarHasCorrectButtons() {
        val state = SummaryDetailsUiState(
            summary = SummaryUi(
                id = SummaryId("1"),
                title = "Title",
                author = "Author",
                coverUrl = "https://cover.url",
                duration = "1h 30m"
            ),
            isFavorite = false,
            progressState = ProgressState.Hide,
            favoriteProgressState = ProgressState.Hide,
            recommendations = emptyList()
        )

        composeTestRule.setContent {
            SummaryDetailsScreen(
                state = state,
                onBackClick = {},
                onPlayClick = {},
                onLoadSummary = {},
                onToggleFavorite = {},
                onRecommendationClick = {}
            )
        }

        composeTestRule.onNodeWithTag("save_favorite").assertExists()
        composeTestRule.onNodeWithTag("back_button", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("share", useUnmergedTree = true).assertExists()

        composeTestRule.onNodeWithTag("share", useUnmergedTree = true).performClick()
    }
}
