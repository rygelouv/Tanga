package app.books.tanga.feature.pricing

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.books.tanga.R
import app.books.tanga.coreui.components.animatedBorder
import app.books.tanga.coreui.theme.LocalSpacing
import kotlinx.collections.immutable.toImmutableList

@Composable
fun PricingPlanItem(
    title: String,
    price: String,
    cadence: String,
    selected: Boolean,
    highlight: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appliedBorder = if (highlight) {
        modifier.fillMaxWidth().animatedBorder(
            borderColors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow).toImmutableList(),
            backgroundColor = Color.White,
            shape = MaterialTheme.shapes.large,
            borderWidth = 1.dp
        )
    } else {
        modifier.fillMaxWidth().border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.secondary,
            shape = MaterialTheme.shapes.large
        )
    }
    Row(
        modifier = appliedBorder
            .background(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                shape = MaterialTheme.shapes.large
            )
            .padding(
                horizontal = LocalSpacing.current.medium,
                vertical = LocalSpacing.current.medium
            )
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = if (selected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(
            modifier =
            Modifier
                .width(LocalSpacing.current.medium)
                .weight(1f)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = price,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.width(LocalSpacing.current.extraSmall))
            Text(
                modifier = Modifier.offset(y = 2.dp),
                text = cadence,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.width(LocalSpacing.current.small))
            if (selected) {
                Icon(
                    modifier = Modifier.size(14.dp),
                    painter = painterResource(id = R.drawable.ic_checked),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}
