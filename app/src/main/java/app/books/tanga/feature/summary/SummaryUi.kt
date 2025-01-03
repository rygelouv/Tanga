package app.books.tanga.feature.summary

import app.books.tanga.common.ui.ProgressState
import app.books.tanga.entity.Summary
import app.books.tanga.entity.SummaryId
import app.books.tanga.errors.UiError

data class SummaryUi(
    val id: SummaryId,
    val coverUrl: String,
    val title: String,
    val author: String,
    val duration: String,
    val keyLearnings: List<String>,
    // TODO: 2023-09-21 make this non-nullable after FakeData is removed
    val synopsis: String? = "",
    val purchaseBookUrl: String? = null,
)

fun Summary.toSummaryUi(): SummaryUi =
    SummaryUi(
        id = id,
        coverUrl = coverImageUrl,
        title = title,
        author = author,
        duration = playingLength,
        synopsis = synopsis,
        purchaseBookUrl = purchaseBookUrl,
        keyLearnings = keyLearnings
    )

data class SummaryContentState(
    val summary: SummaryUi? = null,
    val isFavorite: Boolean = false,
    val favoriteProgressState: ProgressState = ProgressState.Hide,
    val error: UiError? = null
) {
    val summaryId: SummaryId?
        get() = summary?.id
}
