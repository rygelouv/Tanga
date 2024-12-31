package app.books.tanga.feature.search

import app.books.tanga.common.ui.ProgressState
import app.books.tanga.feature.categories.CategoryUi
import app.books.tanga.feature.categories.toCategoryUi
import app.books.tanga.feature.summary.SummaryInteractor
import app.books.tanga.fixtures.Fixtures
import app.books.tanga.rule.MainCoroutineDispatcherExtension
import app.books.tanga.tracking.AnalyticsTracker
import app.books.tanga.tracking.Events
import app.books.tanga.tracking.Pages
import app.books.tanga.tracking.Properties
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineDispatcherExtension::class)
@ExperimentalCoroutinesApi
class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    private lateinit var summaryInteractor: SummaryInteractor
    private lateinit var analyticsTracker: AnalyticsTracker

    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        summaryInteractor = mockk()
        analyticsTracker = mockk(relaxed = true)

        val categories = listOf(Fixtures.dummyCategory1, Fixtures.dummyCategory2)
        val summaries = listOf(Fixtures.dummySummary1, Fixtures.dummySummary2)
        coEvery { summaryInteractor.getCategories() } returns Result.success(categories)
        coEvery { summaryInteractor.getAllSummaries() } returns Result.success(summaries)

        viewModel = SearchViewModel(summaryInteractor, analyticsTracker)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load categories and summaries`() = runTest {
        // Given
        val categories = listOf(Fixtures.dummyCategory1, Fixtures.dummyCategory2)
        val summaries = listOf(Fixtures.dummySummary1, Fixtures.dummySummary2)
        coEvery { summaryInteractor.getCategories() } returns Result.success(categories)
        coEvery { summaryInteractor.getAllSummaries() } returns Result.success(summaries)

        // When
        viewModel.state.test {
            // Then
            val finalState = awaitItem()
            assert(finalState.progressState == ProgressState.Hide)
            assert(finalState.categories?.size == 2)
            assert(finalState.summaries?.size == 2)
        }

        verify { analyticsTracker.trackPage(Pages.SEARCH) }
    }

    @Test
    fun `onSearch should update state and trigger search`() = runTest {
        // Given
        val query = "test query"
        val searchResults = listOf(Fixtures.dummySummary1)
        coEvery { summaryInteractor.search(query) } returns Result.success(searchResults)

        // When
        viewModel.onSearch(query)

        // Then
        viewModel.state.test {
            val loadingState = awaitItem()
            assert(loadingState.progressState == ProgressState.Show)
            assert(loadingState.query == query)
            assert(!loadingState.shouldShowCategories)

            val finalState = awaitItem()
            assert(finalState.progressState == ProgressState.Hide)
            assert(finalState.summaries?.size == 1)
            assert(finalState.summaries?.first()?.title == Fixtures.dummySummary1.title)
        }

        verify { analyticsTracker.track(Events.ACTION_SEARCH, mapOf(Properties.SEARCH_QUERY to query)) }
    }

    @Test
    fun `onSearch with empty query should reload all the summaries`() = runTest {
        // Given
        val query = ""

        // When
        viewModel.onSearch(query)

        // Then
        viewModel.state.test {
            val loadingState = awaitItem()
            assert(loadingState.progressState == ProgressState.Show)
            assert(loadingState.query == query)
            assert(loadingState.shouldShowCategories)

            val finalState = awaitItem()
            assert(finalState.progressState == ProgressState.Hide)
            assert(finalState.categories?.size == 2)
            assert(finalState.summaries?.size == 2)
        }

        coVerify(exactly = 0) { summaryInteractor.search(any()) }
        coVerify(exactly = 2) { summaryInteractor.getAllSummaries() }
    }

    @Test
    fun `onCategorySelected should update selected categories and load summaries`() = runTest {
        // Given
        val category = CategoryUi(Fixtures.dummyCategory1.id.value, Fixtures.dummyCategory1.name, 0)
        val summaries = Fixtures.summariesForCategory1
        coEvery {
            summaryInteractor.getSummariesForCategories(listOf(Fixtures.dummyCategory1.id.value))
        } returns Result.success(summaries)

        // When
        viewModel.onCategorySelected(category)

        // Then
        viewModel.state.test {
            val loadingState = awaitItem()
            assert(loadingState.progressState == ProgressState.Show)
            assert(loadingState.selectedCategories.contains(category))

            val finalState = awaitItem()
            assert(finalState.progressState == ProgressState.Hide)
            assert(finalState.summaries?.size == summaries.size)
            assert(finalState.summaries?.first()?.title == summaries.first().title)
        }

        verify {
            analyticsTracker.track(
                Events.TAP_CATEGORY_ITEM_IN_SEARCH,
                mapOf(Properties.CATEGORY_ID to Fixtures.dummyCategory1.id.value)
            )
        }
    }

    @Test
    fun `onCategoryUnselected should update selected categories and load summaries`() = runTest {
        // Given
        val category = Fixtures.dummyCategory1.toCategoryUi()

        val fullSummaries = listOf(Fixtures.dummySummary1, Fixtures.dummySummary2, Fixtures.dummySummary3)
        val summaries = listOf(Fixtures.dummySummary2, Fixtures.dummySummary3)
        coEvery { summaryInteractor.getSummariesForCategories(listOf(category.id)) } returns Result.success(summaries)
        coEvery { summaryInteractor.getSummariesForCategories(emptyList()) } returns Result.success(fullSummaries)

        viewModel.onCategorySelected(category)

        // When
        viewModel.onCategoryUnselected(category)

        // Then
        viewModel.state.test {
            val loadingState = awaitItem()
            assert(loadingState.progressState == ProgressState.Show)
            assert(!loadingState.selectedCategories.contains(category))

            awaitItem()

            val finalState = awaitItem()
            assert(finalState.progressState == ProgressState.Hide)
            assert(finalState.summaries?.size == fullSummaries.size)
        }
    }

    @Test
    fun `onNavigateToSummary should emit NavigateTo event`() = runTest {
        // Given
        val summaryId = Fixtures.dummySummary1.id

        // When
        viewModel.onNavigateToSummary(summaryId)

        // Then
        viewModel.events.test {
            val event = awaitItem()
            assert(event is SearchUiEvent.NavigateTo.ToSummary)
            assert((event as SearchUiEvent.NavigateTo.ToSummary).summaryId == summaryId)
        }
    }

    @Test
    fun `onRetry should reset error and reload data`() = runTest {
        // Given
        val categories = listOf(Fixtures.dummyCategory1, Fixtures.dummyCategory2)
        val summaries = listOf(Fixtures.dummySummary1, Fixtures.dummySummary2)
        coEvery { summaryInteractor.getCategories() } returns Result.success(categories)
        coEvery { summaryInteractor.getAllSummaries() } returns Result.success(summaries)

        // When
        viewModel.onRetry()

        // Then
        viewModel.state.test {
            val initialState = awaitItem()
            assert(initialState.error == null)
            assert(initialState.progressState == ProgressState.Show)

            val finalState = awaitItem()
            assert(finalState.progressState == ProgressState.Hide)
            assert(finalState.categories?.size == 2)
            assert(finalState.summaries?.size == 2)
        }
    }
}
