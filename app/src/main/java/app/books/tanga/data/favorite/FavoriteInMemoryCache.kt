package app.books.tanga.data.favorite

import app.books.tanga.entity.Favorite
import app.books.tanga.entity.FavoriteId
import app.books.tanga.entity.SummaryId
import javax.inject.Inject
import javax.inject.Singleton

/**
 * In memory cache for favorites.
 * Used to speed up favorite-related operations.
 * Also used as single source of truth for favorites.
 *
 * This cache is a singleton meaning it will be cleared only when the app is killed.
 */
@Singleton
class FavoriteInMemoryCache @Inject constructor() {
    private val cache = HashMap<FavoriteId, Favorite>()

    fun add(favorite: Favorite) {
        cache[favorite.id] = favorite
    }

    fun remove(favorite: Favorite) {
        cache.remove(favorite.id)
    }

    fun getBySummaryId(summaryId: SummaryId): Favorite? = cache.values.find { it.summaryId == summaryId.value }

    fun getAll(): List<Favorite> = cache.values.toList()

    fun putAll(favorites: List<Favorite>) {
        favorites.forEach { favorite ->
            cache[favorite.id] = favorite
        }
    }

    fun isEmpty(): Boolean = cache.isEmpty()

    fun clear() {
        cache.clear()
    }
}
