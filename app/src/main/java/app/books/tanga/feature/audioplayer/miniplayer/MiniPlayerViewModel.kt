package app.books.tanga.feature.audioplayer.miniplayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.entity.SummaryId
import app.books.tanga.feature.audioplayer.fullplayer.MiniPlayerUiState
import app.books.tanga.feature.audioplayer.infrastructure.PlayerActions
import app.books.tanga.feature.audioplayer.infrastructure.PlayerAvailability
import app.books.tanga.feature.audioplayer.infrastructure.PlayerController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MiniPlayerViewModel @Inject constructor(
    private val playerController: PlayerController
) : ViewModel(), PlayerActions by playerController {

    private val _state: MutableStateFlow<MiniPlayerUiState> =
        MutableStateFlow(MiniPlayerUiState())
    val state: StateFlow<MiniPlayerUiState> = _state

    fun init() {
        // Observe the playback state and update the UI accordingly.
        viewModelScope.launch {
            playerController.playbackState.collect { playbackState ->
                if (playbackState.state != _state.value.playerState) {
                    _state.update { it.copy(playerState = playbackState.state) }
                }
            }
        }
        viewModelScope.launch {
            playerController.playAvailability.collect { playAvailability ->
                when (playAvailability) {
                    PlayerAvailability.Available -> loadCurrentlyPlayingSummary()
                    else -> Unit
                }
            }
        }
    }

    /**
     * Try to get the currently playing summary from the player controller.
     * Load the summary if it exists.
     * This is called only from the mini player. Full player calls [loadSummary] directly.
     */
    private fun loadCurrentlyPlayingSummary() {
        val currentlyPlayingAudioTrack = playerController.getCurrentlyPlayingAudioTrack()
        if (currentlyPlayingAudioTrack != null) {
            _state.update {
                it.copy(
                    showMiniPlayer = true,
                    summaryId = SummaryId(currentlyPlayingAudioTrack.id),
                    audioTrack = currentlyPlayingAudioTrack,
                    title = currentlyPlayingAudioTrack.title,
                    author = currentlyPlayingAudioTrack.author,
                    coverUrl = currentlyPlayingAudioTrack.coverUrl,
                )
            }
            playerController.initPlayer(currentlyPlayingAudioTrack, viewModelScope)
        }
    }

    fun onDismissMiniPlayer() {
        // Note we should Pause the player when the mini player is dismissed.
        _state.update { it.copy(showMiniPlayer = false) }
    }

    override fun onCleared() {
        super.onCleared()
        playerController.releasePlayer()
    }
}
