package app.books.tanga.feature.search

import androidx.annotation.DrawableRes
import app.books.tanga.common.ui.ProgressIndicatorState
import app.books.tanga.core_ui.icons.TangaIcons
import app.books.tanga.domain.categories.Category
import app.books.tanga.domain.categories.PredefinedCategory
import app.books.tanga.feature.summary.list.SummaryUi

data class SearchUiState(
    val progressIndicatorState: ProgressIndicatorState,
    val query: String? = null,
    val categories: List<CategoryUi>? = null,
    val shouldShowCategories: Boolean = true,
    val selectedCategory: CategoryUi? = null,
    val summaries: List<SummaryUi>? = null,
    val error: Throwable? = null
)

data class CategoryUi(
    val slug: String,
    val name: String,
    @DrawableRes val icon: Int
)

fun Category.toCategoryUi(): CategoryUi {
    return CategoryUi(
        slug = slug,
        name = name,
        icon = when (slug) {
            PredefinedCategory.BUSINESS.slug -> TangaIcons.Business
            PredefinedCategory.PERSONAL_DEVELOPMENT.slug -> TangaIcons.SelfDevelopment
            PredefinedCategory.PSYCHOLOGY.slug -> TangaIcons.Productivity
            PredefinedCategory.FINANCIAL_EDUCATION.slug -> TangaIcons.FinancialEducation
            else -> TangaIcons.SelfDevelopment
        }
    )
}