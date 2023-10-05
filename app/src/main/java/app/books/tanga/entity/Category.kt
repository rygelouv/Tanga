package app.books.tanga.entity

@JvmInline
value class CategoryId(val value: String)

/**
 * Class representing a Category
 */
data class Category(
    val id: CategoryId,
    val name: String,
)

enum class PredefinedCategory(val id: String) {
    BUSINESS("business"),
    PERSONAL_DEVELOPMENT("productivity_and_personal_development"),
    PSYCHOLOGY("life_philosophy_psychology"),
    FINANCIAL_EDUCATION("financial_education"),
}