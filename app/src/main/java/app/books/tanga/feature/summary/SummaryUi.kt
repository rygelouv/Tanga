package app.books.tanga.feature.summary

import androidx.annotation.DrawableRes
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.entity.Summary
import app.books.tanga.entity.SummaryId
import app.books.tanga.errors.UiError

data class SummaryUi(
    val id: SummaryId,
    // TODO: 2023-09-14 Remove this
    @DrawableRes val cover: Int = 0,
    val coverUrl: String? = null,
    val title: String,
    val author: String,
    val duration: String,
    // TODO: 2023-09-21 make this non-nullable after FakeData is removed
    val synopsis: String? = "",
    val textUrl: String? = null,
    val audioUrl: String? = null,
    val graphicUrl: String? = null,
    val videoUrl: String? = null,
    val authorPictureUrl: String? = null,
    val purchaseBookUrl: String? = null,
    // TODO: 2023-09-21 Remove this after FakeData is removed
    val hasGraphic: Boolean = false,
    // TODO: 2023-09-21 Remove this after FakeData is removed
    val hasVideo: Boolean = false
)

fun Summary.toSummaryUi(): SummaryUi =
    SummaryUi(
        id = id,
        coverUrl = coverImageUrl,
        title = title,
        author = author,
        duration = playingLength,
        synopsis = synopsis,
        textUrl = textUrl,
        audioUrl = audioUrl,
        graphicUrl = graphicUrl,
        videoUrl = videoUrl,
        authorPictureUrl = authorPictureUrl,
        purchaseBookUrl = purchaseBookUrl
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
