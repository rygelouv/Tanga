package app.books.tanga.data

import app.books.tanga.domain.summary.Summary
import javax.inject.Inject

/**
 * In memory cache for summaries.
 * This is used to perform a quick search on the summaries
 * because we don't have a full text search in Firestore yet.
 *
 * This cache is volatile and will be cleared as soon as the component using it is destroyed.
 */
class SummaryInMemoryCache @Inject constructor() {
    private val cache = HashMap<String, Summary>()

    fun getAll(): List<Summary> {
        return cache.values.toList()
    }

    fun putAll(summaries: List<Summary>) {
        summaries.forEach { summary ->
            cache[summary.slug] = summary
        }
    }
}