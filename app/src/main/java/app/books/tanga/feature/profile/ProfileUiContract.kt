package app.books.tanga.feature.profile

import androidx.annotation.StringRes
import app.books.tanga.R

data class ProfileUiState(
    val fullName: String? = null,
    @StringRes val accountTypeRes: Int = R.string.freemium
)