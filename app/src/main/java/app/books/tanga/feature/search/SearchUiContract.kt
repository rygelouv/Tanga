package app.books.tanga.feature.search

import app.books.tanga.common.ui.ProgressState
import app.books.tanga.entity.SummaryId
import app.books.tanga.errors.UiError
import app.books.tanga.feature.categories.CategoryUi
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
