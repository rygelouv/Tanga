package app.books.tanga.feature.home

import app.books.tanga.common.ui.ProgressState
import app.books.tanga.errors.toUiError
import app.books.tanga.feature.library.FavoriteInteractor
import app.books.tanga.feature.summary.toSummaryUi
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
class HomeViewModelTest {

    private lateinit var homeInteractor: HomeInteractor
    private lateinit var favoriteInteractor: FavoriteInteractor
    private lateinit var homeViewModel: HomeViewModel

    @BeforeEach
    fun setup() {
        homeInteractor = mockk(relaxed = true)
        favoriteInteractor = mockk()
    }

    @Test
    @DisplayName("HomeViewModel loads home data successfully")
    fun homeViewModelLoadsHomeDataSuccessfully() = runTest {
        val expectedState = HomeUiState(
            progressState = ProgressState.Hide,
            weeklySummary = Fixtures.dummySummary1.toSummaryUi(),
            sections = listOf(Fixtures.dummySection1).map { it.toHomeSectionUi() },
            userFirstName = Fixtures.dummyUser.firsName,
            userPhotoUrl = Fixtures.dummyUser.photoUrl
        )
        coEvery { homeInteractor.getWeeklySummary() } returns Result.success(Fixtures.dummySummary1)
        coEvery { homeInteractor.getSummarySections() } returns Result.success(listOf(Fixtures.dummySection1))
        coEvery { homeInteractor.getUserInfo() } returns Result.success(Fixtures.dummyUser)
        coEvery { favoriteInteractor.getFavorites() } returns Result.success(emptyList())

        homeViewModel = HomeViewModel(homeInteractor, favoriteInteractor)

        homeViewModel.state.test {
            assertEquals(expectedState, expectMostRecentItem())
        }
    }

    @Test
    @DisplayName("HomeViewModel handles error when loading home data")
    fun homeViewModelHandlesErrorWhenLoadingHomeData() = runTest {
        val someException = Exception("some exception")
        val expectedState = HomeUiState(
            progressState = ProgressState.Hide,
            error = someException.toUiError()
        )
        coEvery { homeInteractor.getWeeklySummary() } returns Result.failure(someException)
        coEvery { homeInteractor.getSummarySections() } returns Result.failure(someException)
        coEvery { homeInteractor.getUserInfo() } returns Result.failure(someException)
        coEvery { favoriteInteractor.getFavorites() } returns Result.success(emptyList())

        homeViewModel = HomeViewModel(homeInteractor, favoriteInteractor)

        homeViewModel.state.test {
            val state = expectMostRecentItem()
            assertEquals(expectedState.error, state.error)
            assertEquals(expectedState.progressState, state.progressState)
            assertEquals(expectedState.weeklySummary, state.weeklySummary)
            assertEquals(expectedState.sections, state.sections)
        }
    }

    @Test
    @DisplayName("HomeViewModel retries loading home data successfully")
    fun homeViewModelRetriesLoadingHomeDataSuccessfully() = runTest {
        val expectedState = HomeUiState(
            progressState = ProgressState.Hide,
            weeklySummary = Fixtures.dummySummary1.toSummaryUi(),
            sections = listOf(Fixtures.dummySection1).map { it.toHomeSectionUi() },
            userFirstName = Fixtures.dummyUser.firsName,
            userPhotoUrl = Fixtures.dummyUser.photoUrl
        )
        coEvery { homeInteractor.getWeeklySummary() } returns Result.success(Fixtures.dummySummary1)
        coEvery { homeInteractor.getSummarySections() } returns Result.success(listOf(Fixtures.dummySection1))
        coEvery { homeInteractor.getUserInfo() } returns Result.success(Fixtures.dummyUser)
        coEvery { favoriteInteractor.getFavorites() } returns Result.success(emptyList())

        homeViewModel = HomeViewModel(homeInteractor, favoriteInteractor)

        homeViewModel.onRetry()

        homeViewModel.state.test {
            assertEquals(expectedState, awaitItem())
        }
    }
}
