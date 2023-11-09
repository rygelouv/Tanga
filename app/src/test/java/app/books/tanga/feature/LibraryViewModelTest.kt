package app.books.tanga.feature

import app.books.tanga.common.ui.ProgressState
import app.books.tanga.entity.Favorite
import app.books.tanga.feature.library.FavoriteInteractor
import app.books.tanga.feature.library.LibraryViewModel
import app.books.tanga.feature.library.toFavoriteUi
import app.books.tanga.fixtures.Fixtures
import app.books.tanga.rule.MainCoroutineDispatcherExtension
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

private fun <E> List<E>.second(): E = this[1]

@ExtendWith(MainCoroutineDispatcherExtension::class)
@ExperimentalCoroutinesApi
@ExperimentalTime
class LibraryViewModelTest {

    private lateinit var favoriteInteractor: FavoriteInteractor
    private lateinit var viewModel: LibraryViewModel
    private val fakeFavorites = listOf(Fixtures.dummyFavorite1, Fixtures.dummyFavorite2)

    @BeforeEach
    fun setUp() {
        // Prepare the mock for FavoriteInteractor
        favoriteInteractor = mockk(relaxed = true)
    }

    @Test
    fun `Initial state is progress shown`() = runTest {
        viewModel = LibraryViewModel(favoriteInteractor)

        viewModel.state.test {
            val initialState = awaitItem()
            Assertions.assertTrue(initialState.progressState == ProgressState.Show)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `Library state updates when favorites change`() = runTest {
        val favoritesFlow: Flow<List<Favorite>> = flow { emit(fakeFavorites) }

        coEvery { favoriteInteractor.observeFavorites() } returns favoritesFlow

        viewModel = LibraryViewModel(favoriteInteractor)

        viewModel.state.test {
            val emission = expectMostRecentItem()
            Assertions.assertTrue(emission.progressState == ProgressState.Hide)
            val expectedFavoriteUi = fakeFavorites.map { it.toFavoriteUi() }
            Assertions.assertEquals(emission.favorites?.size, expectedFavoriteUi.size)
            Assertions.assertEquals(emission.favorites?.first(), expectedFavoriteUi.first())
            Assertions.assertEquals(emission.favorites?.second(), expectedFavoriteUi.second())
            cancelAndConsumeRemainingEvents()
        }
    }
}
