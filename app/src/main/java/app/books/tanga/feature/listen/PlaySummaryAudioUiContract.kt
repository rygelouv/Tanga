package app.books.tanga.feature.listen

import app.books.tanga.errors.UiError
import app.books.tanga.feature.audioplayer.PlaybackState

data class PlaySummaryAudioUiState(
    val summaryId: String? = null,
    val title: String? = null,
    val author: String? = null,
    val coverUrl: String? = null,
    val duration: String? = null,
    val playbackState: PlaybackState? = null,
    val error: UiError? = null
)