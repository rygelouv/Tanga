package app.books.tanga.data

import app.books.tanga.data.preferences.DefaultPrefDataStoreRepository
import app.books.tanga.data.user.UserRepository
import app.books.tanga.data.user.UserRepositoryImpl
import app.books.tanga.data.user.userCollection
import app.books.tanga.entity.UserId
import app.books.tanga.fakes.FakeFirestoreOperationHandler
import app.books.tanga.firestore.FirestoreOperationHandler
import app.books.tanga.fixtures.Fixtures
import app.books.tanga.session.SessionId
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserRepositoryTest {
    private val firestoreMock: FirebaseFirestore = mockk()
    private val prefDataStoreRepoMock: DefaultPrefDataStoreRepository = mockk()
    private val operationHandler: FirestoreOperationHandler = FakeFirestoreOperationHandler()
    private val firebaseAuthMock: FirebaseAuth = mockk()
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        userRepository = UserRepositoryImpl(
            firestoreMock,
            prefDataStoreRepoMock,
            operationHandler,
            firebaseAuthMock
        )
        setupFirestoreMocks()
    }

    private fun setupFirestoreMocks() {
        val mockSnapshot = mockk<DocumentSnapshot>()
        val mockDocumentReference = mockk<DocumentReference>()
        val mockCollectionReference = mockk<CollectionReference>()
        val mockTask = mockk<Task<DocumentSnapshot>>()
        val mockVoidTask = mockk<Task<Void>>()

        every { firestoreMock.userCollection } returns mockCollectionReference
        every { mockCollectionReference.document(any()) } returns mockDocumentReference
        every { mockDocumentReference.get() } returns mockTask
        every { mockDocumentReference.set(any()) } returns mockVoidTask
        every { mockDocumentReference.delete() } returns mockVoidTask

        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        coEvery { mockTask.await() } coAnswers { mockSnapshot }
        coEvery { mockVoidTask.await() } coAnswers { mockk<Void>() }
        coEvery { mockSnapshot.data } coAnswers { Fixtures.dummyUserFirestoreData }
    }

    @Test
    fun `getUser returns user when retrieval is successful`() = runTest {
        val user = Fixtures.dummyUser2

        every { prefDataStoreRepoMock.getSessionId() } returns flowOf(SessionId("someSessionId"))

        val result = userRepository.getUser()

        // Assertions for a successful retrieval
        assertTrue(result.isSuccess)
        assertEquals(user, result.getOrNull())
    }

    @Test
    fun `getUser returns null when there is no session ID`() = runTest {
        // Set up mocks for dependencies
        every { prefDataStoreRepoMock.getSessionId() } returns flowOf(null)

        val result = userRepository.getUser()

        // Assertion for a null user when there's no session ID
        assertTrue(result.isSuccess)
        assertNull(result.getOrNull())
    }

    @Test
    fun `getUserId returns the correct UserId when there is a session ID`() = runTest {
        // Set up mocks for dependencies
        every { prefDataStoreRepoMock.getSessionId() } returns flowOf(SessionId("someSessionId"))

        val result = userRepository.getUserId()

        // Assertion for the correct UserId when there's a session ID
        assertEquals(UserId("someSessionId"), result)
    }

    @Test
    fun `getUserId returns null when there is no session ID`() = runTest {
        // Set up mocks for dependencies
        every { prefDataStoreRepoMock.getSessionId() } returns flowOf(null)

        val result = userRepository.getUserId()

        // Assertion for null when there's no session ID
        assertNull(result)
    }

    @Test
    fun `createUser returns success when user creation is successful`() = runTest {
        // Create a user object to be created
        val user = Fixtures.dummyUser

        val result = userRepository.createUser(user)

        // Assertion for a successful user creation
        assertTrue(result.isSuccess)
    }

    @Test
    fun `deleteUser returns success when user deletion is successful`() = runTest {
        // Create a user object to be deleted
        val user = Fixtures.dummyUser

        // Set up mocks for dependencies
        // coEvery { operationHandler.executeOperation<Unit>(any()) } coAnswers { Result.success(Unit) }

        val result = userRepository.deleteUser(user)

        // Assertion for a successful user deletion
        assertTrue(result.isSuccess)
    }
}
