package app.books.tanga.feature.search

import androidx.annotation.DrawableRes
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.core_ui.icons.TangaIcons
import app.books.tanga.entity.Category
import app.books.tanga.entity.PredefinedCategory
import app.books.tanga.errors.UiError
import app.books.tanga.feature.summary.SummaryUi

data class SearchUiState(
    val progressState: ProgressState,
    val query: String? = null,
    val categories: List<CategoryUi>? = null,
    val shouldShowCategories: Boolean = true,
    val selectedCategories: MutableList<CategoryUi> = mutableListOf(),
    val summaries: List<SummaryUi>? = null,
    val error: UiError? = null
)

data class CategoryUi(
    val id: String,
    val name: String,
    @DrawableRes val icon: Int,
)

fun Category.toCategoryUi(): CategoryUi =
    CategoryUi(
        id = id.value,
        name = name,
        icon = when (id.value) {
            PredefinedCategory.BUSINESS.id -> TangaIcons.Business
            PredefinedCategory.PERSONAL_DEVELOPMENT.id -> TangaIcons.SelfDevelopment
            PredefinedCategory.PSYCHOLOGY.id -> TangaIcons.Productivity
            PredefinedCategory.FINANCIAL_EDUCATION.id -> TangaIcons.FinancialEducation
            else -> TangaIcons.SelfDevelopment
        }
    )

sealed class SearchUiEvent {
    data class ShowSnackError(
        val error: UiError
    ) : SearchUiEvent()
}
