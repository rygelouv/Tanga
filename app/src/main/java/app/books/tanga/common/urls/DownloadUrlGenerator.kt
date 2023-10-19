package app.books.tanga.common.urls

import app.books.tanga.entity.SummaryId
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import kotlinx.coroutines.tasks.await
import timber.log.Timber

/**
 * Interface for generating download URLs for various types of resources associated with a summary or book item.
 * Implementations of this interface provide methods for generating URLs for cover images, text summaries,
 * audio files, and graphic assets.
 */
interface DownloadUrlGenerator {

    suspend fun generateCoverDownloadUrl(summaryId: SummaryId): String?

    suspend fun generateTextDownloadUrl(summaryId: SummaryId): String?

    suspend fun generateAudioDownloadUrl(summaryId: SummaryId): String?

    suspend fun generateGraphicDownloadUrl(summaryId: SummaryId): String?
}

/**
 * Implementation of [DownloadUrlGenerator] for generating download URLs for various types of resources
 * using Firebase Storage.
 */
class StorageDownloadUrlGenerator @Inject constructor(
    private val storage: FirebaseStorage
) : DownloadUrlGenerator {

    /**
     * Check if the cover download URL for the given summary ID is already cached.
     * If it is, return the cached URL. Otherwise, generate the download URL and cache it.
     */
    override suspend fun generateCoverDownloadUrl(
        summaryId: SummaryId
    ): String? = SummaryCoverUrlCache.get(summaryId) ?: getSummaryReference(summaryId).generateDownloadUrl(
        path = SummaryFormatType.COVER.filename
    ).also { url ->
        url?.let { SummaryCoverUrlCache.put(summaryId, it) }
    }

    override suspend fun generateTextDownloadUrl(summaryId: SummaryId): String? = getSummaryReference(
        summaryId
    ).generateDownloadUrl(
        path = SummaryFormatType.TEXT.filename
    )

    override suspend fun generateAudioDownloadUrl(summaryId: SummaryId): String? = getSummaryReference(
        summaryId
    ).generateDownloadUrl(
        path = SummaryFormatType.AUDIO.filename
    )

    override suspend fun generateGraphicDownloadUrl(summaryId: SummaryId): String? = getSummaryReference(
        summaryId
    ).generateDownloadUrl(
        path = SummaryFormatType.GRAPHIC.filename
    )

    private fun getSummaryReference(summaryId: SummaryId): StorageReference {
        val storageRef = storage.reference

        return storageRef.child(summaryId.value)
    }

    private suspend fun StorageReference.generateDownloadUrl(path: String): String? = try {
        child(path).downloadUrl.await().toString()
    } catch (e: StorageException) {
        Timber.e("Error generating download url for path: ${this.path}/$path", e)
        null
    }

    companion object {
        /**
         * Singleton instance of [StorageDownloadUrlGenerator] for convenience in places where when can't use Hilt DI.
         */
        val instance: StorageDownloadUrlGenerator
            get() = StorageDownloadUrlGenerator(storage = Firebase.storage)
    }
}

@Module
@InstallIn(SingletonComponent::class)
fun interface StorageDownloadUrlGeneratorModule {
    @Binds
    fun StorageDownloadUrlGenerator.provideStorageDownloadUrlGenerator(): DownloadUrlGenerator
}
