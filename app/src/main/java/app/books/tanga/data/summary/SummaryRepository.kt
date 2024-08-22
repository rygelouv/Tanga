package app.books.tanga.data.summary

import app.books.tanga.entity.Summary
import app.books.tanga.entity.SummaryId
import app.books.tanga.firestore.FirestoreDatabase
import app.books.tanga.firestore.FirestoreOperationHandler
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

interface SummaryRepository {
    suspend fun getSummary(summaryId: SummaryId): Result<Summary>

    /**
     * Get the list of summaries
     */
    suspend fun getAllSummaries(): Result<List<Summary>>

    suspend fun getSummariesByCategory(categoryId: String): Result<List<Summary>>

    suspend fun searchSummaryInMemoryCache(query: String): Result<List<Summary>>

    suspend fun saveSummariesInMemoryCache(summaries: List<Summary>)

    suspend fun getWeeklySummary(): Result<Summary>
}

class SummaryRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val summaryInMemoryCache: SummaryInMemoryCache,
    private val operationHandler: FirestoreOperationHandler
) : SummaryRepository {

    override suspend fun getSummary(summaryId: SummaryId): Result<Summary> =
        operationHandler.executeOperation {
            val summary =
                firestore
                    .summaryCollection
                    .document(summaryId.value)
                    .get()
                    .await()

            summary.data?.toSummary() ?: throw Exception("Summary not found")
        }

    override suspend fun getAllSummaries(): Result<List<Summary>> =
        operationHandler.executeOperation {
            val summaries =
                firestore.summaryCollection
                    .whereEqualTo(FirestoreDatabase.Summaries.Fields.IS_VISIBLE, true)
                    .get()
                    .await()

            summaries.map {
                it.data.toSummary()
            }
        }

    override suspend fun getSummariesByCategory(categoryId: String): Result<List<Summary>> =
        operationHandler.executeOperation {
            val summaries =
                firestore
                    .summaryCollection
                    .whereEqualTo(FirestoreDatabase.Summaries.Fields.IS_VISIBLE, true)
                    .whereArrayContains(FirestoreDatabase.Summaries.Fields.CATEGORIES, categoryId)
                    .get()
                    .await()

            summaries.map {
                it.data.toSummary()
            }
        }

    override suspend fun getWeeklySummary(): Result<Summary> {
        val weeklySummaryCollection = firestore.weeklySummaryCollection.get().await()
        val weeklySummaryData = weeklySummaryCollection.documents.firstOrNull()
        val weeklySummarySlug = weeklySummaryData?.id ?: error("Weekly summary not found")
        val weeklySummaryId = SummaryId(weeklySummarySlug)
        val weeklySummary = getSummary(weeklySummaryId)

        return weeklySummary
    }

    /**
     * Search for summaries by title or author
     * This is a quick search using the in memory cache
     */
    override suspend fun searchSummaryInMemoryCache(query: String): Result<List<Summary>> {
        val summaries = summaryInMemoryCache.getAll()
        val filteredSummaries =
            summaries.filter { summary ->
                summary.title.contains(query, ignoreCase = true) ||
                    summary.author.contains(query, ignoreCase = true)
            }
        return Result.success(filteredSummaries)
    }

    override suspend fun saveSummariesInMemoryCache(summaries: List<Summary>) {
        summaryInMemoryCache.putAll(summaries)
    }

    private val FirebaseFirestore.summaryCollection: CollectionReference
        get() = collection(FirestoreDatabase.Summaries.COLLECTION_NAME)

    private val FirebaseFirestore.weeklySummaryCollection: CollectionReference
        get() = collection(FirestoreDatabase.WeeklySummary.COLLECTION_NAME)
}
