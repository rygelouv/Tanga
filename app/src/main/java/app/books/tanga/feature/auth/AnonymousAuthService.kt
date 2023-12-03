package app.books.tanga.feature.auth

import app.books.tanga.data.user.toAnonymousUser
import app.books.tanga.entity.User
import app.books.tanga.entity.UserId
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

typealias FirebaseAuthResult = com.google.firebase.auth.AuthResult

interface AnonymousAuthService {

    suspend fun signInAnonymously(): AuthResult

    suspend fun linkAnonymousAccountToGoogleAccount(authCredential: AuthCredential): AuthResult
}

class AnonymousAuthServiceImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AnonymousAuthService {

    @Suppress("TooGenericExceptionThrown")
    override suspend fun signInAnonymously(): AuthResult {
        val result = auth.signInAnonymously().await()
        val firebaseUser = result.user ?: throw Throwable("Failed to sign in anonymously")

        val user = firebaseUser.toAnonymousUser()

        return AuthResult(
            user = user,
            isNewUser = result.additionalUserInfo?.isNewUser == true
        )
    }

    @Suppress("TooGenericExceptionThrown")
    override suspend fun linkAnonymousAccountToGoogleAccount(authCredential: AuthCredential): AuthResult {
        val currentUser =
            auth.currentUser ?: throw Throwable("No anonymous user to link to Google account")
        val result = currentUser.linkWithCredential(authCredential).await()

        return AuthResult(
            user = createUserFromAnonymousLinkResult(result = result),
            isNewUser = true
        )
    }

    /**
     * When linking an anonymous account to permanent account, the display name and photo URL are not set,
     * so we need to get them from the provider data.
     */
    @Suppress("TooGenericExceptionThrown")
    private fun createUserFromAnonymousLinkResult(result: FirebaseAuthResult): User {
        val firebaseUser = result.user ?: throw Throwable("Failed to link anonymous account to Google account")

        val displayName = auth.currentUser?.providerData?.firstOrNull { it.displayName != null }?.displayName
        val email = firebaseUser.email
        val photoUrl = auth.currentUser?.providerData?.firstOrNull { it.photoUrl != null }?.photoUrl?.toString()
        val creationDate = firebaseUser.metadata?.creationTimestamp?.let { Date(it) } ?: Date()

        return User(
            id = UserId(firebaseUser.uid),
            fullName = requireNotNull(displayName) { "User must have a display name" },
            email = requireNotNull(email) { "User must have an email" },
            photoUrl = photoUrl,
            isPro = false,
            isAnonymous = false,
            createdAt = creationDate
        )
    }
}
