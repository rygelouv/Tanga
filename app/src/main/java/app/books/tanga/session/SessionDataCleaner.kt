package app.books.tanga.session

import app.books.tanga.data.favorite.FavoriteInMemoryCache
import app.books.tanga.di.IoDispatcher
import app.books.tanga.errors.TangaErrorTracker
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext

/**
 * Clears all session data from the app.
 * This include data stored locally on the device and in-memory caches.
 */
class SessionDataCleaner @Inject constructor(
    private val favoriteInMemoryCache: FavoriteInMemoryCache,
    private val errorTracker: TangaErrorTracker,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : suspend () -> Unit {
    override suspend fun invoke() {
        withContext(ioDispatcher + NonCancellable) {
            favoriteInMemoryCache.clear()
            errorTracker.clearUserDetails()
        }
    }
}
