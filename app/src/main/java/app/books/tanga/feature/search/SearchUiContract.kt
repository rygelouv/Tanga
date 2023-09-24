package app.books.tanga.feature.search

import androidx.annotation.DrawableRes
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.core_ui.icons.TangaIcons
import app.books.tanga.entity.Category
import app.books.tanga.entity.PredefinedCategory
import app.books.tanga.feature.summary.SummaryUi

data class SearchUiState(
    val progressState: ProgressState,
    val query: String? = null,
    val categories: List<CategoryUi>? = null,
    val shouldShowCategories: Boolean = true,
    val selectedCategories: MutableList<CategoryUi> = mutableListOf(),
    val summaries: List<SummaryUi>? = null,
    val error: Throwable? = null
)

data class CategoryUi(
    val id: String,
    val name: String,
    @DrawableRes val icon: Int
)

fun Category.toCategoryUi(): CategoryUi {
    return CategoryUi(
        id = id,
        name = name,
        icon = when (id) {
            PredefinedCategory.BUSINESS.id -> TangaIcons.Business
            PredefinedCategory.PERSONAL_DEVELOPMENT.id -> TangaIcons.SelfDevelopment
            PredefinedCategory.PSYCHOLOGY.id -> TangaIcons.Productivity
            PredefinedCategory.FINANCIAL_EDUCATION.id -> TangaIcons.FinancialEducation
            else -> TangaIcons.SelfDevelopment
        }
    )
}