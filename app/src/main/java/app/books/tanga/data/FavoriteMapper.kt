package app.books.tanga.data

import app.books.tanga.domain.favorites.Favorite

fun FirestoreData.toFavorite(): Favorite {
    return Favorite(
        uid = this[FirestoreDatabase.Favorites.Fields.UID].toString(),
        title = this[FirestoreDatabase.Favorites.Fields.TITLE].toString(),
        author = this[FirestoreDatabase.Favorites.Fields.AUTHOR].toString(),
        coverUrl = this[FirestoreDatabase.Favorites.Fields.COVER_URL].toString(),
        userId = this[FirestoreDatabase.Favorites.Fields.USER_ID].toString(),
        summaryId = this[FirestoreDatabase.Favorites.Fields.SUMMARY_ID].toString(),
        playingLength = this[FirestoreDatabase.Favorites.Fields.PLAYING_LENGTH].toString()
    )
}