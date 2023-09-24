package app.books.tanga.data.user

import app.books.tanga.data.FirestoreData
import app.books.tanga.data.FirestoreDatabase
import app.books.tanga.entity.User
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
    FirestoreDatabase.Users.Fields.UID to uid,
    FirestoreDatabase.Users.Fields.FULL_NAME to fullName,
    FirestoreDatabase.Users.Fields.EMAIL to email,
    FirestoreDatabase.Users.Fields.PHOTO_URL to photoUrl,
    FirestoreDatabase.Users.Fields.CREATED_AT to FieldValue.serverTimestamp()
)

fun FirestoreData.toUser(uid: String) = User(
    uid = uid,
    fullName = this[FirestoreDatabase.Users.Fields.FULL_NAME].toString(),
    email = this[FirestoreDatabase.Users.Fields.EMAIL].toString(),
    photoUrl = this[FirestoreDatabase.Users.Fields.PHOTO_URL].toString(),
    isPro = false
)