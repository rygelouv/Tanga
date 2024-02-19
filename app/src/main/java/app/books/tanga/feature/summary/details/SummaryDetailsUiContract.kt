package app.books.tanga.feature.summary.details

import app.books.tanga.common.ui.ProgressState
import app.books.tanga.entity.SummaryId
import app.books.tanga.errors.UiError
import app.books.tanga.feature.summary.SummaryUi
import app.books.tanga.utils.randomUid

/**
 * @param summary the summary to display
 * @param progressState the progress state of the summary loading operation
 * @param isFavorite whether the summary is a user's favorite or not
 * @param favoriteProgressState the progress state of the favorite operation
 * @param recommendations the list of recommended summaries related to the current summary
 */
data class SummaryDetailsUiState(
    val progressState: ProgressState = ProgressState.Hide,
    val summary: SummaryUi? = null,
    val isFavorite: Boolean = false,
    val favoriteProgressState: ProgressState = ProgressState.Hide,
    val recommendations: List<SummaryUi> = emptyList(),
    val error: UiError? = null
)

sealed interface SummaryDetailsUiEvent {
    data object Empty : SummaryDetailsUiEvent

    data class ShowAuthSuggestion(val id: String = randomUid()) : SummaryDetailsUiEvent

    sealed interface NavigateTo : SummaryDetailsUiEvent {
        data class ToSummaryDetails(
            val summaryId: SummaryId
        ) : NavigateTo

        data object ToPrevious : NavigateTo

        data class ToAudioPlayer(
            val summaryId: SummaryId
        ) : NavigateTo

        data class ToReadSummary(
            val summaryId: SummaryId
        ) : NavigateTo

        data object ToAuth : NavigateTo
    }
}
