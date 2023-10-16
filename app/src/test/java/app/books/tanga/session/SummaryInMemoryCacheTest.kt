package app.books.tanga.session

import app.books.tanga.data.summary.SummaryInMemoryCache
import app.books.tanga.entity.CategoryId
import app.books.tanga.entity.Summary
import app.books.tanga.entity.SummaryId
import org.junit.jupiter.api.Assertions.assertEquals
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
        assertEquals(listOf(summary1, summary2), cache.getAll())
    }

    @Test
    fun `putAll adds multiple summaries to cache`() {
        val summaries = listOf(
            dummySummary1,
            dummySummary2
        )
        cache.putAll(summaries)
        assertEquals(summaries, cache.getAll())
    }

    companion object {
        val dummySummary1 = Summary(
            id = SummaryId("1"),
            title = "Content1",
            author = "Author1",
            synopsis = "Synopsis1",
            coverImageUrl = "CoverImageUrl1",
            textUrl = "TextUrl1",
            categories = listOf(CategoryId("1"), CategoryId("2")),
            playingLength = "PlayingLength1",
            audioUrl = "AudioUrl1",
            graphicUrl = "GraphicUrl1",
            videoUrl = "VideoUrl1",
            authorPictureUrl = "AuthorPictureUrl1",
            purchaseBookUrl = "PurchaseBookUrl1",
        )

        val dummySummary2 = Summary(
            id = SummaryId("2"),
            title = "Content2",
            author = "Author2",
            synopsis = "Synopsis2",
            coverImageUrl = "CoverImageUrl2",
            textUrl = "TextUrl2",
            categories = listOf(CategoryId("3"), CategoryId("4")),
            playingLength = "PlayingLength2",
            audioUrl = "AudioUrl2",
            graphicUrl = "GraphicUrl2",
            videoUrl = "VideoUrl2",
            authorPictureUrl = "AuthorPictureUrl2",
            purchaseBookUrl = "PurchaseBookUrl2",
        )
    }
}
