package app.books.tanga.summary

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import app.books.tanga.R
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.entity.CategoryId
import app.books.tanga.feature.search.CategoryUi
import app.books.tanga.feature.summary.list.SummariesByCategoryScreenContainer
import app.books.tanga.feature.summary.list.SummariesByCategoryUiState
import app.books.tanga.feature.summary.list.SummariesByCategoryViewModel
import app.books.tanga.feature.summary.toSummaryUi
import app.books.tanga.fixtures.Fixtures
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class SummariesByCategoryScreenContainerTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val viewModel = mockk<SummariesByCategoryViewModel>(relaxed = true)

    @Test
    fun summariesByCategoryScreenContainerDisplaysCorrectContent() {
        val categoryName = "Category 1"

        every { viewModel.state.value } returns SummariesByCategoryUiState(
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
        )

        composeTestRule.setContent {
            SummariesByCategoryScreenContainer(
                categoryId = CategoryId("1"),
                categoryName = categoryName,
                onNavigateToPreviousScreen = {},
                onNavigateToSummary = {},
                onNavigateToSearch = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText(categoryName).assertExists()
        composeTestRule.onAllNodesWithText("Content1").assertCountEquals(1)
        composeTestRule.onAllNodesWithText("Content2").assertCountEquals(1)
    }
}
