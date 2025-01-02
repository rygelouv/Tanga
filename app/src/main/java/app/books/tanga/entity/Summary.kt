package app.books.tanga.entity

@JvmInline
value class SummaryId(val value: String)

/**
 *  Class representing a Summary
 *  @param author: the author of the book the summary is about
 *  @param coverImageUrl: the link to the summary cover image
 *  @param playingLength: the length of the summary audio
 *  @param synopsis: the book synopsis
 *  @param title: the title of the book the summary is about
 *  @param keyLearnings the list of key learnings from the summary
 *  @param categories: the list of categories the summary belongs to
 */
data class Summary(
    val id: SummaryId,
    val title: String,
    val author: String,
    val synopsis: String,
    val coverImageUrl: String,
    val playingLength: String,
    val purchaseBookUrl: String,
    val categories: List<CategoryId>,
    val keyLearnings: List<String>
)
