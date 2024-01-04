package app.books.tanga.feature.summary.list

import app.books.tanga.common.ui.ProgressState
import app.books.tanga.errors.UiError
import app.books.tanga.feature.search.CategoryUi
import app.books.tanga.feature.summary.SummaryUi

data class SummariesByCategoryUiState(
    val categoryUi: CategoryUi? = null,
    val progressState: ProgressState = ProgressState.Hide,
    val summaries: List<SummaryUi>? = null,
    val error: UiError? = null
)

sealed class SummariesByCategoryUiEvent {

    sealed class NavigateTo : SummariesByCategoryUiEvent() {
        data class ToSummary(
            val summaryId: String
        ) : NavigateTo()

        data object ToSearch : NavigateTo()
    }
}
