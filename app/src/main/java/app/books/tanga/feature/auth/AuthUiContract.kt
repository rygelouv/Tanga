package app.books.tanga.feature.auth

import androidx.annotation.StringRes
import app.books.tanga.R
import app.books.tanga.common.ui.ProgressState
import app.books.tanga.errors.UiError
import com.google.android.gms.auth.api.identity.BeginSignInResult

data class AuthUiState(
    val googleSignInButtonProgressState: ProgressState = ProgressState.Hide,
    val disableGoogleSignInButton: Boolean = false,
    @StringRes val skipText: Int = R.string.auth_skip,
    val skipProgressState: ProgressState = ProgressState.Hide,
)

sealed interface AuthUiEvent {
    data object Empty : AuthUiEvent

    data class Error(
        val error: UiError
    ) : AuthUiEvent

    @JvmInline
    value class LaunchGoogleSignIn(
        val signInResult: BeginSignInResult
    ) : AuthUiEvent

    data object Close : AuthUiEvent

    sealed interface NavigateTo : AuthUiEvent {
        data object ToHomeScreen : NavigateTo
        data object ToNotificationPermissionScreen : NavigateTo
    }
}
