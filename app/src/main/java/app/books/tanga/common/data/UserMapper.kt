package app.books.tanga.common.data

import app.books.tanga.common.domain.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue

fun FirebaseUser.toUser(): User = User(
    uid = uid,
    fullName = requireNotNull(displayName),
    email = requireNotNull(email),
    photoUrl = photoUrl?.toString(),
    isPro = false
)

fun User.toFireStoreUserData() = mapOf(
    FirestoreDatabase.Users.Fields.DISPLAY_NAME to fullName,
    FirestoreDatabase.Users.Fields.EMAIL to email,
    FirestoreDatabase.Users.Fields.PHOTO_URL to photoUrl,
    FirestoreDatabase.Users.Fields.CREATED_AT to FieldValue.serverTimestamp()
)