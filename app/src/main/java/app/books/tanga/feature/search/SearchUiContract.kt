package app.books.tanga.feature.search

import androidx.annotation.DrawableRes
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.coreui.icons.TangaIcons
import app.books.tanga.entity.Category
import app.books.tanga.entity.CategoryId
import app.books.tanga.entity.PredefinedCategory
import app.books.tanga.entity.SummaryId
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
    @DrawableRes val icon: Int
)

fun Category.toSearchCategoryUi(): CategoryUi =
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

fun getCategoryIllustration(categoryId: CategoryId): Int =
    when (categoryId.value) {
        PredefinedCategory.BUSINESS.id -> app.books.tanga.coreui.R.drawable.graphic_business_simple
        PredefinedCategory.PERSONAL_DEVELOPMENT.id -> app.books.tanga.coreui.R.drawable.graphic_personal_goals_checklist
        PredefinedCategory.PSYCHOLOGY.id -> app.books.tanga.coreui.R.drawable.graphic_questions_simple
        PredefinedCategory.FINANCIAL_EDUCATION.id -> app.books.tanga.coreui.R.drawable.graphic_investing_finance
        else -> app.books.tanga.coreui.R.drawable.graphic_career_simple
    }

sealed class SearchUiEvent {
    data class ShowSnackError(
        val error: UiError
    ) : SearchUiEvent()

    sealed class NavigateTo : SearchUiEvent() {
        data class ToSummary(
            val summaryId: SummaryId
        ) : NavigateTo()
    }
}
