package app.books.tanga.common.urls

import app.books.tanga.entity.SummaryId

/**
 * A cache for storing the cover image URL for summaries.
 */
object SummaryCoverUrlCache {
    private val cache = mutableMapOf<SummaryId, String>()

    fun get(summaryId: SummaryId): String? = cache[summaryId]

    fun put(summaryId: SummaryId, imageUrl: String) {
        cache[summaryId] = imageUrl
    }

    fun clear() {
        cache.clear()
    }
}
