package app.books.tanga.feature.summary.list

import androidx.annotation.DrawableRes
import app.books.tanga.domain.summary.Summary

data class SummaryUi(
    val id: String,
    @DrawableRes val cover: Int = 0, // TODO: 2023-09-14 Remove this
    val coverUrl: String? = null,
    val title: String,
    val author: String,
    val duration: String,
    val hasGraphic: Boolean = false,
    val hasVideo: Boolean = false
)

fun Summary.toSummaryUi(): SummaryUi {
    return SummaryUi(
        id = slug,
        title = title,
        author = author,
        coverUrl = coverImageUrl,
        duration = playingLength,
        hasGraphic = graphicUrl.isNotEmpty(),
        hasVideo = videoUrl.isNotEmpty()
    )
}