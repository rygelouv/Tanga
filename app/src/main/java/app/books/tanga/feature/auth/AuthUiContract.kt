package app.books.tanga.feature.auth

import app.books.tanga.common.ui.ProgressState
import com.google.android.gms.auth.api.identity.BeginSignInResult

sealed interface AuthUiEvent {

    @JvmInline
    value class ShowProgress(val progressState: ProgressState): AuthUiEvent

    @JvmInline
    value class LaunchGoogleSignIn(val signInResult: BeginSignInResult): AuthUiEvent

    sealed interface NavigateTo: AuthUiEvent {
        object ToHoMeScreen: NavigateTo
    }
}