package app.books.tanga.feature.summary

import app.books.tanga.common.ui.ProgressState
import app.books.tanga.entity.CategoryId
import app.books.tanga.errors.toUiError
import app.books.tanga.feature.search.CategoryUi
import app.books.tanga.feature.search.getCategoryIllustration
import app.books.tanga.feature.summary.list.SummariesByCategoryUiState
import app.books.tanga.feature.summary.list.SummaryByCategoryViewModel
import app.books.tanga.fixtures.Fixtures
import app.books.tanga.rule.MainCoroutineDispatcherExtension
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineDispatcherExtension::class)
@ExperimentalCoroutinesApi
class SummaryByCategoryViewModelTest {

    private lateinit var summaryInteractor: SummaryInteractor
    private lateinit var summaryByCategoryViewModel: SummaryByCategoryViewModel

    @BeforeEach
    fun setup() {
        summaryInteractor = mockk()
        summaryByCategoryViewModel = SummaryByCategoryViewModel(summaryInteractor)
    }

    @Test
    @DisplayName("SummaryByCategoryViewModel loads summaries successfully")
    fun summaryByCategoryViewModelLoadsSummariesSuccessfully() = runTest {
        val categoryId = CategoryId("1")
        val categoryName = "Category 1"
        val expectedState = SummariesByCategoryUiState(
            progressState = ProgressState.Hide,
            summaries = listOf(Fixtures.dummySummary1).map { it.toSummaryUi() },
            categoryUi = CategoryUi(
                id = categoryId.value,
                name = categoryName,
                icon = getCategoryIllustration(categoryId)
            )
        )
        coEvery {
            summaryInteractor.getSummariesByCategory(any())
        } returns Result.success(listOf(Fixtures.dummySummary1))

        summaryByCategoryViewModel.loadSummaries(categoryId, categoryName)

        summaryByCategoryViewModel.state.test {
            assertEquals(expectedState, awaitItem())
        }
    }

    @Test
    @DisplayName("SummaryByCategoryViewModel handles error when loading summaries")
    fun summaryByCategoryViewModelHandlesErrorWhenLoadingSummaries() = runTest {
        val error = Exception("Some exception")
        val categoryId = CategoryId("1")
        val categoryName = "Category 1"
        val expectedState = SummariesByCategoryUiState(
            progressState = ProgressState.Hide,
            error = error.toUiError(),
            categoryUi = CategoryUi(
                id = categoryId.value,
                name = categoryName,
                icon = getCategoryIllustration(categoryId)
            )
        )
        coEvery { summaryInteractor.getSummariesByCategory(any()) } returns Result.failure(error)

        summaryByCategoryViewModel.loadSummaries(categoryId, categoryName)

        summaryByCategoryViewModel.state.test {
            assertEquals(expectedState, awaitItem())
        }
    }
}
