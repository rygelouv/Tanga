package app.books.tanga.feature.categories

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import app.books.tanga.R
import app.books.tanga.entity.Category

enum class PredefinedCategory(
    val id: String,
    @StringRes val categoryName: Int,
    @StringRes val topics: Int,
    @DrawableRes val icon: Int,
    @DrawableRes val illustration: Int
) {
    BUSINESS_CAREER(
        id = "business_career",
        categoryName = R.string.business_career,
        topics = R.string.business_topics,
        icon = app.books.tanga.coreui.R.drawable.ic_business,
        illustration = app.books.tanga.coreui.R.drawable.graphic_business_simple
    ),
    PRODUCTIVITY_SELF_GROWTH(
        id = "productivity_and_personal_development",
        categoryName = R.string.productivity_self_growth,
        topics = R.string.personal_growth_topics,
        icon = app.books.tanga.coreui.R.drawable.ic_self_development,
        illustration = app.books.tanga.coreui.R.drawable.graphic_personal_goals_checklist
    ),
    SELF_HELP(
        id = "life_philosophy_psychology",
        categoryName = R.string.self_help,
        topics = R.string.psychology_self_help_topics,
        icon = app.books.tanga.coreui.R.drawable.ic_productivity,
        illustration = app.books.tanga.coreui.R.drawable.graphic_questions_simple
    ),
    FINANCIAL_EDUCATION(
        id = "financial_education",
        categoryName = R.string.financial_education,
        topics = R.string.financial_education_topics,
        icon = app.books.tanga.coreui.R.drawable.ic_financial_education,
        illustration = app.books.tanga.coreui.R.drawable.graphic_investing_finance
    );

    companion object {
        fun fromId(id: String): PredefinedCategory = entries.find { it.id == id } ?: BUSINESS_CAREER
    }
}

data class CategoryUi(
    val id: String,
    val name: String,
    @DrawableRes val icon: Int,
    @StringRes val topics: Int = 0
)

fun Category.toCategoryUi(): CategoryUi =
    CategoryUi(
        id = id.value,
        name = name,
        icon = PredefinedCategory.fromId(id.value).icon,
    )
