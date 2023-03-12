package app.books.tanga.feature.main

sealed interface MainUiEvent {

    object Empty: MainUiEvent

    sealed interface NavigateTo: MainUiEvent {
        object ToAuth: NavigateTo
    }
}