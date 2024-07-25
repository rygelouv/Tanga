package app.books.tanga.entity

import androidx.annotation.StringRes
import app.books.tanga.R

@JvmInline
value class CategoryId(val value: String)

/**
 * Class representing a Category
 */
data class Category(
    val id: CategoryId,
    val name: String
)

enum class PredefinedCategory(val id: String, @StringRes val topics: Int) {
    BUSINESS("business", R.string.business_topics),
    PERSONAL_DEVELOPMENT("productivity_and_personal_development", R.string.personal_growth_topics),
    PSYCHOLOGY("life_philosophy_psychology", R.string.psychology_self_help_topics),
    FINANCIAL_EDUCATION("financial_education", R.string.financial_education_topics);

    companion object {
        fun fromId(id: String): PredefinedCategory = entries.find { it.id == id } ?: BUSINESS
    }
}
