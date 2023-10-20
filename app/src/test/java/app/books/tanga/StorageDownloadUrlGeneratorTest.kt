package app.books.tanga

import app.books.tanga.common.urls.StorageDownloadUrlGenerator
import app.books.tanga.common.urls.SummaryCoverUrlCache
import app.books.tanga.entity.SummaryId
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.mockk.coEvery
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StorageDownloadUrlGeneratorTest {

    private lateinit var generator: StorageDownloadUrlGenerator
    private val mockUrl = "http://example.com/cached_url"
    private val mockStorage = mockk<FirebaseStorage>()
    private val mockStorageReference = mockk<StorageReference>()
    private val mockkStorageReferenceSummary = mockk<StorageReference>()
    private val mockkStorageReferenceSummaryCover = mockk<StorageReference>()

    @BeforeEach
    fun setUp() {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")

        every { mockStorage.reference } returns mockStorageReference
        every { mockStorageReference.child(any()) } returns mockkStorageReferenceSummary
        every { mockkStorageReferenceSummary.child(any()) } returns mockkStorageReferenceSummaryCover
        coEvery { mockkStorageReferenceSummaryCover.downloadUrl.await().toString() } returns mockUrl

        generator = StorageDownloadUrlGenerator(storage = mockStorage)
    }

    @Test
    fun `verify generateCoverDownloadUrl uses cached value when url had been already cached`() =
        runTest {
            // Given
            val summaryId = SummaryId("123")
            val otherUrl = "http://example.com/some_other_url"
            SummaryCoverUrlCache.put(summaryId, otherUrl)

            // When
            val url = generator.generateCoverDownloadUrl(summaryId)
            val url2 = generator.generateCoverDownloadUrl(summaryId)

            Assertions.assertTrue(url == otherUrl)
            Assertions.assertTrue(url2 == otherUrl)

            verify(exactly = 0) { mockkStorageReferenceSummaryCover.downloadUrl }
            confirmVerified(mockkStorageReferenceSummaryCover)
        }

    @Test
    fun `verify generateCoverDownloadUrl generates url when url had not been found in cache`() =
        runTest {
            // Given
            val summaryId = SummaryId("123")
            // val otherUrl = "http://example.com/some_other_url"
            SummaryCoverUrlCache.clear()

            // When
            val url = generator.generateCoverDownloadUrl(summaryId)

            Assertions.assertTrue(url == mockUrl)

            verify(exactly = 1) { mockkStorageReferenceSummaryCover.downloadUrl }
            confirmVerified(mockkStorageReferenceSummaryCover)
        }

    @Test
    fun `verify generateCoverDownloadUrl generate url after cache had been cleared`() = runTest {
        // Given
        val summaryId = SummaryId("123")
        val otherUrl = "http://example.com/some_other_url"
        SummaryCoverUrlCache.put(summaryId, otherUrl)

        // When
        val url = generator.generateCoverDownloadUrl(summaryId)
        Assertions.assertTrue(url == otherUrl)

        SummaryCoverUrlCache.clear()

        val url2 = generator.generateCoverDownloadUrl(summaryId)
        Assertions.assertTrue(url2 == mockUrl)

        verify(exactly = 1) { mockkStorageReferenceSummaryCover.downloadUrl }
        confirmVerified(mockkStorageReferenceSummaryCover)
    }

    @Test
    fun `verify generateCoverDownloadUrl saves url to cache after generating`() = runTest {
        // Given
        val summaryId = SummaryId("123")

        // When
        val url = generator.generateCoverDownloadUrl(summaryId)

        val urlFromCache = SummaryCoverUrlCache.get(summaryId)

        Assertions.assertTrue(url == mockUrl)
        Assertions.assertTrue(urlFromCache == url)

        verify(exactly = 1) { mockkStorageReferenceSummaryCover.downloadUrl }
        confirmVerified(mockkStorageReferenceSummaryCover)
    }

    @AfterEach
    fun tearDown() {
        SummaryCoverUrlCache.clear()
        unmockkAll()
    }
}
