package app.books.tanga.data.category

import app.books.tanga.entity.Category
import app.books.tanga.firestore.FirestoreDatabase
import app.books.tanga.firestore.FirestoreOperationHandler
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

fun interface CategoryRepository {
    suspend fun getCategories(): Result<List<Category>>
}

val FirebaseFirestore.categoryCollection
    get() = collection(FirestoreDatabase.Categories.COLLECTION_NAME)

class CategoryRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val operationHandler: FirestoreOperationHandler
) : CategoryRepository, FirestoreOperationHandler by operationHandler {
    override suspend fun getCategories(): Result<List<Category>> =
        executeOperation {
            val categories = firestore.categoryCollection.get().await()
            categories.map {
                it.data.toCategory()
            }
        }
}
