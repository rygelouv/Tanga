package app.books.tanga.session

import app.books.tanga.errors.AppError
import app.books.tanga.errors.FirestoreErrorFactory
import app.books.tanga.errors.OperationError
import app.books.tanga.firestore.FirestoreOperationHandlerImpl
import app.books.tanga.tools.InternetConnectivityMonitor
import com.google.firebase.firestore.FirebaseFirestoreException
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FirestoreOperationHandlerTest {

    private val internetConnectivityMonitor: InternetConnectivityMonitor = mockk()
    private lateinit var handler: FirestoreOperationHandlerImpl

    @BeforeEach
    fun setup() {
        handler = FirestoreOperationHandlerImpl(internetConnectivityMonitor)
    }

    @Test
    fun `executeOperation returns success when operation is successful`() = runTest {
        val result = handler.executeOperation { "Success" }

        assertTrue(result.isSuccess)
        assertEquals("Success", result.getOrNull())
    }

    @Test
    fun `executeOperation returns AppError when FirebaseFirestoreException occurs`() = runTest {
        val firebaseException: FirebaseFirestoreException = mockk()
        mockkObject(FirestoreErrorFactory)
        every {
            FirestoreErrorFactory.createError(any<FirebaseFirestoreException>())
        } returns OperationError.UnknownError(Exception())

        val result = handler.executeOperation {
            throw firebaseException
        }

        assertTrue(result.isFailure)
        // Assuming FirestoreErrorFactory.createError converts the exception into a FirestoreError type.
        assertTrue(result.exceptionOrNull() is AppError)
    }

    @Test
    fun `executeOperation returns NoInternetConnectionError when exception occurs and no internet`() = runTest {
        val genericException = Exception("Generic error")
        coEvery { internetConnectivityMonitor.isInternetAvailable } returns flowOf(false).stateIn(this)

        val result = handler.executeOperation {
            throw genericException
        }

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is OperationError.NoInternetConnectionError)
    }

    @Test
    fun `executeOperation returns UnknownError when exception occurs and internet is available`() = runTest {
        val genericException = Exception("Generic error")
        coEvery { internetConnectivityMonitor.isInternetAvailable } returns flowOf(true).stateIn(this)

        val result = handler.executeOperation {
            throw genericException
        }

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is OperationError.UnknownError)
    }

    companion object {
        @JvmStatic
        @AfterAll
        fun tearDown() {
            unmockkObject(FirestoreErrorFactory)
        }
    }
}
