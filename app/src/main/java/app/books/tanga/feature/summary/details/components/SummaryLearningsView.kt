package app.books.tanga.feature.summary.details.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.sp
import app.books.tanga.R
import app.books.tanga.coreui.common.ExcludeFromJacocoGeneratedReport
import app.books.tanga.coreui.theme.LocalSpacing
import app.books.tanga.coreui.theme.TangaTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun SummaryLearningsView(
    keyLearningsItems: ImmutableList<String>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(LocalSpacing.current.medium),
        verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.extraSmall)
    ) {
        Text(
            text = stringResource(id = R.string.summary_details_key_learnings_title),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.size(LocalSpacing.current.small))

        keyLearningsItems.forEach {
            KeyLearningsItem(text = it)
            Spacer(modifier = Modifier.width(LocalSpacing.current.medium))
        }
    }
}

@Composable
private fun KeyLearningsItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = app.books.tanga.coreui.R.drawable.ic_check),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(LocalSpacing.current.medium))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.outline,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 13.sp
        )
    }
}

@ExcludeFromJacocoGeneratedReport
@Preview(showBackground = true, device = "id:pixel_5")
@Composable
private fun SummaryDetailsScreenPreview() {
    TangaTheme {
        SummaryLearningsView(
            keyLearningsItems = listOf(
                "Create business operating system",
                "Set clear company vision",
                "Build accountable leadership team",
                "Measure key business metrics",
                "Solve issues through systematic approach"
            ).toImmutableList()
        )
    }
}
