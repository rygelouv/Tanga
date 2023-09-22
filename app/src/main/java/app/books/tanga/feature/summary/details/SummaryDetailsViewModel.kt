package app.books.tanga.feature.summary.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.domain.summary.Summary
import app.books.tanga.domain.summary.SummaryInteractor
import app.books.tanga.feature.summary.toSummaryUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummaryDetailsViewModel @Inject constructor(
    private val summaryInteractor: SummaryInteractor
) : ViewModel() {

    private val _state: MutableStateFlow<SummaryDetailsUiState> =
        MutableStateFlow(SummaryDetailsUiState(progressState = ProgressState.Show))
    val state: StateFlow<SummaryDetailsUiState> = _state.asStateFlow()

    /**
     * Load the summary with the given id then load the recommendations for this summary
     */
    fun loadSummary(summaryId: String) {
        viewModelScope.launch {
            summaryInteractor.getSummary(summaryId).onSuccess { summary ->
                _state.update {
                    it.copy(
                        progressState = ProgressState.Hide,
                        summary = summary.toSummaryUi(),
                    )
                }
                loadRecommendations(summary)
            }.onFailure {
                Log.e("SummaryDetailsViewModel", "Error loading summary with id: $summaryId", it)
            }
        }
    }

    private fun loadRecommendations(summary: Summary) {
        viewModelScope.launch {
            summaryInteractor.getRecommendationsForSummary(summary).onSuccess { recommendations ->
                _state.update { it.copy(recommendations = recommendations.map { it.toSummaryUi() }) }
            }
        }
    }
}