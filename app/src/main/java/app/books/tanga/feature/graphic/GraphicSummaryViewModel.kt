package app.books.tanga.feature.graphic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.data.download.FileDownloader
import app.books.tanga.entity.SummaryId
import app.books.tanga.feature.summary.SummaryBehaviorDelegate
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
class GraphicSummaryViewModel @Inject constructor(
    private val summaryBehaviorDelegate: SummaryBehaviorDelegate,
    private val fileDownloader: FileDownloader
) : ViewModel(), SummaryBehaviorDelegate by summaryBehaviorDelegate {

    private val _state: MutableStateFlow<GraphicSummaryUiState> =
        MutableStateFlow(GraphicSummaryUiState(progressState = ProgressState.Show))
    val state: StateFlow<GraphicSummaryUiState> = _state.asStateFlow()

    private val _events: Channel<GraphicSummaryUiEvent> = Channel(Channel.BUFFERED)
    val events: Flow<GraphicSummaryUiEvent> = _events.receiveAsFlow()

    init {
        summaryBehaviorDelegate.setUp(viewModelScope)
        observerSummaryBehaviorState()
    }

    private fun observerSummaryBehaviorState() {
        viewModelScope.launch {
            summaryBehaviorDelegate.summaryContentState
                .collect { state ->
                    state.error?.let { error ->
                        _state.update { currentState ->
                            currentState.copy(
                                progressState = ProgressState.Hide,
                                error = error
                            )
                        }
                    }
                    _state.update { currentState ->
                        currentState.copy(
                            progressState = ProgressState.Hide,
                            summaryContentState = state,
                        )
                    }
                }
        }
    }

    override fun loadSummary(summaryId: SummaryId) {
        _state.update { currentState ->
            currentState.copy(progressState = ProgressState.Show)
        }
        viewModelScope.launch {
//            fileDownloader.downloadSummaryText(summaryId)
//                .onSuccess { textBytes ->
//                    val textContent = textBytes?.toString(Charsets.UTF_8) ?: ""
//                    _state.update { currentState ->
//                        currentState.copy(
//                            progressState = ProgressState.Hide,
//                            summaryTextContent = textContent
//                        )
//                    }
//                    summaryBehaviorDelegate.loadSummary(summaryId)
//                }
//                .onFailure { error ->
//                    _state.update { currentState ->
//                        currentState.copy(
//                            progressState = ProgressState.Hide,
//                            error = error.toUiError()
//                        )
//                    }
//                }
            summaryBehaviorDelegate.loadSummary(summaryId)
        }
    }

    fun onPlayAudioClicked() {
        val summaryId = _state.value.summaryContentState.summaryId ?: return
        postEvent(GraphicSummaryUiEvent.NavigateTo.ToAudioPlayer(summaryId = summaryId))
    }

    private fun postEvent(event: GraphicSummaryUiEvent) {
        viewModelScope.launch {
            _events.send(event)
        }
    }
}
