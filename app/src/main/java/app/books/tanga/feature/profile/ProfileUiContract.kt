package app.books.tanga.feature.profile

import androidx.annotation.StringRes
import app.books.tanga.R

data class ProfileUiState(
    val userInfo: UserInfoUi? = null,
    @StringRes val accountTypeRes: Int = R.string.freemium
)

data class UserInfoUi(
    val fullName: String,
    val photoUrl: String?,
    val isAnonymous: Boolean?
)

sealed interface ProfileUiEvent {
    data object Empty : ProfileUiEvent

    data class Error(
        val error: Throwable
    ) : ProfileUiEvent

    sealed interface NavigateTo : ProfileUiEvent {
        data object ToPricingPlan : NavigateTo

        data object ToAuth : NavigateTo
    }
}
