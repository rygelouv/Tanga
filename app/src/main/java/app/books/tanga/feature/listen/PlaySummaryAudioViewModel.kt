package app.books.tanga.feature.listen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.errors.toUiError
import app.books.tanga.feature.audioplayer.AudioTrack
import app.books.tanga.feature.audioplayer.PlayerActions
import app.books.tanga.feature.audioplayer.PlayerController
import app.books.tanga.feature.summary.SummaryInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaySummaryAudioViewModel @Inject constructor(
    private val playerController: PlayerController,
    private val summaryInteractor: SummaryInteractor
) : ViewModel(),
    PlayerActions by playerController {
    private val _state: MutableStateFlow<PlaySummaryAudioUiState> =
        MutableStateFlow(PlaySummaryAudioUiState())
    val state: StateFlow<PlaySummaryAudioUiState> = _state

    init {
        // Observe the playback state and update the UI accordingly.
        viewModelScope.launch {
            playerController.playbackState.collect { playbackState ->
                _state.update {
                    it.copy(playbackState = playbackState)
                }
            }
        }
    }

    fun loadSummary(summaryId: String) {
        viewModelScope.launch {
            summaryInteractor
                .getSummary(summaryId)
                .onSuccess { summary ->
                    _state.update {
                        it.copy(
                            summaryId = summary.id.value,
                            title = summary.title,
                            author = summary.author,
                            duration = summary.playingLength,
                            coverUrl = summary.coverImageUrl
                        )
                    }
                    val audioTrack = AudioTrack(id = summary.id.value, url = summary.audioUrl)
                    playerController.initPlayer(audioTrack, viewModelScope)
                }.onFailure {
                    Log.e("SummaryDetailsViewModel", "Error loading summary with id: $summaryId", it)
                    _state.update { state ->
                        state.copy(error = it.toUiError())
                    }
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        playerController.releasePlayer()
    }
}
