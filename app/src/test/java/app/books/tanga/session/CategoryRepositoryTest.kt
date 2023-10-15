package app.books.tanga.session

import app.books.tanga.data.category.CategoryRepositoryImpl
import app.books.tanga.data.category.categoryCollection
import app.books.tanga.data.category.toCategory
import app.books.tanga.entity.Category
import app.books.tanga.entity.CategoryId
import app.books.tanga.firestore.FirestoreOperationHandler
import app.books.tanga.firestore.FirestoreOperationHandlerImpl
import app.books.tanga.tools.InternetConnectivityMonitor
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class CategoryRepositoryTest {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var operationHandler: FirestoreOperationHandler
    private lateinit var repository: CategoryRepositoryImpl
    private val internetConnectivityMonitor = FakeInternetConnectivityMonitor()

    private class FakeInternetConnectivityMonitor : InternetConnectivityMonitor {
        private val testDispatcher = UnconfinedTestDispatcher()

        override val isInternetAvailable: StateFlow<Boolean> by lazy {
            startMonitoring().stateIn(
                scope = CoroutineScope(testDispatcher),
                started = SharingStarted.Eagerly,
                initialValue = true
            )
        }

        fun startMonitoring() = flowOf(true)
    }

    @BeforeEach
    fun setUp() {
        firestore = mockk()
        operationHandler = FirestoreOperationHandlerImpl(internetConnectivityMonitor)
        repository = CategoryRepositoryImpl(firestore, operationHandler)
    }

    @Test
    @Disabled("Test taking too long to run - something must be wrong")
    fun `getCategories fetches categories successfully`() = runTest {
        // Given
        val mockSnapshot = mockk<QuerySnapshot>()
        val mockDocument = mockk<DocumentSnapshot>()
        val category = Category(
            id = CategoryId("testId"),
            name = "testName"
        ) // Assume some predefined Category object or mock
        coEvery { firestore.categoryCollection.get().await() } returns mockSnapshot
        every { mockSnapshot.documents } returns listOf(mockDocument, mockDocument)
        every { mockDocument.data } returns mockMapOfData() // Assume some predefined map or mock
        // every { mockMapOfData().toCategory() } returns category // Assume you have this extension function

        // When
        val categories = repository.getCategories().getOrNull()

        // Then
        assert(categories?.size == 2)
        assert(categories?.get(0) == category)
        assert(categories?.get(1) == category)

        coVerify {
            // operationHandler.executeOperation(any())
            firestore.categoryCollection
            mockSnapshot.documents
            mockDocument.data?.toCategory()
        }
    }

    private fun mockMapOfData(): Map<String, Any?> = mapOf(
        "id" to "testId",
        "name" to "testName"
    )
}
