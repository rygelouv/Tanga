package app.books.tanga.data.user

import app.books.tanga.firestore.FirestoreDatabase
import app.books.tanga.data.preferences.DefaultPrefDataStoreRepository
import app.books.tanga.entity.OperationError
import app.books.tanga.entity.User
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface UserRepository {

    suspend fun getUser(): Result<User?>

    suspend fun createUser(user: User)

    suspend fun deleteUser(user: User)
}

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val prefDataStoreRepo: DefaultPrefDataStoreRepository
) : UserRepository {

    override suspend fun getUser(): Result<User?> {
        val sessionId = prefDataStoreRepo.getSessionId().first()
        return sessionId?.let {
            runCatching {
                val uid = sessionId.value
                val userDocument = firestore.userCollection.document(uid).get().await()
                val userDataMap = userDocument.data
                userDataMap?.toUser(uid)
            }.onFailure { Result.failure<Throwable>(it) }
        } ?: Result.failure(OperationError("Session Id not found in pref datastore"))
    }

    override suspend fun createUser(user: User) {
        val userMap = user.toFireStoreUserData()
        firestore.userCollection
            .document(user.id.value)
            .set(userMap).await()
    }

    override suspend fun deleteUser(user: User) {
        firestore
            .userCollection
            .document(user.id.value)
            .delete()
    }

    private val FirebaseFirestore.userCollection: CollectionReference
        get() = collection(FirestoreDatabase.Users.COLLECTION_NAME)
}