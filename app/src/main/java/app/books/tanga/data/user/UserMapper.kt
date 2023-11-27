package app.books.tanga.data.user

import app.books.tanga.entity.User
import app.books.tanga.entity.UserId
import app.books.tanga.firestore.FirestoreData
import app.books.tanga.firestore.FirestoreDatabase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import java.util.Date
import timber.log.Timber

fun FirebaseUser.toAnonymousUser(): User =
    User(
        id = UserId(uid),
        fullName = "Anonymous",
        email = "",
        photoUrl = null,
        isPro = false,
        isAnonymous = true,
        createdAt = metadata?.creationTimestamp?.let { Date(it) } ?: Date()
    )

fun FirebaseUser.toUser(): User {
    runCatching {
        Timber.e("user id ====> $uid")
        Timber.e("user email ====> $email")
        Timber.e("user displayName ====> $displayName")
        Timber.e("user photoUrl ====> $photoUrl")
        Timber.e("user metadata ====> $metadata")
        Timber.e("Create date ====> ${metadata?.creationTimestamp?.let { Date(it) } ?: Date()}")
    }.onFailure {
        Timber.e("Error ====> $it")
    }

    return User(
        id = UserId(uid),
        fullName = requireNotNull(displayName) { "User must have a display name" },
        email = requireNotNull(email) { "User must have an email" },
        photoUrl = photoUrl?.toString(),
        isPro = false,
        createdAt = metadata?.creationTimestamp?.let { Date(it) } ?: Date()
    )
}

fun User.toFireStoreUserData() =
    mapOf(
        FirestoreDatabase.Users.Fields.UID to id,
        FirestoreDatabase.Users.Fields.FULL_NAME to fullName,
        FirestoreDatabase.Users.Fields.EMAIL to email,
        FirestoreDatabase.Users.Fields.PHOTO_URL to photoUrl,
        // We use the server timestamp to avoid issues with the device time
        FirestoreDatabase.Users.Fields.CREATED_AT to FieldValue.serverTimestamp()
    )

fun FirestoreData.toUser(uid: String) =
    User(
        id = UserId(uid),
        fullName = this[FirestoreDatabase.Users.Fields.FULL_NAME].toString(),
        email = this[FirestoreDatabase.Users.Fields.EMAIL].toString(),
        photoUrl = this[FirestoreDatabase.Users.Fields.PHOTO_URL].toString(),
        isPro = false,
        createdAt = (this[FirestoreDatabase.Users.Fields.CREATED_AT] as Timestamp).toDate()
    )
