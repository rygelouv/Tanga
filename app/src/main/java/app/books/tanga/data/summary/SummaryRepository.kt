package app.books.tanga.data.summary

import app.books.tanga.firestore.FirestoreDatabase
import app.books.tanga.entity.Summary
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface SummaryRepository {

    suspend fun getSummary(summaryId: String): Result<Summary>

    /**
     * Get the list of summaries
     */
    suspend fun getAllSummaries(): Result<List<Summary>>

    suspend fun getSummariesByCategory(categoryId: String): Result<List<Summary>>
    suspend fun searchSummaryInMemoryCache(query: String): Result<List<Summary>>

    suspend fun saveSummariesInMemoryCache(summaries: List<Summary>)
}

class SummaryRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val summaryInMemoryCache: SummaryInMemoryCache
) : SummaryRepository {
    override suspend fun getSummary(summaryId: String): Result<Summary> {
        return runCatching {
            val summary = firestore.summaryCollection.document(summaryId).get().await()

            summary.data?.toSummary() ?: throw Exception("Summary not found")
        }.onFailure {
            Result.failure<Throwable>(it)
        }
    }

    override suspend fun getAllSummaries(): Result<List<Summary>> {
        return runCatching {
            val summaries = firestore.summaryCollection.get().await()

            summaries.map {
                it.data.toSummary()
            }
        }.onFailure {
            Result.failure<Throwable>(it)
        }
    }

    override suspend fun getSummariesByCategory(categoryId: String): Result<List<Summary>> {
        return runCatching {
            val summaries = firestore.summaryCollection
                .whereArrayContains(FirestoreDatabase.Summaries.Fields.CATEGORIES, categoryId)
                .get()
                .await()

            summaries.map {
                it.data.toSummary()
            }
        }.onFailure {
            Result.failure<Throwable>(it)
        }
    }

    /**
     * Search for summaries by title or author
     * This is a quick search using the in memory cache
     */
    override suspend fun searchSummaryInMemoryCache(query: String): Result<List<Summary>> {
        return runCatching {
            val summaries = summaryInMemoryCache.getAll()
            summaries.filter { summary ->
                summary.title.contains(query, ignoreCase = true) ||
                        summary.author.contains(query, ignoreCase = true)
            }
        }.onFailure {
            Result.failure<Throwable>(it)
        }
    }

    override suspend fun saveSummariesInMemoryCache(summaries: List<Summary>) {
        summaryInMemoryCache.putAll(summaries)
    }

    private val FirebaseFirestore.summaryCollection: CollectionReference
        get() = collection(FirestoreDatabase.Summaries.COLLECTION_NAME)
}