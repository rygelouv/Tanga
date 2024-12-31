package app.books.tanga.feature.main

import app.books.tanga.feature.audioplayer.miniplayer.MiniPlayerState

sealed interface MainUiEvent {
    data object Empty : MainUiEvent

    sealed interface NavigateTo : MainUiEvent {
        data object ToAuth : NavigateTo
    }
}

data class MainUiState(
    val miniPlayerState: MiniPlayerState = MiniPlayerState(),
    val showMiniPlayerContainer: Boolean = true
)
