package app.books.tanga.data.favorite

import android.util.Log
import app.books.tanga.data.FirestoreDatabase
import app.books.tanga.entity.Favorite
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface FavoriteRepository {
    /**
     * Create a new favorite
     */
    suspend fun createFavorite(favorite: Favorite): Result<Unit>

    /**
     * Delete a favorite
     */
    suspend fun deleteFavorite(favorite: Favorite): Result<Unit>

    /**
     * Get all favorites for a given user
     */
    suspend fun getFavorites(userId: String): Result<List<Favorite>>

    /**
     * Get a favorite by its summary id
     */
    suspend fun getFavoriteBySummaryId(summaryId: String, userId: String): Result<Favorite?>

    /**
     * Get a stream of favorites for a given user
     */
    suspend fun getFavoritesStream(userId: String): Flow<List<Favorite>>
}

/**
 * Uses firestore as the remote data source and an in-memory cache as the local data source
 * Cache is used as single source of truth
 */
class FavoriteRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val cache: FavoriteInMemoryCache
) : FavoriteRepository {

    /**
     * Create the favorite on Firestore, get the [DocumentReference] and update the field
     * [Favorite.uid] with the [DocumentReference.id]
     * Then update the cache
     */
    override suspend fun createFavorite(favorite: Favorite): Result<Unit> {
        return runCatching {
            val documentReference = firestore.favoriteCollection.add(favorite).await()
            firestore.favoriteCollection.document(documentReference.id)
                .update(FirestoreDatabase.Favorites.Fields.UID, documentReference.id).await()
            // Update cache
            cache.add(favorite = favorite.copy(uid = documentReference.id))
        }.onFailure {
            Result.failure<Throwable>(it)
        }
    }

    /**
     * Delete the favorite from Firestore and update the cache
     */
    override suspend fun deleteFavorite(favorite: Favorite): Result<Unit> {
        return runCatching {
            firestore.favoriteCollection.document(favorite.uid).delete().await()
            // Update cache
            cache.remove(favorite = favorite)
        }.onFailure {
            Log.e("FavoriteRepositoryImpl", "Error deleting favorite", it)
            Result.failure<Throwable>(it)
        }
    }

    /**
     * Get the favorite from the cache if it exists, otherwise get it from Firestore
     */
    override suspend fun getFavoriteBySummaryId(
        summaryId: String,
        userId: String
    ): Result<Favorite?> {
        return runCatching {
            if (cache.isEmpty()) {
                getFavoriteBySummaryIdFromFirestore(
                    summaryId = summaryId,
                    userId = userId
                ).getOrThrow()
            } else {
                cache.getBySummaryId(summaryId)
            }
        }.onFailure {
            Result.failure<Throwable>(it)
        }
    }

    /**
     * Get a stream of favorites for a given user
     * First get the favorites from the cache, then get the favorites from Firestore
     */
    override suspend fun getFavoritesStream(userId: String): Flow<List<Favorite>> {
        val cacheStream =  flowOf(getFavoritesFromCache())
        val firestoreStream = firestore.favoriteCollection.whereEqualTo(
            FirestoreDatabase.Favorites.Fields.USER_ID,
            userId
        ).snapshots().map { querySnapshot ->
            querySnapshot.map { it.data.toFavorite() }.also { favorites ->
                cache.putAll(favorites)
            }
        }
        return merge(cacheStream, firestoreStream)
    }

    /**
     * Get the favorite from Firestore by its summary id
     */
    private suspend fun getFavoriteBySummaryIdFromFirestore(
        summaryId: String,
        userId: String
    ): Result<Favorite?> {
        return runCatching {
            val favorite = firestore.favoriteCollection
                .whereEqualTo(FirestoreDatabase.Favorites.Fields.SUMMARY_ID, summaryId)
                .whereEqualTo(FirestoreDatabase.Favorites.Fields.USER_ID, userId)
                .get()
                .await()
                .firstOrNull()
            favorite?.data?.toFavorite()
        }.onFailure {
            Result.failure<Throwable>(it)
        }
    }

    /**
     * Get the favorites from the cache if it is not empty, otherwise get them from Firestore
     */
    override suspend fun getFavorites(userId: String): Result<List<Favorite>> {
        return if (cache.isEmpty()) {
            getFavoritesFromFirestore(userId)
        } else {
            Result.success(getFavoritesFromCache())
        }
    }

    /**
     * Get the favorites from Firestore for a user, then save them to the cache
     */
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

    private fun getFavoritesFromCache(): List<Favorite> {
        Log.d("FavoriteRepositoryImpl", "getFavoritesFromCache")
        return cache.getAll()
    }

    private val FirebaseFirestore.favoriteCollection
        get() = collection(FirestoreDatabase.Favorites.COLLECTION_NAME)
}