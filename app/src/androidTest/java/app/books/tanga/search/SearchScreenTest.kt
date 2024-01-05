package app.books.tanga.search

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import app.books.tanga.R
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.errors.OperationError
import app.books.tanga.errors.toUiError
import app.books.tanga.feature.search.CategoryUi
import app.books.tanga.feature.search.SearchScreen
import app.books.tanga.feature.search.SearchUiState
import app.books.tanga.feature.summary.toSummaryUi
import app.books.tanga.fixtures.Fixtures
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun searchScreen_Displays_search_box() {
        composeTestRule.setContent {
            SearchScreen(
                state = SearchUiState(progressState = ProgressState.Show),
                snackBarHostState = SnackbarHostState(),
                onSearch = {},
                onNavigateToPreviousScreen = {},
                onCategorySelect = {},
                onCategoryUnselect = {},
                onRetry = {},
                onNavigateToSummary = {}
            )
        }

        composeTestRule.onNodeWithText(context.getString(R.string.explore_search)).assertExists()
        composeTestRule.onNodeWithText(context.getString(R.string.explore_search))
            .performTextInput("Good to Great")
    }

    @Test
    fun searchScreen_DisplaysCategoryHeaderWhenShouldShowCategoriesIsTrue() {
        val state = SearchUiState(
            progressState = ProgressState.Hide,
            shouldShowCategories = true,
            categories = listOf(CategoryUi("1", "Category 1", R.drawable.ic_contact_email)),
            summaries = listOf(
                Fixtures.dummySummary1.toSummaryUi(),
                Fixtures.dummySummary2.toSummaryUi()
            )
        )
        composeTestRule.setContent {
            SearchScreen(
                state = state,
                snackBarHostState = SnackbarHostState(),
                onSearch = {},
                onNavigateToPreviousScreen = {},
                onCategorySelect = {},
                onCategoryUnselect = {},
                onRetry = {},
                onNavigateToSummary = {}
            )
        }

        composeTestRule.onNodeWithText("Category 1").assertExists()
    }

    @Test
    fun searchScreen_DoesNotDisplayCategoryHeaderWhenShouldShowCategoriesIsFalse() {
        val state = SearchUiState(
            progressState = ProgressState.Hide,
            shouldShowCategories = false,
            categories = listOf(CategoryUi("1", "Category 1", R.drawable.ic_contact_email)),
            summaries = listOf(
                Fixtures.dummySummary1.toSummaryUi(),
                Fixtures.dummySummary2.toSummaryUi()
            )
        )
        composeTestRule.setContent {
            SearchScreen(
                state = state,
                snackBarHostState = SnackbarHostState(),
                onSearch = {},
                onNavigateToPreviousScreen = {},
                onCategorySelect = {},
                onCategoryUnselect = {},
                onRetry = {},
                onNavigateToSummary = {}
            )
        }

        composeTestRule.onNodeWithText("Category 1").assertDoesNotExist()
    }

    @Test
    fun searchScreen_DisplaysSummaries() {
        val state = SearchUiState(
            progressState = ProgressState.Hide,
            summaries = listOf(
                Fixtures.dummySummary1.toSummaryUi(),
                Fixtures.dummySummary2.toSummaryUi()
            )
        )
        composeTestRule.setContent {
            SearchScreen(
                state = state,
                snackBarHostState = SnackbarHostState(),
                onSearch = {},
                onNavigateToPreviousScreen = {},
                onCategorySelect = {},
                onCategoryUnselect = {},
                onRetry = {},
                onNavigateToSummary = {}
            )
        }

        composeTestRule.onNodeWithText("Content1").assertExists()
        composeTestRule.onNodeWithText("Content2").assertExists()
    }

    @Test
    fun searchScreen_DisplaysErrorWhenError_isNotNull() {
        val someException = Exception("Unknown error")
        val state = SearchUiState(
            progressState = ProgressState.Hide,
            error = OperationError.UnknownError(someException).toUiError()
        )
        composeTestRule.setContent {
            SearchScreen(
                state = state,
                snackBarHostState = SnackbarHostState(),
                onSearch = {},
                onNavigateToPreviousScreen = {},
                onCategorySelect = {},
                onCategoryUnselect = {},
                onRetry = {},
                onNavigateToSummary = {}
            )
        }

        composeTestRule.onNodeWithText(context.getString(R.string.error_message_default)).assertExists()
    }
}
