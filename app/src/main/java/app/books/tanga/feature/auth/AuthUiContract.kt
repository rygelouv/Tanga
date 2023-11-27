package app.books.tanga.feature.auth

import app.books.tanga.common.ui.ProgressState
import app.books.tanga.errors.UiError
import com.google.android.gms.auth.api.identity.BeginSignInResult

data class AuthUiState(
    val googleSignInButtonProgressState: ProgressState,
    val skipProgressState: ProgressState = ProgressState.Hide,
) {
    companion object {
        fun emptyState() = AuthUiState(googleSignInButtonProgressState = ProgressState.Hide)
    }
}

sealed interface AuthUiEvent {
    data object Empty : AuthUiEvent

    data class Error(
        val error: UiError
    ) : AuthUiEvent

    @JvmInline
    value class LaunchGoogleSignIn(
        val signInResult: BeginSignInResult
    ) : AuthUiEvent

    sealed interface NavigateTo : AuthUiEvent {
        data object ToHomeScreen : NavigateTo
    }
}
