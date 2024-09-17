package app.books.tanga.feature.listen

import app.books.tanga.entity.SummaryId
import app.books.tanga.errors.UiError
import app.books.tanga.feature.audioplayer.AudioTrack
import app.books.tanga.feature.audioplayer.PlaybackState
import app.books.tanga.feature.audioplayer.PlayerState

data class PlaySummaryAudioUiState(
    val summaryId: String? = null,
    val title: String? = null,
    val author: String? = null,
    val coverUrl: String? = null,
    val duration: String? = null,
    val playbackState: PlaybackState? = null,
    val error: UiError? = null,
    val audioTrack: AudioTrack? = null
)

data class MiniPlayerUiState(
    val summaryId: SummaryId? = null,
    val title: String? = null,
    val author: String? = null,
    val coverUrl: String? = null,
    val playerState: PlayerState? = null,
    val audioTrack: AudioTrack? = null,
    val showMiniPlayer: Boolean = false
)
