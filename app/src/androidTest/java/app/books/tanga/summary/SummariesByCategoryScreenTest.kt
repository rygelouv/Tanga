package app.books.tanga.summary

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import app.books.tanga.R
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.feature.search.CategoryUi
import app.books.tanga.feature.summary.list.SummariesByCategoryScreen
import app.books.tanga.feature.summary.list.SummariesByCategoryUiState
import app.books.tanga.feature.summary.toSummaryUi
import app.books.tanga.fixtures.Fixtures
import org.junit.Rule
import org.junit.Test

class SummariesByCategoryScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun summariesByCategoryScreenDisplaysCategoryName() {
        val categoryName = "Business"
        composeTestRule.setContent {
            SummariesByCategoryScreen(
                state = SummariesByCategoryUiState(
                    categoryUi = CategoryUi(
                        id = "1",
                        name = categoryName,
                        icon = R.drawable.graphic_reading
                    ),
                    progressState = ProgressState.Hide,
                    summaries = buildList {
                        add(Fixtures.dummySummary1.toSummaryUi())
                        add(Fixtures.dummySummary2.toSummaryUi())
                    },
                    error = null
                ),
                onNavigateToPreviousScreen = {},
                onNavigateToSummary = {},
                onNavigateToSearch = {}
            )
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext

        composeTestRule.onNodeWithText(categoryName).assertExists()
        composeTestRule.onNodeWithTag("summaries_grid").assertExists()
        composeTestRule.onAllNodesWithText("Content1").assertCountEquals(1)
        composeTestRule.onNodeWithText(context.getString(R.string.library_explore_summaries))
            .assertExists()
            .assertHasClickAction()
    }
}
