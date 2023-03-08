package app.books.tanga.common.domain

import app.books.tanga.common.data.FirestoreDatabase
import app.books.tanga.common.data.toFireStoreUserData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface UserRepository {

    suspend fun getUser(): User

    suspend fun createUser(user: User)
}

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : UserRepository {

    override suspend fun getUser(): User {
        TODO("Not yet implemented")
    }

    override suspend fun createUser(user: User) {
        val userMap = user.toFireStoreUserData()
        firestore.collection(FirestoreDatabase.Users.COLLECTION_NAME)
            .document(user.uid)
            .set(userMap).await()
    }
}