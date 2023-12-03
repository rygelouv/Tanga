package app.books.tanga.feature.auth

import app.books.tanga.data.user.toUser
import app.books.tanga.di.GoogleSignInModule
import app.books.tanga.errors.DomainError.UnableToSignInWithGoogleError
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.tasks.await
import timber.log.Timber

/**
 * An interface for Google sign-in operations.
 */
interface GoogleAuthService {
    suspend fun initSignIn(): BeginSignInResult

    suspend fun completeSignIn(
        credentials: SignInCredential,
        linkAnonymousAccount: suspend (AuthCredential) -> AuthResult
    ): AuthResult

    suspend fun signOut()
}

class GoogleAuthServiceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val signInClient: SignInClient,
    @Named(GoogleSignInModule.GOOGLE_SIGN_IN_REQUEST)
    private var signInRequest: BeginSignInRequest,
    @Named(GoogleSignInModule.GOOGLE_SIGN_UP_REQUEST)
    private var signUpRequest: BeginSignInRequest
) : GoogleAuthService {
    /**
     * If initialization of Sign in request fails, then try sign up request
     */
    override suspend fun initSignIn(): BeginSignInResult =
        try {
            signInClient.beginSignIn(signInRequest).await()
        } catch (e: Exception) {
            Timber.e("Unable to get sign in result. Attempt sign up", e)
            logGoogleError(e)
            signInClient.beginSignIn(signUpRequest).await()
        }

    /**
     * Use the Google credentials to sign-in with Firebase Auth.
     * Convert the Firebase user to our own User model and check if user is new.
     */
    @Throws(UnableToSignInWithGoogleError::class)
    override suspend fun completeSignIn(
        credentials: SignInCredential,
        linkAnonymousAccount: suspend (AuthCredential) -> AuthResult
    ): AuthResult {
        // Get Google credentials
        val googleCredentials =
            GoogleAuthProvider.getCredential(credentials.googleIdToken, null)

        if (auth.currentUser?.isAnonymous == true) {
            return linkAnonymousAccount(googleCredentials)
        }

        // Sign-in with firebase auth
        val firebaseAuthResult = auth.signInWithCredential(googleCredentials).await()
        val isNewUser = firebaseAuthResult.additionalUserInfo?.isNewUser == true

        val user = auth.currentUser?.toUser() ?: throw UnableToSignInWithGoogleError()

        return AuthResult(
            user = user,
            isNewUser = isNewUser
        )
    }

    override suspend fun signOut() {
        signInClient.signOut().await()
        auth.signOut()
    }

    private fun logGoogleError(error: Throwable) {
        if (error is ApiException) {
            Timber.d(
                "Google ApiException with code ${error.statusCode} and message ${error.message}",
                error
            )
        }
    }
}
