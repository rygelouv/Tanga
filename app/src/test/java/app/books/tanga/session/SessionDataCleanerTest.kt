package app.books.tanga.session

import app.books.tanga.data.favorite.FavoriteInMemoryCache
import app.books.tanga.errors.TangaErrorTracker
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SessionDataCleanerTest {

    private val favoriteInMemoryCache: FavoriteInMemoryCache = mockk(relaxed = true)
    private val errorTracker: TangaErrorTracker = mockk(relaxed = true)
    private val ioDispatcher = TestCoroutineDispatcher()

    private lateinit var sessionDataCleaner: SessionDataCleaner

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(ioDispatcher)
        sessionDataCleaner = SessionDataCleaner(favoriteInMemoryCache, errorTracker, ioDispatcher)
    }

    @Test
    fun `when invoked, favoriteInMemoryCache is cleared`() = runTest {
        sessionDataCleaner()

        coVerify { favoriteInMemoryCache.clear() }
    }

    @Test
    fun `when invoked, errorTracker clears the user details`() = runTest {
        sessionDataCleaner()

        coVerify { errorTracker.clearUserDetails() }
    }
}
