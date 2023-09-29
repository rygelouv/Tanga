package app.books.tanga.feature.summary

import androidx.annotation.DrawableRes
import app.books.tanga.entity.Summary

data class SummaryUi(
    val id: String,
    @DrawableRes val cover: Int = 0, // TODO: 2023-09-14 Remove this
    val coverUrl: String? = null,
    val title: String,
    val author: String,
    val duration: String,
    val synopsis: String? = "", // TODO: 2023-09-21 make this non-nullable after FakeData is removed
    val textUrl: String? = null,
    val audioUrl: String? = null,
    val graphicUrl: String? = null,
    val videoUrl: String? = null,
    val authorPictureUrl: String? = null,
    val purchaseBookUrl: String? = null,
    val hasGraphic: Boolean = false, // TODO: 2023-09-21 Remove this after FakeData is removed
    val hasVideo: Boolean = false // TODO: 2023-09-21 Remove this after FakeData is removed
)

fun Summary.toSummaryUi(): SummaryUi {
    return SummaryUi(
        id = id.value,
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
        purchaseBookUrl = purchaseBookUrl,
    )
}