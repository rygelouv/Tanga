package app.books.tanga.data.favorite

import app.books.tanga.entity.Favorite
import app.books.tanga.entity.FavoriteId
import app.books.tanga.firestore.FirestoreData
import app.books.tanga.firestore.FirestoreDatabase

fun FirestoreData.toFavorite(): Favorite = Favorite(
    id = FavoriteId(this[FirestoreDatabase.Favorites.Fields.UID].toString()),
    title = this[FirestoreDatabase.Favorites.Fields.TITLE].toString(),
    author = this[FirestoreDatabase.Favorites.Fields.AUTHOR].toString(),
    coverUrl = this[FirestoreDatabase.Favorites.Fields.COVER_URL].toString(),
    userId = this[FirestoreDatabase.Favorites.Fields.USER_ID].toString(),
    summaryId = this[FirestoreDatabase.Favorites.Fields.SUMMARY_ID].toString(),
    playingLength = this[FirestoreDatabase.Favorites.Fields.PLAYING_LENGTH].toString()
)

fun Favorite.toFirestoreData() = mapOf(
    // FirestoreDatabase.Favorites.Fields.UID to id, No needed, Firestore will generate it.
    FirestoreDatabase.Favorites.Fields.TITLE to title,
    FirestoreDatabase.Favorites.Fields.AUTHOR to author,
    FirestoreDatabase.Favorites.Fields.COVER_URL to coverUrl,
    FirestoreDatabase.Favorites.Fields.USER_ID to userId,
    FirestoreDatabase.Favorites.Fields.SUMMARY_ID to summaryId,
    FirestoreDatabase.Favorites.Fields.PLAYING_LENGTH to playingLength,
)
