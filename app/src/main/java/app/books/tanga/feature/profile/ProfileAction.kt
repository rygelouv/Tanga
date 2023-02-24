package app.books.tanga.feature.profile

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import app.books.tanga.R
import app.books.tanga.ui.theme.*

enum class ProfileAction(
    @DrawableRes val icon: Int,
    val color: Color,
    val iconBackgroundColor: Color,
    @StringRes val text: Int,
    val shouldTint: Boolean = false
) {
    CONTACT(
        icon = R.drawable.ic_contact_email,
        color = ProfileYellow,
        iconBackgroundColor = ProfileYellowBackground,
        text = R.string.contact_us
    ),
    PRIVACY_AND_TERMS(
        icon = R.drawable.ic_privacy,
        color = ProfileGreen,
        iconBackgroundColor = ProfileGreenBackground,
        text = R.string.privay_and_terms
    ),
    NOTIFICATIONS(
        icon = R.drawable.ic_notification_bell,
        color = ProfilePurple,
        iconBackgroundColor = ProfilePurpleBackground,
        text = R.string.notificaitons_label
    ),
    LOGOUT(
        icon = R.drawable.ic_logout,
        color = ProfileRed,
        iconBackgroundColor = ProfileRedBackground,
        text = R.string.logout,
        shouldTint = true
    )
}