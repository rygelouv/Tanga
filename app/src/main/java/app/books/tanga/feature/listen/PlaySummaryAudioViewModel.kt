package app.books.tanga.feature.listen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.books.tanga.data.download.DownloadUrlGenerator
import app.books.tanga.entity.Summary
import app.books.tanga.entity.SummaryId
import app.books.tanga.errors.toUiError
import app.books.tanga.feature.audioplayer.AudioTrack
import app.books.tanga.feature.audioplayer.PlayerActions
import app.books.tanga.feature.audioplayer.PlayerController
import app.books.tanga.feature.summary.SummaryInteractor
import app.books.tanga.tracking.AnalyticsTracker
import app.books.tanga.tracking.Pages
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class PlaySummaryAudioViewModel @Inject constructor(
    private val playerController: PlayerController,
    private val summaryInteractor: SummaryInteractor,
    private val downloadUrlGenerator: DownloadUrlGenerator,
    private val analyticsTracker: AnalyticsTracker
) : ViewModel(), PlayerActions by playerController {

    private val _state: MutableStateFlow<PlaySummaryAudioUiState> =
        MutableStateFlow(PlaySummaryAudioUiState())
    val state: StateFlow<PlaySummaryAudioUiState> = _state

    init {
        analyticsTracker.trackPage(Pages.PLAY_SUMMARY_AUDIO)
        // Observe the playback state and update the UI accordingly.
        viewModelScope.launch {
            playerController.playbackState.collect { playbackState ->
                _state.update { it.copy(playbackState = playbackState) }
            }
        }
    }

    fun loadSummary(summaryId: SummaryId) {
        viewModelScope.launch {
            summaryInteractor
                .getSummary(summaryId)
                .onSuccess { summary ->
                    val audioUrl = downloadUrlGenerator.generateAudioDownloadUrl(summary.id)
                    _state.update {
                        it.copy(
                            summaryId = summary.id.value,
                            title = summary.title,
                            author = summary.author,
                            duration = summary.playingLength,
                            coverUrl = summary.coverImageUrl
                        )
                    }
                    audioUrl?.let {
                        val audioTrack = summary.toAudioTrack(it)
                        playerController.initPlayer(audioTrack, viewModelScope)
                        _state.update { state ->
                            state.copy(audioTrack = audioTrack)
                        }
                    }
                }.onFailure {
                    Timber.e("Error loading summary with id: $summaryId", it)
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

fun Summary.toAudioTrack(url: String): AudioTrack = AudioTrack(
    id = id.value,
    url = url,
    title = title,
    author = author,
    coverUrl = coverImageUrl
)
