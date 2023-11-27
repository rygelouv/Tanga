package app.books.tanga.feature.profile

import androidx.annotation.StringRes
import app.books.tanga.R

data class ProfileUiState(
    val fullName: String? = null,
    val photoUrl: String? = null,
    val isAnonymous: Boolean = false,
    @StringRes val accountTypeRes: Int = R.string.freemium
)

sealed interface ProfileUiEvent {
    data object Empty : ProfileUiEvent

    data class Error(
        val error: Throwable
    ) : ProfileUiEvent

    sealed interface NavigateTo : ProfileUiEvent {
        data class ToPricingPlan(
            val isAnonymous: Boolean
        ) : NavigateTo

        data class ToAuth(
            val isAnonymous: Boolean
        ) : NavigateTo
    }
}
