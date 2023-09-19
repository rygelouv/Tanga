package app.books.tanga.domain.categories

import app.books.tanga.domain.summary.Summary

/**
 * Class representing a Category
 */
data class Category(
    val slug: String,
    val name: String,
)

enum class PredefinedCategory(val slug: String) {
    BUSINESS("business"),
    PERSONAL_DEVELOPMENT("productivity_and_personal_development"),
    PSYCHOLOGY("life_philosophy_psychology"),
    FINANCIAL_EDUCATION("financial_education"),
}

/**
 * Represents a combination of a category and its summaries
 */
data class Section(
    val category: Category,
    val summaries: List<Summary>
)