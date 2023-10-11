package app.books.tanga.data.category

import app.books.tanga.entity.Category
import app.books.tanga.firestore.FirestoreDatabase
import app.books.tanga.firestore.FirestoreOperationHandler
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

interface CategoryRepository {
    suspend fun getCategories(): Result<List<Category>>
}

class CategoryRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val operationHandler: FirestoreOperationHandler
) : CategoryRepository {
    override suspend fun getCategories(): Result<List<Category>> =
        operationHandler.executeOperation {
            val categories = firestore.categoryCollection.get().await()
            categories.map {
                it.data.toCategory()
            }
        }

    private val FirebaseFirestore.categoryCollection
        get() = collection(FirestoreDatabase.Categories.COLLECTION_NAME)
}
