package app.books.tanga.data.user

import app.books.tanga.data.preferences.DefaultPrefDataStoreRepository
import app.books.tanga.entity.User
import app.books.tanga.entity.UserId
import app.books.tanga.firestore.FirestoreDatabase
import app.books.tanga.firestore.FirestoreOperationHandler
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await

interface UserRepository {
    suspend fun getUser(): Result<User?>

    suspend fun getUserId(): UserId?

    suspend fun createUser(user: User): Result<Unit>

    suspend fun deleteUser(user: User): Result<Unit>
}

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val prefDataStoreRepo: DefaultPrefDataStoreRepository,
    private val operationHandler: FirestoreOperationHandler
) : UserRepository {
    override suspend fun getUser(): Result<User?> {
        val sessionId = prefDataStoreRepo.getSessionId().first() ?: return Result.success(null)
        return operationHandler.executeOperation {
            val uid = sessionId.value
            val userDocument =
                firestore
                    .userCollection
                    .document(uid)
                    .get()
                    .await()
            val userDataMap = userDocument.data
            userDataMap?.toUser(uid)
        }
    }

    override suspend fun getUserId(): UserId? {
        val sessionId = prefDataStoreRepo.getSessionId().first()
        return sessionId?.value?.let { UserId(it) }
    }

    override suspend fun createUser(user: User): Result<Unit> {
        val userMap = user.toFireStoreUserData()
        return operationHandler.executeOperation {
            firestore
                .userCollection
                .document(user.id.value)
                .set(userMap)
                .await()
        }
    }

    override suspend fun deleteUser(user: User): Result<Unit> =
        operationHandler.executeOperation {
            firestore
                .userCollection
                .document(user.id.value)
                .delete()
        }

    private val FirebaseFirestore.userCollection: CollectionReference
        get() = collection(FirestoreDatabase.Users.COLLECTION_NAME)
}
