package app.books.tanga.data.category

import app.books.tanga.data.FirestoreDatabase
import app.books.tanga.entity.Category
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface CategoryRepository {

    suspend fun getCategories(): Result<List<Category>>
}

class CategoryRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : CategoryRepository {

    override suspend fun getCategories(): Result<List<Category>> {
        return runCatching {
            val categories = firestore.categoryCollection.get().await()
            categories.map {
                it.data.toCategory()
            }
        }.onFailure { Result.failure<Throwable>(it) }
    }

    private val FirebaseFirestore.categoryCollection
        get() = collection(FirestoreDatabase.Categories.COLLECTION_NAME)
}