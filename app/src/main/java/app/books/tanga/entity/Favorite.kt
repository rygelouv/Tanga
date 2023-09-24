package app.books.tanga.entity

/**
 * A favorite is a summary that the user has saved in his library.
 * @param uid the unique identifier of the favorite
 * @param title the title of the summary
 * @param author the author of the summary
 * @param coverUrl the url of the summary cover image
 * @param userId the id of the user who saved the summary
 * @param summaryId the id of the summary
 * @param playingLength the length of the summary audio
 */
data class Favorite(
    val uid: String,
    val title: String,
    val author: String,
    val coverUrl: String,
    val userId: String,
    val summaryId: String,
    val playingLength: String
)