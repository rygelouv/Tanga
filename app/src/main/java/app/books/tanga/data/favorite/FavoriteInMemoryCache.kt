package app.books.tanga.data.favorite

import app.books.tanga.entity.Favorite
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
    private val cache = HashMap<String, Favorite>()

    fun add(favorite: Favorite) {
        cache[favorite.uid] = favorite
    }

    fun remove(favorite: Favorite) {
        cache.remove(favorite.uid)
    }

    fun getBySummaryId(summaryId: String): Favorite? {
        return cache.values.find { it.summaryId == summaryId }
    }

    fun getAll(): List<Favorite> {
        return cache.values.toList()
    }

    fun putAll(favorites: List<Favorite>) {
        favorites.forEach { favorite ->
            cache[favorite.uid] = favorite
        }
    }

    fun isEmpty(): Boolean {
        return cache.isEmpty()
    }
}