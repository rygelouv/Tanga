package app.books.tanga.feature.auth

import app.books.tanga.data.user.UserRepository
import app.books.tanga.entity.User
import app.books.tanga.errors.DomainError
import app.books.tanga.session.SessionId
import app.books.tanga.session.SessionManager
import app.books.tanga.session.SessionState
import app.books.tanga.utils.resultOf
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInCredential
import javax.inject.Inject

/**
 * Represents the result of a sign-in operation.
 */
data class AuthResult(
    val user: User,
    val isNewUser: Boolean
)

class AuthenticationInteractor @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager,
    private val googleAuthService: GoogleAuthService
) {

    /**
     * Initializes the Google sign-in process.
     */
    suspend fun initGoogleSignIn(): Result<BeginSignInResult> {
        val result = resultOf {
            googleAuthService.initSignIn()
        }

        return if (result.isSuccess) {
            result
        } else {
            Result.failure(DomainError.UnableToSignInWithGoogleError(result.exceptionOrNull()))
        }
    }

    /**
     * Completes the Google sign-in process.
     *
     * Create the user in the database if it doesn't exist.
     * Then Open a new session for the user.
     */
    suspend fun completeGoogleSignIn(credentials: SignInCredential): Result<SessionState> {
        return resultOf {
            val authResult = googleAuthService.completeSignIn(credentials)
            if (authResult.isNewUser) {
                userRepository.createUser(user = authResult.user)
            }
            authResult.user.id.let {
                val sessionId = SessionId(it.value)
                sessionManager.openSession(sessionId)
                SessionState.SignedIn(sessionId)
            }
        }.onFailure {
            return Result.failure(DomainError.UnableToSignInWithGoogleError(it))
        }
    }

    suspend fun signOut(): Result<SessionState> {
        return resultOf {
            googleAuthService.signOut()
            sessionManager.closeSession()
            SessionState.SignedOut
        }
    }

    suspend fun deleteUser(user: User): Result<SessionState> {
        return resultOf {
            signOut()
            userRepository.deleteUser(user)
            SessionState.SignedOut
        }
    }
}