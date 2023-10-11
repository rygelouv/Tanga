package app.books.tanga.feature.main

sealed interface MainUiEvent {
    data object Empty : MainUiEvent

    sealed interface NavigateTo : MainUiEvent {
        data object ToAuth : NavigateTo
    }
}
