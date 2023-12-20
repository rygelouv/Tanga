package app.books.tanga.feature.summary

import app.books.tanga.common.ui.ProgressState
import app.books.tanga.entity.SummaryId
import app.books.tanga.errors.toUiError
import app.books.tanga.feature.library.FavoriteInteractor
import app.books.tanga.feature.summary.details.SummaryDetailsUiEvent
import app.books.tanga.feature.summary.details.SummaryDetailsViewModel
import app.books.tanga.fixtures.Fixtures
import app.books.tanga.rule.MainCoroutineDispatcherExtension
import app.books.tanga.session.SessionManager
import app.cash.turbine.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineDispatcherExtension::class)
@ExperimentalCoroutinesApi
class SummaryDetailsViewModelTest {

    @MockK
    lateinit var summaryInteractor: SummaryInteractor

    @MockK
    lateinit var favoriteInteractor: FavoriteInteractor

    @MockK
    lateinit var sessionManager: SessionManager

    private lateinit var viewModel: SummaryDetailsViewModel

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = SummaryDetailsViewModel(summaryInteractor, favoriteInteractor, sessionManager)
    }

    @Test
    fun `loadSummary - success scenario`() = runTest {
        val summaryId = SummaryId("1")
        val summary = Fixtures.dummySummary1
        coEvery { summaryInteractor.getSummary(summaryId) } returns Result.success(summary)
        coEvery { favoriteInteractor.isFavorite(summaryId) } returns Result.success(false)
        coEvery { summaryInteractor.getRecommendationsForSummary(any()) } returns Result.success(emptyList())

        viewModel.loadSummary(summaryId)

        viewModel.state.test {
            val state = awaitItem()
            Assertions.assertEquals(ProgressState.Hide, state.progressState)
            Assertions.assertEquals(summary.toSummaryUi(), state.summary)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `loadSummary - failure scenario`() = runTest {
        val summaryId = SummaryId("1")
        val exception = Exception("Error")
        coEvery { summaryInteractor.getSummary(summaryId) } returns Result.failure(exception)

        viewModel.loadSummary(summaryId)

        viewModel.state.test {
            val state = awaitItem()
            Assertions.assertEquals(ProgressState.Hide, state.progressState)
            Assertions.assertEquals(exception.toUiError(), state.error)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `toggleFavorite - add favorite`() = runTest {
        // Prepare the environment
        val summaryId = SummaryId("1")
        val summary = Fixtures.dummySummary1

        coEvery { summaryInteractor.getSummary(summaryId) } returns Result.success(summary)
        coEvery { favoriteInteractor.createFavorite(summary) } returns Result.success(Unit)
        coEvery { favoriteInteractor.isFavorite(summaryId) } returns Result.success(false)
        coEvery { summaryInteractor.getRecommendationsForSummary(any()) } returns Result.success(emptyList())
        coEvery { sessionManager.hasSession() } returns true

        viewModel.loadSummary(summaryId) // Load the summary

        // Act - Toggle favorite (add)
        viewModel.toggleFavorite()

        // Assert - Verify state updates and interactions
        viewModel.state.test {
            val updatedState = awaitItem()
            // Hide progress after adding
            Assertions.assertEquals(ProgressState.Hide, updatedState.favoriteProgressState)
            Assertions.assertTrue(updatedState.isFavorite) // Verify favorite status

            cancelAndConsumeRemainingEvents()
        }

        coVerify { favoriteInteractor.createFavorite(summary) }
    }

    @Test
    fun `toggleFavorite - remove favorite`() = runTest {
        // Prepare the environment
        val summaryId = SummaryId("1")
        val summary = Fixtures.dummySummary1

        coEvery { summaryInteractor.getSummary(summaryId) } returns Result.success(summary)
        coEvery { favoriteInteractor.deleteFavoriteBySummaryId(summaryId) } returns Result.success(Unit)
        coEvery { favoriteInteractor.isFavorite(summaryId) } returns Result.success(true) // Set as favorite
        coEvery { summaryInteractor.getRecommendationsForSummary(any()) } returns Result.success(emptyList())
        coEvery { sessionManager.hasSession() } returns true

        viewModel.loadSummary(summaryId) // Load the summary

        // Act - Toggle favorite (remove)
        viewModel.toggleFavorite()

        viewModel.state.test {
            val updatedState = awaitItem()
            // Hide progress after removing
            Assertions.assertEquals(ProgressState.Hide, updatedState.favoriteProgressState)
            Assertions.assertFalse(updatedState.isFavorite) // Verify not favorite

            cancelAndConsumeRemainingEvents()
        }

        coVerify { favoriteInteractor.deleteFavoriteBySummaryId(summaryId) }
    }

    @Test
    fun `onPlayClick - with session`() = runTest {
        val summaryId = SummaryId("1")
        val summary = Fixtures.dummySummary1

        coEvery { summaryInteractor.getSummary(summaryId) } returns Result.success(summary)
        coEvery { favoriteInteractor.deleteFavoriteBySummaryId(summaryId) } returns Result.success(Unit)
        coEvery { favoriteInteractor.isFavorite(summaryId) } returns Result.success(true) // Set as favorite
        coEvery { summaryInteractor.getRecommendationsForSummary(any()) } returns Result.success(emptyList())

        coEvery { sessionManager.hasSession() } returns true

        viewModel.loadSummary(summaryId)

        viewModel.onPlayClick()

        viewModel.events.test {
            val event = expectMostRecentItem()
            Assertions.assertTrue(event is SummaryDetailsUiEvent.NavigateTo.ToAudioPlayer)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `toggleFavorite -  when sessionManager has no session`() = runTest {
        coEvery { sessionManager.hasSession() } returns false

        viewModel.toggleFavorite()

        viewModel.events.test {
            val event = expectMostRecentItem()
            Assertions.assertTrue(event is SummaryDetailsUiEvent.ShowAuthSuggestion)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `onPlayClick - when sessionManager has no session`() = runTest {
        coEvery { sessionManager.hasSession() } returns false

        viewModel.onPlayClick()

        viewModel.events.test {
            val event = expectMostRecentItem()
            Assertions.assertTrue(event is SummaryDetailsUiEvent.ShowAuthSuggestion)
            cancelAndConsumeRemainingEvents()
        }
    }
}
