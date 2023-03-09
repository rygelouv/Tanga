package app.books.tanga.feature.auth

import app.books.tanga.common.ui.ProgressState
import com.google.android.gms.auth.api.identity.BeginSignInResult

data class AuthUiState(
    val googleSignInButtonProgressState: ProgressState
) {
    companion object {
        fun emptyState() = AuthUiState(googleSignInButtonProgressState = ProgressState.Idle)
    }
}

sealed interface AuthUiEvent {

    object Empty: AuthUiEvent

    @JvmInline
    value class LaunchGoogleSignIn(val signInResult: BeginSignInResult): AuthUiEvent

    sealed interface NavigateTo: AuthUiEvent {
        object ToHomeScreen: NavigateTo
    }
}