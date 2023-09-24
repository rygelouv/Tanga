package app.books.tanga.entity

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
 *  categories: the list of categories the summary belongs to
 *  textUrl: the link to the summary text
 */
data class Summary(
    val slug: String,
    val title: String,
    val author: String,
    val synopsis: String,
    val coverImageUrl: String, // TODO: 2023-09-22 Change to coverUrl
    val categories: List<CategorySlug>,
    val textUrl: String,
    val audioUrl: String,
    val graphicUrl: String,
    val videoUrl: String,
    val playingLength: String,
    val authorPictureUrl: String,
    val purchaseBookUrl: String,
)