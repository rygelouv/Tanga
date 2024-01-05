package app.books.tanga.coreui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import app.books.tanga.coreui.theme.Shapes
import app.books.tanga.coreui.theme.extraExtraLarge

@Composable
fun InfoCard(
    @DrawableRes image: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.3f),
                shape = Shapes.extraExtraLarge
            )
            .background(
                MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.2f),
                shape = Shapes.extraExtraLarge
            )
            .padding(8.dp)
            .testTag("info_card")
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "Info Card Image",
                modifier = Modifier.size(80.dp),
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                modifier = Modifier.testTag("info_card_text"),
                text = "•Personal Development" +
                    " •Self-help" +
                    " •Psychology" +
                    " •Philosophy" +
                    " •Productivity" +
                    " •Lifestyle",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline,
            )
        }
    }
}
