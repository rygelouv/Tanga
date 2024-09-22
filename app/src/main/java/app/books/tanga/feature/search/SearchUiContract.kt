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

/**
 * Represents the state of the query input field
 */
sealed class QueryInputState {

    /**
     * When the query input has not been interacted with yet
     */
    data object Idle : QueryInputState()

    /**
     * When the query input has been interacted with and the query is not empty
     */
    data class Active(
        val query: String
    ) : QueryInputState()

    /**
     * When the query input has been interacted with and the query has been cleared
     */
    data object Empty : QueryInputState()
}

fun QueryInputState.isNotIdle(): Boolean = this !is QueryInputState.Idle
