package app.books.tanga.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToIndex
import app.books.tanga.R
import app.books.tanga.coreui.components.InfoCard
import app.books.tanga.coreui.components.TangaButton
import app.books.tanga.coreui.theme.TangaTheme
import app.books.tanga.feature.summary.list.SummaryGrid
import app.books.tanga.fixtures.FakeUiData
import kotlinx.collections.immutable.toImmutableList
import org.junit.Rule
import org.junit.Test

class SummaryGridTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun summaryGrid_displaysHeaderAndFooter_whenEmptyList() {
        val allSummaries = FakeUiData.allSummaries()
        composeTestRule.setContent {
            TangaTheme {
                SummaryGrid(
                    summaries = allSummaries.toImmutableList(),
                    header = { InfoCard(image = R.drawable.graphic_pricing) },
                    footer = { TangaButton(text = "Button", onClick = { }) },
                    onSummaryClick = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("info_card").assertExists()
        composeTestRule.onNodeWithText(allSummaries.first().title).assertExists()
        composeTestRule.onNodeWithTag("SummaryGrid").performScrollToIndex(allSummaries.lastIndex)
        composeTestRule.onNodeWithText("Button").assertExists()
    }

    @Test
    fun summaryGrid_displaysAllSummaries() {
        val allSummaries = FakeUiData.allSummaries()

        composeTestRule.setContent {
            TangaTheme {
                SummaryGrid(
                    summaries = allSummaries.toImmutableList(),
                    onSummaryClick = {}
                )
            }
        }

        allSummaries.forEachIndexed { index, summary ->
            composeTestRule.onNodeWithTag("SummaryGrid").performScrollToIndex(index)
            composeTestRule.onNodeWithText(summary.title).assertExists()
        }
    }
}
