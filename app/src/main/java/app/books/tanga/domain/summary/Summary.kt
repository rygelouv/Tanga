package app.books.tanga.domain.summary

import app.books.tanga.domain.categories.Category

typealias CategorySlug = String

/**
 *  Class representing a Summary
 *  audioUrl: the link to the summary audio
 *  author: the author of the book the summary is about
 *  coverImageUrl: the link to the summary cover image
 *  graphicUrl: the link to the summary graphic
 *  playingLength: the length of the summary audio
 *  slug: the slug of the summary
 *  summaryUrl: the link to the summary
 *  synopsis: the book synopsis
 *  title: the title of the book the summary is about
 *  videoUrl: the link to the summary video
 */
data class Summary(
    val audioUrl: String,
    val author: String,
    val categories: List<CategorySlug>,
    val coverImageUrl: String,
    val graphicUrl: String,
    val playingLength: String,
    val slug: String,
    val summaryUrl: String,
    val synopsis: String,
    val title: String,
    val videoUrl: String
)