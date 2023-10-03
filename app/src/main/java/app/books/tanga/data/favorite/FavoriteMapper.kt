package app.books.tanga.data.favorite

import app.books.tanga.firestore.FirestoreDatabase
import app.books.tanga.entity.Favorite
import app.books.tanga.entity.FavoriteId
import app.books.tanga.firestore.FirestoreData

fun FirestoreData.toFavorite(): Favorite {
    return Favorite(
        id = FavoriteId(this[FirestoreDatabase.Favorites.Fields.UID].toString()),
        title = this[FirestoreDatabase.Favorites.Fields.TITLE].toString(),
        author = this[FirestoreDatabase.Favorites.Fields.AUTHOR].toString(),
        coverUrl = this[FirestoreDatabase.Favorites.Fields.COVER_URL].toString(),
        userId = this[FirestoreDatabase.Favorites.Fields.USER_ID].toString(),
        summaryId = this[FirestoreDatabase.Favorites.Fields.SUMMARY_ID].toString(),
        playingLength = this[FirestoreDatabase.Favorites.Fields.PLAYING_LENGTH].toString()
    )
}