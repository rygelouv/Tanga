package app.books.tanga.common.data

import app.books.tanga.common.data.preferences.DefaultPrefDataStoreRepository
import app.books.tanga.common.domain.OperationError
import app.books.tanga.common.domain.user.User
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
                val userDocument = firestore.userCollection.document(it.value).get().await()
                userDocument.toObject(User::class.java)
            }.onFailure { Result.failure<Throwable>(it) }
        } ?: Result.failure(OperationError("Session Id not found in pref datastore"))
    }

    override suspend fun createUser(user: User) {
        val userMap = user.toFireStoreUserData()
        firestore.userCollection
            .document(user.uid)
            .set(userMap).await()
    }

    override suspend fun deleteUser(user: User) {
        firestore
            .userCollection
            .document(user.uid)
            .delete()
    }

    private val FirebaseFirestore.userCollection: CollectionReference
        get() = collection(FirestoreDatabase.Users.COLLECTION_NAME)
}