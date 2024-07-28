package app.books.tanga.feature.settings

data class SettingsUiState(
    val isLoggingOut: Boolean = false,
    val showLogoutConfirmationDialog: Boolean = false,
)

sealed class SettingsUiEvent {
    data class Error(
        val error: Throwable
    ) : SettingsUiEvent()

    data object Empty : SettingsUiEvent()

    sealed class NavigateTo : SettingsUiEvent() {
        data object Auth : NavigateTo()
    }
}
