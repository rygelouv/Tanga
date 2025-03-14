package app.books.tanga.data.aiprompts

import app.books.tanga.entity.AIPrompt
import app.books.tanga.entity.SummaryId
import app.books.tanga.firestore.FirestoreDatabase
import app.books.tanga.firestore.FirestoreOperationHandler
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

fun interface AIPromptsRepository {
    suspend fun getAIPromptsForSummary(summaryId: SummaryId): Result<List<AIPrompt>>
}

val FirebaseFirestore.aiPromptsCollection
    get() = collection(FirestoreDatabase.AIPrompts.COLLECTION_NAME)

class AIPromptsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val operationHandler: FirestoreOperationHandler
) : AIPromptsRepository, FirestoreOperationHandler by operationHandler {

    override suspend fun getAIPromptsForSummary(summaryId: SummaryId): Result<List<AIPrompt>> = executeOperation {
        val docSnapshot = firestore.aiPromptsCollection
            .document(summaryId.value)
            .get()
            .await()
        val aiPrompts = docSnapshot.get(FirestoreDatabase.AIPrompts.Fields.PROMPTS) as List<Map<String, Any?>>
        aiPrompts.map {
            it.toAIPrompt()
        }
    }
}
