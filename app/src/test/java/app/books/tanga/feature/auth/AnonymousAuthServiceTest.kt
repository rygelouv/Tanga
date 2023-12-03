package app.books.tanga.feature.auth

import app.books.tanga.data.user.toAnonymousUser
import app.books.tanga.fixtures.Fixtures
import app.books.tanga.rule.MainCoroutineDispatcherExtension
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserInfo
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainCoroutineDispatcherExtension::class)
class AnonymousAuthServiceTest {

    private lateinit var auth: FirebaseAuth
    private lateinit var anonymousAuthService: AnonymousAuthServiceImpl

    @BeforeEach
    fun setup() {
        auth = mockk()
        anonymousAuthService = AnonymousAuthServiceImpl(auth)
    }

    @Test
    fun `signInAnonymously should return valid AuthResult on success`() = runTest {
        val mockTask = mockk<Task<FirebaseAuthResult>>(relaxed = true)
        val mockAuthResult = mockk<FirebaseAuthResult>()
        val mockFirebaseUser = mockk<FirebaseUser>()
        mockkStatic("app.books.tanga.data.user.UserMapperKt")
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")

        coEvery { auth.signInAnonymously() } returns mockTask
        coEvery { mockFirebaseUser.toAnonymousUser() } returns Fixtures.dummyUser
        coEvery { mockTask.await() } returns mockAuthResult
        coEvery { mockAuthResult.user } returns mockFirebaseUser
        coEvery { mockAuthResult.additionalUserInfo?.isNewUser } returns true

        val result = anonymousAuthService.signInAnonymously()

        Assertions.assertNotNull(result.user)
        Assertions.assertEquals(Fixtures.dummyUser, result.user)
    }

    @Test
    fun `signInAnonymously should throw Throwable when user is null`() = runTest {
        val mockAuthResult = mockk<FirebaseAuthResult>(relaxed = true)
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")

        coEvery { auth.signInAnonymously().await() } returns mockAuthResult
        coEvery { mockAuthResult.user } returns null

        assertThrows<Throwable> {
            anonymousAuthService.signInAnonymously()
        }
    }

    @Test
    fun `linkAnonymousAccountToGoogleAccount should return valid AuthResult on success`() = runTest {
        val mockAuthCredential = mockk<AuthCredential>()
        val mockFirebaseUser = mockk<FirebaseUser>()
        val mockAuthResult = mockk<FirebaseAuthResult>()
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        val userInfo: UserInfo = mockk()
        val mockUser = Fixtures.dummyUser2
        val creationTimeStamp = mockUser.createdAt.time

        every { auth.currentUser } returns mockFirebaseUser
        every { mockAuthResult.user } returns mockFirebaseUser
        every { mockFirebaseUser.uid } returns mockUser.id.value
        every { mockFirebaseUser.email } returns mockUser.email
        every { mockFirebaseUser.metadata?.creationTimestamp } returns creationTimeStamp
        every { mockFirebaseUser.providerData } returns listOf(userInfo)
        every { userInfo.displayName } returns mockUser.fullName
        every { userInfo.photoUrl.toString() } returns mockUser.photoUrl!!
        coEvery { mockFirebaseUser.linkWithCredential(mockAuthCredential).await() } returns mockAuthResult

        val result = anonymousAuthService.linkAnonymousAccountToGoogleAccount(mockAuthCredential)

        Assertions.assertNotNull(result.user)
        Assertions.assertTrue(result.isNewUser)
        Assertions.assertEquals(mockUser, result.user)
    }

    @Test
    fun `linkAnonymousAccountToGoogleAccount should throw Throwable when currentUser is null`() = runTest {
        val mockAuthCredential = mockk<AuthCredential>()

        coEvery { auth.currentUser } returns null

        assertThrows<Throwable> {
            anonymousAuthService.linkAnonymousAccountToGoogleAccount(mockAuthCredential)
        }
    }

    @Test
    fun `createUserFromAnonymousLinkResult should handle null displayName correctly`() = runTest {
        val mockAuthCredential = mockk<AuthCredential>()
        val mockCurrentUser = mockk<FirebaseUser>(relaxed = true)
        val mockAuthResult = mockk<FirebaseAuthResult>(relaxed = true)
        val mockFirebaseUser = mockk<FirebaseUser>(relaxed = true)
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        val providerData = listOf(mockk<UserInfo>().apply { every { displayName } returns null })

        coEvery { auth.currentUser } returns mockCurrentUser
        coEvery { mockCurrentUser.linkWithCredential(mockAuthCredential).await() } returns mockAuthResult
        coEvery { mockAuthResult.user } returns mockFirebaseUser
        coEvery { auth.currentUser?.providerData } returns providerData

        assertThrows<Throwable> {
            anonymousAuthService.linkAnonymousAccountToGoogleAccount(mockAuthCredential)
        }
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }
}
