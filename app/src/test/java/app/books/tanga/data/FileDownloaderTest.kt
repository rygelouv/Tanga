package app.books.tanga.data

import app.books.tanga.data.download.FileDownloader
import app.books.tanga.data.download.summaryReference
import app.books.tanga.entity.SummaryId
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class FileDownloaderTest {

    private lateinit var fileDownloader: FileDownloader
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference

    @BeforeEach
    fun setup() {
        storage = mockk()
        storageReference = mockk()
        fileDownloader = FileDownloader(storage)
    }

    @Test
    fun `downloadSummaryText returns successful result when download is successful`() = runTest {
        val summaryId = SummaryId("test")
        val expectedBytes = "Test content".toByteArray()
        coEvery { storage.summaryReference(summaryId) } returns storageReference
        coEvery { storageReference.child(any()) } returns storageReference
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        coEvery { storageReference.getBytes(any()).await() } returns expectedBytes

        val result = fileDownloader.downloadSummaryText(summaryId)

        assertTrue(result.isSuccess)
        assertArrayEquals(expectedBytes, result.getOrNull())
    }

    @Test
    fun `downloadSummaryText returns failure result when download fails`() = runTest {
        val summaryId = SummaryId("test")
        val exception = Exception("Test error")
        coEvery { storage.summaryReference(summaryId) } returns storageReference
        coEvery { storageReference.child(any()) } returns storageReference
        coEvery { storageReference.getBytes(any()) } throws exception

        val result = fileDownloader.downloadSummaryText(summaryId)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }
}
