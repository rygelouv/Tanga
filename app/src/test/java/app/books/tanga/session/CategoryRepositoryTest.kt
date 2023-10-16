package app.books.tanga.session

import app.books.tanga.data.category.CategoryRepositoryImpl
import app.books.tanga.data.category.categoryCollection
import app.books.tanga.entity.Category
import app.books.tanga.entity.CategoryId
import app.books.tanga.firestore.FirestoreDatabase
import app.books.tanga.firestore.FirestoreOperationHandler
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class CategoryRepositoryTest {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var operationHandler: FirestoreOperationHandler
    private lateinit var repository: CategoryRepositoryImpl

    @BeforeEach
    fun setUp() {
        firestore = mockk()
        operationHandler = FakeFirestoreOperationHandler()
        repository = CategoryRepositoryImpl(firestore, operationHandler)
    }

    @Test
    fun `getCategories fetches categories successfully`() = runTest {
        // Given
        setupFirestoreMocks()
        val category = Category(
            id = CategoryId("testId"),
            name = "testName"
        )

        // When
        val categories = repository.getCategories().getOrNull()

        // Then
        assertTrue(categories?.size == 2)
        assertEquals(category, categories?.get(0))
        assertEquals(category, categories?.get(1))
    }

    private fun setupFirestoreMocks() {
        val mockSnapshot = mockk<QuerySnapshot>()
        val mockDocument = mockk<QueryDocumentSnapshot>()
        val mockCollectionReference = mockk<CollectionReference>()
        val mockTask = mockk<Task<QuerySnapshot>>()

        every { firestore.categoryCollection } returns mockCollectionReference
        every { mockCollectionReference.get() } returns mockTask
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        coEvery { mockTask.await() } coAnswers { mockSnapshot }

        every { mockSnapshot.iterator() } returns mutableListOf(mockDocument, mockDocument).iterator()
        every { mockSnapshot.documents } returns listOf(mockDocument, mockDocument)
        every { mockDocument.data } returns mockMapOfData()
    }

    private fun mockMapOfData(): Map<String, Any?> = mapOf(
        FirestoreDatabase.Categories.Fields.SLUG to "testId",
        FirestoreDatabase.Categories.Fields.NAME to "testName"
    )

    class FakeFirestoreOperationHandler : FirestoreOperationHandler {
        override suspend fun <T> executeOperation(operation: suspend () -> T): Result<T> = Result.success(operation())
    }
}
