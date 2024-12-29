package app.books.tanga.notifications.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.coreui.common.ExcludeFromJacocoGeneratedReport
import app.books.tanga.coreui.components.TangaButton
import app.books.tanga.coreui.components.TangaLinedButton
import app.books.tanga.coreui.theme.LocalSpacing
import app.books.tanga.coreui.theme.LocalTintColor
import app.books.tanga.coreui.theme.TangaTheme

@Composable
fun NotificationExplainerScreen(
    onSkip: () -> Unit,
    modifier: Modifier = Modifier,
    onAllow: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = LocalSpacing.current.small)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.small, vertical = LocalSpacing.current.medium),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = CircleShape
                    )
                    .size(32.dp)
                    .padding(9.dp)
                    .clickable { onSkip() },
                painter = painterResource(id = app.books.tanga.coreui.R.drawable.ic_close),
                contentDescription = null,
                tint = LocalTintColor.current.color
            )
        }

        Spacer(modifier = Modifier.height(LocalSpacing.current.small))

        Text(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.medium),
            text = stringResource(id = R.string.notification_explainer_message),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(LocalSpacing.current.extraLarge))

        Image(
            modifier =
            Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(horizontal = 58.dp),
            painter = painterResource(id = R.drawable.graphic_push_notifications_blue),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(LocalSpacing.current.extraLarge))

        Features()

        Spacer(modifier = Modifier.height(LocalSpacing.current.extraExtraLarge))

        TangaButton(
            modifier = Modifier.padding(horizontal = LocalSpacing.current.large),
            text = stringResource(id = R.string.notification_explainer_allow),
            showInLightColor = true,
            onClick = onAllow
        )

        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))

        TangaLinedButton(
            modifier = Modifier.padding(horizontal = LocalSpacing.current.large),
            text = stringResource(id = R.string.notification_explainer_not_now),
            onClick = onSkip
        )

        Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
    }
}

@Composable
fun Features(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = LocalSpacing.current.large),
        verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.large),
    ) {
        NotificationFeatureItem(R.drawable.schedule, stringResource(R.string.notification_weekly_alert_message))
        NotificationFeatureItem(
            R.drawable.price_sticker,
            stringResource(R.string.notification_summaries_update_alert_message)
        )
        NotificationFeatureItem(
            R.drawable.subscription,
            stringResource(R.string.notifications_subscriptions_discounts_alert_message)
        )
    }
}

@Composable
private fun NotificationFeatureItem(
    @DrawableRes icon: Int,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(28.dp),
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(LocalSpacing.current.medium))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.outline,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true)
@Composable
@ExcludeFromJacocoGeneratedReport
private fun NotificationExplainerScreenPreview() {
    TangaTheme {
        NotificationExplainerScreen(
            onSkip = {},
            onAllow = {}
        )
    }
}
