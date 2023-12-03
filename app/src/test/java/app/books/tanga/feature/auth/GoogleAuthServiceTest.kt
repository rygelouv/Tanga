package app.books.tanga.feature.auth

import app.books.tanga.entity.User
import app.books.tanga.entity.UserId
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import java.util.Date
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GoogleAuthServiceTest {

    private val auth: FirebaseAuth = mockk(relaxed = true)
    private val signInClient: SignInClient = mockk(relaxed = true)
    private val signInRequest: BeginSignInRequest = mockk(relaxed = true)
    private val signUpRequest: BeginSignInRequest = mockk(relaxed = true)

    private val googleAuthService = GoogleAuthServiceImpl(auth, signInClient, signInRequest, signUpRequest)

    @BeforeEach
    fun setUp() {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
    }

    @Test
    fun `initSignIn should return result on successful signInRequest`() = runTest {
        val mockResult = mockk<BeginSignInResult>(relaxed = true)
        coEvery { signInClient.beginSignIn(signInRequest).await() } returns mockResult

        val result = googleAuthService.initSignIn()

        Assertions.assertEquals(mockResult, result)
    }

    @Test
    fun `initSignIn should retry with signUpRequest on signInRequest failure`() = runTest {
        val mockResult = mockk<BeginSignInResult>(relaxed = true)
        coEvery { signInClient.beginSignIn(signInRequest).await() } throws RuntimeException("Test Exception")
        coEvery { signInClient.beginSignIn(signUpRequest).await() } returns mockResult

        val result = googleAuthService.initSignIn()

        Assertions.assertEquals(mockResult, result)
    }

    @Test
    fun `completeSignIn should link anonymous account when current user is anonymous`() = runTest {
        val mockCredentials = mockk<SignInCredential>(relaxed = true)
        val mockAuthResult = mockk<AuthResult>(relaxed = true)
        val mockUser = mockk<FirebaseUser>(relaxed = true)
        every { auth.currentUser } returns mockUser
        every { mockUser.isAnonymous } returns true
        every { mockCredentials.googleIdToken } returns "some google id token"

        val linkAnonymousAccount: suspend (AuthCredential) -> AuthResult = { mockAuthResult }

        val result = googleAuthService.completeSignIn(mockCredentials, linkAnonymousAccount)

        Assertions.assertEquals(mockAuthResult, result)
    }

    @Test
    fun `completeSignIn should sign in with firebase and return AuthResult on non-anonymous user`() = runTest {
        val mockCredentials = mockk<SignInCredential>(relaxed = true)
        val mockFirebaseAuthResult = mockk<FirebaseAuthResult>(relaxed = true)
        val mockUser = mockk<FirebaseUser>(relaxed = true)
        val mockAuthResultForAnonymous = mockk<AuthResult>()

        every { auth.currentUser } returns mockUser
        every { mockUser.isAnonymous } returns false
        with(mockUser) {
            every { uid } returns "someSessionId"
            every { displayName } returns "Mansa Musa"
            every { email } returns "some@mail.com"
            every { photoUrl.toString() } returns "https://example.com/johndoe.jpg"
            every { metadata?.creationTimestamp } returns 1697806449
        }

        val mockAuthResult = AuthResult(
            User(
                id = UserId("someSessionId"),
                fullName = "Mansa Musa",
                email = "some@mail.com",
                photoUrl = "https://example.com/johndoe.jpg",
                createdAt = Date(1697806449),
                isPro = false
            ),
            false
        )

        every { mockCredentials.googleIdToken } returns "some google id token"
        coEvery { auth.signInWithCredential(any()).await() } returns mockFirebaseAuthResult

        val result = googleAuthService.completeSignIn(mockCredentials) { mockAuthResultForAnonymous }

        Assertions.assertEquals(mockAuthResult, result)
    }

    @Test
    fun `signOut should sign out from both signInClient and FirebaseAuth`() = runTest {
        val mockVoidTask = mockk<Void>()

        coEvery { signInClient.signOut().await() } returns mockVoidTask
        coEvery { auth.signOut() } just Runs

        googleAuthService.signOut()

        coVerify(exactly = 1) { signInClient.signOut().await() }
        verify(exactly = 1) { auth.signOut() }
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }
}
