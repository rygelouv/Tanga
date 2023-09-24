package app.books.tanga.feature.library

import app.books.tanga.common.ui.ProgressState
import app.books.tanga.domain.favorites.Favorite

data class LibraryUiState(
    val progressState: ProgressState = ProgressState.Hide,
    val favorites: List<FavoriteUi>? = null
)

data class FavoriteUi(
    val uid: String,
    val summaryId: String,
    val title: String,
    val author: String,
    val coverUrl: String,
    val playingLength: String
)

fun Favorite.toFavoriteUi(): FavoriteUi {
    return FavoriteUi(
        uid = uid,
        summaryId = summaryId,
        title = title,
        author = author,
        coverUrl = coverUrl,
        playingLength = playingLength
    )
}