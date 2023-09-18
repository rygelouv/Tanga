package app.books.tanga.domain.categories

import app.books.tanga.domain.summary.Summary

/**
 * Class representing a Category
 */
data class Category(
    val slug: String,
    val name: String,
)

/**
 * Represents a combination of a category and its summaries
 */
data class Section(
    val category: Category,
    val summaries: List<Summary>
)