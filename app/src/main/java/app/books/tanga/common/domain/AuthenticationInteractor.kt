package app.books.tanga.common.domain

import app.books.tanga.common.data.toUser
import app.books.tanga.common.di.GoogleSignInModule.Companion.GOOGLE_SIGN_IN_REQUEST
import app.books.tanga.common.di.GoogleSignInModule.Companion.GOOGLE_SIGN_UP_REQUEST
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

enum class SessionStatus {
    SIGNED_IN,
    SIGNED_OUT
}

interface AuthenticationInteractor {

    suspend fun initGoogleSignIn(): Result<BeginSignInResult>

    suspend fun launchGoogleSignIn(credentials: SignInCredential): Result<SessionStatus>
}

class AuthenticationInteractorImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val auth: FirebaseAuth,
    private val signInClient: SignInClient,
    @Named(GOOGLE_SIGN_IN_REQUEST)
    private var signInRequest: BeginSignInRequest,
    @Named(GOOGLE_SIGN_UP_REQUEST)
    private var signUpRequest: BeginSignInRequest,
) : AuthenticationInteractor {

    override suspend fun initGoogleSignIn(): Result<BeginSignInResult> {
        return runCatching {
            signInClient.beginSignIn(signInRequest).await()
        }.onFailure {
            println("Unable to get sign in result. Attempt sign up")
            runCatching {
                signInClient.beginSignIn(signUpRequest).await()
            }.onFailure { failure ->
                println("Unable to get sign up result")
                // TODO do proper error creation
                Result.failure<Throwable>(failure)
            }
        }
    }

    override suspend fun launchGoogleSignIn(credentials: SignInCredential): Result<SessionStatus> {
        return runCatching {
            val googleCredentials = GoogleAuthProvider.getCredential(credentials.googleIdToken, null)
            val authResult = auth.signInWithCredential(googleCredentials).await()
            val isNewUser = authResult.additionalUserInfo?.isNewUser == true
            if (isNewUser) {
                auth.currentUser?.let { firebaseUser ->
                    userRepository.createUser(user = firebaseUser.toUser())
                }
            }
            SessionStatus.SIGNED_IN
        }.onFailure {
            println("Unable to authenticate with Firebase auth using Google credentials")
            // TODO do proper error creation
            Result.failure<Throwable>(it)
        }
    }

}