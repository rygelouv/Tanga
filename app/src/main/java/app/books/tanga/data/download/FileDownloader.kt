package app.books.tanga.data.download

import app.books.tanga.data.SummaryFormatType
import app.books.tanga.entity.SummaryId
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject
import kotlinx.coroutines.tasks.await
import timber.log.Timber

const val ONE_MEGABYTE: Long = 1024 * 1024

@Suppress("TooGenericExceptionCaught")
class FileDownloader @Inject constructor(
    private val storage: FirebaseStorage
) {
    suspend fun downloadSummaryText(summaryId: SummaryId): Result<ByteArray?> {
        val summaryRef = storage.summaryReference(summaryId)
        val textFileRef = summaryRef.child(SummaryFormatType.TEXT.filename)

        return try {
            val textBytes = textFileRef.getBytes(ONE_MEGABYTE).await()
            Result.success(textBytes)
        } catch (e: Exception) {
            Timber.e(e, "Error downloading text file for summary: $summaryId")
            Result.failure(e)
        }
    }
}
