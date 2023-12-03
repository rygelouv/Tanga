package app.books.tanga.feature.auth

import app.books.tanga.data.user.UserRepository
import app.books.tanga.errors.DomainError
import app.books.tanga.fixtures.Fixtures
import app.books.tanga.session.SessionManager
import app.books.tanga.session.SessionState
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInCredential
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AuthenticationInteractorTest {

    private val userRepository: UserRepository = mockk(relaxed = true)
    private val sessionManager: SessionManager = mockk(relaxed = true)
    private val googleAuthService: GoogleAuthService = mockk(relaxed = true)
    private val anonymousAuthService: AnonymousAuthService = mockk(relaxed = true)

    private val interactor =
        AuthenticationInteractor(userRepository, sessionManager, googleAuthService, anonymousAuthService)

    @Test
    fun `signInAnonymously should return user on success`() = runTest {
        val mockUser = Fixtures.dummyUser
        val mockAuthResult = AuthResult(mockUser, true)

        coEvery { anonymousAuthService.signInAnonymously() } returns mockAuthResult

        val result = interactor.signInAnonymously()

        Assertions.assertTrue(result.isSuccess)
        Assertions.assertEquals(mockUser, result.getOrNull())
    }

    @Test
    fun `signInAnonymously should handle failure`() = runTest {
        val exception = RuntimeException("Test Exception")
        coEvery { anonymousAuthService.signInAnonymously() } throws exception

        val result = interactor.signInAnonymously()

        Assertions.assertTrue(result.isFailure)
        Assertions.assertEquals(DomainError.AuthenticationError(exception), result.exceptionOrNull())
    }

    @Test
    fun `initGoogleSignIn should return success result on successful initiation`() = runTest {
        val mockSignInResult = mockk<BeginSignInResult>(relaxed = true)
        coEvery { googleAuthService.initSignIn() } returns mockSignInResult

        val result = interactor.initGoogleSignIn()

        Assertions.assertTrue(result.isSuccess)
        Assertions.assertEquals(mockSignInResult, result.getOrNull())
    }

    @Test
    fun `initGoogleSignIn should return failure on unsuccessful initiation`() = runTest {
        val exception = RuntimeException("Test Exception")
        coEvery { googleAuthService.initSignIn() } throws exception

        val result = interactor.initGoogleSignIn()

        Assertions.assertTrue(result.isFailure)
        Assertions.assertEquals(
            DomainError.UnableToSignInWithGoogleError(exception),
            result.exceptionOrNull()
        )
    }

    @Test
    fun `completeGoogleSignIn should handle successful sign in`() = runTest {
        val mockCredentials = mockk<SignInCredential>(relaxed = true)
        val mockUser = Fixtures.dummyUser
        val mockAuthResult = AuthResult(mockUser, true)

        coEvery { googleAuthService.completeSignIn(any(), any()) } returns mockAuthResult

        val result = interactor.completeGoogleSignIn(mockCredentials)

        Assertions.assertTrue(result.isSuccess)
        Assertions.assertEquals(mockUser, result.getOrNull())
    }

    @Test
    fun `completeGoogleSignIn should handle failure`() = runTest {
        val mockCredentials = mockk<SignInCredential>(relaxed = true)
        val exception = RuntimeException("Test Exception")
        coEvery { googleAuthService.completeSignIn(any(), any()) } throws exception

        val result = interactor.completeGoogleSignIn(mockCredentials)

        Assertions.assertTrue(result.isFailure)
        Assertions.assertEquals(
            DomainError.UnableToSignInWithGoogleError(exception),
            result.exceptionOrNull()
        )
    }

    @Test
    fun `signOut should successfully sign out`() = runTest {
        coEvery { googleAuthService.signOut() } just Runs
        coEvery { sessionManager.closeSession() } just Runs

        val result = interactor.signOut()

        Assertions.assertTrue(result.isSuccess)
        Assertions.assertEquals(SessionState.SignedOut, result.getOrNull())
    }

    @Test
    fun `deleteUser should successfully delete user and sign out`() = runTest {
        val mockUser = Fixtures.dummyUser

        coEvery { userRepository.deleteUser(mockUser) } returns Result.success(Unit)
        coEvery { sessionManager.closeSession() } just Runs
        coEvery { googleAuthService.signOut() } just Runs

        val result = interactor.deleteUser(mockUser)

        Assertions.assertTrue(result.isSuccess)
        Assertions.assertEquals(SessionState.SignedOut, result.getOrNull())
    }
}
