package app.books.tanga.data.user

import app.books.tanga.entity.User
import app.books.tanga.entity.UserId
import app.books.tanga.firestore.FirestoreData
import app.books.tanga.firestore.FirestoreDatabase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import java.util.Date

fun FirebaseUser.toUser(): User =
    User(
        id = UserId(uid),
        fullName = requireNotNull(displayName),
        email = requireNotNull(email),
        photoUrl = photoUrl?.toString(),
        isPro = false,
        createdAt = metadata?.creationTimestamp?.let { Date(it) } ?: Date(),
    )

fun User.toFireStoreUserData() =
    mapOf(
        FirestoreDatabase.Users.Fields.UID to id,
        FirestoreDatabase.Users.Fields.FULL_NAME to fullName,
        FirestoreDatabase.Users.Fields.EMAIL to email,
        FirestoreDatabase.Users.Fields.PHOTO_URL to photoUrl,
        FirestoreDatabase.Users.Fields.CREATED_AT to
            FieldValue.serverTimestamp(), // We use the server timestamp to avoid issues with the device time
    )

fun FirestoreData.toUser(uid: String) =
    User(
        id = UserId(uid),
        fullName = this[FirestoreDatabase.Users.Fields.FULL_NAME].toString(),
        email = this[FirestoreDatabase.Users.Fields.EMAIL].toString(),
        photoUrl = this[FirestoreDatabase.Users.Fields.PHOTO_URL].toString(),
        isPro = false,
        createdAt = (this[FirestoreDatabase.Users.Fields.CREATED_AT] as Timestamp).toDate(),
    )
