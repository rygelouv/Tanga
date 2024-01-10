package app.books.tanga.feature.read

import app.books.tanga.common.ui.ProgressState
import app.books.tanga.entity.SummaryId
import app.books.tanga.errors.UiError
import app.books.tanga.feature.summary.SummaryContentState

data class ReadSummaryUiState(
    val progressState: ProgressState = ProgressState.Show,
    val summaryContentState: SummaryContentState = SummaryContentState(),
    val summaryTextContent: String? = null,
    val textScaleFactor: TextScaleFactor = TextScaleFactor.DEFAULT,
    val fontSizeChooserVisible: Boolean = false,
    val error: UiError? = null
)

sealed interface ReadSummaryUiEvent {
    data object Empty : ReadSummaryUiEvent

    sealed interface NavigateTo : ReadSummaryUiEvent {

        data class ToAudioPlayer(
            val summaryId: SummaryId
        ) : NavigateTo

        data object ToPricingPlans : NavigateTo
    }
}
