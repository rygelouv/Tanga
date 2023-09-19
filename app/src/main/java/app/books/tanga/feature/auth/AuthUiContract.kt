package app.books.tanga.feature.auth

import app.books.tanga.common.ui.ProgressIndicatorState
import com.google.android.gms.auth.api.identity.BeginSignInResult

data class AuthUiState(
    val googleSignInButtonProgressIndicatorState: ProgressIndicatorState
) {
    companion object {
        fun emptyState() = AuthUiState(googleSignInButtonProgressIndicatorState = ProgressIndicatorState.Hide)
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