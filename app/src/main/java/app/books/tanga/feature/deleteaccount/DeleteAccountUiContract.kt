package app.books.tanga.feature.deleteaccount

data class DeleteAccountUiState(
    val isDeleting: Boolean = false,
    val showConfirmationDialog: Boolean = false
)

sealed class DeleteAccountUiEvent {
    data class Error(
        val error: Throwable
    ) : DeleteAccountUiEvent()

    data object Empty : DeleteAccountUiEvent()

    sealed class NavigateTo : DeleteAccountUiEvent() {
        data object Auth : NavigateTo()
    }
}
