package app.books.tanga.feature.summary.details

import app.books.tanga.common.ui.ProgressState
import app.books.tanga.errors.UiError
import app.books.tanga.feature.summary.SummaryUi

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

