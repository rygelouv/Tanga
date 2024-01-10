package app.books.tanga.feature.summary

import app.books.tanga.common.ui.ProgressState
import app.books.tanga.entity.SummaryId
import app.books.tanga.errors.toUiError
import app.books.tanga.feature.library.FavoriteInteractor
import app.books.tanga.fixtures.Fixtures
import app.books.tanga.rule.MainCoroutineDispatcherExtension
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineDispatcherExtension::class)
@ExperimentalCoroutinesApi
class SummaryBehaviorDelegateTest {

    private lateinit var summaryBehaviorDelegate: SummaryBehaviorDelegate
    private lateinit var summaryInteractor: SummaryInteractor
    private lateinit var favoriteInteractor: FavoriteInteractor
    private val testScope = TestCoroutineScope()

    @BeforeEach
    fun setup() {
        summaryInteractor = mockk(relaxed = true)
        favoriteInteractor = mockk()
        summaryBehaviorDelegate = SummaryBehaviorDelegateImpl(summaryInteractor, favoriteInteractor)
        summaryBehaviorDelegate.setUp(testScope)
    }

    @Test
    fun `loadSummary updates state with summary when successful`() = runTest {
        val summaryId = SummaryId("test")
        val summary = Fixtures.dummySummary1
        coEvery { summaryInteractor.getSummary(summaryId) } returns Result.success(summary)

        summaryBehaviorDelegate.loadSummary(summaryId)

        val state = summaryBehaviorDelegate.summaryContentState.first()
        assertEquals(summary.toSummaryUi(), state.summary)
    }

    @Test
    fun `loadSummary updates state with error when failed`() = runTest {
        val summaryId = SummaryId("test")
        val error = Exception("Test error")
        coEvery { summaryInteractor.getSummary(summaryId) } returns Result.failure(error)

        summaryBehaviorDelegate.loadSummary(summaryId)

        val state = summaryBehaviorDelegate.summaryContentState.first()
        assertEquals(error.toUiError(), state.error)
    }

    @Test
    fun `toggleFavorite updates state with favorite status when successful`() = runTest {
        val summaryId = SummaryId("test")
        val summary = Fixtures.dummySummary1
        coEvery { summaryInteractor.getSummary(summaryId) } returns Result.success(summary)
        coEvery { favoriteInteractor.isFavorite(summaryId) } returns Result.success(false)
        coEvery { favoriteInteractor.createFavorite(any()) } returns Result.success(Unit)
        coEvery { favoriteInteractor.deleteFavoriteBySummaryId(any()) } returns Result.success(Unit)

        summaryBehaviorDelegate.loadSummary(summaryId)
        summaryBehaviorDelegate.toggleFavorite()

        summaryBehaviorDelegate.summaryContentState.test {
            assertEquals(
                SummaryContentState(
                    summary = summary.toSummaryUi(),
                    isFavorite = true,
                    favoriteProgressState = ProgressState.Hide
                ),
                expectMostRecentItem()
            )

            summaryBehaviorDelegate.toggleFavorite()

            assertEquals(
                SummaryContentState(
                    summary = summary.toSummaryUi(),
                    isFavorite = false,
                    favoriteProgressState = ProgressState.Hide
                ),
                expectMostRecentItem()
            )
        }
    }

    @Test
    fun `toggleFavorite does not update state when summary is not loaded`() = runTest {
        summaryBehaviorDelegate.toggleFavorite()

        val state = summaryBehaviorDelegate.summaryContentState.first()
        assertFalse(state.isFavorite)
        assertEquals(ProgressState.Hide, state.favoriteProgressState)
    }
}
