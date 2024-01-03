package app.books.tanga.feature.summary

import app.books.tanga.data.category.CategoryRepository
import app.books.tanga.data.summary.SummaryRepository
import app.books.tanga.entity.CategoryId
import app.books.tanga.entity.Summary
import app.books.tanga.entity.SummaryId
import app.books.tanga.feature.summary.SummaryInteractor.Companion.RECOMMENDED_SUMMARIES_COUNT
import app.books.tanga.fixtures.Fixtures
import app.books.tanga.fixtures.Fixtures.summariesForCategory1
import app.books.tanga.fixtures.Fixtures.summariesForCategory2
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SummaryInteractorTest {

    private lateinit var summaryRepository: SummaryRepository
    private lateinit var categoryRepository: CategoryRepository
    private lateinit var summaryInteractor: SummaryInteractor

    @BeforeEach
    fun setup() {
        summaryRepository = mockk(relaxed = true)
        categoryRepository = mockk()
        summaryInteractor = SummaryInteractor(summaryRepository, categoryRepository)
    }

    @Test
    fun `summaryInteractor retrieves summary successfully`() = runTest {
        val expectedSummary = Result.success(Fixtures.dummySummary1)
        coEvery { summaryRepository.getSummary(any()) } returns expectedSummary

        val actualSummary = summaryInteractor.getSummary(SummaryId("1"))

        assertEquals(expectedSummary, actualSummary)
        coVerify { summaryRepository.getSummary(any()) }
    }

    @Test
    fun `summaryInteractor retrieves summaries for categories successfully`() = runTest {
        val expectedSummaries = Result.success(summariesForCategory1 + summariesForCategory2)
        coEvery { summaryRepository.getSummariesByCategory(any()) } returns expectedSummaries

        val actualSummaries = summaryInteractor.getSummariesForCategories(listOf("1"))

        assertEquals(expectedSummaries, actualSummaries)
        coVerify { summaryRepository.getSummariesByCategory(any()) }
    }

    @Test
    fun `summaryInteractor retrieves all summaries successfully`() = runTest {
        val expectedSummaries = Result.success(listOf(Fixtures.dummySummary1, Fixtures.dummySummary2))
        coEvery { summaryRepository.getAllSummaries() } returns expectedSummaries

        val actualSummaries = summaryInteractor.getAllSummaries()

        assertEquals(expectedSummaries, actualSummaries)
        coVerify { summaryRepository.getAllSummaries() }
    }

    @Test
    fun `summaryInteractor retrieves categories successfully`() = runTest {
        val expectedCategories = Result.success(listOf(Fixtures.dummyCategory1, Fixtures.dummyCategory2))
        coEvery { categoryRepository.getCategories() } returns expectedCategories

        val actualCategories = summaryInteractor.getCategories()

        assertEquals(expectedCategories, actualCategories)
        coVerify { categoryRepository.getCategories() }
    }

    @Test
    fun `summaryInteractor retrieves recommended summaries successfully`() = runTest {
        val summary = Fixtures.dummySummary1
        val expectedSummaries = Result.success(listOf(Fixtures.dummySummary2))
        coEvery {
            summaryRepository.getSummariesByCategory(any())
        } returns Result.success(listOf(Fixtures.dummySummary2))

        val actualSummaries = summaryInteractor.getRecommendationsForSummary(summary)

        assertEquals(expectedSummaries, actualSummaries)
        coVerify { summaryRepository.getSummariesByCategory(any()) }
    }

    @Test
    fun `summaryInteractor retrieves empty list when no recommended summaries`() = runTest {
        val summary = Fixtures.dummySummary1
        coEvery { summaryRepository.getSummariesByCategory(any()) } returns Result.success(emptyList())

        val actualSummaries = summaryInteractor.getRecommendationsForSummary(summary)

        assertEquals(Result.success(emptyList<Summary>()), actualSummaries)
        coVerify { summaryRepository.getSummariesByCategory(any()) }
    }

    @Test
    fun `summaryInteractor retrieves recommended summaries without the input summary`() = runTest {
        val summary = Fixtures.dummySummary1
        val expectedSummaries = Result.success(listOf(Fixtures.dummySummary2))
        coEvery { summaryRepository.getSummariesByCategory(any()) } returns Result.success(
            listOf(
                summary,
                Fixtures.dummySummary2
            )
        )

        val actualSummaries = summaryInteractor.getRecommendationsForSummary(summary)

        assertEquals(expectedSummaries, actualSummaries)
        coVerify { summaryRepository.getSummariesByCategory(any()) }
    }

    @Test
    fun `summaryInteractor returns recommendations without duplications`() = runTest {
        val summary = Fixtures.dummySummary1
        val expectedSummaries = Result.success(listOf(Fixtures.dummySummary2))
        coEvery { summaryRepository.getSummariesByCategory(any()) } returns Result.success(
            listOf(
                summary,
                Fixtures.dummySummary2,
                Fixtures.dummySummary2,
                Fixtures.dummySummary2,
                summary,
                Fixtures.dummySummary2,
                Fixtures.dummySummary2,
                summary,
            )
        )

        val actualSummaries = summaryInteractor.getRecommendationsForSummary(summary)

        assertEquals(1, actualSummaries.getOrNull()?.size)
        coVerify { summaryRepository.getSummariesByCategory(any()) }
    }

    @Test
    fun `summaryInteractor returns only 5 recommendations`() = runTest {
        val summary = Fixtures.dummySummary1

        val expectedSummaries = Result.success(listOf(Fixtures.dummySummary2))
        coEvery { summaryRepository.getSummariesByCategory(any()) } returns Result.success(
            listOf(
                summary,
                Fixtures.dummySummary2,
                dummySummary3,
                dummySummary4,
                summary,
                dummySummary5,
                dummySummary6,
                summary,
            )
        )

        val actualSummaries = summaryInteractor.getRecommendationsForSummary(summary)

        assertEquals(RECOMMENDED_SUMMARIES_COUNT, actualSummaries.getOrNull()?.size)
        coVerify { summaryRepository.getSummariesByCategory(any()) }
    }

    companion object {
        val dummySummary3 = Summary(
            id = SummaryId("3"),
            title = "Content3",
            author = "Author3",
            synopsis = "Synopsis1",
            coverImageUrl = "http://example.com/image.png",
            textUrl = "TextUrl1",
            categories = listOf(CategoryId("1"), CategoryId("2")),
            playingLength = "12:20",
            audioUrl = "AudioUrl1",
            graphicUrl = "GraphicUrl1",
            videoUrl = "VideoUrl1",
            authorPictureUrl = "AuthorPictureUrl1",
            purchaseBookUrl = "PurchaseBookUrl1",
        )

        val dummySummary4 = Summary(
            id = SummaryId("4"),
            title = "Content4",
            author = "Author4",
            synopsis = "Synopsis2",
            coverImageUrl = "http://example.com/image.png",
            textUrl = "TextUrl2",
            categories = listOf(CategoryId("3"), CategoryId("4")),
            playingLength = "10:36",
            audioUrl = "AudioUrl2",
            graphicUrl = "GraphicUrl2",
            videoUrl = "VideoUrl2",
            authorPictureUrl = "AuthorPictureUrl2",
            purchaseBookUrl = "PurchaseBookUrl2",
        )

        val dummySummary5 = Summary(
            id = SummaryId("5"),
            title = "Content5",
            author = "Author5",
            synopsis = "Synopsis5",
            coverImageUrl = "http://example.com/image.png",
            textUrl = "TextUrl1",
            categories = listOf(CategoryId("1"), CategoryId("2")),
            playingLength = "12:20",
            audioUrl = "AudioUrl1",
            graphicUrl = "GraphicUrl1",
            videoUrl = "VideoUrl1",
            authorPictureUrl = "AuthorPictureUrl1",
            purchaseBookUrl = "PurchaseBookUrl1",
        )

        val dummySummary6 = Summary(
            id = SummaryId("6"),
            title = "Content6",
            author = "Author6",
            synopsis = "Synopsis6",
            coverImageUrl = "http://example.com/image.png",
            textUrl = "TextUrl2",
            categories = listOf(CategoryId("3"), CategoryId("4")),
            playingLength = "10:36",
            audioUrl = "AudioUrl2",
            graphicUrl = "GraphicUrl2",
            videoUrl = "VideoUrl2",
            authorPictureUrl = "AuthorPictureUrl2",
            purchaseBookUrl = "PurchaseBookUrl2",
        )
    }
}
