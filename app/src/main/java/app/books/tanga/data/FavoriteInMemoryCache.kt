package app.books.tanga.data

import app.books.tanga.domain.favorites.Favorite
import javax.inject.Inject

class FavoriteInMemoryCache @Inject constructor() {
    private val cache = HashMap<String, Favorite>()

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