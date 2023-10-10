package app.books.tanga.feature.profile

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.core_ui.theme.ProfileGreen
import app.books.tanga.core_ui.theme.ProfileGreenBackground
import app.books.tanga.core_ui.theme.ProfilePurple
import app.books.tanga.core_ui.theme.ProfilePurpleBackground
import app.books.tanga.core_ui.theme.ProfileRed
import app.books.tanga.core_ui.theme.ProfileRedBackground
import app.books.tanga.core_ui.theme.ProfileYellow
import app.books.tanga.core_ui.theme.ProfileYellowBackground
import app.books.tanga.core_ui.theme.navyTransparent

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
        text = R.string.contact_us,
    ),
    PRIVACY_AND_TERMS(
        icon = R.drawable.ic_privacy,
        color = ProfileGreen,
        iconBackgroundColor = ProfileGreenBackground,
        text = R.string.privay_and_terms,
    ),
    NOTIFICATIONS(
        icon = R.drawable.ic_notification_bell,
        color = ProfilePurple,
        iconBackgroundColor = ProfilePurpleBackground,
        text = R.string.notificaitons_label,
    ),
    LOGOUT(
        icon = R.drawable.ic_logout,
        color = ProfileRed,
        iconBackgroundColor = ProfileRedBackground,
        text = R.string.logout,
        shouldTint = true
    ),
}

@Composable
fun ProfileContentAction(
    modifier: Modifier,
    action: ProfileAction,
    onClick: () -> Unit = {}
) {
    Row(
        modifier =
        modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, color = MaterialTheme.colorScheme.navyTransparent),
            ) { onClick() }
            .padding(horizontal = 30.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier =
            Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color = action.iconBackgroundColor)
                .padding(all = 10.dp)
        ) {
            Icon(
                modifier = Modifier.size(25.dp),
                imageVector = ImageVector.vectorResource(id = action.icon),
                contentDescription = "action icon",
                tint = action.color
            )
        }
        Spacer(modifier = Modifier.width(25.dp))
        Text(
            modifier = Modifier.weight(5f),
            text = stringResource(id = action.text),
            style = MaterialTheme.typography.bodyLarge,
            color = if (action.shouldTint) action.color else MaterialTheme.colorScheme.onPrimaryContainer
        )
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_chevon_right),
            contentDescription = "action icon",
            tint = if (action.shouldTint) action.color else MaterialTheme.colorScheme.outline
        )
    }
}
