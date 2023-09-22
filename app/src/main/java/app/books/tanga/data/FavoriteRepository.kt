package app.books.tanga.data

import app.books.tanga.domain.favorites.Favorite
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface FavoriteRepository {
    suspend fun createFavorite(favorite: Favorite): Result<Unit>

    suspend fun deleteFavorite(favorite: Favorite): Result<Unit>

    suspend fun getFavorite(favoriteId: String): Result<Favorite?>

    suspend fun getFavorites(userId: String): Result<List<Favorite>>

    suspend fun getFavoritesFromCache(): List<Favorite>

    suspend fun getFavoriteFromCacheBySummerId(summaryId: String): Favorite?
}

class FavoriteRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val cache: FavoriteInMemoryCache
) : FavoriteRepository {
    override suspend fun createFavorite(favorite: Favorite): Result<Unit> {
        return runCatching {
            val documentReference = firestore.favoriteCollection.add(favorite).await()
            firestore.favoriteCollection.document(documentReference.id)
                .update(FirestoreDatabase.Favorites.Fields.UID, documentReference.id).await()
            Unit
        }.onFailure {
            Result.failure<Throwable>(it)
        }
    }

    override suspend fun deleteFavorite(favorite: Favorite): Result<Unit> {
        return runCatching {
            firestore.favoriteCollection.document(favorite.uid).delete().await()
            Unit
        }.onFailure {
            Result.failure<Throwable>(it)
        }
    }

    override suspend fun getFavorite(favoriteId: String): Result<Favorite?> {
        return runCatching {
            val favorite = firestore.favoriteCollection.document(favoriteId).get().await()
            favorite.data?.toFavorite()
        }.onFailure {
            Result.failure<Throwable>(it)
        }
    }

    override suspend fun getFavorites(userId: String): Result<List<Favorite>> {
        return if (cache.isEmpty()) {
            getFavoritesFromFirestore(userId)
        } else {
            Result.success(getFavoritesFromCache())
        }
    }

    private suspend fun getFavoritesFromFirestore(userId: String): Result<List<Favorite>> {
        return runCatching {
            val favorites = firestore.favoriteCollection.whereEqualTo(
                FirestoreDatabase.Favorites.Fields.USER_ID,
                userId
            ).get().await()
            favorites.map {
                it.data.toFavorite()
            }.also { cache.putAll(it) } // Save to cache
        }.onFailure {
            Result.failure<Throwable>(it)
        }
    }

    override suspend fun getFavoritesFromCache(): List<Favorite> {
        return cache.getAll()
    }

    override suspend fun getFavoriteFromCacheBySummerId(summaryId: String): Favorite? {
        return cache.getBySummaryId(summaryId)
    }

    private val FirebaseFirestore.favoriteCollection
        get() = collection(FirestoreDatabase.Favorites.COLLECTION_NAME)
}