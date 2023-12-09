package app.books.tanga.feature.summary.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.entity.Summary
import app.books.tanga.entity.SummaryId
import app.books.tanga.errors.toUiError
import app.books.tanga.feature.library.FavoriteInteractor
import app.books.tanga.feature.summary.SummaryInteractor
import app.books.tanga.feature.summary.toSummaryUi
import app.books.tanga.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SummaryDetailsViewModel @Inject constructor(
    private val summaryInteractor: SummaryInteractor,
    private val favoriteInteractor: FavoriteInteractor,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _state: MutableStateFlow<SummaryDetailsUiState> =
        MutableStateFlow(SummaryDetailsUiState(progressState = ProgressState.Show))
    val state: StateFlow<SummaryDetailsUiState> = _state.asStateFlow()

    private val _events: Channel<SummaryDetailsUiEvent> = Channel()
    val events: Flow<SummaryDetailsUiEvent> = _events.receiveAsFlow()

    /**
     * Holding the domain summary object to help perform future operations
     */
    lateinit var summary: Summary

    /**
     * Load the summary with the given id then load the recommendations for this summary
     * and the favorite status
     */
    fun loadSummary(summaryId: SummaryId) {
        viewModelScope.launch {
            summaryInteractor
                .getSummary(summaryId)
                .onSuccess {
                    summary = it
                    _state.update { state ->
                        state.copy(
                            progressState = ProgressState.Hide,
                            summary = summary.toSummaryUi()
                        )
                    }
                    loadFavoriteStatus(summaryId)
                    loadRecommendations(summary)
                }.onFailure {
                    Log.e("SummaryDetailsViewModel", "Error loading summary with id: $summaryId", it)
                    _state.update { state ->
                        state.copy(
                            progressState = ProgressState.Hide,
                            error = it.toUiError()
                        )
                    }
                }
        }
    }

    /**
     * Load the favorite status for the given summary id
     */
    private suspend fun loadFavoriteStatus(summaryId: SummaryId) {
        favoriteInteractor
            .isFavorite(summaryId)
            .onSuccess { isFavorite ->
                _state.update { it.copy(isFavorite = isFavorite) }
            }.onFailure { error ->
                Log.e("SummaryDetailsViewModel", "Error loading favorite status", error)
            }
    }

    private fun loadRecommendations(summary: Summary) {
        viewModelScope.launch {
            summaryInteractor
                .getRecommendationsForSummary(summary)
                .onSuccess { recommendations ->
                    _state.update { it.copy(recommendations = recommendations.map { it.toSummaryUi() }) }
                }.onFailure {
                    Log.e("SummaryDetailsViewModel", "Error loading recommendations", it)
                    // TODO Post snackbar error event
                }
        }
    }

    /**
     * When user clicks on the favorite button, show the progress indicator and
     * toggle the favorite status
     */
    fun toggleFavorite() {
        // Do nothing if the summary is not initialized
        if (this::summary.isInitialized.not()) return

        // Show saving progress
        _state.update { it.copy(favoriteProgressState = ProgressState.Show) }

        // Get the current favorite status
        val isFavorite = _state.value.isFavorite

        viewModelScope.launch {
            if (isFavorite) {
                removeFavorite()
            } else {
                saveFavorite()
            }
        }
    }

    private suspend fun saveFavorite() {
        favoriteInteractor
            .createFavorite(summary)
            .onSuccess {
                _state.update {
                    it.copy(
                        isFavorite = true,
                        favoriteProgressState = ProgressState.Hide
                    )
                }
                // TODO: Show a snackbar to notify the user that the summary is added to favorites
            }.onFailure { error ->
                _state.update { it.copy(favoriteProgressState = ProgressState.Hide) }
                Log.e("SummaryDetailsViewModel", "Error creating favorite", error)
            }
    }

    private suspend fun removeFavorite() {
        favoriteInteractor
            .deleteFavoriteBySummaryId(summary.id)
            .onSuccess {
                _state.update {
                    it.copy(
                        isFavorite = false,
                        favoriteProgressState = ProgressState.Hide
                    )
                }
                // TODO: Show a snackbar to notify the user that the summary is removed from favorites
            }.onFailure { error ->
                _state.update { it.copy(favoriteProgressState = ProgressState.Hide) }
                Log.e("SummaryDetailsViewModel", "Error deleting favorite", error)
            }
    }

    fun onPlayClick() {
        viewModelScope.launch {
            if (sessionManager.hasSession()) {
                postEvent(SummaryDetailsUiEvent.NavigateTo.ToAudioPlayer(summary.id))
            } else {
                postEvent(SummaryDetailsUiEvent.ShowAuthSuggestion)
            }
        }
    }

    private fun postEvent(event: SummaryDetailsUiEvent) {
        viewModelScope.launch {
            _events.send(event)
        }
    }
}
