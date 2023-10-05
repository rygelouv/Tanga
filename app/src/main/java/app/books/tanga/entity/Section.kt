package app.books.tanga.entity

/**
 * Represents a combination of a category and its summaries
 */
data class Section(
    val category: Category,
    val summaries: List<Summary>
)