package app.books.tanga.domain.favorites


data class Favorite(
    val uid: String,
    val title: String,
    val author: String,
    val coverUrl: String,
    val userId: String,
    val summaryId: String,
    val playingLength: String
)