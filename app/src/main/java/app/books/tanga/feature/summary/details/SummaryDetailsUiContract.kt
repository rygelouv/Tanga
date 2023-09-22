package app.books.tanga.feature.summary.details

import app.books.tanga.common.ui.ProgressState
import app.books.tanga.feature.summary.SummaryUi

/**
 * @param summary the summary to display
 * @param isFavorite whether the summary is a user's favorite or not
 * @param recommendations the list of recommended summaries related to the current summary
 */
data class SummaryDetailsUiState(
    val progressState: ProgressState = ProgressState.Hide,
    val summary: SummaryUi? = null,
    val isFavorite: Boolean = false,
    val recommendations: List<SummaryUi> = emptyList()
)

