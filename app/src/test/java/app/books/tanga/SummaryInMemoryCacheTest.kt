package app.books.tanga

import app.books.tanga.data.summary.SummaryInMemoryCache
import app.books.tanga.fixtures.Fixtures.dummySummary1
import app.books.tanga.fixtures.Fixtures.dummySummary2
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SummaryInMemoryCacheTest {

    private lateinit var cache: SummaryInMemoryCache

    @BeforeEach
    fun setUp() {
        cache = SummaryInMemoryCache()
    }

    @Test
    fun `getAll retrieves all summaries`() {
        val summary1 = dummySummary1
        val summary2 = dummySummary2
        cache.putAll(listOf(summary1, summary2))
        Assertions.assertEquals(listOf(summary1, summary2), cache.getAll())
    }

    @Test
    fun `putAll adds multiple summaries to cache`() {
        val summaries = listOf(
            dummySummary1,
            dummySummary2
        )
        cache.putAll(summaries)
        Assertions.assertEquals(summaries, cache.getAll())
    }
}
