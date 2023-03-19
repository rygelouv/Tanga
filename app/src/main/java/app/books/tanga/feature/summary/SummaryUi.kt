package app.books.tanga.feature.summary

import androidx.annotation.DrawableRes

data class SummaryUi(
    val id: String,
    @DrawableRes val cover: Int,
    val title: String,
    val author: String,
    val duration: String,
    val hasGraphic: Boolean = false,
    val hasVideo: Boolean = false
)