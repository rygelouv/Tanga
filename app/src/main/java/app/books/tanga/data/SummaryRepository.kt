package app.books.tanga.data

import app.books.tanga.domain.summary.Summary
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface SummaryRepository {
    /**
     * Get the list of summaries
     */
    suspend fun getSummaries(): Result<List<Summary>>

    suspend fun getSummaryByCategory(categoryId: String): Result<List<Summary>>
}

class SummaryRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : SummaryRepository {

    override suspend fun getSummaries(): Result<List<Summary>> {
        return runCatching {
            val summaries = firestore.summaryCollection.get().await()

            summaries.map {
                it.data.toSummary()
            }
        }.onFailure {
            Result.failure<Throwable>(it)
        }
    }

    override suspend fun getSummaryByCategory(categoryId: String): Result<List<Summary>> {
        return runCatching {
            val summaries = firestore.summaryCollection
                .whereEqualTo("categories", categoryId)
                .get()
                .await()

            summaries.map {
                it.data.toSummary()
            }
        }.onFailure {
            Result.failure<Throwable>(it)
        }
    }

    private val FirebaseFirestore.summaryCollection: CollectionReference
        get() = collection(FirestoreDatabase.Summaries.COLLECTION_NAME)
}