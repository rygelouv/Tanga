package app.books.tanga.feature.graphic

import app.books.tanga.common.ui.ProgressState
import app.books.tanga.entity.SummaryId
import app.books.tanga.errors.UiError
import app.books.tanga.feature.summary.SummaryContentState

data class GraphicSummaryUiState(
    val progressState: ProgressState = ProgressState.Show,
    val summaryContentState: SummaryContentState = SummaryContentState(),
    val summaryGraphicImage: String? = null,
    val error: UiError? = null
)

sealed interface GraphicSummaryUiEvent {
    data object Empty : GraphicSummaryUiEvent

    sealed interface NavigateTo : GraphicSummaryUiEvent {

        data class ToAudioPlayer(
            val summaryId: SummaryId
        ) : NavigateTo

        data object ToPricingPlans : NavigateTo
    }
}
